import java.awt.Rectangle;

public class Carrot {

	int x;
	int y;
	
	Carrot(int x, int y){
		this.x = x;
		this.y = y;
		
	}
	
	public Rectangle getBorder() {
		
		return new Rectangle(x,y,30,30);
	}
	
	
	
}
