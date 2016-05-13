package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;

import java.io.IOException;

public interface TestStrategy {
    boolean executeTest(TestFile testFile, Engine engine) throws IOException;
}
