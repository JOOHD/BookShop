package com.joo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // Swagger2 version 활성화
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket api(){ // Docket : Swagger 설정을 할 수 있게 도와주는 클래스
		return new Docket(DocumentationType.SWAGGER_2)
				.select()// apis() / paths() 메서드를 사용할 수 있게 해준다.
				.apis(RequestHandlerSelectors.basePackage("com.joo.controller"))  // 컨트롤러가 패키지를 지정하여 해당 패키지 API를 문서화 한다.
				.paths(PathSelectors.any()) // apis중에서 특정 path조건 API만 문서화 하는 2차 필터
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false); // 400,404,500 .. 표기를 ui에서 삭제한다.(false)
	}
	
	private ApiInfo apiInfo() { // 제목, 설정 등 문서에 대한 정보를 주기 위해 호출한다.
        return new ApiInfoBuilder()
            .title("BookShop API Test")
            .description("BookShop REST API")
            .version("1.0.0")
            .contact(new Contact("BookShop GitHub","https://github.com/JOOHD/BookShop", ""))
            .build();
    }
	
	/* swagger-ui 페이지 연결 핸들러 설정 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    /* 
      -Swagger 3.0 JWT 로그인 구현 시, 인가가 필요한 API에 무분별 접근을 막기 위한 코드  	
    // swagger Authorize 버튼 생성을 위한 기능
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }

    // swagger Authorize 버튼 생성을 위한 기능
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

    // swagger Authorize 버튼 생성을 위한 기능
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
    */
}
