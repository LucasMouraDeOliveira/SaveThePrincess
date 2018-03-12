/**
 * Socket de communication avec le serveur
 */
var socket;

/**
 * Identifiant du serveur sur lequel le joueur est connecté
 */
var server;

/**
 * Connecte le client à un serveur de jeu via une websocket
 */
function connect(serverName, playerName) {
	server = serverName;
	socket = new WebSocket("ws://localhost:8080/websocket-game")
	socket.onmessage = onMessage;
	socket.onopen = function() {
		var data = new Object();
		data.serverName = serverName;
		data.playerName = playerName;
		send("join-server", data);
	}
}

function send(action, data) {
	var obj = new Object();
	obj.action = action;
	obj.data = data;
	socket.send(JSON.stringify(obj));
}

/**
 * Fonction appelée lorsque la websocket côté client reçoit un message.
 * 
 * @param message les données du message
 */
function onMessage(message) {
	var json = JSON.parse(message.data);
	var type = json.type;
	console.log(type);
	if(type === "update") {
		updateCanvas(json.data);
	} else if(type === "join-server-success") {
		startUpdate(50);
	}
}