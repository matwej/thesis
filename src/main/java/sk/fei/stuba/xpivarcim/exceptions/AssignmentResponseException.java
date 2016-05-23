package sk.fei.stuba.xpivarcim.exceptions;

public class AssignmentResponseException extends Exception {

    public AssignmentResponseException(int status) {
        super("Assignemnt operation failed with code: " + String.valueOf(status));
    }

}
