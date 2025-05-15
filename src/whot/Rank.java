package whot;

public enum Rank {
    ONE(1, true),
    TWO(2, true),
    THREE(3, false),
    FOUR(4, false),
    FIVE(5, true),
    SEVEN(7, false),
    EIGHT(8, true),
    TEN(10, false),
    ELEVEN(11, false),
    TWELVE(12, false),
    THIRTEEN(13, false),
    FOURTEEN(14, true),
    TWENTY(20, true);

    private final int num;
    private final boolean special;

    Rank(int num, boolean isSpecial) {
        this.num = num;
        this.special = isSpecial;
    }

    public int getNum () {
        return num;
    }

    public boolean isSpecial() {
        return special;
    }
}
