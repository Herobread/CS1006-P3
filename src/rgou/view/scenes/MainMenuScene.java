package rgou.view.scenes;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.scenes.MainMenuController;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScene extends GameSceneBase {
	GameStateController gameStateController;

	public MainMenuScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		RenderScaleContext renderContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderContext.scaleFont());

		ImageBox logo = new ImageBox("ui/logo.png");
		logo.setBounds(renderContext.scaleRectangle(222, 92, 192, 99));
		add(logo);

		LabelBox text = new LabelBox("Play:");
		text.setBounds(renderContext.scaleRectangle(248, 200, 140, 19));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);

		ImageButton play = new ImageButton("buttons/play-local.png");
		play.setBounds(renderContext.scaleRectangle(248, 226, 40, 40));
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuController.onLocalPlayPressed(gameStateController);
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
			}
		});
		add(play);

		ImageButton playRemote = new ImageButton("buttons/play-remote.png");
		playRemote.setBounds(renderContext.scaleRectangle(298, 226, 40, 40));
		playRemote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clear board
				MainMenuController.onRemotePlayerPressed(gameStateController, gameSceneController);

				// go to config
				gameSceneController.setActiveScene(GameScenes.SELECT_REMOTE);
			}
		});
		add(playRemote);

		ImageButton playAi = new ImageButton("buttons/play-ai.png");
		playAi.setBounds(renderContext.scaleRectangle(348, 226, 40, 40));
		playAi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.SELECT_AI);
			}
		});
		add(playAi);
	}
}
