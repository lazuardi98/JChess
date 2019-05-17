package sistem;


public enum Warna
{
    HITAM, PUTIH;

    public String abbrev() {
        switch (this) {
        case HITAM:
            return "hitam";
        case PUTIH:
            return "putih";
        default:
            return "-";
        }
    }

    public String string() {
        switch (this) {
        case HITAM:
            return "HITAM";
        case PUTIH:
            return "PUTIH";
        default:
            return "-";
        }
    }

    public Warna opposite() {
        if (this == HITAM) {
            return PUTIH;
        } else {
            return HITAM;
        }
    }

    public int direction() {
        if (this == PUTIH) {
            return -1;
        } else {
            return 1;
        }
    }
}
