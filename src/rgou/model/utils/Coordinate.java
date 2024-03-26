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

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;
        if (this == o) return true;

        // Check if the object is null or of a different class
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinate coord = (Coordinate) o;

        return coord.getY() == this.getY() && coord.getX() == this.getX();
    }
}
