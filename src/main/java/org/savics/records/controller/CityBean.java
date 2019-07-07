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
public class CityBean {

    private List<String> cities;
    private String selectedItem;

    @PostConstruct

    public void init() {
        cities = Arrays.asList("Paris", "Brussels", "London", "Stocholm", "Beijing", "Casablanca", "Algiers", "Tunis");
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
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
