package sk.fei.stuba.xpivarcim.test.languages;

import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.support.Settings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class CUnitTestFile extends UnitTestFile {

    private Settings settings;
    private Solution solution;
    private Set<TestFile> testFiles;

    public CUnitTestFile(Settings settings, Solution solution, Set<TestFile> testFiles) {
        this.settings = settings;
        this.solution = solution;
        this.testFiles = testFiles;
    }

    @Override
    public void writeHead(FileOutputStream stream) throws IOException {
        for (String headerFile : solution.filteredExtensionSourceFiles("h")) {
            stream.write(("#include \"" + headerFile + "\"\n").getBytes());
        }
    }

    @Override
    public void writeTests(FileOutputStream stream) throws IOException {
        for (TestFile testFile : testFiles) {
            stream.write(("\n#test _" + testFile.getIndex() + "\n").getBytes());
            stream.write(testFile.getContent().getBytes());
            stream.write("\n".getBytes());
        }
    }

    @Override
    public void writeTail(FileOutputStream stream) throws IOException {
        stream.write("\n#main-pre\nsrunner_set_xml(sr,\"report.xml\");\n".getBytes());
        stream.write(("tcase_set_timeout(tc1_1," + settings.getUnitTimeout() + ");\n").getBytes());
        stream.close();
    }
}
