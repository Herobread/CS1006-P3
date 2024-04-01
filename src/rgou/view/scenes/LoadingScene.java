package rgou.view.scenes;

import rgou.controllers.GameSceneController;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class LoadingScene extends GameSceneBase {
	public LoadingScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		RenderScaleContext renderScaleContext = new RenderScaleContext(gameSceneController.getSceneScale());

		ImageBox loading = new ImageBox("ui/loading.png");
		loading.setBounds(renderScaleContext.scaleRectangle(255, 171, 125, 16));
		add(loading);

	}
}
