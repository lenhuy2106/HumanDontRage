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


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.hm.cs.se.game.app.Game;
import edu.hm.cs.se.game.app.GameBoard;
/**
 * Test class for the game.
 * @author Ulrike Hammerschall
 *
 */
public class TestClient {

	/** game under test */
	private Game game;
        private int i = 1;

	/** reader for example game */
	private BufferedReader reader;

	@Before
	public void setUp() throws Exception {
		GameBoard board = new GameBoard();
		game = new Game(board, 4, "", 0);
	}

	/**
	 * compare line by line the state of the game with the state of the
	 * example game.
	 */
	@Test
	public void play() {
		try (BufferedReader reader = new BufferedReader(new FileReader("instructions.txt"))) {
			String next = reader.readLine();
			while(next != null) {
				String[] array = next.split(",");
				if(array[0].equals("roll"))
					game.roll(Integer.parseInt(array[1]));
				else if(array[0].equals("move"))
					game.move(Integer.parseInt(array[1]));
				next = reader.readLine();
				Assert.assertEquals("Strings should be equal", compareString(), game.toString());
                                System.out.println(i++ + "  " + game.toString());

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
		try {
			if(reader == null)
				reader = new BufferedReader(new FileReader("example_game.txt"));
			result = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
