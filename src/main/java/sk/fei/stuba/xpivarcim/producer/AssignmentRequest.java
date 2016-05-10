package sk.fei.stuba.xpivarcim.producer;

import java.io.Serializable;
import java.util.Date;

public class AssignmentRequest implements Serializable {

    private static final long serialVersionUID = 5L;

    private long id;
    private Date lastUpdated;

    public AssignmentRequest() {}

    public AssignmentRequest(Date lastUpdated, long id) {
        this.lastUpdated = lastUpdated;
        this.id = id;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
