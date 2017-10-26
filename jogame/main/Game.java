package jogame.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
	
	//Overall game window and variables for content pane size
	public static final int WIDTH = 1280, HEIGHT = WIDTH / 4 * 3;
	public static int act_width, act_height;
	
	//Thread to run the game and the initial running state
	private Thread thread;
	private boolean running = false;
	
	//Handler to handle events
	private Window window;

	public enum STATE{
		Menu, 
		Game,
		Game_Setup,
		Deck_Build,
		Help,
		Settings,
	};
	
	public static STATE game_state = STATE.Menu;
	
	//Game constructor
	public Game() {
		
		game_state = STATE.Menu;
		
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();

		//window = new Window((int)screen_size.getWidth(), (int)screen_size.getHeight(), "Joseph's Game", this);
		window = new Window(1280, 960, "Joseph's Game", this);
				
		this.start();
	}
	
	//Creates the thread for the game
	public synchronized void start() {
		//Initializes the new thread and starts it, setting running to true
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		//Tries ending the thread, setting running to false
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Runs the game
	public void run() {
		
		//Sets game window to active window
		//this.requestFocus();
		
		//Gets current moment in nanoseconds
		long last_time = System.nanoTime();
		
		//Sets ticks to 60 for 60 seconds
		double amount_of_ticks = 60.0;
		double ns = 1000000000 / amount_of_ticks;
		double delta = 0;
		
		//Gets current moment in milliseconds
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		//For each moment that passes, goes through tick and render for game
		while(running) {
			
			//Gets new current moment in nanoseconds
			long now = System.nanoTime();
			
			//Finds difference between then and now
			delta += (now - last_time) / ns;
			
			//Makes then, now
			last_time = now;
			
			//If time has passed
			while(delta >= 1) {
				delta--;
			}
			
			//Renders game if running
			if(running)
				render();
			frames++;
			
			//Shows FPS
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	//Renders the game
	public void render() {
		if(window != null)
			window.render();
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		return var;
	}
	
	//Gets font size based upon desired width of string
	public static int getFontSize(int width, String text) {
		Canvas c = new Canvas();
		FontMetrics metrics;
		Font fnt;
		int temp_width = 0;
		for(int i = 0; i < 120; i++) {
			fnt = new Font("arial", 1, i);
			metrics = c.getFontMetrics(fnt);
			temp_width = metrics.stringWidth(text);
			if(temp_width >= width)
				return i;
		}
		return 0;
	}
	
	//Gets font size based upon desired height
	public static int getFontSize(int height) {
		Canvas c = new Canvas();
		FontMetrics metrics;
		Font fnt;
		int temp_height = 0;
		for(int i = 0; i < 120; i++) {
			fnt = new Font("arial", 1, i);
			metrics = c.getFontMetrics(fnt);
			temp_height = metrics.getMaxAscent() + metrics.getMaxDescent();
			if(temp_height >= height)
				return i;
		}
		return 0;
	}
	
	public static int getStringHeight(Font fnt) {
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt);
		int string_height = metrics.getMaxAscent() + metrics.getMaxDescent();
		c = null;
		return string_height;
	}
	
	//Gets string width
	public static int getStringWidth(String text, Font fnt) {
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt);
		int string_width = metrics.stringWidth(text);
		c = null;
		return string_width;
	}
	
	public static int getFontDescent(Font fnt) {
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt);
		c = null;
		return metrics.getMaxDescent();
	}
	
	public static void wrapString(Graphics g, String str, int x, int y, int width, int height, boolean centered) {
		Canvas c = new Canvas();
		Font fnt = new Font("arial", 1, 1);
		FontMetrics metrics = c.getFontMetrics(fnt);
		int strings = 0;
		String words[] = str.split(" ");
		for(int i = 100; i > 0; i--) {
			fnt = new Font("arial", 1, i);
			metrics = c.getFontMetrics(fnt);
			double string_height = metrics.getAscent() + metrics.getDescent();
			strings = getNumberLines(words, fnt, width);
			if(strings * string_height < height)
				break;
		}
		String lines[] = new String[strings];
		for(int i = 0; i < strings; i++)
			lines[i] = "";
		int line = 0;
		int string_length = 0;
		for(int i = 0; i < words.length; i++) {
			if(string_length + metrics.stringWidth(words[i]) > width) {
				string_length = 0;
				line++;
			}
			string_length += metrics.stringWidth(words[i] + " ");
			lines[line] += words[i] + " ";
		}
		g.setFont(fnt);
		y += metrics.getAscent() + metrics.getDescent();
		for(int i = 0; i <= line; i++) {
			string_length = metrics.stringWidth(lines[i]);
			if(centered)
				g.drawString(lines[i], x + width / 2 - string_length / 2, y);
			else
				g.drawString(lines[i], x, y);
			y += metrics.getAscent() + metrics.getDescent();
		}
	}
	
	public static int getNumberLines(String[] words, Font fnt, int width) {
		Canvas c = new Canvas();
		FontMetrics metrics = c.getFontMetrics(fnt); 
		int string_length = 0;
		int lines = 1;
		for(int i = 0; i < words.length; i++) {
			if(string_length + metrics.stringWidth(words[i]) > width) {
				lines++;
				string_length = 0;
			}
			string_length += metrics.stringWidth(words[i] + " ");
		}
		return lines;
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
