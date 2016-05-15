package sk.fei.stuba.xpivarcim.testing.languages;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Java implements Language {

    private final Map<String, String> commandsMap = new HashMap<>();
    private Settings settings;

    public Java(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","javac *.java");
        commandsMap.put("run", "java Main ");
        commandsMap.put("test_prep", ""); // no preparation needed
        commandsMap.put("test","gradle test");
    }

    @Override
    public void createUnitTestFile(Solution solution, Set<TestFile> testFiles) throws IOException {
        FileOutputStream ostream = new FileOutputStream(settings.opDir + solution.getId() + "/src/test/java/MainTest.java");
        ostream.write("import static org.junit.Assert.*;\n".getBytes("UTF-8"));
        ostream.write("import org.junit.*;\n\n".getBytes("UTF-8"));
        ostream.write("public class MainTest {\n".getBytes("UTF-8"));
        for(TestFile testFile : testFiles) {
            ostream.write(("@Test(timeout="+String.valueOf(settings.unitTimeout)+")\n").getBytes("UTF-8"));
            ostream.write(("public void test" + String.valueOf(testFile.getIndex()) + "() throws Exception {\n").getBytes("UTF-8"));
            ostream.write(testFile.getContent().getBytes("UTF-8"));
            ostream.write("\n}".getBytes("UTF-8"));
        }
        ostream.write("\n}".getBytes("UTF-8"));
        ostream.close();
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

    @Override
    public String getUnitSolDir() {
        return "/src/main/java";
    }
}
