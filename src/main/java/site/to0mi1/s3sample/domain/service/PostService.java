package site.to0mi1.s3sample.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import site.to0mi1.s3sample.application.helper.S3Helper;
import site.to0mi1.s3sample.domain.entitiy.Post;
import site.to0mi1.s3sample.domain.repository.PostRepository;

/**
 * 投稿画面ののビジネスロジック.
 * ここでは、DBへのデータを取り扱う。S3へのデータ保存は、Amazon S3へ依存しているためドメインから取り除いている。
 * S3関連の機能は、{@link S3Helper}を参照.
 * @author tomiyama
 *
 */
@Service
@Transactional
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	public Post save(Post post) {
		return postRepository.save(post);
	}
	
	public Page<Post> findAllOrderByIdDesc(int page){
		return postRepository.findAll(new PageRequest(page, 100, Direction.DESC, "id"));
	}
}
