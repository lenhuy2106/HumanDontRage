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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import edu.hm.cs.se.game.app.Field;
import edu.hm.cs.se.game.app.Game;
import edu.hm.cs.se.game.app.GameBoard;

/**
 * Lower panel of the user interface. Visualizes game fields, player fields and
 * the current position of the player pawns.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class BoardView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    /**
     * available colors
     */
    public static Color[] colors = new Color[]{
        Color.BLACK,
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW
    };

    /**
     * list of all fields of the game
     */
    private List<FieldView> fields = new ArrayList<>();

    /**
     * reference to the game logic
     */
    private final Game game;

    /**
     * Custom-Constructor. Initializes all visible fields of the game and
     * assigns their internal field representation.
     *
     * @param game Reference to the game
     * @param board Reference to the game board
     */
    public BoardView(Game game, GameBoard board) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setBackground(Color.WHITE);
        this.game = game;
        this.game.addObserver(this);
        Field[][] model = board.getBoard();

        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model.length; j++) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = i;
                gbc.gridy = j;
                if (model[i][j] != null) {
                    Field field = model[i][j];
                    FieldView next = new FieldView(colors[field.getIndex()], field, game);
                    //
                    next.setBackground(colors[field.getIndex()]);
                    //

                    next.update();
                    fields.add(next);
                    add(next, gbc);
                }
            }
        }
    }

    /**
     * update game state on view.
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        for (FieldView field : fields) {
            field.update();
        }
    }

}
