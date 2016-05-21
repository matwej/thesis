package sk.fei.stuba.xpivarcim.producer;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private long solutionId;
    private int status;
    private String message;
    private Map<Integer, Boolean> tests;
    private boolean saTest;

    public Result() {}

    public Result(long solutionId) {
        this.solutionId = solutionId;
        tests = new HashMap<>();
    }

    public void appendMessage(String message) {
        if(this.message == null)
            this.message = "";
        this.message += message + "|";
    }

    public void addTest(Integer key, Boolean result) {
        tests.put(key, result);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSaTest(boolean saTest) {
        this.saTest = saTest;
    }

    public Map getTests() {
        return tests;
    }

    public long getSolutionId() {
        return solutionId;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSaTest() {
        return saTest;
    }
}
