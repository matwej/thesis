package sk.fei.stuba.xpivarcim.test.core.engines;

import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.producer.Result;

import java.util.concurrent.TimeoutException;

public interface Engine {
    void executeTests(String workDir, Result result) throws TimeoutException, CompilationException;
}
