package rgou.view.scenes;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.ImageButton;
import rgou.view.components.RenderContext;
import rgou.view.sceneTemplates.GameSceneBase;

import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScene extends GameSceneBase {
	public MainMenuScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		RenderContext renderContext = new RenderContext(gameSceneController.getSceneScale());

		ImageButton play = new ImageButton("buttons/play-local.png");

		play.setBounds(renderContext.scaleRectangle(248, 226, 40, 40));

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Clicked");
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
			}
		});

		add(play);

		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		repaint();
	}
}
