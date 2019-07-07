package org.savics.records.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbUtils {

    private static final Logger logger = LoggerFactory.getLogger(JaxbUtils.class);

    /**
     * JavaBean to xml
     * Encoding UTF-8
     *
     * @param obj
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * JavaBean to xml
     *
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {

        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            logger.error("#[JaxbUtils] convertToXml occurs error", e);
        }

        return result;
    }

    /**
     * JavaBean to xml with
     *
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXmCommand(Object obj, String encoding) {

        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = null;
            marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.fragment", true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            StringWriter stringWriter = new StringWriter();
            stringWriter.append("<?xml version=\"1.0\"?>");
            stringWriter.append("<!DOCTYPE COMMAND PUBLIC -//Ocam//DTD XML Command 1.0//EN xml/command.dtd>");
            marshaller.marshal(obj, stringWriter);
            result = stringWriter.toString();
        } catch (Exception e) {
            logger.error("#[JaxbUtils] convertToXml occurs error", e);
        }

        return result;
    }

    /**
     * xml to JavaBean
     *
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static <T> T converyToJavaBean(XMLStreamReader xsr, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(xsr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static <T> T converyToJavaBean(Class<T> c, InputSource source, JAXBContext context) {
        T t = null;
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(source);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
}
