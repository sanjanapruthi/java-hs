import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;


public class Coin{

	int x,y;
	BufferedImage coinSpriteSheet;
	BufferedImage[] coinArray;
	int currentCoinImage;
	Rectangle rect;
	
	public Coin(int x, int y){

		 this.x = x;
		 this.y = y;

		try{
			coinSpriteSheet = ImageIO.read(new File("coinSpriteSheet.png"));
		}catch(Exception e){

		}
		coinArray=new BufferedImage[6];

		for (int i=0; i<6; i++){
			coinArray[i] = coinSpriteSheet.getSubimage(115*i,0,115,200);
		}

	}

	public void move(){
		currentCoinImage++;
		if (currentCoinImage==24){
			currentCoinImage=0;
		}
		x--;
	}


	public Rectangle getRect(){
		rect = new Rectangle(x, y, 50,50);
		return rect;

	}



}