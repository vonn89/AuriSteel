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
public class Customer {
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty kota = new SimpleStringProperty();
    private final StringProperty negara = new SimpleStringProperty();
    private final StringProperty kodePos = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty kontakPerson = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty noHandphone = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty noNpwp = new SimpleStringProperty();
    private final StringProperty namaNpwp = new SimpleStringProperty();
    private final StringProperty alamatNpwp = new SimpleStringProperty();
    private final DoubleProperty limitHutang = new SimpleDoubleProperty();
    private final DoubleProperty hutang = new SimpleDoubleProperty();
    private final DoubleProperty deposit = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private Pegawai sales;

    public String getAlamatNpwp() {
        return alamatNpwp.get();
    }

    public void setAlamatNpwp(String value) {
        alamatNpwp.set(value);
    }

    public StringProperty alamatNpwpProperty() {
        return alamatNpwp;
    }

    public String getNamaNpwp() {
        return namaNpwp.get();
    }

    public void setNamaNpwp(String value) {
        namaNpwp.set(value);
    }

    public StringProperty namaNpwpProperty() {
        return namaNpwp;
    }

    public String getNoNpwp() {
        return noNpwp.get();
    }

    public void setNoNpwp(String value) {
        noNpwp.set(value);
    }

    public StringProperty noNpwpProperty() {
        return noNpwp;
    }

    public Pegawai getSales() {
        return sales;
    }

    public void setSales(Pegawai sales) {
        this.sales = sales;
    }
    
    public double getDeposit() {
        return deposit.get();
    }

    public void setDeposit(double value) {
        deposit.set(value);
    }

    public DoubleProperty depositProperty() {
        return deposit;
    }


    public double getHutang() {
        return hutang.get();
    }

    public void setHutang(double value) {
        hutang.set(value);
    }

    public DoubleProperty hutangProperty() {
        return hutang;
    }

    public double getLimitHutang() {
        return limitHutang.get();
    }

    public void setLimitHutang(double value) {
        limitHutang.set(value);
    }

    public DoubleProperty limitHutangProperty() {
        return limitHutang;
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

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getKota() {
        return kota.get();
    }

    public void setKota(String value) {
        kota.set(value);
    }

    public StringProperty kotaProperty() {
        return kota;
    }

    public String getNegara() {
        return negara.get();
    }

    public void setNegara(String value) {
        negara.set(value);
    }

    public StringProperty negaraProperty() {
        return negara;
    }

    public String getKodePos() {
        return kodePos.get();
    }

    public void setKodePos(String value) {
        kodePos.set(value);
    }

    public StringProperty kodePosProperty() {
        return kodePos;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getKontakPerson() {
        return kontakPerson.get();
    }

    public void setKontakPerson(String value) {
        kontakPerson.set(value);
    }

    public StringProperty kontakPersonProperty() {
        return kontakPerson;
    }

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }

    public String getNoHandphone() {
        return noHandphone.get();
    }

    public void setNoHandphone(String value) {
        noHandphone.set(value);
    }

    public StringProperty noHandphoneProperty() {
        return noHandphone;
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
