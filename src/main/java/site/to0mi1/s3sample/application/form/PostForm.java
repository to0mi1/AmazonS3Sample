package site.to0mi1.s3sample.application.form;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 * トップ画面に表示される投稿フォームの定義。
 * バリデーションの定義などもここで行う。
 * @author to0mi1
 */
public class PostForm {
	/**
	 * 投稿本文.
	 * 本文は、必須入力.
	 */
	@NotBlank
	private String text;
	
	/**
	 * 添付ファイル.
	 */
	private MultipartFile file;
	
	/*
	 * 以下、アクセッサ
	 */
	public String getText() {
		return text;
	}
	public void setText(String test) {
		this.text = test;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
