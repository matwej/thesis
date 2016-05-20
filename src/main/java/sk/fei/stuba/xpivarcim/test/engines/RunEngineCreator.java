package sk.fei.stuba.xpivarcim.test.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;

public class RunEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Solution solution, Language language) throws IOException {
        solution.createFiles(workDir+"/");
        return new RunEngine(language);
    }

}
