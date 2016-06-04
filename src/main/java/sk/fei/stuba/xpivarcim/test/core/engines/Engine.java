package sk.fei.stuba.xpivarcim.test.core.engines;

import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.producer.messages.Result;

public interface Engine {
    void executeTests(String workDir, Result result) throws CompilationException;
}
