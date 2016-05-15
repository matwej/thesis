package sk.fei.stuba.xpivarcim.testing.engines;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.test.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

// TODO dokoncit
public class UnitEngine implements Engine {

    private Solution solution;

    public UnitEngine(Solution solution) {
        this.solution = solution;
    }

    @Override
    public void executeTests(Result result, Set<TestFile> testFiles, Language lang) throws IOException {
        String operationDir = engine.getSettings().opDir + engine.getSolution().getId();
        prepareUnitFiles(operationDir, engine.getSettings().unitProtoDir + engine.getUnitDirName());
        createTestFiles(engine);
        createSolutionFiles(engine);
        prepareCommands();
        TestUtils.executeCommands(commands);
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
