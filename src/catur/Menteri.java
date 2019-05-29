package catur;

import sistem.*;

import static sistem.Tipe.*;

/** Implementasi Menteri seperti
 * pada permainan catur.
 */
public class Menteri implements Bidak
{
    private Permainan permainan;
    private Warna color;
    private int x;
    private int y;

    public Menteri(Warna color, Permainan permainan, int x, int y) {
        this.color = color;
        this.permainan = permainan;
        this.x = x;
        this.y = y;
    }
    
    public String imageString() {
        return this.color.abbrev() + "_" + MENTERI.abbrev();
    }

    public Warna color() {
        return this.color;
    }
    
    public Tipe type() {
        return MENTERI;
    }
    
    public boolean makeValidMove(int a, int b) {
        if (this.permainan.get(a, b) != null
            && this.permainan.get(a, b).color() == this.color) {
            return false;
        } else if (a + b == this.x + this.y) {
            int dir = (a - this.x) / Math.abs(a - this.x);
            for (int i = this.x + dir, j = this.y - dir; i != a; i += dir, j -= dir) {
                if (this.permainan.get(i, j) != null) {
                    return false;
                }
            }
            Gerakan gerakan = new GerakanNormal(this, this.x, this.y, this.permainan.get(a, b), a, b);
            return makeMoveCareful(gerakan);
        } else if (a - b == this.x - this.y) {
            int dir = (a - this.x) / Math.abs(a - this.x);
            for (int i = this.x + dir, j = this.y + dir; i != a; i += dir, j += dir) {
                if (this.permainan.get(i, j) != null) {
                    return false;
                }
            }
            Gerakan gerakan = new GerakanNormal(this, this.x, this.y, this.permainan.get(a, b), a, b);
            return makeMoveCareful(gerakan);
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
        if (a + b == this.x + this.y) {
            int dir = (a - this.x) / Math.abs(a - this.x);
            for (int i = this.x + dir, j = this.y - dir; i != a; i += dir, j -= dir) {
                if (this.permainan.get(i, j) != null) {
                    return false;
                }
            }
            return true;
        } else if (a - b == this.x - this.y) {
            int dir = (a - this.x) / Math.abs(a - this.x);
            for (int i = this.x + dir, j = this.y + dir; i != a; i += dir, j += dir) {
                if (this.permainan.get(i, j) != null) {
                    return false;
                }
            }
            return true;
        } else if (a == this.x) {
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
            return true;
        }
    }
}
