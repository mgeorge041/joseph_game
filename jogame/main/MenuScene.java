package jogame.main;

import java.awt.Font;
import java.awt.event.MouseEvent;

public class MenuScene extends Scene{
	
	private static final long serialVersionUID = -6246992672689035128L;
	
	private MenuButton new_game;
	private MenuButton help;
	private MenuButton settings;
	private MenuButton quit;
	private GridLayout grid;
	
	public MenuScene(int width, int height, String title, Window window) {
	
		super(width, height, title, window);
		
		grid = new GridLayout(3, 4, this);
		int rows[] = {1, 2};
		int row_pos[] = {height/3*2, height/5*4};
		int cols[] = {1, 2, 3};
		int col_pos[] = {width/4, width/2, width/4*3};
		
		grid.setRowsPos(rows, row_pos);
		grid.setColsPos(cols, col_pos);
		
		game_state = Game.STATE.Menu;
		
		Font fnt = new Font("arial", 1, 30);
		
		new_game = new MenuButton("New Game", grid.Col(2), grid.Row(1), fnt, this);
		settings = new MenuButton("Settings", grid.Col(1), grid.Row(2), fnt, this);
		help = new MenuButton("Help", grid.Col(2), grid.Row(2), fnt, this);
		quit = new MenuButton("Quit", grid.Col(3), grid.Row(2), fnt, this);
		
		String picks[] = {"help", "me", "now"};
		CheckBox box = new CheckBox(100, 100, picks, this);
		
		check_box_handler.add(box);
		
		button_handler.add(new_game);
		button_handler.add(help);
		button_handler.add(settings);
		button_handler.add(quit);
	}
	/*
	public void render(Graphics g) {		
		Font fnt = new Font("arial", 1, 30);
		Font fnt2 = new Font("arial", 1, 28);
		Font fnt3 = new Font("arial", 1, 26);
		Font fnt4 = new Font("arial", 1, 24);
		Font fnt5 = new Font("arial", 1, 22);
		Font fnt6 = new Font("arial", 1, 20);
		Font fnt7 = new Font("arial", 1, 18);
		Font fnt8 = new Font("arial", 1, 16);
		Font fnt9 = new Font("arial", 1, 14);
		Font fnt10 = new Font("arial", 1, 12);
		Font fnt11 = new Font("arial", 1, 10);
		Font fnt12 = new Font("arial", 1, 8);
		
		g.setColor(Color.black);
		
		g.setFont(fnt);
		g.drawString("30", 10, 20);
		g.setFont(fnt2);
		g.drawString("28", 10, 50);
		g.setFont(fnt3);
		g.drawString("26", 10, 80);
		g.setFont(fnt4);
		g.drawString("24", 10, 110);
		g.setFont(fnt5);
		g.drawString("22", 10, 140);
		g.setFont(fnt6);
		g.drawString("20", 10, 170);
		g.setFont(fnt7);
		g.drawString("18", 10, 200);
		g.setFont(fnt8);
		g.drawString("16", 10, 230);
		g.setFont(fnt9);
		g.drawString("14", 10, 260);
		g.setFont(fnt10);
		g.drawString("12", 10, 290);
		g.setFont(fnt11);
		g.drawString("10", 10, 320);
		g.setFont(fnt12);
		g.drawString("8", 10, 350);
		
		
	}*/
}
