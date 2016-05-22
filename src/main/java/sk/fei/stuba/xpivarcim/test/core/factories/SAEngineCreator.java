package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.core.engines.SAEngine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;
import java.util.Set;

public class SAEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Set<TestFile> testFiles, Solution solution, Language language) throws IOException {
        Utils.copyDirs(workDir, language.getSettings().unitProtoDir + language.getSADirName());
        return new SAEngine(language);
    }

    @Override
    protected String solutionFilesTargetDir(Language language) {
        return workDir+language.getSASolDir()+"/";
    }
}
