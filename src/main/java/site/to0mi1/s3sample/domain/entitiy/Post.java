package site.to0mi1.s3sample.domain.entitiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 投稿データのエンティティ。
 * @author tomiyama
 *
 */
@Entity
@Table(name="tb_posts")
public class Post {
	/**
	 * 投稿ID（主キー）.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	/**
	 * 投稿内容.
	 */
	@Lob
	@Column(name="text")
	@NotNull
	private String text;

	
	/**
	 * 添付ファイルをS3へ保存した時のID.これでファイルをS3より取得する.
	 */
	@Column(name="file_id")
	private String fileId;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	
}
