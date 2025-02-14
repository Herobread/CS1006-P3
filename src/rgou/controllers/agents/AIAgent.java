package rgou.controllers.agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.model.Board;
import rgou.utils.Pair;

public class AIAgent extends Agent {
	private boolean expectMiniMax = true;

	private static HashMap<String, String> coordToZone = new HashMap<>();

	public AIAgent(String player, Board board) {
		super(player, board);

		// Populates the coordToZone Map
		populateCoordsToZone();

		// disable dice roll
		// and board panel inputs in future
		isInputRequired = false;

		board.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (board.getActivePlayer().equals(player) && board.isRollAvailable()) {
					Timer timer = new Timer(500, event -> moveAvailable());
					timer.setRepeats(false);
					timer.start();
				}
			}
		});
	}

	public boolean isAdvancedAI() {
		return expectMiniMax;
	}

	public void setAdvancedAI(boolean expectiMinMax) {
		this.expectMiniMax = expectiMinMax;
	}

	// Creates the full map of coordinates to zones
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

	// Triggered whenever it is the AI's turn to move
	public void moveAvailable() {
		Board board = getBoard();

		// Roll dice
		roll();

		// Check if move is available at all:
		// if unavailable, return
		if (!board.isSelectMoveAvailable())
			return;

		// Get paths for both players
		List<Pair<Point, String>> pathWithAIPawns = board.getPathWithPawns(player);
		List<Pair<Point, String>> pathWithPlayerPawns = board.getPathWithPawns("light");

		List<Point> aiPawns = new ArrayList<>();
		List<Point> playerPawns = new ArrayList<>();

		// Populate list of AI pawns
		pathWithAIPawns.stream()
				.filter(pair -> pair.getSecond() != null)
				.filter(pair -> pair.getSecond().equals(player))
				.map(pair -> pair.getFirst())
				.filter(point -> !point.equals(new Point(2, 5)))
				.forEach(point -> aiPawns.add(point));
		// Start tile doesn't show if a pawn is there, so as long as there is
		// remaining stock, add a starting pawn to the list of potentially
		// movable pawns
		if (board.getCurrentPlayerStock() > 0)
			aiPawns.add(new Point(2, 4));

		// Populate list of player pawns
		pathWithPlayerPawns.stream()
				.filter(pair -> pair.getSecond() != null)
				.filter(pair -> pair.getSecond().equals("light"))
				.map(pair -> pair.getFirst())
				.filter(point -> !point.equals(new Point(0, 5)))
				.forEach(point -> playerPawns.add(point));
		if (board.getPlayerStock("light") > 0)
			playerPawns.add(new Point(0, 4));

		// Get move depending on which AI is selected
		Pair<Point, Double> move;
		if (expectMiniMax)
			move = getExpectiMinMaxMove(aiPawns, playerPawns);
		else
			move = getCurrentStateMove(aiPawns, playerPawns, pathWithPlayerPawns);

		// Make move
		Timer timer = new Timer(1000, event -> move((int) move.getFirst().getX(), (int) move.getFirst().getY()));
		timer.setRepeats(false);
		timer.start();
	}

	// This is the basic AI that only looks at the current state of the board and
	// makes the best move based on that
	private Pair<Point, Double> getCurrentStateMove(List<Point> aiPawns, List<Point> playerPawns,
			List<Pair<Point, String>> pathWithPlayerPawns) {

		// Get all possible moves and calculate metrics
		Map<Point, Double> moves = getPossibleMoves(5, aiPawns, playerPawns, player, board);
		// Return best move
		return chooseBestMove(moves);
	}

	// This is the advanced AI that should utilize the expectiminmax algorithm to
	// look ahead one move before making its move
	private Pair<Point, Double> getExpectiMinMaxMove(List<Point> aiPawns, List<Point> playerPawns) {
		Map<Point, Double> moves = new HashMap<>();

		// For each AI pawn that could be moved, check endpoint
		for (Point aiPawn : aiPawns) {
			Point endPoint = board.getPossibleMove(aiPawn);

			// Reset isCapture before every iteration, used to check if a player pawn would
			// be sent back to start
			boolean isCapture = false;

			// If valid endpoint --> valid move
			if (endPoint != null) {
				// Get new list of AI pawns
				List<Point> newAIPawns = getNewPawnList(aiPawn, endPoint, aiPawns);

				// Check if capture happened: if it did, update list of player pawns
				List<Point> newPlayerPawns;

				String pawnAtTarget = board.getTile(endPoint).getPawn();
				if (pawnAtTarget != null)
					isCapture = pawnAtTarget.equals("light");

				if (isCapture) // If capture, remove that player pawn from list
					newPlayerPawns = removePawnFromList(endPoint, playerPawns);
				else
					newPlayerPawns = playerPawns;

				// Now that have new state for a particular move, extend the tree
				Double weightedMetric = 0.0;
				for (int possibleRoll = 0; possibleRoll <= 4; possibleRoll++) {
					// For each possible roll, need to get all possible player moves

					Map<Point, Double> playerMoves = getPossibleMoves(possibleRoll, newPlayerPawns,
							newAIPawns, "light", board);
					// For each set of possible player moves, assume player will choose the best one
					Pair<Point, Double> bestPlayerMove = chooseBestMove(playerMoves);

					// Now weight the chosen player move based on the roll chance
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
				}

				// Once you have a weighted metric, add to moves
				moves.put(aiPawn, weightedMetric);
			}
		}

		// At this point, should have a map of potential moves and the weighted metric
		// of the
		// move the AI expects the player will make following that move.
		// AI will choose the move with the lowest associated metric, so the player next
		// turn will make a "worse" move
		Pair<Point, Double> worstPlayerMove = new Pair<Point, Double>(new Point(0, 0), 100.0);

		// In addition, if all associated metrics are the same, AI makes move based on
		// what is
		// best for itself for that particular turn (i.e. how simple AI makes decision)
		List<Point> sameMetricMoves = new ArrayList<>();
		Boolean noPlayerEffect = null;

		for (Point move : moves.keySet()) {
			// If both metrics equal and a difference in all metrics hasn't been found, add
			// to list
			if ((int) Math.floor(moves.get(move) * 100000) == (int) Math.floor(worstPlayerMove.getSecond() * 100000)
					&& noPlayerEffect) {
				sameMetricMoves.add(move);
			} // If found lower metric, update worstPlayerMove
			else if (moves.get(move) < worstPlayerMove.getSecond()) {
				// This will always be run first since worstPlayerMove is preset to have
				// a very high metric, so change noPlayerEffect to true and add move to list
				if (noPlayerEffect == null) {
					sameMetricMoves.add(move);
					noPlayerEffect = true;
				}
				// If a lower metric is found again, that means there was a move that would
				// result in the player getting a worse move, so clear list
				else if (noPlayerEffect) {
					noPlayerEffect = false;
					sameMetricMoves.clear();
				}
				worstPlayerMove.setFirst(move);
				worstPlayerMove.setSecond(moves.get(move));
			} // Check in case the following moves have a higher metric.
				// If they do, should make move with lower associated metric
			else if (moves.get(move) > worstPlayerMove.getSecond()) {
				noPlayerEffect = false;
				sameMetricMoves.clear();
			}
		}

		// If sameMetricMoves is empty, that means one of the moves it could make would
		// result in the player getting a worse move, so make that move
		if (sameMetricMoves.isEmpty())
			return worstPlayerMove;

		// If not, all moves the AI could make would give the player a move with
		// the same weighted metric, so make move decision
		// based on what is best for the AI given the
		// current gamestate
		else
			return getCurrentStateMove(sameMetricMoves, playerPawns, board.getPathWithPawns("light"));
	}

	// Goes through a list of moves and chooses the one with the highest metric
	private Pair<Point, Double> chooseBestMove(Map<Point, Double> moves) {
		Pair<Point, Double> bestMove = new Pair<Point, Double>(new Point(0, 0), -1.5);
		moves.entrySet().stream()
				.forEach(move -> {
					if (move.getValue() > bestMove.getSecond()) {
						bestMove.setFirst(move.getKey());
						bestMove.setSecond(move.getValue());
					}
				});
		return bestMove;
	}

	// Takes a list of pawn locations and updates the location of a single pawn
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

	// Removes a single pawn from a list
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

	// Gets all possible moves and their associated metrics from a given game state
	private Map<Point, Double> getPossibleMoves(int roll, List<Point> pawnsToMove, List<Point> otherPawns,
			String selectedPlayer, Board board) {

		String opposingPlayer;
		if (selectedPlayer.equals("light"))
			opposingPlayer = "dark";
		else
			opposingPlayer = "light";

		Map<Point, Double> moves = new HashMap<>();

		// Go through each pawn in list
		for (Point pawn : pawnsToMove) {
			// Check if needed to input a roll for getting a posisble move
			// Only need to specify a roll for expectiminmax
			Point endPoint;
			// If roll inputted as 5, just use current roll value
			if (roll == 5)
				endPoint = board.getPossibleMove(pawn);
			else
				endPoint = board.getPossibleMove(pawn, roll, selectedPlayer);

			// If possible move
			if (endPoint != null) {
				// Get zones
				String startZone = coordToZone.get(pointToString(pawn));
				String endZone = coordToZone.get(pointToString(endPoint));

				// Check if move would result in a capture
				boolean isCapture = false;
				String pawnAtTarget = board.getTile(endPoint).getPawn();
				if (pawnAtTarget != null)
					isCapture = pawnAtTarget.equals(opposingPlayer);

				// Check if pawn in danger
				boolean inDanger = checkDanger(pawn, board.getPathWithPawns(opposingPlayer), opposingPlayer);

				// Check how many selected pawns are in start and danger 1 zone
				int selectedPawnsInStartAndD1 = pawnsToMove.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone.equals("D1") || zone.equals("S"))
						.mapToInt(zone -> 1)
						.sum();

				// Check how many opposing pawns are in start and danger 1 zone
				int opposingPawnsInStartAndD1 = otherPawns.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone != null)
						.filter(zone -> zone.equals("D1") || zone.equals("S"))
						.mapToInt(zone -> 1)
						.sum();

				// Check if there is a selected pawn in danger 2 zone
				boolean selectedPawnInD2 = pawnsToMove.stream()
						.map(p -> coordToZone.get(pointToString(p)))
						.filter(zone -> zone.equals("D2"))
						.mapToInt(zone -> 1)
						.sum() > 1;

				// Check if both end tiles are full
				boolean endFull = board.getTile(new Point(2, 6)).getPawn() != null
						&& board.getTile(new Point(2, 7)).getPawn() != null;

				// Get metric
				double metric = calculateMetric(isCapture, inDanger, pawn, endPoint, startZone,
						endZone, selectedPawnsInStartAndD1, opposingPawnsInStartAndD1, selectedPawnInD2, endFull);

				// Add the pawn to move and the associated metric to list
				moves.put(pawn, metric);
			}
		}

		// Return list of possible moves and their metrics
		return moves;
	}

	// Checks if a piece is in danger
	private boolean checkDanger(Point point, List<Pair<Point, String>> pathWithOpposingPawns, String opposingPlayer) {
		// If pawn on middle rosette, it's safe so return false
		if (point.equals(new Point(1, 3)))
			return false;

		// Check zone, because piece only in danger if in danger zone
		String zone = coordToZone.get(pointToString(point));
		if (zone.equals("D1") || zone.equals("D2")) {
			// Go through each point in the opposing player's path
			for (Pair<Point, String> opposingPlayerPathPair : pathWithOpposingPawns) {
				// When the point in the opposing player's path with the pawn to check is found,
				// check the 4 spaces behind to see if an opposing pawn is there.
				// If an opposing pawn is within 4 tiles behind the selected pawn,
				// the pawn is in danger.

				// Note: One of the two loops here appear to be redundant, as they both
				// do the same check of looking for the selected piece in the opposing
				// player's path. However, when attempting to remove one of the loops,
				// the game crashed, so, we chose to keep these method as is despite
				// it seeming inefficient. It doesn't slow the AI down, so as long as it
				// does what it needs to, the redundant code won't be noticed by a player.
				if (opposingPlayerPathPair.getFirst().equals(point)) {
					ListIterator<Pair<Point, String>> iter = pathWithOpposingPawns.listIterator();
					while (iter.hasNext()) {
						if (iter.next().getFirst().equals(point)) {
							// Iterate through the 4 tiles behind the piece
							for (int i = 0; i < 4; i++) {
								String pawnAtTile = iter.previous().getSecond();
								if (pawnAtTile != null)
									if (pawnAtTile.equals(opposingPlayer))
										// If one of the 4 tiles behind the piece has an opposing
										// pawn, pawn in danger
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

	// Converts a point into a readable String (also used for the coordToZone map)
	private String pointToString(Point point) {
		return point.getX() + "," + point.getY();
	}

	// Calculates the metric of a particular move
	private double calculateMetric(boolean isCapture, boolean inDanger, Point startPoint,
			Point endPoint, String startZone,
			String endZone, int selectedPawnsInStartAndD1, int opposingPawnsInStartAndD1, boolean AIPieceInD2,
			boolean endFull) {
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
			if (endZone.equals("D2"))
				finalMetric += 4.6;
			else if (endZone.equals("D1")) {
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
