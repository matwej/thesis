package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.db.repos.AssignmentRepository;
import sk.fei.stuba.xpivarcim.entities.Assignment;
import sk.fei.stuba.xpivarcim.entities.files.SourceFile;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.AssignmentRequest;
import sk.fei.stuba.xpivarcim.producer.Producer;
import sk.fei.stuba.xpivarcim.testing.Handler;

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
            key = "orderRoutingKey")
    )
    public void processSolution(Solution solution) throws IOException {
        System.out.println(solution.getId());

        Handler handler = new Handler(solution, assignmentRepository, producer, settings);
        handler.test();
    }

    // TODO THIS SHIT JE LEN TESTOVACI A TEMPORARY, OK!!!

    @RabbitListener(queues = "Assignment")
    public Assignment responseAssignment(AssignmentRequest req) {
        if(req.getId() == 404)
            return new Assignment(4,"C",new Date(),null, null, 404);
        if(req.getId() == 3) {
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(4, "omg nejake class {} () neriesim teraz", "2", "4"));
            Assignment a =
                    new Assignment(req.getId(), "JAVA", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
        }
        if(req.getId() == 99) {
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(2, "omg nejake class {} () neriesim teraz", "4", "4 5 6 7 "));
            Assignment a =
                    new Assignment(req.getId(), "C", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
        }
        return null;
    }

}
