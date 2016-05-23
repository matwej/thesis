package sk.fei.stuba.xpivarcim.test.core.engines;

import sk.fei.stuba.xpivarcim.producer.Result;

public interface Engine {
    void executeTests(String workDir, Result result);
}
