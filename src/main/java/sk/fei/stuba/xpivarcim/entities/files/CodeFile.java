package sk.fei.stuba.xpivarcim.entities.files;

public class CodeFile {

    private String name;
    private String content;

    public CodeFile() {
    }

    public CodeFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
