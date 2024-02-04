package model.piecePuzzle;

import java.util.HashMap;
import java.util.Map;

import model.colors.ColorPalette;

public class TetrisPieceL implements PiecePuzzle {

	// Warning: Beaucoup d'attributs sont partagés dans les classes Pièce L, T, U etc... Une classe abstraite aurait été appréciée.

	public static final Map<Integer, Map<Integer, Boolean[][]>> ensemblesForme = initShapes(); // BP: Il vaut mieux faire `ensemblesForme = initShapes();` plutôt que `ensemblesForme = new HashMap<>()` et un block `static {}` ingérable.
	private Boolean[][] shape;
	private int height;
	private int width;
	private int currentOrientation;
	private PointTetris center;
	private String color;

	// Warning: Je vois bien l'idée de réutiliser les matrices entre les pièces. Par contre,
	// tout mettre dans un block "static" me semble barbare, il vaut mieux utiliser
	// des fonctions static pour les initialiser
	static {
		Map<Integer, Boolean[][]> forme3x3 = new HashMap<>();
		Map<Integer, Boolean[][]> forme5x5 = new HashMap<>();
		int height3 = 3;
		int width3 = 3;
		int height5 = 5;
		int width5 = 5;
		/*
		 * for width 3 and height 3
		 */
		Boolean[][] matriceRight = new Boolean[height3][width3];
		Boolean[][] matriceUp = new Boolean[height3][width3];

		Boolean[][] matriceLeft = new Boolean[width3][height3];
		Boolean[][] matriceDown = new Boolean[width3][height3];
		/*
		 * for width 5 and height 5
		 */
		Boolean[][] matriceRight5 = new Boolean[height5][width5];
		Boolean[][] matriceUp5 = new Boolean[height5][width5];

		Boolean[][] matriceLeft5 = new Boolean[width5][height5];
		Boolean[][] matriceDown5 = new Boolean[width5][height5];

		for (int row = 0; row < height3; row++) {
			for (int col = 0; col < width3; col++) {
				if (row == height3 - 1 || col == 0) {
					matriceUp[row][col] = true;
				}

				else {
					matriceUp[row][col] = false;
				}
			}
		}

		for (int row = 0; row < height3; row++) {
			for (int col = 0; col < width3; col++) {
				if (row == 0 || col == width3 - 1) {
					matriceDown[row][col] = true;
				} else {
					matriceDown[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width3; row++) {
			for (int col = 0; col < height3; col++) {
				if (row == 0 || col == 0) {
					matriceLeft[row][col] = true;
				} else {
					matriceLeft[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width3; row++) {
			for (int col = 0; col < height3; col++) {
				if (row == width3 - 1 || col == height3 - 1) {
					matriceRight[row][col] = true;
				} else {
					matriceRight[row][col] = false;
				}
			}
		}

		forme3x3.put(0, matriceUp);
		forme3x3.put(1, matriceRight);
		forme3x3.put(2, matriceDown);
		forme3x3.put(3, matriceLeft);

		for (int row = 0; row < height5; row++) {
			for (int col = 0; col < width5; col++) {
				if (row == height5 - 1 || col == 0) {
					matriceUp5[row][col] = true;
				}

				else {
					matriceUp5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < height5; row++) {
			for (int col = 0; col < width5; col++) {
				if (row == 0 || col == width5 - 1) {
					matriceDown5[row][col] = true;
				} else {
					matriceDown5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width5; row++) {
			for (int col = 0; col < height5; col++) {
				if (row == 0 || col == 0) {
					matriceLeft5[row][col] = true;
				} else {
					matriceLeft5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width5; row++) {
			for (int col = 0; col < height5; col++) {
				if (row == width5 - 1 || col == height5 - 1) {
					matriceRight5[row][col] = true;
				} else {
					matriceRight5[row][col] = false;
				}
			}
		}
		forme5x5.put(0, matriceUp5);
		forme5x5.put(1, matriceRight5);
		forme5x5.put(2, matriceDown5);
		forme5x5.put(3, matriceLeft5);
		ensemblesForme.put(5, forme5x5);
		ensemblesForme.put(3, forme3x3);
	}

	public static Map<Integer, Map<Integer, Boolean[][]>> initShapes() {
		// BP: Et du coup, avec cette fonction cela devient intéressant de refactor en plusieurs petites fonctions

		Map<Integer, Map<Integer, Boolean[][]>> result = new HashMap<>();
		Map<Integer, Boolean[][]> forme3x3 = new HashMap<>();
		Map<Integer, Boolean[][]> forme5x5 = new HashMap<>();
		int height3 = 3;
		int width3 = 3;
		int height5 = 5;
		int width5 = 5;
		/*
		 * for width 3 and height 3
		 */
		Boolean[][] matriceRight = new Boolean[height3][width3];
		Boolean[][] matriceUp = new Boolean[height3][width3];

		Boolean[][] matriceLeft = new Boolean[width3][height3];
		Boolean[][] matriceDown = new Boolean[width3][height3];
		/*
		 * for width 5 and height 5
		 */
		Boolean[][] matriceRight5 = new Boolean[height5][width5];
		Boolean[][] matriceUp5 = new Boolean[height5][width5];

		Boolean[][] matriceLeft5 = new Boolean[width5][height5];
		Boolean[][] matriceDown5 = new Boolean[width5][height5];

		for (int row = 0; row < height3; row++) {
			for (int col = 0; col < width3; col++) {
				if (row == height3 - 1 || col == 0) {
					matriceUp[row][col] = true;
				}

				else {
					matriceUp[row][col] = false;
				}
			}
		}

		for (int row = 0; row < height3; row++) {
			for (int col = 0; col < width3; col++) {
				if (row == 0 || col == width3 - 1) {
					matriceDown[row][col] = true;
				} else {
					matriceDown[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width3; row++) {
			for (int col = 0; col < height3; col++) {
				if (row == 0 || col == 0) {
					matriceLeft[row][col] = true;
				} else {
					matriceLeft[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width3; row++) {
			for (int col = 0; col < height3; col++) {
				if (row == width3 - 1 || col == height3 - 1) {
					matriceRight[row][col] = true;
				} else {
					matriceRight[row][col] = false;
				}
			}
		}

		forme3x3.put(0, matriceUp);
		forme3x3.put(1, matriceRight);
		forme3x3.put(2, matriceDown);
		forme3x3.put(3, matriceLeft);

		for (int row = 0; row < height5; row++) {
			for (int col = 0; col < width5; col++) {
				if (row == height5 - 1 || col == 0) {
					matriceUp5[row][col] = true;
				}

				else {
					matriceUp5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < height5; row++) {
			for (int col = 0; col < width5; col++) {
				if (row == 0 || col == width5 - 1) {
					matriceDown5[row][col] = true;
				} else {
					matriceDown5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width5; row++) {
			for (int col = 0; col < height5; col++) {
				if (row == 0 || col == 0) {
					matriceLeft5[row][col] = true;
				} else {
					matriceLeft5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width5; row++) {
			for (int col = 0; col < height5; col++) {
				if (row == width5 - 1 || col == height5 - 1) {
					matriceRight5[row][col] = true;
				} else {
					matriceRight5[row][col] = false;
				}
			}
		}
		forme5x5.put(0, matriceUp5);
		forme5x5.put(1, matriceRight5);
		forme5x5.put(2, matriceDown5);
		forme5x5.put(3, matriceLeft5);
		result.put(5, forme5x5);
		result.put(3, forme3x3);
		return result;
	}

	public TetrisPieceL(PointTetris center, int height, int width, int currentOrientation) {
		this.center = center;
		this.height = height;
		this.width = width;
		this.color = new ColorPalette().randomColor();
		this.currentOrientation = currentOrientation;
		this.shape = ensemblesForme.get(this.height).get(this.currentOrientation);
	}

	@Override
	public void leftRotation() {
		if (currentOrientation == 3) {
			this.currentOrientation = 0;
			shape = ensemblesForme.get(this.height).get(currentOrientation);
		} else {
			this.currentOrientation++;
			shape = ensemblesForme.get(this.height).get(currentOrientation);
		}

	}

	@Override
	public void rightRotation() {
		if (currentOrientation == 0) {
			this.currentOrientation = 3;
			shape = ensemblesForme.get(this.height).get(currentOrientation);
		} else {
			this.currentOrientation--;
			shape = ensemblesForme.get(this.height).get(currentOrientation);
		}
	}

	@Override
	public int getHauteur() {
		return height;
	}

	@Override
	public int getLargeur() {
		return width;
	}

	@Override
	public Boolean[][] getShape() {
		return this.shape;
	}

	public PointTetris getCenter() {
		return this.center;
	}

	@Override
	public void updateCenter(PointTetris point) {
		this.center = point;
	}

	@Override
	public String getColor() {
		return this.color;
	}

	@Override
	public void setColor(String c) {
		this.color = c;
	}

	@Override
	public String toString() {
		// BP: Quand on fait des concatenations de string dans une boucle, il faut
		// utiliser un StringBuilder
		// BP: Vous pouvez utiliser une boucle `for(Boolean : )`
		// Exemple :

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < this.shape.length; i++) {
			for (Boolean isFilled : this.shape[i]) {
				if (isFilled) {
					stringBuilder.append("#");
				} else {
					stringBuilder.append("-");
				}
			}
			stringBuilder.append("\n");
		}
		// Et on finit par `return stringBuilder;`

		String str = "";
		for (int i = 0; i < this.shape.length; i++) {
			for (int j = 0; j < this.shape[i].length; j++) {
				if (this.shape[i][j]) {
					str += "#";
				} else {
					str += "-";
				}
			}
			str += "\n";
		}
		return str;
	}

}
