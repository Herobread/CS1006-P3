package rgou;

import rgou.controllers.GameSceneController;
import rgou.model.Board;
import rgou.view.GameScenes;
import rgou.view.MainFrame;
import rgou.view.assetLoaders.AssetsPreloader;

public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		MainFrame mainFrame = new MainFrame();
		GameSceneController gameSceneController = new GameSceneController(mainFrame, board);

		// load assets
		Thread thread = new Thread(() -> {
			AssetsPreloader.load();
			// load default scene:
			gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
		});
		thread.run();

		// show loading screen
		gameSceneController.renderActiveScene();
	}
}