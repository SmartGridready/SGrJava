package com.smartgridready.communicator.common.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XPathHelperTest {

    private static final Logger LOG = LoggerFactory.getLogger(XPathHelperTest.class);
    
    @Test
    void parseXmlResponseWithXPath() throws Exception {
        final String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root><valueTag>value</valueTag><propertyTag property=\"yes\" /><propertyTag property=\"no\" /></root>";
        final String xPath1 = "/root/valueTag";
        final String xPath2 = "/root/propertyTag[@property='yes']";
        final String expectedStr1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><valueTag>value</valueTag>";
        final String expectedStr2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><propertyTag property=\"yes\"/>";

        var rval = XPathHelper.parseXmlResponse(xPath1, xmlStr);
        LOG.info("XML result = {}", rval.getString());
        assertEquals(expectedStr1, rval.getString());

        rval = XPathHelper.parseXmlResponse(xPath2, xmlStr);
        LOG.info("XML result = {}", rval.getString());
        assertEquals(expectedStr2, rval.getString());
    }
}
