
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import java.applet.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
public class GameTemplate extends JPanel implements KeyListener,Runnable,MouseListener
{
	private float angle;
	private int x,x2;
	private int y,y2;
	private JFrame frame;
	Thread t;
	private boolean gameOn1, gameOn2, introScreen, instructionsScreen, restartScreen;
	BufferedImage guy, guy2, guy3, guy4, carrotImg, bgimg;
	BufferedImage[] guys=new BufferedImage[3];//blue right
	BufferedImage[] guys2=new BufferedImage[3];//blue left
	BufferedImage[] guys3=new BufferedImage[3];//yellow right
	BufferedImage[] guys4=new BufferedImage[3];//yellow left
	boolean restart=false;
	int imgCount=0;
	int imgCount2 = 0;
	boolean right = true;
	boolean right2 = false;
	boolean up = false;
	boolean up2 = false;
	int score1=0;
	int score2 = 0;
//	Thread t1;
	int keyPressedByUser;
	ArrayList<Carrot> carrotList;
	ArrayList<Base> baseList;
//	ArrayList<Rectangle> carrotRects, baseRects;
	Rectangle bunny1rect;
	Rectangle bunny2rect;
	boolean canWalk=false;
	boolean canWalk2=false;
	//boolean canJump = true;
	boolean intersectsSomething=false;
	int starttime;
	String finalMessage;
	boolean displayRestart = false;
	
