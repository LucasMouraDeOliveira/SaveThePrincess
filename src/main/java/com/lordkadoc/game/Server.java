package com.lordkadoc.game;

import java.util.Map;

public class Server {
	
	private String name;
	
	private Map<String, Player> players;
	
	public Server(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public boolean addPlayer(String playerToken) {
		return false;
	}
	
	public boolean removePlayer(String playerToken) {
		return false;
	}
	
	public void startGame() {
		//TODO create game map and launch server
	}
	
	public void stopGame() {
		//TODO
	}
	
}
