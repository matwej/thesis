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
        if (req.getId() == 404) // chyba
            return new Assignment(4, "C", new Date(), null, null, 404);
        /*
        {
            "id":15,
            "assignmentId":3,
            "sourceFiles":[
                {
                    "name":"Unit.java",
                    "content":"public class Unit{\n\npublic static int powTwo(int a) {\nreturn a*a;\n}\n}"
                },{
                    "name":"Main.java",
                    "content":"public class Main{\npublic static void main(String args[]){\nint a = Integer.valueOf(args[0]);\nSystem.out.println(Unit.powTwo(a));\n}\n}"
                }
            ]
        }
         */
        if (req.getId() == 3) {
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(4, "omg nejake class {} () neriesim teraz", "2", "4"));
            Assignment a =
                    new Assignment(req.getId(), "JAVA", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
        }
        /*
            unit test java
            to co vyssie, len assignmentId:5
         */
        if (req.getId() == 5) {
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(10, "int out = Unit.powTwo(3);\nassertEquals(9,out);", null, null));
            tf.add(new TestFile(11, "int out = Unit.powTwo(10);\nassertEquals(1000,out);", null, null));
            Assignment a =
                    new Assignment(req.getId(), "JAVA", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
        }
        /*
        {
            "id":247,
            "assignmentId":99,
            "sourceFiles":[
                {
                    "name":"Hop.cpp",
                    "content":"#include \"Hop.h\"\n\nHop::Hop() {\nh = 4;\n}\nint Hop::hop() {\nreturn h;\n}"
                },{
                    "name":"Hop.h",
                    "content":"class Hop {\nprivate:\nint h;\npublic:\nHop();\nint hop();\n};"
                },{
                    "name":"main.c",
                    "content":"#include <stdio.h>\n#include <stdlib.h>\n#include \"Hop.h\"\n\nint main(int argc, char *argv[]) {\nint i;\nHop * h = new Hop();\nfor(i=0;i<atoi(argv[1]);i++)\nprintf(\"%d \",i+h->hop());\n\nreturn 0;\n}"
                }
            ]
        }
         */
        if (req.getId() == 99) {
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(2, "omg nejake class {} () neriesim teraz", "4", "4 5 6 7 "));
            Assignment a =
                    new Assignment(req.getId(), "C", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
        }
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
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(20, "fail_unless(hop(2,10) == 20, \"failed\");", null, null));
            tf.add(new TestFile(1500, "fail_unless(hop(500,3) == 2000, \"failed\");", null, null));
            Assignment a =
                    new Assignment(req.getId(), "C", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
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
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "1\n2\n3".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(5, "omg nejake class {} () neriesim teraz", "5", "*\n**\n***\n****\n*****\n"));
            tf.add(new TestFile(3, null, "3", "*\n**\n***\n"));
            Assignment a =
                    new Assignment(req.getId(), "C", new Date(), sf, tf, 200);
            a.setFiles();
            return a;
        }
        return null;
    }

}
