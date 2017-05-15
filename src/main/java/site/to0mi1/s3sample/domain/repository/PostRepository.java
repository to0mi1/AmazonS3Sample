package site.to0mi1.s3sample.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.to0mi1.s3sample.domain.entitiy.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
