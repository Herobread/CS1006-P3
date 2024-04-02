package rgou.model;

public class Event {
	private EventTypes type;
	private String player;
	private int x;
	private int y;
	private Board board;

	public Event(Board board, String player, EventTypes type, int x, int y) {
		this.board = board;
		this.player = player;
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public Event(Board board, String player, EventTypes type) {
		this.board = board;
		this.player = player;
		this.type = type;
	}

	public EventTypes getType() {
		return type;
	}

	public void setType(EventTypes type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
}
