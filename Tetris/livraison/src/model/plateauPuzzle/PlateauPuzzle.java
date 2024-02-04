package model.plateauPuzzle;

import java.util.ArrayList;

import controleur.AbstractModeleEcoutable;
import model.chaineOfResponsibility.BoundaryHandler;
import model.chaineOfResponsibility.PieceCollisionHandler;
import model.gameInitialisationStrategy.InitialConfigurationStrategy;
import model.pieceFactory.PieceFactory;
import model.piecePuzzle.PiecePuzzle;
import model.piecePuzzle.PointTetris;

public class PlateauPuzzle extends AbstractModeleEcoutable {

	// Attributs représentant les caractéristiques du plateau de puzzle
	private int width;
	private int height;
	private int maxShapes;
	private int maxMoves;
	// Warning: Ouch ! Pourquoi déclarer cette variable `public` ? une variable
	// privée avec get/set me parait plus approprié
	public PointTetris[][] grid; // Une grille de points Tetris
	private ArrayList<PiecePuzzle> listOfShapes = new ArrayList<>(); // Liste des formes sur le plateau
	private BoundaryHandler handler; // Gestionnaire de limites pour les pièces
	private PieceFactory pieceFactory; // Fabrique de pièces
	private InitialConfigurationStrategy initialStrategy; // Stratégie d'initialisation du jeu

	// Constructeur par défaut
	public PlateauPuzzle() {
		// Initializing the handler and the piece factory
		this.handler = new BoundaryHandler(new PieceCollisionHandler(null));
		this.pieceFactory = new PieceFactory();
	}

	// Méthode pour initialiser le jeu
	public void initializeGame() {
		// Initialisation des attributs du plateau avec la stratégie
		this.initialStrategy.initialConfiguration();
		// Initialisation de la grille
		this.grid = new PointTetris[this.getHeight()][this.getWidth()];
		for (int rows = 0; rows < this.getHeight(); rows++) {
			for (int cols = 0; cols < this.getWidth(); cols++) {
				this.grid[rows][cols] = new PointTetris(rows, cols);
			}
		}
		// Création de formes grace a la factorie
		while (this.listOfShapes.size() < maxShapes) {
			// Selection d'une forme aléatoire
			int forme = (int) (Math.random() * 3) + 1; // BP: Créez une seule instance `this.random = new Random()` dans
														// le constructeur de la classe, et appelez le avec
														// `this.random.nextInt(3)` par exemple

			// Selection des position x,y du centre de la forme
			int row = (int) (Math.random() * this.height); // BP: Pareil
			int col = (int) (Math.random() * this.width);// BP: Pareil

			// Selection de la taille de la piece
			int pieceSize = 0;
			if (Math.random() < 0.8) {
				pieceSize = 3;
			} else {
				pieceSize = 5;
			}
			int pieceOrientation = (int) (Math.random() * 3);// BP: Pareil
			PiecePuzzle piece = null;
			// BP: on préfère utiliser un `switch() {case: break}` plutôt qu'une suite de if
			if (forme == 1) {
				piece = pieceFactory.createShapeL(new PointTetris(row, col), pieceSize, pieceSize, pieceOrientation);
			} else if (forme == 2) {
				piece = pieceFactory.createShapeT(new PointTetris(row, col), pieceSize, pieceSize, pieceOrientation);
			} else if (forme == 3) {
				piece = pieceFactory.createShapeU(new PointTetris(row, col), pieceSize, pieceSize, pieceOrientation);
			}

			// Si la piéce respect bien toutes les contraintes on la pose sur le plateau
			if (piece != null && this.handler.handlePiece(row, col, piece, this, false)) {
				this.listOfShapes.add(piece);
				this.allocatePieces();
			}
		}
	}

	// Méthode pour rafraîchir la grille
	public void refreshGrid() {
		// On met les isOccupied des toutes les cells a false
		for (int row = 0; row < this.height; row++) {
			for (int col = 0; col < this.width; col++) {
				this.grid[row][col].setOccupied(false);
				this.grid[row][col].affectTo(-1);
			}
		}
	}

