package com.lordkadoc.server.manager;

import java.util.HashMap;
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
	
	public boolean createServer(String serverName) {
		if(this.servers.get(serverName) != null) {
			return false;
		}
		Server server = new Server(serverName);
		this.servers.put(serverName, server);
		return true;
	}

	public void addPlayerToServer(WebSocketSession session, String playerName, String serverName) {
		Server server = this.servers.get(serverName);
		if(server == null)
			return;
		server.addPlayer(session, playerName);
	}
	
	public void startServer(String serverName) {
		Server server = this.servers.get(serverName);
		if(server != null) {
			server.startGame();
		}
	}
	
	public void sendUpdate(WebSocketSession session, String serverId, JsonObject data) {
		Server server = this.servers.get(serverId); 
		if(server != null) {
			server.sendUpdate(session, data);
		}
	}
	
//	public List<Server> listServers() {		
//		List<Server> servers = new ArrayList<Server>();
//		servers.addAll(this.servers.values());
//		return servers;
//	}
	
}