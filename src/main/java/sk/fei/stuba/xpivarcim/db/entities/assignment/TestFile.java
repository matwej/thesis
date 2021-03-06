package sk.fei.stuba.xpivarcim.db.entities.assignment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TestFile extends AssignmentFile<String> {

    @Column
    @NotNull
    private int index;
    @Column
    private String input;
    @Column
    private String output;
    @Column
    private int timeout;

    public TestFile() {}

    public TestFile(int index, String content, String input, String output) {
        this.index = index;
        this.content = content;
        this.input = input;
        this.output = output;
    }

    public String safeInput() {
        if(input == null) return "";
        return input.replace("&","").replace("|","");
    }

    public boolean isRunTest() {
        return output != null;
    }

    public int getIndex() {
        return index;
    }

    public String getInput() {
        return input;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getOutput() {
        return output;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
