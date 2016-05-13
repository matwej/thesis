package sk.fei.stuba.xpivarcim.consumer;

/*
JAVA
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
C
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

import sk.fei.stuba.xpivarcim.entities.files.CodeFile;

public class Solution {

    private long id;
    private long assignmentId;
    private CodeFile[] sourceFiles;

    public Solution() {
    }

    public Solution(long id, long assignmentId, CodeFile[] sourceFiles) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.sourceFiles = sourceFiles;
    }

    public long getId() {
        return id;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public CodeFile[] getSourceFiles() {
        return sourceFiles;
    }

}
