package com.qurepass.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	/**
	 * Cross-Origin 이슈 설정
	 * @return
	 * @author 최창현
	 */
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOrigin("http://localhost:8060");
		config.addAllowedOrigin("http://localhost:8070");
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		filterBean.setOrder(0);
		
		return filterBean;
	}

}
