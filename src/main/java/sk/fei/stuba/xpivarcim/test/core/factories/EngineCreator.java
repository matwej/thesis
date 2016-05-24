package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.db.entities.assignment.SourceFile;
import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class EngineCreator {

    protected String workDir;

    public void execTests(Assignment assignment, Solution solution, Language language, Result result) throws IOException, TimeoutException, CompilationException {
        workDir = language.getSettings().opDir + solution.getId();
        Engine engine = createEngine(assignment, solution, language);
        solution.createFiles(solutionFilesTargetDir(language));
        createSourceFiles(assignment, workDir);
        language.calibrateCommands(solution);
        engine.executeTests(workDir, result);
    }

    protected abstract Engine createEngine(Assignment assignment, Solution solution, Language language) throws IOException;

    protected abstract String solutionFilesTargetDir(Language language);

    private void createSourceFiles(Assignment assignment, String workDir) throws IOException {
        if (assignment.getSourceFiles() != null) {
            for (SourceFile file : assignment.getSourceFiles()) {
                Utils.createFile(workDir + "/", file.getName(), file.getContent());
            }
        }
    }
}
