package levels;

import gfx.BackgroundLayer;

import java.util.ArrayList;

public abstract class Level extends Renderable {

	public ArrayList<BackgroundLayer> backgrounds = new ArrayList<BackgroundLayer>();
	public int palleteNumber;
	
	public Level(ArrayList<BackgroundLayer> backgrounds, int palleteNumber) {
		super(1);
		
	}
	
}
