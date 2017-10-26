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
import java.util.ArrayList;

public class CheckBox extends MouseAdapter{
	
	protected CheckBoxHandler check_box_handler;
	protected ArrayList<Rectangle> check_boxes = new ArrayList<Rectangle>();
	protected Scene scene;
	
	protected int x;
	protected int y;
	protected int y_start;
	protected int options;
	protected int total_height;
	protected int total_width;
	
	protected int width=20;
	protected int height=20;
	
	protected String title;
	protected boolean selected[];
	protected String choices[];
	
	public CheckBox(int x, int y, String picks[], Scene scene){
		this.options = picks.length - 1;
		this.x = x;
		this.y = y;
		y_start = y;
		//this.scene = scene;
		this.title = picks[0];
		
		Font fnt = new Font("arial", 1, 30);
		int string_height = getStringHeight(title, fnt);
		y += string_height + 15;
		
		selected = new boolean[options];
		choices = picks;
		
		for(int i = 0; i < options; i++) {
			selected[i] = false;
			check_boxes.add(makeBox(x, y, width, height));
			y += height + 10;
		}
		total_height = (y - y_start);
		selected[0] = true;
		
		scene.addMouseListener(this);
		
		Font fnt2 = new Font("arial", 1, 20);
		
		int max_string_width = getStringWidth(title, fnt);
		
		for(int i = 0; i < check_boxes.size(); i++) {
			String text = choices[i + 1];
			int string_width = getStringWidth(text, fnt2);
			if(string_width > max_string_width)
				max_string_width = string_width;
		}

		total_width = max_string_width + width + 5;
	}
	
	public Rectangle makeBox(int x, int y, int width, int height) {
		Rectangle bounds = new Rectangle();
	    
	    bounds.setBounds(x, y - height/2, width, height);

	    return bounds;
	}
	
	public void mouseClicked(MouseEvent e) {
		clicked(e.getPoint());
	}
	
	public void render(Graphics g) {
		drawBoxes(g);
		drawChoices(g);
	}
	
	public int getStringHeight(String text, Font fnt) {
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt);
		int string_height = metrics.getMaxAscent() - metrics.getMaxDescent();
		c = null;
		return string_height;
	}
	
	public int getStringWidth(String text, Font fnt) {
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt);
		int string_width = metrics.stringWidth(text);
		c = null;
		return string_width;
	}
	
	public int getSelection() {
		for(int i = 0; i < selected.length; i++) {
			if(selected[i] == true)
				return i;
		}
		return selected.length;
	}
	
	public void setSelection(int selection) {
		for(int i = 0; i < selected.length; i++)
			selected[i] = false;
		selected[selection] = true;
	}
	
	public String[] getChoices() {
		return choices;
	}
	
	public boolean mouseOver(Point p, Rectangle bounds) {
		if(bounds.contains(p))
			return true;
		else
			return false;
	}
	
	public void drawBoxes(Graphics g) {
	    for(int i = 0; i < check_boxes.size(); i++) {
	    	Rectangle bounds = check_boxes.get(i);
	    
	    	if(selected[i] == false) {
	    		g.setColor(Color.white);
				g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
				
	    		g.setColor(Color.black);
			    g.drawRect((int)bounds.getX(), (int)bounds.getY(), width, height);
			    g.drawLine(x, 0, x, 800);
			    g.drawLine(0, y, 800, y);
	    	}else {
	    		g.setColor(Color.blue);
				g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
				
			    g.setColor(Color.black);
			    g.drawLine(x, 0, x, 800);
			    g.drawLine(0, y, 800, y);
			    g.drawRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	    	}
	    }
	}
	
	public void drawChoices(Graphics g) {
		Canvas c = new Canvas();
		g.setColor(Color.black);
		Font fnt = new Font("arial", 1, 30);
		int string_height = getStringHeight(title, fnt);
		g.setFont(fnt);
		g.drawString(title, x, y_start + string_height);
		
		Font fnt2 = new Font("arial", 1, 20);
		FontMetrics metrics = c.getFontMetrics(fnt2);
		g.setFont(fnt2);
		
		for(int i = 0; i < check_boxes.size(); i++) {
			Rectangle bounds = check_boxes.get(i);
			
			String text = choices[i + 1];
		    
		    double bot = bounds.getY() + bounds.getHeight()/2 + (metrics.getMaxAscent() - metrics.getMaxDescent())/2;
		    
			g.drawString(text, (int)(bounds.getX() + bounds.getWidth() + 5), (int)bot);
		}
		c = null;
	}
	
	public void clicked(Point p) {
		int select = options + 1;
		for(int i = 0; i < check_boxes.size(); i++) {
			Rectangle bounds = check_boxes.get(i);
			if(mouseOver(p, bounds)) 
				select = i;
		}
		if(select <= options) {
			for(int i = 0; i < check_boxes.size(); i++) {
				if(select == i)
					selected[i] = true;
				else
					selected[i] = false;
			}
		}
	}
	
	public int getTotalHeight() {
		return total_height;
	}
	
	public int getTotalWidth() {
		return total_width;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
