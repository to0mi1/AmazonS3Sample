package site.to0mi1.s3sample.framework.exception;

public class S3SampleException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String messageId;
	
	/**
	 * アプリケーションのカスタム例外.
	 * @param プロパティファイルと紐づくメッセージID
	 * @param 例外
	 */
	public S3SampleException(String messageId, Throwable e) {
		super(e);
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	

}
