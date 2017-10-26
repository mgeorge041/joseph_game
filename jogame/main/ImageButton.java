package jogame.main;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class ImageButton extends Button{
	
	//Text for button and font
	protected Image img;
	protected Image img_hl;

	//Variables for size and location of button
	protected int bot;

	public ImageButton(String image, int x, int y, Scene scene){
		super(x, y, scene);
		
		ImageIcon i = new ImageIcon("images/" + image + ".png");
		img = i.getImage();
		i = new ImageIcon("images/" + image + "_hl.png");
		img_hl = i.getImage();
		
		//Uses string dimensions to size button
	    width = img.getWidth(null);
	    height = img.getHeight(null);
	    
	    //Sets button upper left corner
	    left = x - width/2;
	    top = y - height/2;
	    
	    //Sets button bounds
	    bounds.setBounds(left, top, width, height);
	}
	
	//Draws button if mouse not over
	public void drawButton(Graphics g) {
		g.drawImage(img, left, top, width, height, null);
	}
	
	//Highlights if mouse over button
	public void highlight(Graphics g) {
		g.drawImage(img_hl, left, top, width, height, null);
	}
	
	//Highlights and shrinks if mouse held over button
	public void clicked(Graphics g) {
		g.drawImage(img_hl, left+padding, top+padding, width-padding*2, height-padding*2, null);
	}
}
