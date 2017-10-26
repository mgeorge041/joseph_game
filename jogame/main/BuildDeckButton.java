package jogame.main;

import java.awt.Font;

public class BuildDeckButton extends TextButton{
	
	BuildDeckScene scene;
	
	public BuildDeckButton(String text, int x, int y, Font fnt, BuildDeckScene scene) {
		super(text, x, y, fnt, scene);
		
		this.scene = scene;
	}

	public void action() {
		Window window = scene.getWindow();
		//Save button saves deck and goes back to game setup
		if(text == "Save") {
			Game.game_state = Game.STATE.Game_Setup;
			if(scene.getDeck() == "P1")
				window.getScene("Game Settings").setDeck1(scene.getDeckManager());
			else
				window.getScene("Game Settings").setDeck2(scene.getDeckManager());
			window.getScene("Game Settings").setCardHandler(scene.getCardTypeHandler());
			window.setScene("Game Settings");
		}
		//Cancel button clears deck and goes back to game setup
		else if(text == "Cancel") {
			if(scene.getDeck() == "P1")
				window.getScene("Game Settings").setDeck1(null);
			else
				window.getScene("Game Settings").setDeck2(null);
			scene.getDeckManager().clear();
			scene.getIncButtonHandler().clearCounts();
			Game.game_state = Game.STATE.Game_Setup;
			window.setScene("Game Settings");
		}
		else if(text == "Warrior") {
			scene.setCardClass("Warrior");
			scene.updateIncButtons("Warrior");
		}
		else if(text == "Archer") {
			scene.setCardClass("Archer");
			scene.updateIncButtons("Archer");
		}
		else if(text == "Beast") {
			scene.setCardClass("Beast");
			scene.updateIncButtons("Beast");
		}
		else if(text == "Undead") {
			scene.setCardClass("Undead");
			scene.updateIncButtons("Undead");
		}
		else if(text == "Mystical") {
			scene.setCardClass("Mystical");
			scene.updateIncButtons("Mystical");
		}
		else if(text == "General") {
			scene.setCardClass("General");
			scene.updateIncButtons("General");
		}
		else if(text == "Tiles") {
			scene.setCardClass("Tiles");
			scene.updateIncButtons("Tiles");
		}
		
	}

}
