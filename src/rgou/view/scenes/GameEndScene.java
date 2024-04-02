package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.model.Board;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class GameEndScene extends GameSceneBase {
	private GameStateController gameStateController;

	public GameEndScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		Board board = gameStateController.getBoard();
		RenderScaleContext renderContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderContext.scaleFont());

		LabelBox text = new LabelBox(board.getVictoriousPlayer() + " won!");
		text.setBounds(renderContext.scaleRectangle(218, 157, 200, 19));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);

		ImageButton okButton = new ImageButton("buttons/ok.png");
		okButton.setBounds(renderContext.scaleRectangle(298, 179, 42, 20));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});
		add(okButton);
	}
}
