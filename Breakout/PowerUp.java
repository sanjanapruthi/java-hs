import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;


public class PowerUp {


    int xPos;
    int yPos;
    Color color;
    int special;
   // green, pink, red


    int right, left, up, down = 0;


    boolean space = false;

 //1=extra life, 2=more balls, 3=laser


    public PowerUp(int x,int y, Color c) {


        xPos = x;

        yPos = y;

        color = c;
        if (color==Color.green)
        	special = 1;
        if (color==Color.pink)
        	special = 2;
        if (color==Color.red)
        	special = 3;

        down = 1;


    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);

        g2d.fillOval(xPos, yPos, 7, 7);



    }


    public void move() {

        xPos += left + right;
        yPos += up + down;

    }

    public Rectangle getBorder() {

        return new Rectangle(xPos, yPos, 7, 7);

    }


}