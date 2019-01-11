import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;


public class PowerUp{

	int x,y;
	BufferedImage powerUpSpriteSheet;
	BufferedImage[] powerUpArray;
	int currentPowerUpImage;
	Rectangle rect;
	int timeKeeper;
	
	public PowerUp(int x, int y){

		 this.x = x;
		 this.y = y;

		try{
			powerUpSpriteSheet = ImageIO.read(new File("star.png"));
		}catch(Exception e){

		}
		powerUpArray=new BufferedImage[4];

		for (int i=0; i<4; i++){
			powerUpArray[i] = powerUpSpriteSheet.getSubimage(26*i,0,25,25);
		}

	}

	public void move(){
		currentPowerUpImage++;
		if (currentPowerUpImage==24){
			currentPowerUpImage=0;
		}
		x--;
	}


	public Rectangle getRect(){
		rect = new Rectangle(x, y, 25,25);
		return rect;

	}



}