package com.shopme.admin.user.controller;

import java.io.IOException;
import java.security.Provider.Service;

import javax.swing.text.ChangedCharSetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;

@Controller
public class AccountController {

	@Autowired
	private UserService service;

	// authenticated principal is the logged-in user
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser, 
			Model model) {

		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);

		model.addAttribute("user", user);

		return "users/account_form";
	}
	
	@PostMapping("/account/update")
	public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile, 
			@AuthenticationPrincipal ShopmeUserDetails loggedUser) throws IOException {

		if (!multipartFile.isEmpty()) {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);

			User savedUser = service.updateAccount(user);

			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {

			// set photo to null for getPhotosImagePath() method's conditional statement in
			// User class
			if (user.getPhotos().isEmpty())
				user.setPhotos(null);
			service.updateAccount(user);
		}

		// change username at the logout navigation bar
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		redirectAttributes.addFlashAttribute("message", "Your Account Details have been updated.");

		return "redirect:/account";
	}
	
	
}
