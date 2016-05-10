package sk.fei.stuba.xpivarcim.producer;

public enum StatusCode {
    OK(200), NOT_FOUND(404), NOT_MODIFIED(304);
    private final int value;

    private StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
