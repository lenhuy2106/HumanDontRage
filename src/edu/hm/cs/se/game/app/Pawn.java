/*
 *University of Applied Science Munich 2014
 *Faculty:    Computer Science FK07
 *Name:       Mathias Long Yan && Huy Nhu Le
 *Date:       2014-05-20
 *Subject:    SE 2
 *Lecturer:   Prof. Dr. U. Hammerschall
 *Project:    Human dont Rage
 */
package edu.hm.cs.se.game.app;

/**
 * Class for player pawns. Each player has four pawns.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class Pawn {

    /**
     * index of the player.
     */
    private final int index;

    /**
     * Custom-Constructor. initializes new pawn with number of the assigned
     * player.
     *
     * @param index: number of the player (0 < index <= 4).
     */
    public Pawn(int index) {
        this.index = index;
    }

    /**
     * return index of the player.
     *
     * @return index of the player.
     */
    public int getIndex() {
        return index;
    }

}
