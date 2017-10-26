package jogame.main;

import java.awt.Graphics;
import java.util.ArrayList;

public class CheckBoxHandler{
	
	private ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();
	
	public CheckBoxHandler() {
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < boxes.size(); i++) {
			boxes.get(i).render(g);
		}
	}
	
	public void add(CheckBox box) {
		boxes.add(box);
	}
}
