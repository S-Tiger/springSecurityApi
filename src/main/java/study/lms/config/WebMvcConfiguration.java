package study.lms.config;

import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import study.lms.common.security.AuthorizationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthorizationFilter authorizationFilter;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authorizationFilter)
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/user/login","/api/user/sign");
    }
}
