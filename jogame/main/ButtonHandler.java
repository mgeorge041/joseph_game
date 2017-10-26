package jogame.main;

import java.awt.Graphics;
import java.util.ArrayList;

public class ButtonHandler{

	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public ButtonHandler() {
		
	}
	
	//Adds button to handler
	public void add(Button button) {
		buttons.add(button);
	}
	
	//Removes button from handler
	public void remove(Button button) {
		buttons.remove(button);
	}
	
	//Returns list of buttons
	public ArrayList<Button> getButtons() {
		return buttons;
	}
	
	//Renders buttons in handler
	public void render(Graphics g) {
		try {
			for(int i = 0; i < buttons.size(); i++) {
				buttons.get(i).render(g);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
