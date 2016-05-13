package sk.fei.stuba.xpivarcim.testing;

import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.entities.Assignment;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.AssignmentResponseException;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.producer.StatusCode;
import sk.fei.stuba.xpivarcim.testing.engines.Engine;
import sk.fei.stuba.xpivarcim.testing.test.RunTest;
import sk.fei.stuba.xpivarcim.testing.test.UnitTest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Handler {

    private Solution solution;
    private Assignment assignment;
    private AssignmentRepository assignmentRepository;
    private Producer producer;
    private Result result;
    private Engine engine;
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
            assemble();
        } catch (ParseException | AssignmentResponseException | UnsupportedLanguageException e) {
            result.setStatus(StatusCode.ERROR.getValue());
            result.appendMessage(e.getMessage());
        } catch (Exception e) {
            result.setStatus(StatusCode.UNEXPECTED_ERROR.getValue());
            result.appendMessage(e.getMessage());
        }
        producer.send("Result", result);
    }

    private void prepareAssignment() throws ParseException, AssignmentResponseException {
        assignment = assignmentRepository.findOne(solution.getAssignmentId());
        if (assignment == null)
            assignment = producer.downloadAssignment(solution.getAssignmentId());
        else
            assignment = producer.updateAssignment(assignment);
        assignmentRepository.save(assignment);
    }

    private void assemble() throws UnsupportedLanguageException, IOException {
        engine = EngineFactory.getEngine(assignment.getCodeLanguage(), solution, settings);
        setUpDir();
        for (TestFile f : assignment.getTestFiles()) {
            if (f.isRunTest()) {
                result.addTest(f.getIndex(), engine.executeTest(f, new RunTest()));
            } else {
                result.addTest(f.getIndex(), engine.executeTest(f, new UnitTest()));
            }
        }
        tearDownDir();
        result.setStatus(StatusCode.OK.getValue());
    }

    private void setUpDir() throws IOException {
        if (dir.toFile().exists())
            tearDownDir();
        Files.createDirectory(dir, Settings.ATTRS);
//        UserPrincipalLookupService lookupService =
//                FileSystems.getDefault().getUserPrincipalLookupService();
//        UserPrincipal userPrincipal =
//                lookupService.lookupPrincipalByName("tester");
//        Files.setOwner(path, userPrincipal);
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
