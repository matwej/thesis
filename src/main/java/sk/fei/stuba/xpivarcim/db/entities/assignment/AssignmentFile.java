package sk.fei.stuba.xpivarcim.db.entities.assignment;

import sk.fei.stuba.xpivarcim.db.entities.Assignment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AssignmentFile<T> {

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    protected Assignment assignment;
    @Column
    protected T content;

    public T getContent() {
        return content;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
