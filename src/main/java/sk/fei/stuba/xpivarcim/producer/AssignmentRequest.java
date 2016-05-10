package sk.fei.stuba.xpivarcim.producer;

import java.io.Serializable;
import java.util.Date;

public class AssignmentRequest implements Serializable {

    private static final long serialVersionUID = 5L;

    private long id;
    private Date lastUpdated;

    public AssignmentRequest(Date lastUpdated, long id) {
        this.lastUpdated = lastUpdated;
        this.id = id;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
    public long getId() {
        return id;
    }
}
