package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BuildDeckScene extends Scene{
	
	//Increment button handler and deck manager
	private IncrementButtonHandler inc_button_handler;
	private DeckManager deck_manager;
	public CardTypeHandler card_handler;
	private GridLayout grid;
	private TileManager tile_manager;
	
	//Cancel and save buttons
	private BuildDeckButton cancel;
	private BuildDeckButton save;
	private BuildDeckButton warrior;
	private BuildDeckButton beast;
	private BuildDeckButton archer;
	private BuildDeckButton undead;
	private BuildDeckButton mystical;
	private BuildDeckButton general;
	private BuildDeckButton tiles;
	private String card_class;
	private String piece_type;
	
	//All the increment buttons
	private IncrementButton b1;
	private IncrementButton b2;
	private IncrementButton b3;
	private IncrementButton b4;
	private IncrementButton b5;
	private IncrementButton b6;
	private IncrementButton b7;
	private IncrementButton b8;
	private IncrementButton b9;
	private IncrementButton b10;
	private IncrementButton b11;
	private IncrementButton b12;
	
	//Deck name for P1 or P2
	private String deck;
	
	public BuildDeckScene(int width, int height, String title, Window window) {
		super(width, height, title, window);
		
		card_handler = new CardTypeHandler();
		tile_manager = new TileManager();
		
		//Determines which player deck belongs to
		if(title == "Build Deck: P1")
			deck = "P1";
		else
			deck = "P2";
		
		//Creates handlers
		inc_button_handler = new IncrementButtonHandler();
		
		//Sets game state to deck building
		game_state = Game.STATE.Deck_Build;
		
		Font fnt = new Font("arial", 1, 30);
		
		grid = new GridLayout(14, 11, this);
		
		//Sets box dimensions for card previews
		int box_width = window.getWidth()/6;
		int box_height = window.getHeight()/4;
		int vert_gap = (window.getHeight() - box_height * 3) / 5;
		
		//Determines where first box x coordinate will start
		int string_width = Game.getStringWidth("Resources", fnt);
		
		//Sets grid rows
		grid.setRowPos(1, 5);										// ROW 1: top of boxes
		grid.setRowPos(2, grid.Row(1) + box_height / 2);			// ROW 2: middle of first box row
		grid.setRowPos(3, grid.Row(1) + box_height + vert_gap / 2);	// ROW 3: first increment button row
		grid.setRowPos(4, grid.Row(1) + box_height + vert_gap);		// ROW 4: top of second box
		grid.setRowPos(5, grid.Row(4) + box_height / 2);			// ROW 5: middle of second box row
		grid.setRowPos(6, grid.Row(4) + box_height + vert_gap / 2);	// ROW 6: second increment button row
		grid.setRowPos(7, grid.Row(4) + box_height + vert_gap);		// ROW 7: top of third box
		grid.setRowPos(8, grid.Row(7) + box_height / 2);			// ROW 8: middle of third box row
		grid.setRowPos(9, grid.Row(7) + box_height + vert_gap / 2); // ROW 9: third increment button row
		grid.setRowPos(10, grid.Row(9) + vert_gap * 3 / 2);			// ROW 10: buttons
		
		//Sets grid cols
		grid.setColPos(1, 5);										// COL 1: first text
		grid.setColPos(2, grid.Col(1) + string_width);				// COL 2: first box
		grid.setColPos(3, grid.Col(2) + box_width / 2);				// COL 3: middle of first box
		grid.setColPos(4, grid.Col(2) + box_width + 5);				// COL 4: second box
		grid.setColPos(5, grid.Col(4) + box_width / 2);				// COL 5: middle of second box
		grid.setColPos(6, grid.Col(4) + box_width + 5);		 		// COL 6: third box
		grid.setColPos(7, grid.Col(6) + box_width / 2);				// COL 7: middle of third box
		grid.setColPos(8, grid.Col(6) + box_width + 5);				// COL 8: fourth box
		grid.setColPos(9, grid.Col(8) + box_width / 2);				// COL 9: middle of fourth box
		grid.setColPos(10, grid.Col(8) + box_width + 5);			// COL 10: deck count
		grid.setColPos(11, grid.Col(10) + box_width / 2); 			// COL 11: cancel button
		
		//Creates cancel and save buttons in bottom right corner
		cancel = new BuildDeckButton("Cancel", grid.Col(11) + 10, grid.Row(10), fnt, this);
		save = new BuildDeckButton("Save", grid.Col(10), grid.Row(10), fnt, this);
		
		GridLayout class_grid = new GridLayout(1, 7, this);
		BuildDeckButton sample = new BuildDeckButton("Mystical", 0, 0, fnt, this);
		int button_width = sample.getButtonWidth();
		sample = null;
		
		for(int i = 0; i < 7; i++)
			class_grid.setColPos(i + 1, (int)(button_width * (i + 0.5)));
		
		warrior = new BuildDeckButton("Warrior", class_grid.Col(1), grid.Row(10), fnt, this);
		archer = new BuildDeckButton("Archer", class_grid.Col(2), grid.Row(10), fnt, this);
		beast = new BuildDeckButton("Beast", class_grid.Col(3), grid.Row(10), fnt, this);
		undead = new BuildDeckButton("Undead", class_grid.Col(4), grid.Row(10), fnt, this);
		mystical = new BuildDeckButton("Mystical", class_grid.Col(5), grid.Row(10), fnt, this);
		general = new BuildDeckButton("General", class_grid.Col(6), grid.Row(10), fnt, this);
		tiles = new BuildDeckButton("Tiles", class_grid.Col(7), grid.Row(10), fnt, this);
		
		card_class = "General";
		piece_type = "Card";
		
		int inc_box_width = box_width / 8;
		int inc_box_height = vert_gap / 4 * 3;
		
		//Initializes increment buttons into grid pattern
		b1 = new IncrementButton(grid.Col(3), grid.Row(3), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b2 = new IncrementButton(grid.Col(5), grid.Row(3), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b3 = new IncrementButton(grid.Col(7), grid.Row(3), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b4 = new IncrementButton(grid.Col(9), grid.Row(3), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b5 = new IncrementButton(grid.Col(3), grid.Row(6), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b6 = new IncrementButton(grid.Col(5), grid.Row(6), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b7 = new IncrementButton(grid.Col(7), grid.Row(6), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b8 = new IncrementButton(grid.Col(9), grid.Row(6), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b9 = new IncrementButton(grid.Col(3), grid.Row(9), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b10 = new IncrementButton(grid.Col(5), grid.Row(9), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b11 = new IncrementButton(grid.Col(7), grid.Row(9), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		b12 = new IncrementButton(grid.Col(9), grid.Row(9), inc_box_width, inc_box_height, card_handler.getClassCard(card_class), piece_type, this);
		
		//Creates deck manager
		deck_manager = new DeckManager(grid.Col(10), grid.Row(1));
		
		//Adds all buttons to handlers
		button_handler.add(cancel);
		button_handler.add(save);
		button_handler.add(warrior);
		button_handler.add(archer);
		button_handler.add(beast);
		button_handler.add(undead);
		button_handler.add(mystical);
		button_handler.add(general);
		button_handler.add(tiles);
		
		inc_button_handler.add(b1);
		inc_button_handler.add(b2);
		inc_button_handler.add(b3);
		inc_button_handler.add(b4);
		inc_button_handler.add(b5);
		inc_button_handler.add(b6);
		inc_button_handler.add(b7);
		inc_button_handler.add(b8);
		inc_button_handler.add(b9);
		inc_button_handler.add(b10);
		inc_button_handler.add(b11);
		inc_button_handler.add(b12);
		
		card_class = "General";
	}
	
	//Renders deck building scene
	public void render(Graphics g) {
		super.render(g);
		
		//Sets box dimensions for card previews
		int box_width = window.getWidth()/6;
		int box_height = window.getHeight()/4;
		Font fnt = new Font("arial", 1, 30);
		
		int string_height = Game.getStringHeight(fnt);
		
		g.setColor(Color.yellow);
		g.fillRect(0, 0, grid.Col(2), string_height + 10);
		g.setColor(Color.black);
		g.drawString(card_class, grid.Col(2)/2 - Game.getStringWidth(card_class, fnt) / 2, string_height);
		
		ArrayList<Rectangle> rect = new ArrayList<Rectangle>();
		
		//Draws 9 boxes in grid for card previews
		for(int j = 0; j < 3; j++) 
			for(int i = 0; i < 4; i++) 
				rect.add(new Rectangle(grid.Col(i * 2 + 2), grid.Row(j * 3 + 1), box_width, box_height));
		
		for(int i = 0; i < 12; i++) {
			IncrementButton temp = inc_button_handler.getIncButtons().get(i);
			if(!temp.getName().equals("")) 
				if(card_class != "Tiles")
					card_handler.getCard(temp.getName()).render(g, rect.get(i));
		}
		
		//Renders handlers
		inc_button_handler.render(g);
		deck_manager.update(inc_button_handler.getIncButtons());
		deck_manager.render(g);
		
		if(deck_manager.isComplete())
			save.setClickable(true);
		else
			save.setClickable(false);
	}
	
	//Returns deck manager
	public DeckManager getDeckManager() {
		return deck_manager;
	}
	
	//Returns increment button handler
	public IncrementButtonHandler getIncButtonHandler() {
		return inc_button_handler;
	}
	
	public void updateIncButtons(String card_class) {
		if(card_class != "Tiles") {
			card_handler.setCardPos();
			for(int i = 0; i < 12; i++) {
				IncrementButton temp = inc_button_handler.getIncButtons().get(i);
				temp.setCount(0);
				temp.setName(card_handler.getClassCard(card_class));
				if(!temp.getName().equals(""))
					temp.setClickable(true);
				else
					temp.setClickable(false);
				for(int j = 0; j < deck_manager.getNames().size(); j++) {
					if(deck_manager.getNames().get(j).equals(temp.getName()))
							temp.setCount(deck_manager.getCounts().get(j));
				}
			}
		}else 
			for(int i = 0; i < 12; i++) {
				IncrementButton temp = inc_button_handler.getIncButtons().get(i);
				temp.setCount(0);
				temp.setName(tile_manager.getNextTile());
				
			}
	}
	
	//Returns deck name
	public String getDeck() {
		return deck;
	}
	
	public CardTypeHandler getCardTypeHandler() {
		return card_handler;
	}
	
	public String getCardClass() {
		return card_class;
	}
	
	public void setCardClass(String card_class) {
		this.card_class = card_class;
	}
	
	public TileManager getTileManager() {
		return tile_manager;
	}
	
	public void setPieceType(String piece_type) {
		this.piece_type = piece_type;
	}
}
