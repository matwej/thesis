package sk.fei.stuba.xpivarcim.test.core.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class UnitEngine implements Engine {

    private Solution solution;
    private Language language;
    private Set<TestFile> testFiles;

    public UnitEngine(Set<TestFile> testFiles, Solution solution, Language language) {
        this.language = language;
        this.solution = solution;
        this.testFiles = testFiles;
    }

    @Override
    public void executeTests(String workDir, Result result) throws CompilationException {
        try {
            language.createUnitTestFile(solution, testFiles);
            Utils.runCommands(workDir, prepareCommands(language));
            language.mapUnitTestResults(workDir, result);
        } catch (Exception e) {
            setTestsAsFailed(result);
        }
        if(language.isCompiled())
            checkForCompilationError(workDir);
    }

    private void checkForCompilationError(String workDir) throws CompilationException {
        File errors = new File(workDir + "/test_errors");
        String out = "";
        if(errors.exists()) {
            try {
                Scanner scanner = new Scanner(errors).useDelimiter("\\A");
                if(scanner.hasNext()) {
                   if(scanner.next().contains(language.compilationErrorString()))
                       throw new CompilationException();
                }
            } catch (IOException e) {
                // nevermind this
            }
        }
    }

    private void setTestsAsFailed(Result result) {
        for(TestFile testFile : testFiles) {
            result.addTest(testFile.getIndex(),false);
        }
    }

    private Queue<String> prepareCommands(Language language) {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommand("test"));
        return commands;
    }
}
