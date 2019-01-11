import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;


public class Bee{

	int x,y;
	BufferedImage beeSpriteSheet;
	BufferedImage[] beeArray;
	int currentBeeImage;
	Rectangle rect;
	
	public Bee(int x, int y){

		 this.x = x;
		 this.y = y;

		try{
			beeSpriteSheet = ImageIO.read(new File("beesprite.png"));
		}catch(Exception e){

		}
		beeArray=new BufferedImage[11];

		for (int i=0; i<11; i++){
			beeArray[i] = beeSpriteSheet.getSubimage(244*(i%5),244*(i/5),244,244);
		}

	}

	public void move(){
		currentBeeImage++;
		if (currentBeeImage==33){
			currentBeeImage=0;
		}
		
		x-=2;
	}

	public Rectangle getRect(){
		rect = new Rectangle(x+10, y+10, 40, 30);
		return rect;

	}



}