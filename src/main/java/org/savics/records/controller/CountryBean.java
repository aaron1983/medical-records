package org.savics.records.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
@ApplicationScoped
public class CountryBean {

    private List<String> countries;
    private String selectedItem;
    @PostConstruct

    public void init() {
        countries = Arrays.asList("France","Belgium","United Kingdom",
                "Sweden","China","Morocco","Algeria","Tunisia","Cameroon");
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setFields(List<String> countries) {
        this.countries = countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void valueChange(ValueChangeEvent event) {


            String selectedItem = (String) event.getNewValue();
            this.selectedItem = selectedItem;

    }

    public void selectedListener(AjaxBehaviorEvent event) {
        //Field selectedItem= (Field) ((UIOutput) event.getSource()).getValue();
        //this.selectedItem = selectedItem;

    }
}
