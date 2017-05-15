package site.to0mi1.s3sample.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import site.to0mi1.s3sample.framework.exception.S3SampleException;

/**
 * アプリケーション内で発生したカスタム例外のS3SampleExceptionをハンドルする。
 * 例外発生した場合は、プロパティファイルからメッセージを取得し、エラー画面を表示する。
 * @author tomiyama
 *
 */
@ControllerAdvice
public class ExceptionHandler {

	private Logger logger =  LoggerFactory.getLogger(ExceptionHandler.class);

	@Autowired
	MessageSource source;

	@org.springframework.web.bind.annotation.ExceptionHandler({ S3SampleException.class })
	public ModelAndView handleAppError(S3SampleException exception) {
		logger.error(exception.getMessage(), exception);
		ModelAndView modelAndView = new ModelAndView("error");
		try {
			modelAndView.addObject("message", source.getMessage(exception.getMessageId(), null, null));
		} catch (NoSuchMessageException e) {
			modelAndView.addObject("message", "システムエラーが発生しました。");
		}
		return modelAndView;
	}
}