	public GameTemplate()
	{
		frame=new JFrame();
		x=50;
		y=650;
		x2 = 1300;
		y2 = 650;
		gameOn1=false;
		gameOn2 = false;
		introScreen = true;
		instructionsScreen=false;
		restartScreen=false;
		playSound2();
		
		
		//get images from spritesheets
		try {
			guy = ImageIO.read(new File("st1.png"));
			guy2 = ImageIO.read(new File("st1.png"));
			guy3 = ImageIO.read(new File("st1.png"));
			guy4 = ImageIO.read(new File("st1.png"));


			for(int x=0;x<3;x++){
				guys[x]=guy.getSubimage((x%3)*64,129,64,62);
			}
			for(int x=0;x<3;x++){
				guys2[x]=guy2.getSubimage((x%3)*64,65,64,62);
			}
			for(int x=0;x<3;x++){
				guys3[x]=guy3.getSubimage(576+(x%3)*64,256+129,64,62);
			}
			for(int x=0;x<3;x++){
				guys4[x]=guy4.getSubimage(576+(x%3)*64,256+65,64,62);
			}


		}
		catch (IOException e) {
		}

		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.add(this);
		frame.setSize(1400,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		t=new Thread(this);
		t.start();
		
		//make lists of all the objects on the screen
		carrotList = new ArrayList<Carrot>();
		for (int i=0; i<49; i++) {
			carrotList.add(new Carrot(100+i*25, 110+140*((int)(Math.random()*5))));//55,165, 275 
		}
		
		baseList = new ArrayList<Base>();
		for (int i=0; i<3; i++) {
			baseList.add(new Base(50+i*400, 155));
		}
		for (int i=0; i<3; i++) {
			baseList.add(new Base(250+i*400, 295));
		}
		for (int i=0; i<3; i++) {
			baseList.add(new Base(50+i*400, 435));
		}
		for (int i=0; i<3; i++) {
			baseList.add(new Base(250+i*400, 575));
		}
		

	}

	public void run()
	{
		while(true)
		{
			if(gameOn1) //single player mode
			{
				if (100-((int)System.currentTimeMillis()-starttime)/1000 <= 0) {//timer
					//gameOn1=false;
					restartScreen=true;
					finalMessage = "You lost :(";
					try {
						Thread.sleep(3000);
						gameOn1=false;
						displayRestart=true;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//Math happens here!
				
				if(keyPressedByUser==39)//right
				{
					right=true;
					up = false;
					x+=2;
					imgCount++;
					if(imgCount>2)
						imgCount=0;
				}
				if(keyPressedByUser==37)//left
				{
					right = false;
					up = false;
					x-=2;
					imgCount++;
					if(imgCount>2)
						imgCount=0;
				}
				
				if(keyPressedByUser==38) {//up
					up = true;
					y-=6;
					
				}
				if (canWalk==false) {//falls
					if (y<650) {
						y+=1;
					}
					
				}
				bunny1rect = new Rectangle(x+20, y+15, 35, 65);
				//g2d.drawRect(x+20, y+15, 35, 65);

				if (baseList!=null) {//intersections with bases
					intersectsSomething=false;
					for (int i=0; i<baseList.size(); i++) {
						if (bunny1rect.intersects(baseList.get(i).getBorder())) {
							//System.out.print("sos");
							intersectsSomething=true;
							if (((x+55)>=baseList.get(i).x)&&(y+15<baseList.get(i).y)&&(y+80>baseList.get(i).y+10)&&(x+20<baseList.get(i).x)) {
								x=baseList.get(i).x-55;
							}
							if (((y+80)>=baseList.get(i).y)&&(y<baseList.get(i).y)&&((y+80)<baseList.get(i).y+10)) {
								//y--;
								//System.out.println("hello");
								canWalk=true;
							}else {
								canWalk=false;
							}
							if ((y+15<(baseList.get(i).y+10))&&(y+40>(baseList.get(i).y+10))) {
								y=baseList.get(i).y;
							}
							if ((x+20<(baseList.get(i).x+250))&&(y+15<baseList.get(i).y)&&(y+80>baseList.get(i).y+10)&&(x+55>baseList.get(i).x+250)) {
								x=baseList.get(i).x+230;

							}
						}
					}
					if (intersectsSomething==false) {
						canWalk=false;
					}

				}
				
				//stays in bounds
				if (x+55>=1400) {
					x=1400-55-1;
				}
				if (x+20<=0) {
					x=-20+1;
				}
				if (y+15+65>=800) {
					y = 800-80-1;
				}
				if (y+15<=0) {
					y=-15+1;
				}
				
				bunny1rect = new Rectangle(x+20, y+15, 35, 65);
				
				if (carrotList!=null) {//intersections with carrots
					for (int i=0; i<carrotList.size(); i++) {
						if (bunny1rect.intersects(carrotList.get(i).getBorder())) {
							carrotList.remove(i);
							playSound();
							score1++;
						}
					}

				}
				
				if (carrotList.size()==0) {//check if won
					//gameOn1=false;
					restartScreen=true;
					finalMessage = "YOU WON!!! :)";
					try {
						Thread.sleep(3000);
						gameOn1=false;
						displayRestart=true;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				


				
			}
			
			if (gameOn2) {//2 player
				
				//Math happens here!
				
				if(keyPressedByUser==68)//d
				{
					right=true;
					up = false;
					x+=2;
					imgCount++;
					if(imgCount>2)
						imgCount=0;
				}
				if(keyPressedByUser==65)//a
				{
					right = false;
					up = false;
					x-=2;
					imgCount++;
					if(imgCount>2)
						imgCount=0;
				}
				if(keyPressedByUser==39)//right
				{
					right2 = true;
					up2 = false;
					x2+=2;
					imgCount2++;
					if(imgCount2>2)
						imgCount2=0;
				}
				if(keyPressedByUser==37)//left
				{
					right2 = false;
					up2 = false;
					x2-=2;
					imgCount2++;
					if(imgCount2>2)
						imgCount2=0;
				}
				if(keyPressedByUser==38) {//up
					up2 = true;
					y2-=10;//////////////////////////////////////////////////////////////
					
				}
				if(keyPressedByUser==87) {//w
					up = true;
					y-=10;///////////////////////////////////////////////////////////////
					
				}
				if (canWalk==false) {
					if (y<650) {
						y+=1;
					}
				}
				
				if (canWalk2==false) {
					if (y2<650) {
						y2+=1;
					}
				}
				
				bunny1rect = new Rectangle(x+20, y+15, 35, 65);
				bunny2rect = new Rectangle(x2+20, y2+15, 35, 65);

				//g2d.drawRect(x+20, y+15, 35, 65);

				if (baseList!=null) {//intersects with bases

					intersectsSomething=false;
					for (int i=0; i<baseList.size(); i++) {
						if (bunny1rect.intersects(baseList.get(i).getBorder())) {
							//System.out.print("sos");
							intersectsSomething=true;
							if (((x+55)>=baseList.get(i).x)&&(y+15<baseList.get(i).y)&&(y+80>baseList.get(i).y+10)&&(x+20<baseList.get(i).x)) {
								x=baseList.get(i).x-55;
							}
							if (((y+80)>=baseList.get(i).y)&&(y<baseList.get(i).y)&&((y+80)<baseList.get(i).y+10)) {
								//y--;
								//System.out.println("hello");
								canWalk=true;
							}else {
								canWalk=false;
							}
							if ((y+15<(baseList.get(i).y+10))&&(y+40>(baseList.get(i).y+10))) {
								y=baseList.get(i).y;
							}
							if ((x+20<(baseList.get(i).x+250))&&(y+15<baseList.get(i).y)&&(y+80>baseList.get(i).y+10)&&(x+55>baseList.get(i).x+250)) {
								x=baseList.get(i).x+230;

							}
						}
					}
					if (intersectsSomething==false) {
						canWalk=false;
					}

				}
				if (baseList!=null) {//bunny 2 intersects w bases

					intersectsSomething=false;
					for (int i=0; i<baseList.size(); i++) {
						if (bunny2rect.intersects(baseList.get(i).getBorder())) {
							//System.out.print("sos");
							intersectsSomething=true;
							if (((x2+55)>=baseList.get(i).x)&&(y2+15<baseList.get(i).y)&&(y2+80>baseList.get(i).y+10)&&(x2+20<baseList.get(i).x)) {
								x2=baseList.get(i).x-55;
							}
							if (((y2+80)>=baseList.get(i).y)&&(y2<baseList.get(i).y)&&((y2+80)<baseList.get(i).y+10)) {
								//y--;
								//System.out.println("hello");
								canWalk2=true;
							}else {
								canWalk2=false;
							}
							if ((y2+15<(baseList.get(i).y+10))&&(y2+40>(baseList.get(i).y+10))) {
								y2=baseList.get(i).y;
							}
							if ((x2+20<(baseList.get(i).x+250))&&(y2+15<baseList.get(i).y)&&(y2+80>baseList.get(i).y+10)&&(x2+55>baseList.get(i).x+250)) {
								x2=baseList.get(i).x+230;

							}
						}
					}
					if (intersectsSomething==false) {
						canWalk2=false;
					}

				}
				
				
				//stays in bounds
				if (x+55>=1400) {
					x=1400-55-1;
				}
				if (x+20<=0) {
					x=-20+1;
				}
				if (y+15+65>=800) {
					y = 800-80-1;
				}
				if (y+15<=0) {
					y=-15+1;
				}
				
				if (x2+55>=1400) {
					x2=1400-55-1;
				}
				if (x2+20<=0) {
					x2=-20+1;
				}
				if (y2+15+65>=800) {
					y2 = 800-80-1;
				}
				if (y2+15<=0) {
					y2=-15+1;
				}
				
			
				bunny1rect = new Rectangle(x+20, y+15, 35, 65);
				bunny2rect = new Rectangle(x2+20, y2+15, 35, 65);

				
				if (carrotList!=null) {//carrot intersections

					for (int i=0; i<carrotList.size(); i++) {
						if (bunny1rect.intersects(carrotList.get(i).getBorder())) {
							carrotList.remove(i);
							playSound();
							score1++;
						}
						
					}
					for (int i=0; i<carrotList.size(); i++) {
						if (bunny2rect.intersects(carrotList.get(i).getBorder())) {
							carrotList.remove(i);
							playSound();
							score2++;
						}
						
					}
					
					if (carrotList.size()<=0) {
						restartScreen=true;
						
						if (score1>=score2)
							finalMessage = "Player 1 won! :)";
							
						if (score1<score2)
							finalMessage = "Player 2 won! :)";	
							
						try {
							Thread.sleep(3000);
							gameOn1=false;
							displayRestart=true;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

				}
				

				
			}
			repaint();

			if(restart)
			{
				restart=false;
				gameOn2=true;
			}
			try
			{
				t.sleep(10);
			}catch(InterruptedException e)
			{
			}
		
			
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		//all painting happens here!

		//g2d.fillRect(50,50,350,350);
		
		if (introScreen==true) {
			g2d.setColor(new Color(187, 247, 246));
			g2d.fillRect(0,0,1400,1000);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 100);
			g2d.setFont(f);
			g2d.setColor(new Color(115, 11, 224));
			g2d.drawString("Hungry Bunnies!",280,300);
			g2d.setColor(new Color(205, 169, 229));
			g2d.fillRoundRect(550, 350, 300, 100, 25, 25);
			g2d.fillRoundRect(750, 500, 250, 80, 25, 25);
			g2d.fillRoundRect(400, 500, 250, 80, 25, 25);
			g2d.setColor(new Color(61, 9, 183));
			Font f2 = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
			g2d.setFont(f2);
			g2d.drawString("Instructions", 560, 420);
			g2d.drawString("1 player", 420, 560);
			g2d.drawString("2 player", 770, 560);

			
		}
		
		if (gameOn1==true) {	
			try {
				bgimg = ImageIO.read(new File("meadow.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g2d.drawImage(bgimg, 0, 0, 1400, 800, null);
			
			g2d.setColor(Color.BLACK);
			if (right==true) {
				if (up==true) {
					imgCount = 1;
				}
				g2d.drawImage(guys[imgCount],x,y,80,80,null);
				//g2d.drawRect(x+10, y+5, 35, 55);
			}
			if (right==false) {
				if (up==true) {
					imgCount = 1;
				}
				g2d.drawImage(guys2[imgCount],x,y,80,80,null);
			}
			try {
				carrotImg = ImageIO.read(new File("carrot.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i=0; i<carrotList.size(); i++) {
				g2d.drawImage(carrotImg, carrotList.get(i).x, carrotList.get(i).y, 30, 30, null);
			}
			
			g2d.setColor(new Color(117, 12, 12));
			for (int i=0; i<baseList.size(); i++) {
				g2d.fillRect(baseList.get(i).x, baseList.get(i).y, baseList.get(i).width, baseList.get(i).height);
	
			}
			
			g2d.setColor(new Color(61, 9, 183));
			Font f7 = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			g2d.setFont(f7);
			g2d.drawString("Time left: "+(100-((int)System.currentTimeMillis()-starttime)/1000)+"", 100, 50);
			g2d.drawString("Score: "+score1+"", 1200, 50);

			
			
			
		}
		
		
		if (gameOn2==true) {	
			try {
				bgimg = ImageIO.read(new File("meadow.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g2d.drawImage(bgimg, 0, 0, 1400, 800, null);
			
			g2d.setColor(Color.BLACK);
			if (right==true) {
				if (up==true) {
					imgCount = 1;
				}
				g2d.drawImage(guys[imgCount],x,y,80,80,null);
				//g2d.drawRect(x+10, y+5, 35, 55);
			}
			if (right==false) {
				if (up==true) {
					imgCount = 1;
				}
				g2d.drawImage(guys2[imgCount],x,y,80,80,null);
			}
			if (right2==true) {
				if (up2==true) {
					imgCount2 = 1;
				}
				g2d.drawImage(guys3[imgCount2],x2,y2,80,80,null);
			}
			if (right2==false) {
				if (up2==true) {
					imgCount2 = 1;
				}
				g2d.drawImage(guys4[imgCount2],x2,y2,80,80,null);
			}
			try {
				carrotImg = ImageIO.read(new File("carrot.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i=0; i<carrotList.size(); i++) {
				g2d.drawImage(carrotImg, carrotList.get(i).x, carrotList.get(i).y, 30, 30, null);
			}
			
			g2d.setColor(new Color(117, 12, 12));
			for (int i=0; i<baseList.size(); i++) {
				g2d.fillRect(baseList.get(i).x, baseList.get(i).y, baseList.get(i).width, baseList.get(i).height);
	
			}
			g2d.setColor(new Color(61, 9, 183));
			Font f7 = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			g2d.setFont(f7);
			g2d.drawString("Player 1 Score: "+score1+"", 100, 50);
			g2d.drawString("Player 2 Score: "+score2+"", 1200, 50);
		}
		
		
		
		if (instructionsScreen==true) {
			g2d.setColor(new Color(187, 247, 246));
			g2d.fillRect(0,0,1400,1000);
			Font f3 = new Font(Font.SANS_SERIF, Font.BOLD, 80);
			g2d.setFont(f3);
			g2d.setColor(new Color(115, 11, 224));
			g2d.drawString("Instructions",450,100);
			g2d.setColor(new Color(205, 169, 229));
			g2d.fillRoundRect(100, 650, 200, 80, 25, 25);
			g2d.setColor(new Color(61, 9, 183));
			Font f4 = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
			g2d.setFont(f4);
			g2d.drawString("Back", 140, 710);
			g2d.setColor(new Color(47, 19, 114));
			Font f5 = new Font(Font.SANS_SERIF, Font.PLAIN, 35);
			g2d.setFont(f5);
			g2d.drawString("Help the hungry bunnies find food!", 100, 200);
			g2d.drawString("Move the bunnies around the screen to collect carrots.", 100, 250);
			g2d.drawString("One Player: Get all the carrots in 100 seconds to win!", 100, 325);
			g2d.drawString("Use the left and right arrow keys to move sideways, and use the ", 100, 375);
			g2d.drawString("up arrow key to jump. ", 100, 425);
			g2d.drawString("Two players: The player with the most carrots wins!", 100, 500);
			g2d.drawString("Player 1: Use the 'a', 'd' and 'w' keys to move and to jump", 100, 550);
			g2d.drawString("Player 2: Use the left, right and up arrow keys to move and to jump", 100, 600);


			
		}
		
		if (restartScreen==true) {
			
			if (displayRestart) {
				g2d.setColor(new Color(187, 247, 246));
				g2d.fillRect(0,0,1400,1000);
				g2d.setColor(new Color(205, 169, 229));
				g2d.fillRoundRect(550, 350, 300, 100, 25, 25);
				g2d.setColor(new Color(61, 9, 183));
				Font f2 = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
				g2d.setFont(f2);
				g2d.drawString("Play Again", 560, 420);
			}
			
			g2d.setColor(new Color(61, 9, 183));
			Font f6 = new Font(Font.SANS_SERIF, Font.BOLD, 100);
			g2d.setFont(f6);
			g2d.drawString(finalMessage, 300, 300);
			
			
		}


	}
	public void keyPressed(KeyEvent key)
	{
		System.out.println(key.getKeyCode());
		keyPressedByUser = key.getKeyCode();
		
		if(key.getKeyCode()==82)
			restart=true;
		
	}
	public void keyReleased(KeyEvent key)
	{
		keyPressedByUser = 0;
	}
	public void keyTyped(KeyEvent key)
	{
	}
	public static void main(String args[])
	{
		GameTemplate app=new GameTemplate();
		//Carrot c = new Carrot();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
		if ((introScreen==true)&&(e.getX()>400)&&(e.getX()<650)&&(e.getY()>500)&&(e.getY()<580)) {
			gameOn1=true;
			starttime = (int)System.currentTimeMillis();
			//System.out.println("pressed");
			introScreen=false;
		}
		if ((introScreen==true)&&(e.getX()>750)&&(e.getX()<1000)&&(e.getY()>500)&&(e.getY()<580)) {
			gameOn2=true;
			//System.out.println("pressed2");
			introScreen=false;
		}
		if ((introScreen==true)&&(e.getX()>550)&&(e.getX()<850)&&(e.getY()>350)&&(e.getY()<450)) {
			instructionsScreen=true;
			//System.out.println("presse3d");
			introScreen=false;
		}
		if ((instructionsScreen==true)&&(e.getX()>100)&&(e.getX()<300)&&(e.getY()>650)&&(e.getY()<730)) {
			instructionsScreen=false;
			//System.out.println("pressed4");
			introScreen=true;
		}
		
		//reset everythinggg
		if ((displayRestart==true)&&(restartScreen==true)&&(e.getX()>550)&&(e.getX()<850)&&(e.getY()>350)&&(e.getY()<450)) {
			x=50;
			y=650;
			x2 = 1300;
			y2 = 650;
			gameOn1=false;
			gameOn2 = false;
			introScreen = true;
			instructionsScreen=false;
			restartScreen=false;
			displayRestart=false;
			carrotList = new ArrayList<Carrot>();
			//make carrot array
			for (int i=0; i<49; i++) {
				carrotList.add(new Carrot(100+i*25, 110+140*((int)(Math.random()*5))));//55,165, 275 
			}
			
			baseList = new ArrayList<Base>();
			for (int i=0; i<3; i++) {
				baseList.add(new Base(50+i*400, 155));
			}
			for (int i=0; i<3; i++) {
				baseList.add(new Base(250+i*400, 295));
			}
			for (int i=0; i<3; i++) {
				baseList.add(new Base(50+i*400, 435));
			}
			for (int i=0; i<3; i++) {
				baseList.add(new Base(250+i*400, 575));
			}
			imgCount=0;
			imgCount2 = 0;
			right = true;
			right2 = false;
			up = false;
			up2 = false;
			score1=0;
			score2 = 0;
			intersectsSomething=false;
			canWalk=false;
			canWalk=false;
			
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void playSound() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/ding.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void playSound2() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/music.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.loop(100);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	
}