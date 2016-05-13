package sk.fei.stuba.xpivarcim.entities.files;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class TestFile extends ModuleFile<String> implements Serializable {

    private static final long serialVersionUID = 3L;

    @Column
    private int index;
    @Column
    private String input;
    @Column
    private String output;

    public TestFile() {
    }

    public TestFile(int index, String content, String input, String output) {
        this.index = index;
        this.content = content;
        this.input = input;
        this.output = output;
    }

    public String getSafeInput() {
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

    public String getOutput() {
        return output;
    }


}
