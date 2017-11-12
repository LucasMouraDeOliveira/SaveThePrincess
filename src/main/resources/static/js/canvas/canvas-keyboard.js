/**
 * Stocke l'état des touches du joueur pour la frame courante
 */
var keyboard = {};

/**
 * Code des touches fléchées
 */
const ARROW_LEFT = 37;
const ARROW_UP = 38;
const ARROW_RIGHT = 39;
const ARROW_BOTTOM = 40;

/**
 * Met à jour l'état du clavier quand une touche est pressée.
 * 
 * @param event évenement clavier
 */
function keyPressed(event) {
	var preventDefault = true;
	switch(event.keyCode) {
	case ARROW_LEFT:
		keyboard.left = true;
		break;
	case ARROW_UP:
		keyboard.up = true;
		break;
	case ARROW_RIGHT:
		keyboard.right = true;
		break;
	case ARROW_BOTTOM:
		keyboard.bottom = true;
		break;
	default:
		preventDefault = false;
	}
	//Evite le défilement s'il y a une scrollbar
	if(preventDefault) {
		event.preventDefault();
	}
}

/**
 * Met à jour l'état du clavier quand une touche est relâchée
 * 
 * @param event évenement clavier
 */
function keyReleased(event) {
	switch(event.keyCode) {
	case ARROW_LEFT:
		keyboard.left = false;
		break;
	case ARROW_UP:
		keyboard.up = false;
		break;
	case ARROW_RIGHT:
		keyboard.right = false;
		break;
	case ARROW_BOTTOM:
		keyboard.bottom = false;
		break;
	}
}