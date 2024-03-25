
import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private Square[][] gameBoard = new Square[8][3];

    private Path gamePath = new Path();
    public GameBoard() {
        gameBoard[0] = new Square[]{new RosetteSquare(), new Square(), new RosetteSquare()};
        gameBoard[1] = new Square[]{new Square(), new Square(), new Square()};
        gameBoard[2] = new Square[]{new Square(), new Square(), new Square()};
        gameBoard[3] = new Square[]{new Square(), new RosetteSquare(), new Square()};
        gameBoard[4] = new Square[]{new OutOfBoundsSquare(), new Square(), new OutOfBoundsSquare()};
        gameBoard[5] = new Square[]{new OutOfBoundsSquare(), new Square(), new OutOfBoundsSquare()};
        gameBoard[6] = new Square[]{new RosetteSquare(), new Square(), new RosetteSquare()};
        gameBoard[7] = new Square[]{new Square(), new Square(), new Square()};

    }

    public Square[][] getGameBoard() {
        return gameBoard;
    }

    public Path getGamePath() {
        return gamePath;
    }

    public Square getSquare(int x, int y) {
        return gameBoard[x][y];
    }
}

class Path {

    private ArrayList<CoordinatePoint> LIGHT_PATH = new ArrayList<>();
    private ArrayList<CoordinatePoint> DARK_PATH= new ArrayList<>();

    public Path() {
        // Creates first stretch of path, this is from the 3 index X co-ordinate to the 0 index x co-ordiante
        for(int x = 3; x >= 0; x--) {
            DARK_PATH.add(new CoordinatePoint(x, 0));
            LIGHT_PATH.add(new CoordinatePoint(x, 2));
        }

        // Creates the shared path, this is down the middle of the board
        for(int x = 0; x < 8; x++) {
            DARK_PATH.add(new CoordinatePoint(x, 1));
            LIGHT_PATH.add(new CoordinatePoint(x, 1));
        }

        // Creates the ending stretch of the path
        DARK_PATH.add(new CoordinatePoint(7,0));
        LIGHT_PATH.add(new CoordinatePoint(7,2));

        DARK_PATH.add(new CoordinatePoint(6,0));
        LIGHT_PATH.add(new CoordinatePoint(6,2));

    }

    public ArrayList<CoordinatePoint> getDARK_PATH() {
        return DARK_PATH;
    }

    public ArrayList<CoordinatePoint> getLIGHT_PATH() {
        return LIGHT_PATH;
    }
}

