package rgou.controllers.scenes;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.agents.AIAgent;
import rgou.controllers.agents.Agent;
import rgou.controllers.agents.LocalAgent;
import rgou.controllers.listeners.BoardListenerRebinder;
import rgou.model.Board;

public class MainMenuController {
	private static void resetBoard(GameStateController gameStateController) {
		Board newBoard = new Board();
		newBoard = BoardListenerRebinder.rebind(gameStateController.getBoard(), newBoard);
		gameStateController.setBoard(newBoard);
	}

	public static void onLocalPlayPressed(GameStateController gameStateController) {
		resetBoard(gameStateController);

		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		Agent darkAgent = new LocalAgent("dark", gameStateController.getBoard());

		gameStateController.setPlayerAgent("light", lightAgent);
		gameStateController.setPlayerAgent("dark", darkAgent);
	}

	public static void onAIPlayerPressed(GameStateController gameStateController) {
		resetBoard(gameStateController);

		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		Agent darkAgent = new AIAgent("dark", gameStateController.getBoard());

		gameStateController.setPlayerAgent("light", lightAgent);
		gameStateController.setPlayerAgent("dark", darkAgent);
	}

	public static void onRemotePlayerPressed(GameStateController gameStateController,
			GameSceneController gameSceneController) {
		resetBoard(gameStateController);
	}
}
