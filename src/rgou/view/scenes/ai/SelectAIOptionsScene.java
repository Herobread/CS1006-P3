package rgou.view.scenes.ai;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.scenes.SelectAIOptionsController;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

public class SelectAIOptionsScene extends GameSceneBase {
	GameStateController gameStateController;

	public SelectAIOptionsScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		RenderScaleContext renderScaleContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderScaleContext.scaleFont());

		ImageButton exit = new ImageButton("buttons/cross.png");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});
		exit.setBounds(renderScaleContext.scaleRectangle(596, 10, 20, 20));
		add(exit);

		LabelBox title = new LabelBox("Choose AI difficulty");
		title.setBounds(renderScaleContext.scaleRectangle(218, 10, 200, 19));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title);

		ImageButton simpleAiButton = new ImageButton("buttons/ai-simple.png");
		simpleAiButton.setBounds(renderScaleContext.scaleRectangle(163, 138, 60, 60));
		simpleAiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectAIOptionsController.handleSimpleAISelected(gameSceneController, gameStateController);
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
			}
		});
		add(simpleAiButton);

		ImageButton advancedAiButton = new ImageButton("buttons/ai-advanced.png");
		advancedAiButton.setBounds(renderScaleContext.scaleRectangle(391, 138, 60, 60));
		advancedAiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectAIOptionsController.handleAdvancedAISelected(gameSceneController, gameStateController);
				gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
			}
		});
		add(advancedAiButton);

		LabelBox simpleAIDescription1 = new LabelBox("LightThinker");
		simpleAIDescription1.setBounds(renderScaleContext.scaleRectangle(90, 216, 208, 19));
		simpleAIDescription1.setHorizontalAlignment(SwingConstants.CENTER);
		add(simpleAIDescription1);

		LabelBox simpleAIDescription2 = new LabelBox("(normal)");
		simpleAIDescription2.setBounds(renderScaleContext.scaleRectangle(90, 235, 208, 19));
		simpleAIDescription2.setHorizontalAlignment(SwingConstants.CENTER);
		add(simpleAIDescription2);

		LabelBox advancedAIDescription1 = new LabelBox("Overthinker");
		advancedAIDescription1.setBounds(renderScaleContext.scaleRectangle(317, 216, 208, 19));
		advancedAIDescription1.setHorizontalAlignment(SwingConstants.CENTER);
		add(advancedAIDescription1);

		LabelBox advancedAIDescription2 = new LabelBox("(difficult)");
		advancedAIDescription2.setBounds(renderScaleContext.scaleRectangle(317, 235, 208, 19));
		advancedAIDescription2.setHorizontalAlignment(SwingConstants.CENTER);
		add(advancedAIDescription2);
	}
}
