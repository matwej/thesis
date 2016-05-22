package sk.fei.stuba.xpivarcim.test.core.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.producer.Result;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface Engine {
    void executeTests(String workDir, Result result);
}
