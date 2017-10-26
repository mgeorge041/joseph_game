package jogame.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class SceneHandler {
	
	private ArrayList<Scene> scenes = new ArrayList<Scene>();
	
	public SceneHandler() {
	}
	
	public void render() {
		try {
			for(int i = 0; i < scenes.size(); i++) {
				Scene temp = scenes.get(i);
				
				if(temp != null && temp.game_state == Game.game_state && temp.isDisplayable() != false) {
					
					BufferStrategy bs = temp.getBufferStrategy();
					
					//Creates buffer if not already exists
					if(bs == null) {
						//Creates 3 buffers for rendering the window
						temp.createBufferStrategy(3);
						bs = temp.getBufferStrategy();
					}
					
					//Initializes the graphics variable equal to the buffer graphics
					Graphics g = bs.getDrawGraphics();
					
					temp.clearCanvas(g);
					
					temp.render(g);
					
					bs.show();
					g.dispose();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(Scene scene) {
		boolean found = false;
		for(int i = 0; i < scenes.size(); i++) {
			if(scenes.get(i).getTitle() == scene.getTitle()) {
				found = true;
				break;
			}
		}
		if(found == false)
			scenes.add(scene);
	}
	
	public ArrayList<Scene> getScenes(){
		return scenes;
	}
}
