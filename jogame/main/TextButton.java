package jogame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public abstract class TextButton extends Button{
	
	//Text for button and font
	protected String text;
	protected Font fnt;

	//Variables for size and location of button
	protected int bot;

	public TextButton(String text, int x, int y, Font fnt, Scene scene){
		super(x, y, scene);
		this.text = text;
		this.fnt = fnt;
		
		updateBounds();
	}
	
	//Draws button if mouse not over
	public void drawButton(Graphics g) {
	    // Set the font
	    g.setFont(fnt);

	    //Draws gray interior
	    g.setColor(Color.lightGray);
	    g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	    
	    // Draw the String
	    g.setColor(Color.black);
	    g.drawString(text, left, bot);
	    g.drawRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	}
	
	//Highlights if mouse over button
	public void highlight(Graphics g) {
		// Set the font
		g.setFont(fnt);

		//Draws blue interior
	    g.setColor(Color.blue);
	    g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	    
	    // Draw the String
	    g.setColor(Color.white);
	    g.drawString(text, left, bot);
	    g.setColor(Color.black);
	    g.drawRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	}
	
	//Highlights and shrinks if mouse held over button
	public void clicked(Graphics g) {
		//Sets the font
		g.setFont(fnt);
		
		//Draws blue interior
		g.setColor(Color.blue);
		g.fillRect((int)bounds.getX()+padding/4, (int)bounds.getY() + padding/4, width-padding/2, height-padding/2);
		
		//Draws the string
	    g.setColor(Color.white);
	    g.drawString(text, left, bot);
	    g.setColor(Color.black);
	    g.drawRect((int)bounds.getX()+padding/4, (int)bounds.getY() + padding/4, width-padding/2, height-padding/2);
	}
	
	public void updateBounds() {
		//Gets dimensions of button text
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt);
		
		//Uses string dimensions to size button
	    width = metrics.stringWidth(text) + padding;
	    height = metrics.getHeight() + padding;
	    
	    //Sets button upper left corner
	    left = x - metrics.stringWidth(text)/2;
	    bot = y + (metrics.getMaxAscent() - metrics.getMaxDescent())/2;
	    
	    //Sets button bounds
	    bounds.setBounds(left - padding/2, y - height/2, width, height);
	    
	    //Clears canvas for string dimensions
	    c = null;
	}
	
	public void setText(String text) {
		this.text = text;
		updateBounds();
	}
}
