/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.
 */

package edu.hm.cs.se.game.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author T500
 */
class Proxy implements Runnable {
    private final Game game;
    private Socket server;
    private boolean idle;
    private PrintWriter pw;
    private BufferedReader br;

    public Proxy(Game game, String ipAddress, int port) {
        this.game = game;

        try {
            idle = true;
            server = new Socket(ipAddress, port);
            pw = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("no server running");
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                String command = br.readLine();
                System.out.println(command);

                if(command != null) {
                    String[] array = command.split(",");

                    if(array[0].equals("roll")) {
                        game.roll(Integer.parseInt(array[1]));
                    }
                    else if(array[0].equals("move")) {
                        game.move(Integer.parseInt(array[1]));
                    }
                    else if(array[0].equals("color")) {
                        game.setMyColor(Integer.parseInt(array[1]));
                    }
                    else if(array[0].equals("start")) {
                        game.setNumberOfPlayers(Integer.parseInt(array[1]));
                        idle = false;
                    }
                    else if(array[0].equals("exit")) {
                        System.out.println("A client has disconnected. Game will close in 10 seconds.");
                        for(int i = 10; i >= 0; i--) {
                            Thread.sleep(1000);
                            System.out.println(i);
                        }
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
            }
        }
    }

    public boolean isIdle() {
        return idle;
    }

    public void sendMove(int fieldIndex) {
        pw.println("move," + fieldIndex);
        pw.flush();
    }

    public void sendRoll(int dice) {
        pw.println("roll," + dice);
        pw.flush();
    }
}
