package sk.fei.stuba.xpivarcim.test.languages;

import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.support.Settings;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class JavaUnitTestFile extends UnitTestFile {

    private Settings settings;
    private Set<TestFile> testFiles;

    public JavaUnitTestFile(Settings settings, Set<TestFile> testFiles) throws IOException {
        this.testFiles = testFiles;
        this.settings = settings;
    }

    @Override
    public void writeHead(FileOutputStream stream) throws IOException {
        stream.write("import static org.junit.Assert.*;\n".getBytes());
        stream.write("import org.junit.*;\n".getBytes());
        stream.write("public class MainTest {\n".getBytes());
    }

    @Override
    public void writeTests(FileOutputStream stream) throws IOException {
        for(TestFile testFile : testFiles) {
            stream.write(("@Test(timeout="+String.valueOf(settings.getUnitTimeoutMilis())+")\n").getBytes());
            stream.write(("public void test" + String.valueOf(testFile.getIndex()) + "() throws Exception {\n").getBytes());
            stream.write(testFile.getContent().getBytes());
            stream.write("\n}".getBytes());
        }
    }

    @Override
    public void writeTail(FileOutputStream stream) throws IOException {
        stream.write("}".getBytes());
        stream.close();
    }


}
