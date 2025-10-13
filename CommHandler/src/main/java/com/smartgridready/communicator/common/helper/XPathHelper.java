package com.smartgridready.communicator.common.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.common.GenDriverException;

/**
 * Provides helper methods for XML and XPath.
 */
public class XPathHelper {

    private static final Logger LOG = LoggerFactory.getLogger(XPathHelper.class);

    /** Helper class. */
    private XPathHelper() {
        throw new IllegalStateException("Helper class");
    }

    /**
     * Loads an XML string into a document
     * @param xmlResp the XML string
     * @return an instance of {@link Document}
     * @throws ParserConfigurationException when the document parser could not be initialized
     * @throws SAXException when the document could not be parsed
     * @throws IOException when an other error occurred
     */
    public static Document convertXmlToDocument(String xmlResp) throws ParserConfigurationException, SAXException, IOException {
        InputStream xmlStream = new ByteArrayInputStream(xmlResp.getBytes());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xmlStream);
    }

    /**
     * Converts a DOM node to a string.
     * @param node the DOM node
     * @return a string
     * @throws TransformerException when transformation failed
     */
    public static String convertNodeToString(Node node) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        DOMSource source = new DOMSource(node);

        transformer.transform(source, result);
        return writer.toString();
    }

    /**
     * Evaluates an XPath expression on an XML string and returns as SGr value.
     * @param xPath the XPath expression
     * @param xmlResp the XML string
     * @return an instance of {@link StringValue}
     * @throws GenDriverException when an error occurred during parsing
     */
    public static Value parseXmlResponse(String xPath, String xmlResp) throws GenDriverException {
        try {
            if (xPath == null || xPath.trim().isEmpty()) {
                return StringValue.of(xmlResp);
            } else {
                Document doc = convertXmlToDocument(xmlResp);
                XPath xp = XPathFactory.newInstance().newXPath();
                Node node = (Node) xp.compile(xPath).evaluate(doc, XPathConstants.NODE);
                LOG.debug("xml node name={} type={}", node.getNodeName(), node.getNodeType());
                return StringValue.of(convertNodeToString(node));
            }
        } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException | TransformerException e) {
            throw new GenDriverException("Failed to parse XML response", e);
        }
    }
}
