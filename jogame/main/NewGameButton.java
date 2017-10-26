package jogame.main;

import java.awt.Font;

public class NewGameButton extends TextButton{
	
	private NewGameScene scene;
	
	public NewGameButton(String text, int x, int y, Font fnt, NewGameScene scene){
		super(text, x, y, fnt, scene);
		
		this.scene = scene;
	}

	public void action() {
		Window window = scene.getWindow();
		if(text == "Start Game") {
			Game.game_state = Game.STATE.Game;
			if(window.getScene("Joseph's Game") == null) {
				GameScene game_scene = new GameScene(window.getWidth(), window.getHeight(), "Joseph's Game", window, scene.getDeck1(), scene.getDeck2(), scene.getCardHandler());
				window.addScene(game_scene);
			}
			window.getScene("Joseph's Game").restartGame(scene);
			window.setScene("Joseph's Game");
		}
		else if(text == "Cancel") {
			Game.game_state = Game.STATE.Menu;
			window.setScene("Joseph's Game Menu");
		}
		else if(text == "Build Deck: P1") {
			Game.game_state = Game.STATE.Deck_Build;
			if(window.getScene("Build Deck: P1") == null) {
				BuildDeckScene build_deck_scene = new BuildDeckScene(window.getWidth(), window.getHeight(), "Build Deck: P1", window);
				window.addScene(build_deck_scene);
			}
			window.setScene("Build Deck: P1");
		}
		else if(text == "Build Deck: P2") {
			Game.game_state = Game.STATE.Deck_Build;
			if(window.getScene("Build Deck: P2") == null) {
				BuildDeckScene build_deck_scene = new BuildDeckScene(window.getWidth(), window.getHeight(), "Build Deck: P2", window);
				window.addScene(build_deck_scene);
			}
			window.setScene("Build Deck: P2");
		}
	}
}
