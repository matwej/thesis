package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

class RunEngine implements Engine {

    private Language language;

    RunEngine(Language language) {
        this.language = language;
    }

    @Override
    public void executeTests(String workDir, Result result, Set<TestFile> testFiles) throws IOException, ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (TestFile f : testFiles) {
            Queue<String> commands = prepareCommands(f, language);
            try {
                int timeout = f.getTimeout() == 0 ? language.getSettings().runTimeout : f.getTimeout();
                String output = TestUtils.runTimeoutableCommands(workDir, commands, timeout, service);
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
