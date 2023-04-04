package com.shopme;

import java.util.Properties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import com.shopme.setting.EmailSettingBag;

public class Utility {
	
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		
		System.out.println("Site URL: " + siteURL);
		System.out.println("ServletPath: " + request.getServletPath());
		
		/* output for the two print statements as follow,
		 * Site URL: http://localhost/Shopme/create_customer
		 * ServletPath: /create_customer
		 */
		
		return siteURL.replace(request.getServletPath(), "");
	}
	
	public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settings) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(settings.getHost());
		mailSender.setPort(settings.getPort());
		mailSender.setUsername(settings.getUsername());
		mailSender.setPassword(settings.getPassword());
		
		
		Properties mailProperties = new Properties();
		mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
		mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());
		
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}
}