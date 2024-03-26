package rgou;

import rgou.view.GameSceneController;
import rgou.view.MainFrame;

public class Main {
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		GameSceneController gameSceneController = new GameSceneController(frame);

		gameSceneController.renderActiveScene();
	}
}