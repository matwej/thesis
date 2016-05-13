package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.testing.test.TestStrategy;

import java.io.IOException;
import java.util.Map;

public interface Engine {
    boolean executeTest(TestFile testFile, TestStrategy testStrategy) throws IOException;
    Solution getSolution();
    Settings getSettings();
    Map<String, String> getCommands();
}
