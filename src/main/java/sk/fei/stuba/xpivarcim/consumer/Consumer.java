package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.fei.stuba.xpivarcim.db.entities.Assignment;
import sk.fei.stuba.xpivarcim.db.entities.assignment.SourceFile;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.producer.AssignmentRequest;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.support.Settings;
import sk.fei.stuba.xpivarcim.test.Handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class Consumer {

    @Autowired
    Producer producer;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    Settings settings;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "Solution", durable = "true"),
            exchange = @Exchange(value = "auto.exch"),
            key = "Solution")
    )
    public void processSolution(Solution solution) throws IOException {
        Handler handler = new Handler(solution, assignmentRepository, producer, settings);
        handler.test();
    }
}
