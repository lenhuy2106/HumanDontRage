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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Upper panel of the game view. Allows interaction of the player with the game.
 * Controls the run of the game.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class DiceView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;
    /**
     * shows number of dice
     */
    private final JLabel dice;
    /**
     * shows color of the current player
     */
    private final JPanel player;
    /**
     * button to roll
     */
    private final JButton button;
    /**
     * reference to the game
     */
    private final Game game;
    /**
     * index of the current player
     */
    private int index = 1;

    /**
     * Custom-Constructor. Initializes the panel and its components.
     *
     * @param game Reference to the game.
     */
    public DiceView(final Game game) {
        this.game = game;
        this.game.addObserver(this);

        dice = new JLabel();
        add(dice);

        button = new JButton("Wuerfeln");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                game.roll();
            }

        });
        add(button);

        player = new JPanel();
        player.setBackground(BoardView.colors[index]);
        add(player);
    }

    /**
     * update dice, current player and next move on the user interface.
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        index = game.getIndex();
        player.setBackground(BoardView.colors[index]);
        dice.setText(Integer.toString(game.getDice()));
    }

}
