package sk.fei.stuba.xpivarcim.test.languages;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.support.Settings;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface Language {
    Map<String, String> commandsMap = new HashMap<>();

    void createUnitTestFile(String workDir, Solution solution, Set<TestFile> testFiles) throws IOException;
    void mapUnitTestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException;

    void mapSATestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException;

    String getCommand(String key);
    void calibrateCommands(Solution solution);

    String getUnitDirName();
    String getUnitSolDir();

    String getSASolDir();
    String getSADirName();

    Settings getSettings();

    boolean isCompiled();
    String compilationErrorString();
}
