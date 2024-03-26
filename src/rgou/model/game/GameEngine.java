package rgou.model.game;

import java.nio.charset.CoderResult;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import rgou.model.utils.Coordinate;

public class GameEngine {

    private static Random random = new Random();
    private static Scanner reader = new Scanner(System.in);

    private static GameBoard gameBoard;

    private static Player currentPlayer;

    public static void main(String[] args) {
        gameBoard = new GameBoard();
        Player DarkPlayer = new Player("X");
        Player LightPlayer = new Player("O");
        View view = new View();

        DarkPlayer.setPath(gameBoard.getGamePath().getDARK_PATH());
        LightPlayer.setPath(gameBoard.getGamePath().getLIGHT_PATH());

        System.out.println(DarkPlayer.getPath());


        Player[] players = new Player[] { DarkPlayer, LightPlayer };

        int turn = 0;

        Player winner;


        while ((winner = checkWinCondition(DarkPlayer, LightPlayer)) == null) {

            // Display board
            view.printBoard(gameBoard);

            // Display how many pieces left in stock
            view.printPieceStock(players);

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
            Coordinate pieceToMoveLocation = getCoord();

            // Move piece
            if(validCoordinate(currentPlayer, pieceToMoveLocation)) {
                Coordinate startCoord = pieceToMoveLocation;
                Coordinate endCoord = findEndCoordiante(currentPlayer, startCoord, roll);

                System.out.println(startCoord+" "+endCoord);

                movePiece(startCoord, endCoord);
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
        if(xy.length != 2) {
            System.out.println("Please enter an actual co-ordinate!");
        }
        try {
            int x;
            int y;
            if (((x = Integer.parseInt(xy[0])) >= 0 && x < 3) && ((y = Integer.parseInt(xy[1])) >= 0 && y < 8)) {
                Coordinate xyPoint = new Coordinate(x, y);
                return xyPoint;
            }
            else if ((x = Integer.parseInt(xy[0])) == -1 && (y = Integer.parseInt(xy[1])) == -1) {
                return new Coordinate(x, y);
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean checkPlayerPieceOnCoord(Player currentPlayer, Coordinate pieceToMoveLocation) {
        return gameBoard.getSquare(pieceToMoveLocation).getCurrentPiece().getSymbol().equals(currentPlayer.getSymbol());
    }

    private static boolean validCoordinate(Player currentPlayer, Coordinate pieceToMoveLocation) {
        if(pieceToMoveLocation.getX() == -1 && pieceToMoveLocation.getY() == -1) {
            return true;
        }
        return checkPlayerPieceOnCoord(currentPlayer, pieceToMoveLocation);
    }

    private static Coordinate findEndCoordiante(Player currentPlayer, Coordinate startCoord, int roll) {
        if(startCoord.getX() == - 1 && startCoord.getY() == -1) {
            return currentPlayer.getPath().get(roll - 1);
        }
        int currentLocation = currentPlayer.getPath().indexOf(startCoord);

        return currentPlayer.getPath().get(currentLocation + roll);
    }

    public static void movePiece(Coordinate startCoord, Coordinate endCoord) {
        Piece pieceToMove;
        if(startCoord.getX() == -1 && startCoord.getY() == -1) {
            pieceToMove = currentPlayer.getInStock().poll();
        }
        else {
            pieceToMove = gameBoard.getSquare(startCoord).getCurrentPiece();
            gameBoard.getSquare(startCoord).setCurrentPiece(null);
        }

        gameBoard.getSquare(endCoord).setCurrentPiece(pieceToMove);
    }


}
