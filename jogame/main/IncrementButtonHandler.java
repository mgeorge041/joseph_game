package jogame.main;

import java.awt.Graphics;
import java.util.ArrayList;

public class IncrementButtonHandler {
	
	private ArrayList<IncrementButton> inc_buttons = new ArrayList<IncrementButton>();
	
	public IncrementButtonHandler() {
		
	}
	
	public void add(IncrementButton inc_button) {
		inc_buttons.add(inc_button);
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < inc_buttons.size(); i++) {
			inc_buttons.get(i).render(g);
		}
	}

	public ArrayList<IncrementButton> getIncButtons(){
		return inc_buttons;
	}
	
	public void clearCounts() {
		for(int i = 0; i < inc_buttons.size(); i++) {
			inc_buttons.get(i).setCount(0);
		}
	}
	
}
