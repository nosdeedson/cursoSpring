package br.com.edson.cursoSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class OpenApiDocumentation implements WebMvcConfigurer {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.edson.cursoSpring.resources"))
					.paths(PathSelectors.any())
				.build()
				.apiInfo(this.apiInfo());
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	
	
	}
	private ApiInfo apiInfo() {
//		return new ApiInfo("API do curso Realizado na AlgaWorks",
//				"Esta API é utilizada", "Versão 1.0",
//				"Curso Spring",
//				new Contact("Edson Jose de Souza", "AlgaWorks", "nosdejs32@gmail.com"),
//				"Permitido uso para estudantes", "teste", 
//				Collections.emptyList() 
//		);
		
		return new ApiInfoBuilder()
				.title("API do curso Realizado na AlgaWorks")
				.description("Api para delevery de comida tipo Ifood")
				.contact( new Contact("Edson José de Souza", "https://nosdeedson.github.io/", 
						"nosdejs32@gmail.com"))
				.version("1")
				.build();
	}
}
