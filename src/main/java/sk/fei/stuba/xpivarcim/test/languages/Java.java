package sk.fei.stuba.xpivarcim.test.languages;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.support.Settings;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Set;

class Java implements Language {

    private Settings settings;

    Java(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","javac *.java");
        commandsMap.put("run", "java Main ");
        commandsMap.put("test","gradle test");
        commandsMap.put("sa", "gradle check");
    }

    @Override
    public void createUnitTestFile(Solution solution, Set<TestFile> testFiles) throws IOException {
        FileOutputStream ostream = new FileOutputStream(settings.opDir + solution.getId() + "/src/test/java/MainTest.java");
        ostream.write("import static org.junit.Assert.*;\n".getBytes());
        ostream.write("import org.junit.*;\n".getBytes());
        ostream.write("public class MainTest {\n".getBytes());
        for(TestFile testFile : testFiles) {
            ostream.write(("@Test(timeout="+String.valueOf(settings.getUnitTimeoutMilis())+")\n").getBytes());
            ostream.write(("public void test" + String.valueOf(testFile.getIndex()) + "() throws Exception {\n").getBytes());
            ostream.write(testFile.getContent().getBytes());
            ostream.write("\n}".getBytes());
        }
        ostream.write("}".getBytes());
        ostream.close();
    }

    @Override
    public void mapUnitTestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(workDir + "/report/TEST-MainTest.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        Element root = doc.getDocumentElement();
        NodeList testcases = root.getElementsByTagName("testcase");
        for(int i=0;i<testcases.getLength();i++) {
            Node item = testcases.item(i);
            String testName = item.getAttributes().getNamedItem("name").getTextContent();
            int index = Integer.parseInt(testName.replaceAll("[\\D]", ""));
            result.addTest(index, !item.hasChildNodes());
        }
    }

    @Override
    public void mapSATestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(workDir + "/report/main.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        Element root = doc.getDocumentElement();
        // TODO dorobit
    }

    @Override
    public String getCommand(String key) {
        return commandsMap.get(key);
    }

    @Override
    public String getUnitDirName() {
        return settings.javaUnitDir;
    }

    @Override
    public String getSADirName() {
        return settings.javaStaticDir;
    }

    @Override
    public String getUnitSolDir() {
        return "/src/main/java";
    }

    @Override
    public String getSASolDir() {
        return "/src/main/java";
    }

    @Override
    public Settings getSettings() {
        return settings;
    }
}
