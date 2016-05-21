package sk.fei.stuba.xpivarcim.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.*;

public class Utils {

    public static String runTimeoutableCommands(final String workDir, final Queue<String> commands, int timeout, ExecutorService service)
            throws ExecutionException, InterruptedException, TimeoutException {
        Callable<String> run = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return runCommands(workDir, commands);
            }
        };
        RunnableFuture<String> future = new FutureTask<>(run);
        service.execute(future);
        String output;
        try {
            output = future.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new TimeoutException();
        }
        return output;
    }

    public static String runCommands(String workDir, Queue<String> commands) throws IOException, InterruptedException {
        createScriptFile(workDir + "/runner.sh", commands);
        String output = "";
        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "runner.sh");
        builder.directory(new File(workDir));
        Process p = builder.start();
        p.waitFor();
        Scanner s = new Scanner(p.getInputStream()).useDelimiter("\\A");
        if (s.hasNext())
            output = s.next();
        s.close();
        return output;
    }

    public static void createFile(String targetDir, String name, byte[] content) throws IOException {
        File f = new File(targetDir + name);
        if(!f.exists()) {
            FileOutputStream outputStream = new FileOutputStream(f);
            outputStream.write(content);
            outputStream.close();
        }
    }

    private static void createScriptFile(String name, Queue<String> commands) throws IOException {
        File tmp = new File(name);
        FileWriter writer = new FileWriter(tmp);
        writer.write("#!/bin/sh\n");
        for (String command : commands) {
            writer.write(command + "\n");
        }
        writer.close();
    }

}
