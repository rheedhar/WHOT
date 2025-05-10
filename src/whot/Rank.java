package whot;

public enum Rank {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SEVEN(7),
    EIGHT(8),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    THIRTEEN(13),
    FOURTEEN(14),
    TWENTY(20);

    private final int num;

    Rank(int num) {
        this.num = num;
    }

    public int getNum () {
        return num;
    }
}
