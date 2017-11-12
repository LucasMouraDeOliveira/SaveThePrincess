/**
 * l'objet canvas dans l'arbre dom
 */
var canvas;

/**
 * le contexte graphique du canvas
 */
var ctx;

/**
 * Initialise le canvas
 */
function loadCanvas() {
	//Chargement du canvas
	canvas = document.getElementById("gameCanvas");
	ctx = canvas.getContext("2d");
	//Chargement des listeners
	$(window).on("keyup", keyPressed);
	$(window).on("keydown", keyReleased);
	$(window).on("mousedown", mousePressed);
	$(window).on("mouseup", mouseReleased);
	$(window).on("mousemove", mouseMoved);
	console.log("[CONFIG] Canvas loading is OK");
}

/**
 * Au chargement de la page on intialise le canvas
 */
$(document).ready(function() {
	loadCanvas();
});