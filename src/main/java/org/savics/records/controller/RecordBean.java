package org.savics.records.controller;

import org.savics.records.model.Record;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.Serializable;
@Named
public class RecordBean implements Serializable {
    @PersistenceContext(name = "records-pu", unitName = "records-pu",type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    private static final long serialVersionUID = 1L;
    private Record currentRecord = new Record();

    private Record record;

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Record getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(Record currentRecord) {
        this.currentRecord = currentRecord;
    }

    public Record save(Record record){
        return null;
    }
}
