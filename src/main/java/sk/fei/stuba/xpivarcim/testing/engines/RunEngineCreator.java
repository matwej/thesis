package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.consumer.CodeFile;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.support.Utils;

import java.io.IOException;

public class RunEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Solution solution, Language language) throws IOException {
        createSolutionFiles(solution);
        return new RunEngine(language);
    }

    private void createSolutionFiles(Solution solution) throws IOException {
        for (CodeFile file : solution.getSourceFiles()) {
            Utils.createFile(workDir+"/",file.getName(), file.getContent().getBytes("UTF-8"));
        }
    }
}
