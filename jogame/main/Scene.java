package jogame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;;

public abstract class Scene extends Canvas{

	private static final long serialVersionUID = -335415946840416727L;
	
	protected Window window;
	private String title;
	private int width;
	private int height;
	protected ButtonHandler button_handler = new ButtonHandler();
	protected CheckBoxHandler check_box_handler = new CheckBoxHandler();
	protected Game.STATE game_state;
	protected int last_x;
	protected int last_y;
	private Image mouse;
	
	public Scene() {};
	
	public Scene(int width, int height, String title, Window window) {
	
		this.width = width;
		this.height = height;
		this.window = window;
		this.title = title;
		
		this.setSize(new Dimension(width, height));
		
		this.setBackground(Color.white);
	}
	
	public Window getWindow() {
		return window;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void clearCanvas(Graphics g) {
		g.clearRect(0, 0, width, height);
	}
	
	public void render(Graphics g) {
		button_handler.render(g);
		check_box_handler.render(g);
		g.drawImage(mouse, last_x, last_y, 30, 60, null);
	}
	
	public int getBoardSize() {
		return 0;
	}
	
	public void restartGame(Scene scene) {
	}
	
	public void nextTurn() {
		
	}
	
	public DeckManager getDeckManager() {
		return null;
	}
	
	public IncrementButtonHandler getIncButtonHandler() {
		return null;
	}
	
	public void setDeck1(DeckManager deck) {
		
	}
	
	public void setDeck2(DeckManager deck) {
		
	}
	
	public void setCardHandler(CardTypeHandler card_handler) {
		
	}
	
	public GameBoard getBoard() {
		return null;
	}
	
	public CardTypeHandler getCardTypeHandler() {
		return null;
	}
	
	public TileManager getTileManager() {
		return null;
	}
	
	
}
