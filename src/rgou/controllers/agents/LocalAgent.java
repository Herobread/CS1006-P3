package rgou.controllers.agents;

import rgou.model.Board;

/**
 * Represents a local player agent.
 */
public class LocalAgent extends Agent {
	/**
	 * Initializes a LocalAgent with the specified player and board.
	 * Input is always required for a local agent.
	 * 
	 * @param player The current player team (light or dark).
	 * @param board  The board to interact with.
	 */
	public LocalAgent(String player, Board board) {
		super(player, board);
		isInputRequired = true;
	}
}
