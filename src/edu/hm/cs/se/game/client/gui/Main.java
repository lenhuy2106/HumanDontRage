package edu.hm.cs.se.game.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.hm.cs.se.game.app.Game;
import edu.hm.cs.se.game.app.GameBoard;

/**
 * Main class of the game. Initializes the main components: GameBoard (model),
 * Game (controller) and Views.
 *
 * @author U.Hammerschall
 * @version 1.0
 */
public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 550);
        contentPane = new JPanel();
        setContentPane(contentPane);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    GameBoard board = new GameBoard();
                    Game game = new Game(board);
                    frame.add(new DiceView(game), BorderLayout.NORTH);
                    frame.add(new BoardView(game, board), BorderLayout.CENTER);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
