package jogame.main;

import java.awt.Graphics;
import java.util.ArrayList;

public class PieceHandler{

	private ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
	private int units_played;
	private int units_lost;
	
	public void add(GamePiece piece) {
		pieces.add(piece);
		units_played++;
	}
	
	public void render(Graphics g) {
		try {
			for(int i = 0; i < pieces.size(); i++) {
				GamePiece temp = pieces.get(i);
				if(temp != null)				
					temp.render(g);	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		pieces.clear();
	}
	
	public ArrayList<GamePiece> getPieces(){
		return pieces;
	}
	
	public void resetPieces(int player_num) {
		for(int i = 0; i < pieces.size(); i++) {
			if(pieces.get(i).getPlayerNum() == player_num)
				pieces.get(i).resetPiece();
		}
	}
	
	public void remove(GamePiece piece) {
		pieces.remove(piece);
		units_lost++;
	}
	
	public int countMoveable() {
		int moveable = 0;
		for(int i = 0; i < pieces.size(); i++) 
			if(pieces.get(i).getMoveable())
				moveable++;
		return moveable;
	}
	
	public int countAttackable() {
		int attackable = 0;
		for(int i = 0; i < pieces.size(); i++) 
			if(pieces.get(i).getAttackable())
				attackable++;
		return attackable;
	}
	
	public void setClickable(boolean clickable, GamePiece piece) {
		for(int i = 0; i < pieces.size(); i++) {
			if(pieces.get(i) != piece)
				pieces.get(i).setClickable(clickable);
		}
	}
	
	public int getUnitsPlayed() {
		return units_played;
	}
	
	public void setUnitsPlayed(int played) {
		this.units_played = played;
	}
	
	public int getUnitsLost() {
		return units_lost;
	}
	
	public void setUnitsLost(int lost) {
		this.units_lost = lost;
	}
}
