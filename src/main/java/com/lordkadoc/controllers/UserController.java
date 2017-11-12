package com.lordkadoc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lordkadoc.entities.User;
import com.lordkadoc.services.users.UserService;

@Controller
@RequestMapping("/signin")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String signin() {
		return "signin";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView createAccount(HttpServletRequest request) {
		
		//Request parameters
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		
		//Model
		ModelAndView model = new ModelAndView();
		
		// TODO vérifier les champs
		
		User user = this.userService.findUserByLogin(login);
		if (user != null) {
			model.setViewName("signin");
			model.addObject("error", "This login is already taken");
		} else {
			user = new User();
			user.setLogin(login);
			user.setEmail(email);
			user.setPassword(password);
			this.userService.insertUser(user);
			model.setViewName("");
		}
		return model;
	}

}
