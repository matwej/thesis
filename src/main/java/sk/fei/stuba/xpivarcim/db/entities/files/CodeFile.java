package sk.fei.stuba.xpivarcim.db.entities.files;

import java.io.FileOutputStream;
import java.io.IOException;

public class CodeFile extends ModuleFile<String> {

    private String name;

    public CodeFile() {
    }

    public CodeFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void create(String dirPath) throws IOException {
        FileOutputStream ostream = new FileOutputStream(dirPath + name);
        ostream.write(content.getBytes("UTF-8"));
        ostream.close();
    }

    public String getName() {
        return name;
    }
}
