package sk.fei.stuba.xpivarcim.db.entities.assignment;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SourceFile extends AssignmentFile<byte[]> {

    @Column
    private String name;

    public SourceFile() {
    }

    public SourceFile(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

}
