package sk.fei.stuba.xpivarcim.producer;

public class AssignmentResponseException extends Exception {

    private int status;

    public AssignmentResponseException(int status) {
        this.status = status;
    }

}
