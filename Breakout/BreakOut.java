import javax.swing.JFrame;

import javax.swing.JPanel;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.RenderingHints;

import java.awt.event.*;

import java.io.File;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.awt.Rectangle;

import java.util.ArrayList;

import java.awt.Font;

import java.awt.Color;

public class BreakOut extends JPanel implements KeyListener {

    BufferedImage image;
	boolean space = false;
	boolean gameStart = false;
	volatile boolean gameStartSpace = false;
	int score = 0;
	boolean gameOver=false;
	Paddle paddle = new Paddle(450,700);
	ArrayList<Brick> bricks = new ArrayList<Brick>();
	ArrayList<Ball> balls = new ArrayList<Ball>();
	int level = 1;
	int lives = 3;
	boolean levelChange=true;
	ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	boolean laserOn=false;
	int laserCounter = 0; //time for laser
	ArrayList<Laser> lasers = new ArrayList<Laser>();

    public BreakOut() {

        setFocusable(true);

        JFrame frame = new JFrame("Break Out");


        frame.setSize(1200, 900);

		balls.add(new Ball(493,686));

        frame.add(this);

        frame.setVisible(true);

        addKeyListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		while (true){

			while (gameStart==false){
				repaint();
				if (gameStartSpace==true)
					gameStart=true;
			}

			while (gameOver==false) {

				if (levelChange==true){
					for (int j=powerUps.size()-1; j>=0; j--){
						powerUps.remove(j);
					}
					for (int i=lasers.size()-1; i>=0; i--){
						lasers.remove(i);
					}
					for (int i=balls.size()-1; i>=0; i--){
						balls.remove(i);
					}
					balls.add(new Ball(493,686));
					laserOn=false;
					if (level==1){
						for (int i=bricks.size()-1; i>=0; i--){
							bricks.remove(i);
						}
						for (int i=0; i<12; i++)
						bricks.add(new Brick(10+80*i,100,Color.red));
						for (int i=0; i<12; i++)
						bricks.add(new Brick(25+80*i,150,Color.orange));
						for (int i=0; i<12; i++)
						bricks.add(new Brick(10+80*i,200,Color.yellow));
						for (int i=0; i<12; i++)
						bricks.add(new Brick(25+80*i,250,Color.green));
					}

					if (level==2){
						lives++;
						for (int i=0; i<5; i++){
							bricks.add(new Brick(10, 10+70*i, Color.red));
							bricks.add(new Brick(140, 10+70*i, Color.orange));
							bricks.add(new Brick(270, 10+70*i, Color.yellow));
							bricks.add(new Brick(400, 10+70*i, Color.green));
							bricks.add(new Brick(530, 10+70*i, Color.cyan));
							bricks.add(new Brick(660, 10+70*i, Color.blue));
							bricks.add(new Brick(790, 10+70*i, Color.magenta));
							bricks.add(new Brick(920, 10+70*i, Color.pink));
						}
					}
					if (level==3){
						lives++;
						for (int i=0; i<1; i++){
							bricks.add(new Brick(20+80*i, 290+70*i, Color.red));
						}
						for (int i=0; i<2; i++){
							bricks.add(new Brick(20+80*i, 220+70*i, Color.orange));
						}
						for (int i=0; i<3; i++){
							bricks.add(new Brick(20+80*i, 150+70*i, Color.yellow));
						}
						for (int i=0; i<4; i++){
							bricks.add(new Brick(20+80*i, 80+70*i, Color.green));
						}
						for (int i=0; i<5; i++){
							bricks.add(new Brick(20+80*i, 10+70*i, Color.cyan));
							bricks.add(new Brick(100+80*i, 10+70*i, Color.blue));
							bricks.add(new Brick(180+80*i, 10+70*i, Color.magenta));
							bricks.add(new Brick(260+80*i, 10+70*i, Color.pink));
							bricks.add(new Brick(340+80*i, 10+70*i, Color.red));
							bricks.add(new Brick(420+80*i, 10+70*i, Color.orange));
							bricks.add(new Brick(500+80*i, 10+70*i, Color.yellow));
							bricks.add(new Brick(580+80*i, 10+70*i, Color.green));
						}
						for (int i=0; i<4; i++){
							bricks.add(new Brick(660+80*i, 10+70*i, Color.cyan));
						}
						for (int i=0; i<3; i++){
							bricks.add(new Brick(740+80*i, 10+70*i, Color.blue));
						}
						for (int i=0; i<2; i++){
							bricks.add(new Brick(820+80*i, 10+70*i, Color.magenta));
						}
						for (int i=0; i<1; i++){
							bricks.add(new Brick(900+80*i, 10+70*i, Color.pink));
						}
					}

					repaint();
					try {
						Thread.sleep(1000);
					} catch (Exception e){
					}
					levelChange=false;
				}

				while (levelChange==false){
					paddle.move();

					for (int i=powerUps.size()-1; i>=0; i--){
						PowerUp p = powerUps.get(i);
						p.move();
						if (p.getBorder().intersects(paddle.getBorder())){
							specialStuff(p);
							powerUps.remove(p);
						}
						if (p.yPos==750)
							powerUps.remove(p);
						if (p.yPos<0)
							powerUps.remove(p);
					}

					if (laserOn==true){
						if (laserCounter%50==0){
							lasers.add(new Laser(paddle.xPos+28,688));
							lasers.add(new Laser(paddle.xPos+70,688));
						}
						laserCounter++;
						if (laserCounter>1000){
							laserOn=false;
							laserCounter=0;
						}
					}

					for (int i=lasers.size()-1; i>=0; i--){
						Laser l = lasers.get(i);
						l.move();
						for (int j=bricks.size()-1; j>=0; j--){
							Brick b = bricks.get(j);
							if (l.getBorder().intersects(b.getBorder())){
								bricks.remove(j);
								lasers.remove(i);
								score+=10;
								break;
							}
						}
					}
					for (int i=lasers.size()-1; i>=0; i--){
						Laser l = lasers.get(i);
						if (l.yPos<=0)
							lasers.remove(i);
					}
					for (Ball ball: balls){
						ball.move();

						if (ball.getBorder().intersects(paddle.getBorder())){
							if (ball.xPos<paddle.xPos+25){
								ball.left=-2;
								ball.right=0;
								ball.up=-1;
								ball.down=0;
							}else if (ball.xPos+7<paddle.xPos+50){
								ball.left=-1;
								ball.right=0;
								ball.up=-2;
								ball.down=0;
							}
							else if (ball.xPos+7<paddle.xPos+75){
								ball.left=0;
								ball.right=1;
								ball.up=-2;
								ball.down=0;
							}else if (ball.xPos<=paddle.xPos+100){
								ball.left=0;
								ball.right=2;
								ball.up=-1;
								ball.down=0;
							}
							if (ball.xPos+7==paddle.xPos+50){
								ball.left=0;
								ball.right=0;
								ball.up=-2;
								ball.down=0;
							}
						}
						if (ball.yPos<0){
							//ball.down=-1*ball.up;
							//ball.up=0;
							ball.up*=-1;
						}
						if (ball.yPos>710){
							ball.up=0;
							ball.down=0;
							ball.left=0;
							ball.right=0;
							balls.remove(ball);
							if ((balls.size()==0)&&(lives>0)){
								lives--;
								for (int j=powerUps.size()-1; j>=0; j--){
									powerUps.remove(j);
								}
								for (int i=lasers.size()-1; i>=0; i--){
									lasers.remove(i);
								}
								laserOn=false;
								balls.add(new Ball(493,686));
								balls.get(0).xPos=493;
								balls.get(0).yPos=686;
								balls.get(0).up=-2;
								paddle.xPos=450;
								paddle.yPos=700;
								try {
									Thread.sleep(1000);
								} catch (Exception e){
								}
							}
							if (lives<=0){
								gameOver=true;
								for (int j=powerUps.size()-1; j>=0; j--){
									powerUps.remove(j);
								}
								for (int i=lasers.size()-1; i>=0; i--){
									lasers.remove(i);
								}
								laserOn=false;
							}
							repaint();
							balls.remove(ball);
							break;
						}
						if (gameOver==true)
							break;
						if (ball.xPos<0){
							ball.right=-1*ball.left;
							ball.left=0;
						}
						if (ball.xPos>984){
							ball.left=-1*ball.right;
							ball.right=0;
						}

						for (int i=bricks.size()-1; i>=0; i--){
							if ((Math.abs((ball.xPos+14)-(bricks.get(i).xPos))<=2)&&(ball.yPos+14>=bricks.get(i).yPos)&&(ball.yPos<=bricks.get(i).yPos+30)){
								ball.left=-1*ball.right;
								ball.right=0;
								checkPowerUp(bricks.get(i));
								bricks.remove(i);
								score+=10;
							}
							else if ((Math.abs((ball.xPos)-(bricks.get(i).xPos+60))<=2)&&(ball.yPos+14>=bricks.get(i).yPos)&&(ball.yPos<=bricks.get(i).yPos+30)){
								ball.right=-1*ball.left;
								ball.left=0;
								checkPowerUp(bricks.get(i));
								bricks.remove(i);
								score+=10;
							}
							else if ((Math.abs((ball.yPos)-(bricks.get(i).yPos+30))<=2)&&(ball.xPos+14>=bricks.get(i).xPos)&&(ball.xPos<=bricks.get(i).xPos+60)){
								//ball.down=-1*ball.up;
								//ball.up=0;
								ball.up*=-1;
								checkPowerUp(bricks.get(i));
								bricks.remove(i);
								score+=10;
							}
							else if ((Math.abs((ball.yPos+14)-(bricks.get(i).yPos))<=2)&&(ball.xPos+14>=bricks.get(i).xPos)&&(ball.xPos<=bricks.get(i).xPos+60)){
								//ball.up=-1*ball.down;
								//ball.down=0;
								ball.up*=-1;
								checkPowerUp(bricks.get(i));
								bricks.remove(i);
								score+=10;
							}
						}
					}

					if (bricks.size()==0){
						if (level<=3){
							level++;
							if (level==4)
								gameOver=true;
							levelChange=true;
							for (int i=0; i<balls.size(); i++){
								balls.remove(i);
							}
							balls.add(new Ball(493,686));

							balls.get(0).xPos=493;
							balls.get(0).yPos=686;
							balls.get(0).up=-2;
							balls.get(0).down=0;
							balls.get(0).right=0;
							balls.get(0).left=0;
							paddle.xPos=450;
							paddle.yPos=700;
							repaint();
						}
					}

					repaint();
					try {

						Thread.sleep(6);

					} catch (Exception e){
					}
					if (gameOver==true)
						break;
				}


			}

			while (gameOver==true){

					try {
						Thread.sleep(100);
					} catch (Exception e){
					}

					repaint();

					if (space==true){
						space=false;
						gameOver=false;
						score=0;
						for (int i=0; i<balls.size(); i++){
							balls.remove(i);
						}
						balls.add(new Ball(493,686));
						balls.get(0).xPos=493;
						balls.get(0).yPos=686;
						balls.get(0).up=-2;
						paddle.xPos=450;
						paddle.yPos=700;
						lives = 3;
						level=1;
						levelChange=true;
						repaint();
					}

			}
		}
	}



