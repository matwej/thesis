package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public abstract class EngineCreator {

    protected String workDir;

    public void execTests(Set<TestFile> testFiles, Solution solution, Language language, Result result) throws IOException, TimeoutException, CompilationException {
        workDir = language.getSettings().opDir + solution.getId();
        Engine engine = createEngine(testFiles, solution, language);
        solution.createFiles(solutionFilesTargetDir(language));
        engine.executeTests(workDir, result);
    }

    protected abstract Engine createEngine(Set<TestFile> testFiles, Solution solution, Language language) throws IOException;

    protected abstract String solutionFilesTargetDir(Language language);
}
