public class Piece {
    private String symbol;
    private Player player;
    public Piece(Player player, String symbol) {
        this.player = player;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
