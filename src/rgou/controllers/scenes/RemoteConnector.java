package rgou.controllers.scenes;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.agents.Agent;
import rgou.controllers.agents.LocalAgent;
import rgou.controllers.agents.RemoteAgent;
import rgou.model.remote.RemoteConfig;
import rgou.model.remote.RemoteStatus;

public class RemoteConnector {
	public static void createConnection(GameSceneController gameSceneController,
			GameStateController gameStateController) throws Exception {
		RemoteConfig remoteConfig = gameStateController.getRemoteConfig();

		System.out.println("connecting");
		System.out.println(remoteConfig);

		switch (remoteConfig.getRemoteType()) {
			case CLIENT:
				createClient(gameSceneController, gameStateController);
				break;
			case SERVER:
				createServer(gameSceneController, gameStateController);
				break;
			default:
				break;
		}

	}

	private static void createClient(GameSceneController gameSceneController, GameStateController gameStateController)
			throws Exception {
		// TODO
	}

	private static void createServer(GameSceneController gameSceneController, GameStateController gameStateController)
			throws Exception {
		// TODO

		// add some network code here

		// change status for visual info:
		RemoteStatus remoteStatus = gameStateController.getRemoteStatus();

		remoteStatus.setStatus("connecting...");
		Thread.sleep(1000);
		remoteStatus.setStatus("connected");
		Thread.sleep(1000);

		// set up local player agent
		Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		gameStateController.setPlayerAgent("light", lightAgent);

		// set up remote agent
		// pass some other stuff to remote agent, if needed
		// remote agent will observe local player actions and handle remote player
		// see code inside of agent
		Agent darkAgent = new RemoteAgent(
				"dark",
				gameStateController,
				gameSceneController);
		gameStateController.setPlayerAgent("dark", darkAgent);

		// switch to gameplay if successful connection:
		// gameSceneController.setActiveScene(GameScenes.GAMEPLAY);

		// or throw an exception if something goes wrong:
		throw new Exception("networking was not implemented");
	}
}
