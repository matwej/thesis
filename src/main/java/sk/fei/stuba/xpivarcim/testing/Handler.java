package sk.fei.stuba.xpivarcim.testing;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.entities.Assignment;
import sk.fei.stuba.xpivarcim.producer.AssignmentRequest;
import sk.fei.stuba.xpivarcim.producer.AssignmentResponseException;
import sk.fei.stuba.xpivarcim.producer.Producer;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Handler {

    private Solution solution;
    private Assignment assignment;
    private AssignmentRepository assignmentRepository;
    private Producer producer;

    public Handler(Solution solution, AssignmentRepository assignmentRepository, Producer producer) {
        this.solution = solution;
        this.assignmentRepository = assignmentRepository;
        this.producer = producer;
    }

    public void prepareAssignment() throws ParseException, AssignmentResponseException {
        assignment = assignmentRepository.findOne(solution.getAssignmentId());
        if(assignment == null) {
            AssignmentRequest request =
                    new AssignmentRequest(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1970"),solution.getAssignmentId());
            assignment = (Assignment) producer.sendAndReceive("Assignment", request);
            if(assignment.getStatus() != 200)
                throw new AssignmentResponseException(assignment.getStatus());
            assignment.setFiles(); // kvoli persistencii
            assignmentRepository.save(assignment);
        } else {

        }
    }

    public void prepareSolution() {

    }

    public void test() {
        try {
            prepareAssignment();
        } catch (Exception e) {
            e.printStackTrace();
        }
        prepareSolution();
    }

}
