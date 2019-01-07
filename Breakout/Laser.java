import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;


public class Laser {


    int xPos;
    int yPos;


    int right, left, up, down = 0;



    public Laser(int x,int y) {


        xPos = x;

        yPos = y;

        up = -1;


    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.red);

        g2d.fillRect(xPos, yPos, 2, 10);



    }


    public void move() {

        xPos += left + right;
        yPos += up + down;


    }

    public Rectangle getBorder() {

        return new Rectangle(xPos, yPos, 2, 10);

    }


}