package sk.fei.stuba.xpivarcim.testing.support;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Queue;
import java.util.Scanner;

public class TestUtils {

    public static String executeCommands(Queue<String> commands) throws IOException {
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
