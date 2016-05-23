package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.db.entities.assignment.SourceFile;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.core.engines.RunEngine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class RunEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Assignment assignment, Solution solution, Language language) throws IOException {
        workDir += "/runtest";
        new File(workDir).mkdir();
        return new RunEngine(assignment.runTestFiles(), language);
    }

    @Override
    protected String solutionFilesTargetDir(Language language) {
        return workDir+"/";
    }

}
