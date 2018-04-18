package com.imooc.bootsell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
		@Bean
		public Docket demoApi() {
				return new Docket(DocumentationType.SWAGGER_2)
						.apiInfo(this.apiInfo())
						.groupName("boot-sell")
						.genericModelSubstitutes(DeferredResult.class)
						.useDefaultResponseMessages(false)
						.forCodeGeneration(true)
						.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
						.select()
						.apis(RequestHandlerSelectors.basePackage("com.imooc"))
						.build();
		}

		private ApiInfo apiInfo() {
				return new ApiInfoBuilder().title("boot-sell")
						.description("java项目的例子及组件的使用方式").contact(new Contact("chenfu", "", "1062281158@qq.com"))
						.version("1.0").build();
		}
}
