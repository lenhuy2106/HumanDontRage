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
     * relevant state current inhabited 2: startfield is occupied, player may roll 3: player may move
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

    public void progress() {

        switch (state) {
            // 1: player may roll
            case 1:
                if (dice < 6) {
                    // TODO: noPawnMoving
                    if (attemptsLeft > 1 /* && noPawnsMoving */) {
                        attemptsLeft--;
                    } else if (attemptsLeft == 0 /* && noPawnsMoving */) {
//                        player.getGame().nextPlayer();
                    }
                }
                ;
        }

    }

}
