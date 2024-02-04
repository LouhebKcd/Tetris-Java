package model.chaineOfResponsibility;

import model.piecePuzzle.PiecePuzzle;
import model.plateauPuzzle.PlateauPuzzle;

/**
 * Interface définissant le contrat pour les gestionnaires de pièces Tetris dans la chaîne de responsabilité.
 */

public interface TetrisHandler {
    
    //Fonction qui Traite une pièce Tetris en fonction des coordonnées et du plateau de jeu.
    boolean handlePiece(int x, int y, PiecePuzzle piece, PlateauPuzzle plateau , boolean bool);
}
