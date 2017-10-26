package jogame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IncrementButton extends MouseAdapter {

	private int count = 0;
	private int max;
	private int x;
	private int y;
	private int width;
	private int height;
	private int padding = 10;
	private String name;
	private boolean clickable = true;
	private boolean tile = false;
	private Scene scene;
	
	private Rectangle box1 = new Rectangle();
	private Rectangle box2 = new Rectangle();
	
	public IncrementButton(int x, int y, int width, int height, String name, String class_type, Scene scene){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.scene = scene;
		
		/*if(!name.equals("")) {
			if(class_type.equals("Card"))
				max = scene.getCardTypeHandler().getCard(name).getMaxCount();
			else if(class_type.equals("Tile"))
				max = scene.getTileManager().getTile(name).getMaxCount();
		}*/
		
		scene.addMouseListener(this);
		
		box1.setBounds(x - width / 2 * 3 - padding, y - height / 2, width, height);
		box2.setBounds(x + width / 2 + padding, y - height / 2, width, height);
		
		if(name.equals(""))
			clickable = false;
	}
	
	public void render(Graphics g) {
		if(clickable) {
			g.setColor(Color.black);
			Font fnt = new Font("arial", 1, 20);
			g.setFont(fnt);
			g.drawRect((int)box1.getX(), (int)box1.getY(), (int)box1.getWidth(), (int)box1.getHeight());
			g.drawRect(x - width / 2, y - height / 2, width, height);
			g.drawRect((int)box2.getX(), (int)box2.getY(), (int)box2.getWidth(), (int)box2.getHeight());
			g.drawString(String.valueOf(count), x - Game.getStringWidth(String.valueOf(count), fnt) / 2, y + Game.getStringHeight(fnt) / 2);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		mouseOver(e.getPoint());
	}
	
	public void mouseOver(Point p) {
		if(box1.contains(p) && count != 0 && clickable)
			count--;
		else if(box2.contains(p) && count != max && clickable)
			count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		if(!name.equals("") && !tile)
			max = scene.getCardTypeHandler().getCard(name).getMaxCount();
		else
			max = 20;
	}
	
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
}
