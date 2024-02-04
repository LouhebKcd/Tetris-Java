package vue;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import controleur.EcouteurModele;
import model.piecePuzzle.*;
import model.plateauPuzzle.OutOfMapException;
import model.plateauPuzzle.PlateauPuzzle;

public class GameWindow extends JFrame implements KeyListener, MouseListener, EcouteurModele {

	private JPanel[][] cellPanels; // Tableau de JPanel pour representer la grille du jeu.
	public PlateauPuzzle model = null;
	private PointTetris selectedPoint = null;
	public PiecePuzzle piece = null;
	public static int compteur = 0;
	private JLabel scoreLabel;
	private JLabel remainingMoves;
	private PiecePuzzle lastPiece = null;
	private JPanel cellContainer;

	//constructeur de la class GameWindow
	public GameWindow(PlateauPuzzle model) {
		this.model = model;
		this.model.ajoutEcouteur(this);
		int rows = model.getHeight();
		int cols = model.getWidth();

		
		JPanel scorePanel = new JPanel();

		scorePanel.setBackground(Color.decode("#171717"));
		scorePanel.setPreferredSize(new Dimension(150, rows));
		this.scoreLabel = new JLabel("<html><body style:'width : 150 px'>Score: keep playing !</body><html>");
		this.scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
		this.scoreLabel.setForeground(Color.WHITE);
		scorePanel.add(this.scoreLabel);

		this.remainingMoves = new JLabel("<html><body style:'width : 150 px'>Reamaining moves : " + model.getMaxMoves() + " </body><html>");
		this.remainingMoves.setHorizontalAlignment(SwingConstants.CENTER);
		this.remainingMoves.setFont(new Font("Arial", Font.BOLD, 16));
		this.remainingMoves.setForeground(Color.WHITE);
		scorePanel.add(this.remainingMoves);

		JButton valider = new JButton("Finish");
		valider.addActionListener(new ActionListener() { // BP: Utilisez une fonction fléchée (fonction anonyme)

			@Override
			public void actionPerformed(ActionEvent e) {
				float score = model.calculateScore();
                JOptionPane.showMessageDialog(null, "Votre score est : " + score, "Score", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			
		});
		valider.setFocusable(false);
		scorePanel.add(valider);

		this.cellContainer = new JPanel();
		this.cellContainer.setLayout(new GridLayout(rows, cols));
		this.cellPanels = new JPanel[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.cellPanels[row][col] = new JPanel();
				this.cellPanels[row][col].setBackground(Color.black);
				this.cellPanels[row][col].setBorder((BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED))));																								
				this.cellPanels[row][col].setPreferredSize(new Dimension(35, 35));
				this.cellContainer.add(this.cellPanels[row][col]);
			}
		}

		add(this.cellContainer, BorderLayout.WEST);
		add(scorePanel, BorderLayout.EAST);
		updateGrid(model.getGrid());
		addKeyListener(this);
		addMouseListener(this);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// BP: Préférez l'utilisation de `javax.swing.WindowConstants.EXIT_ON_CLOSE`
		setResizable(false);
		setTitle("Tetris Game");
		setVisible(true);

	}

	//Met à jour la grille graphique à partir d'une grille de points Tetris( la grille du model)
	public void updateGrid(PointTetris[][] grid) {

		// Parcours de la grille pour mettre à jour les cellules non occupées
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!grid[i][j].isOccupied())
					cellPanels[i][j].setBackground(Color.decode("#171717"));
			}
		}

		// Parcours des pièces du modèle pour mettre à jour les cellules occupées
		for (PiecePuzzle p : model.getShapes()) {
			Boolean[][] test = p.getShape();
			int d = p.getHauteur() / 2;
			int g = p.getLargeur() / 2;
			int count1 = 0;

			// Parcours des cellules occupées de la pièce pour mettre à jour leurs couleurs
			for (int i = p.getCenter().getPositionX() - d; i < p.getCenter().getPositionX() - d + p.getHauteur(); i++) {
				int count2 = 0;
				for (int j = p.getCenter().getPositionY() - g; j < p.getCenter().getPositionY() - g
						+ p.getLargeur(); j++) {
					if (test[count1][count2]) {
						cellPanels[i][j].setBackground(Color.decode(p.getColor()));
					}
					count2++;
				}
				count1++;
			}
		}
	}

	@Override
	public void modeleMisAJour() {
		updateGrid(model.getGrid());
	}

	@Override
	public void mousePressed(MouseEvent e) {

		int mouseX = (int) e.getPoint().getX();
		int mouseY = (int) e.getPoint().getY();
		// Calculez les indices de la cellule correspondante.
		int cellWidth = cellPanels[0][0].getWidth();
		int cellHeight = cellPanels[0][0].getHeight();
		int rowIndex = (mouseY / cellWidth) - 1;
		int colIndex = (mouseX / cellHeight);
		// Vrifiez si la cellule est valide.
		if (rowIndex >= 0 && rowIndex < model.getHeight() && colIndex >= 0 && colIndex < model.getWidth()) {
			// enregistrer le point selectioner
			if (model.getGrid()[rowIndex][colIndex].isOccupied()) {
				// Vérifie si le nombre maximum de mouvements a été atteint
				if (model.getMaxMoves() == 0) {

					// Affiche le score et désactive la grille
					this.scoreLabel.setText("<html><body style:'width : 150 px'>Score: " + model.calculateScore()
							+ "</body><html>");

					float score = model.calculateScore();
					JOptionPane.showMessageDialog(null, "Votre score est : " + score, "Score", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				GameWindow.compteur++;
				selectedPoint = model.getGrid()[rowIndex][colIndex];
				piece = this.model.getForme(this.selectedPoint);
			}
		}
	}

	//Gérer les événements liés aux touches du clavier
	@Override
	public void keyPressed(KeyEvent e) {

		// Vérifie si un point Tetris est sélectionné
		if (this.selectedPoint != null) {

			// Vérifie si une pièce est associée au point sélectionné
			if (piece != null) {

				// Vérifie si la pièce actuelle est différente de la dernière pièce traitée
				if (!piece.equals(lastPiece)) {

					// Décrémente le nombre maximum de mouvements et met à jour l'interface
					model.setMaxMoves();
					this.remainingMoves.setText("<html><body style:'width : 150 px'>Reamaining moves : "
							+ model.getMaxMoves() + " </body><html>");
					lastPiece = piece;
				}

				// Gestion des différentes touches du clavier
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					try {
						model.movePiece(2, piece);
					} catch (OutOfMapException err) {
						System.out.println(err.getMessage());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {

					try {
						model.movePiece(3, piece);

					} catch (OutOfMapException err) {
						System.out.println(err.getMessage());
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					try {
						model.movePiece(4, piece);
					} catch (OutOfMapException err) {
						System.out.println(err.getMessage());
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					try {
						model.movePiece(1, piece);
					} catch (OutOfMapException err) {
						System.out.println(err.getMessage());
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					try {
						model.rotateRight(piece);
					} catch (OutOfMapException err) {
						System.out.println(err.getMessage());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_G) {
					try {
						model.rotateLeft(piece);
					} catch (OutOfMapException err) {
						System.out.println(err.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
