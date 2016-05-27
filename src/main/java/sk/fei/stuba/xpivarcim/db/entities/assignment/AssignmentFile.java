package sk.fei.stuba.xpivarcim.db.entities.assignment;

import sk.fei.stuba.xpivarcim.db.entities.Assignment;

import javax.persistence.*;

@MappedSuperclass
public class AssignmentFile<T> {

    @Id @GeneratedValue private long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    protected Assignment assignment;
    @Column(columnDefinition = "text")
    protected T content;

    public T getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
