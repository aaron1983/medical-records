package org.savics.records.rest;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.savics.records.model.Record;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestClient {

    private static final String REST_URI = "http://127.0.0.1:8080/medical-records/v1/records";
    private static List<Object> providers = new ArrayList<Object>();
    private static WebTarget target;

    public static List<Record> getRecords(){

        List<Record> records = new ArrayList<Record>();
        try {

            Client client = ClientBuilder.newBuilder().build();
            providers.add(new JacksonJsonProvider());
            client.register(providers);
            WebTarget target = client.target(REST_URI);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) {
                records = response.readEntity(new GenericType<List<Record>>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    public static Record getRecordById(Long id) {
        Record record = new Record();
        try {
            Client client = ClientBuilder.newBuilder().build();
            client.register(Arrays.asList(new JacksonJsonProvider()));
            WebTarget target = client.target(REST_URI + "/" + id);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT_CHARSET,"UTF-8")
                    .get();
            if (response.getStatus() == 200) {
                record = response.readEntity(new GenericType<Record>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }

    public static Record save(Record record) {

        try {
            Entity<Record> recordEntity=Entity.entity(record,MediaType.APPLICATION_JSON);

            Client client = ClientBuilder.newBuilder().build();
            client.register(Arrays.asList(new JacksonJsonProvider()));
            WebTarget target = client.target(REST_URI );

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT_CHARSET,"UTF-8")
                    .post(recordEntity);
            if (response.getStatus() == 200) {
                record = response.readEntity(new GenericType<Record>() {
                });
            } else {
                response.close();
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }
}
