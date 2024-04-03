package rgou.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.model.dice.DiceRollResult;
import rgou.model.dice.DiceRoller;
import rgou.model.dice.DiceRollsResult;
import rgou.utils.Pair;

import java.awt.Point;

public class Board {
	//// game event listener
	private List<ChangeListener> eventListeners = new ArrayList<>();

	public void addEventListener(ChangeListener eventListener) {
		eventListeners.add(eventListener);
	}

	public List<ChangeListener> getEventListeners() {
		return eventListeners;
	}

	public void setEventListeners(List<ChangeListener> eventListeners) {
		this.eventListeners.clear();
		this.eventListeners.addAll(eventListeners);
	}

	protected void notifyEvent(Event event) {
		for (ChangeListener eventListener : eventListeners) {
			eventListener.stateChanged(new ChangeEvent(event));
		}
	}

	//// game board listener
	private List<ChangeListener> listeners = new ArrayList<>();

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	public List<ChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<ChangeListener> listeners) {
		this.listeners.clear();
		this.listeners.addAll(listeners);
	}

	protected void notifyChange() {
		for (ChangeListener listener : listeners) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}

	private Tile[][] board;

	private String activePlayer = "light";
	private String noMovesPlayerWarning = null;

	private boolean isRollAvailable = true;
	private boolean isSelectMoveAvailable = false;

	private ArrayList<Point> lightPath;
	private ArrayList<Point> darkPath;

	private final int TOTAL_PAWNS = 7;

	private int lightPiecesInStock = TOTAL_PAWNS;
	private int darkPiecesInStock = TOTAL_PAWNS;

	private int lightScore = 0;
	private int darkScore = 0;

	private DiceRollsResult lastDiceRollsResult;

	private boolean RULES_SAFE_ROSSETE = true;

	public Board() {
		lightPath = new ArrayList<>();
		darkPath = new ArrayList<>();
		initialiseTiles();
		initialiseLightPath();
		initialiseDarkPath();
	}

	private void initialiseLightPath() {
		// left side: start -> up
		lightPath.add(new Point(0, 4)); // start
		lightPath.add(new Point(0, 3));
		lightPath.add(new Point(0, 2));
		lightPath.add(new Point(0, 1));
		lightPath.add(new Point(0, 0));

		// middle: up -> bottom
		lightPath.add(new Point(1, 0));
		lightPath.add(new Point(1, 1));
		lightPath.add(new Point(1, 2));
		lightPath.add(new Point(1, 3));
		lightPath.add(new Point(1, 4));
		lightPath.add(new Point(1, 5));
		lightPath.add(new Point(1, 6));
		lightPath.add(new Point(1, 7));

		// left side: bottom -> end
		lightPath.add(new Point(0, 7));
		lightPath.add(new Point(0, 6));
		lightPath.add(new Point(0, 5)); // end
	}

	private void initialiseDarkPath() {
		// left side: start -> up
		darkPath.add(new Point(2, 4)); // start
		darkPath.add(new Point(2, 3));
		darkPath.add(new Point(2, 2));
		darkPath.add(new Point(2, 1));
		darkPath.add(new Point(2, 0));

		// middle: up -> bottom
		darkPath.add(new Point(1, 0));
		darkPath.add(new Point(1, 1));
		darkPath.add(new Point(1, 2));
		darkPath.add(new Point(1, 3));
		darkPath.add(new Point(1, 4));
		darkPath.add(new Point(1, 5));
		darkPath.add(new Point(1, 6));
		darkPath.add(new Point(1, 7));

		// left side: bottom -> end
		darkPath.add(new Point(2, 7));
		darkPath.add(new Point(2, 6));
		darkPath.add(new Point(2, 5)); // end
	}

	public String getNoMovesPlayerWarning() {
		return noMovesPlayerWarning;
	}

	public String getActivePlayer() {
		return activePlayer;
	}

	private void initialiseTiles() {
		board = new Tile[8][3];
		// row 0
		board[0][0] = new Tile.Builder("tiles/rosette.png")
				.rosette(true)
				.build();
		board[0][1] = new Tile.Builder("tiles/tile-5.png")
				.build();
		board[0][2] = new Tile.Builder("tiles/rosette.png")
				.rosette(true)
				.build();

		// row 1
		board[1][0] = new Tile.Builder("tiles/tile-4.png")
				.build();
		board[1][1] = new Tile.Builder("tiles/tile-1.png")
				.build();
		board[1][2] = new Tile.Builder("tiles/tile-4.png")
				.build();

		// row 2
		board[2][0] = new Tile.Builder("tiles/tile-1.png")
				.build();
		board[2][1] = new Tile.Builder("tiles/tile-3.png")
				.build();
		board[2][2] = new Tile.Builder("tiles/tile-1.png")
				.build();

		// row 3
		board[3][0] = new Tile.Builder("tiles/tile-4.png")
				.build();
		board[3][1] = new Tile.Builder("tiles/rosette.png")
				.rosette(true)
				.build();
		board[3][2] = new Tile.Builder("tiles/tile-4.png")
				.build();

		// row 4
		board[4][0] = new Tile.Builder("tiles/tile-start.png")
				.start(true)
				.team("light")
				.build();
		board[4][1] = new Tile.Builder("tiles/tile-1.png")
				.build();
		board[4][2] = new Tile.Builder("tiles/tile-start.png")
				.start(true)
				.team("dark")
				.build();

		// row 5
		board[5][0] = new Tile.Builder("tiles/tile-end.png")
				.finish(true)
				.team("light")
				.build();
		board[5][1] = new Tile.Builder("tiles/tile-3.png")
				.build();
		board[5][2] = new Tile.Builder("tiles/tile-end.png")
				.finish(true)
				.team("dark")
				.build();

		// row 6
		board[6][0] = new Tile.Builder("tiles/rosette.png")
				.rosette(true)
				.build();
		board[6][1] = new Tile.Builder("tiles/tile-4.png")
				.build();
		board[6][2] = new Tile.Builder("tiles/rosette.png")
				.rosette(true)
				.build();

		// row 7
		board[7][0] = new Tile.Builder("tiles/tile-2.png")
				.build();
		board[7][1] = new Tile.Builder("tiles/tile-1.png")
				.build();
		board[7][2] = new Tile.Builder("tiles/tile-2.png")
				.build();
	}

	private void changePlayerTurn() {
		if (activePlayer.equals("light")) {
			activePlayer = "dark";
		} else {
			activePlayer = "light";
		}

		// hide warning about no moves
		if (activePlayer.equals(noMovesPlayerWarning)) {
			noMovesPlayerWarning = null;
		}

		notifyChange();
	}

	public void roll() {
		if (!isRollAvailable) {
			return;
		}

		lastDiceRollsResult = DiceRoller.roll();
		isRollAvailable = false;
		isSelectMoveAvailable = true;

		if (!checkCurrentPlayerMoveAvailability()) {
			noMovesPlayerWarning = activePlayer;
			changePlayerTurn();
			isRollAvailable = true;
			isSelectMoveAvailable = false;
		}

		notifyChange();
		notifyEvent(new Event(this, activePlayer, EventTypes.ROLL));
	}

	public void customRoll(String[] diceRollResults){
		if (!isRollAvailable) {
			return;
		}
		lastDiceRollsResult = DiceRoller.createCustomResult(diceRollResults);
		isRollAvailable = false;
		isSelectMoveAvailable = true;

		if (!checkCurrentPlayerMoveAvailability()) {
			noMovesPlayerWarning = activePlayer;
			changePlayerTurn();
			isRollAvailable = true;
			isSelectMoveAvailable = false;
		}

		notifyChange();
		notifyEvent(new Event(this, activePlayer, EventTypes.ROLL));
	}

	public DiceRollsResult getLastDiceRollsResult() {
		if (lastDiceRollsResult == null) {
			// roll as placeholder
			lastDiceRollsResult = DiceRoller.roll();
		}

		return lastDiceRollsResult;
	}

	public void rollCustomDice(DiceRollsResult lastDiceRollsResult) {
		this.lastDiceRollsResult = lastDiceRollsResult;
		isRollAvailable = false;
		isSelectMoveAvailable = true;

		notifyChange();
	}

	/**
	 * gets possible move for current player from Point
	 * 
	 * @param point
	 * @return Point where pawn can move, null otherwise
	 */
	public Point getPossibleMove(Point p) {
		return getPossibleMove(p.x, p.y);
	}

	/**
	 * gets possible move for current player from Point and given roll
	 * 
	 * @param point
	 * @param roll
	 * @return Point where pawn can move, null otherwise
	 */
	public Point getPossibleMove(Point p, int roll, String player) {
		return getPossibleMove(p.x, p.y, roll, player);
	}

	/**
	 * gets possible move for current player from x y
	 * 
	 * @param x
	 * @param y
	 * @return Point where pawn can move, null otherwise
	 */
	public Point getPossibleMove(int x, int y) {
		Tile currentTile = getTile(x, y);
		Point currentPoint = new Point(x, y);
		boolean isStart = currentTile.isStart();

		// check if pawn of correct team exists on the tile
		boolean isCorrectTeamPawn = activePlayer.equals(currentTile.getPawn());
		boolean isNormalMove = isCorrectTeamPawn && !isStart;

		boolean isCorrectTeamStart = currentTile.getTeam().equals(activePlayer);
		boolean isStockAvailable = getCurrentPlayerStock() > 0;
		boolean isStartTileMove = isStart && isStockAvailable && isCorrectTeamStart;

		if (!isNormalMove && !isStartTileMove) {
			return null;
		}

		// get possible position
		ArrayList<Point> activePlayerPath = getCurrentPlayerPath();

		int currentTileIdOnPath = activePlayerPath.indexOf(currentPoint);

		// check if selected point is on path
		if (currentTileIdOnPath == -1) {
			return null;
		}

		// dice wasn't rolled
		if (lastDiceRollsResult == null) {
			return null;
		}

		// calculate expected location:
		int diceRoll = lastDiceRollsResult.getTotal();

		Point targetTilePoint;
		try {
			targetTilePoint = activePlayerPath.get(diceRoll + currentTileIdOnPath);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

		Tile targetTile = getTile(targetTilePoint);

		// check tile content
		// check if enemy
		String playerThatOccupies = targetTile.getPawn();
		boolean isTileEmpty = playerThatOccupies == null;
		boolean isTileOccupiedByEnemy = !isTileEmpty && !activePlayer.equals(playerThatOccupies);
		boolean isRosette = targetTile.isRosette();

		if (RULES_SAFE_ROSSETE && isTileOccupiedByEnemy && isRosette) {
			return null;
		}

		// check if current player occupies
		boolean isTileOccupiedBySelf = !isTileEmpty && playerThatOccupies.equals(activePlayer);

		if (isTileOccupiedBySelf) {
			return null;
		}

		return targetTilePoint;
	}

	/**
	 * gets possible move for current player from x y and a given roll
	 * 
	 * @param x
	 * @param y
	 * @param roll
	 * @return Point where pawn can move, null otherwise
	 */
	public Point getPossibleMove(int x, int y, int roll, String player) {
		DiceRollsResult originalDiceRollsResult = lastDiceRollsResult;

		// create fake dice roll:
		DiceRollResult[] fakeRolls = new DiceRollResult[DiceRoller.DICES_AMOUNT];
		for (int i = 0; i < DiceRoller.DICES_AMOUNT; i++) {
			boolean isWin = i < roll;
			fakeRolls[i] = new DiceRollResult(isWin, "", 0);
		}
		// override last roll with fake
		lastDiceRollsResult = new DiceRollsResult(fakeRolls);

		String ogPlayer = activePlayer;
		activePlayer = player;
		Point move = getPossibleMove(x, y);

		activePlayer = ogPlayer;
		// return back to original state
		lastDiceRollsResult = originalDiceRollsResult;

		return move;
	}

	public boolean makeMove(Point p) {
		return makeMove(p.x, p.y);
	}

	public boolean makeMove(int x, int y) {
		if (isRollAvailable) {
			return false;
		}

		if (!isSelectMoveAvailable) {
			return false;
		}

		Point possibleMovePoint = getPossibleMove(x, y);

		if (possibleMovePoint == null) {
			return false;
		}

		// make move
		Tile targetTile = getTile(possibleMovePoint);
		String playerThatOccupies = targetTile.getPawn();

		Point currentTilePoint = new Point(x, y);
		Tile currentTile = getTile(currentTilePoint);

		// tile capture
		boolean isEnemyOccupied = playerThatOccupies != activePlayer && playerThatOccupies != null;
		if (isEnemyOccupied) {
			changeEnemyPlayerStock(1);
		}

		if (targetTile.isFinish()) {
			currentTile.setPawn(null);
			changeCurrentPlayerScore(1);
			isRollAvailable = true;
			changePlayerTurn();
			notifyEvent(new Event(this, activePlayer, EventTypes.MOVE, x, y));
			// notifyChange();
			return true;
		}

		if (currentTile.isStart()) {
			changeCurrentPlayerStock(-1);
		} else {
			currentTile.setPawn(null);
		}

		targetTile.setPawn(activePlayer);

		if (!targetTile.isRosette()) {
			changePlayerTurn();
		}
		isRollAvailable = true;

		notifyEvent(new Event(this, activePlayer, EventTypes.MOVE, x, y));
		notifyChange();
		return true;
	}

	public boolean checkCurrentPlayerMoveAvailability() {
		if (lastDiceRollsResult.getTotal() == 0) {
			return false;
		}

		ArrayList<Point> currentPlayerPath = getCurrentPlayerPath();

		for (Point currentPlayerPathPoint : currentPlayerPath) {
			if (getPossibleMove(currentPlayerPathPoint) != null) {
				return true;
			}
		}

		return false;
	}

	public String getVictoriousPlayer() {
		if (lightScore == TOTAL_PAWNS) {
			return "light";
		}

		if (darkScore == TOTAL_PAWNS) {
			return "dark";
		}

		return null;
	}

	public ArrayList<Point> getPlayerPath(String player) {
		if (player.equals("light")) {
			return lightPath;
		}

		if (player.equals("dark")) {
			return darkPath;
		}

		return null;
	}

	public ArrayList<Point> getCurrentPlayerPath() {
		if (activePlayer.equals("light")) {
			return lightPath;
		} else {
			return darkPath;
		}
	}

	public void changeCurrentPlayerScore(int delta) {
		if (activePlayer.equals("light")) {
			lightScore += delta;
		} else {
			darkScore += delta;
		}
	}

	public void changeCurrentPlayerStock(int delta) {
		if (activePlayer.equals("light")) {
			lightPiecesInStock += delta;
		} else {
			darkPiecesInStock += delta;
		}
	}

	public void changeEnemyPlayerStock(int delta) {
		if (!activePlayer.equals("light")) {
			lightPiecesInStock += delta;
		} else {
			darkPiecesInStock += delta;
		}
	}

	public int getCurrentPlayerStock() {
		if (activePlayer.equals("light")) {
			return lightPiecesInStock;
		} else {
			return darkPiecesInStock;
		}
	}

	public int getPlayerStock(String player) {
		if (player.equals("light")) {
			return lightPiecesInStock;
		} else {
			return darkPiecesInStock;
		}
	}

	public int getPlayerScore(String player) {
		if (player.equals("light")) {
			return lightScore;
		} else {
			return darkScore;
		}
	}

	public Tile getTile(Point point) {
		return getTile((int) point.getX(), (int) point.getY());
	}

	public Tile getTile(int x, int y) {
		return board[y][x];
	}

	public Tile[][] getBoard() {
		return board;
	}

	public boolean isRollAvailable() {
		return isRollAvailable;
	}

	public boolean isSelectMoveAvailable() {
		return isSelectMoveAvailable;
	}

	//////////// AI

	public List<Pair<Point, String>> getPathWithPawns(String player) {
		List<Point> path = getPlayerPath(player);
		List<Pair<Point, String>> pathWithPawns = new ArrayList<>();

		for (Point pathPoint : path) {
			String pawn = getTile(pathPoint).getPawn();
			Pair<Point, String> pair = new Pair<>(pathPoint, pawn);
			pathWithPawns.add(pair);
		}

		return pathWithPawns;
	}
}
