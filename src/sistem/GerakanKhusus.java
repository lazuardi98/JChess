package sistem;

import catur.Bidak;

public class GerakanKhusus implements Gerakan
{
    private GerakanNormal move1;
    private GerakanNormal move2;

    public GerakanKhusus(GerakanNormal move1, GerakanNormal move2) {
        this.move1 = move1;
        this.move2 = move2;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public Gerakan undoMove() {
        return new GerakanKhusus((GerakanNormal) this.move2.undoMove(),
            (GerakanNormal) this.move1.undoMove());
    }

    @Override
    public Bidak movedPiece() {
        return this.move1.movedPiece();
    }

    public GerakanNormal move1() {
        return this.move1;
    }

    public GerakanNormal move2() {
        return this.move2;
    }
}
