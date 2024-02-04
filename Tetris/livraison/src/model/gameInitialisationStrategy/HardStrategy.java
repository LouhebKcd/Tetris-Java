package model.gameInitialisationStrategy;

import model.plateauPuzzle.PlateauPuzzle;

/*
 * Class qui implémente InitialConfigurationStrategy et définit la strategy hard
 */
public class HardStrategy implements InitialConfigurationStrategy {
	private PlateauPuzzle plateau;

	/*
	 * Passage d'une réf du plateau pour initialiser ses attributs
	 */
	public HardStrategy(PlateauPuzzle plateau) {
		this.plateau = plateau;
	}

	/*
	 * Méthode qui initialise les attributs du plateau
	 */
	@Override
	public void initialConfiguration() {
		this.plateau.setInitialMaxMoves(15);
		this.plateau.setHeight(20);
		this.plateau.setWidth(40);
		this.plateau.setMaxShapes(20);

	}

}
