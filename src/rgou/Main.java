package rgou;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.view.GameScenes;
import rgou.view.MainFrame;
import rgou.view.assetLoaders.AssetsPreloader;

public class Main {
	public static void main(String[] args) {
		GameStateController gameStateController = new GameStateController();
		MainFrame mainFrame = new MainFrame();
		GameSceneController gameSceneController = new GameSceneController(mainFrame, gameStateController);

		gameSceneController.setActiveScene(GameScenes.LOADING);
		gameSceneController.renderActiveScene();

		// load assets
		Thread thread = new Thread(() -> {
			AssetsPreloader.load();
			// load default scene:
			gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
		});
		thread.run();
	}
}