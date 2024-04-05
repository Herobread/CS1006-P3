package rgou.controllers;

import java.util.EnumMap;
import java.util.Map;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageBox;
import rgou.view.exceptions.InvalidSceneException;
import rgou.view.sceneTemplates.GameSceneBase;
import rgou.view.scenes.GameEndScene;
import rgou.view.scenes.GameplayScene;
import rgou.view.scenes.LoadingScene;
import rgou.view.scenes.MainMenuScene;
import rgou.view.scenes.RemoteOptionsScene;
import rgou.view.scenes.ai.SelectAIOptionsScene;
import rgou.view.scenes.remote.HostScene;
import rgou.view.scenes.remote.JoinScene;
import rgou.view.scenes.remote.loading.LoadingRemoteScene;

/**
 * Controls the switching and rendering of game scenes.
 */
public class GameSceneController {
	private final int ORIGINAL_SCENE_WIDTH = 636;
	private final int ORIGINAL_SCENE_HEIGHT = 358;
	private final double ORIGINAL_ASPECT_RATIO = (double) ORIGINAL_SCENE_WIDTH / ORIGINAL_SCENE_HEIGHT;

	private JFrame mainFrame;
	private Map<GameScenes, GameSceneBase> sceneMap;
	private GameScenes activeScene = GameScenes.LOADING;
	private GameSceneBase activeScenePanel = null;
	private GameStateController gameStateController = null;

	/**
	 * Constructs a GameSceneController with the specified main frame.
	 * 
	 * @param mainFrame the main frame of the game
	 */
	public GameSceneController(JFrame mainFrame, GameStateController gameStateController) {
		this.mainFrame = mainFrame;
		this.gameStateController = gameStateController;

		sceneMap = new EnumMap<>(GameScenes.class);

		initializeScenes();

		addResizeListener();
	}

	private void addResizeListener() {
		// Resize panel inside of the frame when frame is resized
		mainFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				renderActiveScene();
			}
		});
	}

	private void initializeScenes() {
		sceneMap.put(GameScenes.LOADING, new LoadingScene(this));
		sceneMap.put(GameScenes.MAIN_MENU, new MainMenuScene(this, gameStateController));
		sceneMap.put(GameScenes.GAMEPLAY, new GameplayScene(this, gameStateController));
		sceneMap.put(GameScenes.GAME_END, new GameEndScene(this, gameStateController));
		sceneMap.put(GameScenes.SELECT_REMOTE, new RemoteOptionsScene(this, gameStateController));
		sceneMap.put(GameScenes.SELECT_REMOTE_HOST, new HostScene(this, gameStateController));
		sceneMap.put(GameScenes.SELECT_REMOTE_JOIN, new JoinScene(this, gameStateController));
		sceneMap.put(GameScenes.SELECT_REMOTE_LOADING, new LoadingRemoteScene(this, gameStateController));
		sceneMap.put(GameScenes.SELECT_AI, new SelectAIOptionsScene(this, gameStateController));
	}

	/**
	 * Renders the active scene.
	 * 
	 * Removes all content from scene, then runs it, and finally repaints.
	 */
	public void renderActiveScene() {
		if (activeScenePanel == null) {
			throw new InvalidSceneException();
		}

		GameSceneBase currentScene = getScene(activeScene);
		currentScene.removeAll();
		currentScene.setOpaque(false);
		activeScenePanel.setBounds(getPanelTargetBounds());
		currentScene.run();

		mainFrame.getContentPane().removeAll();
		ImageBox bg = new ImageBox("ui/background.png");
		bg.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.getContentPane().add(currentScene);
		mainFrame.getContentPane().add(bg);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	public double getSceneScale() {
		return (double) activeScenePanel.getWidth() / ORIGINAL_SCENE_WIDTH;
	}

	// Calculates how to scale the panel inside the frame while keeping aspect ratio
	private Rectangle getPanelTargetBounds() {
		int frameWidth = mainFrame.getWidth();
		int frameHeight = mainFrame.getHeight();

		double targetWidth, targetHeight;

		double frameAspectRatio = (double) frameWidth / frameHeight;

		if (frameAspectRatio > ORIGINAL_ASPECT_RATIO) {
			// Frame is wider than the desired aspect ratio
			targetHeight = frameHeight;
			targetWidth = targetHeight * ORIGINAL_ASPECT_RATIO;
		} else {
			// Frame is narrower or equal to the desired aspect ratio
			targetWidth = frameWidth;
			targetHeight = targetWidth / ORIGINAL_ASPECT_RATIO;
		}

		// Center frame
		int x = (int) ((frameWidth - targetWidth) / 2);
		int y = (int) ((frameHeight - targetHeight) / 2);

		return new Rectangle(x, y - 10, (int) targetWidth, (int) targetHeight);
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
		activeScenePanel = getScene(scene);
		activeScene = scene;
		renderActiveScene();
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
		return activeScenePanel;
	}
}
