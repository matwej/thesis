package sk.fei.stuba.xpivarcim.entities.files;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class SourceFile extends ModuleFile<byte[]> implements Serializable {

    private static final long serialVersionUID = 2L;

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
