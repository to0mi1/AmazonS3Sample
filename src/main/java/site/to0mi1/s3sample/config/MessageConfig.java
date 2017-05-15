package site.to0mi1.s3sample.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Springのメッセージ多言語化。バリデーションエラーメッセージを日本語で取扱できるよう設定する。
 * @author tomiyama
 *
 */
@Configuration
public class MessageConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    private MessageSource messageSource;

    @Bean
    public LocalValidatorFactoryBean validator()
    {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Override
    public Validator getValidator()
    {
        return validator();
    }
}