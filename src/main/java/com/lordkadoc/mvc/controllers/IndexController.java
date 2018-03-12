package com.lordkadoc.mvc.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lordkadoc.server.manager.ServerManager;

@Controller
public class IndexController {
	
	@Autowired
	private ServerManager serverManager;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/game")
	public String game(Model model, Principal principal, HttpServletRequest request) {
		String serverName = request.getParameter("serverName");
		String maxPlayerStr = request.getParameter("maxPlayers");
		int maxPlayer = 0;
		try {
			maxPlayer = Integer.valueOf(maxPlayerStr);
		} catch(NumberFormatException e) {
			//TODO renvoyer sur le formulaire de création avec une erreur
		}
		if(serverManager.createServer(serverName, maxPlayer)) {
			model.addAttribute("serverName", serverName);
			model.addAttribute("playerName", principal.getName());
		} else {
			//TODO renvoyer sur le formulaire de création avec une erreur
			//(le nom de serveur existe déjà)
		}
		return "game";
	}
	
	@RequestMapping("/joinGame") 
	public String joinGame(Model model, Principal principal, HttpServletRequest request){
		String serverName = request.getParameter("serverName");
		String playerName = principal.getName();
		model.addAttribute("serverName", serverName);
		model.addAttribute("playerName", playerName);
		return "game";
	}
	
	@RequestMapping("/createGame")
	public String createGame(){
		return "createGame";
	}
	
	@RequestMapping("/servers")
	public String servers(Model model) {
		model.addAttribute("servers", this.serverManager.listServers());
		return "servers";
	}
}
