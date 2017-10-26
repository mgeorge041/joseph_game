package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileManager {

	private ArrayList<String> tiles = new ArrayList<String>();
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Integer> counts = new ArrayList<Integer>();
	private int total;
	private int padding = 5;
	private int next_tile = 0;
	
	Font fnt = new Font("arial", 1, 30);
	Font fnt2 = new Font("arial", 1, 20);

	
	public TileManager() {
		//Reads through CSV file to find all card types and stores in array
		String file = "files/tiles.csv";
		String line = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			line = br.readLine();
			while((line = br.readLine()) != null) {
				tiles.add(line);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getTile(String name) {
		for(int i = 0; i < tiles.size(); i++) 
			if(tiles.get(i).equals(name)) {
				return tiles.get(i);
			}
		return null;
	}
	
	public String getNextTile() {
		if(next_tile <= tiles.size()) {
			next_tile++;
			return tiles.get(next_tile--);
		}
		return "";
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
	
	public void render(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.setFont(fnt);
		int y_start = y;
		y += Game.getStringHeight(fnt);
		g.drawString("Tiles", x, y);
		y+= Game.getStringHeight(fnt2) + padding;
		g.setFont(fnt2);
		g.drawString("Tile", x, y);
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
		y = y_start;
	}
	
	public ArrayList<String> getNames(){
		return names;
	}
	
	public ArrayList<String> getTiles(){
		return tiles;
	}
}
