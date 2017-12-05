/**
 * 
 */
var stompClient;

/**
 * Connecte le client au serveur via une websocket
 */
function connect() {
	var socket = new SockJS(PATH_TO_WEBSOCKET_ENDPOINT);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, onConnect);
	//Désactive les logs dans la console chaque fois qu'on reçoit/envoie un message
	stompClient.debug = null;
}

/**
 * Envoie des données à la websocket côté serveur.
 * 
 * @param route le destinataire du message
 * @param data les données à envoyer
 */
function send(route, data) {
	stompClient.send(SERVER_MESSAGE_ADDRESS+"/"+route, {}, data);
}

/**
 * "Abonne" la socket à une route pour qu'elle reçoive les messages à destination de cette route.
 * 
 * @param route la route d'accès aux messages
 * @param handler la méthode appelée lorqu'un message est reçu sur la route
 */
function subscribe(route, handler) {
	stompClient.subscribe(CLIENT_MESSAGE_ADDRESS + "/" + route, handler === null ? onMessage : handler);
}

function subscribeToQueue(handler) {
	stompClient.subscribe('/user/queue/messages', handler);
} 

function update(data) {
	send("update", data);
}

function join(data) {
	send("join", data);
}

function create(data) {
	send("create", data);
}

/**
 * Déconnecte la websocket du serveur.
 */
function disconnect() {
	if (stompClient !== null) {
        stompClient.disconnect();
    }
}

/**
 * Fonction appelée lorsque la connexion au serveur en websocket à réussi.
 * 
 * @param data les données retournées par le serveur
 */
function onConnect(data) {
	console.log("[CONFIG] WebSocket connection is OK");
	console.log("[DATA] received message from server : " + data);
	subscribe("update");
	subscribe("joinOk", onJoin);
	subscribe("createOk", onCreate);
//	var joinServerMessage = JSON.stringify({'playerId' : 1, 'serverId' : 1});
//	join(joinServerMessage);
	var createServerMessage = JSON.stringify({'serverName' : 'Serveur de test'});
	create(createServerMessage);
}

/**
 * Fonction appelée lorsque la websocket côté client reçoit un message.
 * 
 * @param message les données du message
 */
function onMessage(message) {
	//console.log(message.body);
}

/**
 * Fonction appelée lorsque la création d'un serveur à réussi.
 * 
 * @param message les informations du serveur créé
 */
function onCreate(message) {
	var data = JSON.parse(message.body);
	var playerId = 1;
	join(JSON.stringify({'serverId' : data.serverId, 'playerId' : playerId}));
}

/**
 * Fonction appelée lorsque le client réussit à rejoindre un serveur
 * @param message
 */
function onJoin(message) {
	//TODO
}