	// Méthode pour allouer les pièces sur la grille
	public void allocatePieces() {
		// Allocation des pièces sur la grille

		// On rafréchie d'abord le plateau
		this.refreshGrid();

		// Pour chaque piece
		for (int index = 0; index < this.listOfShapes.size(); index++) {
			PiecePuzzle piece = this.listOfShapes.get(index);
			Boolean[][] shape = piece.getShape();
			int heighHalf = piece.getHauteur() / 2;
			int widthHalf = piece.getLargeur() / 2;
			int row = 0;

			// on recupére les coordonnées de la cellule en haut a gauche de la piece par
			// rapport au plateau
			// et on applique leur isOccupied au isOccupied des cellules du plateau
			for (int i = piece.getCenter().getPositionX() - heighHalf; i < piece.getCenter().getPositionX() - heighHalf
					+ piece.getHauteur(); i++) {
				int col = 0;
				for (int j = piece.getCenter().getPositionY() - widthHalf; j < piece.getCenter().getPositionY()
						- widthHalf
						+ piece.getLargeur(); j++) {
					if (shape[row][col]) {
						this.grid[i][j].setOccupied(true);
						this.grid[i][j].affectTo(index);
					}
					col++;
				}
				row++;
			}
		}
	}

	// On fait bouger la piece séléctionné vers x+1, x-1, y+1 ou y-1 si sa nouvelle
	// position n'engendre aucune collision ou de outOfRangeException
	public void movePiece(int direction, PiecePuzzle piece) throws OutOfMapException {
		// BP: Utilisez un switch case
		if (direction == 1) {
			// Bas
			// Vérifie s'il est possible de déplacer la pièce vers le bas
			if (this.handler.handlePiece(piece.getCenter().getPositionX() + 1, piece.getCenter().getPositionY(), piece,
					this, true)) {
				// mettre a jour les positions de la cellule Centrale
				piece.updateCenter(this.grid[piece.getCenter().getPositionX() + 1][piece.getCenter().getPositionY()]);
				this.allocatePieces(); // Alloue à nouveau les pièces sur la grille
			} else {
				throw new OutOfMapException("impossible place already taken");
			}
		} else if (direction == 2) {
			// Droite
			// Logique similaire pour le déplacement vers la droite
			if (this.handler.handlePiece(piece.getCenter().getPositionX(), piece.getCenter().getPositionY() + 1, piece,
					this, true)) {
				// mettre a jour les positions de la cellule Centrale
				piece.updateCenter(this.grid[piece.getCenter().getPositionX()][piece.getCenter().getPositionY() + 1]);
				this.allocatePieces();
			} else {
				throw new OutOfMapException("impossible place already taken");
			}
		} else if (direction == 3) {
			// Gauche
			// Logique similaire pour le déplacement vers la gauche
			if (this.handler.handlePiece(piece.getCenter().getPositionX(), piece.getCenter().getPositionY() - 1, piece,
					this, true)) {
				// mettre a jour les positions de la cellule Centrale
				piece.updateCenter(this.grid[piece.getCenter().getPositionX()][piece.getCenter().getPositionY() - 1]);
				this.allocatePieces();
			} else {
				throw new OutOfMapException("impossible place already taken");
			}
		} else if (direction == 4) {
			// Haut
			// Logique similaire pour le déplacement vers le haut
			if (this.handler.handlePiece(piece.getCenter().getPositionX() - 1, piece.getCenter().getPositionY(), piece,
					this, true)) {
				// mettre a jour les positions de la cellule Centrale
				piece.updateCenter(this.grid[piece.getCenter().getPositionX() - 1][piece.getCenter().getPositionY()]);
				this.allocatePieces();
			} else {
				throw new OutOfMapException("impossible place already taken");
			}
		}
		fireChange(); // Notifie les écouteurs du changement
	}

	// Méthode qui vérifie s'il y a une collision lors de la rotation d'une pièce.
	public Boolean collisionRotation(PiecePuzzle p) {
		// Obtient la matrice représentant la forme de la pièce.
		Boolean[][] shape = p.getShape();
		int d = p.getHauteur() / 2; // Calcule la demi-hauteur de la pièce.
		int g = p.getLargeur() / 2; // Calcule la demi-largeur de la pièce.
		Boolean res = false; // Initialise la variable de résultat à faux.
		int count1 = 0;

		// Parcours de la zone où la pièce serait après rotation.
		for (int i = p.getCenter().getPositionX() - d; i < p.getCenter().getPositionX() - d + p.getHauteur(); i++) {
			int count2 = 0;
			for (int j = p.getCenter().getPositionY() - g; j < p.getCenter().getPositionY() - g + p.getLargeur(); j++) {
				// Vérifie si la cellule de la piece entre en collision avec une cellule déjà
				// occupée sur le plateau.
				// BP: Vous pouvez utiliser une seule expression, pas besoin de if.
				// Exemple : `res = res || (!shape[count1][count2] &&
				// this.grid[i][j].isOccupied())`.
				// Ainsi, dès qu'une seule collision est détecté, res restera à True grâce au
				// `res ||`

				if (!shape[count1][count2]) { // BP: Vous pouvez fusionner les if ici avec un && logique
												// `!shape[count1][count2] && this.grid[i][j].isOccupied()`
					if (this.grid[i][j].isOccupied()) {
						res = true; // Collision détectée.
					}
				}
				count2++;
			}
			count1++;
		}

		return res; // Retourne vrai s'il y a collision, faux sinon.
	}

