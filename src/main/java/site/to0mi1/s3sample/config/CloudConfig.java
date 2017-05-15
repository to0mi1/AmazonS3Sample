package site.to0mi1.s3sample.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Cloudには、AWS上のインスタンスを自動解釈し、構成を管理？するような機能があるようだが、
 * 今回は、使用しないため、無効化しておく。これを有効化している場合は、AWS上で権限設定など少し面倒なこともあるかも。
 * @author tomiyama
 *
 */
@Configuration
@EnableAutoConfiguration(exclude={
		ContextStackAutoConfiguration.class})
public class CloudConfig {

}
