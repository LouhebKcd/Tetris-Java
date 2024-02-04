package model.chaineOfResponsibility;

import model.piecePuzzle.PiecePuzzle;
import model.plateauPuzzle.PlateauPuzzle;

/**
 * Class abstraite implémentant l'interface TetrisHandler et fournissant une base pour les gestionnaires Tetris.
 */
public abstract class AbstractTetrisHandler implements TetrisHandler {
    protected TetrisHandler nextHandler;

    // Constructeur initialisant le gestionnaire suivant
    public AbstractTetrisHandler(TetrisHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Traitement de la pièce Tetris en appelant le gestionnaire suivant si disponible
    @Override
    public boolean handlePiece(int x, int y, PiecePuzzle piece, PlateauPuzzle plateau , boolean bool) {
        
        // Si un gestionnaire suivant est disponible, appeler sa méthode handlePiece
        if (nextHandler != null) {
            return nextHandler.handlePiece(x, y, piece, plateau, bool);
        }
        // Si aucun gestionnaire suivant, retourner true (traitement réussi par défaut)
        return true;
    }
}
