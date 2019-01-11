import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;


public class Hero{
	
	int heroY,heroX;
	boolean jumping;
	int jumpingtracker;
	BufferedImage turtleSpriteSheet;
	BufferedImage[] turtleArray;
	int currentTurtleImage;
	Rectangle rect;


	public Hero(){
		heroY = 600;
		heroX = 100;
		jumpingtracker=25;
		jumping = false;
		try{
			turtleSpriteSheet = ImageIO.read(new File("turtlesheet.png"));
		}catch(Exception e){

		}
		turtleArray=new BufferedImage[3];

		for (int i=0; i<3; i++){
			turtleArray[i] = turtleSpriteSheet.getSubimage(48*i,100,50,50);
		}


	}

	public void move(){
		currentTurtleImage++;
		if (currentTurtleImage==45){
			currentTurtleImage=0;
		}
		if (jumping==true){
			heroY=heroY-5;
			jumpingtracker--;
			if (jumpingtracker==0){
				jumpingtracker=25;
				jumping=false;
			}
		}
		if (heroY>=600){
			heroY=600;
		}else {
			heroY+=1;
		}
		if (heroY<0){
			heroY=0;
		}
	}

	public Rectangle getRect(){
		rect = new Rectangle(heroX, heroY, 100,100);
		return rect;

	}



}