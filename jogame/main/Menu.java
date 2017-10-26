package jogame.main;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu{

	private Game game;
	private MenuButton new_game;
	private MenuButton help;
	private MenuButton settings;
	private MenuButton quit;
	private GamePiece test;
	
	public Menu(int act_width, int act_height) {
		this.game = game;
		
		Font fnt = new Font("arial", 1, 30);
		/*
		new_game = new MenuButton("New Game", act_width/2, act_height/3*2, fnt);
		help = new MenuButton("Help", act_width/2, act_height/5*4, fnt);
		settings = new MenuButton("Settings", act_width/4, act_height/5*4, fnt);
		quit = new MenuButton("Quit", act_width/4*3, act_height/5*4, fnt);
		
		game.menu_button_handler.add(new_game);
		game.menu_button_handler.add(help);
		game.menu_button_handler.add(settings);
		game.menu_button_handler.add(quit);
		
		test = new GamePiece("hello", "name is:", game.act_width/2, game.act_height/2, fnt);
		
		game.piece_handler.add(test);*/
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(20, 20, 130, 60);
	}
}
