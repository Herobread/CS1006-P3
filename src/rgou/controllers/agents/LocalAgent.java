package rgou.controllers.agents;

import rgou.model.Board;

public class LocalAgent extends Agent {
	public LocalAgent(String player, Board board) {
		super(player, board);
		isInputRequired = true;
	}
}
