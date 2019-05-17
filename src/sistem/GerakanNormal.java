package sistem;

import catur.Bidak;

public class GerakanNormal implements Gerakan
{
    private Bidak selected;

    private Bidak target;

    private Bidak replace;

    private int x1;

    private int y1;

    private int x2;

    private int y2;

    public GerakanNormal(Bidak s, int x1, int y1, Bidak t, int x2, int y2) {
        this.selected = s;
        this.x1 = x1;
        this.y1 = y1;
        this.target = t;
        this.x2 = x2;
        this.y2 = y2;
        this.replace = null;
    }

    private GerakanNormal(Bidak s, int x1, int y1, Bidak t,
                          int x2, int y2, Bidak r) {
        this.selected = s;
        this.x1 = x1;
        this.y1 = y1;
        this.target = t;
        this.x2 = x2;
        this.y2 = y2;
        this.replace = r;
    }

    public boolean isDouble() {
        return false;
    }

    public Gerakan undoMove() {
        return new GerakanNormal(this.selected, this.x2, this.y2, null, this.x1, this.y1, this.target);
    }
    
    public Bidak movedPiece() {
        return this.selected;
    }

    public Bidak selected() {
        return this.selected;
    }

    public Bidak target() {
        return this.target;
    }

    public Bidak replace() {
        return this.replace;
    }

    public int x1() {
        return this.x1;
    }

    public int y1() {
        return this.y1;
    }

    public int x2() {
        return this.x2;
    }

    public int y2() {
        return this.y2;
    }
}
