package rgou.controllers.agents;

import rgou.model.Board;

/**
 * Simple API to communicate between Board and
 * other types of players such as AI, remote, and local.
 */
public class Agent {
	/** Current player team, can be light or dark. */
	protected String player;
	protected boolean isInputRequired = true;

	protected Board board;

	/**
	 * Initializes an Agent with the specified player and board.
	 * 
	 * @param player The current player team (light or dark).
	 * @param board  The board to interact with.
	 */
	public Agent(String player, Board board) {
		this.board = board;
		this.player = player;
	}

	/**
	 * Checks if input is required from the agent.
	 * 
	 * @return true if input is required, false otherwise.
	 */
	public boolean isInputRequired() {
		return isInputRequired;
	}

	/**
	 * Sets whether input is required from the agent.
	 * 
	 * @param val true if input is required, false otherwise.
	 */
	public void setInputRequired(boolean val) {
		this.isInputRequired = val;
	}

	/**
	 * Rolls the dice.
	 */
	public void roll() {
		board.roll();
	}

	/**
	 * Moves a piece on the board.
	 * 
	 * @param x The x-coordinate of the move.
	 * @param y The y-coordinate of the move.
	 * @return true if the move was successful, false otherwise.
	 */
	public boolean move(int x, int y) {
		return board.makeMove(x, y);
	}

	/**
	 * Gets the board associated with this agent.
	 * 
	 * @return The board.
	 */
	public Board getBoard() {
		return board;
	}

	@Override
	public String toString() {
		return "Agent [isInputRequired=" + isInputRequired + "]";
	}
}
