package sistem;

public enum Tipe
{
    GAJAH, RAJA, KUDA, PION, MENTERI, BENTENG;

    public String abbrev() {
        switch (this) {
        case GAJAH:
            return "gajah";
        case RAJA:
            return "raja";
        case KUDA:
            return "kuda";
        case PION:
            return "pion";
        case MENTERI:
            return "menteri";
        case BENTENG:
            return "benteng";
        default:
            return "-";
        }
    }
}
