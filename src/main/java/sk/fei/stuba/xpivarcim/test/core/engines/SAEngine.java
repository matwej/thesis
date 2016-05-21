package sk.fei.stuba.xpivarcim.test.core.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class SAEngine implements Engine {

    private Language language;

    public SAEngine(Language language) {
        this.language = language;

    }

    @Override
    public void executeTests(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException, ExecutionException, InterruptedException {
        Utils.runCommands(workDir, prepareCommands());
        language.mapSATestResults(workDir, result);
    }

    private Queue<String> prepareCommands() {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommand("sa"));
        return commands;
    }

}
