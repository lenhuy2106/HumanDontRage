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
     * Constant for the Board/fieldArray-Size.
     */
    private static final int BOARD_SIZE = 40;
    /**
     * defines number of expected players
     */
    private static int numberOfPlayers;
    /**
     * list of all players
     */
    private final List<Player> players = new ArrayList<>();
    /**
     * list of all simple game fields
     */
    private final List<Field> fields;
    /**
     * Controls the interaction between client and server.
     */
    private Proxy proxy;
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
     * indicates if player is on move (no roll)
     */
    private boolean isOnMove;
    /**
     * Flag for finished or not.
     */
    private boolean finished;
    /**
     * Indicates if game is played on a network.
     */
    private final boolean isNetwork;

    private Thread thread;

    private int myColor;

    /**
     * Initializes a new game and selects the first player.
     *
     * @param board: model of the game.
     */
    public Game(GameBoard board, int number, String ipAddress, int port) throws InterruptedException {
        fields = board.getFields();
        isNetwork = !ipAddress.equals("");
        this.numberOfPlayers = number;

        if (isNetwork) {
            proxy = new Proxy(this, ipAddress, port);
            thread = new Thread(proxy);
	    thread.start();

            System.out.println("Waiting for game to start...");
            while (proxy.isIdle()) {
                Thread.sleep(1000);
            }
            System.out.println("Game starts!");
        }

        // initialize four players
        for (int i = 1; i <= numberOfPlayers; i++) {
            Player player = new Player(i, board, this);
            players.add(player);
        }
        index = 1;
        turn = new Turn(players.get(0));
        isOnMove = false;
        finished = false;
    }

    /**
     * Get current player index on turn.
     *
     * @return current player index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter for flag if finished.
     * @return
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets the finished flag.
     * @param finished Sets the flag if finished - true, or not - false.
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setMyColor(int myColor) {
        this.myColor = myColor;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        Game.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Getter for the list of players.
     * @return current player.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * roll a random number between 1 and 6.
     */
    public void roll() {
        if (index == myColor) {
            dice = (int) ((Math.random()) * 6 + 1);

            if (isNetwork) {
                proxy.sendRoll(dice);
            } else {
                roll(dice);
            }
        }
    }

    /**
     * roll a predefined number. Useful for tests.
     *
     * @param number of dice
     */
    public void roll(int number) {
        dice = number;

        nextTurn();
        refresh();
    }

    /**
     * move pawn situated on the field indicated by index.
     *
     * @param fieldIndex: index of the field where pawn is situated.
     */
    public void move(int fieldIndex) {


        Field field = fields.get(fieldIndex);
        if (field.getPawn() != null && index == field.getPawn().getIndex()) {

            boolean isOwnPawn = field.getPawn() != null ? index == field.getPawn().getIndex() : false;
            boolean isSimpleField = fields.indexOf(field) != -1;
            int nextId = (fields.indexOf(field) + dice) % BOARD_SIZE;
            Field targetField = fields.get(nextId);
            Pawn targetPawn = targetField.getPawn();
            boolean freeTarget = targetPawn != null ?
                    targetPawn.getIndex() != index : true;

            final Player currentPlayer = players.get(field.getPawn().getIndex()-1);
            final int startCap = (field.getPawn().getIndex()-1 == 0) ? BOARD_SIZE : (field.getPawn().getIndex()-1) * 10;
            final int endPos = fields.indexOf(field) - startCap + dice;

            freeTarget &= (endPos < 4 && startCap > fields.indexOf(field) && startCap <= fields.indexOf(field)+dice) ?
                    currentPlayer.freeEnd(endPos) : true;

            if (isSimpleField && isOwnPawn && isOnMove && freeTarget) {

    //            System.err.println(startCap);

                // if index of startfield - currentfield + dice between 0 and 6. it can land on an endfield
                if(startCap > fields.indexOf(field) && startCap <= fields.indexOf(field)+dice) {
    //                System.err.println("test");
                    if(endPos < 4 ){
    //                    System.err.println("cmon just abit");
                        if(currentPlayer.freeEnd(endPos)){
                            currentPlayer.sendToEnd(endPos);
                            field.setPawn(null);
    //                        System.err.println("'Infiltraded");
                        }
                    }
                }
                else {
                    // handling movement on field including sending back enemy pawns
                    if(targetPawn == null) {
                        targetField.setPawn(field.getPawn());
                        field.setPawn(null);
                    } else if(targetPawn.getIndex() != index) {
                        players.get(targetPawn.getIndex()-1).sendBackHome(nextId);
    //                    System.err.println("SEND HOME YO!");
                        targetField.setPawn(field.getPawn());
                        field.setPawn(null);
                    }
                }
                nextTurn();
                setOnMove(false);
            }
            refresh();
        }
    }

    /**
     * Moves pawn situated on the field.
     * Only possible if field is a simple field and game is on move, not roll.
     *
     * @param field: field where pawn is situated.
     */
    public void move(Field field) {
        if (index == myColor) {
            if (isNetwork) {
                proxy.sendMove(fields.indexOf(field));
            } else {
                move(fields.indexOf(field));
            }
        }
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

    /**
     * Sets the isOnMove-Flag.
     * @param onMove
     */
    public void setOnMove(boolean onMove) {
        isOnMove = onMove;
        refresh();
    }

    /**
     * Getter for isOnMove-flag
     * @return
     */
    public boolean isOnMove() {
        return isOnMove;
    }

    /**
     * Initiates the next turn of a player.
     */
    private void nextTurn() {
            if (turn.progress()) {
                    index = (index < numberOfPlayers) ? ++index : 1;
                    turn = new Turn(players.get(index - 1));
            }

        refresh();
    }

    /**
     * ToString for the Actionpart.
     * @return m - move, r - roll action.
     */
    public String moveToString() {
        String result;
        if (finished) {
            result = "f";
        } else result = isOnMove ? "m" : "r";
        return result;
    }

    /**
     * ToString for the fieldsArray.
     * @param list fieldarray
     * @return String
     */
    public String listToString(List<Field> list) {
        StringBuilder result = new StringBuilder();
        result.append("[");

        for (Field field : list) {
            Pawn pawn = field.getPawn();
            result.append(pawn == null ? "0" : pawn.getIndex()).
                    append(", ");
        }
        result.deleteCharAt(result.length()-1);
        result.deleteCharAt(result.length()-1);
        result.append("]");
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder lists = new StringBuilder();
        for (Player player : players) {
            lists.append(player.homeToString());
            lists.append(player.endToString());
        }
        return String.valueOf(index)
                + moveToString()
                + dice
                + lists
                + listToString(fields);
    }
}
