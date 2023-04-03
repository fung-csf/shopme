package com.shopme.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.SettingService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomerController {
	
	@Autowired private CustomerService customerService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<Country> listCountries = customerService.listAllCountries();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Customer Registration");
		model.addAttribute("customer", new Customer());
		
		return "register/register_form";
	}
	
	@PostMapping("/create_customer")
	public String createCustomer(@ModelAttribute(name = "customer") Customer customer, 
			Model model, HttpServletRequest request)  {
			
		customerService.registerCustomer(customer);
//		sendVerificationEmail(request, customer);
		
		model.addAttribute("pageTitle", "Registration Succeeded!");
		
		return "register/register_success";
	}
	
	
}
