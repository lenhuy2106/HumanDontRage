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
    private final Game game;
    /**
     * relevant state current inhabited 2: startfield is occupied, player may roll 3: player may move
     */
    private int state;
    private final int dice;
    private int attempts;

    public Turn(Game game) {

        this.game = game;
        dice = game.getDice();
        state = 1;
        attempts = 3;
    }

    public void doStep() {

        switch (state) {
            // 1: player may roll
            case 1:
                if (dice < 6) {

                    attempts--;
                    };
        }

    }

}
