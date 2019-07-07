package org.savics.records.view;


import org.savics.records.controller.RecordBean;
import org.savics.records.controller.RecordController;
import org.savics.records.model.Record;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class DataExporterView implements Serializable {

    private List<Record> records;

    @Inject
    @ManagedProperty("#{recordController}")
    private RecordController service;
    @Inject
    @ManagedProperty("#{recordBean}")
    private RecordBean recordBean;

    @PostConstruct
    public void init() {
        records=service.getRecords();

    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public RecordController getService() {
        return service;
    }

    public void setService(RecordController service) {
        this.service = service;
    }

    public RecordBean getRecordBean() {
        return recordBean;
    }

    public void setRecordBean(RecordBean recordBean) {
        this.recordBean = recordBean;
    }
}