	// Méthode qui vérifie s'il y a une collision à une position spécifiée sur le
	// plateau avec une pièce donnée.
	public Boolean collision(int x, int y, PiecePuzzle p, boolean bool) {

		Boolean[][] shape = p.getShape(); // Obtient la matrice représentant la forme de la pièce.

		int d = p.getHauteur() / 2; // Calcule la demi-hauteur de la pièce.
		int g = p.getLargeur() / 2; // Calcule la demi-largeur de la pièce.
		Boolean res = false; // Initialise la variable de résultat à faux.
		int count1 = 0;
		PlateauPuzzle copy = clonePlateau(); // Crée une copie du plateau pour simuler le prochain movement de la piece
												// dedans

		// Si la pièce doit être retirée du plateau (utilisé lors du déplacement de la
		// pièce).
		if (bool) {
			for (int i = p.getCenter().getPositionX() - d; i < p.getCenter().getPositionX() - d + p.getHauteur(); i++) {
				int count2 = 0;
				for (int j = p.getCenter().getPositionY() - g; j < p.getCenter().getPositionY() - g
						+ p.getLargeur(); j++) {
					if (shape[count1][count2]) {
						copy.getGrid()[i][j].setOccupied(false);
					}
					count2++;
				}
				count1++;
			}
		}

		// Parcours de la zone où la pièce serait après déplacement.
		count1 = 0;
		for (int i = x - d; i < x - d + p.getHauteur(); i++) {
			int count2 = 0;
			for (int j = y - g; j < y - g + p.getLargeur(); j++) {
				// Si la une des cellule de la piece aprés son déplacement a un isOccupied a
				// true et on trouve que
				// le isOccupied dans le plateau de la cellule qui va acceuilir cette cellule
				// est aussi a true il y'as donc une collision
				if (shape[count1][count2]) { // BP: Pareil, fusion de if et `res = res ||`
					if (copy.getGrid()[i][j].isOccupied()) {
						res = true; // Collision détectée.
					}
				}
				count2++;
			}
			count1++;
		}

		return !res;
	}

	// Méthode qui vérifie si la position spécifiée est en dehors des limites du
	// plateau pour une pièce donnée.
	public boolean outOfRange(int row, int col, PiecePuzzle piece) {
		// verifier si la forme elle sort de la grille ou pas!! chaud non?
		int hauteur = piece.getHauteur() / 2; // Calcule la demi-hauteur de la pièce.
		int largeur = piece.getLargeur() / 2; // Calcule la demi-largeur de la pièce.
		int offsetLeft = row - largeur;
		int offsetRight = row + largeur;
		int offsetTop = col - hauteur;
		int offsetButtom = col + hauteur;
		// Vérifie si la pièce dépasse les limites du plateau.
		return offsetButtom < this.width && offsetLeft >= 0 && offsetRight < this.height && offsetTop >= 0;
	}

	public void rotateLeft(PiecePuzzle piece) throws OutOfMapException { // BP: L'exception n'est jamais throw. Vous
																			// pouvez retirer le throws
		if (!this.collisionRotation(piece)) {
			piece.leftRotation();
			this.allocatePieces();
			fireChange();
		}
	}

	// Méthode qui effectue la rotation à gauche d'une pièce s'il n'y a pas de
	// collision.
	public void rotateRight(PiecePuzzle piece) throws OutOfMapException {
		if (!this.collisionRotation(piece)) {
			piece.rightRotation(); // Effectue la rotation à gauche.
			this.allocatePieces(); // Réalloue les pièces sur le plateau.
			fireChange(); // Notifie les écouteurs du changement.
		}
	}

