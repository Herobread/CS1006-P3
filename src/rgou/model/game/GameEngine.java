package rgou.model.game;

import java.util.Random;
import java.util.Scanner;

import rgou.model.utils.Coordinate;

public class GameEngine {

    private static Random random = new Random();
    private static Scanner reader = new Scanner(System.in);

    private static GameBoard gameBoard;

    public static void main(String[] args) {
        gameBoard = new GameBoard();
        Player DarkPlayer = new Player("X");
        Player LightPlayer = new Player("O");
        View view = new View();

        Player[] players = new Player[] { DarkPlayer, LightPlayer };

        int turn = 0;

        Player winner;
        Player currentPlayer;

        while ((winner = checkWinCondition(DarkPlayer, LightPlayer)) == null) {

            // Display board
            view.printBoard(gameBoard);

            // Set the current player
            currentPlayer = players[turn];

            // Get the user to input roll
            view.askPlayerToRoll(currentPlayer);
            while (getRoll()) {
                view.askPlayerToRoll(currentPlayer);
            }
            int roll = diceRoll();

            // Ask the co-ordinate to move
            view.askWhichPieceToMove(roll);
            Coordinate moveTo = getCoord();
            while (moveTo != null) {
                view.askPlayerToRoll(currentPlayer);
                moveTo = getCoord();
                if (checkCoordValidity(currentPlayer, moveTo) && checkPieceValidity(currentPlayer, moveTo)) {

                }
            }

            turn = 1 - turn;
        }

    }

    public static Player checkWinCondition(Player dP, Player lP) {
        if (dP.getPieces().length == 0) {
            return dP;
        } else if (lP.getPieces().length == 0) {
            return lP;
        }
        return null;
    }

    public static int diceRoll() {
        return random.nextInt(4) + 1;
    }

    public static boolean getRoll() {
        String roll = reader.nextLine();
        if (roll.equals("roll")) {
            System.out.println("Rolling...");
            return false;
        } else if (roll.equals("quit")) {
            System.exit(1);
            return false;
        } else {
            System.out.println("Type 'roll'");
            return true;
        }
    }

    public static Coordinate getCoord() {
        String coord = reader.nextLine();
        String[] xy = coord.split(",");
        try {
            int x;
            int y;
            if (((x = Integer.parseInt(xy[0])) >= 0 && x < 8) && ((y = Integer.parseInt(xy[1])) >= 0 && y < 4)) {
                Coordinate xyPoint = new Coordinate(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                return xyPoint;
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean checkCoordValidity(Player currentPlayer, Coordinate moveTo) {
        if (currentPlayer.getSymbol().equals("X")) {
            return gameBoard.getGamePath().getDARK_PATH().contains(moveTo);
        } else if (currentPlayer.getSymbol().equals("O")) {
            return gameBoard.getGamePath().getLIGHT_PATH().contains(moveTo);
        }
        return false;
    }

    private static boolean checkPieceValidity(Player currentPlayer, Coordinate moveTo) {
        Piece ifExist = gameBoard.getSquare(moveTo.getX(), moveTo.getY()).getCurrentPiece();
        if (ifExist != null) {
            if (currentPlayer.getSymbol().equals("X")) {
                return ifExist.getSymbol().equals("X");

            } else if (currentPlayer.getSymbol().equals("O")) {
                return ifExist.getSymbol().equals("O");
            }
        }
        System.out.println("Incorrect use of method");
        return false;
    }

}
