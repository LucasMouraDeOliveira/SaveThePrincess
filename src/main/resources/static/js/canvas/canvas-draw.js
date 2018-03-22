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
 * la taille en pixels d'une case à l'écran
 */
var CELL_SIZE = 32;

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
	loadCharacterImages();
	loadFloorImages();
	loadWallImages();
}

function loadCharacterImages() {
	loadImage("knight", "knight.png");
}

function loadFloorImages() {
	loadImage("floor1", "floor/wooden_floor_top_left.png");
}

function loadWallImages() {
	loadImage("wall1", "wall/stone_wall_front_left.png");
	loadImage("wall2", "wall/stone_wall_front_right.png");
	loadImage("wall3", "wall/stone_wall_vertical_top.png");
	loadImage("wall4", "wall/stone_wall_vertical_bottom.png");
}

function loadImage(alias, src) {
	var img = new Image();
	img.onload = function() {
		images.set(alias, img);
	}
	img.src = "img/"+src;
}

/**
 * Met à jour l'affichage du canvas
 * 
 * @param data les nouvelles données de jeu à afficher
 */
function updateCanvas(data) {
	//On efface l'écran
	ctx.fillStyle = "black";
	ctx.fillRect(0, 0, canvas.width, canvas.height);
	
	//on récupère les infos du joueur
	var ownPlayer = data.ownPlayer;
	var offsetX = ownPlayer.x - (canvas.width/2);
	var offsetY = ownPlayer.y - (canvas.height/2);
	
	//on bloque l'offset sur les bords de la map
	offsetX = Math.max(offsetX, 0);
	offsetY = Math.max(offsetY, 0);
	offsetX = Math.min(offsetX, (data.width-canvas.width));
	offsetY = Math.min(offsetY, (data.height-canvas.height));
	
	//on dessine la map
	var cells = data.map;
	for(var i in cells) {
		var cell = cells[i];
		var floor = cell.floor;
		if(cell.floor !== undefined && cell.floor !== 0) {
			ctx.drawImage(images.get("floor"+cell.floor), (cell.x * CELL_SIZE) - offsetX, (cell.y * CELL_SIZE) - offsetY);
		}
		var wall = cell.wall;
		if(cell.wall !== undefined && cell.wall !== 0) {
			ctx.drawImage(images.get("wall"+cell.wall), (cell.x*CELL_SIZE) - offsetX, (cell.y * CELL_SIZE) - offsetY);
		}
	}
	
	//On dessine le joueur
	ctx.fillStyle = "white";
	ctx.textAlign = "center";
	ctx.fillText(ownPlayer.name, (ownPlayer.x) - offsetX, (ownPlayer.y-25) - offsetY);
	ctx.drawImage(images.get("knight"), (ownPlayer.x-25) - offsetX, (ownPlayer.y-25) - offsetY);
	
	//On dessine les entités
	var players = data.players;
	for(var i in players) {
		var player = players[i];
		ctx.textAlign = "center";
		ctx.fillText(player.name, (player.x) - offsetX, (player.y-25) - offsetY);
		ctx.drawImage(images.get("knight"), (player.x-25) - offsetX, (player.y-25) - offsetY);
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