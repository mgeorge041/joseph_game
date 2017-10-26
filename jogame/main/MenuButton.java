package jogame.main;

import java.awt.Font;

public class MenuButton extends TextButton{

	private MenuScene scene;
	
	public MenuButton(String text, int x, int y, Font fnt, MenuScene scene){
		super(text, x, y, fnt, scene);
		
		this.scene = scene;
	}

	public void action() {
		if(text == "New Game") {
			Game.game_state = Game.STATE.Game_Setup;
			if(scene.getWindow().getScene("Game Settings") == null) {
				NewGameScene new_game_scene = new NewGameScene(scene.getWindow().getWidth(), scene.getWindow().getHeight(), "Game Settings", scene.getWindow());
				scene.getWindow().addScene(new_game_scene);
			}
			scene.getWindow().setScene("Game Settings");
		}
		else if(text == "Quit")
			System.exit(1);
		else if(text == "Help")
			//Game.game_state = Game.STATE.Help;
			System.out.println("penis penis penis");
		else
			Game.game_state = Game.STATE.Settings;
	}
}
