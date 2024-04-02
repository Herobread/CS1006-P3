package rgou.controllers.scenes;

import rgou.controllers.GameStateController;
import rgou.controllers.agents.AIAgent;
import rgou.controllers.agents.Agent;
import rgou.controllers.agents.LocalAgent;
import rgou.model.Board;

public class MainMenuController {
	public static void onLocalPlayPressed(GameStateController gameStateController) {
		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		Agent darkAgent = new LocalAgent("dark", gameStateController.getBoard());

		// gameStateController.setBoard(new Board());
		gameStateController.setPlayerAgent("light", lightAgent);
		gameStateController.setPlayerAgent("dark", darkAgent);
	}

	public static void onAIPlayerPressed(GameStateController gameStateController) {
		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		Agent darkAgent = new AIAgent("dark", gameStateController.getBoard());

		// gameStateController.setBoard(new Board());
		gameStateController.setPlayerAgent("light", lightAgent);
		gameStateController.setPlayerAgent("dark", darkAgent);
	}
}
