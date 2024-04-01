package rgou.controllers.agents;

import rgou.model.Board;

/**
 * simple API to communicate between Board and
 * other types of players such as AI, remote and local
 */
public class Agent {
	/**
	 * current player team
	 * can be light or dark
	 */
	protected String player;
	protected boolean isInputRequired = true;

	protected Board board;

	/*
	 * roll
	 * move
	 * see current board state
	 */

	public Agent(String player, Board board) {
		this.board = board;
		this.player = player;
	}

	public boolean isInputRequired() {
		return isInputRequired;
	}

	public void roll() {
		board.roll();
	}

	public boolean move(int x, int y) {
		return board.makeMove(x, y);
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public String toString() {
		return "Agent [isInputRequired=" + isInputRequired + "]";
	}

}
