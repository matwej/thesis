package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.Settings;

import java.util.Map;

public interface Language {
    Map<String, String> getCommands();
    String getUnitDirName();
    Settings getSettings();
}
