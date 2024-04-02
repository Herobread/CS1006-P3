package rgou.controllers;

import rgou.controllers.agents.Agent;
import rgou.model.Board;

public class GameStateController {
	private Board board;
	private Agent lightPlayer;
	private Agent darkPlayer;

	public GameStateController() {
		board = new Board();
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public Agent getPlayerAgent(String player) {
		if (player.equals("light")) {
			return lightPlayer;
		}

		if (player.equals("dark")) {
			return darkPlayer;
		}

		return null;
	}

	public void setPlayerAgent(String player, Agent agent) {
		if (player.equals("light")) {
			lightPlayer = agent;
		}

		if (player.equals("dark")) {
			darkPlayer = agent;
		}
	}
}
