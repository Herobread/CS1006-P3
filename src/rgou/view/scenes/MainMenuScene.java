package rgou.view.scenes;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.ImageBox;
import rgou.view.components.ImageButton;
import rgou.view.components.RenderScaleContext;
import rgou.view.components.LabelBox;
import rgou.view.sceneTemplates.GameSceneBase;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import java.awt.Color;
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
		text.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(text);

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
