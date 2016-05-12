package sk.fei.stuba.xpivarcim.testing;

public class EngineFactory {

    public static Engine getEngine(String lang) throws UnsupportedLanguageException {
        if(lang.equals("JAVA")) {
            return new JavaEngine();
        } else if(lang.equals("C")) {
            return new CEngine();
        } else {
            throw new UnsupportedLanguageException(lang);
        }
    }

}
