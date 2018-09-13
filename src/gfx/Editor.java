package gfx;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import resources.AppConfiguration;
import resources.FileStream;

public class Editor {
	private static final int SUBDIVISION_SIZE = 64;
	public static int WIDTH = 1024;
	public static int HEIGHT = 896;
	private int auxX, auxY;
	private float inputCharX, inputCharY;
	/** HUD text drawer position */
	public static int posY = 633, posX = 17, space = 8;
	/** Window size */
	public static int width = 768, height = 480;
	private int aspectW, aspectH;
	/** Font texture */
	private Texture text, grid_cursor, cursor;
	private Vector2f bg_position, cursor_position, grid_cursor_position;
	private ArrayList<BackgroundLayer> bg_main;
	private float dt;
	private float movementSpeed = 5f;
	private CameraController camera;
	
	// DrawnWord testX = new DrawnWord("MOVE X:", 36, new Vector2f(60,
	// HEIGHT-60), new Vector4f(1, 0, 0, 1));
	// DrawnWord testY = new DrawnWord("     Y:", 36, new Vector2f(60,
	// HEIGHT-120), new Vector4f(1, 0, 0, 1));
	ArrayList<DrawnWord> hud = new ArrayList<DrawnWord>();
	private float sensitivity = 0.75f;

	public void inicia() {
		readWindowAttributes();
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setLocation(
					(Display.getDesktopDisplayMode().getWidth() / 2)
							- (width / 2), 30);
			Display.create();
			Display.setResizable(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		text = loadTexture("text");
		grid_cursor = loadTexture("images/fg/grid_cursor");
		cursor = loadTexture("images/fg/cursor_1");
		grid_cursor_position = new Vector2f(0, 0);
		cursor_position = new Vector2f(0, 0);
		camera = new CameraController(0, 0);
		bg_position = new Vector2f(camera.position.x, camera.position.y);
		bg_main = new ArrayList<BackgroundLayer>();
		bg_main.add(new BackgroundLayer(bg_position, new Vector4f(0, 0, WIDTH,
				WIDTH * 1.5f), new Vector4f(0.0f, 0.35f, 0.65f, 1f),
				loadTexture("images/bg/mhz_4"), 0.0002f, 0.0005f));
		bg_main.add(new BackgroundLayer(bg_position, new Vector4f(0, 0, WIDTH,
				WIDTH * 1.5f), new Vector4f(0.0f, 0.35f, 0.65f, 1f),
				loadTexture("images/bg/mhz_3"), 0.0003f, 0.0005f));
		bg_main.add(new BackgroundLayer(bg_position, new Vector4f(0, 0, WIDTH,
				WIDTH * 1.5f), new Vector4f(0.0f, 0.35f, 0.65f, 1f),
				loadTexture("images/bg/mhz_2"), 0.0004f, 0.0005f));
		bg_main.add(new BackgroundLayer(bg_position, new Vector4f(0, 0, WIDTH,
				WIDTH * 1.5f), new Vector4f(0.0f, 0.35f, 0.65f, 1f),
				loadTexture("images/bg/mhz_1"), 0.0005f, 0.0005f));
		hud.add(new DrawnWord("SCORE ", 40,
				new Vector2f(40, HEIGHT - 80), new Vector4f(1, 1, 0, 1), new Vector2f(0, 0)));
		hud.add(new DrawnWord("1234567890", 40,
				new Vector2f(180, HEIGHT - 80), new Vector4f(1, 1, 1, 1), new Vector2f(0, 0)));
		hud.add(new DrawnWord("TIME ", 40,
				new Vector2f(40, HEIGHT - 120), new Vector4f(1, 1, 0, 1), new Vector2f(0, 0)));
		hud.add(new DrawnWord("0:12", 40,
				new Vector2f(160, HEIGHT - 120), new Vector4f(1, 1, 1, 1), new Vector2f(0, 0)));
		hud.add(new DrawnWord("RINGS", 40,
				new Vector2f(40, HEIGHT - 160), new Vector4f(1, 1, 0, 1), new Vector2f(0, 0)));
		hud.add(new DrawnWord("999", 40,
				new Vector2f(180, HEIGHT - 160), new Vector4f(1, 1, 1, 1), new Vector2f(0, 0)));
		iniciarGL();
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mouse.setGrabbed(true);
		while (!Display.isCloseRequested()) {
			// Desenha OpenGL aqui!
			renderGL();
			update();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

	private void readWindowAttributes() {
		AppConfiguration configuration = FileStream.loadConfig();
		String aspect = configuration.aspect;
		aspectW = Integer.parseInt(configuration.aspect.split(":")[0]);
		aspectH = Integer.parseInt(configuration.aspect.split(":")[1]);
		width = Integer.parseInt(configuration.resolution.split("x")[0]);
		height = Integer.parseInt(configuration.resolution.split("x")[1]);
		if (aspect.contains("4:3")) {
			WIDTH = 1024;
			HEIGHT = 768;
		} else if (aspect.contains("5:4")) {
			WIDTH = 960;
			HEIGHT = 768;
		} else if (aspect.contains("8:7")) {
			WIDTH = 1024;
			HEIGHT = 896;
		} else if (aspect.contains("16:9")) {
			WIDTH = 1024;
			HEIGHT = 576;
		} else if (aspect.contains("16:10")) {
			WIDTH = 1024;
			HEIGHT = 640;
		}
	}

	private Texture loadTexture(String textureName) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(
					new File("res/" + textureName + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void iniciarGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
	}

	private void enableTransparency() {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	}

	private void disableTransparency() {
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

	private void renderGL() {
		clearCanvas();
		draw_SCENE_TEST();
		drawGridCursor();
		drawCursor();
	}

	private void drawCursor() {
		Color.white.bind();
		cursor.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(cursor_position.x, cursor_position.y);
		glTexCoord2f(0, 1f);
		glVertex2f(cursor_position.x, cursor_position.y - 32);
		glTexCoord2f(1f, 1f);
		glVertex2f(cursor_position.x + 32f, cursor_position.y - 32);
		glTexCoord2f(1f, 0);
		glVertex2f(cursor_position.x + 32f, cursor_position.y);
		glEnd();
	}

	private void drawGridCursor() {
		Color.white.bind();
		grid_cursor.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(1f, 0);
		glVertex2f(grid_cursor_position.x + 64f, grid_cursor_position.y + 64f);
		glTexCoord2f(0, 0);
		glVertex2f(grid_cursor_position.x, grid_cursor_position.y + 64f);
		glTexCoord2f(0, 1f);
		glVertex2f(grid_cursor_position.x, grid_cursor_position.y);
		glTexCoord2f(1f, 1f);
		glVertex2f(grid_cursor_position.x + 64f, grid_cursor_position.y);
		glEnd();
	}

	private void draw_SCENE_TEST() {
		for (int i = 0; i < bg_main.size(); i++) {
			drawBackground(bg_main.get(i));
		}
		disableTransparency();
		glColor4f(1, 0, 0, 1);
		for (int i = 0; i < WIDTH; i += SUBDIVISION_SIZE) {
			glBegin(GL_LINES);

			glVertex2f(i, 0);
			glVertex2f(i, HEIGHT);

			glEnd();
		}
		for (int i = 0; i < HEIGHT; i += SUBDIVISION_SIZE) {
			glBegin(GL_LINES);

			glVertex2f(0, i);
			glVertex2f(WIDTH, i);

			glEnd();
		}
		enableTransparency();
		//drawString(testX);
		for(int i = 0; i < hud.size(); i++){
			drawString(hud.get(i));
		}
		Color.white.bind();
		text.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0.99f, 0);
		glVertex2f(200, 200);
		glTexCoord2f(0, 0);
		glVertex2f(20, 200);
		glTexCoord2f(0, 0.99f);
		glVertex2f(20, 20);
		glTexCoord2f(0.99f, 0.99f);
		glVertex2f(200, 20);
		glEnd();
	}

	private void drawBackground(BackgroundLayer bg) {
		enableTransparency();
		Color.white.bind();
		bg.texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(bg.texture_positioning.z, bg.texture_positioning.y);
		glVertex2f(bg.position.x + bg.dimensions.z, bg.position.y
				+ bg.dimensions.w);
		glTexCoord2f(bg.texture_positioning.x, bg.texture_positioning.y);
		glVertex2f(bg.position.x + bg.dimensions.x, bg.position.y
				+ bg.dimensions.w);
		glTexCoord2f(bg.texture_positioning.x, bg.texture_positioning.w);
		glVertex2f(bg.position.x + bg.dimensions.x, bg.position.y
				+ bg.dimensions.y);
		glTexCoord2f(bg.texture_positioning.z, bg.texture_positioning.w);
		glVertex2f(bg.position.x + bg.dimensions.z, bg.position.y
				+ bg.dimensions.y);
		glEnd();
	}

	private void drawString(DrawnWord word) {
		Vector2f pos = new Vector2f(word.position.x + word.translation.x, word.position.y + word.translation.y);
		drawString(pos, word.word, word.size, word.color);
	}

	private void clearCanvas() {
		glClearColor(0.0f, 0.0f, 0.0f, 1f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	private void drawString(Vector2f position, String string, int wordWidth,
			Vector4f color) {

		float auxWid = position.x;
		glColor4f(color.x, color.y, color.z, color.w);

		text.bind();

		for (char c : string.toCharArray()) {

			glBegin(GL_QUADS);

			if (c == '\n') {
				position.x = auxWid;
				position.y -= wordWidth + (wordWidth / 3);
			} else {
				auxX = c % 16;
				auxY = c / 16;
				inputCharX = auxX;
				inputCharY = auxY;

				glTexCoord2f((inputCharX / 16f), ((inputCharY + 1f) / 16f));
				glVertex2f(position.x , position.y);
				glTexCoord2f(((inputCharX + 1f) / 16f), ((inputCharY + 1f) / 16f));
				glVertex2f(position.x + wordWidth, position.y);
				glTexCoord2f(((inputCharX + 1f) / 16f), (inputCharY / 16f));
				glVertex2f(position.x  + wordWidth,
						position.y + wordWidth);
				glTexCoord2f((inputCharX / 16f), (inputCharY / 16f));
				glVertex2f(position.x , position.y + wordWidth);

				position.x += wordWidth
						- ((c == 'M') ? 8
								: ((c == 'W') ? 9
										: ((c == '0') ? 18
												: ((c == 'L') ? 24
														: ((c == 'I') ? 28
																:((c == 'R') ? 18
																: ((c == '1')
																		|| (c == 'F') || (c == ':') ? 22
																		: ((c == 'T')
																				|| (c == 'P') ? 20
																				:((c == 'B')|| (c == 'D')|| (c == 'O') ? 18
																						: 16)))))))));
			}

			glEnd();

		}
		position.x = auxWid;

	}

	public void update() {
		updateKeyboard();
		updateMouse();
		updateCamera();
		//updateControllers();
		updateWindowState();
	}

	private void updateKeyboard() {
		dt = 2f;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Sys.alert("Close",
					"To continue, press ESCAPE on your keyboard or OK on the screen.");
			Display.destroy();
			System.exit(0);
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
				&& bg_main.get(bg_main.size() - 1).texture_positioning.y > -0.27f)// move up
		{
			camera.moveUp(movementSpeed * dt);
			for (int i = 0; i < bg_main.size(); i++) {
				bg_main.get(i).deslocate(0, -movementSpeed);
			}
			cursor_position.y += movementSpeed * dt;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) && camera.position.y < 0)// move down
		{
			camera.moveDown(movementSpeed * dt);
			for (int i = 0; i < bg_main.size(); i++) {
				bg_main.get(i).deslocate(0, movementSpeed);
			}
			cursor_position.y += -movementSpeed * dt;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))// move left
		{
			camera.moveLeft(movementSpeed * dt);
			for (int i = 0; i < bg_main.size(); i++) {
				bg_main.get(i).deslocate(-movementSpeed, 0);
			}
			cursor_position.x += -movementSpeed * dt;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))// move right
		{
			camera.moveRight(movementSpeed * dt);
			for (int i = 0; i < bg_main.size(); i++) {
				bg_main.get(i).deslocate(movementSpeed, 0);
			}
			cursor_position.x += movementSpeed * dt;
		}
	}

	private void updateMouse() {
		grid_cursor_position.x = (float) Math.floor(cursor_position.x/64f) * 64f;
		grid_cursor_position.y = (float) Math.floor(cursor_position.y/64f) * 64f;
		cursor_position.x += ((sensitivity * Mouse.getDX()));
		cursor_position.y += ((sensitivity * Mouse.getDY()));
		
		if (cursor_position.x < -camera.position.x){
			cursor_position.x = -camera.position.x;
		} else if (cursor_position.x >= WIDTH - camera.position.x){
			cursor_position.x = WIDTH - camera.position.x - 2;
		}
		
		if (cursor_position.y < -camera.position.y){
			cursor_position.y = -camera.position.y;
		} else if (cursor_position.y > HEIGHT - camera.position.y - 2){
			cursor_position.y = HEIGHT - camera.position.y - 2;
		}
		
		if (Mouse.isButtonDown(0)){
			System.out.println(Mouse.getDX() + ", " + Mouse.getDY());
		}
	}

	private void updateCamera() {
		glLoadIdentity();
		camera.lookThrough();
		bg_position.x = -camera.position.x;
		bg_position.y = -camera.position.y;
		for(int i = 0; i < hud.size(); i++){
			hud.get(i).translation.x =  -camera.position.x;
			hud.get(i).translation.y =  -camera.position.y;
		}
		
	}

	private void updateWindowState() {
		if (Display.wasResized()) {
			width = Display.getWidth();
			height = Display.getHeight();
			System.out.println(width + " x " + height);
			if (width > height * aspectW / aspectH) {
				glScissor((width - height * aspectW / aspectH) / 2, 0, height
						* aspectW / aspectH, height);
				glViewport((width - height * aspectW / aspectH) / 2, 0, height
						* aspectW / aspectH, height);
			} else {
				glScissor(0, (height - width * aspectH / aspectW) / 2, width,
						width * aspectH / aspectW);
				glViewport(0, (height - width * aspectH / aspectW) / 2, width,
						width * aspectH / aspectW);
			}
		}
	}

	public static void main(String[] argv) {
		Editor exemplo = new Editor();
		exemplo.inicia();
	}
}