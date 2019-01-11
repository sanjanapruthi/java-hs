import java.awt.Rectangle;

public class Base {

	int x;
	int y;
	int width;
	int height;
	
	public Base(int x, int y) {
		height = 10;
		width = 250;
		this.x = x;
		this.y = y;
		
	}
	
public Rectangle getBorder() {
		
		return new Rectangle(x,y,width,height);
	}
	
	
}
