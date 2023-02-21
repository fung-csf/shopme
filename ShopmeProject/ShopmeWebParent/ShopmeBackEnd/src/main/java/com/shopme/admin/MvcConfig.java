package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	
	/*
	 * In short, we can tell Spring MVC to map a given URL (e.g. "/user-photos") 
	 * to any directory (e.g. D:/ShopmeProject/ShopmeBackend/user-photos) 
	 * and expose the resources (images, js, css...) in this directory to 
	 * the web clients (browsers) - that's the primary purpose
	 *  of using resource handlers in Spring MVC.
	 * 
	 * */
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String dirName = "user-photos";
		
		Path userPhotoDir = Paths.get(dirName);
		
		String userPhotosPath = userPhotoDir.toFile().getAbsolutePath();
		
		
		// add /** to allow all files under this directory to be available to client
		
		registry.addResourceHandler("/" + dirName + "/**")
		.addResourceLocations("file:/" + userPhotosPath + "/");
		
	}
	
	
	
	
}
