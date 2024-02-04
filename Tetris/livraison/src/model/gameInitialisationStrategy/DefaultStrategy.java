package model.gameInitialisationStrategy;

import model.plateauPuzzle.PlateauPuzzle;

/*
 * Class qui implémente InitialConfigurationStrategy et définit  la strategy par defaut
 */
public class DefaultStrategy implements InitialConfigurationStrategy {
	private PlateauPuzzle plateau;

	/*
	 * Passage d'une réf du plateau pour initialiser ses attributs
	 */
	public DefaultStrategy(PlateauPuzzle plateau) {
		this.plateau = plateau;
	}

	/*
	 * Méthode qui initialise les attributs du plateau
	 */
	@Override
	public void initialConfiguration() {
		this.plateau.setInitialMaxMoves(20);
		this.plateau.setHeight(20);
		this.plateau.setWidth(20);
		this.plateau.setMaxShapes(10);
	}
}
