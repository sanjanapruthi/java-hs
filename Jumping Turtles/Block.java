import java.awt.*;


public class Block{

	
	int x,y;
	boolean movingX;
	boolean movingY;
	Rectangle rect;


	public Block(int x,int y){
		this.x = x;
		this.y = y;
		movingX = false;
		movingY = false;
	}

	public void move(){
		x--;
		if (x==-200){
			//x+=4000;
		}
	}

	public Rectangle getRect(){
		rect = new Rectangle(x, y, 200,50);
		return rect;

	}

/*	public makeMovingX(){
		movingX = true;
	}

	public makeMovingY(){
		movingY = true;
	}
*/


}