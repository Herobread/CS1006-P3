public class Square {

    private Piece currentPiece;
    private boolean outOfBounds = false;

    private boolean rossete = false;

    public Square() {

    }

    public Piece getCurrentPiece() {
        return this.currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public boolean isOutOfBounds() {
        return this.outOfBounds;
    }

    public boolean isRossete() {
        return rossete;
    }
}

class OutOfBoundsSquare extends Square {
    private boolean outOfBounds = true;
}

class RosetteSquare extends Square {
    private boolean rossete = true;
}
