package com.lordkadoc.game;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.lordkadoc.entities.User;
import com.lordkadoc.services.users.UserService;

@Scope(value="singleton")
@Component
public class ServerManager {
	
	private int serverMaxId;
	
	private Map<Integer, Server> servers;
	
	@Autowired
	private UserService userService;
	
	public ServerManager() {
		this.servers = new HashMap<Integer, Server>();
		this.serverMaxId = 0;
	}
	
	public boolean joinServer(Integer serverId, Integer playerId) {
		System.out.println("trying to join server");
		Server server = this.getServer(serverId);
		if(server == null)
			return false;
		System.out.println("server id is valid");
		User user = this.userService.findUser(playerId);
		if(user == null)
			return false;
		//TODO rejoindre un serveur et retourner true
		return false;
	}
	
	public Integer createServer(String serverName) {
		this.serverMaxId++;
		//TODO vérifier que le nom n'est pas déjà pris
		Server server = new Server(serverName);
		this.servers.put(this.serverMaxId, server);
		return this.serverMaxId;
	}
	
	public Server getServer(Integer serverId) {
		return this.servers.get(serverId);
	}
	
}