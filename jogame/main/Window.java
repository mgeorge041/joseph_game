package jogame.main;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window{
	
	//Content pane size
	public static int act_height;
	public static int act_width;
	private Game game;

	//Creates jframe for the game
	private JFrame frame = new JFrame();	
	
	private SceneHandler scene_handler = new SceneHandler();
	
	public Window(int width, int height, String title, Game game) {
		
		this.game = game;
		
		//Sets frame title
		frame.setTitle(title);
		
		//Sets specs for window size
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
	
		//Closes the window when click on X
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Prevents resizing of the window
		frame.setResizable(false);
		
		//Makes window start in middle of screen
		frame.setLocationRelativeTo(null);
		
		//Sets frame visible
		frame.setVisible(true);
		
		//Finds actual content window size
		act_height = frame.getContentPane().getHeight();
		act_width = frame.getContentPane().getWidth();
		
		scene_handler.add(new MenuScene(act_width, act_height, "Joseph's Game Menu", this));
		frame.add(scene_handler.getScenes().get(0));
		
		//ImageIcon i = new ImageIcon("images/mouse.png");
		//Image mouse = i.getImage();
		//Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(mouse, new Point(frame.getX(), frame.getY()), "img");
		//frame.setCursor(c);
	}
	
	public Game getGame() {
		return game;
	}
	
	public int getWidth() {
		return act_width;
	}
	
	public int getHeight() {
		return act_height;
	}
	
	public SceneHandler getSceneHandler() {
		return scene_handler;
	}
	
	public void addScene(Scene scene) {
		scene_handler.add(scene);
	}
	
	public void setScene(String title) {
		frame.add(getScene(title), 0);
	}
	
	public Scene getScene(String title) {
		Scene temp;
		for(int i = 0; i < scene_handler.getScenes().size(); i++) {
			temp = scene_handler.getScenes().get(i);
			if(temp != null)
				if(temp.getTitle() == title)
					return temp; 
		}
		return null;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void render() {
		scene_handler.render();
	}
}
