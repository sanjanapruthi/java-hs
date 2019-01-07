public class Wall{
	int[] x,y;
	boolean visible;

	public Wall(int[] x, int[] y, boolean visible){
		this.x = x;
		this.y = y;
		this.visible = visible;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public int[] getX(){
		return x;
	}

	public int[] getY(){
			return y;
	}
	public boolean getVisible(){
		return visible;
	}


}