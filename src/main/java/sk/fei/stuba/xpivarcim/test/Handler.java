package sk.fei.stuba.xpivarcim.test;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.exceptions.AssignmentResponseException;
import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.exceptions.UnsupportedLanguageException;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.producer.StatusCode;
import sk.fei.stuba.xpivarcim.support.Settings;
import sk.fei.stuba.xpivarcim.test.core.factories.EngineCreator;
import sk.fei.stuba.xpivarcim.test.core.factories.RunEngineCreator;
import sk.fei.stuba.xpivarcim.test.core.factories.SAEngineCreator;
import sk.fei.stuba.xpivarcim.test.core.factories.UnitEngineCreator;
import sk.fei.stuba.xpivarcim.test.languages.Language;
import sk.fei.stuba.xpivarcim.test.languages.LanguageContext;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeoutException;

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
        dir = Paths.get(settings.getOperationsDir() + String.valueOf(solution.getId()));
    }

    public void test() {
        try {
            prepareAssignment();
            assembleAndRun();
            result.setStatus(StatusCode.OK.getValue());
        } catch (AssignmentResponseException |
                UnsupportedLanguageException |
                CompilationException |
                TimeoutException e) {
            result.setStatus(StatusCode.ERROR.getValue());
            result.setMessage(e.getMessage());
        } catch (IOException e) {
            result.setStatus(StatusCode.UNEXPECTED_ERROR.getValue());
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
            throws IOException, UnsupportedLanguageException, TimeoutException, CompilationException {
        setUpDir();
        Language language = LanguageContext.getLanguage(assignment.getCodeLanguage(), settings);
        run(language);
        tearDownDir();
    }

    private void run(Language language) throws IOException, TimeoutException, CompilationException {
        if(assignment.isSaTest()) {
            EngineCreator engineCreator = new SAEngineCreator();
            engineCreator.execTests(assignment, solution, language, result);
        }
        if (!assignment.runTestFiles().isEmpty()) {
            EngineCreator engineCreator = new RunEngineCreator();
            engineCreator.execTests(assignment, solution, language, result);
        }
        if (!assignment.unitTestFiles().isEmpty()) {
            EngineCreator engineCreator = new UnitEngineCreator();
            engineCreator.execTests(assignment, solution, language, result);
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
