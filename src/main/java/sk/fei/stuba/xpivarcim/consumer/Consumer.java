package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.producer.strategies.AssignmentProducer;
import sk.fei.stuba.xpivarcim.producer.strategies.ResultProducer;
import sk.fei.stuba.xpivarcim.support.Settings;
import sk.fei.stuba.xpivarcim.test.AssignmentHandler;
import sk.fei.stuba.xpivarcim.test.SolutionHandler;

import java.io.IOException;

@Component
public class Consumer {

    @Autowired
    AssignmentHandler assignmentHandler;

    @Autowired
    ResultProducer resultProducer;

    @Autowired
    Settings settings;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "Solution", durable = "true"),
            exchange = @Exchange(value = "auto.exch"),
            key = "Solution")
    )
    public void processSolution(Solution solution) throws IOException {
        SolutionHandler handler = new SolutionHandler(solution, settings, resultProducer, assignmentHandler);
        handler.test();
    }
}