    public static void main(String[] args) {

        BreakOut game = new BreakOut();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



		if (gameStart==false){
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, 1000, 800);
			g2d.setColor(Color.pink);
			g2d.fillRect(2, 2, 996, 796);
			g2d.setColor(Color.blue);
			g2d.setFont(new Font("TimesRoman", Font.ITALIC, 30));
			g2d.drawString("Welcome to Break Out!!!",300,300);
			g2d.drawString("Press Spacebar to Begin", 300, 350);
		}

		if (gameStart == true){

			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, 1000, 800);
			g2d.setColor(Color.darkGray);
			g2d.fillRect(2, 2, 996, 796);
			paddle.paint(g2d);
			for (int i=0; i<bricks.size(); i++){
				if (bricks.get(i).visible==true)
					bricks.get(i).paint(g2d);
			}
			for (int i=0; i<balls.size(); i++){
				balls.get(i).paint(g2d);
			}
			for (PowerUp p:powerUps){
				p.paint(g2d);
			}
			for (Laser l:lasers){
				l.paint(g2d);
			}
			g2d.setColor(Color.blue);
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g2d.drawString("Score: "+score,1020,600);
			g2d.drawString("Lives: "+lives,1020,700);
			g2d.setFont(new Font("TimesRoman", Font.ITALIC, 20));
			g2d.drawString("POWER UPS:",1030,250);
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g2d.setColor(Color.green);
			g2d.fillOval(1020,300,7,7);
			g2d.drawString("= Extra Life",1030,310);
			g2d.setColor(Color.pink);
			g2d.fillOval(1020,350,7,7);
			g2d.drawString("= Additional Balls",1030,360);
			g2d.setColor(Color.red);
			g2d.fillOval(1020,400,7,7);
			g2d.drawString("= Laser",1030,410);


