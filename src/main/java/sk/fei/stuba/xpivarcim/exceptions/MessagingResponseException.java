package sk.fei.stuba.xpivarcim.exceptions;

public class MessagingResponseException extends Exception {

    public MessagingResponseException(int status) {
        super("Messaging operation failed with code: " + String.valueOf(status));
    }

}
