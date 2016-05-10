package sk.fei.stuba.xpivarcim.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.fei.stuba.xpivarcim.entities.Assignment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Producer {

    @Autowired
    RabbitTemplate producerTemplate;

    public void send(String queueName, Object obj) {
        producerTemplate.convertAndSend(queueName, obj);
    }

    public Object sendAndReceive(String queueName, Object obj) {
        return producerTemplate.convertSendAndReceive(queueName, obj);
    }

    public Assignment downloadAssignment(long id) throws ParseException, AssignmentResponseException {
        AssignmentRequest request =
                new AssignmentRequest(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1970"), id);
        Assignment assignment = (Assignment) sendAndReceive("Assignment", request);
        if (assignment.getStatus() != StatusCode.OK.getValue())
            throw new AssignmentResponseException(assignment.getStatus());
        assignment.setFiles();
        assignment.setLastUpdated(new Date());
        return assignment;
    }

    public Assignment updateAssignment(Assignment assignment) throws AssignmentResponseException {
        AssignmentRequest request =
                new AssignmentRequest(assignment.getLastUpdated(), assignment.getId());
        Assignment response = (Assignment) sendAndReceive("Assignment", request);
        if (response.getStatus() == StatusCode.NOT_FOUND.getValue())
            throw new AssignmentResponseException(response.getStatus());
        if (response.getStatus() == StatusCode.NOT_MODIFIED.getValue())
            return assignment;
        response.setFiles();
        response.setLastUpdated(new Date());
        return response;
    }

}
