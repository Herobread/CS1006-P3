package rgou.view;

import java.util.EnumMap;
import java.util.Map;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import rgou.view.exceptions.InvalidSceneException;
import rgou.view.scenes.GameOverScene;
import rgou.view.scenes.GameSceneBase;
import rgou.view.scenes.GameplayScene;
import rgou.view.scenes.MainMenuScene;

/**
 * Controls the switching and rendering of game scenes.
 */
public class GameSceneController {
	// private final int WIDTH = 1280;
	// private final int HEIGHT = 720;
	// private final double ASPECT_RATIO = (double) WIDTH / HEIGHT;

	private JFrame mainFrame;
	private Map<GameScenes, GameSceneBase> sceneMap;
	private GameScenes activeScene = GameScenes.MAIN_MENU;
	private GameSceneBase activeSceneObject = null;

	/**
	 * Constructs a GameSceneController with the specified main frame.
	 * 
	 * @param mainFrame the main frame of the game
	 */
	public GameSceneController(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		sceneMap = new EnumMap<>(GameScenes.class);

		initializeScenes();

		setActiveScene(GameScenes.MAIN_MENU);
	}

	private void initializeScenes() {
		MainMenuScene mainMenuScene = new MainMenuScene(this);
		GameplayScene gameplayScene = new GameplayScene(this);
		GameOverScene gameOverScene = new GameOverScene(this);

		sceneMap.put(GameScenes.MAIN_MENU, mainMenuScene);
		sceneMap.put(GameScenes.GAMEPLAY, gameplayScene);
		sceneMap.put(GameScenes.GAME_OVER, gameOverScene);
	}

	/**
	 * Renders the active scene.
	 * 
	 * removes all content from scene
	 * then runs it
	 * and finally, repaints
	 */
	public void renderActiveScene() {
		if (activeSceneObject == null) {
			throw new InvalidSceneException();
		}

		GameSceneBase currentScene = getScene(activeScene);
		currentScene.removeAll();
		currentScene.run();
		currentScene.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// currentScene.setBackground(Color.LIGHT_GRAY);

		renderSceneOnMainFrame(currentScene);
	}

	private void renderSceneOnMainFrame(GameSceneBase scene) {
		mainFrame.getContentPane().removeAll();
		mainFrame.setBackground(Color.BLACK);
		mainFrame.getContentPane().add(scene);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	/**
	 * Retrieves the scene object associated with the specified scene.
	 * 
	 * @param scene the scene enumeration
	 * @return the scene object
	 * @throws InvalidSceneException if the scene is not valid
	 */
	public GameSceneBase getScene(GameScenes scene) throws InvalidSceneException {
		GameSceneBase sceneObject = sceneMap.get(scene);
		if (sceneObject == null) {
			throw new InvalidSceneException();
		}
		return sceneObject;
	}

	/**
	 * Sets the active scene.
	 * 
	 * @param scene the scene enumeration to set as active
	 * @throws InvalidSceneException if the scene is not valid
	 */
	public void setActiveScene(GameScenes scene) throws InvalidSceneException {
		System.out.println("Setting active scene to: " + scene);
		activeSceneObject = getScene(scene);
		activeScene = scene;
	}

	/**
	 * Gets the active scene.
	 * 
	 * @return the active scene enum
	 */
	public GameScenes getActiveScene() {
		return activeScene;
	}

	/**
	 * Gets the panel of the active scene.
	 * 
	 * @return the panel of the active scene
	 */
	public JPanel getActiveScenePanel() {
		return activeSceneObject;
	}
}
