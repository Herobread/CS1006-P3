package rgou.view.scenes;

import javax.swing.JPanel;
import rgou.view.GameSceneController;

/**
 * Base abstract class for game scenes.
 */
public abstract class GameSceneBase extends JPanel implements Runnable {
	/**
	 * The controller responsible for managing game scenes.
	 */
	protected GameSceneController gameSceneController;

	/**
	 * Constructs a GameSceneBase object with the specified game scene controller.
	 * 
	 * @param gameSceneController the controller responsible for managing game
	 *                            scenes
	 */
	public GameSceneBase(GameSceneController gameSceneController) {
		this.gameSceneController = gameSceneController;
		setLayout(null);
	}

	/**
	 * Runs the rendering logic associated with the game scene.
	 */
	public abstract void run();
}
