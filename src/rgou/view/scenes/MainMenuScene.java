package rgou.view.scenes;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.ImageButton;

import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScene extends GameSceneBase {
	public MainMenuScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		double scale = gameSceneController.getSceneScale();
		System.out.println(scale);
		System.out.println("rerender");

		ImageButton play = new ImageButton("assets/textures/buttons/play-local.png");
		play.setBounds((int) (248 * scale), (int) (226 * scale), (int) (40 * scale), (int) (40 * scale));

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Clicked");
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
				// gameSceneController.renderActiveScene();
			}
		});
		play.setOpacity(0.5f);
		add(play);

		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		setPreferredSize(new Dimension((int) (300 * scale), 100));

		repaint();
	}
}
