package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.support.Utils;
import sk.fei.stuba.xpivarcim.test.core.engines.Engine;
import sk.fei.stuba.xpivarcim.test.core.engines.UnitEngine;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class UnitEngineCreator extends EngineCreator {

    @Override
    protected Engine createEngine(Set<TestFile> testFiles, Solution solution, Language language) throws IOException {
        Utils.copyDirs(workDir, language.getSettings().unitProtoDir + language.getUnitDirName());
        return new UnitEngine(testFiles, solution, language);
    }

    @Override
    protected String solutionFilesTargetDir(Language language) {
        return workDir+language.getUnitSolDir()+"/";
    }

}
