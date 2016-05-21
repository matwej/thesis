package sk.fei.stuba.xpivarcim.test.core.factories;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
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
        prepareUnitFiles(workDir, language.getSettings().unitProtoDir + language.getUnitDirName());
        return new UnitEngine(testFiles, solution, language);
    }

    @Override
    protected String solutionFilesTargetDir(Language language) {
        return workDir+language.getUnitSolDir()+"/";
    }

    private void prepareUnitFiles(String targetDir, String sourceDir) throws IOException {
        final Path sourcePath = Paths.get(sourceDir);
        final Path targetPath = Paths.get(targetDir);
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(final Path dir,
                                                     final BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(targetPath.resolve(sourcePath
                        .relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                Files.copy(file,
                        targetPath.resolve(sourcePath.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
