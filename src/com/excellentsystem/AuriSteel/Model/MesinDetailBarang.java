/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class MesinDetailBarang {

    private final StringProperty kodeMesin = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final BooleanProperty status = new SimpleBooleanProperty();

    public boolean isStatus() {
        return status.get();
    }

    public void setStatus(boolean value) {
        status.set(value);
    }

    public BooleanProperty statusProperty() {
        return status;
    }

    public String getKodeBarang() {
        return kodeBarang.get();
    }

    public void setKodeBarang(String value) {
        kodeBarang.set(value);
    }

    public StringProperty kodeBarangProperty() {
        return kodeBarang;
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
