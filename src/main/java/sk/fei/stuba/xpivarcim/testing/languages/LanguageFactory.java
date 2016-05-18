package sk.fei.stuba.xpivarcim.testing.languages;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.testing.support.UnsupportedLanguageException;

public class LanguageFactory {

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
