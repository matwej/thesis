package sk.fei.stuba.xpivarcim.testing.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public abstract class EngineCreator {

    String workDir;

    public void execTests(Solution solution, Result result, Set<TestFile> testFiles, Language language) throws IOException, ParserConfigurationException, SAXException, ExecutionException, InterruptedException {
        workDir = language.getSettings().opDir + solution.getId();
        Engine engine = createEngine(solution, language);
        engine.executeTests(workDir, result, testFiles);
    }

    protected abstract Engine createEngine(Solution solution, Language language) throws IOException;

}
