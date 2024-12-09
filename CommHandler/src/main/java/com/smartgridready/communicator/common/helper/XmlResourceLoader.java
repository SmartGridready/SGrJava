package com.smartgridready.communicator.common.helper;

import java.io.IOException;
import java.io.StringReader;

import com.smartgridready.ns.v0.FunctionalProfileFrame;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;

public class XmlResourceLoader<T> {

    private final Class<T> clazz;

    public XmlResourceLoader(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T load(String resourcePath, String xmlContent, boolean validate) throws IOException {

        T result;
		try {
			result = unmarshal(xmlContent, validate);
		} catch (Exception e) {
            throw new IOException(e.getCause() != null ? e.getCause() : e);
		}

        if (result == null) {
			throw new IOException(String.format("Resource '%s' could not be loaded", resourcePath));
		}

		return result;
    }

    @SuppressWarnings("unchecked")
    private T unmarshal(String xmlContent, boolean validate) throws JAXBException, SAXException {
        // Get JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

        // Create Unmarshaller
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        // Setup schema validator
        if (validate) {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema tSchema = sf.newSchema(getClass().getClassLoader().getResource("SGrIncluder.xsd"));
            jaxbUnmarshaller.setSchema(tSchema);
        }

        var result = jaxbUnmarshaller.unmarshal(new StringReader(xmlContent));
        if (result instanceof JAXBElement) {
            var jaxbElement = (JAXBElement<T>)result;
            return jaxbElement.getValue();
        } else if (result instanceof FunctionalProfileFrame) {
            return (T)result;
        }

        throw new JAXBException("Could not unmarshal, result is not a JAXB element");
    }
}
