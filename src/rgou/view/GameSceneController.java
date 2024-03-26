package rgou.view;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import rgou.view.exceptions.InvalidSceneException;
import rgou.view.scenes.GameOverScene;
import rgou.view.scenes.GameSceneBase;
import rgou.view.scenes.GameplayScene;
import rgou.view.scenes.MainMenuScene;

public class GameSceneController {
	private JFrame mainFrame;
	private Map<Scenes, GameSceneBase> sceneMap;
	private Scenes activeScene = Scenes.MAIN_MENU;
	private GameSceneBase activeSceneObject = null;
	private Timer timer;

	public GameSceneController(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		sceneMap = new EnumMap<>(Scenes.class);

		initializeScenes();

		setActiveScene(Scenes.MAIN_MENU);
	}

	private void initializeScenes() {
		MainMenuScene mainMenuScene = new MainMenuScene(this);
		GameplayScene gameplayScene = new GameplayScene(this);
		GameOverScene gameOverScene = new GameOverScene(this);

		sceneMap.put(Scenes.MAIN_MENU, mainMenuScene);
		sceneMap.put(Scenes.GAMEPLAY, gameplayScene);
		sceneMap.put(Scenes.GAME_OVER, gameOverScene);
	}

	public void renderActiveScene() {
		if (activeSceneObject != null) {
			System.out.println("rendering");
			GameSceneBase currentScene = getScene(activeScene);
			currentScene.run();
			mainFrame.getContentPane().removeAll();
			mainFrame.getContentPane().add(currentScene);
			mainFrame.revalidate();
			mainFrame.repaint();
		} else {
			System.err.println("no scene");
		}
	}

	public GameSceneBase getScene(Scenes scene) throws InvalidSceneException {
		GameSceneBase sceneObject = sceneMap.get(scene);
		if (sceneObject == null) {
			throw new InvalidSceneException();
		}
		return sceneObject;
	}

	public void setActiveScene(Scenes scene) throws InvalidSceneException {
		System.out.println("Setting active scene to: " + scene);
		activeSceneObject = getScene(scene);
		activeScene = scene;
	}

	public Scenes getActiveScene() {
		return activeScene;
	}

	public JPanel getActiveScenePanel() {
		return activeSceneObject;
	}

	// Don't forget to stop the timer when your application exits
	public void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
	}
}
