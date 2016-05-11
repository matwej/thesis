package sk.fei.stuba.xpivarcim.producer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {

    private static final long serialVersionUID = 9L;

    private long solutionId;
    private int status;
    private String message;
    private Map tests;

    public Result(long solutionId) {
        this.solutionId = solutionId;
        tests = new HashMap<Integer, Boolean>();
    }

    public void appendMessage(String message) {
        if(this.message == null)
            this.message = "";
        this.message += "|" + message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
