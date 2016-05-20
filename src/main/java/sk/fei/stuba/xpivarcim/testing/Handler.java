package sk.fei.stuba.xpivarcim.testing;

import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.entities.Assignment;
import sk.fei.stuba.xpivarcim.entities.files.SourceFile;
import sk.fei.stuba.xpivarcim.producer.AssignmentResponseException;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.producer.StatusCode;
import sk.fei.stuba.xpivarcim.testing.engines.EngineCreator;
import sk.fei.stuba.xpivarcim.testing.engines.RunEngineCreator;
import sk.fei.stuba.xpivarcim.testing.engines.UnitEngineCreator;
import sk.fei.stuba.xpivarcim.testing.languages.Language;
import sk.fei.stuba.xpivarcim.testing.languages.LanguageContext;
import sk.fei.stuba.xpivarcim.testing.support.TestTimedOutException;
import sk.fei.stuba.xpivarcim.testing.support.UnsupportedLanguageException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Handler {

    private Solution solution;
    private Assignment assignment;
    private AssignmentRepository assignmentRepository;
    private Producer producer;
    private Result result;
    private Settings settings;
    private Path dir;

    public Handler(Solution solution, AssignmentRepository assignmentRepository, Producer producer, Settings settings) {
        this.solution = solution;
        this.assignmentRepository = assignmentRepository;
        this.producer = producer;
        this.settings = settings;
        result = new Result(solution.getId());
        dir = Paths.get(settings.opDir + String.valueOf(solution.getId()));
    }

    public void test() {
        try {
            prepareAssignment();
            assembleAndRun();
            result.setStatus(StatusCode.OK.getValue());
        } catch (AssignmentResponseException |
                UnsupportedLanguageException |
                TestTimedOutException e) {
            e.printStackTrace();
            result.setStatus(StatusCode.ERROR.getValue());
            result.appendMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(StatusCode.UNEXPECTED_ERROR.getValue());
            result.appendMessage(e.getMessage());
        }
        producer.send("Result", result);
    }

    private void prepareAssignment() throws AssignmentResponseException {
        assignment = assignmentRepository.findOne(solution.getAssignmentId());
        if (assignment == null)
            assignment = producer.downloadAssignment(solution.getAssignmentId());
        else
            assignment = producer.updateAssignment(assignment);
        assignmentRepository.save(assignment);
    }

    private void assembleAndRun()
            throws IOException, UnsupportedLanguageException,
            ParserConfigurationException, SAXException, ExecutionException,
            InterruptedException, TestTimedOutException {
        setUpDir();
        createSourceFiles();
        Language language = LanguageContext.getLanguage(assignment.getCodeLanguage(), settings);
        if (!assignment.runTestFiles().isEmpty()) {
            EngineCreator engineCreator = new RunEngineCreator();
            engineCreator.execTests(solution, result, assignment.runTestFiles(), language);
        }
        if (!assignment.unitTestFiles().isEmpty()) {
            EngineCreator engineCreator = new UnitEngineCreator();
            engineCreator.execTests(solution, result, assignment.unitTestFiles(), language);
        }
        tearDownDir();
    }

    private void createSourceFiles() throws IOException {
        if (assignment.getSourceFiles() != null)
            for (SourceFile file : assignment.getSourceFiles()) {
                file.create(dir.toString() + "/");
            }
    }

    private void setUpDir() throws IOException {
        if (dir.toFile().exists())
            tearDownDir();
        Files.createDirectory(dir, Settings.ATTRS);
    }

    private void tearDownDir() throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return CONTINUE;
                } else {
                    throw exc;
                }
            }
        });
    }


}
