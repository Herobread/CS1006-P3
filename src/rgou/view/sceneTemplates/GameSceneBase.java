package rgou.view.sceneTemplates;

import javax.swing.JPanel;
import rgou.view.GameSceneController;

/**
 * Base abstract class for game scenes.
 * 
 * To add new scene:
 * - create new class in view/scenes/SomeNameScene.java that will implement
 * GameSceneBase
 * - in view/GameScenes add new enum that will represent the scene id
 * - in GameSceneController add new scene to initializeScenes() with new id and
 * scene object
 * 
 * Note: GameSceneController automatically clears scene
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
	 * 
	 * Note: GameSceneController automatically clears scene
	 */
	public abstract void run();
}
