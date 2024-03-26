package rgou.model.game;

import java.util.ArrayList;

import rgou.model.utils.Coordinate;

public class GameBoard {
    private Square[][] gameBoard = new Square[8][3];

    private Path gamePath = new Path();

    public GameBoard() {
        gameBoard[0] = new Square[] { new RosetteSquare(), new Square(), new RosetteSquare() };
        gameBoard[1] = new Square[] { new Square(), new Square(), new Square() };
        gameBoard[2] = new Square[] { new Square(), new Square(), new Square() };
        gameBoard[3] = new Square[] { new Square(), new RosetteSquare(), new Square() };
        gameBoard[4] = new Square[] { new OutOfBoundsSquare(), new Square(), new OutOfBoundsSquare() };
        gameBoard[5] = new Square[] { new OutOfBoundsSquare(), new Square(), new OutOfBoundsSquare() };
        gameBoard[6] = new Square[] { new RosetteSquare(), new Square(), new RosetteSquare() };
        gameBoard[7] = new Square[] { new Square(), new Square(), new Square() };

    }

    public Square[][] getGameBoard() {
        return gameBoard;
    }

    public Path getGamePath() {
        return gamePath;
    }

    public Square getSquare(Coordinate coord) {
        return gameBoard[coord.getY()][coord.getX()];
    }


}

class Path {

    private ArrayList<Coordinate> LIGHT_PATH = new ArrayList<>();
    private ArrayList<Coordinate> DARK_PATH = new ArrayList<>();

    public Path() {
        // Creates first stretch of path, this is from the 3 index X co-ordinate to the
        // 0 index x co-ordiante
        for (int y = 3; y >= 0; y--) {
            DARK_PATH.add(new Coordinate(0, y));
            LIGHT_PATH.add(new Coordinate(2, y));
        }

        // Creates the shared path, this is down the middle of the board
        for (int y = 0; y < 8; y++) {
            DARK_PATH.add(new Coordinate(1, y));
            LIGHT_PATH.add(new Coordinate(1, y));
        }

        // Creates the ending stretch of the path
        DARK_PATH.add(new Coordinate(0, 7));
        LIGHT_PATH.add(new Coordinate(2, 7));

        DARK_PATH.add(new Coordinate(0,6));
        LIGHT_PATH.add(new Coordinate(2, 6));

    }

    public ArrayList<Coordinate> getDARK_PATH() {
        return DARK_PATH;
    }

    public ArrayList<Coordinate> getLIGHT_PATH() {
        return LIGHT_PATH;
    }
}
