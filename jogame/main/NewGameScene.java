package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class NewGameScene extends Scene{

	private static final long serialVersionUID = 2315830451345177663L;
	
	private CheckBox game_size;
	private CheckBox deck_select1;
	private CheckBox deck_select2;
	private boolean show_deck_select1 = false;
	private boolean show_deck_select2 = false;
	private DeckManager deck1;
	private DeckManager deck2;
	private StdDeckManager std_deck;
	private CheckBoxHandler check_box_handler = new CheckBoxHandler();
	private CardTypeHandler card_handler;
	private GridLayout grid;
	
	private NewGameButton start;
	private NewGameButton cancel;
	private NewGameButton build_deck1;
	private NewGameButton build_deck2;
	
	public NewGameScene(int width, int height, String title, Window window) {
		super(width, height, title, window);
		
		card_handler = new CardTypeHandler();
		
		game_state = Game.STATE.Game_Setup;
		
		Font fnt = new Font("arial", 1, 30);
		
		//Grid creation and first row position
		grid = new GridLayout(7, 4, this);
		grid.setRowPos(1, 50); 																		// ROW 1: board size checkbox
		grid.setColPos(1, 100); 																	// COL 1: board size checkbox and deck select checkboxes
		
		//Board size checkbox
		String choices[] = {"Board Size", "Small", "Medium", "Large", "Extra Large"};
		game_size = new CheckBox(grid.Col(1), grid.Row(1), choices, this);
		game_size.setSelection(1);
		grid.setRowPos(2, grid.Row(1) + game_size.getTotalHeight()); 								// ROW 2: first deck select
		
		//Deck selection checkboxes
		String decks1[] = {"Deck Selection: Player 1", "Standard Deck", "Custom Deck"};
		String decks2[] = {"Deck Selection: Player 2", "Standard Deck", "Custom Deck"};

		//Sample button for deck build buttons
		NewGameButton sample = new NewGameButton("Build Deck: P1", 0, 0, fnt, this);
		
		//First deck selection and build deck button
		deck_select1 = new CheckBox(grid.Col(1), grid.Row(2), decks1, this);
		grid.setColPos(2, grid.Col(1) + deck_select1.getTotalWidth() + sample.getButtonWidth()); 	//COL 2: build deck buttons
		grid.setRowPos(3, grid.Row(2) + deck_select1.getTotalHeight()/2); 							//ROW 3: first deck build button
		build_deck1 = new NewGameButton("Build Deck: P1", grid.Col(2), grid.Row(3), fnt, this);
		
		//Second deck selection
		grid.setRowPos(4, grid.Row(2) + deck_select1.getTotalHeight()); 							//ROW 4: second deck select
		deck_select2 = new CheckBox(grid.Col(1), grid.Row(4), decks2, this);
		grid.setRowPos(5, grid.Row(4) + deck_select2.getTotalHeight()/2); 							//ROW 5: second deck build button
		build_deck2 = new NewGameButton("Build Deck: P2", grid.Col(2), grid.Row(5), fnt, this);
		
		//Build deck buttons, initially hidden and unclickable
		build_deck1.setClickable(false);
		build_deck2.setClickable(false);
		
		//Clears sample button
		sample = null;
		
		grid.setColPos(3, grid.Col(2) + build_deck1.getButtonWidth() / 2 + 5);						//COL 3: check boxes for deck completion
				
		//Start and cancel new game buttons
		grid.setColPos(4, width/4*3); 																//COL 4: start and cancel buttons
		grid.setRowPos(6, height/4*3); 																//ROW 6: start button
		grid.setRowPos(7, height/8*7); 																//ROW 7: cancel button
		start = new NewGameButton("Start Game", grid.Col(4), grid.Row(6), fnt, this);
		cancel = new NewGameButton("Cancel", grid.Col(4), grid.Row(7), fnt, this);
		
		//Creation of standard deck
		createStdDeck();
		setDeck1(std_deck);
		setDeck2(std_deck);
		
		//Adds check boxes to handler
		check_box_handler.add(game_size);
		check_box_handler.add(deck_select1);
		check_box_handler.add(deck_select2);
		
		//Adds buttons to handler
		button_handler.add(start);
		button_handler.add(cancel);
	}
	
	public int getBoardSize() {
		return game_size.getSelection() + 3;
	}
	
	public void render(Graphics g) {
		super.render(g);
		check_box_handler.render(g);
		
		if(show_deck_select1) {
			g.setColor(Color.black);
			g.drawRect(grid.Col(3), grid.Row(3) - build_deck1.getButtonHeight() / 4, build_deck1.getButtonHeight() / 2, build_deck1.getButtonHeight() / 2);
			if(deck1 != null) {
				g.setColor(new Color(0, 225, 0));
				g.fillRect(grid.Col(3), grid.Row(3) - build_deck1.getButtonHeight() / 4, build_deck1.getButtonHeight() / 2, build_deck1.getButtonHeight() / 2);
			}
		}
		if(show_deck_select2) {
			g.setColor(Color.black);
			g.drawRect(grid.Col(3), grid.Row(5) - build_deck2.getButtonHeight() / 4, build_deck2.getButtonHeight() / 2, build_deck2.getButtonHeight() / 2);
			if(deck2 != null) {
				g.setColor(new Color(0, 225, 0));
				g.fillRect(grid.Col(3), grid.Row(5) - build_deck2.getButtonHeight() / 4, build_deck2.getButtonHeight() / 2, build_deck2.getButtonHeight() / 2);
			}
		}
		
		//Shows build deck 1 if selected
		if(deck_select1.getSelection() == 1 && !show_deck_select1){
			button_handler.add(build_deck1);
			show_deck_select1 = true;
			build_deck1.setClickable(true);
			setDeck1(null);
		}
		else if(deck_select1.getSelection() == 0 && show_deck_select1) {
			button_handler.remove(build_deck1);
			show_deck_select1 = false;
			build_deck1.setClickable(false);
			setDeck1(std_deck);
		}
		
		//Shows build deck 2 if selected
		if(deck_select2.getSelection() == 1 && !show_deck_select2){
			button_handler.add(build_deck2);
			show_deck_select2 = true;
			build_deck2.setClickable(true);
			setDeck2(null);
		}
		else if(deck_select2.getSelection() == 0 && show_deck_select2) {
			button_handler.remove(build_deck2);
			show_deck_select2 = false;
			build_deck2.setClickable(false);
			setDeck2(std_deck);
		}
		if(deck1 == null || deck2 == null)
			start.setClickable(false);
		else
			start.setClickable(true);
	}
	
	public void createStdDeck() {
		std_deck = new StdDeckManager("Std_Deck");
	}
	
	public void setDeck1(DeckManager deck1) {
		this.deck1 = deck1;
	}
	
	public void setDeck2(DeckManager deck2) {
		this.deck2 = deck2;
	}
	
	public DeckManager getDeck1() {
		return deck1;
	}
	
	public DeckManager getDeck2() {
		return deck2;
	}
	
	public StdDeckManager getStdDeck() {
		return std_deck;
	}
	
	public void setCardHandler(CardTypeHandler card_handler) {
		this.card_handler = card_handler;
	}
	
	public CardTypeHandler getCardHandler() {
		return card_handler;
	}
}
