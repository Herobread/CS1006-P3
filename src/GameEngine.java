import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {

    static Random random = new Random();
    static Scanner reader = new Scanner(System.in);

    static GameBoard gameBoard;
    public static void main(String[] args) {
        gameBoard = new GameBoard();
        Player DarkPlayer = new Player("X");
        Player LightPlayer = new Player("O");
        View view = new View();

        Player[] players = new Player[]{DarkPlayer, LightPlayer};

        int turn = 0;

        Player winner;
        Player currentPlayer;


        while( ( winner = checkWinCondition(DarkPlayer, LightPlayer)) == null) {

            //Display board
            view.printBoard(gameBoard);

            // Set the current player
            currentPlayer = players[turn];

            // Get the user to input roll
            view.askPlayerToRoll(currentPlayer);
            while(getRoll()) {
                view.askPlayerToRoll(currentPlayer);
            }
            int roll = diceRoll();

            // Ask the co-ordinate to move
            view.askWhichPieceToMove(roll);
            CoordinatePoint moveTo = getCoord();
            while(moveTo != null) {
                view.askPlayerToRoll(currentPlayer);
                moveTo = getCoord();
                if(checkCoordValidity(currentPlayer, moveTo) && checkPieceValidity(currentPlayer, moveTo )) {

                }
            }



            turn = 1 - turn;
        }

    }




    public static Player checkWinCondition(Player dP, Player lP) {
        if(dP.getPieces().length == 0) {
            return dP;
        }
        else if(lP.getPieces().length == 0) {
            return lP;
        }
        return null;
    }

    public static int diceRoll() {
        return random.nextInt(4) + 1;
    }

    public static boolean getRoll() {
        String roll = reader.nextLine();
        if(roll.equals("roll")) {
            System.out.println("Rolling...");
            return false;
        }
        else if(roll.equals("quit")) {
            System.exit(1);
            return false;
        }
        else {
            System.out.println("Type 'roll'");
            return true;
        }
    }

    public static CoordinatePoint getCoord() {
        String coord = reader.nextLine();
        String[] xy = coord.split(",");
        try {
            int x;
            int y;
            if(((x = Integer.parseInt(xy[0])) >= 0 && x < 8) && ((y = Integer.parseInt(xy[1])) >= 0 && y < 4)) {
                CoordinatePoint xyPoint = new CoordinatePoint(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                return xyPoint;
            }
            return null;
        }
        catch(NumberFormatException e) {
            return null;
        }
    }

    private static boolean checkCoordValidity(Player currentPlayer, CoordinatePoint moveTo) {
        if(currentPlayer.getSymbol().equals("X")) {
            return gameBoard.getGamePath().getDARK_PATH().contains(moveTo);
        }
        else if(currentPlayer.getSymbol().equals("O")) {
            return gameBoard.getGamePath().getLIGHT_PATH().contains(moveTo);
        }
        return false;
    }

    private static boolean checkPieceValidity(Player currentPlayer, CoordinatePoint moveTo) {
        Piece ifExist = gameBoard.getSquare(moveTo.getX(), moveTo.getY()).getCurrentPiece();
        if(ifExist != null) {
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
