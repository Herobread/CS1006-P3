package rgou.controllers.remote;

import java.net.ServerSocket;
import java.net.Socket;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.agents.Agent;
import rgou.controllers.agents.LocalAgent;
import rgou.controllers.agents.RemoteAgent;
import rgou.model.remote.RemoteConfig;
import rgou.model.remote.RemoteStatus;
import rgou.view.GameScenes;

/**
 * Utility class for creating remote connections.
 */
public class RemoteConnector {
	/**
	 * Creates a connection based on the remote configuration.
	 * 
	 * @param gameSceneController The game scene controller.
	 * @param gameStateController The game state controller.
	 * @throws Exception if an error occurs during connection creation.
	 */
	public static void createConnection(GameSceneController gameSceneController,
			GameStateController gameStateController) throws Exception {
		RemoteConfig remoteConfig = gameStateController.getRemoteConfig();

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
		Socket socket = new Socket(gameStateController.getRemoteConfig().getHostname(),
				Integer.parseInt(gameStateController.getRemoteConfig().getPort()));

		RemoteStatus remoteStatus = gameStateController.getRemoteStatus();
		remoteStatus.setStatus("connecting...");
		Thread.sleep(100);
		remoteStatus.setStatus("connected");
		Thread.sleep(100);

		Agent clientPlayer = new LocalAgent("dark", gameStateController.getBoard());
		gameStateController.setPlayerAgent("dark", clientPlayer);

		Agent remotePlayer = new RemoteAgent(
				"light",
				gameStateController,
				gameSceneController,
				socket);
		gameStateController.setPlayerAgent("light", remotePlayer);

		gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
	}

	private static void createServer(GameSceneController gameSceneController, GameStateController gameStateController)
			throws Exception {

		try (ServerSocket ss = new ServerSocket(Integer.parseInt(gameStateController.getRemoteConfig().getPort()));) {
			Socket socket = ss.accept();

			RemoteStatus remoteStatus = gameStateController.getRemoteStatus();
			remoteStatus.setStatus("connecting...");
			Thread.sleep(100);
			remoteStatus.setStatus("connected");
			Thread.sleep(100);

			Agent clientPlayer = new LocalAgent("light", gameStateController.getBoard());
			gameStateController.setPlayerAgent("light", clientPlayer);

			Agent remotePlayer = new RemoteAgent(
					"dark",
					gameStateController,
					gameSceneController,
					socket);
			gameStateController.setPlayerAgent("dark", remotePlayer);

			gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
		}
	}
}
