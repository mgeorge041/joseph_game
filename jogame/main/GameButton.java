package jogame.main;

import java.awt.Font;

public class GameButton extends TextButton{
	
	private GameScene scene;
	
	public GameButton(String text, int x, int y, Font fnt, GameScene scene) {
		super(text, x, y, fnt, scene);
		
		this.scene = scene;
	}

	public void action() {
		Window window = scene.getWindow();
		if(text == "Quit") {
			Game.game_state = Game.STATE.Menu;
			window.setScene("Joseph's Game Menu");
		}else if(text == "End Turn")
			scene.nextTurn();
		else if(text == "Finish Layout") {
			if(scene.getTileSetup()) {
				scene.setTileSetup(false);
			}else {
				scene.setGame(true);
				scene.getSetupButton().setText("End Turn");
				scene.getPlayer(1).setTurn(true);
				scene.getPlayer(1).getTileHand().clear();
				scene.getPlayer(2).getTileHand().clear();
				scene.getBoard().clearHighlightTiles();
			}
		}
	}

}
