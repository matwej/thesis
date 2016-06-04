package sk.fei.stuba.xpivarcim.test.languages.java;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.test.languages.UnitTestResults;

public class JavaUnitTestResults extends UnitTestResults {


    @Override
    public String getPathToXml(String workDir) {
        return workDir + "/report/TEST-MainTest.xml";
    }

    @Override
    public void processTestCases(Element root, Result result) {
        NodeList testcases = root.getElementsByTagName("testcase");
        for(int i=0;i<testcases.getLength();i++) {
            Node item = testcases.item(i);
            String testName = item.getAttributes().getNamedItem("name").getTextContent();
            int index = Integer.parseInt(testName.replaceAll("[\\D]", ""));
            result.addTest(index, !item.hasChildNodes());
        }
    }
}
