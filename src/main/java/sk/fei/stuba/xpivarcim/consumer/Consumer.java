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
            "solutionId":15,
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
            Set<TestFile> tf = new HashSet<>();
            TestFile f = new TestFile(4, "omg nejake class {} () neriesim teraz", "2", "4\n");
            f.setTimeout(10);
            tf.add(f);
            return new Assignment(req.getId(), "JAVA", new Date(), null, tf, 200, true);
        }
        /*
            unit test java
            to co vyssie, len assignmentId:5
         */
        if (req.getId() == 5) {
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(10, "int out = Unit.powTwo(3);\nassertEquals(9,out);", null, null));
            tf.add(new TestFile(11, "int out = Unit.powTwo(10);\nassertEquals(1000,out);", null, null));
            return new Assignment(req.getId(), "JAVA", new Date(), null, tf, 200, false);
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
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(2, "omg nejake class {} () neriesim teraz", "4", "4 5 6 7 "));
            return new Assignment(req.getId(), "C", new Date(), null, tf, 200, false);
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
        /* BUBBLE SORT java run aj unit test
            {
            "id":345,
            "assignmentId":55,
            "sourceFiles":[
		{
                    "name":"Main.java",
                    "content":"import java.io.*;\nimport java.util.ArrayList;\nimport java.util.Scanner;\n \nclass Main {\n  public static void main(String []args) throws FileNotFoundException {\n        Scanner scanner = new Scanner(new FileReader(\"vstup.txt\"));\n        ArrayList<Integer> nums = new ArrayList<>();\n        while(scanner.hasNextLine()) {\n            nums.add(Integer.parseInt(scanner.nextLine()));\n        }\n        scanner.close();\n        Integer[] array = nums.toArray(new Integer[nums.size()]);\n\n\t\t\t\tsort(array);\n\n        for (int c = 0; c < array.length; c++)\n            System.out.println(array[c]);\n  }\n\n\tpublic static void sort(Integer[] array) {\n\t\tint swap;\n\t\tfor (int c = 0; c < ( array.length - 1 ); c++) {\n            for (int d = 0; d < array.length - c - 1; d++) {\n                if (array[d] > array[d+1])\n                {\n                    swap       = array[d];\n                    array[d]   = array[d+1];\n                    array[d+1] = swap;\n                }\n            }\n        }\t\t\n\t}\n}"
                }
            ]
        }
         */
        if (req.getId() == 55) {
            Set<SourceFile> sf = new HashSet<>();
            sf.add(new SourceFile("vstup.txt", "15\n47\n1\n10\n36\n89\n22\n".getBytes()));
            Set<TestFile> tf = new HashSet<>();
            tf.add(new TestFile(1, null, null, "1\n10\n15\n22\n36\n47\n89\n"));
            tf.add(new TestFile(2, "Integer[] a=new Integer[]{5,2,4,1,3};Main.sort(a);assertArrayEquals(a,new Integer[]{1,2,3,4,5});", null, null));
            return new Assignment(req.getId(), "JAVA", new Date(), sf, tf, 200, false);
        }
        return null;
    }

}
