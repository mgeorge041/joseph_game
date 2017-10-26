package jogame.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Path2D;

import javax.swing.ImageIcon;

public class TileType {

	private Image tile_image;
	private Image tile_highlight;
	private Image tile_highlight_green;
	private String name;
	private int max; 
	
	public TileType(String name) {
		this.name = name;
		ImageIcon i = new ImageIcon("images/tile_" + name + ".png");
		tile_image = i.getImage();
		i = new ImageIcon("images/tile_hl.png");
		tile_highlight = i.getImage();
		i = new ImageIcon("images/tile_hlg.png");
		tile_highlight_green = i.getImage();
	}
	
	public Image getImage() {
		return tile_image;
	}
	
	public Image getHLImage() {
		return tile_highlight;
	}
	
	public Image getHLGImage() {
		return tile_highlight_green;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public int getMax() {
		return max;
	}
	
	public void render(Graphics g, int x, int y, int side, Path2D path) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setClip(path);
		g2d.drawImage(tile_image, x, (int)(y - side * Math.sqrt(3)/2), side * 2, (int)(side * Math.sqrt(3)), null);
		g2d.setClip(null);
	}
	
	public void highlight(Graphics g, int x, int y, int side, Path2D path) {
		g.drawImage(tile_highlight, x, (int)(y - side * Math.sqrt(3)/2), side * 2, (int)(side * Math.sqrt(3)), null);
	}
	
	public void magnifyTile(Graphics g, int x, int y, int side, Path2D path) {
		render(g, x, y, side, path);
	}
	
}
