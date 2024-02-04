package model.gameInitialisationStrategy;

import model.plateauPuzzle.PlateauPuzzle;

/*
 * Class qui implémente InitialConfigurationStrategy et définit la strategy medium
 */
public class MediumDifficulty implements InitialConfigurationStrategy {
	private PlateauPuzzle plateau;

	/*
	 * Passage d'une réf du plateau pour initialiser ses attributs
	 */
	public MediumDifficulty(PlateauPuzzle plateau) {
		this.plateau = plateau;
	}

	/*
	 * Méthode qui initialise les attributs du plateau
	*/

	@Override
	public void initialConfiguration() {
		this.plateau.setInitialMaxMoves(20);
		this.plateau.setHeight(20);
		this.plateau.setWidth(30);
		this.plateau.setMaxShapes(15);
	}

}
