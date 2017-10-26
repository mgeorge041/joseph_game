package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class DeckManager {

	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Integer> counts = new ArrayList<Integer>();
	
	Font fnt = new Font("arial", 1, 30);
	Font fnt2 = new Font("arial", 1, 20);
	private int x;
	private int y;
	private int y_start;
	private int y_final;
	private int padding = 5;
	private int total;
	
	public DeckManager(int x, int y) {
		this.x = x;
		this.y = y;
		y_start = y;
	}
	
	public void update(ArrayList<IncrementButton> inc_buttons) {
		for(int i = 0; i < inc_buttons.size(); i++) {
			String name = inc_buttons.get(i).getName();
			int count = inc_buttons.get(i).getCount();
			boolean found = false;
			for(int j = 0; j < names.size(); j++) {
				if(names.get(j) == name) {
					counts.set(j, count);
					found = true;
					break;
				}
			}
			if(!found) {
				names.add(name);
				counts.add(count);
			}
		}
		total = 0;
		for(int i = 0; i < counts.size(); i++)
			total += counts.get(i);
	}
	
	public void add(String name, int count) {
		boolean found = false;
		for(int i = 0; i < names.size(); i++) {
			if(names.get(i) == name) {
				counts.set(i, count);
				found = true;
				break;
			}
		}
		if(!found) {
			names.add(name);
			counts.add(count);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.setFont(fnt);
		y += Game.getStringHeight(fnt);
		g.drawString("Deck Contents", x, y);
		y+= Game.getStringHeight(fnt2) + padding;
		g.setFont(fnt2);
		g.drawString("Card", x, y);
		int string_width = Game.getStringWidth("Archery Range ", fnt2) * 5 / 4;
		int string_height = Game.getStringHeight(fnt2);
		g.drawString("Count", x + string_width, y);
		for(int i = 0; i < names.size(); i++) {
			if(counts.get(i) != 0) {
				y += string_height + padding;
				g.drawString(names.get(i), x, y);
				g.drawString(String.valueOf(counts.get(i)), x + string_width, y);
			}
		}
		y += string_height + padding;
		g.drawString("Total", x, y);
		if(total != 60)
			g.setColor(Color.red);
		else
			g.setColor(new Color(0, 200, 0));
		g.drawString(String.valueOf(total), x + string_width, y);
		y_final = y;
		y = y_start;
	}
	
	public ArrayList<String> getNames(){
		return names;
	}
	
	public ArrayList<Integer> getCounts(){
		return counts;
	}
	
	public void clear() {
		names.clear();
		counts.clear();
	}
	
	public boolean isComplete() {
		if(total == 60)
			return true;
		else
			return false;
	}
	
	public int getBottom() {
		return y_final;
	}
}
