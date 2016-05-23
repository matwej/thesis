package sk.fei.stuba.xpivarcim.test.languages;

import sk.fei.stuba.xpivarcim.exceptions.UnsupportedLanguageException;
import sk.fei.stuba.xpivarcim.support.Settings;

public class LanguageContext {

    public static Language getLanguage(String languageCode, Settings settings) throws UnsupportedLanguageException {
        switch (languageCode) {
            case "JAVA":
                return new Java(settings);
            case "C":
                return new C(settings);
            default:
                throw new UnsupportedLanguageException(languageCode);
        }
    }

}
