package sk.fei.stuba.xpivarcim.testing.languages;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface Language {
    void createUnitTestFile(Solution solution, Set<TestFile> testFiles) throws IOException;
    void mapUnitTestResults(Solution solution, Result result) throws IOException, ParserConfigurationException, SAXException;
    Map<String, String> getCommands();
    String getUnitDirName();
    Settings getSettings();
    String getUnitSolDir();
}
