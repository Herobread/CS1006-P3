package rgou.model.game;

public class Square {

    private Piece currentPiece;
    protected boolean outOfBounds = false;

    protected boolean rossete = false;

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
    public OutOfBoundsSquare() {
        outOfBounds = true;
    }
}

class RosetteSquare extends Square {
    public RosetteSquare() {
        rossete = true;
    }
}
