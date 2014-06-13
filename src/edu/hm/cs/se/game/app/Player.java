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
     *
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
    public boolean start() {

        boolean result = true;
        Pawn startPawn = startField.getPawn();

        if (startPawn == null) {
            for (Field field : homeFields) {
                if (field.getPawn() != null) {
                    startField.setPawn(field.getPawn());
                    field.setPawn(null);
                    break;
                }
            }
            // if startField is occupied by other player's pawn
        } else if (startPawn.getIndex() != index) {
            game.getPlayers().get(startPawn.getIndex()-1).sendBackHome(fields.indexOf(startField));
            for (Field field : homeFields) {
                if (field.getPawn() != null) {
                    startField.setPawn(field.getPawn());
                    field.setPawn(null);
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Number of pawns moving on simple fields.
     *
     * @return number of pawns moving.
     */
    public int pawnsOnMove() {
        int pawnsOnMove = 4;

        pawnsOnMove -= (pawnsOnHome() + pawnsOnEnd());
        return pawnsOnMove;
    }

    /**
     * Checks if any pawn of the current player can move.
     * @return true - if there is one moveable pawn, else false.
     */
    public boolean pawnsCanMove() {
        boolean canMove = false;

        for (Field field : fields) {
            if (field.getPawn() != null) {
                if (field.getPawn().getIndex() == index) {
                    if(targetExceedsFields(field)){
                        if(targetIsEndField(field)) {
                            final int startCap = (field.getPawn().getIndex() - 1 == 0) ? fields.size() : (field.getPawn().getIndex() - 1) * 10;
                            final int endPos = fields.indexOf(field) - startCap + getDice();
                            if (freeEnd(endPos)) {
                                canMove = true;
                                break;
                            }
                        }
                    } else if (fields.get((fields.indexOf(field) + getDice()) % fields.size()).getPawn() == null) {
                        canMove = true;
                        break;
                    } else if (fields.get((fields.indexOf(field) + getDice()) % fields.size()).getPawn().getIndex() != index){
                        canMove = true;
                        break;
                    }
                }
            }
        }
        return canMove;
    }

    /**
     * Checks how many pawns are on the homefields.
     * @return numeral value of pawns on homefields.
     */
    public int pawnsOnHome() {
        int pawnsOnHome = 0;
        for (Field field : homeFields) {
            if (field.getPawn() != null) {
                pawnsOnHome++;
            }
        }
        return pawnsOnHome;
    }
    /**
     * Checks how many pawns are on the endfields.
     * @return numeral value of pawns on endfields.
     */
    public int pawnsOnEnd() {
        int pawnsOnEnd = 0;
        for (Field field : endFields) {
            if (field.getPawn() != null) {
                pawnsOnEnd++;
            }
        }
        return pawnsOnEnd;
    }

    /**
     * Checks if the pawn on the startfield can move and automatically moves him/it.
     * @param autoMove defines if method does the automove or not.
     * @return true if he can move, else false.
     */
    public boolean startPawnMayMove(final boolean autoMove) {
        boolean result = canMoveStart();
        if (result && autoMove) {
            game.setOnMove(true);
            game.move(startField);
        }
        return result;
    }

    /**
     * Checks if the pawn on the startfield is able to move.
     * @return true - moveable, else false.
     */
    public boolean canMoveStart(){
        int startId = (index - 1) * 10;
        Field targetField = fields.get(startId + getDice());
        Pawn targetPawn = targetField.getPawn();
        boolean result = (targetPawn == null) ? true : targetPawn.getIndex() != index;
        return result;
    }

    /**
     * Notifies the game, that current player is on move now.
     */
    public void onMove() {
        game.setOnMove(true);
    }

    /**
     * Sends pawn on the given field back home.
     * Removes the pawn from the fieldsarray and puts a new one back to the homearray.
     * @param fieldID targetfield with the pawn on it.
     */
    public void sendBackHome(int fieldID){
	for(Field field : homeFields) {
            if(field.getPawn() == null){
                Pawn pawn = new Pawn(index);
                field.setPawn(pawn);
//                System.err.println("Pawn send Home");
                fields.get(fieldID).setPawn(null);
                break;
            }
        }
    }

    /**
     * Checks if the targetField is an endField of the current player.
     * 
     * @param field field of the pawn, which is tested.
     * @return true - if targetField is an endField of the currentPlayer, else false.
     */
    public boolean targetIsEndField(Field field) {
        boolean isEnd = false;
        final int startCap = (field.getPawn().getIndex() - 1 == 0) ? fields.size() : (field.getPawn().getIndex() - 1) * 10;
        final int endPos = fields.indexOf(field) - startCap + getDice();
        if (startCap > fields.indexOf(field) && startCap <= fields.indexOf(field) + getDice()) {
            if (endPos < 4) {
                isEnd = true;
            }
        }
        return isEnd;
    }
    
    /**
     * Checks if the targetField exceeds the range of the normal field.
     * Includes endFields and shooting over them.
     * @param field field of the pawn, which is tested.
     * @return true - if targetField exceeds the normal fieldarray, else false.
     */
    public boolean targetExceedsFields(Field field) {
        boolean exceeded = false;
        final int startCap = (field.getPawn().getIndex() - 1 == 0) ? fields.size() : (field.getPawn().getIndex() - 1) * 10;
        final int endPos = fields.indexOf(field) - startCap + getDice();
        if (startCap > fields.indexOf(field) && startCap <= fields.indexOf(field) + getDice()) {
            if (endPos < 6) {
                exceeded = true;
            }
        }
        return exceeded;        
    }

    /**
     * Checks if the endField at index is free.
     * @param index index of the endfield which is tested.
     * @return true - for free endField at this index, else false.
     */
    public boolean freeEnd(int index) {
        return endFields.get(index).getPawn() == null;
    }

    public boolean freeStart() {
        return startField.getPawn() == null;
    }

    public boolean ownPawnOnStart() {
        boolean result = startField.getPawn() != null ? startField.getPawn().getIndex() == index : false;
        return result;
    }

    public void sendToEnd(int endID) {
        endFields.get(endID).setPawn(new Pawn(index));
    }

    public int getID() {
        return index;
    }

    public String homeToString() {
        return game.listToString(homeFields);
    }

    public String endToString() {
        return game.listToString(endFields);
    }
}
