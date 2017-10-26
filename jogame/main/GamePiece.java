package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Collections;

import javax.swing.ImageIcon;

public class GamePiece extends MouseAdapter{
	
	//Outline for piece and artwork
	private Image piece_outline;
	private Image card_art;
	private Image piece_art;
	private Image piece_highlight;
	private Image arrow;
	private CardType card;
	
	//Stats and ability
	private int health;
	private int speed_left;
	private Player player;
	private AbilityButton ability;
	private boolean can_ability = true;
	
	//Piece position variables
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle bounds;
	private BoardSpot spot;
	private BoardSpot target_spot;
	private int last_x;
	private int last_y;
	
	//Mouse location variables
	private boolean over = false;
	private boolean clicked = false;
	private boolean clicking = false;
	private boolean dragged = false;
	private boolean clickable = true;
	private boolean moveable = false;
	private boolean can_attack;
	
	public GamePiece(CardType card, Player player, BoardSpot spot){
		//Sets piece stats
		this.card = card;
		this.health = card.getHealth();
		this.speed_left = 0;
		this.moveable = false;
		if(card.getMight() > 0)
			can_attack = true;
		
		//Sets player
		this.player = player;
		
		//Updates spot details
		spot.setPiece(this);
		this.spot = spot;
		this.width = (int)spot.getBoard().getSpotWidth()/2*5/4;
		this.height = (int)(spot.getBoard().getSpotHeight()/Math.sqrt(3)*5/4);
		updatePos(spot);
		bounds = new Rectangle();
		bounds.setBounds(x, y, width, height);
		
		//Adds mouse listener
		spot.getBoard().getScene().addMouseListener(this);
		spot.getBoard().getScene().addMouseMotionListener(this);
		
		/*Sets artwork
		 * Piece_outline: player 1 or 2 for magnifying window
		 * Art: card drawing for magnifying window
		 * Piece: actual piece artwork
		 * Highlight: piece's highlight artwork
		 * Arrow: arrow to follow piece movement
		 */
		ImageIcon i;
		if(player.getPlayerNum() == 1)
			i = new ImageIcon("images/p1_piece_mag.png");
		else
			i = new ImageIcon("images/p2_piece_mag.png");
		piece_outline = i.getImage();
		i = new ImageIcon("images/artwork/" + card.getName() + ".png");
		card_art = i.getImage();
		i = new ImageIcon("images/" + card.getName() + "_piece.png");
		piece_art = i.getImage();
		i = new ImageIcon("images/" + card.getName() + "_piece_hl.png");
		piece_highlight = i.getImage();
		i = new ImageIcon("images/arrow.png");
		arrow = i.getImage();
		//Creates ability button
		if(card.getHasAbility()) 
			ability = new AbilityButton(x + width + 5, y, card, this);
	}
	
	//Checks if mouse over piece
	public void mouseMoved(MouseEvent e) {
		mouseOver(e.getPoint());
	}
	
	//If pressed on piece
	public void mousePressed(MouseEvent e) {
		if(over && player.isTurn() && moveable) {
			Collections.swap(player.getPieceHandler().getPieces(), player.getPieceHandler().getPieces().indexOf(this), player.getPieceHandler().getPieces().size()-1);
			clicked = true;
			last_x = e.getX();
			last_y = e.getY();
		}
	}

	//Updates position to mouse drag
	public void mouseDragged(MouseEvent e) {
		if(clicked) {
			dragged = true;
			x += e.getX() - last_x;
			y += e.getY() - last_y;
			last_x = e.getX();
			last_y = e.getY();
		}
	}
	
	//Checks if piece released on another spot
	public void mouseReleased(MouseEvent e) {
		if(dragged) {
			//Sees if released over a board spot
			Point p = new Point(e.getPoint());
			mouseOverSpot(p);
			if(target_spot != null) {
				//Gets distance between released spot and initial spot
				int distance = getDistance(target_spot, spot);
				
				//If released spot has piece or this piece can't move that far, return to initial position
				if(target_spot.getPiece() != null || distance > speed_left)
					updatePos(spot);
				//Otherwise, move to released spot
				else {
					speed_left -= distance;
					if(speed_left == 0)
						moveable = false;
					spot.setPiece(null);
					spot = target_spot;
					spot.setPiece(this);
					updatePos(spot);
				}
			}else 
				updatePos(spot);
		}
		clicked = false;
		dragged = false;
	}
	
