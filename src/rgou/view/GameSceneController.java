package rgou.view;

import java.util.EnumMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import rgou.view.exceptions.InvalidSceneException;
import rgou.view.sceneTemplates.GameSceneBase;
import rgou.view.scenes.GameOverScene;
import rgou.view.scenes.GameplayScene;
import rgou.view.scenes.MainMenuScene;

/**
 * Controls the switching and rendering of game scenes.
 */
public class GameSceneController {
	private final int EXPECTED_WIDTH = 636;
	private final int EXPECTED_HEIGHT = 358;
	private final double ASPECT_RATIO = (double) EXPECTED_WIDTH / EXPECTED_HEIGHT;

	private JFrame mainFrame;
	private Map<GameScenes, GameSceneBase> sceneMap;
	private GameScenes activeScene = GameScenes.MAIN_MENU;
	private GameSceneBase activeScenePanel = null;

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

		addResizeListener();
	}

	private void addResizeListener() {
		// resize panel inside of the frame
		// when frame is resized
		mainFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				renderActiveScene();
			}
		});
	}

	private void initializeScenes() {
		sceneMap.put(GameScenes.MAIN_MENU, new MainMenuScene(this));
		sceneMap.put(GameScenes.GAMEPLAY, new GameplayScene(this));
		sceneMap.put(GameScenes.GAME_OVER, new GameOverScene(this));

		// add new scenes:
		// sceneMap.put(scene name, Class that extends GameSceneBase);
	}

	/**
	 * Renders the active scene.
	 * 
	 * removes all content from scene
	 * then runs it
	 * and finally, repaints
	 */
	public void renderActiveScene() {
		if (activeScenePanel == null) {
			throw new InvalidSceneException();
		}

		GameSceneBase currentScene = getScene(activeScene);
		currentScene.removeAll();
		activeScenePanel.setBounds(getPanelTargetBounds());
		currentScene.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		currentScene.run();

		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().add(currentScene);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	public double getSceneScale() {
		return (double) activeScenePanel.getWidth() / EXPECTED_WIDTH;
	}

	// calculates how to scale the panel inside the frame,
	// while keeping aspect ratio
	private Rectangle getPanelTargetBounds() {
		int frameWidth = mainFrame.getWidth();
		int frameHeight = mainFrame.getHeight();

		double targetWidth, targetHeight;

		double frameAspectRatio = (double) frameWidth / frameHeight;

		if (frameAspectRatio > ASPECT_RATIO) {
			// frame is wider than the desired aspect ratio
			targetHeight = frameHeight;
			targetWidth = targetHeight * ASPECT_RATIO;
		} else {
			// frame is narrower or equal to the desired aspect ratio
			targetWidth = frameWidth;
			targetHeight = targetWidth / ASPECT_RATIO;
		}

		// center frame
		int x = (int) ((frameWidth - targetWidth) / 2);
		int y = (int) ((frameHeight - targetHeight) / 2);

		return new Rectangle(x, y - 20, (int) targetWidth, (int) targetHeight);
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
