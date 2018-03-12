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
	var imgFloor = new Image();
	imgFloor.onload = function() {
		images.set("floor", imgFloor);
	}
	imgFloor.src = "img/floor.png";
	var imgWall = new Image();
	imgWall.onload = function() {
		images.set("wall", imgWall);
	}
	imgWall.src = "img/wall-top.png";
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
	//on dessine la map
	var cells = data.map;
	for(var i in cells) {
		var cell = cells[i];
		ctx.drawImage(images.get("floor"), cell.x * 64, cell.y * 64);
		if(cell.obstacle !== undefined && cell.obstacle !== null) {
			ctx.drawImage(images.get("wall"), cell.x*64, cell.y * 64);
		}
	}
	//On dessine les entités
	var players = data.players;
	for(var i in players) {
		var player = players[i];
		ctx.textAlign = "center";
		ctx.fillText(player.name, player.x + 25, player.y);
		ctx.drawImage(images.get("knight"), player.x, player.y);
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