package sk.fei.stuba.xpivarcim.test.languages;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class UnitTestFile {

    public final void create(FileOutputStream stream) throws IOException {
        writeHead(stream);
        writeTests(stream);
        writeTail(stream);
    }

    public abstract void writeHead(FileOutputStream stream) throws IOException;
    public abstract void writeTests(FileOutputStream stream) throws IOException;
    public abstract void writeTail(FileOutputStream stream) throws IOException;
}
