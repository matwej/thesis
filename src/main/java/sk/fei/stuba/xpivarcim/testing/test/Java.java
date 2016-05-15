package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.Settings;

import java.util.HashMap;
import java.util.Map;

public class Java implements Language {

    private final Map<String, String> commandsMap = new HashMap<>();
    private Settings settings;

    public Java(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","javac *.java");
        commandsMap.put("run", "java Main ");
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
    public Settings getSettings() {
        return settings;
    }
}
