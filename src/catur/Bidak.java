package catur;

import sistem.Warna;
import sistem.Tipe;

/** Interface untuk Bidak
 * Implementasi oleh kelas bidak lain
 */
public interface Bidak {

    String imageString();

    Warna color();

    Tipe type();

    boolean makeValidMove(int a, int b);

    boolean hasMove();

    boolean canCapture(int a, int b);

    void setLocation(int x, int y);

}
