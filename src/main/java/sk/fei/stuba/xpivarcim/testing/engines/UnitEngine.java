package sk.fei.stuba.xpivarcim.testing.engines;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class UnitEngine implements Engine {

    private Solution solution;
    private Language language;

    UnitEngine(Solution solution, Language language) {
        this.language = language;
        this.solution = solution;
    }

    @Override
    public void executeTests(String workDir, Result result, Set<TestFile> testFiles) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        prepareUnitFiles(workDir, language.getSettings().unitProtoDir + language.getUnitDirName());
        language.createUnitTestFile(solution, testFiles);
        TestUtils.runCommands(workDir, prepareCommands(language));
        language.mapUnitTestResults(solution, result);
    }

    private Queue<String> prepareCommands(Language language) {
        Queue<String> commands = new LinkedList<>();
        commands.add(language.getCommand("test_prep"));
        commands.add(language.getCommand("test"));
        return commands;
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
