package sk.fei.stuba.xpivarcim.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "Solution", durable = "true"),
            exchange = @Exchange(value = "auto.exch"),
            key = "orderRoutingKey")
    )
    public void processSolution(Solution solution) throws IOException {
        System.out.println(solution.getId());
        System.out.println(solution.getAssignmentId());

        Handler handler = new Handler(solution, assignmentRepository, producer);
        handler.test();

//        FileOutputStream f = new FileOutputStream(file.getName());
//        DataOutputStream dataOutputStream =  new DataOutputStream(f);
//        String c = new String(file.getContent(), "UTF-8");
//        dataOutputStream.writeUTF(c);
    }

    // TODO THIS SHIT JE LEN TESTOVACI A TEMPORARY, OK!!!

    @RabbitListener(queues = "Assignment")
    public Assignment responseAssignment(AssignmentRequest req) {
        Set<SourceFile> sf = new HashSet<>();
        sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
        Set<TestFile> tf = new HashSet<>();
        tf.add(new TestFile(4, "omg nejake class {} () neriesim teraz", null, null));
        Assignment a =
                new Assignment(
                        req.getId(),
                        "JAVA",
                        new Date(),
                        sf,
                        tf,
                        200
                );
        a.setFiles();
        return a;
    }

}