	//Click on piece sets targeting for piece
	public void mouseClicked(MouseEvent e) {
		mouseOverSpot(e.getPoint());
		if(player.isTurn() && over && clickable && can_attack) {
			//If not currently targeting other pieces, sets to targeting
			if(!clicking) {
				clicking = true;
				if(ability != null)
					ability.setClickable(true);
			}
			//If currently targeting other pieces, sets to not targeting
			else {
				clicking = false;	
				if(ability != null)
					ability.setClickable(false);
			}
			//If currently targeting other pieces, highlights all targets
			if(clicking) {
				spot.setTargetNeighbor(spot, card.getRange(), player.getPlayerNum());
				spot.setTargeted(true);
				spot.getBoard().getScene().getPlayer(1).getPieceHandler().setClickable(false, this);
				spot.getBoard().getScene().getPlayer(2).getPieceHandler().setClickable(false, this);
				moveable = false;
			}
			//If not currently targeting other pieces, clears all targets
			else {
				spot.clearTargetNeighbor(spot, card.getRange());
				spot.setTargeted(false);
				spot.getBoard().getScene().getPlayer(1).getPieceHandler().setClickable(true, this);
				spot.getBoard().getScene().getPlayer(2).getPieceHandler().setClickable(true, this);
				if(speed_left > 0)
					moveable = true;
			}
		}
		//If targeting other pieces and clicks to attack, performs damage and clears all targets
		else if(clicking && target_spot != spot && target_spot.getTargeted()) {
			damage(target_spot);
			spot.clearTargetNeighbor(spot, card.getRange());
			spot.setTargeted(false);
			spot.getBoard().getScene().getPlayer(1).getPieceHandler().setClickable(true, this);
			spot.getBoard().getScene().getPlayer(2).getPieceHandler().setClickable(true, this);
			clicking = false;
			can_attack = false;
		}		
	}
	
	//Checks if mouse position is over piece
	public void mouseOver(Point p) {
		if(bounds.contains(p))
			over = true;
		else
			over = false;
	}
	
	//Gets distance between two spots
	public int getDistance(BoardSpot spot1, BoardSpot spot2) {
		int dx = Math.abs(spot1.getCoords()[0] - spot2.getCoords()[0]);
		int dy = Math.abs(spot1.getCoords()[1] - spot2.getCoords()[1]);
		int dz = Math.abs(spot1.getCoords()[2] - spot2.getCoords()[2]);
		int distance = (dx + dy + dz) / 2;
		return distance;
	}
	
	//Updates the piece to the spot position
	public void updatePos(BoardSpot spot) {
		x = spot.getCenter().x - width/2;
		y = spot.getCenter().y - height/2;		
		if(ability != null)
			ability.updateBounds(x + width + 5, y);
	}
	
	//Damage between pieces, sets moveable to false
	public void damage(BoardSpot spot2) {
		if(spot2.getTargeted() && can_attack)
			spot2.getPiece().takeDamage(card.getMight());
		can_attack = false;
		speed_left = 0;
	}
	
	//Takes damage
	public void takeDamage(int damage) {
		health -= damage;
		if(health <= 0) {
			spot.setPiece(null);
			player.getPieceHandler().remove(this);
			clearPiece();
			if(card.getName().equals("City")) {
				Game.game_state = Game.STATE.Menu;
				player.getScene().getWindow().setScene("Joseph's Game Menu");
			}
		}
	}
	
	//Checks if mouse released over a board spot
	public void mouseOverSpot(Point p) {
		SpotHandler spot_handler = spot.getBoard().getSpotHandler();
		if(spot_handler.mouseOver(p)) 
			target_spot = spot_handler.getOverSpot();
		else
			target_spot = null;
	}
	
	//Sets piece to effectively null
	public void clearPiece() {
		x = 0; 
		y = 0;
		width = 0;
		height = 0;
		bounds.setBounds(x, y, width, height);
	}
	
