package model.chaineOfResponsibility;

import model.piecePuzzle.PiecePuzzle;
import model.plateauPuzzle.PlateauPuzzle;

/**
 * Gestionnaire concret qui vérifie si la pièce Tetris se trouve en dehors des limites du plateau
 */
public class BoundaryHandler extends AbstractTetrisHandler {

    // Constructeur initialisant le gestionnaire suivant dans la chaîne
    public BoundaryHandler(TetrisHandler nextHandler) {
        super(nextHandler);
    }

    // Traitement de la pièce Tetris en vérifiant si elle est en dehors des limites du plateau,
    // si elle est à l'intérieur, on l'envoie vers le gestionnaire suivant
    @Override
    public boolean handlePiece(int x, int y, PiecePuzzle piece, PlateauPuzzle plateau , boolean bool) {
        // BP: Pas besoin de condition ternaire, un simple && logique suffit: 
        // `return plateau.outOfRange(x, y, piece) && super.handlePiece(x, y, piece, plateau, bool);`
        return plateau.outOfRange(x, y, piece)? super.handlePiece(x, y, piece, plateau,bool) : false;
    }
}
