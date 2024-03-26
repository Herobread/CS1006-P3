package rgou.view;

import rgou.view.scenes.MainMenuScene;

import rgou.view.scenes.GameOverScene;
import rgou.view.scenes.GameplayScene;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameSceneController {
	private static GameSceneController instance;
	private JFrame frame;

	private GameSceneController() {
		initialize();
	}

	public static GameSceneController getInstance() {
		if (instance == null) {
			instance = new GameSceneController();
		}
		return instance;
	}

	private void initialize() {
		frame = new JFrame("My Game");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void showMainMenu() {
		MainMenuScene mainMenuScene = new MainMenuScene();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(mainMenuScene);
		frame.revalidate();
		frame.repaint();
	}

	public void startGameplay() {
		GameplayScene gameplayScene = new GameplayScene();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(gameplayScene);
		frame.revalidate();
		frame.repaint();
	}

	public void showGameOver() {
		GameOverScene gameOverScene = new GameOverScene();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(gameOverScene);
		frame.revalidate();
		frame.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GameSceneController.getInstance().showMainMenu(); // Show main menu initially
		});
	}
}
