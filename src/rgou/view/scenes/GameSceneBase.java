package rgou.view.scenes;

import javax.swing.JPanel;
import rgou.view.GameSceneController;

public abstract class GameSceneBase extends JPanel implements Runnable {
	protected GameSceneController gameSceneController;

	public GameSceneBase(GameSceneController gameSceneController) {
		this.gameSceneController = gameSceneController;
	}

	public abstract void run();
}
