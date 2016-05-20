package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.CodeFile;
import sk.fei.stuba.xpivarcim.testing.languages.Language;

import java.io.IOException;

public class UnitEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Solution solution, Language language) throws IOException {
        createSolutionFiles(solution, language);
        return new UnitEngine(solution, language);
    }

    private void createSolutionFiles(Solution solution, Language language) throws IOException {
        for (CodeFile file : solution.getSourceFiles()) {
            file.create(workDir + language.getUnitSolDir() + "/");
        }
    }
}
