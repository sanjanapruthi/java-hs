import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;


public class Paddle {


    int xPos;
    int yPos;


    int right, left, up, down = 0;

    boolean space = false;


    public Paddle(int x,int y) {


        xPos = x;

        yPos = y;


    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        g2d.fillRoundRect(xPos, yPos, 100, 20, 10, 10);

        g2d.setColor(Color.MAGENTA);

        g2d.fillRoundRect(xPos+1, yPos+1, 98, 18, 10, 10);


    }


    public void move() {

        xPos += left + right;
		if (xPos>898)
			xPos=898;
		if (xPos<2)
			xPos=2;
        yPos = 700;

    }


    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_DOWN)

            down = 1;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)

            right = 3;

        if (e.getKeyCode() == KeyEvent.VK_UP)

            up = -1;

        if (e.getKeyCode() == KeyEvent.VK_LEFT)

            left = -3;



    }


    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_DOWN)

            down = 0;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)

            right = 0;

        if (e.getKeyCode() == KeyEvent.VK_UP)

            up = 0;

        if (e.getKeyCode() == KeyEvent.VK_LEFT)

            left = 0;


    }

    public Rectangle getBorder() {
        return new Rectangle(xPos, yPos, 100, 20);
    }


}