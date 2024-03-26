package rgou.model.utils;

public class Coordinate {
    private int X;
    private int Y;

    public Coordinate(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public String toString() {
        return "(" + X + "," + Y + ")";
    }
}
