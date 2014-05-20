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

/**
 * Class for player pawns. Each player has four pawns.
 * @author U. Hammerschall
 * @version 1.0
 */
public class Pawn {

    /**
     * index of the player.
     */
    private final int index;

    /**
     * Custom-Constructor.
     * initializes new pawn with number of the assigned player.
     * @param index: number of the player (0 < index <= 4).
     */
    public Pawn(int index) {
	this.index = index;
    }

    /**
     * return index of the player.
     * @return index of the player.
     */
    public int getIndex() {
	return index;
    }

}
