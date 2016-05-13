package sk.fei.stuba.xpivarcim.testing.test;

import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.Queue;

public class UnitTest implements TestStrategy {

    private Queue<String> commands;

    public UnitTest() {
        commands = new LinkedList<>();
    }

    @Override
    public boolean executeTest(TestFile testFile, Engine engine) throws IOException {
        String operationDir = engine.getSettings().opDir + engine.getSolution().getId();
        prepareUnitFiles(operationDir, engine.getSettings().unitProtoDir + engine.getUnitDirName());
        return false;
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
