package gfx;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector2f;

public class CameraController {

	public Vector2f position;

	public CameraController(float x, float y) {
		position = new Vector2f();
		position.x = x;
		position.y = y;
	}

	public void moveUp(float ammount) {
		position.y -= ammount;
	}
	
	public void moveDown(float ammount){
		position.y += ammount;
	}
	
	public void moveLeft(float ammount){
		position.x += ammount;
	}
	
	public void moveRight(float ammount){
		position.x -= ammount;
	}

	public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(0, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(0, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, -0.0001f);
    }


}
