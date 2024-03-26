package rgou.view.scenes;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScene extends GameSceneBase {
	public MainMenuScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		JButton button = new JButton("Go to gameplay panel");

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Clicked");
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
				gameSceneController.renderActiveScene();
			}
		});

		add(button);
	}
}
