package sk.fei.stuba.xpivarcim.testing;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.testing.engines.CEngine;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;
import sk.fei.stuba.xpivarcim.testing.engines.JavaEngine;

public class EngineFactory {

    public static Engine getEngine(String lang, Solution solution, Settings settings) throws UnsupportedLanguageException {
        if (lang.equals("JAVA")) {
            return new JavaEngine(solution, settings);
        } else if (lang.equals("C")) {
            return new CEngine(solution, settings);
        } else {
            throw new UnsupportedLanguageException(lang);
        }
    }

}
