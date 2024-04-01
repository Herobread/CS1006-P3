package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.model.Board;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.components.ui.BoardPanel;
import rgou.view.components.ui.DicePanel;
import rgou.view.sceneTemplates.GameSceneBase;

public class GameplayScene extends GameSceneBase {
	private Board board;
	private GameStateController gameStateController;
	private LabelBox turn;

	public GameplayScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameSceneController = gameSceneController;
		this.gameStateController = gameStateController;
		this.board = gameStateController.getBoard();

		board.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				gameSceneController.renderActiveScene();
			}
		});
	}

	public void run() {
		if (board.getVictoriousPlayer() != null) {
			gameSceneController.setActiveScene(GameScenes.GAME_END);
		}

		RenderScaleContext renderContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderContext.scaleFont());

		turn = new LabelBox(board.getActivePlayer() + "’s turn");
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		turn.setBounds(renderContext.scaleRectangle(262, 18, 112, 19));
		add(turn);

		// light info
		LabelBox lightStock = new LabelBox(board.getPlayerStock("light") + "x");
		lightStock.setBounds(renderContext.scaleRectangle(201, 51, 30, 19));
		lightStock.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lightStock);

		ImageBox lightDecorationalPawn = new ImageBox("pawns/pawn-light.png");
		lightDecorationalPawn.setBounds(renderContext.scaleRectangle(234, 52, 18, 18));
		add(lightDecorationalPawn);

		LabelBox lightScore = new LabelBox("score: " + board.getPlayerScore("light"));
		lightScore.setBounds(renderContext.scaleRectangle(150, 313, 102, 19));
		lightScore.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lightScore);

		// dark info
		LabelBox darkStock = new LabelBox("x" + board.getPlayerStock("dark"));
		darkStock.setBounds(renderContext.scaleRectangle(405, 52, 30, 19));
		add(darkStock);

		ImageBox darkDecorationalPawn = new ImageBox("pawns/pawn-dark.png");
		darkDecorationalPawn.setBounds(renderContext.scaleRectangle(384, 52, 18, 18));
		add(darkDecorationalPawn);

		LabelBox darkScore = new LabelBox(board.getPlayerScore("dark") + ": score");
		darkScore.setBounds(renderContext.scaleRectangle(384, 313, 102, 19));
		add(darkScore);

		// dice
		boolean isLightNoMoves = "light".equals(board.getNoMovesPlayerWarning());
		boolean isDarkNoMoves = "dark".equals(board.getNoMovesPlayerWarning());

		DicePanel dicePanelLight = new DicePanel(renderContext, gameStateController, "light", isLightNoMoves);
		dicePanelLight.setBounds(renderContext.scaleRectangle(109, 132, 110, 111));
		add(dicePanelLight);

		DicePanel dicePanelDark = new DicePanel(renderContext, gameStateController, "dark", isDarkNoMoves);
		dicePanelDark.setBounds(renderContext.scaleRectangle(417, 132, 110, 111));
		add(dicePanelDark);

		// board
		BoardPanel boardPanel = new BoardPanel(renderContext, gameStateController);
		boardPanel.setBounds(renderContext.scaleRectangle(262, 41, 112, 292));
		add(boardPanel);

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
