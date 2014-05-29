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

        System.out.println("============");
        System.out.println("state:" + state);
        System.out.println("attemptsLeft: " + attemptsLeft);
        System.out.println("pawnsOnMove(): " + player.pawnsOnMove());

        switch (state) {

            case 1:
                if (dice < 6) {
                    if (attemptsLeft > 1 && player.pawnsOnMove() == 0) {
                        System.out.println("row 1");
                        attemptsLeft--;                     // rule 1 - DONE

                    } else if (attemptsLeft == 1 && player.pawnsOnMove() == 0) {
                        System.out.println("row 2");
                                                            // rule 2 - DONE
                        nextPlayer = true;
                    } else if (player.pawnsOnMove() != 0) {
                        player.waitForMove();
                        state = 3;
                        System.out.println("row 7");        // rule 7 - DONE
                    }
                } else if (dice == 6 && player.pawnsOnHome() != 0) {
                    if  (attemptsLeft >= 1) {
                        System.out.println("row 3");
                        state = 2;                          // rule 3 - DONE
                        player.start();
                        attemptsLeft++;
                    } else if (player.pawnsOnHome() == 0 && player.pawnsOnMove() != 0) {
                        System.out.println("row 8");
                        player.waitForMove();
                        state = 3;                          // rule 8 - DONE
                    }
                }
                break;
            case 2:
                if (dice < 6 && player.startPawnMayMove(true)) {
                        nextPlayer = true;
                        System.out.println("row 4,6");
                                                            // rule 4,6 - DONE
                        state = 1;

                } else if (dice == 6 && player.startPawnMayMove(true)) {
                        System.out.println("row 5");
                        state = 1;                          // rule 5 - DONE
                }
                break;
            case 3:
                if (player.pawnsOnEnd() == 4) {
                    System.out.println("row 10");
                                                            // rule 10
                    System.out.println("DA WIN");           // TODO: end message
                    windowDude();
                    System.exit(0);
                } else {
                    System.out.println("row 9");
                    nextPlayer = true;                      // rule 9 - DONE
                }
                break;
            default:
                break;
        }
        return nextPlayer;
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
