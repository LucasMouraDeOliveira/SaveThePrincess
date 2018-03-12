$(document).ready(function() {
	
	// Lorsque la page est chargée, on initie la connexion au serveur. 
	// En fonction du message qu'on reçoit, on démarre la boucle d'update.
	connect($("#server-name").val(), $("#player-name").val());
	
});