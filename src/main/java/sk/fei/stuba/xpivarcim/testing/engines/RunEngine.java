package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.CodeFile;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class RunEngine implements Engine {

    private Solution solution;

    public RunEngine(Solution solution) {
        this.solution = solution;
    }

    @Override
    public void executeTests(Result result, Set<TestFile> testFiles, Language lang) throws IOException {
        createFiles(lang);
        for (TestFile f : testFiles) {
            String output = TestUtils.executeCommands(lang.getSettings().opDir + solution.getId(), prepareCommands(f, lang));
            result.addTest(f.getIndex(),output.equals(f.getOutput()));
        }
    }

    private Queue<String> prepareCommands(TestFile testFile, Language language) {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommands().get("compile"));
        commands.add(language.getCommands().get("run") + testFile.safeInput());
        return commands;
    }

    private void createFiles(Language language) throws IOException {
        for (CodeFile file : solution.getSourceFiles()) {
            file.create(language.getSettings().opDir + solution.getId() + "/");
        }
    }
}
