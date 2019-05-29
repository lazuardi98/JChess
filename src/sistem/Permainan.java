package sistem;

import catur.*;
import grafik.GUI;

import java.util.List;
import java.util.ArrayList;

import static sistem.Warna.*;

/** Inilisiasi
 * permainan catur
 */

public class Permainan
{
    private Bidak[][] papan;
    private GUI gui;
    private Warna giliran;
    private List<Gerakan> gerakan;
    private Raja hRj;
    private Raja pRj;
    private int selectedX;
    private int selectedY;
    private boolean moved;
    private boolean end;
    private boolean[][] possibleGerakan;

    public Permainan() {
        this.gerakan = new ArrayList<Gerakan>();
        this.gui = new GUI("JChess", this);
        this.newPermainan();
    }

    public void newPermainan() {
        initializeBoard();
        newPossibleMove();
        this.gerakan.clear();
        this.giliran = PUTIH;
        moved = false;
        end = false;
        selectedX = -1;
        selectedY = -1;
    }

    public void quit() {
        System.exit(0);
    }

    public void undoMove() {
        if (this.gerakan.size() > 0) {
            Gerakan lastGerakan = this.gerakan.remove(this.gerakan.size() - 1);
            makeMove(lastGerakan.undoMove());
            this.gerakan.remove(this.gerakan.size() - 1);
        }
    }

    public void makeMove(Gerakan gerakan) {
        this.gerakan.add(gerakan);
        if (!gerakan.isDouble()) {
            GerakanNormal normal = (GerakanNormal) gerakan;
            executeMove(normal);
        } else {
            GerakanKhusus khusus = (GerakanKhusus) gerakan;
            executeMove(khusus.move1());
            executeMove(khusus.move2());
        }
        this.giliran = this.giliran.opposite();
    }

    private void executeMove(GerakanNormal move) {
        this.papan[move.x1()][move.y1()] = move.replace();
        if (move.replace() != null) {
            move.replace().setLocation(move.x1(), move.y1());
        }
        this.papan[move.x2()][move.y2()] = move.selected();
        if (move.selected() != null) {
            move.selected().setLocation(move.x2(), move.y2());
        }
        if (move.target() != null) {
            move.target().setLocation(-1, -1);
        }
    }

    public boolean inCheck(Warna color) {
        int x = kingX(color);
        int y = kingY(color);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bidak p = get(i, j);
                if (p != null && p.color() == color.opposite()
                    && p.canCapture(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean noMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bidak p = get(i, j);
                if (p != null && p.color() == this.giliran) {
                    if (p.hasMove()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void newPossibleMove(){
        boolean[][] newPossible = new boolean[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                newPossible[x][y] = false;
            }
        }
        possibleGerakan = newPossible;
    }

    public boolean getPossibleGerakan(int x, int y){
        return possibleGerakan[x][y];
    }

    public void setPossibleGerakan(Bidak pilihanBidak){
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (x == selectedX() && y == selectedY()){
                    possibleGerakan[x][y] = false;
                }
                else {
                    if (pilihanBidak.makeValidMove(x, y)){
                        possibleGerakan[x][y] = true;
                    }
                    else {
                        possibleGerakan[x][y] = false;
                    }
                }
            }
        }
    }

    public boolean guarded(int x, int y) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bidak p = get(i, j);
                if (p != null && p.color() == this.giliran.opposite()
                    && p.canCapture(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Bidak get(int i, int j) {
        return this.papan[i][j];
    }

    public Bidak lastMover() {
        return this.gerakan.get(this.gerakan.size() - 1).movedPiece();
    }

    private void initializeBoard() {
        hRj = new Raja(HITAM, this, 4, 0);
        Bidak hB0 = new Benteng(HITAM, this, 0, 0);
        Bidak hK0 = new Kuda(HITAM, this, 1, 0);
        Bidak hG0 = new Gajah(HITAM, this, 2, 0);
        Bidak hMe = new Menteri(HITAM, this, 3, 0);
        Bidak hG1 = new Gajah(HITAM, this, 5, 0);
        Bidak hK1 = new Kuda(HITAM, this, 6, 0);
        Bidak hB1 = new Benteng(HITAM, this, 7, 0);
        Bidak hP0 = new Pion(HITAM, this, 0, 1);
        Bidak hP1 = new Pion(HITAM, this, 1, 1);
        Bidak hP2 = new Pion(HITAM, this, 2, 1);
        Bidak hP3 = new Pion(HITAM, this, 3, 1);
        Bidak hP4 = new Pion(HITAM, this, 4, 1);
        Bidak hP5 = new Pion(HITAM, this, 5, 1);
        Bidak hP6 = new Pion(HITAM, this, 6, 1);
        Bidak hP7 = new Pion(HITAM, this, 7, 1);
        pRj = new Raja(PUTIH, this, 4, 7);
        Bidak pB0 = new Benteng(PUTIH, this, 0, 7);
        Bidak pK0 = new Kuda(PUTIH, this, 1, 7);
        Bidak pG0 = new Gajah(PUTIH, this, 2, 7);
        Bidak pMe = new Menteri(PUTIH, this, 3, 7);
        Bidak pG1 = new Gajah(PUTIH, this, 5, 7);
        Bidak pK1 = new Kuda(PUTIH, this, 6, 7);
        Bidak pB1 = new Benteng(PUTIH, this, 7, 7);
        Bidak pP0 = new Pion(PUTIH, this, 0, 6);
        Bidak pP1 = new Pion(PUTIH, this, 1, 6);
        Bidak pP2 = new Pion(PUTIH, this, 2, 6);
        Bidak pP3 = new Pion(PUTIH, this, 3, 6);
        Bidak pP4 = new Pion(PUTIH, this, 4, 6);
        Bidak pP5 = new Pion(PUTIH, this, 5, 6);
        Bidak pP6 = new Pion(PUTIH, this, 6, 6);
        Bidak pP7 = new Pion(PUTIH, this, 7, 6);
        Bidak[][] newPapan = {
                {hB0, hP0, null, null, null, null, pP0, pB0}, 
                {hK0, hP1, null, null, null, null, pP1, pK0}, 
                {hG0, hP2, null, null, null, null, pP2, pG0}, 
                {hMe, hP3, null, null, null, null, pP3, pMe},
                {hRj, hP4, null, null, null, null, pP4, pRj}, 
                {hG1, hP5, null, null, null, null, pP5, pG1}, 
                {hK1, hP6, null, null, null, null, pP6, pK1}, 
                {hB1, hP7, null, null, null, null, pP7, pB1}};
        this.papan = newPapan;
    }

    public int kingX(Warna color) {
        if (color == PUTIH) {
            return pRj.getX();
        } else {
            return hRj.getX();
        }
    }

    public int kingY(Warna color) {
        if (color == PUTIH) {
            return pRj.getY();
        } else {
            return hRj.getY();
        }
    }

    public void setSelectedX(int x) {
        selectedX = x;
    }

    public void setSelectedY(int y) {
        selectedY = y;
    }

    public int selectedX() {
        return selectedX;
    }

    public int selectedY() {
        return selectedY;
    }

    public boolean getMoved(){
        return moved;
    }

    public void setMoved(boolean value){
        moved = value;
    }

    public boolean getEnd(){
        return end;
    }

    public void setEnd(boolean value){
        end = value;
    }

    public Bidak[][] board() {
        return this.papan;
    }

    public Warna turn() {
        return this.giliran;
    }
}
