package com.lordkadoc.server.manager;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private ServerManager serverManager;
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
		JsonReader jsonReader = Json.createReader(new StringReader(message.getPayload()));
		JsonObject object = jsonReader.readObject();
		JsonObject data = object.getJsonObject("data");
		String action = object.getString("action");
		if("create-server".equals(action)) {
			String serverName = data.getString("serverName");
			if(this.serverManager.createServer(serverName)) {
				this.serverManager.addPlayerToServer(session, "Player 1", serverName);
				this.serverManager.startServer(serverName);
				this.sendMessage(session, "create-server-success", "The creation of server " + serverName + " succeeded");
			} else {
				this.sendMessage(session, "create-server-failure", "The creation of server " + serverName + " failed");
			}
		} else if("update".equals(action)) {
			//TODO send update to server
			String serverId = data.getString("serverId");
			serverManager.sendUpdate(session, serverId, data);
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//TODO traiter la connexion
		System.out.println("Websocket with id " + session.getId() + " connected");
		System.out.println(this.serverManager.toString());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//TODO gérer la fermeture de la socket en fonction du status
		System.out.println("Websocket with id " + session.getId() + " disconnected");
	}
	
	/**
	 * Envoie un message JSON à une session websocket ouverte
	 * 
	 * @param session la session websocket de l'utilisateur
	 * @param type le type de message
	 * @param message le contenu du message
	 */
	public void sendMessage(WebSocketSession session, String type, String message) {
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObject json = factory.createObjectBuilder().add("type", type).add("data", message).build();
		if(session.isOpen()){
			try {
				session.sendMessage(new TextMessage(json.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
