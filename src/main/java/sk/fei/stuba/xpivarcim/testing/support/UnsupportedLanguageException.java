package sk.fei.stuba.xpivarcim.testing.support;

public class UnsupportedLanguageException extends Exception {

    public UnsupportedLanguageException(String lang) {
        super(lang + " is not supported. Supported Language keys are: C, JAVA");
    }

}