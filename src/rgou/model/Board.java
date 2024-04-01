package rgou.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.model.dice.DiceRoller;
import rgou.model.dice.DiceRollsResult;

import java.awt.Point;

public class Board {
	private List<ChangeListener> listeners = new ArrayList<>();

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
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
	}

	public DiceRollsResult getLastDiceRollsResult() {
		if (lastDiceRollsResult == null) {
			// roll as placeholder
			lastDiceRollsResult = DiceRoller.roll();
		}

		return lastDiceRollsResult;
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

	public void makeMove(Point p) {
		makeMove(p.x, p.y);
	}

	public void makeMove(int x, int y) {
		if (isRollAvailable) {
			return;
		}

		if (!isSelectMoveAvailable) {
			return;
		}

		Point possibleMovePoint = getPossibleMove(x, y);

		if (possibleMovePoint == null) {
			return;
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
			notifyChange();
			return;
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

		notifyChange();
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
}