	//Renders piece
	public void render(Graphics g) {
		Window window = spot.getBoard().getScene().getWindow();
		
		//Clamps to game window
		x = Game.clamp(x, 0, window.getWidth()-width);
		y = Game.clamp(y, 0, window.getHeight()/5*4-height);
		bounds.setBounds(x, y, width, height);
		
		//Draws piece, magnifies, shows ability, and/or shows movement arrow
		drawPiece(g);
		if(over)
			magnify(g);
		if(clicking && ability != null)
			ability.render(g);
		if(dragged) 
			moving(g);
	}
	
	//Designed for having an arrow follow the piece's movement as the player drags the piece around
	public void moving(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		//Gets center of piece's spot
		double ax = spot.getCenter().getX();
		double ay = spot.getCenter().getY();
		
		//Sets rotation point around center of spot
		g2d.translate(ax, ay);
		
		//Finds angle between spot center and piece center
		double theta = Math.atan((y+height/2-ay)/(x+width/2-ax));
		
		//Determines which quadrant piece is in relative to spot center
		double offset = 0;
		if(theta >= 0) {
			if(ay > y+height/2)
				offset = -Math.PI/2;
			else
				offset = Math.PI/2;
		}else {
			if(ay > y+height/2)
				offset = Math.PI/2;
			else
				offset = -Math.PI/2;
		}
		
		g2d.rotate(theta + offset);
		
		//Creates size of arrow depending upon how far away piece is
		double a_height = Math.sqrt(Math.pow((y+height/2-ay), 2) + Math.pow((x+width/2-ax), 2));
		g2d.drawImage(arrow, -(int)a_height/10, -(int)a_height, (int)a_height/5, (int)a_height, null);

		//Resets transform to not affect other rendering of pieces or cards
		g2d.rotate(-theta-offset);
		g2d.translate(-ax, -ay);
		
	}
	
