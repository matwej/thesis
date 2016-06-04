package sk.fei.stuba.xpivarcim.test;

import org.springframework.context.ApplicationContext;
import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.exceptions.CompilationException;
import sk.fei.stuba.xpivarcim.exceptions.MessagingResponseException;
import sk.fei.stuba.xpivarcim.exceptions.UnsupportedLanguageException;
import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.producer.messages.StatusCode;
import sk.fei.stuba.xpivarcim.producer.strategies.Producer;
import sk.fei.stuba.xpivarcim.producer.strategies.ResultProducer;
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

public class Handler {

    private Solution solution;
    private Assignment assignment;
    private AssignmentRepository assignmentRepository;
    private ApplicationContext applicationContext;
    private Producer producer;
    private Result result;
    private Settings settings;
    private Path dir;

    public Handler(Solution solution, AssignmentRepository assignmentRepository, ApplicationContext applicationContext, Settings settings, Producer producer) {
        this.solution = solution;
        this.assignmentRepository = assignmentRepository;
        this.applicationContext = applicationContext;
        this.settings = settings;
        this.producer = producer;
        result = new Result(solution.getSolutionId());
        dir = Paths.get(settings.getOperationsDir() + String.valueOf(solution.getSolutionId()));
    }

    public void test() {
        try {
            prepareAssignment();
            assembleAndRun();
            result.setStatus(StatusCode.OK.getValue());
        } catch (MessagingResponseException |
                UnsupportedLanguageException |
                CompilationException e) {
            result.setStatus(StatusCode.ERROR.getValue());
            result.setMessage(e.getMessage());
        } catch (IOException e) {
            result.setStatus(StatusCode.UNEXPECTED_ERROR.getValue());
        }
        ResultProducer resultProducer = (ResultProducer) applicationContext.getBean("resultProducer");
        resultProducer.send(result);
    }

    private void prepareAssignment() throws MessagingResponseException {
        assignment = assignmentRepository.findOne(solution.getAssignmentId());
        if (assignment == null)
            assignment = (Assignment) producer.download(solution.getAssignmentId());
        else
            assignment = (Assignment) producer.update(assignment);
        assignmentRepository.save(assignment);
    }

    private void assembleAndRun()
            throws IOException, UnsupportedLanguageException, CompilationException {
        setUpDir();
        Language language = LanguageContext.getLanguage(assignment.getCodeLanguage(), settings);
        run(language);
        tearDownDir();
    }

    private void run(Language language) throws IOException, CompilationException {
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
