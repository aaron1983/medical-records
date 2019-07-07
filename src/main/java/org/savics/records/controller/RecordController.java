package org.savics.records.controller;

import org.savics.records.model.Record;
import org.savics.records.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Aaron MAJAMBO
 * aaronmajb@gmail.com
 *
 * */
@Named
@ConversationScoped
public class RecordController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);
    private Long id;
    private Record record;
    @Produces
    @Named
    private FacesContext facesContext;
    @Inject
    private Conversation conversation;
    private long count;
    private Boolean minorOnly;

    private List<Record> records = getRecordsList();

    public List<Record> getRecordsList() {
        List<Record> records = new ArrayList<Record>();

        //For testing purposes
        Record record=new Record();
        record.setFirstName("Jean");
        record.setLastName("DUPONT");
        record.setAge(46);
        record.setCity("Paris");
        record.setCountry("France");
        record.setGender("Male");
        record.setDiabetesStatus(false);

        Record record1=new Record();
        record1.setFirstName("Amina");
        record1.setLastName("TALEB");
        record1.setAge(12);
        record1.setCity("Alger");
        record1.setCountry("Algerie");
        record1.setGender("Female");
        record1.setDiabetesStatus(false);

        Record record2=new Record();
        record2.setFirstName("Marc");
        record2.setLastName("VANVO");
        record2.setAge(19);
        record2.setCity("Brussels");
        record2.setCountry("Belgium");
        record2.setGender("Male");
        record2.setDiabetesStatus(false);

        Record record3=new Record();
        record3.setFirstName("Aaron");
        record3.setLastName("MAJAMBO");
        record3.setAge(35);
        record3.setCity("Bujumbura");
        record3.setCountry("Burundi");
        record3.setGender("Male");
        record3.setDiabetesStatus(false);

        Record record4=new Record();
        record4.setFirstName("Aïssa");
        record4.setLastName("GHOUTI");
        record4.setAge(35);
        record4.setCity("Brussels");
        record4.setCountry("Belgium");
        record4.setGender("Female");
        record4.setDiabetesStatus(false);

        Record record5=new Record();
        record5.setFirstName("Alexandre");
        record5.setLastName("EBONOCK");
        record5.setAge(30);
        record5.setCity("Douala");
        record5.setCountry("Cameroon");
        record5.setGender("Male");
        record5.setDiabetesStatus(false);


        try {
           // records = RestClient.getRecords();
            records =Arrays.asList(record,record1,record2,record3,record4,record5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;

    }

    public void init() {
        if (!facesContext.isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String finishConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "index?faces-redirect=true";
    }

    public void retrieve() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
            //this.conversation.setTimeout(1800000L);
        }
        this.record = findById(getId());

    }

    private Record findById(Long id) {
        Record record = null;
        try {
            Record localRecord = record;
            Optional<Record> optionalRecord=records.stream().filter(payment1 -> localRecord.getId().equals(id)).findAny();
            if(optionalRecord.isPresent()){
                record=optionalRecord.get();
            }else {
                record = RestClient.getRecordById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Boolean getMinorOnly() {
        return minorOnly;
    }

    public void setMinorOnly(Boolean minorOnly) {
        this.minorOnly = minorOnly;
    }
}
