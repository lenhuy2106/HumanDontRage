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
import java.awt.Point;
import java.awt.Polygon;

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
        int offset = 5;
        Point p1 = new Point (0 + offset, getHeight()-offset);
        Point p2 = new Point(getWidth() / 2+offset, getHeight()-offset);
        Point p3 = new Point(getWidth() / 2+offset, offset);
        int[] x = { p1.x, p2.x, p3.x };
        int[] y = { p1.y, p2.y, p3.y };
        if (fill) {
            g.fillPolygon(x, y, x.length);
        } else {
            g2D.setStroke(new BasicStroke(2));
            g.drawPolygon(x, y, x.length);
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
