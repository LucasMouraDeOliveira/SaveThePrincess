/**
 * l'objet canvas dans l'arbre dom
 */
var canvas;

/**
 * le contexte graphique du canvas
 */
var ctx;

/**
 * une map contenant les images utilisées
 */
var images;

/**
 * Initialise le canvas
 */
function loadCanvas() {
	//Chargement du canvas
	canvas = document.getElementById("gameCanvas");
	ctx = canvas.getContext("2d");
	//Chargement des listeners
	$(window).on("keyup", keyReleased);
	$(window).on("keydown", keyPressed);
	$(window).on("mousedown", mousePressed);
	$(window).on("mouseup", mouseReleased);
	$(window).on("mousemove", mouseMoved);
	console.log("[CONFIG] Canvas loading is OK");
}

/**
 * Charge les images utilisées dans le canvas
 */
function loadImages() {
	images = new Map();
	var imgKnight = new Image();
	imgKnight.onload = function() {
		images.set("knight", imgKnight);
	}
	imgKnight.src = "img/knight.png";
}

/**
 * Met à jour l'affichage du canvas
 * 
 * @param data les nouvelles données de jeu à afficher
 */
function updateCanvas(data) {
	//On efface l'écran
	ctx.fillStyle = "white";
	ctx.fillRect(0, 0, canvas.width, canvas.height);
	//On dessine les entités
	var entities = data.entities;
	for(var i in entities) {
		var entity = entities[i];
		ctx.drawImage(images.get("knight"), entity.x, entity.y);
	}
}

/**
 * Au chargement de la page on intialise le canvas
 * et on récupère les images
 */
$(document).ready(function() {
	loadCanvas();
	loadImages();
});