package com.app.Demande;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

    private List<Item> dummyData;

    @PostConstruct
    public void init() {
        dummyData = new ArrayList<>();
        dummyData.add(new Item("Demandeurs inscrits", "12 450", "95%", "Stable"));
        dummyData.add(new Item("Offres disponibles", "3 215", "78%", "En hausse"));
        dummyData.add(new Item("Placements réussis", "1 020", "85%", "Stable"));
        dummyData.add(new Item("Formations suivies", "450", "92%", "En hausse"));
        dummyData.add(new Item("Taux de placement", "60%", "60%", "Moyen"));
    }

    public List<Item> getDummyData() {
        return dummyData;
    }

    public static class Item {
        private String indicator;
        private String value;
        private String indice;
        private String status;

        public Item(String indicator, String value, String indice, String status) {
            this.indicator = indicator;
            this.value = value;
            this.indice = indice;
            this.status = status;
        }

        public String getIndicator() { return indicator; }
        public String getValue() { return value; }
        public String getIndice() { return indice; }
        public String getStatus() { return status; }
    }
}
