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
     * releant state current inhabited 1: player may roll 2: startfield is
     * occupied, player may roll 3: player may move
     */
    private int state;
    private final int dice;
    private final int initTry;

    public Turn(Player player) {

        this.player = player;
        dice = player.getGame().getDice();
        state = 1;
        initTry = 3;
    }

    public void doStep() {

        switch (state) {
            case 1:
                if ();
        }

    }

}
