package study.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableSwagger2 // 어노테이션을 추가하여 Swagger2선언 어노테이션
@Configuration // config 파일에 사용하는 어노테이션
public class SwaggerConfiguration {

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("study.lms.apicontroller")) // 설정할 패키지설정, any 일경우 전부
                .paths(PathSelectors.any()) // 설정한 url들만 필터링, any 일경우 전부
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .globalResponseMessage(RequestMethod.GET, getArrayList());
    }

    private ArrayList<ResponseMessage> getArrayList(){
        ArrayList<ResponseMessage> lists = new ArrayList<ResponseMessage>();
        lists.add(new ResponseMessageBuilder().code(500).message("서버에러").build());
        lists.add(new ResponseMessageBuilder().code(403).message("권한없음").build());
        return lists;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API 타이틀")
                .description("API 상세소개 및 사용법")
                .version("1.0") // 현재배포버전
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "jwt", "header");
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        };
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

}
