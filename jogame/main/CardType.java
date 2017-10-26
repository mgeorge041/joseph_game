package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class CardType {
	
	private String name;
	private CardClass card_class;
	private int health;
	private int might;
	private int speed;
	private int range;
	private Id id;
	private String resource1;
	private int cost1;
	private String resource2;
	private int cost2;
	private int max_count;
	private boolean has_ability = false;
	private String ability_desc;
	private String desc;
	private Image card_outline;
	private Image card_highlight;
	private Image art;
	private Image card_playable;
	private Image piece_art;
		
	public CardType(String name, CardClass card_class, int health, int might, int speed, int range, Id id, String resource1, int cost1, String resource2, int cost2, boolean has_ability, String ability_desc, String desc) {
		this.setName(name);
		this.setCardClass(card_class);
		this.setHealth(health);
		this.setMight(might);
		this.setSpeed(speed);
		this.setRange(range);
		this.setId(id);
		this.setResource1(resource1);
		this.setCost1(cost1);
		this.setResource2(resource2);
		this.setCost2(cost2);
		if(id == Id.Unit)
			max_count = 3;
		else
			max_count = 60;
		if(has_ability) {
			this.has_ability = true;
			this.ability_desc = ability_desc;
		}
		this.desc = desc;
		ImageIcon i = new ImageIcon("images/card_outline.png");
		card_outline = i.getImage();
		i = new ImageIcon("images/card_highlight.png");
		card_highlight = i.getImage();
		i = new ImageIcon("images/artwork/" + name + ".png");
		art = i.getImage();
		i = new ImageIcon("images/card_playable.png");
		card_playable = i.getImage();
		i = new ImageIcon("images/" + name + "_piece.png");
		piece_art = i.getImage();
	}
	
	public void render(Graphics g, Rectangle bounds) {
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		int width = (int)bounds.getWidth();
		int height = (int)bounds.getHeight();
		int x_mid = x + width/2;
		int y_mid = y + height/2;
		int x_bor = width/20;
		int y_bor = height/40;
		int x_bor_pos = x + x_bor;
		int y_bor_pos = y + height - y_bor - 5;
		
		/* CARD LAYOUT
		 * 2.5% border
		 * 47.5% image
		 * 10% name
		 * 10% description
		 * 7.5% ability description
		 * 20% stats
		 * 2.5% border
		 */
 
		//Draws card outline
		g.drawImage(card_outline, x, y, width, height, null);
		
		//Sets font for name within 10% of the height and draws name
		Font fnt = new Font("arial", 1, Game.getFontSize(height / 10));	
		Font fnt_n = new Font("arial", 1, Game.getFontSize(width - x_bor * 2, name));
		if(Game.getStringWidth(name, fnt) > Game.getStringWidth(name, fnt_n))
			fnt = fnt_n;
		g.setColor(Color.black);
		g.setFont(fnt);
		g.drawString(name, x_mid - Game.getStringWidth(name, fnt) / 2, y_mid + height / 10 - Game.getFontDescent(fnt));
		
		//Fits description within 10% of the height
		Game.wrapString(g, desc, x_bor_pos, y_mid + height / 10, width - x_bor * 2, height / 10, true);
		
		//Fits ability description within 7.5% of the height
		if(has_ability) 
			Game.wrapString(g, ability_desc, x_bor_pos, y_mid + height / 5, width - x_bor * 2, height / 40 * 3, false);
		
		if(id != Id.Resource) {
			//Fits stats within 15% of the height
			Font fnt2 = new Font("arial", 1, Game.getFontSize(height / 15));
			Font fnt3 = new Font("arial", 1, Game.getFontSize(width / 2 - x_bor, "Lumber: " + cost1));
			if(Game.getStringWidth("Lumber: " + cost1, fnt2) > Game.getStringWidth("Lumber: " + cost1, fnt3)) 
				fnt2 = fnt3;
			
			//Draws resources
			g.setFont(fnt2);
			g.drawString(resource1 + ": " + cost1, x_bor_pos, y_bor_pos);
			if(!resource2.equals(""))
				g.drawString(resource2 + ": " + cost2, x_mid, y_bor_pos);
			
			//Draws health, speed, might, and range
			int string_height = Game.getStringHeight(fnt2);
			g.drawString("Health: " + health, x_bor_pos, y_bor_pos - string_height * 2);
			g.drawString("Speed: " + speed, x_bor_pos, y_bor_pos - string_height);
			g.drawString("Might: " + might, x_mid, y_bor_pos - string_height * 2);
			g.drawString("Range: " + range, x_mid, y_bor_pos - string_height);
		}
		
		//Draws card art
		g.drawImage(art, x_bor_pos, y + y_bor, width - x_bor * 2, height / 2 - y_bor, null);
		
		//If has ability, draws ability desc and yellow box in corner to indicate ability
		if(has_ability) {
			g.setColor(Color.yellow);
			g.fillRect(x + width - x_bor * 3, y + y_bor, x_bor * 2, x_bor * 2);
			g.setColor(Color.black);
			g.drawRect(x + width - x_bor * 3, y + y_bor, x_bor * 2, x_bor * 2);
			Font fnt4 = new Font("arial", 1, Game.getFontSize(x_bor, "A"));
			g.drawString("A", x + width - x_bor * 2 - Game.getStringWidth("A", fnt4) / 2, y + y_bor + x_bor + Game.getStringHeight(fnt4) / 2);
		}
	}
	
	public void highlightRes(Graphics g) {
		g.setColor(Color.red);
		Font fnt = new Font("arial", 1, 30);
		g.setFont(fnt);
		int pad = 10;
		int x_pos = Window.act_width/4*3;
		int y_pos = Window.act_height/2 + 5;
		int string_height = Game.getStringHeight(fnt);
		
		if(resource1.equals("Food"))
			g.drawString("-" + cost1, x_pos + pad + Game.getStringWidth("Food: 9", fnt), y_pos + string_height * 2);
		else if(resource1.equals("Lumber"))
			g.drawString("-" + cost1, x_pos + pad + Game.getStringWidth("Lumber: 9", fnt), y_pos + string_height * 3);
		else if(resource1.equals("Mana"))
			g.drawString("-" + cost1, x_pos + Window.act_width/8 + Game.getStringWidth("Mana: 9", fnt), y_pos + string_height * 2);
		if(resource2.equals("Food"))
			g.drawString("-" + cost2, x_pos + pad + Game.getStringWidth("Food: 9", fnt), y_pos + string_height * 2);
		else if(resource2.equals("Lumber"))
			g.drawString("-" + cost2, x_pos + pad + Game.getStringWidth("Lumber: 9", fnt), y_pos + string_height * 3);
		else if(resource2.equals("Mana"))
			g.drawString("-" + cost2, x_pos + Window.act_width/8 + Game.getStringWidth("Mana: 9", fnt), y_pos + string_height * 2);

	}
	
	//Draws large card preview in window corner
	public void magnifyCard(Graphics g) {
		int x = Window.act_width/4*3 + 5;
		int y = 10;
		int width = Window.act_width/4 - 10;
		int height = Window.act_height/2;
		
		render(g, new Rectangle(x, y, width, height));
	}
	
	public void ability(BoardSpot spot) {
		/*if(name.equals("Paladin")) {
			GamePiece piece = spot.getPiece();
			if(piece.getPlayer().getHand().getManaCount() > 0) {
				piece.getPlayer().getHand().removeRes("Mana");
				for(int i = 0; i < 6; i++) {
					BoardSpot spot2 = spot.getNeighborSpot(i);
					if(spot2 != null)
						if(spot2.getPiece() != null)
							if(spot2.getPiece().getPlayerNum() == piece.getPlayerNum() && piece.getDistance(spot, spot2) <= piece.getRange())
								spot2.getPiece().setHealth(spot2.getPiece().getHealth() + 1);
				}
			}
		}*/
	}

	public String getName() {
		if(name != null)
			return name;
		return "";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public CardClass getCardClass() {
		return card_class;
	}
	
	public void setCardClass(CardClass card_class) {
		this.card_class = card_class;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMight() {
		return might;
	}

	public void setMight(int might) {
		this.might = might;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public String getResource1() {
		return resource1;
	}

	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}

	public int getCost1() {
		return cost1;
	}

	public void setCost1(int cost1) {
		this.cost1 = cost1;
	}

	public String getResource2() {
		return resource2;
	}

	public void setResource2(String resource2) {
		this.resource2 = resource2;
	}

	public int getCost2() {
		return cost2;
	}

	public void setCost2(int cost2) {
		this.cost2 = cost2;
	}
	
	public int getMaxCount() {
		return max_count;
	}
	
	public boolean getHasAbility() {
		return has_ability;
	}
	
	public String getAbilityDesc() {
		return ability_desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public Image getCardOutline() {
		return card_outline;
	}
	
	public Image getCardHighlight() {
		return card_highlight;
	}
	
	public Image getCardPlayable() {
		return card_playable;
	}
	
	public Image getPieceArt() {
		return piece_art;
	}

}
