package rgou;

import rgou.view.GameSceneController;
import rgou.view.MainFrame;
import rgou.view.scenes.GameSceneBase;
import rgou.view.scenes.MainMenuScene;

// temp file to test main

public class TestingGameScene {
	public static void main(String[] args) throws Exception {
		MainFrame frame = new MainFrame();

		GameSceneController gameSceneController = new GameSceneController(frame);

		GameSceneBase scene = new MainMenuScene(gameSceneController);

		frame.add(scene);
		scene.run();

		// gameSceneController.setActiveScene(Scenes.GAMEPLAY);

		frame.setVisible(true);
	}
}
