package sk.fei.stuba.xpivarcim.consumer;

/*
{
    "solutionId":15,
    "assignmentId":3,
    "sourceFiles":[
        {
            "name":"prve.java",
            "content":"cHVibGljIGludCBzdW0oaW50IGEsaW50IGIpIHsgcmV0dXJuIGErYjsgfQ=="
        },{
            "name":"druhe.java",
            "content":"cHVibGljIGJvb2xlYW4gaXNfd293KFN0cmluZyBzKSB7IHJldHVybiBzID09ICJXT1ciIH0="
        }
    ]
}
*/

import sk.fei.stuba.xpivarcim.files.SourceFile;

public class InboundMessage {

    private long solutionId;
    private long assignmentId;
    private SourceFile[] sourceFiles;

    public InboundMessage() {
    }

    public InboundMessage(long solutionId, long assignmentId, SourceFile[] sourceFiles) {
        this.solutionId = solutionId;
        this.assignmentId = assignmentId;
        this.sourceFiles = sourceFiles;
    }

    public long getSolutionId() {
        return solutionId;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public SourceFile[] getSourceFiles() {
        return sourceFiles;
    }

}
