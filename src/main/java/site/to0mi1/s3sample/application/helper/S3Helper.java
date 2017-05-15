package site.to0mi1.s3sample.application.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@Component
public class S3Helper {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Value("${S3SAMPLE_BUCKET}")
	private String s3Bucket;
	
	/**
	 * AmazonS3上にファイルを保管する。
	 * @param 保存対象ファイルのbyte配列
	 * @param 保存ファイル名
	 * @return 保存ファイル名。保存に失敗した場合には、nullを返却する。
	 */
	public String saveFile(byte[] data, String uploadTemporaryFileId) {
		Resource resource = resourceLoader.getResource("s3://" + s3Bucket + "/s3sample/" + uploadTemporaryFileId);
		WritableResource writableResource = (WritableResource) resource;
		try (OutputStream outputStream = writableResource.getOutputStream();) {
			outputStream.write(data);
		} catch (IOException e) {
			logger.error("s3ファイル保存エラー", e);
			return null;
		}
		return uploadTemporaryFileId;
	}
	
	/**
	 * AmaozonS3上に保存したファイルを取得する。
	 * @param 保存ファイル名
	 * @return ファイルのbyte配列
	 */
	public byte[] getFile(String TemporaryfileId) {
		Resource resource = resourceLoader.getResource("s3://test-pictec-s3/s3sample/" + TemporaryfileId);
		if (resource == null) {
			return null;
		}
		InputStream inputStream;
		try {
			inputStream = resource.getInputStream();
			// byteへ変換
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int c;
			while ((c = inputStream.read()) != -1) {
				bout.write(c);
			}
			return bout.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}
