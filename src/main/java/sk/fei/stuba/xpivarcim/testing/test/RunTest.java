package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.CodeFile;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class RunTest implements TestStrategy {

    private Queue<String> commands;

    public RunTest() {
        commands = new LinkedList<>();
    }

    @Override
    public boolean executeTest(TestFile testFile, Engine engine) throws IOException {
        createFiles(engine.getSolution(), engine.getSettings());
        prepareCommands(testFile, engine);
        String output = TestUtils.executeCommands(commands);
        return output.equals(testFile.getOutput());
    }

    private void prepareCommands(TestFile testFile, Engine engine) {
        commands.add("cd " + engine.getSettings().opDir + engine.getSolution().getId());
        commands.add(engine.getCommands().get("compile"));
        commands.add(engine.getCommands().get("run") + testFile.safeInput());
    }

    private void createFiles(Solution solution, Settings settings) throws IOException {
        for (CodeFile file : solution.getSourceFiles()) {
            file.create(settings.opDir + solution.getId() + "/");
        }
    }
}
