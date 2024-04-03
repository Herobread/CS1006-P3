package rgou.controllers.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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

public class RemoteConnector {
	public static void createConnection(GameSceneController gameSceneController,
			GameStateController gameStateController) throws Exception {
		RemoteConfig remoteConfig = gameStateController.getRemoteConfig();

		System.out.println("creating connection for: " + remoteConfig);

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
		//Client client = new Client(gameSceneController, gameStateController);
		// TODO

		Socket socket = new Socket(gameStateController.getRemoteConfig().getHostname(), Integer.parseInt(gameStateController.getRemoteConfig().getPort()));
		System.out.println("Client connected to " + gameStateController.getRemoteConfig().getHostname() + " on port " + gameStateController.getRemoteConfig().getPort() + ".");
		
		RemoteStatus remoteStatus = gameStateController.getRemoteStatus();
		remoteStatus.setStatus("connecting...");
		Thread.sleep(1000);
		remoteStatus.setStatus("connected");
		Thread.sleep(1000);

		Agent clientPlayer = new LocalAgent("dark", gameStateController.getBoard());
		

		Agent clientOpponent = new RemoteAgent(
		"light",
		gameStateController,
		gameSceneController,
		socket);

		gameStateController.setPlayerAgent("light", clientOpponent);
		
		gameSceneController.setActiveScene(GameScenes.GAMEPLAY);
		
		InputStream inputStream = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		while (true){
			String serverResponse = reader.readLine();
			String[] parsedLine = serverResponse.split(":");
			for (String entry : parsedLine){
				System.out.println(entry);
			}
			if (parsedLine[0].equals("Move")){
				String[] coordinates = parsedLine[1].split(",");
				for (String entry: coordinates){
					System.out.println(entry);
				}
				boolean didMove = clientOpponent.move(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]));
				System.out.println(didMove);
			}
		}

	}

	private static void createServer(GameSceneController gameSceneController, GameStateController gameStateController)
			throws Exception {
			
		// TODO

		
		// add some network code here
		try {
			ServerSocket ss = new ServerSocket(Integer.parseInt(gameStateController.getRemoteConfig().getPort()));
			System.out.println("Server started ... listening on port " + gameStateController.getRemoteConfig().getPort() + " ...");
			
			Socket conn = ss.accept();
			System.out.println("Server got new connection request from " + conn.getInetAddress());


			RemoteStatus remoteStatus = gameStateController.getRemoteStatus();
			remoteStatus.setStatus("connecting...");
			Thread.sleep(1000);
			remoteStatus.setStatus("connected");
			Thread.sleep(1000);

			Agent serverPlayer = new LocalAgent("light", gameStateController.getBoard());
			gameStateController.setPlayerAgent("light", serverPlayer);

			Agent serverOpponent = new RemoteAgent(
			"dark",
			gameStateController,
			gameSceneController,
			conn);
			gameStateController.setPlayerAgent("dark", serverOpponent);

			gameSceneController.setActiveScene(GameScenes.GAMEPLAY);

			OutputStream outputStream = conn.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream, true);

		} catch (Exception e){
				e.printStackTrace();
		
		}
		
		// change status for visual info:

		// set up local player agent
		/*Agent lightAgent = new LocalAgent("light", gameStateController.getBoard());
		gameStateController.setPlayerAgent("light", lightAgent);

		// set up remote agent
		// pass some other stuff to remote agent, if needed
		// remote agent will observe local player actions and handle remote player
		// see code inside of agent
		Agent darkAgent = new RemoteAgent(
				"dark",
				gameStateController,
				gameSceneController);
		gameStateController.setPlayerAgent("dark", darkAgent);*/
		
		
		// switch to gameplay if successful connection:
		// gameSceneController.setActiveScene(GameScenes.GAMEPLAY);

		// or throw an exception if something goes wrong:
		//throw new Exception("networking was not implemented");
	}
}
