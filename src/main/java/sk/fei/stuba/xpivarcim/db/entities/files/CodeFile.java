package sk.fei.stuba.xpivarcim.db.entities.files;

import java.io.FileOutputStream;
import java.io.IOException;

public class CodeFile {

    private String name;
    private String content;

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

    public String getContent() {
        return content;
    }
}
