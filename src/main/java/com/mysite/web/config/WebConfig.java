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
				.allowedOrigins(
						"http://localhost:5173", 
						"http://localhost:8000,",
						"http://10.0.2.2:9000",        // ✅ Android 에뮬레이터용 추가
				        "http://192.168.0.10:9000",      // ✅ 실제 기기에서 접속할 때 PC의 IP도 미리 추가해줘도 좋음
				        "http://192.168.226.180:9000",	// 내핸드폰 ip
						"http://192.168.219.178:9000",
						"http://192.168.0.6:9000"
						)
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
