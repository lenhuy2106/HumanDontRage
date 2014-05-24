/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.
 */
package edu.hm.cs.se.game.app;

/**
 *
 * @author Nhu Huy Le - nle@hm.edu
 */
public class Turn {

    /**
     * reference to player doing step
     */
    private final Player player;
    /**
     * relevant state current inhabited  1: player may roll
                                         2: startfield is occupied, player may roll
                                         3: player may move
                                         4: end
     */
    private int state;
    private int attemptsLeft;

    public Turn(Player player) {

        this.player = player;
        state = 1;
        attemptsLeft = 3;
    }
    /**
     * Action progressing per turn of a player.
     *
     * @return true, if player is finished and player is changing.
     */
    public boolean progress() {

        int dice = player.getDice();
        boolean nextPlayer = false;
        switch (state) {

            case 1:
                if (dice < 6) {
                    if (attemptsLeft > 1) {
                                                    // row 1
                           attemptsLeft--;          // TODO: noPawnMoving
                    } else if (attemptsLeft == 1) {
                                                    // row 2
                           nextPlayer = true;       // TODO: noPawnMoving
                    } else if (false) {
                        ;                           // row 7
                                                    // TODO: pawnMayMove &&
                                                    //       startFieldFree
                    }
                } else if (dice == 6) {
                    if  (attemptsLeft >= 1) {
                        state = 2;                  // row 3
                        player.start();             // TODO: pawnAtHome &&
                                                    //       startFieldFree
                    } else if (false) {
                        ;                           // row 8
                                                    // TODO: noPawnAtHome &&
                                                    //       pawnMayMove
                    }
                }
                break;
            case 2:
                if (dice < 6) {
                    if (false) {                    // row 4
                                                    // TODO: startPawnMayMove
                        state = 1;
                    } else if (dice == 6) {
                        state = 1;                  // row 5
                    } else if (dice <= 6) {
                        state = 1;
                        ;                           // row 6
                    }
                }
                break;
            case 3:
                if (false) {
                    ;                               // row 9
                } else if (false) {
                    ;                               // row 10
                }
                break;
            case 4:
                break;
            default:
                break;
        }
        return nextPlayer;
    }
}
