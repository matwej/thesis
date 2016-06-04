package sk.fei.stuba.xpivarcim.test.languages;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;
import sk.fei.stuba.xpivarcim.support.Settings;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

class C implements Language {

    private Settings settings;

    C(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile", "gcc *.c -I. -o run");
        commandsMap.put("run", "./run ");
        commandsMap.put("test", "make");
        commandsMap.put("sa", "cppcheck . -q -x c --xml 2> sa_report.xml");
    }

    @Override
    public void createUnitTestFile(String workDir, Solution solution, Set<TestFile> testFiles) throws IOException {
        new CUnitTestFile(settings, solution, testFiles).create(new FileOutputStream(workDir + "/check_unit_test.check"));
    }

    @Override
    public void mapUnitTestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(workDir + "/report.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        Element root = doc.getDocumentElement();
        NodeList testCases = root.getElementsByTagName("test");
        for (int i = 0; i < testCases.getLength(); i++) {
            Element item = (Element) testCases.item(i);
            String testName = item.getElementsByTagName("id").item(0).getTextContent();
            int index = Integer.parseInt(testName.replaceAll("[\\D]", ""));
            result.addTest(index, item.getAttribute("result").equals("success"));
        }
    }

    @Override
    public void mapSATestResults(String workDir, Result result) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(workDir + "/sa_report.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        Element root = doc.getDocumentElement();
        result.setSaTest(root.getElementsByTagName("error").getLength() == 0);
    }


    @Override
    public String getCommand(String key) {
        return commandsMap.get(key);
    }

    @Override
    public void calibrateCommands(Solution solution) {
        // nothing needed so far
    }

    @Override
    public String getUnitDirName() {
        return "cunit";
    }

    @Override
    public String getSADirName() {
        return "csa";
    }

    @Override
    public String getUnitSolDir() {
        return "";
    }

    @Override
    public String getSASolDir() {
        return "";
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
        return "Error";
    }

}
