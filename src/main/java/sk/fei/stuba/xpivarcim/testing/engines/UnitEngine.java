package sk.fei.stuba.xpivarcim.testing.engines;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.CodeFile;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.support.TestUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// TODO dokoncit
public class UnitEngine implements Engine {

    private Solution solution;

    public UnitEngine(Solution solution) {
        this.solution = solution;
    }

    @Override
    public void executeTests(Result result, Set<TestFile> testFiles, Language language) throws IOException, ParserConfigurationException, SAXException {
        String operationDir = language.getSettings().opDir + solution.getId();
        prepareUnitFiles(operationDir, language.getSettings().unitProtoDir + language.getUnitDirName());
        language.createUnitTestFile(solution, testFiles);
        createSolutionFiles(language);
        TestUtils.executeCommands(prepareCommands(language));
        mapResults(result, language);
    }

    private void createSolutionFiles(Language language) throws IOException {
        for (CodeFile file : solution.getSourceFiles()) {
            file.create(language.getSettings().opDir + solution.getId() + language.getUnitSolDir() + "/");
        }
    }

    private Queue<String> prepareCommands(Language language) {
        Queue<String> commands = new LinkedList<>();
        commands.add("cd " + language.getSettings().opDir + solution.getId());
        commands.add(language.getCommands().get("test_prep"));
        commands.add(language.getCommands().get("test"));
        return commands;
    }

    private void mapResults(Result result, Language language) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(language.getSettings().opDir + solution.getId() + "/report/TEST-MainTest.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        Element root = doc.getDocumentElement();
        NodeList testcases = root.getElementsByTagName("testcase");
        for(int i=0;i<testcases.getLength();i++) {
            Node item = testcases.item(i);
            String testName = item.getAttributes().getNamedItem("name").getTextContent();
            int index = Integer.parseInt(testName.replaceAll("[\\D]", ""));
            result.addTest(index, !item.hasChildNodes());
        }
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
