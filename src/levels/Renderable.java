package levels;

public abstract class Renderable {
	
	public static final short TYPE_MENU = 0;
	public static final short TYPE_LEVEL = 1;
	
	public int mode;
	
	public Renderable (int mode){
		this.mode = mode;
	}
	
}
