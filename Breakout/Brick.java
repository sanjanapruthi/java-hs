import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;


public class Brick {


    int xPos;
    int yPos;
    Color color;
    Boolean visible;


    int right, left, up, down = 0;

    boolean space = false;

    int powerUp=0; //1=extra life, 2=more balls, 3=laser


    public Brick(int x, int y, Color color) {


        xPos = x;

        yPos = y;

		this.color = color;

		visible = true;

		powerUp=(int)(Math.random()*10)+1;


    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);

        g2d.fillRoundRect(xPos, yPos, 60, 30, 20, 20);

    }

    public Rectangle getBorder() {
        return new Rectangle(xPos, yPos, 60, 30);
    }


}