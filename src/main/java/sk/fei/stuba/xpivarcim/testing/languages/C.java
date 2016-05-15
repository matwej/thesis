package sk.fei.stuba.xpivarcim.testing.languages;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class C implements Language {

    private Settings settings;
    private final Map<String, String> commandsMap = new HashMap<>();

    public C(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","g++ *.* -o languages");
        commandsMap.put("run", "./languages ");
    }

    @Override
    public void createUnitTestFile(Solution solution, Set<TestFile> testFiles) {

    }

    @Override
    public Map<String, String> getCommands() {
        return commandsMap;
    }

    @Override
    public String getUnitDirName() {
        return settings.cUnitDir;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public String getUnitSolDir() {
        return "/";
    }
}
