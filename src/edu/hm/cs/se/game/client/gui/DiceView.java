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
package edu.hm.cs.se.game.client.gui;

import edu.hm.cs.se.game.app.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * Upper panel of the game view. Allows interaction of the player with the game.
 * Controls the run of the game.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class DiceView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;
    /**
     * shows number of dice
     */
    private final JLabel dice;
    /**
     * shows color of the current player
     */
    private final JPanel player;
    /**
     * button to roll
     */
    private final JButton button;
    /**
     * reference to the game
     */
    private final Game game;
    /**
     * index of the current player
     */
    private int index = 1;

    	/** reader for example game */
	private BufferedReader reader;

    /**
     * Custom-Constructor. Initializes the panel and its components.
     *
     * @param game Reference to the game.
     */
    public DiceView(final Game game) {
        this.game = game;
        this.game.addObserver(this);

        dice = new JLabel();
        add(dice);

        button = new JButton("ROLL");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                game.roll();
            }
        });
        add(button);

        player = new JPanel();
        player.setBackground(BoardView.colors[index]);
        add(player);

        // SIMULATION BUTTON
        JButton runRecord = new JButton("Run Record");
        runRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        try {
//                            play();
                            BufferedReader reader = new BufferedReader(new FileReader("instructions.txt"));
                            String next = reader.readLine();
                            while(next != null) {
                                String[] array = next.split(",");
                                if(array[0].equals("roll"))
                                    game.roll(Integer.parseInt(array[1]));
                                else if(array[0].equals("move"))
                                    game.move(Integer.parseInt(array[1]));
                                publish();
                                Thread.sleep(10);
                                next = reader.readLine();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void process(List<Void> list) {
                        game.refresh();
                    }
                };
                worker.execute();
            }
        });
        add(runRecord);
    }

    	/**
	 * compare line by line the state of the game with the state of the
	 * example game.
	 */
	public void play() throws InterruptedException {
		try (BufferedReader reader = new BufferedReader(new FileReader("instructions.txt"))) {
			String next = reader.readLine();
			while(next != null) {
				String[] array = next.split(",");
				if(array[0].equals("roll"))
					game.roll(Integer.parseInt(array[1]));
				else if(array[0].equals("move"))
					game.move(Integer.parseInt(array[1]));
				next = reader.readLine();

				if (!compareString().equals(game.toString())) {
                                    System.out.println("ERROR ========= ");
                                    System.out.println("ACTUAL \n" + game.toString());
//                                    Thread.sleep(999999999);
                                }
                                System.out.println(game.toString());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read next line of string representation of the example game state model.
	 * @return next state of the game
	 */
	public String compareString() {
		String result = null;
		try{
			if(reader == null)
				reader = new BufferedReader(new FileReader("example_game.txt"));
			result = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

    /**
     * update dice, current player and next move on the user interface.
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        button.setEnabled(!game.isOnMove());
        if (game.isOnMove()) {
            button.setText("MOVE");
        } else {
            button.setText("ROLL");
        }
        index = game.getIndex();
        player.setBackground(BoardView.colors[index]);
        dice.setText(Integer.toString(game.getDice()));
    }
}
