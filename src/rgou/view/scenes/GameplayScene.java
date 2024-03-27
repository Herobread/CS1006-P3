package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.ImageButton;
import rgou.view.components.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class GameplayScene extends GameSceneBase {
	public GameplayScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		RenderScaleContext renderContext = new RenderScaleContext(gameSceneController.getSceneScale());

		ImageButton goBackButton = new ImageButton("pawns/pawn-black.png");
		goBackButton.setBounds(renderContext.scaleRectangle(248, 226, 40, 40));
		goBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});

		add(goBackButton);

		repaint();
	}
}
