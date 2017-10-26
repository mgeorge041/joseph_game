package jogame.main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

public class Tile extends MouseAdapter{
	
	//tile parents
	private TileHand hand;
	private Player player;
	private TileType tile;
	
	//Variables for moving tiles
	private int x;
	private int y;
	private int last_x;
	private int last_y;
	private int start_x;
	private int start_y;
	private boolean clicked = false;
	private boolean over = false;
	private boolean dragging = false;
	private BoardSpot target_spot;
	
	//tile size and position
	private Path2D path = new Path2D.Double();
	private Path2D start_path = new Path2D.Double();
	private Path2D magnify_path = new Path2D.Double();
	private int side;
	private int hand_pos;
	private boolean in_hand = true;
	
	public Tile(TileType tile, TileHand hand, int side){
		this.tile = tile;
		this.player = hand.getPlayer();
		this.hand = hand;
		this.side = side;
		
		updatePath(magnify_path, Window.act_width/4*3, (int)(Window.act_width/8*Math.sqrt(3)/2), Window.act_width/8);
		
		//Adds mouse listener
		hand.getPlayer().getScene().addMouseListener(this);
		hand.getPlayer().getScene().addMouseMotionListener(this);
	}
	
	//Sets tile position and updates bounds
	public void updatePos(int new_width) {
		side = new_width / 2;
		x = hand_pos * side * 2 + 10;
		y = Window.act_height / 10 * 9;
		start_x = x;
		start_y = y;
		updatePath(start_path, start_x, start_y, side);
		updatePath(path, x, y, side);
	}
	
	public void updatePath(Path2D update_path, int x, int y, int side) {
		update_path.reset();
		update_path.moveTo(x, y);
		update_path.lineTo(x + side / 2, y - side * Math.sqrt(3)/2);
		update_path.lineTo(x + side * 3/2, y - side * Math.sqrt(3)/2);
		update_path.lineTo(x + side * 2, y);
		update_path.lineTo(x + side * 3/2, y + side * Math.sqrt(3)/2);
		update_path.lineTo(x + side / 2, y + side * Math.sqrt(3)/2);
		update_path.lineTo(x, y);
	}
	
	//If mouse moves over tile
	public void mouseMoved(MouseEvent e) {
		if(in_hand) 
			mouseOver(e.getPoint());
	}
	
	//If mouse is pressed on tile
	public void mousePressed(MouseEvent e) {
		if(in_hand && over) {
			clicked = true;
			last_x = e.getX();
			last_y = e.getY();
		}
	}

	//Moves tile with mouse while dragging
	public void mouseDragged(MouseEvent e) {
		if(clicked) {
			dragging = true;
			x += e.getX() - last_x;
			y += e.getY() - last_y;
			last_x = e.getX();
			last_y = e.getY();
		}
	}
	
	//When mouse is released, move tile based off final location
	public void mouseReleased(MouseEvent e) {
		if(dragging) {				
			//Sees if mouse is over a board spot
			Point p = new Point(e.getPoint());
			mouseOverSpot(p);
			if(target_spot != null) {
				if(!target_spot.hasTile() && target_spot.getHighlight()) {
					target_spot.setTile(tile);
					hand.remove(this);
				}
			//If not over spot, return tile to start position in hand
			}
			x = start_x;
			y = start_y;
			over = false;
		}
		clicked = false;
		dragging = false;
	}
	
	//If mouse is over tile
	public void mouseOver(Point p) {
		if(path.contains(p))
			over = true;
		else
			over = false;
	}
	
	//If mouse is over tile
	public void mouseOverSpot(Point p) {
		if(player.getScene().getBoard().getSpotHandler().mouseOver(p))
			target_spot = player.getScene().getBoard().getSpotHandler().getOverSpot();
		else
			target_spot = null;
	}
	
	//Sets tile to essentially null
	public void clearTile() {
		x = 0;
		y = 0;
		start_x = 0;
		start_y = 0;
		path.reset();
	}
	
	//Renders the tile
	public void render(Graphics g) {
		//Prevents tile from being dragged off screen
		x = Game.clamp(x, 0, Window.act_width/4*3-2*side);
		y = Game.clamp(y, (int)(side*Math.sqrt(3)/2), (int)(Window.act_height-side*Math.sqrt(3)/2));
		
		//Sets tile bounds
		updatePath(path, x, y, side);
	
		//If tile is still in hand, render tile
		if(in_hand == true) {
			tile.render(g, start_x, start_y, side, start_path);
			tile.render(g, x, y, side, path);
			if(over) {
				tile.magnifyTile(g, Window.act_width/4*3, (int)(Window.act_width/8*Math.sqrt(3)/2), Window.act_width/8, magnify_path);
				tile.highlight(g, x, y, side, path);
			}
		}
	}
		
	//Sets hand position of tile
	public void setHandPos(int pos) {
		this.hand_pos = pos;
	}
	
	//Returns hand position of tile
	public int getHandPos() {
		return hand_pos;
	}
	
	//Gets player number
	public int getPlayerNum() {
		return player.getPlayerNum();
	}
	
	public TileType getTile() {
		return tile;
	}
	
	public String getName() {
		return tile.getName();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getStartX() {
		return start_x;
	}
	
	public int getStartY() {
		return start_y;
	}
	
	public int getWidth() {
		return side * 2;
	}
	
	public int getHeight() {
		return (int)(side * Math.sqrt(3));
	}
}
