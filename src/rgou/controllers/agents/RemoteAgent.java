package rgou.controllers.agents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.remote.RemoteAction;
import rgou.model.Event;
import rgou.model.dice.DiceRollsResult;

public class RemoteAgent extends Agent {
	GameStateController gameStateController;
	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter writer;

	public RemoteAgent(
			String player,
			GameStateController gameStateController,
			GameSceneController gameSceneController,
			Socket conn) throws Exception {
		super(player, gameStateController.getBoard());
		this.gameStateController = gameStateController;
		this.socket = conn;

		outputStream = socket.getOutputStream();
		writer = new PrintWriter(outputStream, true);

		// disable dice roll and board panel inputs for current player
		isInputRequired = false;

		// add on move or dice roll event listener
		board.addEventListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Event event = (Event) e.getSource();

					switch (event.getType()) {
						case ROLL:
							onDiceRoll(event);
							break;
						case MOVE:
							onMove(event);
							break;
						default:
							break;
					}
				} catch (Exception err) {
					throw new RuntimeException(err.getMessage());
				}
			}
		});

		////////// handle remote requests
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream input = socket.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));

					String action;
					while ((action = reader.readLine()) != null) {
						handleRemotePlayerAction(action);
					}

					// Close the connection
					socket.close();
				} catch (IOException err) {
					throw new RuntimeException(err.getMessage());
				}
			}
		}).start();

	}

	private void handleRemotePlayerAction(String action) {
		RemoteAction remoteAction = new RemoteAction(action);

		switch (remoteAction.getAction()) {
			case "move":
				int[] metadata = remoteAction.getMetadata();
				int x = metadata[0];
				int y = metadata[1];
				move(x, y);
				break;
			case "roll":
				DiceRollsResult diceRollsResult = new DiceRollsResult(action);
				board.rollCustomDice(diceRollsResult);
				break;
			default:
				break;
		}
	}

	// observe any dice roll
	private void onDiceRoll(Event e) throws Exception {
		String activePlayer = e.getPlayer();

		if (activePlayer.equals(player)) {
			// dice rolled by this Agent aka remote player
			return;
		}

		// handle dice rolled by local player and send to the remote:
		DiceRollsResult diceRollsResult = board.getLastDiceRollsResult();
		writer.println(diceRollsResult.toActionString());
	}

	// observe any move
	private void onMove(Event e) throws Exception {
		String activePlayer = e.getPlayer();

		if (activePlayer.equals(player)) {
			return; // move made by remote - ignore
		}

		// local player move - send data to remote:
		writer.println("move," + e.getX() + "|" + e.getY());
	}

	@Override
	public String toString() {
		return "RemoteAgent [isInputRequired=" + isInputRequired + "]";
	}
}
