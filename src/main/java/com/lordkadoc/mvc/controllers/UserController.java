package com.lordkadoc.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lordkadoc.mvc.entities.User;
import com.lordkadoc.mvc.entities.transfert.UserDTO;
import com.lordkadoc.mvc.services.users.UserService;

@Controller
@RequestMapping("/signin")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "signin";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView createAccount(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, HttpServletRequest request) {
		
		//Model
		ModelAndView model = new ModelAndView();
		model.setViewName("signin");
		
		if(result.hasErrors()) {
			model.addObject("error", "champ invalide");
		} else {
			//If the user form is valid, tries to register it
			User user = this.userService.findUserByLogin(userDTO.getLogin());
			if (user != null) {
				model.addObject("error", "This login is already taken");
			} else {
				user = new User();
				user.setLogin(userDTO.getLogin());
				user.setEmail(userDTO.getEmail());
				user.setPassword(userDTO.getPassword());
				this.userService.saveUser(user);
			}
		}
		
		return model;
	}

}
