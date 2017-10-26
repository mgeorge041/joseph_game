package jogame.main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Button extends MouseAdapter{

	//Variables for size and location of button
	protected int x;
	protected int y;
	protected int left;
	protected int top;
	protected int width;
	protected int height;
	protected int padding = 20;
	protected Rectangle bounds;
	
	//Variables for mouse movement over button
	private boolean over = false;
	private boolean clicked = false;
	private boolean clickable = true;
	
	public Button() {}
	
	public Button(int x, int y, Scene scene){
		this.x = x;
		this.y = y;
		
		//Initializes bounding rectangle
		bounds = new Rectangle();
		
		//Uses string dimensions to size button
	    width = padding;
	    height = padding;
	    
	    //Sets button upper left corner
	    left = x - width/2;
	    top = y - height/2;
	    
	    //Sets button bounds
	    bounds.setBounds(left - padding/2, y - height/2, width, height);
	    
	    //Adds mouse listener to button
	    scene.addMouseListener(this);
	    scene.addMouseMotionListener(this);
	}
	
	//Renders button
	public void render(Graphics g) {
		if(clicked == true)
			clicked(g);
		else if(over)
			highlight(g);
		else
			drawButton(g);
	}
	
	//If mouse moves over button
	public void mouseMoved(MouseEvent e) {
		if(mouseOver(e.getPoint()) && clickable)
			over = true;
		else
			over = false;
	}
	
	//If mouse pressed down on button
	public void mousePressed(MouseEvent e) {
		if(over && clickable)
			clicked = true;
		else
			clicked = false;
	}
	
	//If mouse is dragged after pressing
	public void mouseDragged(MouseEvent e) {
		if(mouseOver(e.getPoint()) && clickable)
			over = true;
		else
			over = false;
	}
	
	//If mouse released on button
	public void mouseReleased(MouseEvent e) {
		if(over && clicked && clickable)
			action();
		clicked = false;
	}
	
	//If mouse spot is over button
	public boolean mouseOver(Point p) {
		if(bounds.contains(p))
			return true;
		else
			return false;
	}
	
	//Draws button if mouse not over
	public abstract void drawButton(Graphics g);
	
	//Highlights if mouse over button
	public abstract void highlight(Graphics g);
	
	//Highlights and shrinks if mouse held over button
	public abstract void clicked(Graphics g); 
	
	//Returns the button width
	public int getButtonWidth() {
		return width;
	}
	
	//Returns the button height
	public int getButtonHeight() {
		return height;
	}
	
	//Sets whether the button can be clicked
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	//The action for when the button is clicked
	public abstract void action();
}
