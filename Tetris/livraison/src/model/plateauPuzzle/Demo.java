package model.plateauPuzzle;

import java.util.Scanner;

import model.gameInitialisationStrategy.DefaultStrategy;

/*
 * demo sur le terminale
 */
public class Demo {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			PlateauPuzzle pz = new PlateauPuzzle();
			pz.setStrategy(new DefaultStrategy(pz));
			pz.initializeGame();
			while (pz.getMaxMoves() > 0) {
				System.out.println(pz);
				System.out.println("Moves Left : " + pz.getMaxMoves());
				System.out.println("Veuillez choisir une piece : ");
				int piece = sc.nextInt();
				while (true) {
					System.out.println(
							"veuillez choisir une action: 1:down , 2:right, 3:left\n4:up, 5:rotate right, 6:rotate left");
					int action = sc.nextInt();
					if (action == 5) {
						try {
							pz.rotateRight(pz.getShapes().get(piece));
							;
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else if (action == 6) {
						try {
							pz.rotateLeft(pz.getShapes().get(piece));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
					try {
						pz.movePiece(action, pz.getShapes().get(piece));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					System.out.println(pz);
					System.out.println("validate new location : y/n?");
					String res = sc.next();
					if (res.equals("y")) {
						break;
					}
				}
				pz.setMaxMoves();
			}
			System.out.println(pz.calculateScore());
		}
	}
}
