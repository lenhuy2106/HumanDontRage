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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import edu.hm.cs.se.game.app.Field;
import edu.hm.cs.se.game.app.Game;

/**
 * Class for all fields that are visible on the user interface. A visible field
 * visualizes the state of a game field. The game field represents the actual
 * state (Player field or game field? Pawn or no pawn on the field?). During
 * update game field and visible field are synchronized.
 *
 * @author U. Hammerschall
 * @version 1.0
 */
public class FieldView extends JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;
    /**
     * original color of the field boarder. Should not change
     */
    private final Color original;
    /**
     * current color of the field. Pawn color or black.
     */
    private Color color;
    /**
     * pawn on field?
     */
    private boolean fill = false;
    /**
     * assigned game field
     */
    private Field field;
    /**
     * game reference to send mouse events to
     */
    private Game game;

    /**
     * Custom-Constructor. Initializes a visible representation of a game field.
     * Does not accept mouse events.
     *
     * @param color: initial and original color of the field.
     * @param field: assigned game field with state information.
     * @param game: game to send events to.
     */
    public FieldView(Color color, Field field, Game game) {
        this.color = color;
        this.original = color;
        this.game = game;
        this.field = field;
        setPreferredSize(new Dimension(40, 40));
        setBackground(Color.WHITE);
        addMouseListener(this);             // essential line
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        if (fill) {
            g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        } else {
            g2D.setStroke(new BasicStroke(2));
            g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    /**
     * synchronize state of the visible field with state of the assigned field.
     */
    public void update() {
        if (field.getPawn() != null) {
            color = BoardView.colors[field.getPawn().getIndex()];
            fill = true;
        } else {
            color = original;
            fill = false;
        }
        repaint();	// repaint panel.
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        game.move(field);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

}
