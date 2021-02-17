/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class RencanaProduksi {

    private final StringProperty noRencana = new SimpleStringProperty();
    private final StringProperty hari = new SimpleStringProperty();
    private final IntegerProperty noKirim = new SimpleIntegerProperty();
    private final StringProperty tujuanKirim = new SimpleStringProperty();
    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final StringProperty customer = new SimpleStringProperty();
    private final StringProperty barang = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty sales = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty tonase = new SimpleDoubleProperty();

    public String getNoRencana() {
        return noRencana.get();
    }

    public void setNoRencana(String value) {
        noRencana.set(value);
    }

    public StringProperty noRencanaProperty() {
        return noRencana;
    }

    public String getTujuanKirim() {
        return tujuanKirim.get();
    }

    public void setTujuanKirim(String value) {
        tujuanKirim.set(value);
    }

    public StringProperty tujuanKirimProperty() {
        return tujuanKirim;
    }

    public int getNoKirim() {
        return noKirim.get();
    }

    public void setNoKirim(int value) {
        noKirim.set(value);
    }

    public IntegerProperty noKirimProperty() {
        return noKirim;
    }

    public double getTonase() {
        return tonase.get();
    }

    public void setTonase(double value) {
        tonase.set(value);
    }

    public DoubleProperty tonaseProperty() {
        return tonase;
    }

    public double getQty() {
        return qty.get();
    }

    public void setQty(double value) {
        qty.set(value);
    }

    public DoubleProperty qtyProperty() {
        return qty;
    }

    public String getSales() {
        return sales.get();
    }

    public void setSales(String value) {
        sales.set(value);
    }

    public StringProperty salesProperty() {
        return sales;
    }

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public String getBarang() {
        return barang.get();
    }

    public void setBarang(String value) {
        barang.set(value);
    }

    public StringProperty barangProperty() {
        return barang;
    }

    public String getCustomer() {
        return customer.get();
    }

    public void setCustomer(String value) {
        customer.set(value);
    }

    public StringProperty customerProperty() {
        return customer;
    }
    
    public String getNoPemesanan() {
        return noPemesanan.get();
    }

    public void setNoPemesanan(String value) {
        noPemesanan.set(value);
    }

    public StringProperty noPemesananProperty() {
        return noPemesanan;
    }

    public String getHari() {
        return hari.get();
    }

    public void setHari(String value) {
        hari.set(value);
    }

    public StringProperty hariProperty() {
        return hari;
    }
    
}
