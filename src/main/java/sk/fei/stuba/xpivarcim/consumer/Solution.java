package sk.fei.stuba.xpivarcim.consumer;

import sk.fei.stuba.xpivarcim.support.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> filteredExtensionSourceFiles(String extension) {
        List<String> out = new ArrayList<>();
        for(CodeFile file : sourceFiles) {
            if(file.getName().endsWith("."+extension)) {
                out.add(file.getName());
            }
        }
        return out;
    }

    public void createFiles(String targetDir) throws IOException {
        for(CodeFile file : sourceFiles) {
            Utils.createFile(targetDir, file.getName(), file.getContent().getBytes("UTF-8"));
        }
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
