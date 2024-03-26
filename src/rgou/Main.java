package rgou;

import rgou.view.GameSceneController;
import rgou.view.MainFrame;

public class Main {
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		GameSceneController gameSceneController = new GameSceneController(mainFrame);

		gameSceneController.renderActiveScene();
	}
}