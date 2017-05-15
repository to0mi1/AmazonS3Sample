package site.to0mi1.s3sample.application.controller;

import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.s3.model.AmazonS3Exception;

import site.to0mi1.s3sample.application.form.PostForm;
import site.to0mi1.s3sample.application.helper.S3Helper;
import site.to0mi1.s3sample.domain.entitiy.Post;
import site.to0mi1.s3sample.domain.service.PostService;

/**
 * トップに画面になる、投稿画面のコントローラ。
 * @author tomiyama
 */
@Controller
@RequestMapping(value="/")
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PostService service;
	
	@Autowired
	S3Helper s3helper;
	
	@ModelAttribute
	private PostForm getPostForm() {
		return new PostForm();
	}
	
	/**
	 * S3から画像ファイルを取得する。
	 * @param S3に保存されたキー。パスから取得される
	 * @return 画像ファイルのbyte配列
	 */
	@ResponseBody
	@RequestMapping(value="img/{id}")
	public HttpEntity<byte[]> getImage(@PathVariable String id) {
		byte[] picture = null;
		try {
			picture = s3helper.getFile(id);
		} catch (AmazonS3Exception e) {
			// 取得に失敗した時は、NotFound
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		
		// レスポンスデータとして返却
		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(picture.length);
		return new HttpEntity<byte[]>(picture, headers);		
	}
	
	
	/**
	 * 表示ページを指定せずに、トップ画面を表示したとき呼ばれる。
	 * この時、最新の投稿を表示する。
	 * @param form
	 * @param model
	 * @return {@link IndexController#page}
	 */
	@RequestMapping(value={"index","/",}, method=RequestMethod.GET)
	public String index(PostForm form, Model model) {
		return page(0, model);
	}
	
	/**
	 * 表示ページを指定し、トップ画面を表示した場合に呼ばれる。投稿は最新投稿順に表示される。
	 * @param page
	 * @param form
	 * @param model
	 * @return {@link IndexController#page}
	 */
	@RequestMapping(value={"index","/",}, params = {"page"}, method=RequestMethod.GET)
	public String index(@RequestParam int page, PostForm form, Model model) {
		return page(page, model);
	}
	
	/**
	 * 投稿されたときに呼ばれる。投稿フォームのバリデーションチェックを実施し、添付ファイルの種類を検証する。
	 * 投稿内容は、DBへ永続化され、添付ファイルは、UUIDを利用してIDを取得した後、それを利用してAmazonS3へ保存される。
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return トップページ
	 */
	@RequestMapping(value={"index","/"}, method=RequestMethod.POST)
	public String post(@Valid PostForm form, BindingResult bindingResult, Model model) {
		
		// 今回は、画面上にイメージ画像として表示するため、代表的な画像ファイルのみ投稿を許可する。
		// 画像ファイル以外が投稿された場合には、トップ画面にファイル形式エラーメッセージを表示する。
		if (form.getFile() != null && !form.getFile().isEmpty() 
				&& !(      form.getFile().getContentType().equals(MediaType.IMAGE_JPEG_VALUE)
						|| form.getFile().getContentType().equals(MediaType.IMAGE_GIF_VALUE)
						|| form.getFile().getContentType().equals(MediaType.IMAGE_PNG_VALUE))) {
			bindingResult.reject("site.to0mi1.s3sample.error.filetype");
		}

		// バリデーションチェック
		if (bindingResult.hasErrors()) {
			logger.debug("バリデーションエラー");
			// エラーがあったときは、メッセージを表示しつつ元の画面へ
			return page(0, model);
		}
		
		logger.debug("text: " + /*form.getText() + */" file:" + form.getFile().getOriginalFilename());
		
		// データの永続化
		Post post = new Post();
		post.setText(form.getText());

		// 本当はコントローラに書きたくないけれど、Amazonに依存するためコントローラに書く
		if (form.getFile() != null && !form.getFile().isEmpty()) {
			StringBuffer fileNameBuffer = new StringBuffer(UUID.randomUUID().toString());
			if (form.getFile().getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
				fileNameBuffer.append(".jpg");
			} else if (form.getFile().getContentType().equals(MediaType.IMAGE_GIF_VALUE)) {
				fileNameBuffer.append(".png");
			} else if (form.getFile().getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
				fileNameBuffer.append(".gif");
			}
			
			// 添付ファイル保存
			try {
				s3helper.saveFile(form.getFile().getBytes(), fileNameBuffer.toString());
			} catch (IOException e) {
				logger.error("添付ファイルStreams取得エラー", e);
				return "redirect:index";
			}
			post.setFileId(fileNameBuffer.toString());
		}
		
		service.save(post);
		
		return "redirect:/";
	}
	
	
	/**
	 * 実際の投稿表示ロジック。引数に与えられたページの投稿を取得し、画面表示する。
	 * @param 表示対象のページ
	 * @param Viewへ渡すモデル
	 * @return トップ画面
	 */
	private String page (int pageIdx, Model model) {
		
		if (pageIdx < 0) { pageIdx = 0; }
		
		// サービスから、投稿を最新順で取得する。
		Page<Post> postPage = service.findAllOrderByIdDesc(pageIdx);
		PageWrapper<Post> page = new PageWrapper<>(postPage, "/");
		
		// モデルへ取得したコンテンツを設定
		model.addAttribute("posts", page.getContent());
		model.addAttribute("page", page);
		return "index";
	}
}
