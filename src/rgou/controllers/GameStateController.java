package rgou.controllers;

import rgou.controllers.agents.Agent;
import rgou.model.Board;
import rgou.model.remote.RemoteConfig;
import rgou.model.remote.RemoteStatus;

/**
 * Controls the game state, including the board, players, and remote
 * configurations.
 */
public class GameStateController {
	private Board board;
	private Agent lightPlayer;
	private Agent darkPlayer;
	private RemoteConfig remoteConfig;
	private RemoteStatus remoteStatus;

	/**
	 * Constructs a GameStateController with a new board and default remote status.
	 */
	public GameStateController() {
		board = new Board();
		remoteStatus = new RemoteStatus(null);
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	/**
	 * Retrieves the player agent for the specified player.
	 * 
	 * @param player the player identifier ("light" or "dark")
	 * @return the agent associated with the player
	 */
	public Agent getPlayerAgent(String player) {
		if (player.equals("light")) {
			return lightPlayer;
		}

		if (player.equals("dark")) {
			return darkPlayer;
		}

		return null;
	}

	/**
	 * Sets the player agent for the specified player.
	 * 
	 * @param player the player identifier ("light" or "dark")
	 * @param agent  the agent to set for the player
	 */
	public void setPlayerAgent(String player, Agent agent) {
		if (player.equals("light")) {
			lightPlayer = agent;
		}

		if (player.equals("dark")) {
			darkPlayer = agent;
		}
	}

	public RemoteConfig getRemoteConfig() {
		return remoteConfig;
	}

	public void setRemoteConfig(RemoteConfig remoteConfig) {
		this.remoteConfig = remoteConfig;
	}

	public RemoteStatus getRemoteStatus() {
		return remoteStatus;
	}

	public void setRemoteStatus(RemoteStatus remoteStatus) {
		this.remoteStatus = remoteStatus;
	}
}
