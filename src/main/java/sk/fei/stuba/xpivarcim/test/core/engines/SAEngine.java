package sk.fei.stuba.xpivarcim.test.core.engines;

import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.util.LinkedList;
import java.util.Queue;

public class SAEngine implements Engine {

    private Language language;

    public SAEngine(Language language) {
        this.language = language;
    }

    @Override
    public void executeTests(String workDir, Result result) {
        try {
            Utils.runCommands(workDir, prepareCommands());
            language.mapSATestResults(workDir, result);
        } catch (Exception e) {
            result.setSaTest(false);
        }
    }

    private Queue<String> prepareCommands() {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommand("sa"));
        return commands;
    }

}
