package sk.fei.stuba.xpivarcim.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.exceptions.MessagingResponseException;
import sk.fei.stuba.xpivarcim.producer.strategies.AssignmentProducer;
import sk.fei.stuba.xpivarcim.producer.strategies.Producer;

@Service
public class AssignmentHandler {

    @Autowired
    AssignmentProducer assignmentProducer;

    @Autowired
    AssignmentRepository assignmentRepository;

    public Assignment prepareAssignment(Solution solution) throws MessagingResponseException {
        Assignment assignment = assignmentRepository.findOne(solution.getAssignmentId());
        if (assignment == null)
            assignment = assignmentProducer.download(solution.getAssignmentId());
        else
            assignment = assignmentProducer.update(assignment);
        assignmentRepository.save(assignment);
        return assignment;
    }

}
