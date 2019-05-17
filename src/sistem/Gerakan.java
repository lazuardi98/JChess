package sistem;

import catur.Bidak;

/** Pergerakan
 * implementasi pada bidak dan tipe gerakan
 */
public interface Gerakan
{
    boolean isDouble();

    Gerakan undoMove();

    Bidak movedPiece();
}
