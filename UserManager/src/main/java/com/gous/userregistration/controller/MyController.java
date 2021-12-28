 package com.gous.userregistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gous.userregistration.model.User;
import com.gous.userregistration.repository.UserRepository;



@Controller
public class MyController {

    @Autowired
    private UserRepository repo;
	
	 @GetMapping(" ")
	 public String viewHomePage() {
		return "index";
		 
	 }
	 
	 @GetMapping("/form_register")
	 public String viewFormRegistration(Model model) {
		 model.addAttribute("user", new User());
		
		 return "form_register";
		 
	 }
	 
	  @PostMapping("/process_register")
	  public String processRegistration(User user) {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		  String encodedPassword = encoder.encode(user.getPassword());
		  user.setPassword(encodedPassword);
		  repo.save(user);
		 return "register_success";
	  }
	  
	  @GetMapping("/list_users")
	  public String viewUsersList(Model model) {
		  List<User> listUsers = repo.findAll();
		  model.addAttribute("listUsers", listUsers);
		  return "users";
	  }
	  
	  @GetMapping("/updateUser/{id}")
		public String updateUser(@PathVariable("id") long id, Model model) {
			User user = repo.getById(id);
			model.addAttribute("user", user);
			return "edit_user";
		}

	   @GetMapping("/deleteUser/{id}")
		public ModelAndView deleteUser(@PathVariable("id") long id) {
			repo.deleteById(id);
			return new ModelAndView("redirect:/list_users");
		}
	   
	   @PostMapping("/saveUser")
		public ModelAndView saveUser(@ModelAttribute User user) {
			if (user.getId() != null) {
				User current = repo.getById(user.getId());
				user.setPassword(current.getPassword());	
			}
			 repo.save(user);
			 return new ModelAndView("redirect:/register_success");
		}
	     @PostMapping("/updateSaveUser")
		public ModelAndView updateSaveUser(@ModelAttribute User user) {
			if (user.getId() != null) {
				User current = repo.getById(user.getId());
				user.setPassword(current.getPassword());	
			}
			 repo.save(user);
			 return new ModelAndView("redirect:/list_users");
		}
	   
}
