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
    public GUI(String title, Permainan permainan) {
        super(title, true);
        _permainan = permainan;
        addLabel("Permainan Dimulai, Giliran PUTIH.", "turn", new Layout("y", 5, "x", 0));
        addMenuButton("Menu->Quit", "quit");
        addMenuButton("Menu->New Game", "newGame");
        addMenuButton("Options->Undo", "undo");
        _display = new Display(permainan);
        add(_display, new Layout("y", 2, "width", 2));
        _display.setMouseHandler("press", this, "mousePressed");
        display(true);
    }

    public void newGame(String dummy) {
        _permainan.newPermainan();
        repaint(true);
    }

    public void quit(String dummy) {
        _permainan.quit();
    }

    public void undo(String dummy) {
        _permainan.undoMove();
        _permainan.setSelectedX(-1);
        _permainan.setSelectedY(-1);
        repaint(true);
    }

    public synchronized void mousePressed(MouseEvent event) {
        if (_permainan.selectedX() == -1) {
            int pressedX = (event.getX() - MARGIN) / KOTAK;
            int pressedY = (event.getY() - MARGIN) / KOTAK;
            Bidak selected = _permainan.get(pressedX, pressedY);
            if (selected != null && selected.color() == _permainan.turn()) {
                _permainan.setSelectedX(pressedX);
                _permainan.setSelectedY(pressedY);
                _display.repaint();
            }
        } else {
            int releasedX = (event.getX() - MARGIN) / KOTAK;
            int releasedY = (event.getY() - MARGIN) / KOTAK;
            Bidak selected = _permainan.get(_permainan.selectedX(), _permainan.selectedY());
            _permainan.setSelectedX(-1);
            _permainan.setSelectedY(-1);
            repaint(selected.makeValidMove(releasedX, releasedY));
        }
    }

    public void repaint(boolean validMove) {
        String label;
        if (validMove) {
            if (_permainan.noMoves()) {
                if (_permainan.inCheck(_permainan.turn())) {
                    label = "SKAT MAT, " + _permainan.turn().opposite().string()
                        + " Menang.";
                } else {
                    label = "REMIS, permainan seri.";
                }
            } else {
                label = "Giliran " + _permainan.turn().string() + ".";
            }
        } else {
            label = "Gerakan Salah. " + "Giliran " + _permainan.turn().string() + ".";
        }
        setLabel("turn", label);
        _display.repaint();
    }

    private final Display _display;

    private final Permainan _permainan;
}
