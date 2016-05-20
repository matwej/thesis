package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestTimedOutException;
import sk.fei.stuba.xpivarcim.testing.support.TestUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RunEngine implements Engine {

    private Solution solution;
    private Language language;

    RunEngine(Solution solution, Language language) {
        this.language = language;
        this.solution = solution;
    }

    @Override
    public void executeTests(String workDir, Result result, Set<TestFile> testFiles) throws IOException, ExecutionException, InterruptedException, TestTimedOutException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (TestFile f : testFiles) {
            Queue<String> commands = prepareCommands(f, language);
            String output = TestUtils.runTimeoutableCommands(workDir, commands, language.getSettings().runTimeout, service);
            result.addTest(f.getIndex(),f.getOutput().equals(output));
        }
        service.shutdown();
    }

    private Queue<String> prepareCommands(TestFile testFile, Language language) {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommand("compile"));
        commands.add(language.getCommand("run") + testFile.safeInput());
        return commands;
    }

}
