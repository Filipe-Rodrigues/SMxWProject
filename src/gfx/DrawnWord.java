package gfx;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

public class DrawnWord {
	public String word;
	public int size;
	public Vector2f position;
	public Vector2f translation;
	public Vector4f color;
	
	public DrawnWord(String word, int size, Vector2f position, Vector4f color, Vector2f translation) {
		super();
		this.word = word;
		this.size = size;
		this.position = position;
		this.color = color;
		this.translation = translation;
	}
	
	
}
