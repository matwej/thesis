package sk.fei.stuba.xpivarcim.entities.files;

import org.springframework.data.jpa.domain.AbstractPersistable;
import sk.fei.stuba.xpivarcim.entities.Assignment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ModuleFile<T> extends AbstractPersistable<Long> {

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
