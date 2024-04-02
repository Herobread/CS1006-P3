package rgou.controllers.agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.model.Board;
import rgou.model.Tile;
import rgou.model.dice.DiceRollsResult;
import rgou.utils.Pair;

public class AIAgent extends Agent {
	private static HashMap<String, String> coordToZone = new HashMap<>();

	public AIAgent(String player, Board board) {
		super(player, board);

		populateCoordsToZone();

		// disable dice roll
		// and board panel inputs in future
		isInputRequired = false;

		board.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (board.getActivePlayer().equals(player) && board.isRollAvailable()) {
					moveAvailable();
				}
			}
		});
	}

	public void populateCoordsToZone() {
		coordToZone.put("2.0,0.0", "OS");
		coordToZone.put("2.0,1.0", "OS");
		coordToZone.put("2.0,2.0", "OS");
		coordToZone.put("2.0,3.0", "OS");

		coordToZone.put("0.0,0.0", "PS");
		coordToZone.put("0.0,1.0", "PS");
		coordToZone.put("0.0,2.0", "PS");
		coordToZone.put("0.0,3.0", "PS");

		coordToZone.put("1.0,0.0", "D1");
		coordToZone.put("1.0,1.0", "D1");
		coordToZone.put("1.0,2.0", "D1");
		coordToZone.put("1.0,3.0", "D1");

		coordToZone.put("1.0,4.0", "D2");
		coordToZone.put("1.0,5.0", "D2");
		coordToZone.put("1.0,6.0", "D2");
		coordToZone.put("1.0,7.0", "D2");

		coordToZone.put("2.0,6.0", "end");
		coordToZone.put("2.0,7.0", "end");

		coordToZone.put("2.0,5.0", "score");
		coordToZone.put("2.0,4.0", "start");
	}

	// any action that updated the board will automatically update the GUI
	// so, I think that this should trigger again when you get extra move
	@SuppressWarnings("unused") // remove this when implementing AI
	public void moveAvailable() {
		// for debug, just to see if it gets opportunity to move at the correct time
		System.out.print("AI is moving");
		// Thread moveThread = new Thread() {
		// @Override
		// public void run() {
		// try {
		/////////////////////////// get board data:
		// all available data:
		Board board = getBoard();
		// use board.someFunction to get stuff:

		///////////////////////////
		// get tiles array
		// each tile has info(isRossette, isFinish, isStart etc)
		// + pawn info in tile.getPawn(), returns "light" or "dark"
		// warning: getTeam returns hard coded colour of start and end, not pawn color
		Tile[][] tiles = board.getBoard();

		/////////////////////////
		// roll dice:
		// (we already check if roll is available, so just roll)
		roll();
		// get dice results:
		DiceRollsResult diceRollsResult = board.getLastDiceRollsResult();
		// get total dice score:
		int total = diceRollsResult.getTotal();
		System.out.println(" and rolled a " + total);

		/////////////////////////
		// check if move is available at all:
		// sometimes may not be available(rolled 0 or no moves)
		// if unavailable: return;
		boolean isMoveAvailable = board.isSelectMoveAvailable();
		if (!isMoveAvailable)
			return;

		//////////////////////
		// get player path with pawns:
		List<Pair<Point, String>> pathWithAIPawns = board.getPathWithPawns(player);
		List<Pair<Point, String>> pathWithPlayerPawns = board.getPathWithPawns("light");

		List<Point> aiPawns = new ArrayList<>();
		List<Point> playerPawns = new ArrayList<>();

		pathWithAIPawns.stream()
				.filter(pair -> pair.getSecond() != null)
				.filter(pair -> pair.getSecond().equals(player))
				.map(pair -> pair.getFirst())
				.filter(point -> !point.equals(new Point(2, 5)))
				.forEach(point -> aiPawns.add(point));

		if (board.getCurrentPlayerStock() > 0)
			aiPawns.add(new Point(2, 4));
		pathWithPlayerPawns.stream()
				.filter(pair -> pair.getSecond() != null)
				.filter(pair -> pair.getSecond().equals("light"))
				.map(pair -> pair.getFirst())
				.filter(point -> !point.equals(new Point(0, 5)))
				.forEach(point -> playerPawns.add(point));

		Map<Point, Double> moves = new HashMap<>();

		for (Point pawnPoint : aiPawns) {
			Point endPoint = board.getPossibleMove(pawnPoint);
			if (endPoint != null) {
				String startZone = coordToZone.get(pointToString(pawnPoint));
				String endZone = coordToZone.get(pointToString(endPoint));

				boolean isCapture = false;
				String pawnAtTarget = board.getTile(endPoint).getPawn();
				if (pawnAtTarget != null)
					isCapture = pawnAtTarget.equals("light");

				boolean inDanger = checkDanger(pawnPoint, pathWithPlayerPawns);

				int aiPiecesInStartAndD1 = aiPawns.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone.equals("D1") || zone.equals("OS"))
						.mapToInt(zone -> 1)
						.sum();

				int playerPiecesInStartAndD1 = playerPawns.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone != null)
						.filter(zone -> zone.equals("D1") || zone.equals("PS"))
						.mapToInt(zone -> 1)
						.sum();

				boolean AIPieceInD2 = aiPawns.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone.equals("D2"))
						.mapToInt(zone -> 1)
						.sum() > 1;

				boolean endFull = board.getTile(new Point(2, 6)).getPawn() != null
						&& board.getTile(new Point(2, 7)).getPawn() != null;

				double metric = calculateMetric(isCapture, inDanger, pawnPoint, endPoint, startZone,
						endZone,
						aiPiecesInStartAndD1, playerPiecesInStartAndD1, AIPieceInD2, endFull);

				moves.put(pawnPoint, metric);
			}
		}

		Pair<Point, Double> bestMove = new Pair<Point, Double>(new Point(0, 0), -100.0);
		moves.entrySet().stream()
				.forEach(move -> {
					System.out.println(pointToString(move.getKey()) + ": " + move.getValue());
					if (move.getValue() > bestMove.getSecond()) {
						bestMove.setFirst(move.getKey());
						bestMove.setSecond(move.getValue());
					}
				});

		//////////////////////
		// make a move:
		// returns true if moved thingy
		System.out.println("Moving: " + bestMove.getFirst().toString() + " : " + bestMove.getSecond());
		boolean didMove = move((int) bestMove.getFirst().getX(), (int) bestMove.getFirst().getY());

		if (!didMove) {
			// skill issue:
			throw new RuntimeException("AI has skill issue, maybe");
		}
		// sleep(1000);
		// } catch (InterruptedException e) {

		// }
		// }
		// };
		// moveThread.run();
	}

	private boolean checkDanger(Point point, List<Pair<Point, String>> pathWithPlayerPawns) {
		String zone = coordToZone.get(pointToString(point));
		if (zone.equals("D1") || zone.equals("D2")) {
			for (Pair playerPathPair : pathWithPlayerPawns) {
				if (playerPathPair.getFirst().equals(point)) {
					ListIterator<Pair<Point, String>> iter = pathWithPlayerPawns.listIterator();
					while (iter.hasNext()) {
						if (iter.next().getFirst().equals(point)) {
							for (int i = 0; i < 4; i++) {
								String pawnAtTile = iter.previous().getSecond();
								if (pawnAtTile != null)
									if (pawnAtTile.equals("light"))
										return true;
							}
							return false;
						}
					}
					return false;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	private String pointToString(Point point) {
		return point.getX() + "," + point.getY();
	}

	private double calculateMetric(boolean isCapture, boolean inDanger, Point startPoint,
			Point endPoint, String startZone,
			String endZone, int aiPiecesInStartAndD1, int playerPiecesInStartAndD1, boolean AIPieceInD2,
			boolean endFull) {

		double finalMetric = 0;
		boolean wouldSaveSafely = isCapture && playerPiecesInStartAndD1 == 1;

		// Rosettas
		if (pointToString(endPoint).equals("1.0,3.0")) { // Mid Rose
			finalMetric += 4;
		} else if (pointToString(endPoint).equals("2.0,0.0")) // Start Rose
			finalMetric += 2.2;
		else if (pointToString(endPoint).equals("2.0,6.0")) // End Rose
			finalMetric += 2.1;
		else if (pointToString(startPoint).equals("1.0,3.0")) // Start at Mid Rose
			finalMetric -= 1;

		// Captures
		if (isCapture) {
			if (endZone.equals("D2"))
				finalMetric += 4.5;
			else if (endZone.equals("D1")) {
				if (playerPiecesInStartAndD1 == 1)
					finalMetric += 2.5;
				if (wouldSaveSafely)
					finalMetric += 0.6;
				else if (aiPiecesInStartAndD1 >= playerPiecesInStartAndD1)
					finalMetric += 1.5;
				else if (aiPiecesInStartAndD1 == playerPiecesInStartAndD1 - 1)
					finalMetric += 1.2;
				else
					finalMetric += 0.5;
			}
		}

		// Moves in Danger
		if (endZone.equals("end")) {
			if (inDanger)
				finalMetric += 2.5;
			else
				finalMetric += 1.1;
		} else if (endZone.equals("D2") && !isCapture) {
			if (inDanger)
				finalMetric += 3;
			else
				finalMetric += 1;
		} else if (endZone.equals("D1") && !isCapture) {
			if (inDanger)
				finalMetric += 1.5;
			else
				finalMetric += 0.5;
		}

		// Start zone
		if (endZone.equals("OS")) {
			if (startZone.equals("start"))
				finalMetric += 1.4;
			if (startZone.equals("OS"))
				finalMetric += 1.3;
		}

		// Scoring
		if (endZone.equals("score")) {
			if (endFull && AIPieceInD2)
				finalMetric += 2.1;
			else
				finalMetric += 0.9;
		}

		return finalMetric;
	}
}
