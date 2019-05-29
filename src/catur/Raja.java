package catur;

import sistem.*;

import static sistem.Tipe.*;

/** Implementasi Raja seperti
 * pada permainan catur.
 */
public class Raja implements Bidak
{
    private Permainan permainan;
    private Warna color;
    private int x;
    private int y;
    private boolean move;

    public Raja(Warna color, Permainan permainan, int x, int y) {
        this.color = color;
        this.permainan = permainan;
        this.x = x;
        this.y = y;
        this.move = false;
    }

    public String imageString() {
        return this.color.abbrev() + "_" + RAJA.abbrev();
    }

    public Warna color() {
        return this.color;
    }

    public Tipe type() {
        return RAJA;
    }

    public boolean makeValidMove(int a, int b) {
        if (Math.abs(a - this.x) <= 1 && Math.abs(b - this.y) <= 1
            && (this.permainan.get(a, b) == null || this.permainan.get(a, b).color() != this.color)) {
            Gerakan gerakan = new GerakanNormal(this, this.x, this.y, this.permainan.get(a, b), a, b);
            return makeMoveCareful(gerakan);
        } else if (this.x == originalX() && this.y == originalY()
            && a == originalX() + 2 && b == originalY()
            && this.permainan.get(a - 1, b) == null && this.permainan.get(a, b) == null
            && this.permainan.get(a + 1, b) != null && this.permainan.get(a + 1, b).type() == BENTENG
            && this.permainan.get(a + 1, b).color() == this.color && !this.permainan.inCheck(this.color)
            && !this.permainan.guarded(a - 1, b) && !this.permainan.guarded(a, b)
            && !this.move && !((Benteng) this.permainan.get(a + 1, b)).moved()) {
            GerakanNormal move1 = new GerakanNormal(this, this.x, this.y,
                this.permainan.get(a, b), a, b);
            GerakanNormal move2 = new GerakanNormal(this.permainan.get(a + 1, b),
                a + 1, b, null, a - 1, b);
            GerakanKhusus move = new GerakanKhusus(move1, move2);
            return makeMoveCareful(move);
        } else if (this.x == originalX() && this.y == originalY()
            && a == originalX() - 2 && b == originalY()
            && this.permainan.get(a - 1, b) == null && this.permainan.get(a, b) == null
            && this.permainan.get(a + 1, b) == null && this.permainan.get(a - 2, b) != null
            && this.permainan.get(a - 2, b).type() == BENTENG
            && this.permainan.get(a - 2, b).color() == this.color
            && !this.permainan.inCheck(this.color)
            && !this.permainan.guarded(a - 1, b) && !this.permainan.guarded(a, b)
            && !this.move && !((Benteng) this.permainan.get(a - 2, b)).moved()) {
            GerakanNormal move1 = new GerakanNormal(this, this.x, this.y,
                this.permainan.get(a, b), a, b);
            GerakanNormal move2 = new GerakanNormal(this.permainan.get(a - 2, b), a - 2,
                b, null, a + 1, b);
            GerakanKhusus move = new GerakanKhusus(move1, move2);
            return makeMoveCareful(move);
        } else {
            return false;
        }
    }
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasMove() {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (this.x + i >= 0 && this.x + i <= 7 && this.y + j >= 0 && this.y + j <= 7) {
                    if (makeValidMove(this.x + i, this.y + j)) {
                        this.permainan.undoMove();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canCapture(int a, int b) {
        return Math.abs(a - this.x) <= 1 && Math.abs(b - this.y) <= 1;
    }

    private boolean makeMoveCareful(Gerakan gerakan) {
        this.permainan.makeMove(gerakan);
        if (this.permainan.inCheck(this.permainan.turn().opposite())) {
            this.permainan.undoMove();
            return false;
        }
        else {
            if (!this.permainan.getMoved()){
                this.permainan.undoMove();
            }
            else {
                this.move = true;
            }
            return true;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private int originalX() {
        return 4;
    }

    private int originalY() {
        if (this.color == Warna.PUTIH) {
            return 7;
        } else {
            return 0;
        }
    }
}
