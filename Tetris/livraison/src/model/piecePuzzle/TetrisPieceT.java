package model.piecePuzzle;

import java.util.HashMap;
import java.util.Map;

import model.colors.ColorPalette;

public class TetrisPieceT implements PiecePuzzle {

	public static final Map<Integer, Map<Integer, Boolean[][]>> ensemblesForme = new HashMap<>();

	static {
		/*
		 * Initializing diffrent shapes for "T"
		 */
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

		Boolean[][] matriceRight5 = new Boolean[height5][width5];
		Boolean[][] matriceUp5 = new Boolean[height5][width5];

		Boolean[][] matriceLeft5 = new Boolean[width5][height5];
		Boolean[][] matriceDown5 = new Boolean[width5][height5];

		for (int row = 0; row < height3; row++) {
			for (int col = 0; col < width3; col++) {
				if (row == height3 - 1 || col == width3 / 2) {
					matriceDown[row][col] = true;
				}

				else {
					matriceDown[row][col] = false;
				}
			}
		}

		for (int row = 0; row < height3; row++) {
			for (int col = 0; col < width3; col++) {
				if (row == 0 || col == width3 / 2) {
					matriceUp[row][col] = true;
				} else {
					matriceUp[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width3; row++) {
			for (int col = 0; col < height3; col++) {
				if (row == width3 / 2 || col == 0) {
					matriceLeft[row][col] = true;
				} else {
					matriceLeft[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width3; row++) {
			for (int col = 0; col < height3; col++) {
				if (row == width3 / 2 || col == height3 - 1) {
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

		ensemblesForme.put(3, forme3x3);

		/*
		 * for width 5 and height 5
		 */
		for (int row = 0; row < height5; row++) {
			for (int col = 0; col < width5; col++) {
				if (row == height5 - 1 || col == width5 / 2) {
					matriceDown5[row][col] = true;
				}

				else {
					matriceDown5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < height5; row++) {
			for (int col = 0; col < width5; col++) {
				if (row == 0 || col == width5 / 2) {
					matriceUp5[row][col] = true;
				} else {
					matriceUp5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width5; row++) {
			for (int col = 0; col < height5; col++) {
				if (row == width5 / 2 || col == 0) {
					matriceLeft5[row][col] = true;
				} else {
					matriceLeft5[row][col] = false;
				}
			}
		}

		for (int row = 0; row < width5; row++) {
			for (int col = 0; col < height5; col++) {
				if (row == width5 / 2 || col == height5 - 1) {
					matriceRight5[row][col] = true;
				} else {
					matriceRight5[row][col] = false;
				}
			}
		}

		forme5x5.put(0, matriceUp5);
		forme5x5.put(3, matriceRight5);
		forme5x5.put(2, matriceDown5);
		forme5x5.put(1, matriceLeft5);

		ensemblesForme.put(5, forme5x5);

	}

	private Boolean[][] shape;
	private int height;
	private int width;
	private int currentOrientation;
	private PointTetris center;
	private String color;

	public TetrisPieceT(PointTetris center, int height, int width, int currentOrientation) {
		this.center = center;
		this.height = height;
		this.width = width;
		this.color = new ColorPalette().randomColor();
		this.currentOrientation = currentOrientation;
		this.shape = ensemblesForme.get(this.width).get(this.currentOrientation);
	}

	/*
	 * right rotation
	 * with current orientation we can switch to the right form to get a right
	 * rotation
	 */
	@Override
	public void rightRotation() {
		if (currentOrientation == 3) {
			this.currentOrientation = 0;
			shape = ensemblesForme.get(this.width).get(currentOrientation);
		} else {
			this.currentOrientation++;
			shape = ensemblesForme.get(this.width).get(currentOrientation);
		}

	}

	/*
	 * left rotation
	 * with current orientation we can switch to the left form to get a left
	 * rotation
	 */
	@Override
	public void leftRotation() {
		if (currentOrientation == 0) {
			this.currentOrientation = 3;
			shape = ensemblesForme.get(this.width).get(currentOrientation);
		} else {
			this.currentOrientation--;
			shape = ensemblesForme.get(this.width).get(currentOrientation);
		}
	}

	@Override
	public int getHauteur() {
		return this.width;
	}

	@Override
	public int getLargeur() {
		return this.height;
	}

	@Override
	public Boolean[][] getShape() {
		return this.shape;
	}

	public PointTetris getCenter() {
		return this.center;
	}

	@Override
	public void updateCenter(PointTetris center) {
		this.center = center;
	}

	@Override
	public String getColor() {
		return this.color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		String str = "";
		for (int row = 0; row < this.shape.length; row++) {
			for (int col = 0; col < this.shape[row].length; col++) {
				if (this.shape[row][col]) {
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