/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.
 */
package edu.hm.cs.se.game.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
     * rules are the same as the ones in the ruleset table
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
                    if (attemptsLeft > 1 && player.pawnsOnMove() == 0) {
                        attemptsLeft--;                     // rule 1

                    } else if (attemptsLeft == 1 && player.pawnsOnMove() == 0) {
                        nextPlayer = true;                  // rule 2

                    } else if (player.pawnsOnMove() != 0) { // rule 7
                        player.waitForMove();
                        state = 3;
                    }
                } else if (dice == 6 && player.pawnsOnHome() != 0) {
                    if  (attemptsLeft >= 1) {               // rule 3
                        state = 2;
                        player.start();
                        attemptsLeft++;
                    }
                } else if (player.pawnsOnHome() == 0 && player.pawnsOnMove() != 0) {
                        player.waitForMove();               // rule 8
                        state = 3;
                    }
                break;
            case 2:
                if (dice < 6 && player.canMoveStart()) {
                        player.startPawnMayMove(true);   
                        nextPlayer = true;                  // rule 4,6
                        state = 1;

                } else if (dice == 6 && player.canMoveStart()) {
                        player.startPawnMayMove(true);    
                        state = 1;                          // rule 5
                }
                break;
            case 3:
                if (player.pawnsOnEnd() == 4) {             // rule 10
                    windowDude();
                    System.exit(0);

                } else {                                    // rule 9
                    nextPlayer = true;
                }
                break;
            default:
                break;
        }
        return nextPlayer;
    }

    public boolean canMoveStart(){
        return player.canMoveStart();
    }
    
    private void windowDude(){
        //1. Create the frame.
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        //Winner-Announcement
        JLabel label = new JLabel("PLAYER " + player.getID());
        label.setHorizontalAlignment(JLabel.CENTER);

        //Buttons
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Adding to JFrame
        frame.add(label,BorderLayout.CENTER);
        frame.add(exit,BorderLayout.SOUTH);

        // JFrame properties
        frame.setSize(300, 150);
        frame.setBackground(Color.BLACK);
        frame.setTitle("AND THE WINNER IS");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
