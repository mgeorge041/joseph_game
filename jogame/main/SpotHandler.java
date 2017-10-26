package jogame.main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class SpotHandler {

	private ArrayList<BoardSpot> spots = new ArrayList<BoardSpot>();
	private BoardSpot over_spot = null;
	
	public SpotHandler() {
		
	}
	
	//Adds a new spot to the list
	public void add(BoardSpot spot) {
		spots.add(spot);
	}
	
	//Renders all spots
	public void render(Graphics g) {
		for(int i = 0; i < spots.size(); i++)
			spots.get(i).render(g);
	}
	
	//Clears the list of spots
	public void clear() {
		spots.clear();
	}
	
	//Sees if the mouse is over a spot
	public boolean mouseOver(Point p) {
		for(int i = 0; i < spots.size(); i++) {
			BoardSpot spot = spots.get(i);
			spot.mouseOver(p);
			if(spot.getOver()) {
				over_spot = spot;
				return true;
			}
		}
		return false;
	}
	
	//Returns the spot using the spot's coordinates
	public BoardSpot getSpot(int coord[]) {
		for(int i = 0; i < spots.size(); i++) {
			BoardSpot temp = spots.get(i);
			if(temp.getCoords()[0] != coord[0])
				continue;
			else if(temp.getCoords()[1] != coord[1])
				continue;
			else if(temp.getCoords()[2] != coord[2])
				continue;
			else
				return temp;
		}
		return null;
	}
	
	//Returns the spot the mouse is currently over
	public BoardSpot getOverSpot() {
		return over_spot;
	}

	// Clears all targeting of all board spots
	public void stopHighlightAllTargets() {
		for(int i = 0; i < spots.size(); i++) {
			spots.get(i).setTargeted(false); 
		}
	}
	
	public void highlightTiles(int player_num) {
		for(int i = 0; i < spots.size(); i++) {
			BoardSpot temp = spots.get(i);
			if(player_num == 1) {
				if(temp.getCoords()[1] < 0 || (temp.getCoords()[1] == 0 && temp.getCoords()[2] > 0))
					temp.setHighlight(true);
				else
					temp.setHighlight(false);
			}else {
				if(temp.getCoords()[1] > 0 || (temp.getCoords()[1] == 0 && temp.getCoords()[2] < 0))
					temp.setHighlight(true);
				else
					temp.setHighlight(false);
			}
		}
	}
	public void clearHighlightTiles() {
		for(int i = 0; i < spots.size(); i++)
			spots.get(i).setHighlight(false);
	}
}