	//Drawing of piece on gameboard
	public void drawPiece(Graphics g) {
		//Going to be replaced once all the artwork is in
		//*****************************************************************************************
		if(piece_art.getWidth(null) < 0) {
			Font fnt = new Font("arial", 1, Game.getFontSize(width/4*3, card.getName()));
			g.setFont(fnt);
			g.drawImage(piece_outline, x, y, width, height, null);
	
			g.setColor(Color.black);
			int string_width = Game.getStringWidth(card.getName(), fnt);
			g.drawString(card.getName(), x + width / 2 - string_width / 2, y + height / 2 - Game.getFontDescent(fnt));
					
			if(card.getId() == Id.Building) {
				Font fnt2 = new Font("arial", 1, Game.getFontSize(width/3*2, "Health: " + health));
				g.setFont(fnt2);
				string_width = Game.getStringWidth("Health: " + health, fnt2);
				
				g.drawString("Health:", x + width / 2 - string_width / 2, y + height / 10 * 9);
				
				if(health < card.getHealth())
					g.setColor(Color.red);
				else
					g.setColor(new Color(0, 175, 0));
				g.drawString(" " + health, x + width / 2 + string_width / 2 - Game.getStringWidth(" " + health, fnt2), y + height / 10 * 9);
			}
			
			else if(card.getId() == Id.Unit) {
				Font fnt2 = new Font("arial", 1, Game.getFontSize(width/4, "H: " + health));
				g.setFont(fnt2);
				g.drawString("H:", x + 5, y + height / 10 * 8);
				g.drawString("S:", x + 5, y + height - 5);
				
				g.drawString("M: ", x + width / 2 + 5, y + height / 10 * 8);
				g.drawString("R: ", x + width / 2 + 5, y + height - 5);
				string_width = Game.getStringWidth("H:", fnt2);
				
				if(can_attack) 
					g.setColor(new Color(0, 175, 0));
				g.drawString(" " + card.getMight(), x + width / 2 + 5 + string_width, y + height / 10 * 8);
				g.drawString(" " + card.getRange(), x + width / 2 + 5 + string_width, y + height - 5);
				
				if(health < card.getHealth())
					g.setColor(Color.red);
				else
					g.setColor(new Color(0, 175, 0));
				g.drawString(" " + health, x + 5 + string_width, y + height / 10 * 8);
				string_width = Game.getStringWidth("H:", fnt2);
				if(speed_left < card.getSpeed())
					g.setColor(Color.red);
				else
					g.setColor(new Color(0, 175, 0));
				g.drawString(" " + speed_left, x + 5 + string_width, y + height - 5);
			}
			if(over)
				g.drawImage(card.getCardHighlight(), x, y, width, height, null);

		}
		//***********************************************************************************
		else {
			g.drawImage(piece_art, x, y, width, height, null);
			//Draws small box displaying piece stats
			if(over) {
				//Draws box
				g.drawImage(piece_highlight, x, y, width, height, null);
				g.setColor(Color.white);
				g.fillRect(x, y - height/4, width, height/4);
				g.setColor(Color.yellow);
				g.drawRect(x, y - height/4, width, height/4);
				
				//Sets font size for stats within box
				Font stat_fnt = new Font("arial", 1, Game.getFontSize(width / 4, "H: " + health));
				g.setFont(stat_fnt);
				
				//Draws labels for each stat
				g.setColor(Color.black);
				g.drawString("H:", x, y - height / 16);
				g.drawString("S:", x + width / 4, y - height / 16);
				g.drawString("M:", x + width / 2, y - height / 16);
				g.drawString("R:", x + width / 4 * 3, y - height / 16);
				
				//Health
				if(health < card.getHealth())
					g.setColor(Color.red);
				else
					g.setColor(new Color(0, 200, 0));
				g.drawString("" + health, x + Game.getStringWidth("H:", stat_fnt), y - height / 16);
				
				//Speed
				if(speed_left <= 0)
					g.setColor(Color.red);
				else
					g.setColor(new Color(0, 200, 0));
				g.drawString("" + speed_left, x + width / 4 + Game.getStringWidth("S:", stat_fnt), y - height / 16);
				
				//Might and range
				if(!can_attack)
					g.setColor(Color.red);
				else
					g.setColor(new Color(0, 200, 0));
				g.drawString("" + card.getMight(), x + width / 2 + Game.getStringWidth("M:", stat_fnt), y - height / 16);
				g.drawString("" + card.getRange(), x + width / 4 * 3 + Game.getStringWidth("R:", stat_fnt), y - height / 16);	
			}
		}
	}
	
