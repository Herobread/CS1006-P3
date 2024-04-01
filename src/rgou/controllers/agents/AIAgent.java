package rgou.controllers.agents;

import java.awt.Point;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.model.Board;
import rgou.model.Tile;
import rgou.model.dice.DiceRollsResult;
import rgou.utils.Pair;

public class AIAgent extends Agent {
	public AIAgent(String player, Board board) {
		super(player, board);

		// disable dice roll
		// and board panel inputs in future
		isInputRequired = false;

		board.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (board.getActivePlayer().equals(player) && board.isRollAvailable()) {
					moveAvailable();
				}
			}
		});
	}

	// any action that updated the board will automatically update the GUI
	// so, I think that this should trigger again when you get extra move
	@SuppressWarnings("unused") // remove this when implementing AI
	public void moveAvailable() {
		// for debug, just to see if it gets opportunity to move at the correct time
		System.out.println("AI is moving");

		/////////////////////////// get board data:
		// all available data:
		Board board = getBoard();
		// use board.someFunction to get stuff:

		///////////////////////////
		// get tiles array
		// each tile has info(isRossette, isFinish, isStart etc)
		// + pawn info in tile.getPawn(), returns "light" or "dark"
		// warning: getTeam returns hard coded colour of start and end, not pawn color
		Tile[][] tiles = board.getBoard();

		/////////////////////////
		// roll dice:
		// (we already check if roll is available, so just roll)
		roll();
		// get dice results:
		DiceRollsResult diceRollsResult = board.getLastDiceRollsResult();
		// get total dice score:
		int total = diceRollsResult.getTotal();

		//////////////////////
		// get player path with pawns:
		List<Pair<Point, String>> pathWithPawns = board.getPathWithPawns(player);

		/////////////////////////
		// check if move is available at all:
		// sometimes may not be available(rolled 0 or no moves)
		// if unavailable: return;
		boolean isMoveAvailable = board.isSelectMoveAvailable();

		//////////////////////
		// check where move will take you:
		Point targetPoint = board.getPossibleMove(1, 1);
		// and convert it to the Tile with more info:
		Tile targetTile = board.getTile(targetPoint);
		// then you can get info if it's rosette for example:
		boolean isRossete = targetTile.isRosette();

		//////////////////////
		// make a move:
		// returns true if moved thingy
		boolean didMove = move(2, 4);

		if (!didMove) {
			// skill issue:
			throw new RuntimeException("AI has skill issue, maybe");
		}
	}
}
