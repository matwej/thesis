package sk.fei.stuba.xpivarcim.test.languages.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.messages.CodeFile;
import sk.fei.stuba.xpivarcim.consumer.messages.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.support.Settings;
import sk.fei.stuba.xpivarcim.test.languages.Language;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class Java implements Language {

    private Settings settings;

    public Java(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","javac *.java");
        commandsMap.put("test","gradle test");
        commandsMap.put("sa", "gradle check");
    }

    @Override
    public void createUnitTestFile(String workDir, Solution solution, Set<TestFile> testFiles) throws IOException {
        new JavaUnitTestFile(settings, testFiles).create(new FileOutputStream(workDir + "/src/test/java/MainTest.java"));
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
        Node summary = root.getElementsByTagName("FindBugsSummary").item(0);
        String bugsCount = summary.getAttributes().getNamedItem("total_bugs").getTextContent();
        result.setSaTest("0".equals(bugsCount));
    }

    @Override
    public String getCommand(String key) {
        return commandsMap.get(key);
    }

    @Override
    public void calibrateCommands(Solution solution) {
        // find class with main method
        String className = "";
        for(CodeFile file : solution.getSourceFiles()) {
            if(file.getContent().contains("public static void main")) {
                className = file.getName().replace(".java", "");
                break;
            }
        }
        commandsMap.put("run", "java " + className + " ");
    }

    @Override
    public String getUnitDirName() {
        return "javaunit";
    }

    @Override
    public String getSADirName() {
        return "javasa";
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

    @Override
    public boolean isCompiled() {
        return true;
    }

    @Override
    public String compilationErrorString() {
        return "Compilation failed";
    }
}
