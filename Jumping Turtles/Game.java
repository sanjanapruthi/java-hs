import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;





public class Game extends JPanel implements KeyListener{
	
	JFrame frame;
	ArrayList<Block> listOfBlocks;
	Hero hero;
	ArrayList<Bee> listOfBees;
	int lengthOfGame;
	ArrayList<Coin> listOfCoins;
	boolean gameInProgress;
	boolean instructions;
	boolean win;
	boolean lose;
	int points;
	BufferedImage one, two, three, four, five, six;
	Bg bg1,bg2,bg3,bg4,bg5,bg6;
	ArrayList<PowerUp> listOfPowerUps;
	boolean powerUpEnabled;
	int timeKeeper;

	
	



	public Game(){
		gameInProgress = false;
		lose = false;
		win = false;
		instructions=true;
		powerUpEnabled = false;
		timeKeeper=0;


		resetEverything();

		frame = new JFrame("Side Scroller");
		frame.add(this);
		frame.setSize(1400,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
		try{
			one = ImageIO.read(new File("one.png"));
			two = ImageIO.read(new File("two.png"));
			three = ImageIO.read(new File("three.png"));
			four = ImageIO.read(new File("four.png"));
			five = ImageIO.read(new File("five.png"));
			six = ImageIO.read(new File("six.png"));
		}catch(Exception e){

		}
				repaint();

		while(true){
			if (gameInProgress){
				run();
			}
			try{
				Thread.sleep(7);
			}catch(Exception e){

			}		
		}

	}

	public void paintComponent(Graphics g){

		super.paintComponent(g);
		//g.drawRect(100,100,100,100);
		//g.drawRect(hero.heroX, hero.heroY, 100,100);

		g.drawImage(one, bg1.x, bg1.y, 16000,800,null);
		g.drawImage(two, bg2.x, bg2.y, 16000,800,null);
		g.drawImage(three, bg3.x, bg3.y+100, 16000,800,null);
		g.drawImage(four, bg4.x, bg4.y, 16000,800,null);
		g.drawImage(five, bg5.x, bg5.y+100, 16000,800,null);
		g.drawImage(six, bg6.x, bg6.y+100, 16000,800,null);
	
	//	g.drawImage(three, bg3.x+16000, bg3.y, 16000,800,null);
		//g.drawImage(four, bg4.x+16000, bg4.y, 16000,800,null);
		g.drawImage(five, bg5.x+16000, bg5.y+100, 16000,800,null);
		g.drawImage(six, bg6.x+16000, bg6.y+100, 16000,800,null);

		//g.drawImage(five, bg5.x+32000, bg5.y, 16000,800,null);
		//g.drawImage(six, bg6.x+32000, bg6.y, 16000,800,null);



		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		g.setFont(f);
		Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 80);


		if (gameInProgress){
			for (int i=0; i<listOfBlocks.size(); i++){
				g.setColor(new Color(191, 127, 255));
				g.fillRect(listOfBlocks.get(i).x, listOfBlocks.get(i).y, 200,50);
				g.setColor(Color.BLACK);
			}
			g.drawImage(hero.turtleArray[hero.currentTurtleImage/15],hero.heroX,hero.heroY,100,100,null);
			for (int i=0; i<listOfBees.size(); i++){
				g.drawImage(listOfBees.get(i).beeArray[(listOfBees.get(i).currentBeeImage)/3],listOfBees.get(i).x,listOfBees.get(i).y,50,50,null);
				//g.drawRect(listOfBees.get(i).x+10, listOfBees.get(i).y+10, 40, 30);
			}
			for (int i=0; i<listOfCoins.size(); i++){
				g.drawImage(listOfCoins.get(i).coinArray[(listOfCoins.get(i).currentCoinImage)/4],listOfCoins.get(i).x,listOfCoins.get(i).y,50,50,null);
				//g.drawRect(listOfCoins.get(i).x, listOfCoins.get(i).y, 50, 50);

			}
			for (int i=0; i<listOfPowerUps.size(); i++){
				g.drawImage(listOfPowerUps.get(i).powerUpArray[(listOfPowerUps.get(i).currentPowerUpImage)/6],listOfPowerUps.get(i).x,listOfPowerUps.get(i).y,50,50,null);
				//g.drawRect(listOfCoins.get(i).x, listOfCoins.get(i).y, 50, 50);

			}
			g.setColor(new Color(175, 203, 247));
			g.drawString("Points: "+points,1100,50);
			g.setColor(Color.BLACK);
		}else if (instructions){
			//g.fillRect(100,100,100,100);
			g.setColor(new Color(175, 203, 247));
			g.drawString("Welcome to my side scroller!",50,80);
			g.drawString("The purpose of this game is to get as many points as possible.",50,160);
			g.drawString("Use the spacebar to jump and collect coins.",50,240);
			g.drawString("Avoid the bees.",50,320);
			g.drawString("The stars enable you to jump higher for a short period of time",50,400);
			g.drawString("Good luck!",50,500);
			g.drawString("Press the spacebar to begin",400,600);
			g.setColor(Color.BLACK);

		}else if (win){
			//g.fillRect(400,100,100,100);
			g.setColor(new Color(175, 203, 247));
			g.setFont(f2);
			g.drawString("YOU WON!!! :)",500,200);
			g.setFont(f);
			g.drawString("You have earned "+points+" points",450,400);
			g.setColor(Color.BLACK);


		}else if (lose){
			//g.fillRect(400,100,100,100);
			g.setColor(new Color(175, 203, 247));
			g.setFont(f2);
			g.drawString("You lost :(",500,200);
			g.setFont(f);
			g.drawString("You have earned "+points+" points",450,400);
			g.setColor(Color.BLACK);

		}


	}



