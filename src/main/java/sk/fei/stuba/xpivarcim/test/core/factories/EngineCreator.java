package sk.fei.stuba.xpivarcim.test.core.factories;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public abstract class EngineCreator {

    protected String workDir;

    public void execTests(Set<TestFile> testFiles, Solution solution, Language language, Result result) throws IOException, ParserConfigurationException, SAXException, ExecutionException, InterruptedException {
        workDir = language.getSettings().opDir + solution.getId();
        solution.createFiles(solutionFilesTargetDir(language));
        Engine engine = createEngine(testFiles, solution, language);
        engine.executeTests(workDir, result);
    }

    protected abstract Engine createEngine(Set<TestFile> testFiles, Solution solution, Language language) throws IOException;

    protected abstract String solutionFilesTargetDir(Language language);
}
