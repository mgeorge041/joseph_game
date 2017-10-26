package jogame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameScene extends Scene{

	private GameBoard board;
	private GameButton quit;
	private GameButton end_turn;
	private Player player1;
	private Player player2;
	private GridLayout grid;
	private boolean tile_setup = true;
	private boolean tile_setup_p1 = true;
	
	public GameScene(int width, int height, String text, Window window, DeckManager deck1, DeckManager deck2, CardTypeHandler card_handler) {
		super(width, height, text, window);
	
		game_state = Game.STATE.Game;
		
		board = new GameBoard(window.getWidth(), window.getHeight(), this);
		
		player1 = new Player(1, deck1, card_handler, this);
		player2 = new Player(2, deck2, card_handler, this);
	
		Font fnt = new Font ("arial", 1, 30);
		
		quit = new GameButton("Quit", window.getWidth()/8*7, window.getHeight()/10*9, fnt, this);
		end_turn = new GameButton("Finish Layout", window.getWidth()/8*7, window.getHeight()/10*7, fnt, this);
		
		button_handler.add(quit);
		button_handler.add(end_turn);
		
		grid = new GridLayout(2, 3, this);
		int rows[] = {1, 2};
		int row_pos[] = {height/2 + 5, height/5*4 - 10};
		int cols[] = {1, 2, 3};
		int col_pos[] = {10, width/4*3, width/8*7};
		
		grid.setRowsPos(rows, row_pos);
		grid.setColsPos(cols, col_pos);
	}
	
	public void render(Graphics g) {		
		g.setColor(Color.black);
		//Vertical line at 3/4 window width
		g.drawLine(grid.Col(2), 0, grid.Col(2), window.getHeight());
		//Horizontal line at 4/5 window width
		g.drawLine(0, window.getHeight()/5*4, window.getWidth(), window.getHeight()/5*4);
		g.drawLine(grid.Col(2), window.getHeight()/2 + 12, window.getWidth(), window.getHeight()/2 + 12);
		g.drawLine(window.getWidth()/16*9, window.getHeight()/5*4, window.getWidth()/16*9, window.getHeight());
		board.render(g);
		
		Player temp_player;
		
		if(player1.isTurn() || tile_setup_p1) {
			temp_player = player1;
			
			g.setColor(Color.red);
		}else {
			temp_player = player2;
			
			g.setColor(new Color(0, 0, 200));
		}
			
		Font fnt = new Font("arial", 1, 30);
		g.setFont(fnt);

		if(!tile_setup) {
			g.drawString("Player " + temp_player.getPlayerNum() + " Turn", grid.Col(1), grid.Row(2));
			g.setColor(Color.black);
			int string_height = Game.getStringHeight(fnt);
			int pad = 10;
			g.drawString("Cards in Deck: " + temp_player.getDeck().getCardCount(), grid.Col(2) + pad, grid.Row(1) + string_height);
			g.drawString("Food: " + temp_player.getHand().getFoodCount(), grid.Col(2) + pad, grid.Row(1) + string_height * 2);
			g.drawString("Lumber: " + temp_player.getHand().getLumberCount(), grid.Col(2) + pad, grid.Row(1) + string_height * 3);
			g.drawString("Mana: " + temp_player.getHand().getManaCount(), grid.Col(3), grid.Row(1) + string_height * 2);
			int moveable_count = temp_player.getPieceHandler().countMoveable();
			int attackable_count = temp_player.getPieceHandler().countAttackable();
			g.drawString("Moveable Pieces: " + moveable_count, grid.Col(2) - Game.getStringWidth("Moveable Pieces: " + moveable_count, fnt), grid.Row(2) - string_height);
			g.drawString("Attackable Pieces: " + attackable_count, grid.Col(2) - Game.getStringWidth("Attackable Pieces: " + attackable_count, fnt), grid.Row(2));
			 
			player1.getPieceHandler().render(g);
			player2.getPieceHandler().render(g);
			temp_player.render(g);
		}else {
			if(tile_setup_p1) {
				board.highlightTiles(1);
				player1.getTileHand().render(g);
				g.drawString("Player " + temp_player.getPlayerNum() + " Setup", grid.Col(1), grid.Row(2));
			}
			else {
				board.highlightTiles(2);
				player2.getTileHand().render(g);
				g.drawString("Player " + temp_player.getPlayerNum() + " Setup", grid.Col(1), grid.Row(2));
			}
		}
		button_handler.render(g);
		
	}
	
	public void restartGame(Scene scene) {
		board.resetBoard(scene);
		player1.resetPlayer();
		player2.resetPlayer();
		end_turn.setText("Finish Layout");
		tile_setup = true;
		tile_setup_p1 = true;
	}
	
	public GameBoard getBoard() {
		return board;
	}
	
	public void nextTurn() {
		if(player1.isTurn()) {
			player2.setTurn(true);
			player1.setTurn(false);
		}
		else {
			player1.setTurn(true);
			player2.setTurn(false);
		}
	}
	
	public Player getPlayer(int player) {
		if(player == 1)
			return player1;
		else
			return player2;
	}
	
	public void setGame(boolean tile_setup) {
		this.tile_setup = !tile_setup;
	}
	
	public boolean getTileSetup() {
		return tile_setup_p1;
	}
	
	public void setTileSetup(boolean tile_setup_p1) {
		this.tile_setup_p1 = tile_setup_p1;
	}
	
	public TextButton getSetupButton() {
		return end_turn;
	}
}
