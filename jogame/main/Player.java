package jogame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Player {

	private Hand hand;
	private Deck deck;
	private DeckManager deck_manager;
	private CardTypeHandler card_handler;
	private PieceHandler piece_handler;
	private TileHand tile_hand;
	private TileManager tile_manager;
	private GameScene scene;
	private boolean is_turn = false;
	private int player_num;
	
	public Player(int player_num, DeckManager deck_manager, CardTypeHandler card_handler, GameScene scene) {
		this.player_num = player_num;
		this.card_handler = card_handler;
		this.deck_manager = deck_manager;
		this.scene = scene;
		this.hand = new Hand(scene, this);
		this.deck = new Deck(deck_manager, card_handler, this);	
		piece_handler = new PieceHandler();
		this.tile_hand = new TileHand(scene, this);
		this.tile_manager = new TileManager();
	}
	
	public void createDeck() {
		deck.clear();
		deck.create(deck_manager, card_handler);
	}
	
	public void createHand() {
		hand.clear();

		for(int i = 0; i < 4; i++)
			hand.add(deck.drawCard());
	}
	
	public void createTileHand() {
		tile_hand.clear();
		for(int i = 0; i < 30; i++)
			tile_hand.add(new Tile(new TileType("Forest"), tile_hand, 100));
	}
	
	public void render(Graphics g) {
		hand.render(g);
	}
	
	//Resets the deck and player pieces
	public void resetPlayer() {
		createTileHand();
		//Creates new deck and hand
		createDeck();
		createHand();
		
		//Sets turn to false
		is_turn = false;
		
		//Clears all old pieces and adds new city
		GameBoard board = scene.getBoard();
		piece_handler.clear();
		piece_handler.add(new GamePiece(card_handler.getCard("City"), this, board.getSpotHandler().getSpot(board.getCity(player_num))));
		piece_handler.setUnitsPlayed(0);
	}
	
	public boolean isTurn() {
		return is_turn;
	}
	
	public void setTurn(boolean turn) {
		this.is_turn = turn;
		scene.getBoard().getSpotHandler().stopHighlightAllTargets();
		if(is_turn) {
			piece_handler.resetPieces(player_num);
			hand.add(deck.drawCard());
		}
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public int getPlayerNum() {
		return player_num;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public CardTypeHandler getCardHandler() {
		return card_handler;
	}
	
	public PieceHandler getPieceHandler() {
		return piece_handler;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public TileHand getTileHand() {
		return tile_hand;
	}
}
