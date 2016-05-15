package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.Settings;

import java.util.HashMap;
import java.util.Map;

public class C implements Language {

    private Settings settings;
    private final Map<String, String> commandsMap = new HashMap<>();

    public C(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","g++ *.* -o test");
        commandsMap.put("run", "./test ");
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
}
