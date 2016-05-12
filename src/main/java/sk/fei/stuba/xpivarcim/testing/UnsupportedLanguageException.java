package sk.fei.stuba.xpivarcim.testing;

public class UnsupportedLanguageException extends Exception {

    public UnsupportedLanguageException(String lang) {
        super(lang + " is not supported.");
    }

}
