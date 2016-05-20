package sk.fei.stuba.xpivarcim.db.entities.files;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.FileOutputStream;
import java.io.IOException;

@Entity
public class SourceFile extends ModuleFile<byte[]> {

    @Column
    private String name;

    public SourceFile() {
    }

    public SourceFile(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public void create(String dirPath) throws IOException {
        FileOutputStream ostream = new FileOutputStream(dirPath + name);
        ostream.write(content);
        ostream.close();
    }

    public String getName() {
        return name;
    }

}
