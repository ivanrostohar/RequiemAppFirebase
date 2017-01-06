package com.example.ivan.requiemapp.models;

/**
 * Created by Ivan on 3.1.2017..
 */

public class ServiceModel {
    private String name, cijena, vrijeme, datum;
    private int numberOfPeople;

    public ServiceModel() {
    }

    public ServiceModel(String name, String cijena, String vrijeme, String datum, int numberOfPeople) {
        this.name = name;
        this.cijena = cijena;
        this.vrijeme = vrijeme;
        this.datum = datum;
        this.numberOfPeople = numberOfPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
