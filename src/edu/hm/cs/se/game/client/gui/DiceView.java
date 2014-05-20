package edu.hm.cs.se.game.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.hm.cs.se.game.app.Game;

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

    //ADSFASDF
    //soosos und nicht so und nochwas 
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
        int next = (index < 4) ? ++index : 1;
        index = next;
        player.setBackground(BoardView.colors[next]);
        dice.setText(Integer.toString(game.getDice()));
    }

}
