package sk.fei.stuba.xpivarcim.consumer;

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
