package sk.fei.stuba.xpivarcim.testing.support;

public class TestTimedOutException extends Exception {
    TestTimedOutException(int time) {
        super("Test timed out after - " + String.valueOf(time));
    }
}
