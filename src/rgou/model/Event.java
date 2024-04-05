package rgou.model;

/**
 * Represents an event in the game.
 */
public class Event {
	private EventTypes type;
	private String player;
	private int x;
	private int y;
	private Board board;

	/**
	 * Constructs an Event with the specified attributes.
	 * 
	 * @param board  the board associated with the event
	 * @param player the player associated with the event
	 * @param type   the type of the event
	 * @param x      the x-coordinate of the event
	 * @param y      the y-coordinate of the event
	 */
	public Event(Board board, String player, EventTypes type, int x, int y) {
		this.board = board;
		this.player = player;
		this.type = type;
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs an Event with the specified attributes.
	 * 
	 * @param board  the board associated with the event
	 * @param player the player associated with the event
	 * @param type   the type of the event
	 */
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

	@Override
	public String toString() {
		return "Event [type=" + type + ", player=" + player + ", x=" + x + ", y=" + y + ", board=" + board + "]";
	}
}
