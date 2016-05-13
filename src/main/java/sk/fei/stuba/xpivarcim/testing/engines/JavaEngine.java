package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.testing.test.TestStrategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaEngine implements Engine {

    private Solution solution;
    private Settings settings;

    private final Map<String, String> commandsMap = new HashMap<>();

    public JavaEngine(Solution solution, Settings settings) {
        this.solution = solution;
        this.settings = settings;

        commandsMap.put("compile","javac *.java");
        commandsMap.put("run", "java Main ");
    }

    @Override
    public boolean executeTest(TestFile testFile, TestStrategy testStrategy) throws IOException {
        return testStrategy.executeTest(testFile, this);
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Map<String, String> getCommands() {
        return commandsMap;
    }

    @Override
    public String getUnitDirName() {
        return settings.javaUnitDir;
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

}
