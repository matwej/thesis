package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.CodeFile;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RunTest implements TestStrategy {

    private Queue<String> commands;

    public RunTest() {
        commands = new LinkedList<>();
    }

    @Override
    public boolean executeTest(TestFile testFile, Engine engine) throws IOException {
        createFiles(engine.getSolution(), engine.getSettings());
        prepareCommands(testFile, engine);
        String output = executeCommands(commands);
        return output.equals(testFile.getOutput());
    }

    private void prepareCommands(TestFile testFile, Engine engine) {
        commands.add("cd " + engine.getSettings().opDir + engine.getSolution().getId());
        commands.add(engine.getCommands().get("compile"));
        commands.add(engine.getCommands().get("run") + testFile.getSafeInput());
    }

    private void createFiles(Solution solution, Settings settings) throws IOException {
        for (CodeFile file : solution.getSourceFiles()) {
            file.create(settings.opDir + solution.getId() + "/");
        }
    }

    private String executeCommands(Queue<String> commands) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("/bin/sh");
        Process p = builder.start();
        //get stdin of shell
        BufferedWriter p_stdin =
                new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        // execute the desired commands
        for (String command : commands) {
            //single execution
            p_stdin.write(command);
            p_stdin.newLine();
            p_stdin.flush();
        }
        // finally close the shell by execution exit command
        p_stdin.write("exit");
        p_stdin.newLine();
        p_stdin.flush();
        Scanner s = new Scanner(p.getInputStream()).useDelimiter("\\A");
        String output = "" + s.next();
        s.close();
        return output;
    }
}
