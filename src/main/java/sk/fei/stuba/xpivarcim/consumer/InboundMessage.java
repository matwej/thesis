package sk.fei.stuba.xpivarcim.consumer;

/*
 *  {"solutionId":15,"assignmentId":3}
 */

public class InboundMessage {

    private long solutionId;
    private long assignmentId;

    public InboundMessage() {}

    public InboundMessage(long solutionId, long assignmentId) {
        this.solutionId = solutionId;
        this.assignmentId = assignmentId;
    }

    public long getSolutionId() {
        return solutionId;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

}
