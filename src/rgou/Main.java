package rgou;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.MainFrame;
import rgou.view.assets.AssetsPreloader;

public class Main {
	public static void main(String[] args) {
		// main frame
		MainFrame mainFrame = new MainFrame();
		GameSceneController gameSceneController = new GameSceneController(mainFrame);

		// load assets
		Thread thread = new Thread(() -> {
			AssetsPreloader.load();

			gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
		});
		thread.run();

		gameSceneController.renderActiveScene();
	}
}