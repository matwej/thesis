package sk.fei.stuba.xpivarcim.testing.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public interface Engine {
    void executeTests(Result result, Set<TestFile> testFiles, Language lang) throws IOException, ParserConfigurationException, SAXException;
}
