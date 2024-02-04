package model.pieceFactory;

import model.colors.ColorPalette;
import model.piecePuzzle.PiecePuzzle;
import model.piecePuzzle.PointTetris;
import model.piecePuzzle.TetrisPieceL;
import model.piecePuzzle.TetrisPieceT;
import model.piecePuzzle.TetrisPieceU;

/*
 * Factory pour les trois types de formes "U" "L" "T"
 */
public class PieceFactory {
	ColorPalette colorPalette = new ColorPalette();

	// Méthode qui créer un "L"
	public PiecePuzzle createShapeL(PointTetris center, int hauteur, int largeur, int currentOrientation) {
		return new TetrisPieceL(center, hauteur, largeur, currentOrientation);
	}

	// Méthode qui créer un "T"
	public PiecePuzzle createShapeT(PointTetris center, int hauteur, int largeur, int currentOrientation) {
		return new TetrisPieceT(center, hauteur, largeur, currentOrientation);
	}

	// Méthode qui créer un "U"
	public PiecePuzzle createShapeU(PointTetris center, int hauteur, int largeur, int currentOrientation) {
		return new TetrisPieceU(center, hauteur, largeur, currentOrientation);
	}

}
