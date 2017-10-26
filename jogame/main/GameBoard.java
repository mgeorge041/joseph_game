package jogame.main;

import java.awt.Graphics;

public class GameBoard {

	private SpotHandler spot_handler = new SpotHandler();
	private int size;
	private int game_width;
	private int game_height;
	private double spot_width;
	private double spot_height;
	private GameScene scene;
	private int city_1[];
	private int city_2[];
	
	public GameBoard(int width, int height, GameScene scene) {
		this.game_width = width;
		this.game_height = height;
		this.scene = scene;
	}
	
	public void drawBoard() {
		int hx = -size + 1;
		int hy = size - 1;
		int hz = 0;
		int coord[] = new int [3];
		city_1 = new int[]{0, size - 1, -size + 1};
		city_2 = new int[]{0, -size + 1, size - 1};
		double sides;
		int hex_num = 1; 

		int columns = size * 2 - 1;
		double max_col_width = (game_width / 4 * 3 - 10) / columns;
		int rows = size * 2 - 1;
		double max_row_height = (game_height / 5 * 4 - 10) / rows;
		
		if(max_col_width < max_row_height)
			sides = max_col_width / 2;
		else
			sides = max_row_height / Math.sqrt(3);
		
		spot_width = sides * 2;
		spot_height = sides * Math.sqrt(3);
	
		int hex = size;
		size = size - 1;
		
		for(int i = -size; i <= size; i++) {
			int r1 = Math.max(-size, -i - size);
			int r2 = Math.min(size, -i + size);
			for(int j = r1; j <= r2; j++) {
				spot_handler.add(new BoardSpot(sides, 1, new int[] {i, j, -i-j}, this));
			}
			
		}
		
/*
		for(int i = 0; i < size; i++) {
			hz = -i;
			hy = size - 1;
			for(int j = 0; j < hex; j++) {
				coord[0] = hx; 
				coord[1] = hy; 
				coord[2] = hz;
				spot_handler.add(new BoardSpot(sides, hex_num, coord, this));
				hy--;
				hz++;
				hex_num++;
			}
			hex++;
			hx++;
		}
		
		hex -= 2;
		for(int i = 0; i < size - 1; i++) {
			hz = -size + 1;
			hy = size - 2 - i;
			for(int j = 0; j < hex; j++) {
				coord[0] = hx; 
				coord[1] = hy; 
				coord[2] = hz;
				spot_handler.add(new BoardSpot(sides, hex_num, coord, this));
				hy--;
				hz++;
				hex_num++;
			}
			hex--;
			hx++;
		}*/
	}
	
	public void render(Graphics g) {
		spot_handler.render(g);
	}
	
	public void highlightTiles(int player_num) {
		spot_handler.highlightTiles(player_num);
	}
	
	public void clearHighlightTiles() {
		spot_handler.clearHighlightTiles();
	}
	
	public void resetBoard(Scene scene) {
		this.size = scene.getBoardSize();
		spot_handler.clear();
		drawBoard();
	}
	
	public SpotHandler getSpotHandler() {
		return spot_handler;
	}
	
	public double getSpotWidth() {
		return spot_width;
	}
	
	public double getSpotHeight() {
		return spot_height;
	}
	
	public GameScene getScene() {
		return scene;
	}
	
	public int getSize() {
		return size;
	}
	
	public int[] getCity(int player_num) {
		if(player_num == 1)
			return city_1;
		else
			return city_2;
	}
}
