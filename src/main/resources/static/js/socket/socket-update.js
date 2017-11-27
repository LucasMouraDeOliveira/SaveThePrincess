/**
 * Message envoyé au serveur à chaque frame
 */
var message = {};

var loop;

/**
 * Démarre une boucle qui envoie les informations du client au serveur toutes les x millisecondes.
 * 
 * @param delay le délai en millisecondes entre deux mises à jour du serveur
 */
function startUpdate(delay) {
	loop = window.setInterval(function() {
		var updatedData = getUpdatedData();
		stompClient.send(updatedData);
	}, delay);
}

/**
 * Stoppe la boucle d'envoi de données au serveur.
 */
function stopUpdate() {
	window.clearInterval(loop);
}

/**
 * Renvoie l'état des touches et de la souris du joueur au format JSON.
 * 
 * @returns un objet JSON contenant les inputs du joueur pour la frame courante
 */
function getUpdatedData() {
	message.keyboard = keyboard;
	message.mouse = mouse;
	return JSON.stringify(message);
}