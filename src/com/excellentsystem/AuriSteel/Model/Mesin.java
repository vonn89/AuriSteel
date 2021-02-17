/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class Mesin {

    private final StringProperty kodeMesin = new SimpleStringProperty();
    private final DoubleProperty kapasitas = new SimpleDoubleProperty();
    private List<MesinDetailBarang> listDetailBarang;

    public List<MesinDetailBarang> getListDetailBarang() {
        return listDetailBarang;
    }

    public void setListDetailBarang(List<MesinDetailBarang> listDetailBarang) {
        this.listDetailBarang = listDetailBarang;
    }

    public double getKapasitas() {
        return kapasitas.get();
    }

    public void setKapasitas(double value) {
        kapasitas.set(value);
    }

    public DoubleProperty kapasitasProperty() {
        return kapasitas;
    }

    public String getKodeMesin() {
        return kodeMesin.get();
    }

    public void setKodeMesin(String value) {
        kodeMesin.set(value);
    }

    public StringProperty kodeMesinProperty() {
        return kodeMesin;
    }

}
