package rgou;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.view.GameScenes;
import rgou.view.MainFrame;
import rgou.view.assetLoaders.AssetsPreloader;

public class Main {
	/**
	 * The main method to start the game.
	 * 
	 * @param args Command-line arguments (unused).
	 */
	public static void main(String[] args) {
		GameStateController gameStateController = new GameStateController();
		MainFrame mainFrame = new MainFrame();
		GameSceneController gameSceneController = new GameSceneController(mainFrame, gameStateController);

		gameSceneController.setActiveScene(GameScenes.LOADING);
		gameSceneController.renderActiveScene();

		// Load assets in a separate thread
		Thread thread = new Thread(() -> {
			AssetsPreloader.load();
			// Load default scene
			gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
		});
		thread.run();
	}
}
