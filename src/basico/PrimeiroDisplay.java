package basico;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;

public class PrimeiroDisplay {

	/** posição do quadrado */
	float x = 400, y = 300;
	/** ângulo de rotação */
	float rotation = 0;
	/** velocidade de rotação*/
	float rSpeed = 0;
	/** Red Green Blue*/
	float r = 1.0f, g = 0.3f, b = 0.3f;
	/** tempo no último frame */
	long lastFrame;
	
	/** frames por segundo */
	int fps;
	/** Tamanho da Janela*/
	int height = 1162, width = 650;
	/** último FPS */
	long lastFPS;
	/**Fonte Unicode*/
	public static UnicodeFont font;

	public void inicia() {
		try {
			Display.setDisplayMode(new DisplayMode(height, width));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		//Inicia OpenGL aqui!
		iniciarGL();
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			// Desenha OpenGL aqui!
			renderGL();
			update(delta);
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private int getDelta() {
		long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    return delta;
	}

	private void iniciarGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, height, 0, width, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	private void renderGL() {
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// R,G,B,A Set The Color To Blue One Time Only
		GL11.glColor3f(r, g, b);

		// draw quad
		GL11.glPushMatrix();
			GL11.glTranslatef(x, y, 0);
			GL11.glRotatef(rotation, 0f, 0f,1f);
			GL11.glTranslatef(-x, -y, 0);
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x - 50, y - 50);
				GL11.glVertex2f(x + 50, y - 50);
				GL11.glVertex2f(x + 50, y + 50);
				GL11.glVertex2f(x - 50, y + 50);
			GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void update(int delta) {

		// change color
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)) r += 0.001f;
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) r -= 0.001f;
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) g += 0.001f;
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) g -= 0.001f;
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9)) b += 0.001f;
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) b -= 0.001f;
		
		if (r < 0) r = 0;
		if (r > 1) r = 1;
		if (g < 0) g = 0;
		if (g > 1) g = 1;
		if (b < 0) b = 0;
		if (b > 1) b = 1;
		
		// rotate quad
		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) rSpeed += 0.001f;
		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) rSpeed -= 0.001f;
		
		rotation += rSpeed * delta;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) x -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) x += 0.35f * delta;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) y -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) y += 0.35f * delta;
		
		// keep quad on the screen
		if (x < 0) x = 0;
		if (x > 800) x = 800;
		if (y < 0) y = 0;
		if (y > 600) y = 600;
		
		updateFPS(); // update FPS Counter
	}

	private void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static void main(String[] argv) {
		PrimeiroDisplay exemplo = new PrimeiroDisplay();
		exemplo.inicia();
	}
}