import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;


public class Ball {


    int xPos;
    int yPos;


    int right, left, up, down = 0;


    boolean space = false;


    public Ball(int x,int y) {


        xPos = x;

        yPos = y;

        up = -2;


    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.cyan);

        g2d.fillOval(xPos, yPos, 14, 14);



    }


    public void move() {

        xPos += left + right;
        yPos += up + down;

    }

    public Rectangle getBorder() {

        return new Rectangle(xPos, yPos, 14, 14);

    }


}