	//Drawing of piece details in magnified window
	public void magnify(Graphics g) {
		int x = Window.act_width/4*3 + 5;
		int y = 10;
		int width = Window.act_width/4 - 10;
		int height = Window.act_height/2;	
		int x_mid = x + width/2;
		int y_mid = y + height/2;
		int x_bor = width/20;
		int y_bor = height/40;
		int x_bor_pos = x + x_bor;
		int y_bor_pos = y + height - y_bor - 5 ;
		
		/* CARD LAYOUT
		 * 2.5% border
		 * 47.5% image
		 * 10% name
		 * 10% description
		 * 7.5% ability description
		 * 20% stats
		 * 2.5% border
		 */

		//Sets two fonts for cards
		Font fnt = new Font("arial", 1, Game.getFontSize(height / 10));		//for name
		
		//Draws card background
		g.drawImage(piece_outline, x, y, width, height, null);
		
		//Draws name within 10% of height
		int name_width = Game.getStringWidth(card.getName(), fnt);
		g.setColor(Color.black);
		g.setFont(fnt);
		g.drawString(card.getName(), x_mid - name_width / 2, y_mid + height / 10 - Game.getFontDescent(fnt));
		
		//Draws piece description within 10% of height
		Game.wrapString(g, card.getDesc(), x_bor_pos, y_mid + height / 10, width - x_bor * 2, height / 10, true);
		
		//Draws details for units and buildings
		if(card.getId() != Id.Resource) {
			//Sets font for 15% of card height for the stats
			Font fnt3 = new Font("arial", 1, Game.getFontSize(height / 15));
			int string_height = Game.getStringHeight(fnt3);
			g.setFont(fnt3);
			
			//Draws health, speed, might, and range
			g.drawString("Health:", x_bor_pos, y_bor_pos - string_height * 2);
			g.drawString("Speed:", x_bor_pos, y_bor_pos - string_height);
			g.drawString("Might:", x_mid, y_bor_pos - string_height * 2);
			g.drawString("Range:", x_mid, y_bor_pos - string_height);
			
			//Draws health, if less than max in red, otherwise in green
			if(health < card.getHealth())
				g.setColor(Color.red);
			else
				g.setColor(new Color(0, 175, 0));
			int string_width = Game.getStringWidth("Health:", fnt3);
			g.drawString(" " + health, x_bor_pos + string_width, y_bor_pos - string_height * 2);
			
			//Draws speed, if zero in red, otherwise in green
			if(speed_left < card.getSpeed())
				g.setColor(Color.red);
			else
				g.setColor(new Color(0, 175, 0));
			string_width = Game.getStringWidth("Speed:", fnt3);
			g.drawString(" " + speed_left, x_bor_pos + string_width, y_bor_pos - string_height);
			
			//Draws might and range, if cannot attack in red, otherwise in green
			if(can_attack) 
				g.setColor(new Color(0, 175, 0));
			string_width = Game.getStringWidth("Range:", fnt3);
			g.drawString(" " + card.getRange(), x_mid + string_width, y_bor_pos - string_height);
			string_width = Game.getStringWidth("Might:", fnt3);
			g.drawString(" " + card.getMight(), x_mid + string_width, y_bor_pos - string_height * 2);
		}
		
		//Draws card image in magnify window
		g.drawImage(card_art, x_bor_pos, y + y_bor, width - x_bor * 2, height / 2 - y_bor, null);
		
		//Draws yellow box for ability symbol in card corner and ability description
		if(ability != null) {
			Font fnt4 = new Font("arial", 1, Game.getFontSize(height / 30));
			Game.wrapString(g, card.getAbilityDesc(), x_bor_pos, y_mid + height / 4, height / 20, width - x_bor * 2, false);
			g.setColor(Color.yellow);
			g.fillRect(x + width - x_bor * 3, y + y_bor, x_bor * 2, x_bor * 2);
			g.setColor(Color.black);
			g.drawRect(x + width - x_bor * 3, y + y_bor, x_bor * 2, x_bor * 2);
			fnt4 = new Font("arial", 1, Game.getFontSize(x_bor, "A"));
			g.drawString("A", x + width - x_bor * 2 - Game.getStringWidth("A", fnt4) / 2, y + y_bor + x_bor + Game.getStringHeight(fnt4) / 2);
		}
	}
	
	//Returns the piece rectangular bounds
	public Rectangle getBounds() {
		return bounds;
	}
	
	//Returns the player
	public Player getPlayer() {
		return player;
	}
	
	//Returns the player number
	public int getPlayerNum() {
		return player.getPlayerNum();
	}
	
	//Returns the piece name
	public String getName() {
		return card.getName();
	}
	
	//Returns the piece's current health
	public int getHealth() {
		return health;
	}
	
	//Sets the piece's current health
	public void setHealth(int health) {
		if(health <= card.getHealth())
			this.health = health;
	}
	
	//Returns the speed of the card
	public int getSpeed() {
		return card.getSpeed();
	}
	
	//Resets the piece for a new turn
	public void resetPiece() {
		speed_left = card.getSpeed();
		if(card.getId() == Id.Unit)
			moveable = true;
		if(card.getRange() > 0)
			can_attack = true;
		else
			can_attack = false;
		can_ability = true;
	}
	
	//Returns the range of the card
	public int getRange() {
		return card.getRange();
	}
	
	//Returns the spot the piece is on 
	public BoardSpot getSpot() {
		return spot;
	}
	
	//Sets whether the piece can be clicked
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	//Returns whether the piece can move
	public boolean getMoveable() {
		return moveable;
	}
	
	//Returns whether the piece can attack
	public boolean getAttackable() {
		return can_attack;
	}
	
	//Sets whether the piece can use its ability
	public void setCanAbility(boolean can_ability) {
		this.can_ability = can_ability;
	}
	
	//Returns whether the piece can use its ability
	public boolean getCanAbility() {
		return can_ability;
	}
}
