package com.protectionDogs.protection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.protectionDogs.protection.filter.PostLoginAuthorizationFilter;
import com.protectionDogs.protection.filter.PreLoginAuthorizationFilter;

@SpringBootApplication
public class ProtectionDogsApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ProtectionDogsApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProtectionDogsApplication.class);
	}

	@Bean
	public FilterRegistrationBean<PreLoginAuthorizationFilter> preLoginFilter(){
	    FilterRegistrationBean<PreLoginAuthorizationFilter> registrationBean 
	      = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new PreLoginAuthorizationFilter());
	    registrationBean.addUrlPatterns("/petsProtection/prelogin/*");
	    return registrationBean;    
	}
	
	@Bean
	public FilterRegistrationBean<PostLoginAuthorizationFilter> postLoginFilter(){
	    FilterRegistrationBean<PostLoginAuthorizationFilter> registrationBean 
	      = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new PostLoginAuthorizationFilter());
	    registrationBean.addUrlPatterns("/petsProtection/postlogin/*");
	    return registrationBean;    
	}

}