	public static void main(String [] args){


		System.out.println("Hello world");
		Game game = new Game();


	}

	public void run(){
		//System.out.println(""+timeKeeper);
		for (int i=0; i<listOfBlocks.size(); i++){
			listOfBlocks.get(i).move();
			if (hero.getRect().intersects(listOfBlocks.get(i).getRect())){
				//figure out where it is, adjust accordingly
				if ((hero.heroY+100<=150)&&(hero.heroY+100>=100)){
					hero.heroY = 25;
				}else if ((hero.heroY+100<=350)&&(hero.heroY+100>=300)){
					hero.heroY = 225;
				}else if ((hero.heroY+100<=550)&&(hero.heroY+100>=500)){
					hero.heroY = 425;
				}else if ((hero.heroY+20<=150)&&(hero.heroY+20>=100)){
					hero.heroY = 131;
				}else if ((hero.heroY+20<=350)&&(hero.heroY+20>=300)){
					hero.heroY = 331;
				}else if ((hero.heroY+20<=550)&&(hero.heroY+20>=500)){
					hero.heroY = 531;
				}
			}
		}
		for (int i=0; i<listOfBees.size(); i++){
			if (hero.getRect().intersects(listOfBees.get(i).getRect())){
				try{
					Thread.sleep(1000);
				}catch(Exception e){

				}
				gameInProgress=false;
				lose=true;
			}
		}
		for (int i=listOfCoins.size()-1; i>=0; i--){
			if (hero.getRect().intersects(listOfCoins.get(i).getRect())){
				points++;
				listOfCoins.remove(i);
			}
		}

		for (int i=listOfPowerUps.size()-1; i>=0; i--){
			if (hero.getRect().intersects(listOfPowerUps.get(i).getRect())){
				listOfPowerUps.remove(i);
				powerUpEnabled = true;
				timeKeeper = 800;
			}
		}

		hero.move();
		for (int i=0; i<listOfBees.size(); i++){
			listOfBees.get(i).move();
		}
		for (int i=0; i<listOfCoins.size(); i++){
			listOfCoins.get(i).move();
		}

		for (int i=0; i<listOfPowerUps.size(); i++){
			listOfPowerUps.get(i).move();
		}


		bg1.move(1);
		bg2.move(2);
		bg3.move(3);
		bg4.move(4);
		bg5.move(5);
		bg6.move(6);

		if (timeKeeper>0){
			timeKeeper--;
			if (timeKeeper==0){
				powerUpEnabled=false;
			}
		}

		if (listOfBlocks.get(listOfBlocks.size()-1).x<-100){
			try{
				Thread.sleep(1000);
			}catch(Exception e){

			}
			gameInProgress=false;
			win=true;
		}
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if ((e.getKeyCode()==32)&&(gameInProgress)){
			hero.jumping=true;
			hero.jumpingtracker=25;
			hero.heroY-=26;
			if (timeKeeper>0){
				hero.heroY-=100;
			}
		}else if ((e.getKeyCode()==32)&&(instructions==true)){
			gameInProgress=true;
			instructions=false;
			repaint();
		}else if ((e.getKeyCode()==32)&&(win==true)){
			instructions=true;
			win=false;
			resetEverything();
			repaint();
		}else if ((e.getKeyCode()==32)&&(lose==true)){
			lose=false;
			instructions=true;
			resetEverything();
			repaint();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}	

	public void resetEverything(){
		hero = new Hero();
		points = 0;
		listOfBlocks = new ArrayList<Block>();
		listOfBees = new ArrayList<Bee>();
		listOfCoins = new ArrayList<Coin>();
		listOfPowerUps = new ArrayList<PowerUp>();
		powerUpEnabled = false;
		timeKeeper = 0;

		File name = new File("BlockTextFile.txt");
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			int yVal = 0;
			while( (text=input.readLine())!= null)
			{
				System.out.println(text);
				lengthOfGame = text.length();
				for (int i=0; i<text.length(); i++){

					if (text.substring(i,i+1).equals("-")){
						listOfBlocks.add(new Block(i*200,100+yVal*200));
					}
				}
				yVal++;
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}
		
		for (int i=0; i<lengthOfGame/3; i++){
			int k = (int)(Math.random()*3);
			listOfBees.add(new Bee(i*1200+200,k*200+50));
		}
		for (int i=0; i<lengthOfGame/6; i++){
			int k = (int)(Math.random()*3);
			listOfPowerUps.add(new PowerUp(i*1200+200,k*200+50));
		}

		for (int i=0; i<lengthOfGame; i++){
			int k = (int)(Math.random()*3);
			listOfCoins.add(new Coin(i*200+100,k*200+50));
		}

		bg1 = new Bg();
		bg2 = new Bg();
		bg3 = new Bg();
		bg4 = new Bg();
		bg5 = new Bg();
		bg6 = new Bg();


	}
	





}