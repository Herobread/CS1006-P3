package rgou.view.components.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import rgou.controllers.GameStateController;
import rgou.controllers.agents.Agent;
import rgou.model.Board;
import rgou.model.dice.DiceRollResult;
import rgou.model.dice.DiceRollsResult;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;

public class DicePanel extends JComponent {
	private final int TOTAL_DICES = 4;
	private GameStateController gameStateController;
	private ImageBox number;
	private ImageBox[] diceImages = new ImageBox[TOTAL_DICES];

	public DicePanel(
			RenderScaleContext renderScaleContext,
			GameStateController gameStateController,
			String player,
			boolean isNoMovesWarningShown) {

		this.gameStateController = gameStateController;
		Board board = this.gameStateController.getBoard();

		setOpaque(false);
		setLayout(null);

		if (isNoMovesWarningShown) {
			LabelBox noMovesWarning = new LabelBox("No moves");
			noMovesWarning.setBounds(renderScaleContext.scaleRectangle(20, 46, 71, 19));
			add(noMovesWarning);
			return;
		}

		if (!player.equals(board.getActivePlayer())) {
			return;
		}

		DiceRollsResult diceRollsResult = board.getLastDiceRollsResult();
		DiceRollResult[] diceRollResults = diceRollsResult.getDiceRollResults();

		String numberTexture = diceRollsResult.getTotal() + "";

		if (board.isRollAvailable()) {
			numberTexture = "unknown";
		}

		number = new ImageBox("dice/numbers/" + numberTexture + ".png");
		number.setBounds(renderScaleContext.scaleRectangle(39, 0, 32, 42));
		add(number);

		for (int i = 0; i < TOTAL_DICES; i++) {
			String texture = diceRollResults[i].getTextureName();
			diceImages[i] = new ImageBox(texture);
			diceImages[i].setBounds(renderScaleContext.scaleRectangle(30 * i, 57, 20, 19));
			add(diceImages[i]);
		}

		Agent currentAgent = gameStateController.getPlayerAgent(player);

		if (board.isRollAvailable() && currentAgent.isInputRequired()) {
			ImageButton rollButton = new ImageButton("buttons/roll.png");
			rollButton.setBounds(renderScaleContext.scaleRectangle(34, 91, 42, 20));
			rollButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.roll();
				}
			});
			add(rollButton);
		}
	}
}
