/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Keuangan {

    private final StringProperty noKeuangan = new SimpleStringProperty();
    private final StringProperty tglKeuangan = new SimpleStringProperty();
    private final StringProperty tglTransaksi = new SimpleStringProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty noTransaksi = new SimpleStringProperty();
    private final StringProperty deskripsi = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getTglTransaksi() {
        return tglTransaksi.get();
    }

    public void setTglTransaksi(String value) {
        tglTransaksi.set(value);
    }

    public StringProperty tglTransaksiProperty() {
        return tglTransaksi;
    }

    public String getNoTransaksi() {
        return noTransaksi.get();
    }

    public void setNoTransaksi(String value) {
        noTransaksi.set(value);
    }

    public StringProperty noTransaksiProperty() {
        return noTransaksi;
    }

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public String getDeskripsi() {
        return deskripsi.get();
    }

    public void setDeskripsi(String value) {
        deskripsi.set(value);
    }

    public StringProperty deskripsiProperty() {
        return deskripsi;
    }

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }

    public String getTglKeuangan() {
        return tglKeuangan.get();
    }

    public void setTglKeuangan(String value) {
        tglKeuangan.set(value);
    }

    public StringProperty tglKeuanganProperty() {
        return tglKeuangan;
    }

    public String getNoKeuangan() {
        return noKeuangan.get();
    }

    public void setNoKeuangan(String value) {
        noKeuangan.set(value);
    }

    public StringProperty noKeuanganProperty() {
        return noKeuangan;
    }

}
