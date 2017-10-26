package jogame.main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Card extends MouseAdapter{
	
	//Card parents
	private Hand hand;
	private Player player;
	private CardType card;
	
	//Variables for moving cards
	private int last_x;
	private int last_y;
	private int start_x;
	private int start_y;
	private boolean clicked = false;
	private boolean over = false;
	private boolean playable = true;
	private boolean dragging = false;
	private BoardSpot target_spot;
	
	//Card size and position
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle bounds;
	private int hand_pos;
	
	public Card(CardType card, int card_height, Hand hand){
		this.card = card;
		this.player = hand.getPlayer();
		this.height = card_height;
		this.hand = hand;
		
		//Initializes card bounds
		bounds = new Rectangle();
		
		//Adds mouse listener
		hand.getPlayer().getScene().addMouseListener(this);
		hand.getPlayer().getScene().addMouseMotionListener(this);
	}
	
	//Sets card position and updates bounds
	public void updatePos(int new_width) {
		width = new_width;
		if(card.getId() != Id.Resource)
			x = hand_pos * (width + 5) + 5;
		else
			x = hand_pos * (width + 5) + Window.act_width / 16 * 9 + 5;
		y = (height + 10) * 4 + 5;
		start_x = x;
		start_y = y;
		
		bounds.setBounds(x, y, width, height);
	}

	//If mouse moves over card
	public void mouseMoved(MouseEvent e) {
		if(hand.getPlayer().isTurn()) 
			mouseOver(e.getPoint());
	}
	
	//If mouse is pressed on card
	public void mousePressed(MouseEvent e) {
		if(hand.getPlayer().isTurn() && over && playable) {
			clicked = true;
			last_x = e.getX();
			last_y = e.getY();
		}
	}

	//Moves card with mouse while dragging
	public void mouseDragged(MouseEvent e) {
		if(clicked) {
			dragging = true;
			x += e.getX() - last_x;
			y += e.getY() - last_y;
			last_x = e.getX();
			last_y = e.getY();
		}
	}
	
	//When mouse is released, move card based off final location
	public void mouseReleased(MouseEvent e) {
		if(dragging) {
			//Sees if mouse is over a board spot
			Point p = new Point(e.getPoint());
			mouseOverSpot(p);
			if(target_spot != null) {
				boolean close = false;
				GamePiece city = target_spot.getNeighborPiece("City");
				if(city != null)
					if(city.getPlayer() == player)
						close = true;
				//Checks to see if the board spot has a piece already
				if(target_spot.getPiece() == null && playable && close) {
					//Adds new piece if no piece already there and removes card from hand
					GamePiece piece = new GamePiece(card, player, target_spot);
					player.getPieceHandler().add(piece);
					hand.shift(this);
					hand.removeRes(this);
					clearCard();
				
				//If not over spot, return card to start position in hand
				}else {
					x = start_x;
					y = start_y;
				}
			//If not over spot, return card to start position in hand
			}else {
				x = start_x;
				y = start_y;
			}
			over = false;
		}
		clicked = false;
		dragging = false;
	}
	
	//If mouse is over card
	public void mouseOver(Point p) {
		if(bounds.contains(p))
			over = true;
		else
			over = false;
	}
	
	//If mouse is over card
	public void mouseOverSpot(Point p) {
		if(player.getScene().getBoard().getSpotHandler().mouseOver(p))
			target_spot = player.getScene().getBoard().getSpotHandler().getOverSpot();
		else
			target_spot = null;
	}
	
	//Sets card to essentially null
	public void clearCard() {
		x = 0;
		y = 0;
		start_x = 0;
		start_y = 0;
		width = 0;
		height = 0;
		bounds.setBounds(x, y, width, height);
	}
	
	//Renders the card
	public void render(Graphics g) {
		//Prevents card from being dragged off screen
		x = Game.clamp(x, 0, Window.act_width/4*3-width);
		y = Game.clamp(y, 0, Window.act_height-height);
		
		//Sets card bounds
		bounds.setBounds(x, y, width, height);

		//If dragging card from hand, draw piece image
		if(dragging && card.getPieceArt().getWidth(null) > 0)
			g.drawImage(card.getPieceArt(), x, y, width, width, null);
		else {
			card.render(g, bounds);
			if(playable)
				g.drawImage(card.getCardPlayable(), x, y, width, height, null);
			if(over) {				
				card.magnifyCard(g);
				card.highlightRes(g);
				if(!dragging) {
					g.drawImage(card.getCardHighlight(), x, y, width, height, null);
				}
			}
			
		}
	}
	
	//Sets hand position of card
	public void setHandPos(int pos) {
		this.hand_pos = pos;
	}
	
	//Returns hand position of card
	public int getHandPos() {
		return hand_pos;
	}
	
	//Gets player number
	public int getPlayerNum() {
		return player.getPlayerNum();
	}
	
	public CardType getCard() {
		return card;
	}
	
	public String getName() {
		return card.getName();
	}
	
	public String getRes1() {
		return card.getResource1();
	}
	
	public int getCost1() {
		return card.getCost1();
	}
	
	public String getRes2() {
		return card.getResource2();
	}
	
	public int getCost2() {
		return card.getCost2();
	}
	
	public void setPlayable(boolean playable) {
		this.playable = playable;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
