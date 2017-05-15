package site.to0mi1.s3sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.MultipartFilter;

@SpringBootApplication
public class S3SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3SampleApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean userInsertingMdcFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		MultipartFilter filter = new MultipartFilter();
		registrationBean.setFilter(filter);
		return registrationBean;
	}
}
