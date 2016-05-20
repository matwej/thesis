package sk.fei.stuba.xpivarcim.test.languages;

public class UnsupportedLanguageException extends Exception {

    UnsupportedLanguageException(String lang) {
        super(lang + " is not supported. Supported Language keys are: C, JAVA");
    }

}
