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
 * Class for all types of fields: game fields and player fields. A field may
 * contain a pawn. A field can be assigned to a special player.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class Field {

    /**
     * index of the field.
     */
    private final int index;
    /**
     * indicates if a pawn is on the field.
     */
    private Pawn pawn;

    /**
     * Custom-Constructor.
     *
     * @param index of the field.
     */
    public Field(int index) {
        this.index = index;
    }

    /**
     * return index of the field
     *
     * @return 0 if field is a game field, return index of the player if field
     * is a player field.
     */
    public int getIndex() {
        return index;
    }

    /**
     * return pawn on the field.
     *
     * @return pawn if available otherwise null.
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * set pawn on the field
     *
     * @param pawn to set on the field.
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
}
