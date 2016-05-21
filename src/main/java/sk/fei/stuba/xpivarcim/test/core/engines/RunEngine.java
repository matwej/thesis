package sk.fei.stuba.xpivarcim.test.core.engines;

import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class RunEngine implements Engine {

    private Language language;
    private Set<TestFile> testFiles;

    public RunEngine(Set<TestFile> testFiles, Language language) {
        this.language = language;
        this.testFiles = testFiles;
    }

    @Override
    public void executeTests(String workDir, Result result) throws IOException, ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (TestFile f : testFiles) {
            Queue<String> commands = prepareCommands(f, language);
            try {
                int timeout = f.getTimeout() == 0 ? language.getSettings().runTimeout : f.getTimeout();
                String output = Utils.runTimeoutableCommands(workDir, commands, timeout, service);
                result.addTest(f.getIndex(),f.getOutput().equals(output));
            } catch (TimeoutException e) {
                result.addTest(f.getIndex(),false);
            }
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
