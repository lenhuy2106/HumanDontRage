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
package edu.hm.cs.se.game.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Master of the game. Manages game logic and state changes. Represents the
 * interface to the view.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class Game extends Observable {

    /**
     * defines number of expected players
     */
    private static final int NUMBER_OF_PLAYERS = 4;
    /**
     * list of all players
     */
    private final List<Player> players = new ArrayList<>();
    /**
     * list of all simple game fields
     */
    private final List<Field> fields;
    /**
     * inhabits the game logic
     */
    private Turn turn;
    /**
     * current value of the dice
     */
    private int dice;
    /**
     * current player index on turn
     */
    int index;

    /**
     * Initializes a new game and selects the first player.
     *
     * @param board: model of the game.
     */
    public Game(GameBoard board) {
        fields = board.getFields();

        // initialize four players
        for (int i = 1; i <= NUMBER_OF_PLAYERS; i++) {
            Player player = new Player(i, board, this);
            players.add(player);
        }
        index = 1;
        turn = new Turn(players.get(index));
    }

    /**
     * Get current player index on turn.
     * @return current player index
     */
    public int getIndex() {
        return index;
    }

    /**
     * roll a random number between 1 and 6.
     */
    public void roll() {
        dice = (int) ((Math.random()) * 6 + 1);
        roll(dice);

        // addon
        if (turn.progress()) {
            index = (index < 4) ? ++index : 1;
            turn = new Turn(players.get(index - 1));
            System.out.println("NEXT PLAYER: " + index);    // TEST
        }
        refresh();
    }

    /**
     * roll a predefined number. Useful for tests.
     *
     * @param number of dice
     */
    public void roll(int number) {
        dice = number;
    }

    /**
     * move pawn situated on the field indicated by index.
     *
     * @param fieldIndex: index of the field where pawn is situated.
     */
    public void move(int fieldIndex) {
        Field field = fields.get(fieldIndex);
        move(field);
    }

    /**
     * move pawn situated on the field.
     *
     * @param field: field where pawn is situated.
     */
    public void move(Field field) {
        if (field.getIndex() == 0) {
            int nextId = fields.indexOf(field) + dice;
            fields.get(nextId).setPawn(field.getPawn());
            field.setPawn(null);
        }
        refresh();
        // TEST
        System.out.println(fields.indexOf(field));
    }

    /**
     * return current value of the dice.
     *
     * @return value of the dice.
     */
    public int getDice() {
        return dice;
    }

    /**
     * notify View about changes in the model.
     */
    public void refresh() {
        setChanged();
        notifyObservers();
    }
}
