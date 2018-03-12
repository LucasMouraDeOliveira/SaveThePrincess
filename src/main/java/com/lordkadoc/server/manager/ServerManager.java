package com.lordkadoc.server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Scope(value="singleton")
@Component
public class ServerManager {
	
	private Map<String, Server> servers;
	
//	@Autowired
//	private UserService userService;
	
	public ServerManager() {
		this.servers = new HashMap<String, Server>();
	}
	
//	public boolean joinServer(Integer serverId, String userName) {
//		//On vérifie si le serveur existe
//		Server server = this.getServer(serverId);
//		if(server == null)
//			return false;
//		
//		//On vérifie si le joueur existe
//		User user = this.userService.findUserByLogin(userName);
//		if(user == null)
//			return false;
//		
//		//On essaie de l'ajouter au serveur
//		return server.addPlayer(user);
//	}
	
//	public void leaveServer(Integer serverId, String playerToken) {
//		//TODO quitter un serveur
//	}
	
	public boolean createServer(String serverName, int maxPlayers) {
		if(this.servers.get(serverName) != null) {
			return false;
		}
		Server server = new Server(serverName, maxPlayers);
		//TODO mettre à un autre endroit
		this.servers.put(serverName, server);
		this.startServer(serverName);
		return true;
	}

	public boolean addPlayerToServer(WebSocketSession session, String playerName, String serverName) {
		Server server = this.servers.get(serverName);
		if(server == null)
			return false;
		return server.addPlayer(session, playerName);
	}
	
	public void startServer(String serverName) {
		Server server = this.servers.get(serverName);
		if(server != null) {
			server.startGame();
		}
	}
	
	public void sendUpdate(WebSocketSession session, String serverName, JsonObject data) {
		Server server = this.servers.get(serverName); 
		if(server != null) {
			server.sendUpdate(session, data);
		}
	}
	
	public List<ServerInfo> listServers() {		
		List<ServerInfo> serverInfos = new ArrayList<ServerInfo>();
		for(Server server : this.servers.values()) {
			serverInfos.add(new ServerInfo(server.getName(), 1, 10));
		}
		return serverInfos;
	}
	
}