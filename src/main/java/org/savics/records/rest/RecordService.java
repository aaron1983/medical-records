package org.savics.records.rest;

import org.savics.records.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.xml.ws.handler.MessageContext;
import java.util.Arrays;
import java.util.List;

@Transactional
@Path("records")
public class RecordService {

    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

    @PersistenceContext(name = "records-pu", unitName = "records-pu", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    private Request request;
    @Context
    MessageContext context;
    @Context
    private HttpServletRequest servletRequest;

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllRecords() {

        List<Record> records = entityManager.createNativeQuery("SELECT * FROM Record  record", Record.class).getResultList();

        return Response.ok().entity(records).build();
    }

    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getRecordById(@PathParam("id") String id) {

        Record record = entityManager.createQuery("SELECT record FROM Record  record WHERE record.id=:id ", Record.class)
                .setParameter("id", id.trim())
                .getSingleResult();
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        EntityTag etag = new EntityTag(Integer.toString(record.hashCode()));
        Response.ResponseBuilder builder = request.evaluatePreconditions(etag);

        if (builder == null) {
            builder = Response.ok(record);
            builder.tag(etag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }

    public Response getSaveRecord(Record record) {

        entityManager.persist(record);

        return Response.accepted().build();
    }


}