	// Méthode qui calcule le score en fonction de la disposition des pièces sur le
	// plateau.
	public float calculateScore() {
		int maxX = -1;
		int minX = 1000;
		int maxY = -1;
		int minY = 1000;

		// BP: Vous devriez utiliser votre liste de Shape avec un calcul, on peut
		// connaitre les coordonnées de chacune des pièces. Ensuite, on regarde parmis
		// les pièces, quelles sont les x;y les plus petits, et quelles sont les
		// x+height;y+width les plus grand

		// Actuellement, vous parcourez toute la grille. Cela va poser des problèmes de
		// perf quand votre grille est grande et pas beaucoup remplis

		// Une manière esthétique de le faire existe à l'aide de stream() sur la liste des shapes

		// Parcours de toutes les cellules du plateau.
		for (int col = 0; col < this.width; col++) {
			for (int row = 0; row < this.height; row++) {
				// Vérifie si la cellule est occupée par une pièce ou appartient à une pièce.
				if (this.grid[row][col].getPartOf() != -1 || this.grid[row][col].isOccupied()) {

					// Met à jour les coordonnées minimales et maximales occupées par une pièce.
					if (minX > col) {
						minX = col;
					}
					if (minY > row) {
						minY = row;
					}

					if (maxX < col) {
						maxX = col;
					}
					if (maxY < row) {
						maxY = row;
					}
				}
			}
		}

		// Calcule la hauteur et la largeur du rectangle englobant les pièces.
		float rectangleHeight = (maxY - minY) + 1;
		float rectangleWidth = (maxX - minX) + 1;

		// Calcule la superficie occupée par les pièces par rapport à la superficie
		// totale du plateau.
		float occupaedArea = ((rectangleHeight * rectangleWidth) / (this.height * this.width)) * 100;

		// Retourne le pourcentage de la superficie totale du plateau qui est occupée
		// par les pièces.
		return 100 - occupaedArea;
	}

	// Méthode qui renvoie la pièce correspondant à un point Tetris donné sur le
	// plateau.
	public PiecePuzzle getForme(PointTetris chosenPoint) {
		return chosenPoint.getPartOf() != -1 ? this.listOfShapes.get((chosenPoint.getPartOf())) : null;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	// Méthode qui crée une copie du plateau.
	public PlateauPuzzle clonePlateau() {
		PlateauPuzzle clone = new PlateauPuzzle();
		clone.setStrategy(initialStrategy);
		clone.setGrid(new PointTetris[this.getHeight()][this.getWidth()]);

		// Copie l'état d'occupation de chaque cellule du plateau.
		for (int rows = 0; rows < this.getHeight(); rows++) {
			for (int cols = 0; cols < this.getWidth(); cols++) {
				clone.setNewCell(rows, cols);
				clone.getGrid()[rows][cols].setOccupied(this.grid[rows][cols].isOccupied());
			}
		}

		return clone;

	}

	// Méthode qui initialise une nouvelle cellule du plateau.
	public void setNewCell(int row, int col) {
		this.grid[row][col] = new PointTetris(row, col);
	}

	public void setGrid(PointTetris[][] newGrid) {
		this.grid = newGrid;
	}

	public PointTetris[][] getGrid() {
		return this.grid;
	}

	public ArrayList<PiecePuzzle> getShapes() {
		return this.listOfShapes;
	}

	public int getMaxMoves() {
		return this.maxMoves;
	}

	public void setInitialMaxMoves(int maxMoves) {
		this.maxMoves = maxMoves;
	}

	public void setMaxMoves() {
		this.maxMoves--;
	}

	public BoundaryHandler getHandler() {
		return this.handler;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMaxShapes(int maxFormes) {
		this.maxShapes = maxFormes;
	}

	public void setStrategy(InitialConfigurationStrategy initialStrategy) {
		this.initialStrategy = initialStrategy;
	}

	public InitialConfigurationStrategy getStrategy() {
		return this.initialStrategy;
	}

	// Méthode qui génère une représentation textuelle du plateau.
	@Override
	public String toString() {
		// BP: Cf la remarque du StringBuilder

		// afficher la grille
		String grid = "";
		for (int row = 0; row < this.height; row++) {
			for (int col = 0; col < this.width; col++) {
				if (this.grid[row][col].isOccupied()) {
					grid += this.listOfShapes.indexOf((this.getForme(this.grid[row][col])));
				} else {
					grid += this.grid[row][col];
				}

			}
			grid += "\n";
		}
		return grid;
	}
}
