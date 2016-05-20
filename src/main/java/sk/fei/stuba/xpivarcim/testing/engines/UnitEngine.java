package sk.fei.stuba.xpivarcim.testing.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.support.Utils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class UnitEngine implements Engine {

    private Solution solution;
    private Language language;

    UnitEngine(Solution solution, Language language) {
        this.language = language;
        this.solution = solution;
    }

    @Override
    public void executeTests(String workDir, Result result, Set<TestFile> testFiles) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        language.createUnitTestFile(solution, testFiles);
        Utils.runCommands(workDir, prepareCommands(language));
        language.mapUnitTestResults(workDir, result);
    }

    private Queue<String> prepareCommands(Language language) {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommand("test_prep"));
        commands.add(language.getCommand("test"));
        return commands;
    }
}
