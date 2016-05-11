package sk.fei.stuba.xpivarcim.testing;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.entities.Assignment;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.AssignmentResponseException;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.producer.Result;

import java.text.ParseException;

public class Handler {

    private Solution solution;
    private Assignment assignment;
    private AssignmentRepository assignmentRepository;
    private Producer producer;
    private Result result;

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
        } catch (Exception e) {
            result.setStatus(500);
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

    private void assemble() {
        for(TestFile f : assignment.getTestFiles()) {
            if(f.isRunTest()) {

            } else {

            }
        }
    }


}
