package catur;

import sistem.*;

import static sistem.Tipe.*;

/** Implementasi Benteng seperti
 * pada permainan catur.
 */
public class Benteng implements Bidak
{
    private Permainan permainan;
    private Warna color;
    private int x;
    private int y;
    private boolean move;

    public Benteng(Warna color, Permainan permainan, int x, int y) {
        this.color = color;
        this.permainan = permainan;
        this.x = x;
        this.y = y;
    }

    public String imageString() { return this.color.abbrev() + "_" + BENTENG.abbrev(); }

    public Warna color() {
        return this.color;
    }

    public Tipe type() {
        return BENTENG;
    }

    public boolean makeValidMove(int a, int b) {
        if (this.permainan.get(a, b) != null
            && this.permainan.get(a, b).color() == this.color) {
            return false;
        } else if (a == this.x) {
            int dir = (b - this.y) / Math.abs(b - this.y);
            for (int i = this.y + dir; i != b; i += dir) {
                if (this.permainan.get(this.x, i) != null) {
                    return false;
                }
            }
            Gerakan gerakan = new GerakanNormal(this, this.x, this.y, this.permainan.get(a, b), a, b);
            return makeMoveCareful(gerakan);
        } else if (b == this.y) {
            int dir = (a - this.x) / Math.abs(a - this.x);
            for (int i = this.x + dir; i != a; i += dir) {
                if (this.permainan.get(i, this.y) != null) {
                    return false;
                }
            }
            Gerakan gerakan = new GerakanNormal(this, this.x, this.y, this.permainan.get(a, b), a, b);
            return makeMoveCareful(gerakan);
        } else {
            return false;
        }
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasMove() {
        if ((this.x + 1 <= 7 && makeValidMove(this.x + 1, this.y))
            || (this.x - 1 >= 0 && makeValidMove(this.x - 1, this.y))
            || (this.y + 1 <= 7 && makeValidMove(this.x, this.y + 1))
            || (this.y - 1 >= 0 && makeValidMove(this.x, this.y - 1))) {
            this.permainan.undoMove();
            return true;
        } else {
            return false;
        }
    }

    public boolean canCapture(int a, int b) {
        if (a == this.x) {
            int dir = (b - this.y) / Math.abs(b - this.y);
            for (int i = this.y + dir; i != b; i += dir) {
                if (this.permainan.get(this.x, i) != null) {
                    return false;
                }
            }
            return true;
        } else if (b == this.y) {
            int dir = (a - this.x) / Math.abs(a - this.x);
            for (int i = this.x + dir; i != a; i += dir) {
                if (this.permainan.get(i, this.y) != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
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

    public boolean moved() {
        return this.move;
    }
}
