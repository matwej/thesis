package sk.fei.stuba.xpivarcim.test.languages;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import sk.fei.stuba.xpivarcim.producer.messages.Result;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class UnitTestResults {
    public final void map(String workDir, Result result) throws ParserConfigurationException, SAXException, IOException {
        Element root = getRootElement(getPathToXml(workDir));
        processTestCases(root, result);
    }

    private Element getRootElement(String pathToXmlFile) throws IOException, ParserConfigurationException, SAXException {
        InputStream xml = new FileInputStream(pathToXmlFile);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xml);
        return doc.getDocumentElement();
    }

    public abstract String getPathToXml(String workDir);
    public abstract void processTestCases(Element root, Result result);
}
