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
 * @author Xtreme
 */
public class PemesananBahanHead {
    
    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final StringProperty tglPemesanan = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty paymentTerm = new SimpleStringProperty();
    private final DoubleProperty kurs = new SimpleDoubleProperty();
    private final DoubleProperty totalPemesanan = new SimpleDoubleProperty();
    private final DoubleProperty downPayment = new SimpleDoubleProperty();
    private final DoubleProperty sisaDownPayment = new SimpleDoubleProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private Customer customer;
    private Pegawai sales;
    private List<PemesananBahanDetail> listPemesananBahanDetail;
    private List<Hutang> listHutang;

    public List<Hutang> getListHutang() {
        return listHutang;
    }

    public void setListHutang(List<Hutang> listHutang) {
        this.listHutang = listHutang;
    }

    public List<PemesananBahanDetail> getListPemesananBahanDetail() {
        return listPemesananBahanDetail;
    }

    public void setListPemesananBahanDetail(List<PemesananBahanDetail> listPemesananBahanDetail) {
        this.listPemesananBahanDetail = listPemesananBahanDetail;
    }
    
    
    public Pegawai getSales() {
        return sales;
    }

    public void setSales(Pegawai sales) {
        this.sales = sales;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    public double getSisaDownPayment() {
        return sisaDownPayment.get();
    }

    public void setSisaDownPayment(double value) {
        sisaDownPayment.set(value);
    }

    public DoubleProperty sisaDownPaymentProperty() {
        return sisaDownPayment;
    }


    public double getKurs() {
        return kurs.get();
    }

    public void setKurs(double value) {
        kurs.set(value);
    }

    public DoubleProperty kursProperty() {
        return kurs;
    }

    

    public double getDownPayment() {
        return downPayment.get();
    }

    public void setDownPayment(double value) {
        downPayment.set(value);
    }

    public DoubleProperty downPaymentProperty() {
        return downPayment;
    }
    

    public String getPaymentTerm() {
        return paymentTerm.get();
    }

    public void setPaymentTerm(String value) {
        paymentTerm.set(value);
    }

    public StringProperty paymentTermProperty() {
        return paymentTerm;
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
    

    public String getNoPemesanan() {
        return noPemesanan.get();
    }

    public void setNoPemesanan(String value) {
        noPemesanan.set(value);
    }

    public StringProperty noPemesananProperty() {
        return noPemesanan;
    }

    public String getTglPemesanan() {
        return tglPemesanan.get();
    }

    public void setTglPemesanan(String value) {
        tglPemesanan.set(value);
    }

    public StringProperty tglPemesananProperty() {
        return tglPemesanan;
    }

    public String getKodeCustomer() {
        return kodeCustomer.get();
    }

    public void setKodeCustomer(String value) {
        kodeCustomer.set(value);
    }

    public StringProperty kodeCustomerProperty() {
        return kodeCustomer;
    }

    public double getTotalPemesanan() {
        return totalPemesanan.get();
    }

    public void setTotalPemesanan(double value) {
        totalPemesanan.set(value);
    }

    public DoubleProperty totalPemesananProperty() {
        return totalPemesanan;
    }

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
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

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
    }

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
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
}
