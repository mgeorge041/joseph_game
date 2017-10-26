package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TileHand{
	
	private Map<Tile, Integer> tiles = new ConcurrentHashMap<Tile, Integer>();

	private Scene scene;
	private Player player;
	
	public TileHand(Scene scene, Player player) {
		this.scene = scene;
		this.player = player;
	}
	
	public void add(Tile tile) {
		if(tile != null) {
			boolean found = false;
			for(Tile key: tiles.keySet()) {
				if(key.getName() == tile.getName()) {
					tiles.replace(key, tiles.get(key) + 1);
					found = true;
					break;
				}
			}
			if(!found) {
				tile.setHandPos(tiles.size());
				tiles.put(tile, 1);
			}
		}
		updateTileWidth();
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 20);
		for(Tile key: tiles.keySet()) {
			key.render(g);
			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("x" + tiles.get(key), (int)(key.getStartX() + key.getWidth()/2 - Game.getStringWidth("x" + tiles.get(key), fnt)/2), 
					(int)(key.getStartY() + key.getHeight()/10*9));
		}
	}
	
	public void remove(Tile tile) {
		for(Tile key: tiles.keySet()) {
			if(key.getName().equals(tile.getName())) 
				tiles.replace(key, tiles.get(key) - 1);
			if(tiles.get(key) == 0) {
				tiles.remove(key);
				updateTileWidth();
				key.clearTile();
			}
		}
	}
	
	public void updateTileWidth() {
		int new_width = 0;
		if(tiles.size() > 8)
			new_width = Window.act_width / 4 * 3 / tiles.size() - 5;
		else
			new_width = Window.act_width / 4 * 3 / 8 - 5;
		for(Tile key: tiles.keySet()) 
			key.updatePos(new_width);
	}
	
	public void clear() {
		for(Tile key: tiles.keySet()) 
			key.clearTile();
		tiles.clear();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public boolean isTurn() {
		return player.isTurn();
	}
	
	public Player getPlayer() {
		return player;
	}
}
