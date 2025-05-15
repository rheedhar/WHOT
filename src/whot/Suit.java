package whot;

public enum Suit {
    CIRCLE("Circle"),
    TRIANGLE("Triangle"),
    CROSS("Cross"),
    SQUARE("Square"),
    STAR("Star"),
    WHOT("Whot");

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
