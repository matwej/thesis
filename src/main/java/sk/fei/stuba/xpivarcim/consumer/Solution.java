package sk.fei.stuba.xpivarcim.consumer;

/*
{
    "id":15,
    "assignmentId":3,
    "sourceFiles":[
        {
            "name":"Unit.java",
            "content":"package com.example;\n\npublic class Unit{\n\npublic static int powTwo(int a) {\nreturn a*a;\n}\n}"
        },{
            "name":"Main.java",
            "content":"import com.example.Unit;\n\npublic class Main{\npublic static void main(String args[]){\nint a = Integer.valueOf(args[0]);\nSystem.out.println(Unit.powTwo(a));\n}\n}"
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
