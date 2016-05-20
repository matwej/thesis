package sk.fei.stuba.xpivarcim.testing.languages;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface Language {
    Map<String, String> commandsMap = new HashMap<>();

    void createUnitTestFile(Solution solution, Set<TestFile> testFiles) throws IOException;
    void mapUnitTestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException;
    String getCommand(String key);
    String getUnitDirName();
    String getUnitSolDir();
    Settings getSettings();
}
