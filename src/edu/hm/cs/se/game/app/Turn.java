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

        System.out.println("============");
        System.out.println("state:" + state);               // TEST
        System.out.println("attemptsLeft: " + attemptsLeft);
        System.out.println("pawnsOnMove(): " + player.pawnsOnMove());

        switch (state) {

            case 1:
                if (dice < 6) {
                    if (attemptsLeft > 1 && player.pawnsOnMove() == 0) {
                        System.out.println("row 1");        // TEST
                        attemptsLeft--;                     // row 1 - DONE

                    } else if (attemptsLeft == 1 && player.pawnsOnMove() == 0) {
                        System.out.println("row 2");        // TEST
                                                            // row 2 - DONE
                        nextPlayer = true;
                    } else if (player.pawnsOnMove() != 0) {
                        player.waitForMove();
                        state = 3;
                        System.out.println("row 7");        // row 7
                                                            // TODO: pawnMayMove &&
                                                            //       startFieldFree
                    }
                } else if (dice == 6 && player.pawnsOnHome() != 0) {
                    if  (attemptsLeft >= 1) {
                        System.out.println("row 3");        // TEST
                        state = 2;                          // row 3 - DONE
                        player.start();
                        attemptsLeft++;
                    } else if (false) {
                        System.out.println("row 8");        // TEST
                        ;                                   // row 8
                                                            // TODO: noPawnAtHome &&
                                                            //       pawnMayMove
                    }
                }
                break;
            case 2:
                if (dice < 6 && player.startPawnMayMove(true)) {
                        nextPlayer = true;
                        System.out.println("row 4,6");        // TEST
                                                            // row 4,6 - DONE
                        state = 1;

                } else if (dice == 6 && player.startPawnMayMove(true)) {
                        System.out.println("row 5");        // TEST
                        state = 1;                          // row 5 - DONE
                }
                break;
            case 3:
                if (player.allPawnsEnd()) {
                    System.out.println("row 10");           // TEST
                                                            // row 9
                    System.out.println("DA WIN");
                    System.exit(0);
                } else {
                    System.out.println("row 9");            // TEST
                    nextPlayer = true;                      // row 10
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
