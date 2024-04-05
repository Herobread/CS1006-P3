package rgou.controllers.scenes;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.agents.AIAgent;
import rgou.controllers.agents.Agent;
import rgou.controllers.agents.LocalAgent;
import rgou.controllers.listeners.BoardListenerRebinder;
import rgou.model.Board;

public class SelectAIOptionsController {
	private static void resetBoard(GameStateController gameStateController) {
		Board newBoard = new Board();
		newBoard = BoardListenerRebinder.rebind(gameStateController.getBoard(), newBoard);
		gameStateController.setBoard(newBoard);
	}

	public static void handleAdvancedAISelected(GameSceneController gameSceneController,
			GameStateController gameStateController) {
		resetBoard(gameStateController);

		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		AIAgent darkAgent = new AIAgent("dark", gameStateController.getBoard());

		darkAgent.setAdvancedAI(true);

		gameStateController.setPlayerAgent("light", lightAgent);
		gameStateController.setPlayerAgent("dark", darkAgent);
	}

	public static void handleSimpleAISelected(GameSceneController gameSceneController,
			GameStateController gameStateController) {
		resetBoard(gameStateController);

		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		AIAgent darkAgent = new AIAgent("dark", gameStateController.getBoard());

		darkAgent.setAdvancedAI(false);

		gameStateController.setPlayerAgent("light", lightAgent);
		gameStateController.setPlayerAgent("dark", darkAgent);

	}
}
