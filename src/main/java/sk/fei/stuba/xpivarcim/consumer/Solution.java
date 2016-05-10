package sk.fei.stuba.xpivarcim.consumer;

/*
{
    "id":15,
    "assignmentId":3,
    "sourceFiles":[
        {
            "name":"prve.java",
            "content":"public class Main{\n\npublic static int powTwo(int a) {\nreturn a*a;\n}\n}"
        },{
            "name":"druhe.java",
            "content":"tralala"
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
