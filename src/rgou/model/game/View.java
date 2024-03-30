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
                    if(symbol.equals("e") && col.getClass() == RosetteSquare.class) {
                        symbol = "r";
                    }
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
        System.out.println("If you want to take out a new piece, type -1,-1");
        System.out.println("Type the co-ordinate of the piece you want to move: ");
    }

    public void printPieceStock(Player[] players) {
        System.out.println(players[0].getSymbol()+"'s player has "+players[0].getInStock().size()+" pieces in stock");
        System.out.println(players[1].getSymbol()+"'s player has "+players[1].getInStock().size()+" pieces in stock");
    }

    public void landedOnRosette(Player player) {
        System.out.println("Player "+player.getSymbol()+" has landed on a rosette!");
        System.out.println("They get to roll again!");
    }
}
