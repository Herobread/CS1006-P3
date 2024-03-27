package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.components.ui.DicePanel;
import rgou.view.sceneTemplates.GameSceneBase;

public class GameplayScene extends GameSceneBase {
	public GameplayScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		RenderScaleContext renderContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderContext.scaleFont());

		LabelBox turn = new LabelBox("black’s turn");
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		turn.setBounds(renderContext.scaleRectangle(262, 18, 112, 19));
		add(turn);

		LabelBox blackScore = new LabelBox("score: 1");
		blackScore.setBounds(renderContext.scaleRectangle(132, 67, 110, 19));
		blackScore.setHorizontalAlignment(SwingConstants.RIGHT);
		add(blackScore);

		LabelBox whiteScore = new LabelBox("score: 1");
		whiteScore.setBounds(renderContext.scaleRectangle(395, 67, 110, 19));
		add(whiteScore);

		ImageBox boardBg = new ImageBox("tiles/board-bg.png");
		boardBg.setBounds(renderContext.scaleRectangle(262, 41, 112, 292));
		add(boardBg);

		DicePanel dicePanel = new DicePanel(renderContext);
		dicePanel.setBounds(renderContext.scaleRectangle(109, 132, 110, 111));
		add(dicePanel);

		///////////////////////////////////////////////////////////////////////////

		ImageButton goBackButton = new ImageButton("buttons/cross.png");
		goBackButton.setBounds(renderContext.scaleRectangle(10, 10, 20, 20));
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
