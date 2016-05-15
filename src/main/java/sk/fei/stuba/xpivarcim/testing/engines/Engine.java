package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.test.Language;

import java.io.IOException;
import java.util.Set;

public interface Engine {
    void executeTests(Result result, Set<TestFile> testFiles, Language lang) throws IOException;
}
