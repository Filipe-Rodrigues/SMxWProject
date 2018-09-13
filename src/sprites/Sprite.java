package sprites;

import mechanics.Physics;

import org.lwjgl.util.vector.Vector2f;

public abstract class Sprite {
	public int lives;
	public String name;
	public Vector2f position;
	public Physics physics;
}
