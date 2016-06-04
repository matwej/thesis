package sk.fei.stuba.xpivarcim.test.languages.c;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sk.fei.stuba.xpivarcim.producer.messages.Result;
import sk.fei.stuba.xpivarcim.test.languages.UnitTestResults;

public class CUnitTestResults extends UnitTestResults {

    @Override
    public String getPathToXml(String workDir) {
        return workDir + "/report.xml";
    }

    @Override
    public void processTestCases(Element root, Result result) {
        NodeList testCases = root.getElementsByTagName("test");
        for (int i = 0; i < testCases.getLength(); i++) {
            Element item = (Element) testCases.item(i);
            String testName = item.getElementsByTagName("id").item(0).getTextContent();
            int index = Integer.parseInt(testName.replaceAll("[\\D]", ""));
            result.addTest(index, item.getAttribute("result").equals("success"));
        }
    }

}
