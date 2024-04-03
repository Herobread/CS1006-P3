package rgou.controllers.agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.model.Board;
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
		coordToZone.put("2.0,0.0", "S");
		coordToZone.put("2.0,1.0", "S");
		coordToZone.put("2.0,2.0", "S");
		coordToZone.put("2.0,3.0", "S");

		coordToZone.put("0.0,0.0", "S");
		coordToZone.put("0.0,1.0", "S");
		coordToZone.put("0.0,2.0", "S");
		coordToZone.put("0.0,3.0", "S");

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
		coordToZone.put("0.0,6.0", "end");
		coordToZone.put("0.0,7.0", "end");

		coordToZone.put("2.0,5.0", "score");
		coordToZone.put("0.0,5.0", "score");
		coordToZone.put("2.0,4.0", "start");
		coordToZone.put("0.0,4.0", "start");
	}

	// any action that updated the board will automatically update the GUI
	// so, I think that this should trigger again when you get extra move
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

		if (board.getPlayerStock("light") > 0)
			playerPawns.add(new Point(0, 4));
		pathWithPlayerPawns.stream()
				.filter(pair -> pair.getSecond() != null)
				.filter(pair -> pair.getSecond().equals("light"))
				.map(pair -> pair.getFirst())
				.filter(point -> !point.equals(new Point(0, 5)))
				.forEach(point -> playerPawns.add(point));

		boolean expectiMinMax = true;

		Pair<Point, Double> move;
		if (expectiMinMax) {
			move = getExpectiMinMaxMove(aiPawns, playerPawns);
		} else {
			move = getCurrentStateMove(aiPawns, playerPawns, pathWithPlayerPawns);
		}

		//////////////////////
		// make a move:
		// returns true if moved thingy
		System.out.println("Moving: " + move.getFirst().toString() + " : " + move.getSecond());
		boolean didMove = move((int) move.getFirst().getX(), (int) move.getFirst().getY());

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

	private Pair<Point, Double> getCurrentStateMove(List<Point> aiPawns, List<Point> playerPawns,
			List<Pair<Point, String>> pathWithPlayerPawns) {
		Map<Point, Double> moves = getPossibleMoves(5, aiPawns, playerPawns, player, board, false);
		return chooseBestMove(moves);
	}

	private Pair<Point, Double> getExpectiMinMaxMove(List<Point> aiPawns, List<Point> playerPawns) {
		Map<Point, Double> moves = new HashMap<>();
		for (Point aiPawn : aiPawns) {
			// For each AI pawn that hasn't scored, check endpoint
			Point endPoint = board.getPossibleMove(aiPawn);
			boolean isCapture = false;

			// If valid endpoint
			if (endPoint != null) {
				// Get new list of AI pawns
				List<Point> newAIPawns = getNewPawnList(aiPawn, endPoint, aiPawns);
				List<Point> newPlayerPawns;

				// Check if capture happened
				String pawnAtTarget = board.getTile(endPoint).getPawn();
				if (pawnAtTarget != null)
					isCapture = pawnAtTarget.equals("light");

				if (isCapture)
					newPlayerPawns = removePawnFromList(endPoint, playerPawns);
				else
					newPlayerPawns = playerPawns;

				newPlayerPawns.stream().forEach(point -> System.out.println(pointToString(point)));
				// Now that have new state for a particular move
				Double weightedMetric = 0.0;

				for (int possibleRoll = 0; possibleRoll <= 4; possibleRoll++) {
					// For each possible roll, need to get all possible player moves

					Map<Point, Double> playerMoves = getPossibleMoves(possibleRoll, newPlayerPawns,
							newAIPawns, "light", board, isCapture);
					playerMoves.entrySet().stream()
							.forEach(pair -> System.out.println(pointToString(pair.getKey()) + pair.getValue()));
					playerMoves.entrySet().stream()
							.map(entry -> entry.getValue())
							.forEach(metric -> System.out.println(metric));
					Pair<Point, Double> bestPlayerMove = chooseBestMove(playerMoves);

					switch (possibleRoll) {
						case 0:
						case 4:
							weightedMetric += bestPlayerMove.getSecond() * (1.0 / 16);
							break;
						case 1:
						case 3:
							weightedMetric += bestPlayerMove.getSecond() * (0.25);
							break;
						case 2:
							weightedMetric += bestPlayerMove.getSecond() * (3.0 / 8);
							break;
					}

					System.out.println(weightedMetric);
				}
				moves.put(aiPawn, weightedMetric);
			}
		}
		Pair<Point, Double> worstPlayerMove = new Pair<Point, Double>(new Point(0, 0), 100.0);
		moves.entrySet().stream()
				.forEach(move -> {
					System.out.println(pointToString(move.getKey()) + ": " + move.getValue());
					if (move.getValue() < worstPlayerMove.getSecond()) {
						worstPlayerMove.setFirst(move.getKey());
						worstPlayerMove.setSecond(move.getValue());
					}
				});

		return worstPlayerMove;
	}

	private Pair<Point, Double> chooseBestMove(Map<Point, Double> moves) {
		Pair<Point, Double> bestMove = new Pair<Point, Double>(new Point(0, 0), -100.0);
		moves.entrySet().stream()
				.forEach(move -> {
					System.out.println(pointToString(move.getKey()) + ": " + move.getValue());
					if (move.getValue() > bestMove.getSecond()) {
						bestMove.setFirst(move.getKey());
						bestMove.setSecond(move.getValue());
					}
				});
		return bestMove;
	}

	private List<Point> getNewPawnList(Point pawnToChange, Point newPoint, List<Point> pawnList) {
		List<Point> newPawnList = new ArrayList<>();
		boolean changeMade = false;
		for (Point pawn : pawnList) {
			if (pawnToChange.equals(pawn) && !changeMade) {
				changeMade = true;
				newPawnList.add(newPoint);
			} else {
				newPawnList.add(pawn);
			}
		}
		return newPawnList;
	}

	private List<Point> removePawnFromList(Point pawnToRemove, List<Point> pawnList) {
		List<Point> newPawnList = new ArrayList<>();
		boolean changeMade = false;
		for (Point pawn : pawnList) {
			if (pawnToRemove.equals(pawn) && !changeMade) {
				changeMade = true;
			} else {
				newPawnList.add(pawn);
			}
		}
		return newPawnList;
	}

	private Map<Point, Double> getPossibleMoves(int roll, List<Point> pawnsToMove, List<Point> otherPawns,
			String selectedPlayer, Board board, boolean isCapture) {

		String opposingPlayer;
		if (selectedPlayer.equals("light"))
			opposingPlayer = "dark";
		else
			opposingPlayer = "light";

		Map<Point, Double> moves = new HashMap<>();

		for (Point pawn : pawnsToMove) {
			isCapture = false;
			Point endPoint;
			if (roll == 5)
				endPoint = board.getPossibleMove(pawn);
			else {
				System.out.println("finding posible move with roll " + roll);
				endPoint = board.getPossibleMove(pawn, roll, selectedPlayer);
			}
			System.out.println(endPoint);

			if (endPoint != null) {
				String startZone = coordToZone.get(pointToString(pawn));
				String endZone = coordToZone.get(pointToString(endPoint));

				if (!isCapture) {
					String pawnAtTarget = board.getTile(endPoint).getPawn();
					if (pawnAtTarget != null) {
						isCapture = pawnAtTarget.equals(opposingPlayer);
						if (isCapture)
							System.out.println(
									selectedPlayer + " pawn at " + pawn
											+ " can capture " + opposingPlayer + " pawn at " + pointToString(endPoint));
					}
				}

				boolean inDanger = checkDanger(pawn, board.getPathWithPawns(opposingPlayer), opposingPlayer);

				int selectedPawnsInStartAndD1 = pawnsToMove.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone.equals("D1") || zone.equals("S"))
						.mapToInt(zone -> 1)
						.sum();

				int opposingPawnsInStartAndD1 = otherPawns.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone != null)
						.filter(zone -> zone.equals("D1") || zone.equals("S"))
						.mapToInt(zone -> 1)
						.sum();

				boolean selectedPawnInD2 = pawnsToMove.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone.equals("D2"))
						.mapToInt(zone -> 1)
						.sum() > 1;

				boolean endFull = board.getTile(new Point(2, 6)).getPawn() != null
						&& board.getTile(new Point(2, 7)).getPawn() != null;

				double metric = calculateMetric(isCapture, inDanger, pawn, endPoint, startZone,
						endZone, selectedPawnsInStartAndD1, opposingPawnsInStartAndD1, selectedPawnInD2, endFull);

				moves.put(pawn, metric);
			}
		}

		return moves;
	}

	private boolean checkDanger(Point point, List<Pair<Point, String>> pathWithOpposingPawns, String opposingPlayer) {
		if (point.equals(new Point(1, 3)))
			return false;
		String zone = coordToZone.get(pointToString(point));
		if (zone.equals("D1") || zone.equals("D2")) {
			for (Pair<Point, String> opposingPlayerPathPair : pathWithOpposingPawns) {
				if (opposingPlayerPathPair.getFirst().equals(point)) {
					ListIterator<Pair<Point, String>> iter = pathWithOpposingPawns.listIterator();
					while (iter.hasNext()) {
						if (iter.next().getFirst().equals(point)) {
							for (int i = 0; i < 4; i++) {
								String pawnAtTile = iter.previous().getSecond();
								if (pawnAtTile != null)
									if (pawnAtTile.equals(opposingPlayer))
										System.out.println("Pawn " + pointToString(point) + " in danger from " + (i + 1)
												+ " tiles behind");
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
			String endZone, int selectedPawnsInStartAndD1, int opposingPawnsInStartAndD1, boolean AIPieceInD2,
			boolean endFull) {
		System.out.println("Calculating metric of " + pointToString(startPoint));
		double finalMetric = 0;

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
			if (endZone.equals("D2")) {
				finalMetric += 4.6;
				System.out.println(4.6);
			} else if (endZone.equals("D1")) {
				if (opposingPawnsInStartAndD1 == 1)
					finalMetric += 3.1;
				else if (selectedPawnsInStartAndD1 >= opposingPawnsInStartAndD1)
					finalMetric += 1.5;
				else if (selectedPawnsInStartAndD1 == opposingPawnsInStartAndD1 - 1)
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
			if (inDanger) {
				finalMetric += 1.5;
			} else
				finalMetric += 0.5;
		}

		// Start zone
		if (endZone.equals("S")) {
			if (startZone.equals("start"))
				finalMetric += 1.4;
			if (startZone.equals("S"))
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
