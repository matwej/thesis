package sk.fei.stuba.xpivarcim.testing;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.entities.Assignment;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.AssignmentResponseException;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.producer.StatusCode;

import java.text.ParseException;

public class Handler {

    private Solution solution;
    private Assignment assignment;
    private AssignmentRepository assignmentRepository;
    private Producer producer;
    private Result result;
    private Engine engine;

    public Handler(Solution solution, AssignmentRepository assignmentRepository, Producer producer) {
        this.solution = solution;
        this.assignmentRepository = assignmentRepository;
        this.producer = producer;
        result = new Result(solution.getId());
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

    private void assemble() throws UnsupportedLanguageException {
        engine = EngineFactory.getEngine(assignment.getCodeLanguage());
        for(TestFile f : assignment.getTestFiles()) {
            if(f.isRunTest()) {

            } else {

            }
        }
    }


}
