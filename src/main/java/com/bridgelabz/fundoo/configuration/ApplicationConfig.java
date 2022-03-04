package com.bridgelabz.fundoo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.fundoo.interceptor.TokenInterceptor;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer{
	
	@Autowired
	private TokenInterceptor tokenInterceptor;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor);
	}
	
	@Bean
    public NewTopic generalTopic() {
        return TopicBuilder.name("BY")
                  .partitions(3)
                  .replicas(1)
                  .build();
    }
	
}
