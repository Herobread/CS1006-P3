package rgou.view.scenes;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScene extends GameSceneBase {
	public MainMenuScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		JPanel panel = new JPanel();

		JButton button = new JButton("Click Me");

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Clicked");
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
				gameSceneController.renderActiveScene();
			}
		});

		panel.add(button);

		this.add(panel);
	}
}
