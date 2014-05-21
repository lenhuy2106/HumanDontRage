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

    /** reference to player doing step */
    private final Player player;
    private final int rule;

    public Turn(Player player, int rule) {
        this.player = player;
        this.rule = rule;

    }
    
    public void doStep() {
        final int dice = player.getGame().getDice();
        
        switch (rule) {
            case 1:;
            default:;
        }
        
    }
    
}
