package grafik;

import catur.Bidak;
import sistem.Permainan;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;

/** Melukis perubahan
 * pada GUI
 */
public class Display extends Pad {

    public static final double MULTIPLIER = 0.7; // 0.7

    public static final int PAPAN = (int) Math.round(700 * MULTIPLIER); // 700

    public static final int KOTAK = (int) Math.round(74 * MULTIPLIER); // 74

    public static final int MARGIN = (int) Math.round(53 * MULTIPLIER); // 53

    public Display(Permainan permainan) {
        this.permainan = permainan;
        setPreferredSize(PAPAN, PAPAN);
    }

    private Image getImage(String name, String type) {
        InputStream in = getClass().getResourceAsStream("/img/" + type + "/" + name);
        try {
            return ImageIO.read(in);
        } catch (IOException ex) {
            return null;
        }
    }

    private Image getPieceImage(Bidak bidak) {
        return getImage(bidak.imageString() + ".png", "bidak");
    }

    private void paintPiece(Graphics2D g, Bidak bidak, int x, int y) {
        if (bidak != null) {
            g.drawImage(getPieceImage(bidak), x, y, KOTAK, KOTAK, null);
        }
    }

    public synchronized void paintComponent(Graphics2D g) {
        Rectangle b = g.getClipBounds();
        g.fillRect(0, 0, b.width, b.height);
        g.drawImage(getImage("papan.jpg", "papan"), 0, 0, PAPAN, PAPAN, null);
        if (this.permainan.inCheck(this.permainan.turn())) {
            g.drawImage(getImage("skak.png", "papan"),
                KOTAK * this.permainan.kingX(this.permainan.turn()) + MARGIN,
                KOTAK * this.permainan.kingY(this.permainan.turn()) + MARGIN, KOTAK, KOTAK, null);
        }
        if (this.permainan.selectedX() != -1) {
            g.drawImage(getImage("pilihan.png", "papan"),
                KOTAK * this.permainan.selectedX() + MARGIN,
                KOTAK * this.permainan.selectedY() + MARGIN, KOTAK, KOTAK, null);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                paintPiece(g, this.permainan.get(i, j),
                    KOTAK * i + MARGIN, KOTAK * j + MARGIN);
            }
        }
    }

    private final Permainan permainan;
}
