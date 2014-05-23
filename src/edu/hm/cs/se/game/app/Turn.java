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
     */
    private int state;
    private final int dice;
    private int attemptsLeft;

    public Turn(Player player) {

        this.player = player;
        dice = player.getGame().getDice();
        state = 1;
        attemptsLeft = 3;
    }

    public boolean progress() {

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
                                                    // TODO: pawnAtHome &&
                                                    //       startFieldFree
                    } else if (false) {
                        ;                           // row 8
                                                    // TODO: noPawnAtHome &&
                                                    //       pawnMayMove
                    }
                }
            case 2:
                if (dice < 6) {
                    if (false) {                    // row 4
                                                    // TODO: startPawnMayMove
                        ;
                    } else if (dice == 6) {
                        ;                           // row 5
                    } else if (dice <= 6) {
                        ;                           // row 6
                    }
                }
            case 3:
                if (false) {
                    ;                               // row 9
                } else if (false) {
                    ;                               // row 10
                }
            default:
                return nextPlayer;
        }

    }

}
