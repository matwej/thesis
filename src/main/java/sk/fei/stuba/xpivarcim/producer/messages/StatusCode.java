package sk.fei.stuba.xpivarcim.producer.messages;

public enum StatusCode {
    OK(200), NOT_FOUND(404), NOT_MODIFIED(304), ERROR(900), UNEXPECTED_ERROR(500);
    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
