import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Enemy{

	int x,y;
	BufferedImage beeSpriteSheet;
	BufferedImage[] beeArray;
	int currentBeeImage;
	Rectangle rect;
	
	public Enemy(int x, int y){

		 this.x = x;
		 this.y = y;

		try{
			beeSpriteSheet = ImageIO.read(new File("beespritesheet.png"));
		}catch(Exception e){

		}
		turtleArray=new BufferedImage[3];

		for (int i=0; i<3; i++){
			beeArray[i] = beeSpriteSheet.getSubimage(48*i,100,50,50);
		}

	}

	public void move(){
		currentBeeImage++;
		if (currentBeeImage==11){
			currentBeeImage=0;
		}
		
		x--;
	}

	public Rectangle getRect(){
		rect = new Rectangle(x, y, 100,100);
		return rect;

	}



}