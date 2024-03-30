package rgou.model.game;

import rgou.model.utils.Coordinate;

import java.util.ArrayList;
import java.util.LinkedList;

// Will Likely change this
public class Player {
    private String symbol; // TODO remove
    private Piece[] pieces = new Piece[7];
    private LinkedList<Piece> inStock = new LinkedList<>();

    private LinkedList<Piece> home = new LinkedList<>();


    private ArrayList<Coordinate> path;

    public Player(String symbol) {
        this.symbol = symbol;
        for (int i = 0; i < 7; i++) {
            pieces[i] = new Piece(this, symbol);
            inStock.add(new Piece(this, symbol));
        }
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public String getSymbol() {
        return symbol;
    }

    public LinkedList<Piece> getInStock() {
        return inStock;
    }

    public Piece takeFromStock() {
        return inStock.poll();
    }

    public void addToStock(Piece piece) {
        inStock.add(piece);
    }

    public void setPath(ArrayList<Coordinate> path) {
        this.path = path;
    }

    public ArrayList<Coordinate> getPath() {
        return path;
    }

    public void addToHome(Piece piece) {
        home.add(piece);
    }

    public int getHomeSize() {
        return home.size();
    }
}
