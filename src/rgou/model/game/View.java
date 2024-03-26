package rgou.model.game;

// Replace this with a GUI
public class View {
    public void printBoard(GameBoard gameBoard) {
        for (Square[] row : gameBoard.getGameBoard()) {
            for (Square col : row) {
                String symbol;
                if (col.getClass() == OutOfBoundsSquare.class) {
                    symbol = "b";
                } else {
                    symbol = (col.getCurrentPiece() == null) ? "e" : col.getCurrentPiece().getSymbol();
                }
                System.out.print(" " + symbol + " ");
            }
            System.out.println();
        }
    }

    public void askPlayerToRoll(Player player) {
        System.out.println("It is the " + player.getSymbol() + " Player's turn to roll: ");
    }

    public void askWhichPieceToMove(int rollNum) {
        System.out.println("You have rolled a " + rollNum);
        System.out.println("Co-ordinate format -> x,y");
        System.out.println("Type the co-ordinate of the piece you want to move: ");
    }
}
