package grafik;

import catur.Bidak;
import sistem.Permainan;

import java.awt.event.MouseEvent;

import static grafik.Display.KOTAK;
import static grafik.Display.MARGIN;


/** Inilisiasi GUI
 * pada awal permainan
 */
public class GUI extends Engine
{
    private final Display display;
    private final Permainan permainan;

    public GUI(String title, Permainan permainan) {
        super(title, true);
        this.permainan = permainan;
        addLabel("Permainan Dimulai, Giliran PUTIH.", "turn", new Layout("y", 5, "x", 0));
        addMenuButton("Menu->Quit", "quit");
        addMenuButton("Menu->New Game", "newGame");
        addMenuButton("Options->Undo", "undo");
        this.display = new Display(permainan);
        add(this.display, new Layout("y", 2, "width", 2));
        this.display.setMouseHandler("press", this, "mousePressed");
        display(true);
    }

    public void newGame(String dummy) {
        this.permainan.newPermainan();
        repaint(true);
    }

    public void quit(String dummy) {
        this.permainan.quit();
    }

    public void undo(String dummy) {
        this.permainan.undoMove();
        this.permainan.setSelectedX(-1);
        this.permainan.setSelectedY(-1);
        this.permainan.setEnd(false);
        repaint(true);
    }

    public synchronized void mousePressed(MouseEvent event) {
        if (this.permainan.selectedX() == -1) {
            int pressedX = (event.getX() - MARGIN) / KOTAK;
            int pressedY = (event.getY() - MARGIN) / KOTAK;
            Bidak selected = this.permainan.get(pressedX, pressedY);
            if (selected != null && selected.color() == this.permainan.turn()) {
                this.permainan.setSelectedX(pressedX);
                this.permainan.setSelectedY(pressedY);
                this.permainan.setMoved(false);
                this.permainan.setPossibleGerakan(selected);
                this.display.repaint();
            }
        } else {
            int releasedX = (event.getX() - MARGIN) / KOTAK;
            int releasedY = (event.getY() - MARGIN) / KOTAK;
            Bidak selected = this.permainan.get(this.permainan.selectedX(), this.permainan.selectedY());
            this.permainan.setSelectedX(-1);
            this.permainan.setSelectedY(-1);
            this.permainan.setMoved(true);
            repaint(selected.makeValidMove(releasedX, releasedY));
        }
    }

    public void repaint(boolean validMove) {
        String label;
        if (validMove) {
            if (this.permainan.noMoves()) {
                this.permainan.setEnd(true);
                if (this.permainan.inCheck(this.permainan.turn())) {
                    label = "SKAT MAT, " + this.permainan.turn().opposite().string() + " Menang.";
                }
                else {
                    label = "REMIS, permainan seri.";
                }
            }
            else {
                if (this.permainan.inCheck(this.permainan.turn())){
                    label = "SKAK. Giliran " + this.permainan.turn().string() + ".";
                }
                else {
                    label = "Giliran " + this.permainan.turn().string() + ".";
                }
            }
        }
        else {
            if (this.permainan.getEnd()){
                if (this.permainan.inCheck(this.permainan.turn())) {
                    label = "SKAT MAT, " + this.permainan.turn().opposite().string() + " Menang.";
                }
                else {
                    label = "REMIS, permainan seri.";
                }
            }
            else {
                if (this.permainan.inCheck(this.permainan.turn())){
                    label = "SKAK. Gerakan Salah. " + "Giliran " + this.permainan.turn().string() + ".";
                }
                else {
                    label = "Gerakan Salah. " + "Giliran " + this.permainan.turn().string() + ".";
                }
            }
        }
        setLabel("turn", label);
        this.display.repaint();
    }
}
