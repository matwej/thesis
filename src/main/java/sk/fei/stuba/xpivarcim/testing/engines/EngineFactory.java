package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;
import sk.fei.stuba.xpivarcim.testing.engines.RunEngine;
import sk.fei.stuba.xpivarcim.testing.engines.UnitEngine;
import sk.fei.stuba.xpivarcim.testing.support.UnsupportedEngineType;

public class EngineFactory {

    public enum EngineType {RUN, UNIT}

    public static Engine getEngine(EngineType type, Solution solution) throws UnsupportedEngineType {
        switch (type) {
            case RUN:
                return new RunEngine(solution);
            case UNIT:
                return new UnitEngine(solution);
            default:
                throw new UnsupportedEngineType();
        }
    }

}
