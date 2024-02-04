package vue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.gameInitialisationStrategy.DefaultStrategy;
import model.gameInitialisationStrategy.HardStrategy;
import model.gameInitialisationStrategy.InitialConfigurationStrategy;
import model.gameInitialisationStrategy.MediumDifficulty;
import model.plateauPuzzle.PlateauPuzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
    /*
    * Notation de la correction : 
    * 
    * Dans les fichiers sources, vous trouverez des commentaires qui commencent par: 
    *      - BP: Best Practice, ce sont de simples conseils pour optimiser, rendre plus clair, rien de bien méchant
    *      - Warning: Ce sont de petites erreurs qui peuvent provoquer des bugs, ou un passage qui rend le code illisible
    *      - Error: Ce sont de grosses erreurs, c'est un -1000 points sur la note finale
    */
public class StartWindow extends JFrame implements ActionListener {

    // Constructeur de la class
    public StartWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // BP: Préférez l'utilisation de `javax.swing.WindowConstants.EXIT_ON_CLOSE`
        setTitle("Start Window");
        setSize(500, 300);

        // Jlabel qui fait office de background
        JLabel backgroundLabel = new JLabel(
                new ImageIcon(getScaledImage("src/assets/tetris.jpg", getSize().width, getSize().height)));
        setContentPane(backgroundLabel);
        setLayout(new BorderLayout());

        // Utilisez un JPanel pour contenir les boutons
        JPanel buttonPanel = new JPanel();

        JButton easyStrategy = new JButton("Easy Mode");
        JButton mediumStrategy = new JButton("Medium Mode");
        JButton hardStrategy = new JButton("Hard Mode");

        easyStrategy.setFont(new Font("Arial", Font.PLAIN, 12));
        easyStrategy.addActionListener(this);

        mediumStrategy.setFont(new Font("Arial", Font.PLAIN, 12));
        mediumStrategy.addActionListener(this);

        hardStrategy.setFont(new Font("Arial", Font.PLAIN, 12));
        hardStrategy.addActionListener(this);

        buttonPanel.add(easyStrategy);
        buttonPanel.add(mediumStrategy);
        buttonPanel.add(hardStrategy);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));

        // Utilisez un BorderLayout pour positionner le panneau des boutons au sud
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Image getScaledImage(String imagePath, int width, int height) {
        ImageIcon originalImageIcon = new ImageIcon(imagePath);
        Image originalImage = originalImageIcon.getImage();
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    // Méthode appelée lorsque le bouton "Start" est cliqué
    @Override
    public void actionPerformed(ActionEvent e) {
        // Fermez la fenêtre de début
        dispose();

        // Initialisez et affichez la fenêtre principale du jeu avec des paramètres
        // différents
        if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            String buttonText = clickedButton.getText();

            PlateauPuzzle g = new PlateauPuzzle();
            InitialConfigurationStrategy strat = null;

            // En fonction du bouton cliqué, initialisez le plateau avec des paramètres
            // différents
            if (buttonText.equals("Easy Mode")) {
                strat = new DefaultStrategy(g);
            } else if (buttonText.equals("Medium Mode")) {
                strat = new MediumDifficulty(g);
            } else if (buttonText.equals("Hard Mode")) {
                strat = new HardStrategy(g);
            } else {
                // Par défaut, utilisez les paramètres du mode facile
                strat = new DefaultStrategy(g);
            }

            // Ajoutez des pièces au plateau
            g.setStrategy(strat);
            g.initializeGame();

            // Lancez la fenêtre du jeu avec le plateau initialisé
            new GameWindow(g);
        }
    }

    public static void main(String[] args) {
        new StartWindow();
    }
}
