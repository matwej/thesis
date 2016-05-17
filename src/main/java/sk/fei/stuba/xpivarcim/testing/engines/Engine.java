package sk.fei.stuba.xpivarcim.testing.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestTimedOutException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Engine {
    void executeTests(Result result, Set<TestFile> testFiles, Language lang) throws IOException, ParserConfigurationException, SAXException, ExecutionException, InterruptedException, TestTimedOutException;
}
