package sk.fei.stuba.xpivarcim.testing.languages;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface Language {
    void createUnitTestFile(Solution solution, Set<TestFile> testFiles) throws IOException;
    Map<String, String> getCommands();
    String getUnitDirName();
    Settings getSettings();
    String getUnitSolDir();
}
