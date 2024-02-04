package model.chaineOfResponsibility;

import model.piecePuzzle.PiecePuzzle;
import model.plateauPuzzle.PlateauPuzzle;

/**
 * Gestionnaire concret qui vérifie s'il y a une collision avec une autre pièce
 * Tetris sur le plateau.
 */
public class PieceCollisionHandler extends AbstractTetrisHandler {

    // Constructeur initialisant le gestionnaire suivant dans la chaîne
    public PieceCollisionHandler(TetrisHandler nextHandler) {
        super(nextHandler);
    }

    // Traitement de la pièce Tetris en vérifiant s'il y a une collision avec une
    // autre pièce sur le plateau
    @Override
    public boolean handlePiece(int x, int y, PiecePuzzle piece, PlateauPuzzle plateau, boolean bool) {
        // BP: Attention, c'est pour vous le dernier maillon de la chaine de
        // responsabilité, mais dans le doute, il faut passer au suivant, si un jour
        // quelqu'un en rajoute un
        return plateau.collision(x, y, piece, bool);
    }
}
