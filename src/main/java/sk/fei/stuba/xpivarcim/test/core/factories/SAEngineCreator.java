package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.core.engines.SAEngine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;

public class SAEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Assignment assignment, Solution solution, Language language) throws IOException {
        Utils.copyDirs(workDir, language.getSettings().unitProtoDir + language.getSADirName());
        return new SAEngine(language);
    }

    @Override
    protected String solutionFilesTargetDir(Language language) {
        return workDir+language.getSASolDir()+"/";
    }
}
