/**
 * Stocke l'état de la souris du joueur pour la frame courante
 */
var mouse = {
	left: false,
	right: false
};

/**
 * Codes des boutons de la souris
 */
const MOUSE_LEFT = 1;
const MOUSE_RIGHT = 3;

/**
 * Met à jour l'état de la souris après un clic de la souris
 * 
 * @param event évenement clavier
 */
function mousePressed(event) {
	switch(event.which){
	case MOUSE_LEFT:		
		mouse.left = true;
		break;
	case MOUSE_RIGHT:
		event.preventDefault();
		mouse.right = true;
		break;
	}
}

/**
 * Met à jour l'état de la souris après un relâchement de la souris
 * 
 * @param event évenement clavier
 */
function mouseReleased(event) {
	switch(event.which){
	case MOUSE_LEFT:		
		mouse.left = false;
		break;
	case MOUSE_RIGHT:
		mouse.right = false;
		break;
	}
}

/**
 * Met à jour l'état de la souris après un mouvement de la souris
 * 
 * @param event évenement clavier
 */
function mouseMoved(event) {
	var x = event.pageX - canvas.offsetLeft;
	var y = event.pageY - canvas.offsetTop;
	mouse.x = x;
	mouse.y = y;
}
