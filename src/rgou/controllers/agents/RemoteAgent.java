package rgou.controllers.agents;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.model.Board;
import rgou.model.Event;
import rgou.model.dice.DiceRollsResult;
import rgou.model.remote.RemoteConfig;

public class RemoteAgent extends Agent {
	GameStateController gameStateController;

	public RemoteAgent(
			String player,
			GameStateController gameStateController,
			GameSceneController gameSceneController) {
		super(player, gameStateController.getBoard());
		this.gameStateController = gameStateController;

		// disable dice roll and board panel inputs for current player
		isInputRequired = false;

		// any updates
		board.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("Board updated");
				// board updated
				// might not need this event handler, but it was created already, so why not
				//
				// maybe needed to sync boards between 2 users sometimes??
				// but then you will need to create a way to compress board to string and back
			}
		});

		// add on move or dice roll event listener
		board.addEventListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
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
			}
		});
	}

	private void onDiceRoll(Event e) {
		System.out.println("dice rolled");

		Board board = e.getBoard();
		String activePlayer = board.getActivePlayer();

		if (activePlayer.equals(player)) {
			// dice rolled by this Agent
			return;
		}

		// TODO

		// handle dice rolled by oponent:
		// aka local player
		// your code to send stuff to remote here
	}

	private void onMove(Event e) {
		System.out.println("pawn moved");

		Board board = e.getBoard();
		String activePlayer = board.getActivePlayer();

		if (activePlayer.equals(player)) {
			// move made by this Agent
			return;
		}

		// TODO

		// handle move by oponent:
		// aka local player
		// your code to send stuff to remote here
	}

	@Deprecated
	@SuppressWarnings("unused") // remove this in final version
	private void examples() {
		////////////////////////
		// get config:
		RemoteConfig remoteConfig = gameStateController.getRemoteConfig();
		String host = remoteConfig.getHostname();
		// see other things in RemoteConfig

		////////////////////////
		// get dice roll:
		DiceRollsResult diceRollsResult = board.getLastDiceRollsResult();
		// ( notice: roll>s< not roll_ )
		// from DiceRollsResult you can get action string:
		// "roll,1|2|3|4"
		// where 1234 are dice roll states
		String action = diceRollsResult.toActionString();
		// then you can send action to the remote client
		//
		// *insert your network code to send data in onDiceRoll or onMove above*
		//
		//
		// *insert your code to recieve data,
		// it will probably be some kind of event
		// listener that will get string*
		//
		// to make it easier to parse data from string to dice roll:
		// create a new constructor in DiceRollsResult from it
		// smth like DiceRollsResult(String whatever)
		//
		// to emulate roll dice with custom results:
		// board.rollCustomDice(DiceRollsResult roll)

		int x = 1; // ignore this
		int y = 1; // ignore this
		// after getting dice roll from another player and updating it's value in board
		//
		// move player:
		// this function moves pawn automatically
		move(x, y);
		// returns true on successful move
		// note: must be legal move and correct activePlayer
		// (for example when light player active: only light player can move light
		// pieces)
	}
}
