package gfx;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

public class BackgroundLayer {

	public Vector2f position;
	public Vector4f dimensions;
	public Vector4f texture_positioning;
	public float movement_multiplier_x;
	public float movement_multiplier_y;
	
	public Texture texture;

	public BackgroundLayer(Vector2f position, Vector4f dimensions, Vector4f texture_positioning,
			Texture text, float movement_multiplier_x, float movement_multiplier_y) {
		super();
		this.position = position;
		this.dimensions = dimensions;
		this.texture_positioning = texture_positioning;
		this.texture = text;
		this.movement_multiplier_x = movement_multiplier_x;
		this.movement_multiplier_y = movement_multiplier_y;
	}
	
	public void deslocate(float ammount_X, float ammount_Y){
		if(texture_positioning.x > 1.0f){
			texture_positioning.x -= 1.0f;
			texture_positioning.z -= 1.0f;
		}
		else if(texture_positioning.x < -1.0f){
			texture_positioning.x += 1.0f;
			texture_positioning.z += 1.0f;
		}
//		if(texture_positioning.y > 1.0f){
//			texture_positioning.y -= 1.0f;
//			texture_positioning.w -= 1.0f;
//		}
//		else if(texture_positioning.y < -1.0f){
//			texture_positioning.y += 1.0f;
//			texture_positioning.w += 1.0f;
//		}
		texture_positioning.x += ammount_X * movement_multiplier_x;
		texture_positioning.z += ammount_X * movement_multiplier_x;
		texture_positioning.y += ammount_Y * movement_multiplier_y;
		texture_positioning.w += ammount_Y * movement_multiplier_y;
	}

}
