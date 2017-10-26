package jogame.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class BoardSpot extends MouseAdapter{
	
	//Center point and outside border path
	private Path2D path = new Path2D.Double();
	private Point center = new Point();
	
	//Variables for mousing over and piece recognition
	private boolean over = false;
	private boolean targeted;
	private boolean highlight = false;
	private GamePiece piece;
	private TileType tile;
	private double top;
	private double left;
	private int coord[];
	private int neighbors[][] = {
			{0, 1, -1}, {-1, 1, 0}, {-1, 0, 1}, 
			{0, -1, 1}, {1, -1, 0}, {1, 0, -1} 
	};
	
	private GameBoard board;

	public BoardSpot(double side, int hex_num, int coord[], GameBoard board) {
		this.board = board;
		this.coord = new int[3];
		for(int i = 0; i < coord.length; i++)
			this.coord[i] = coord[i];
		
		double map_center_x = board.getScene().getWindow().getWidth()/4*3/2;
		double map_center_y = board.getScene().getWindow().getHeight()/5*4/2;
		
		center.setLocation(coord[0] * side * 3 / 2 + map_center_x, (-coord[1] + coord[2]) * side * Math.sqrt(3) / 2 + map_center_y);
		left = center.getX() - side;
		top = center.getY() - side * Math.sqrt(3) / 2;
		
		path.moveTo(left, center.getY());
		path.lineTo(left + side / 2, center.getY() - side * Math.sqrt(3) / 2);
		path.lineTo(left + side * 3/2, center.getY() - side * sqrt(3)/2);
		path.lineTo(left + side * 2, center.getY());
		path.lineTo(left + side * 3/2, center.getY() + side * sqrt(3)/2);
		path.lineTo(left + side / 2, center.getY() + side * sqrt(3)/2);
		path.lineTo(left, center.getY());
		
		tile = new TileType("tile");
		
		//Adds mouse listener
		board.getScene().addMouseMotionListener(this);
	}
	
	//Returns the square root
	private double sqrt(double i) {
		return Math.sqrt(i);
	}
	
	//If mouse over spot
	public void mouseMoved(MouseEvent e) {
		mouseOver(e.getPoint());
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseOver(e.getPoint());
	}
	
	//If mouse spot within spot bounds
	public void mouseOver(Point p) {
		if(path.contains(p))
			over = true;
		else
			over = false;
	}
	
	//Returns if over spot
	public boolean getOver() {
		return over;
	}
	
	//Returns spot center
	public Point getCenter() {
		return center;
	}

	//Renders the spot
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		//Draw spot tile
		g2d.setClip(path);
		g2d.drawImage(tile.getImage(), (int)left, (int)top, (int)board.getSpotWidth() + 1, (int)board.getSpotHeight(), null);
		
		
		//If spot is being targeted, darker red fill
		if(targeted) {
			g2d.setColor(Color.red);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g2d.fill(path);
		}
		//If mouse is over spot and the spot is not currently targeted, light red fill
		else if(over) {
			g2d.drawImage(tile.getHLImage(), (int)left, (int)top, (int)board.getSpotWidth() + 1, (int)board.getSpotHeight(), null);
			//g2d.setColor(Color.yellow);
		//	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			//g2d.fill(path);
		}
		if(highlight) {
			g2d.drawImage(tile.getHLGImage(), (int)left, (int)top, (int)board.getSpotWidth() + 1, (int)board.getSpotHeight(), null);
		}

		g2d.setClip(null);
		
		//Draw outside border
		g2d.setColor(Color.black);
		g2d.draw(path);
		g2d.drawString(coord[0] + ", " + coord[1] + ", " + coord[2], (int)center.getX(), (int)center.getY());
	}
	
	//Returns spot piece
	public GamePiece getPiece() {
		return piece;
	}
	
	//Sets spot piece
	public void setPiece(GamePiece piece) {
		this.piece = piece;
	}
	
	//Sets targeting of spot
	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}
	
	//Returns whether spot is targeted
	public boolean getTargeted() {
		return targeted;
	}
	
	public GameBoard getBoard() {
		return board;
	}
	
	public void setTile(TileType tile) {
		this.tile = tile;
	}
	
	public boolean hasTile() {
		if(!tile.getName().equals("tile")) 
			return true;
		else
			return false;
	}
	
	//Sees if spot is neighbor to current spot
	public boolean isNeighbor(int coords[]) {
		int dx = Math.abs(coord[0] - coords[0]);
		int dy = Math.abs(coord[1] - coords[1]);
		int dz = Math.abs(coord[2] - coords[2]);
		if((dx + dy + dz) / 2 == 1)
			return true;
		return false;
	}
	
	/*
	//Returns neighbor spot
	public BoardSpot getNeighborSpot(int neighbor) {
		BoardSpot spot = board.getSpotHandler().getSpot(getNeighborCoords(neighbor, this));
		return spot;
	}
	
	public ArrayList<BoardSpot> getNeighborSpots(int spot){
		return null;
	}*/
		
	//Gets coordinates of neighbor spot
	public int[] getNeighborCoords(int neighbor, BoardSpot spot) {
		int temp[] = new int[3];
		for(int i = 0; i < 3; i++) 
			temp[i] = spot.getCoords()[i] + neighbors[neighbor][i];
		return temp;
	}
	
	//Sets targeted spot for damage
	public void setTargetNeighbor(BoardSpot spot, int range, int player_num) {
		if(range < 0 || spot == null)
			return;
		for(int i = 0; i < 6; i++) {
			setTargetNeighbor(board.getSpotHandler().getSpot(getNeighborCoords(i, spot)), range - 1, player_num);
		}
		if(spot.getPiece() != null)
			if(spot.getPiece().getPlayerNum() != player_num)
				spot.setTargeted(true);
	}
	
	//Clears targeting of spot for damage
	public void clearTargetNeighbor(BoardSpot spot, int range) {
		if(range < 0 || spot == null)
			return;
		for(int i = 0; i < 6; i++) {
			clearTargetNeighbor(board.getSpotHandler().getSpot(getNeighborCoords(i, spot)), range - 1);
		}
		if(spot.getTargeted())
			spot.setTargeted(false);
	}
	
	//Returns specific piece from neighbor spot
	public GamePiece getNeighborPiece(String piece) {
		for(int i = 0; i < 6; i++) {
			BoardSpot spot = board.getSpotHandler().getSpot(getNeighborCoords(i, this));
			if(spot != null && spot.getPiece() != null) {
				if(spot.getPiece().getName().equals(piece))
					return spot.getPiece();
			}
		}
		return null;
	}
	
	public int[] getCoords() {
		return coord;
	}
	
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	
	public boolean getHighlight() {
		return highlight;
	}
}
