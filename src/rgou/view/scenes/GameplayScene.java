package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import rgou.view.GameSceneController;
import rgou.view.Scenes;

public class GameplayScene extends GameSceneBase {
	private int count = 0;

	public GameplayScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		removeAll();
		JButton button = new JButton("go to main menu");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(Scenes.MAIN_MENU);
				gameSceneController.renderActiveScene();
			}
		});
		add(button);

		JButton button2 = new JButton("increment " + count);

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count += 1;
				gameSceneController.renderActiveScene();
			}
		});
		add(button2);

		repaint();
		// gameSceneController.renderActiveScene();
		System.out.println("running...");
	}
}
