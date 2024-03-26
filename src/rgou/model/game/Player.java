package rgou.model.game;

// Will Likely change this
public class Player {
    private String symbol; // TODO remove
    private Piece[] pieces = new Piece[7];

    public Player(String symbol) {
        this.symbol = symbol;
        for (int i = 0; i < 7; i++) {
            pieces[i] = new Piece(this, symbol);
        }
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public String getSymbol() {
        return symbol;
    }
}
