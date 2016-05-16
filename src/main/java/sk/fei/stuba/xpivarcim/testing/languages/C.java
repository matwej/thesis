package sk.fei.stuba.xpivarcim.testing.languages;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.Settings;
import sk.fei.stuba.xpivarcim.consumer.Solution;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;
import sk.fei.stuba.xpivarcim.producer.Result;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class C implements Language {

    private Settings settings;
    private final Map<String, String> commandsMap = new HashMap<>();

    public C(Settings settings) {
        this.settings = settings;
        commandsMap.put("compile","g++ *.* -o run");
        commandsMap.put("run", "./run ");
        commandsMap.put("test_prep", ""); // no preparation needed
        commandsMap.put("test","make");
    }

    @Override
    public void createUnitTestFile(Solution solution, Set<TestFile> testFiles) throws IOException {
        FileOutputStream ostream = new FileOutputStream(settings.opDir + solution.getId() + "/check_unit_test.check");
        for(String headerFile : solution.filteredExtensionSourceFiles("h")) {
            ostream.write(("#include \""+headerFile+"\"\n").getBytes("UTF-8"));
        }
        for(TestFile testFile : testFiles) {
            ostream.write(("\n#test _" + testFile.getIndex() + "\n").getBytes("UTF-8"));
            ostream.write(testFile.getContent().getBytes("UTF-8"));
            ostream.write("\n".getBytes());
        }
        ostream.write("\n#main-pre\nsrunner_set_xml(sr,\"report.xml\");\n".getBytes());
        ostream.close();
    }

    @Override
    public void mapUnitTestResults(Solution solution, Result result) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(settings.opDir + solution.getId() + "/report.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        Element root = doc.getDocumentElement();
        NodeList testCases = root.getElementsByTagName("test");
        for(int i=0;i<testCases.getLength();i++) {
            Element item = (Element) testCases.item(i);
            String testName = item.getElementsByTagName("id").item(0).getTextContent();
            int index = Integer.parseInt(testName.replaceAll("[\\D]", ""));
            result.addTest(index, item.getAttribute("result").equals("success"));
        }
    }


    @Override
    public Map<String, String> getCommands() {
        return commandsMap;
    }

    @Override
    public String getUnitDirName() {
        return settings.cUnitDir;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public String getUnitSolDir() {
        return "";
    }
}
