package sk.fei.stuba.xpivarcim.producer.strategies;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.exceptions.MessagingResponseException;
import sk.fei.stuba.xpivarcim.producer.messages.AssignmentRequest;
import sk.fei.stuba.xpivarcim.producer.messages.StatusCode;

import java.util.Calendar;
import java.util.Date;

@Service
public class AssignmentProducer implements Producer<Assignment> {

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean(name = "assignmentTemplate")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setDefaultType(Assignment.class);
        converter.setClassMapper(mapper);
        template.setMessageConverter(converter);
        return template;
    }

    @Override
    public Assignment download(long id) throws MessagingResponseException {
        Calendar cal = Calendar.getInstance();
        cal.set(1970,Calendar.JANUARY,1);
        AssignmentRequest request =
                new AssignmentRequest(cal.getTime(), id);
        Assignment assignment = sendAndReceive(request, AssignmentRequest.class);
        if (assignment == null)
            throw new MessagingResponseException(404);
        if (assignment.getStatus() != StatusCode.OK.getValue())
            throw new MessagingResponseException(assignment.getStatus());
        assignment.setFiles();
        assignment.setLastUpdated(new Date());
        return assignment;
    }

    @Override
    public Assignment update(Assignment object) throws MessagingResponseException {
        AssignmentRequest request =
                new AssignmentRequest(object.getLastUpdated(), object.getId());
        Assignment response = sendAndReceive(request, AssignmentRequest.class);
        if (response.getStatus() == StatusCode.NOT_FOUND.getValue())
            throw new MessagingResponseException(response.getStatus());
        if (response.getStatus() == StatusCode.NOT_MODIFIED.getValue())
            return object;
        response.setFiles();
        response.setLastUpdated(new Date());
        return response;
    }

    @Override
    public void send(Assignment object) {
        rabbitTemplate().convertAndSend("Assignment", object);
    }

    @Override
    public <K> Assignment sendAndReceive(K object, Class<K> type) {
        return (Assignment) rabbitTemplate().convertSendAndReceive("Assignment", object);
    }

}
