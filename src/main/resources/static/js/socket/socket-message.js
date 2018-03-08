/**
 * Socket de communication avec le serveur
 */
var socket;

/**
 * Identifiant du serveur sur lequel le joueur est connecté
 */
var serverId;

/**
 * Connecte le client au serveur via une websocket
 */
function connect() {
	socket = new WebSocket("ws://localhost:8080/websocket-game")
	socket.onmessage = onMessage;
	socket.onopen = function() {
		var data = new Object();
		serverId = "Serveur de test " + (Math.random()*9999);
		data.serverName = serverId;
		send("create-server", data);
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
	} else if(type === "create-server-success") {
		startUpdate(50);
	}
}