			if ((levelChange==true)&&(level<4)){
				g2d.setColor(Color.pink);
				g2d.setFont(new Font("TimesRoman", Font.ITALIC, 30));
				g2d.drawString("Level "+level,450,350);
			}

			if ((gameOver==true)&&(lives<=0)){
				g2d.setColor(Color.pink);
				g2d.setFont(new Font("TimesRoman", Font.BOLD, 30));
				g2d.drawString("Game Over :(",300,320);
				g2d.drawString("Your Score was: "+score,300,370);
				g2d.drawString("Press Spacebar to Play Again", 300, 420);
			}

			if (level==4){
				g2d.setColor(Color.pink);
				g2d.setFont(new Font("TimesRoman", Font.BOLD, 30));
				g2d.drawString("YOU WIN!!!!",300,320);
				g2d.drawString("Your Score was: "+score,300,370);
				g2d.drawString("Press Spacebar to Play Again", 300, 420);
			}

		}

    }

    public void keyPressed(KeyEvent e) {

        if ((e.getKeyCode() == KeyEvent.VK_SPACE)&&(gameOver==true))
			space = true;

		if ((e.getKeyCode() == KeyEvent.VK_SPACE)&&(gameStart==false))
			gameStartSpace = true;

       	paddle.keyPressed(e);




    }

    public void keyReleased(KeyEvent e) {

		paddle.keyReleased(e);

    }

    public void keyTyped(KeyEvent e) {
        //not used
    }

    public void checkPowerUp(Brick b){
		if (b.powerUp==1)
			powerUps.add(new PowerUp(b.xPos+30, b.yPos+32, Color.green));
		if (b.powerUp==2)
			powerUps.add(new PowerUp(b.xPos+30, b.yPos+32, Color.pink));
		if (b.powerUp==3)
			powerUps.add(new PowerUp(b.xPos+30, b.yPos+32, Color.red));
	}

	public void specialStuff(PowerUp p){
		if (p.special==1)
			lives++;
		if (p.special==2){
			balls.add(new Ball(paddle.xPos+20,686));
			balls.add(new Ball(paddle.xPos+40,686));
		}
		if (p.special==3){
			laserOn=true;
		}
	}

}
