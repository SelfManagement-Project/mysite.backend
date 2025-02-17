package com.mysite.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    @Value("${file.upload.linux}")
    private String linuxUploadDir;

    @Value("${file.upload.windows}")
    private String windowsUploadDir;
    

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 경로
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedOrigins("http://localhost:5173")
				.allowedHeaders("*") // 모든 요청해더
				.exposedHeaders("Authorization")// 노출시킬 헤더
				.allowCredentials(true); // 쿠키허용
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    String uploadDir = System.getProperty("os.name").toLowerCase().contains("win") 
	        ? windowsUploadDir : linuxUploadDir;
	    registry.addResourceHandler("/upload/**")
        		.addResourceLocations("file:" + uploadDir + "/");
	}
}
