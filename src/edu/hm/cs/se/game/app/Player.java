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

/**
 * Class that represents a player of the game. Each player has four pawns,
 * a list of home fields, a list of target fields and a reference to
 * the general game fields. A player is responsible for all checks and
 * moves of his or her pawns.
 * @author U. Hammerschall
 * @version 1.0
 */
public class Player {

    /** list of home fields of the player */
    private List<Field> homeFields = new ArrayList<>();
    /** list of target fields of the player */
    private List<Field> endFields = new ArrayList<>();
    /** list of game fields */
    private List<Field> fields;
    /** index of the player */
    private final int index;
    /** reference to the game */
    private final Game game;
    /** start field */
    private final Field startField;

    public int getDice() {
        return game.getDice();
    }

    /**
     * initializes a new player.
     * @param index: index that represents color of the player (0 < index <= 4).
     * @param fields: player independent game fields
     * @param game: reference to the game
     */
    public Player(int index, GameBoard board, Game game) {

	this.index = index;
	this.game = game;
	this.fields = board.getFields();
	this.homeFields = board.getHomes(index-1);
	this.endFields = board.getEnds(index-1);
        this.startField = fields.get((index - 1) * 10);

	for(Field field : homeFields) {
		Pawn pawn = new Pawn(index);
		field.setPawn(pawn);
	}
    }

    /**
     * Moves a pawn from player homefield to player startfield.
     */
    public void start() {
        if (startField.getPawn() == null) {
            for (Field field : homeFields) {
                if (field.getPawn() != null) {
                    startField.setPawn(field.getPawn());
                    field.setPawn(null);
                    break;
                }
            }
        }
    }

    /**
     * Number of pawns moving on simple fields.
     * @return number of pawns moving.
     */
    public int pawnsOnMove() {
        int pawnsOnMove = 4;

        pawnsOnMove -= (pawnsOnHome() + pawnsOnEnd());
        return pawnsOnMove;
    }

    public int pawnsOnHome() {
        int pawnsOnHome = 0;
        for (Field field : homeFields) {
            if (field.getPawn() != null) {
                pawnsOnHome++;
            }
        }
        return pawnsOnHome;
    }

    public int pawnsOnEnd() {
        int pawnsOnEnd = 0;
        for (Field field : endFields) {
            if (field.getPawn() != null) {
                pawnsOnEnd++;
            }
        }
        return pawnsOnEnd;
    }

    public boolean startPawnMayMove(boolean autoMove) {
        int startId = (index - 1) * 10;
        Field targetField = fields.get(startId + getDice());
        Pawn targetPawn = targetField.getPawn();
        boolean result = (targetPawn == null) ? true : targetPawn.getIndex() != index;
        if (result && autoMove) {
            game.setOnMove(true);
            game.move(startField);
        }
        return result;
    }

    public void waitForMove() {
        game.setOnMove(true);
    }
    
    public void sendBackHome(int fieldID){
	for(Field field : homeFields) {
            if(field.getPawn() == null){
		Pawn pawn = new Pawn(index);
		field.setPawn(pawn);
                System.err.println("Pawn send Home");
                fields.get(fieldID).setPawn(null);
                break;
            }
	}        
    }
}
