package rgou.view.scenes.remote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class JoinScene extends GameSceneBase {
	GameStateController gameStateController;

	public JoinScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		RenderScaleContext renderScaleContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderScaleContext.scaleFont(16));

		ImageButton exit = new ImageButton("buttons/cross.png");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});
		exit.setBounds(renderScaleContext.scaleRectangle(596, 10, 20, 20));
		add(exit);

		LabelBox text = new LabelBox("Join");
		text.setBounds(renderScaleContext.scaleRectangle(221, 10, 200, 19));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);
	}
}
