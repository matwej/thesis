package sk.fei.stuba.xpivarcim.exceptions;

public class UnsupportedLanguageException extends Exception {

    public UnsupportedLanguageException(String lang) {
        super(lang + " is not supported. Supported Language keys are: c, JAVA");
    }

}
