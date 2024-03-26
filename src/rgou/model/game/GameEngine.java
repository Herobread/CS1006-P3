package rgou.model.game;

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
            if(checkIfNewPieceOut(currentPlayer, pieceToMoveLocation, roll)) {
                takeOutNewPiece(currentPlayer, roll);
            } else if (checkCoordValidity(currentPlayer, pieceToMoveLocation, roll)) {
                moveCurrentPiece(currentPlayer, pieceToMoveLocation, roll);
            }
            else {
                System.out.println(checkCoordValidity(currentPlayer, pieceToMoveLocation, roll));
                System.out.println(gameBoard.getSquare(pieceToMoveLocation).getCurrentPiece().getSymbol());
            }


            // Validate co-ordinate

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

    private static boolean checkCoordValidity(Player currentPlayer, Coordinate moveTo, int roll) {
        if (currentPlayer.getSymbol().equals("X")) {
            int currentLocation = gameBoard.getGamePath().getDARK_PATH().indexOf(moveTo);
            Coordinate newLocation = gameBoard.getGamePath().getDARK_PATH().get(currentLocation+ roll -1);

            return gameBoard.getGamePath().getDARK_PATH().contains(moveTo) &&
                    "X".equals(gameBoard.getSquare(moveTo).getCurrentPiece().getSymbol()) &&
                    !"X".equals(gameBoard.getSquare(newLocation).getCurrentPiece().getSymbol());


        } else if (currentPlayer.getSymbol().equals("O")) {
            int currentLocation = gameBoard.getGamePath().getLIGHT_PATH().indexOf(moveTo);
            Coordinate newLocation = gameBoard.getGamePath().getDARK_PATH().get(currentLocation+ roll -1);

            return gameBoard.getGamePath().getLIGHT_PATH().contains(moveTo) &&
                    "O".equals(gameBoard.getSquare(moveTo).getCurrentPiece().getSymbol()) &&
                    !"O".equals(gameBoard.getSquare(newLocation).getCurrentPiece().getSymbol());
        }
        return false;
    }


    public static Player getPlayerTurn() {
        return currentPlayer;
    }

    private static boolean checkIfNewPieceOut(Player currentPlayer, Coordinate moveTo, int roll) {
        if (currentPlayer.getSymbol().equals("X")) {
            Coordinate coord = gameBoard.getGamePath().getDARK_PATH().get(roll -1);
            if(gameBoard.getSquare(coord).equals("X")) {
                return false;
            }
        } else if (currentPlayer.getSymbol().equals("O")) {
            Coordinate coord = gameBoard.getGamePath().getLIGHT_PATH().get(roll -1);
            if(gameBoard.getSquare(coord).equals("O")) {
                return false;
            }
        }
        return (moveTo.getX() == -1 && moveTo.getY() == -1) && currentPlayer.getInStock().size() > 0;
    }

    public static void takeOutNewPiece(Player currentPlayer, int roll) {
        if (currentPlayer.getSymbol().equals("X")) {
            Coordinate coord = gameBoard.getGamePath().getDARK_PATH().get(roll -1);
            gameBoard.getSquare(coord).setCurrentPiece(currentPlayer.takeFromStock());
        } else if (currentPlayer.getSymbol().equals("O")) {
            Coordinate coord = gameBoard.getGamePath().getLIGHT_PATH().get(roll -1);
            gameBoard.getSquare(coord).setCurrentPiece(currentPlayer.takeFromStock());
        }
    }

    public static void moveCurrentPiece(Player currentPlayer,Coordinate pieceLocation, int roll) {
        if (currentPlayer.getSymbol().equals("X")) {
            int currentLocation = gameBoard.getGamePath().getDARK_PATH().indexOf(pieceLocation);
            Coordinate newLocation = gameBoard.getGamePath().getDARK_PATH().get(currentLocation+ roll -1);

            // Setting previous to null
            Piece move = gameBoard.getSquare(pieceLocation).getCurrentPiece();
            gameBoard.getSquare(pieceLocation).setCurrentPiece(null);

            // Set new piece
            gameBoard.getSquare(newLocation).setCurrentPiece(move);

        } else if (currentPlayer.getSymbol().equals("O")) {
            Coordinate coord = gameBoard.getGamePath().getLIGHT_PATH().get(roll -1);
            int currentLocation = gameBoard.getGamePath().getLIGHT_PATH().indexOf(pieceLocation);
            Coordinate newLocation = gameBoard.getGamePath().getLIGHT_PATH().get(currentLocation+ roll -1);

            // Setting previous to null
            Piece move = gameBoard.getSquare(pieceLocation).getCurrentPiece();
            gameBoard.getSquare(pieceLocation).setCurrentPiece(null);

            // Set new piece
            gameBoard.getSquare(newLocation).setCurrentPiece(move);
        }
    }




}
