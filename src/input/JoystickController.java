package input;

import gfx.SceneDrawer;

public class JoystickController implements Runnable {

	SceneDrawer sceneDrawer;
	
	public JoystickController (SceneDrawer sceneDrawer){
		this.sceneDrawer = sceneDrawer;
	}
	
	@Override
	public void run() {
		while(true){
			sceneDrawer.controller.poll();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
