package jogame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AbilityButton extends Button{

	private CardType card;
	private GamePiece piece;
	
	public AbilityButton(int x, int y, CardType card, GamePiece piece) {
		this.x = x;
		this.y = y;
		this.card = card;
		this.piece = piece;
		this.width = (int)piece.getBounds().getWidth() / 4;
		this.height = (int)piece.getBounds().getWidth() / 4;
		
		bounds = new Rectangle();
		
		bounds.setBounds(x, y, width, height);
		
		setClickable(false);
		
		piece.getSpot().getBoard().getScene().addMouseListener(this);
		piece.getSpot().getBoard().getScene().addMouseMotionListener(this);
	}
	
	//Draws button if mouse not over
	public void drawButton(Graphics g) {
	    //Draws yellow interior
	    g.setColor(Color.yellow);
	    g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	    g.setColor(Color.black);
	    g.drawRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	}
	
	//Highlights if mouse over button
	public void highlight(Graphics g) {
		//Draws green outline
	    g.setColor(Color.yellow);
	    g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	    g.setColor(Color.green);
	    g.drawRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	}
	
	//Highlights and shrinks if mouse held over button
	public void clicked(Graphics g) {
		//Draws green outline
	    g.setColor(Color.green);
	    g.fillRect((int)bounds.getX(), (int)bounds.getY(), width, height);
	    g.setColor(Color.yellow);
	    g.fillRect((int)bounds.getX() + 3, (int)bounds.getY() + 3, width - 6, height - 6);
	}
	
	public void updateBounds(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = (int)piece.getBounds().getWidth() / 4;
		this.height = (int)piece.getBounds().getWidth() / 4;
		
		bounds.setBounds(x, y, width, height);
	}

	public void action() {
		if(piece.getCanAbility()) {
			piece.setCanAbility(false);
			card.ability(piece.getSpot());
		}
	}
}
