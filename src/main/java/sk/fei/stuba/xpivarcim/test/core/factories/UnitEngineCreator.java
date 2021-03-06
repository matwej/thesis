package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.core.engines.UnitEngine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;

public class UnitEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Assignment assignment, Solution solution, Language language) throws IOException {
        workDir += "/unittest";
        Utils.copyDirs(workDir, language.getSettings().getPrototypesDir() + language.getUnitDirName());
        return new UnitEngine(assignment.unitTestFiles(), solution, language);
    }

    @Override
    protected String solutionFilesTargetDir(Language language) {
        return workDir+language.getUnitSolDir()+"/";
    }

}
