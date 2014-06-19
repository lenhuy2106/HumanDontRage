/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.

SE2: Praktikum
Excercise 2 - HumanDontRage

 - 20/5/2014
 */
package edu.hm.cs.se.game.client.gui;

import edu.hm.cs.se.game.app.Game;
import edu.hm.cs.se.game.app.GameBoard;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
     * @param args
     */
    public static void main(String[] args) {
        String ipAdressArg = "";
        int portArg = 0;
        int numberOfPlayersArg = 0;

        boolean isNetwork = args.length == 2;

        if (isNetwork) {
            ipAdressArg = args[0];
            portArg = Integer.parseInt(args[1]);
        // isLocal
        } else if (args.length == 1) {
            try {
                int input = Integer.parseInt(args[0]);
                if (0 < input && input <= 4) {
                    numberOfPlayersArg = input;
                } else {
                    // error message: wrong number
                }
            } catch (NumberFormatException e) {
                // error message: no number
            }
        } else if (args.length == 0) {
            numberOfPlayersArg = 4;
        } else {
            // error message: unknown
            System.exit(0);
        }

        final String ipAdress = ipAdressArg;
        final int port = portArg;
        final int numberOfPlayers = numberOfPlayersArg;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    GameBoard board = new GameBoard();
                    Game game = new Game(board, numberOfPlayers, ipAdress, port);
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
