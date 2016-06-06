package sk.fei.stuba.xpivarcim.test;

import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.exceptions.MessagingResponseException;
import sk.fei.stuba.xpivarcim.exceptions.UnsupportedLanguageException;
import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.producer.messages.StatusCode;
import sk.fei.stuba.xpivarcim.producer.strategies.Producer;
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

import static java.nio.file.FileVisitResult.CONTINUE;

public class SolutionHandler {

    private Solution solution;
    private Producer producer;
    private AssignmentHandler assignmentHandler;
    private Result result;
    private Settings settings;
    private Path dir;

    public SolutionHandler(Solution solution, Settings settings, Producer producer, AssignmentHandler assignmentHandler) {
        this.solution = solution;
        this.settings = settings;
        this.producer = producer;
        this.assignmentHandler = assignmentHandler;
        result = new Result(solution.getSolutionId());
        dir = Paths.get(settings.getOperationsDir() + String.valueOf(solution.getSolutionId()));
    }

    public void test() {
        try {
            Assignment assignment = assignmentHandler.prepareAssignment(solution);
            assembleAndRun(assignment);
            result.setStatus(StatusCode.OK.getValue());
        } catch (MessagingResponseException |
                UnsupportedLanguageException |
                CompilationException e) {
            result.setStatus(StatusCode.ERROR.getValue());
            result.setMessage(e.getMessage());
        } catch (IOException e) {
            result.setStatus(StatusCode.UNEXPECTED_ERROR.getValue());
        }
        producer.send(result);
    }

    private void assembleAndRun(Assignment assignment)
            throws IOException, UnsupportedLanguageException, CompilationException {
        setUpDir();
        Language language = LanguageContext.getLanguage(assignment.getCodeLanguage(), settings);
        run(assignment, language);
        tearDownDir();
    }

    private void run(Assignment assignment, Language language) throws IOException, CompilationException {
        EngineCreator engineCreator;
        if(assignment.isSaTest()) {
            engineCreator = new SAEngineCreator();
            engineCreator.execTests(assignment, solution, language, result);
        }
        if (!assignment.runTestFiles().isEmpty()) {
            engineCreator = new RunEngineCreator();
            engineCreator.execTests(assignment, solution, language, result);
        }
        if (!assignment.unitTestFiles().isEmpty()) {
            engineCreator = new UnitEngineCreator();
            engineCreator.execTests(assignment, solution, language, result);
        }
    }

    private void setUpDir() throws IOException {
        if (dir.toFile().exists())
            tearDownDir();
        Files.createDirectories(dir, Settings.ATTRS);
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
