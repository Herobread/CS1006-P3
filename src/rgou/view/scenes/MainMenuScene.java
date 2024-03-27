package rgou.view.scenes;

import rgou.view.GameSceneController;
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
	public MainMenuScene(GameSceneController gameSceneController) {
		super(gameSceneController);
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
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
			}
		});

		add(play);

		repaint();
	}
}
