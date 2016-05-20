package sk.fei.stuba.xpivarcim.testing.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

interface Engine {
    void executeTests(String workDir, Result result, Set<TestFile> testFiles) throws IOException, ParserConfigurationException, SAXException, ExecutionException, InterruptedException;
}
