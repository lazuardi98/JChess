package catur;

import sistem.*;

import static sistem.Tipe.*;

/** Implementasi Kuda seperti
 * pada permainan catur.
 */
public class Kuda implements Bidak
{
    private Permainan permainan;

    private Warna color;

    private int x;

    private int y;

    public Kuda(Warna color, Permainan permainan, int x, int y) {
        this.color = color;
        this.permainan = permainan;
        this.x = x;
        this.y = y;
    }

    public String imageString() {
        return this.color.abbrev() + "_" + KUDA.abbrev();
    }

    public Warna color() {
        return this.color;
    }

    public Tipe type() {
        return KUDA;
    }

    public boolean makeValidMove(int a, int b) {
        if (this.permainan.get(a, b) != null
            && this.permainan.get(a, b).color() == this.color) {
            return false;
        } else if ((Math.abs(a - this.x) == 2 && Math.abs(b - this.y) == 1)
            || (Math.abs(b - this.y) == 2 && Math.abs(a - this.x) == 1)) {
            GerakanNormal move = new GerakanNormal(this, this.x, this.y,
                this.permainan.get(a, b), a, b);
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
        if ((this.x - 2 >= 0 && this.y + 1 <= 7 && makeValidMove(this.x - 2, this.y + 1))
            || (this.x - 2 >= 0 && this.y - 1 >= 0 && makeValidMove(this.x - 2, this.y - 1))
            || (this.x - 1 >= 0 && this.y + 2 <= 7 && makeValidMove(this.x - 1, this.y + 2))
            || (this.x - 1 >= 0 && this.y - 2 >= 0 && makeValidMove(this.x - 1, this.y - 2))
            || (this.x + 1 <= 7 && this.y + 2 <= 7 && makeValidMove(this.x + 1, this.y + 2))
            || (this.x + 1 <= 7 && this.y - 2 >= 0 && makeValidMove(this.x + 1, this.y - 2))
            || (this.x + 2 <= 7 && this.y + 1 <= 7 && makeValidMove(this.x + 2, this.y + 1))
            || (this.x + 2 <= 7 && this.y - 1 >= 0 && makeValidMove(this.x + 2, this.y - 1))) {
            this.permainan.undoMove();
            return true;
        } else {
            return false;
        }
    }

    public boolean canCapture(int a, int b) {
        return ((Math.abs(a - this.x) == 2 && Math.abs(b - this.y) == 1)
            || (Math.abs(b - this.y) == 2 && Math.abs(a - this.x) == 1));
    }

    private boolean makeMoveCareful(Gerakan gerakan) {
        this.permainan.makeMove(gerakan);
        if (this.permainan.inCheck(this.permainan.turn().opposite())) {
            this.permainan.undoMove();
            return false;
        } else {
            return true;
        }
    }
}
