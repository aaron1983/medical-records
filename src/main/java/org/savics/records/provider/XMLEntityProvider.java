package org.savics.records.provider;


import org.savics.records.util.JaxbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces({"text/xml", "application/xml"})
@Dependent
public class XMLEntityProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

    private static final Logger logger = LoggerFactory.getLogger(XMLEntityProvider.class);
    @Context
    private Providers providers;

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public T readFrom(Class<T> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        logger.debug("readFrom: {}/{}/{}", aClass.getSimpleName(), type, mediaType);
        try {
            JAXBContext jaxbContext=findProvidedJAXBContext(aClass,mediaType);

            return JaxbUtils.converyToJavaBean(aClass, new InputSource(inputStream), jaxbContext);
        } catch (final JAXBException e) {
            logger.warn("An error occurred while unmarshaling a {} object", aClass.getSimpleName());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(T t, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try {
            ContextResolver<JAXBContext> resolver
                    = providers.getContextResolver(JAXBContext.class, mediaType);
            JAXBContext jaxbContext;
            if (null == resolver || null == (jaxbContext = resolver.getContext(aClass))) {
                jaxbContext = JAXBContext.newInstance(aClass);
            }
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(t, outputStream);
        } catch (JAXBException jaxbException) {
            throw new WebApplicationException(jaxbException);
        }

    }

    public JAXBContext findProvidedJAXBContext(Class<?> type, MediaType mediaType) throws JAXBException
    {
        JAXBContext jaxbContext = null;
        ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
        if (resolver != null)
        {
            jaxbContext = resolver.getContext(type);
            if (jaxbContext != null) return jaxbContext;
        }
        return jaxbContext;
    }
}
