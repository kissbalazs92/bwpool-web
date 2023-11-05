package runner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Configurations;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;

public class TestNGExtentConfigGenerator {

    public static void main(String[] args) throws Exception {
        generateTestNGConfig();
    }

    public static void generateTestNGConfig() throws Exception {
        String[] browsers = Configurations.getBrowsers();
        String[] resolutions = Configurations.getResolutions();

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element suiteElement = document.createElement("suite");
        suiteElement.setAttribute("name", "BWPool Tests");
        if (Configurations.isParallelExecution()) {
            suiteElement.setAttribute("parallel", "tests");
            suiteElement.setAttribute("thread-count", Configurations.getParallelThreads());
        }
        document.appendChild(suiteElement);

        for (String browser : browsers) {
            for (String resolution : resolutions) {
                Element testElement = document.createElement("test");
                testElement.setAttribute("name", "Test for " + browser.trim() + " and " + resolution.trim());

                Element paramBrowser = document.createElement("parameter");
                paramBrowser.setAttribute("name", "browser");
                paramBrowser.setAttribute("value", browser.trim());
                testElement.appendChild(paramBrowser);

                Element paramResolution = document.createElement("parameter");
                paramResolution.setAttribute("name", "resolution");
                paramResolution.setAttribute("value", resolution.trim());
                testElement.appendChild(paramResolution);

                Element classesElement = document.createElement("classes");
                Element classElement = document.createElement("class");
                classElement.setAttribute("name", "runner.TestRunner");
                classesElement.appendChild(classElement);
                testElement.appendChild(classesElement);

                suiteElement.appendChild(testElement);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("src/test/resources/testng.xml"));

        transformer.transform(domSource, streamResult);

        Path path = Path.of("src/test/resources/testng.xml");
        List<String> lines = Files.readAllLines(path);
        lines.remove(0);
        lines.add(0, "<!DOCTYPE suite SYSTEM \"https://testng.org/testng-1.0.dtd\" >");
        Files.write(path, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
