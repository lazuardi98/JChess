package catur;

import sistem.*;

import static sistem.Tipe.*;

/** Implementasi Pion seperti
 * pada permainan catur.
 */
public class Pion implements Bidak
{
    private Permainan permainan;

    private Warna color;

    private int x;

    private int y;

    public Pion(Warna color, Permainan permainan, int x, int y) {
        this.color = color;
        this.permainan = permainan;
        this.x = x;
        this.y = y;
    }

    public String imageString() {
        return this.color.abbrev() + "_" + PION.abbrev();
    }

    public Warna color() {
        return this.color;
    }

    public Tipe type() {
        return PION;
    }

    public boolean makeValidMove(int a, int b) {
        if (this.y == start()) {
            if (b == this.y + 2 * direction()) {
                if (a == this.x && this.permainan.get(a, this.y + direction()) == null
                    && this.permainan.get(a, b) == null) {
                    Gerakan gerakan = new GerakanNormal(this, this.x, this.y,
                        this.permainan.get(a, b), a, b);
                    return makeMoveCareful(gerakan);
                } else {
                    return false;
                }
            }
        }
        if (b == this.y + direction()) {
            if (a == this.x && this.permainan.get(a, b) == null) {
                if (b == start() + 6 * direction()) {
                    GerakanNormal move1 = new GerakanNormal(this, this.x, this.y,
                        this.permainan.get(a, b), a, b);
                    Bidak newQu = new Menteri(this.color, this.permainan, a, b);
                    GerakanNormal move2 = new GerakanNormal(newQu, a, b, this, a, b);
                    GerakanKhusus move = new GerakanKhusus(move1, move2);
                    return makeMoveCareful(move);
                } else {
                    Gerakan gerakan = new GerakanNormal(this, this.x, this.y,
                        this.permainan.get(a, b), a, b);
                    return makeMoveCareful(gerakan);
                }
            } else if (Math.abs(a - this.x) == 1 && this.permainan.get(a, b) != null
                && this.permainan.get(a, b).color() != this.color) {
                if (b == start() + 6 * direction()) {
                    GerakanNormal move1 = new GerakanNormal(this, this.x, this.y,
                        this.permainan.get(a, b), a, b);
                    Bidak newQueen = new Menteri(this.color, this.permainan, a, b);
                    GerakanNormal move2 = new GerakanNormal(newQueen, a, b,
                        this, a, b);
                    GerakanKhusus move = new GerakanKhusus(move1, move2);
                    return makeMoveCareful(move);
                } else {
                    Gerakan gerakan = new GerakanNormal(this, this.x, this.y,
                        this.permainan.get(a, b), a, b);
                    return makeMoveCareful(gerakan);
                }
            } else if (Math.abs(a - this.x) == 1 && this.permainan.get(a, b) == null
                && this.y == start() + 3 * direction() && this.permainan.get(a, this.y) != null
                && this.permainan.get(a, this.y).color() != this.color
                && this.permainan.get(a, this.y).type() == PION
                && this.permainan.get(a, this.y) == this.permainan.lastMover()) {
                GerakanNormal move1 = new GerakanNormal(this, this.x, this.y,
                    this.permainan.get(a, b), a, b);
                GerakanNormal move2 = new GerakanNormal(null, this.x, this.y,
                    this.permainan.get(a, b - direction()), a, b - direction());
                GerakanKhusus move = new GerakanKhusus(move1, move2);
                return makeMoveCareful(move);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasMove() {
        if ((this.x + 1 <= 7 && makeValidMove(this.x + 1, this.y + 1))
            || (makeValidMove(this.x, this.y + 1))
            || (this.x - 1 >= 0 && makeValidMove(this.x - 1, this.y + 1))) {
            this.permainan.undoMove();
            return true;
        } else {
            return false;
        }
    }

    
    public boolean canCapture(int a, int b) {
        return (b == this.y + direction() && Math.abs(a - this.x) == 1);
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

    private int direction() {
        return this.color.direction();
    }

    private int start() {
        if (this.color == Warna.PUTIH) {
            return 6;
        } else {
            return 1;
        }
    }
}
