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

    // TODO THIS SHIT JE LEN TESTOVACI A TEMPORARY, OK!!!

    @RabbitListener(queues = "Assignment")
    public Assignment responseAssignment(AssignmentRequest req) {
        if (req.getId() == 404) // chyba
            return new Assignment(req.getId(), "C", new Date(), null, null, 404, false);

        /*
          {
            "id":247,
            "assignmentId":199,
            "sourceFiles":[
                {
                    "name":"Hop.c",
                    "content":"#include \"Hop.h\"\n\nint hop(int a, int b){\nreturn a*b;\n}"
                },{
                    "name":"Hop.h",
                    "content":"#define _H_\nint hop(int a, int b);"
                }
            ]
        }
         */
        if (req.getId() == 199) {
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(20, "fail_unless(hop(2,10) == 20, \"failed\");", null, null));
            tf.add(new TestFile(1500, "fail_unless(hop(500,3) == 1500, \"failed\");", null, null));
            return new Assignment(req.getId(), "C", new Date(), null, tf, 200, true);
        }
        /*
        {
            "id":247,
            "assignmentId":999,
            "sourceFiles":[
                {
                    "name":"main.c",
                    "content":"#include <stdio.h>\n#include <stdlib.h>\nint main(int argc, char *argv[]) {\nint i,j;\nfor(i=1;i<=atoi(argv[1]);i++){\nfor(j=1;j<=i;j++)\nprintf(\"*\");printf(\"\\n\");}\nreturn 0;\n}"
                }
            ]
        }
        */
        if (req.getId() == 999) {
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(5, "omg nejake class {} () neriesim teraz", "5", "*\n**\n***\n****\n*****\n"));
            tf.add(new TestFile(3, null, "3", "*\n**\n***\n"));
            return new Assignment(req.getId(), "C", new Date(), null, tf, 200, false);
        }
        return null;
    }
}
