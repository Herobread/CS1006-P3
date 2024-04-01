package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import rgou.controllers.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class RemoteOptionsScene extends GameSceneBase {
	public RemoteOptionsScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		RenderScaleContext renderScaleContext = new RenderScaleContext(gameSceneController.getSceneScale());

		ImageButton exit = new ImageButton("buttons/cross.png");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});
		exit.setBounds(renderScaleContext.scaleRectangle(596, 10, 20, 20));
		add(exit);

		LabelBox nothing = new LabelBox("nothing here yet");
		nothing.setBounds(renderScaleContext.scaleRectangle(20, 20, 150, 19));
		add(nothing);
	}
}
