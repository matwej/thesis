package sk.fei.stuba.xpivarcim.files;

public class SourceFile extends ModuleFile {

    private String name;

    public SourceFile() {}

    public SourceFile(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}
