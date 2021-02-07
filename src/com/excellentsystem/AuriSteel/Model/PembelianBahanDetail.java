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
 * @author Xtreme
 */
public class PembelianBahanDetail {
    private final StringProperty noPembelian = new SimpleStringProperty();
    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty noPenerimaan = new SimpleStringProperty();
    
    private final StringProperty kodeBahan = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty noKontrak = new SimpleStringProperty();
    private final StringProperty namaBahan = new SimpleStringProperty();
    
    private final StringProperty spesifikasi = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty nilai = new SimpleDoubleProperty();
    private final DoubleProperty hargaBeli = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private PembelianBahanHead pembelianBahanHead;

    public double getHargaBeli() {
        return hargaBeli.get();
    }

    public void setHargaBeli(double value) {
        hargaBeli.set(value);
    }

    public DoubleProperty hargaBeliProperty() {
        return hargaBeli;
    }

    public double getNilai() {
        return nilai.get();
    }

    public void setNilai(double value) {
        nilai.set(value);
    }

    public DoubleProperty nilaiProperty() {
        return nilai;
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

    public String getNoPenerimaan() {
        return noPenerimaan.get();
    }

    public void setNoPenerimaan(String value) {
        noPenerimaan.set(value);
    }

    public StringProperty noPenerimaanProperty() {
        return noPenerimaan;
    }

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
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

    public String getNoKontrak() {
        return noKontrak.get();
    }

    public void setNoKontrak(String value) {
        noKontrak.set(value);
    }

    public StringProperty noKontrakProperty() {
        return noKontrak;
    }

    
    public PembelianBahanHead getPembelianBahanHead() {
        return pembelianBahanHead;
    }

    public void setPembelianBahanHead(PembelianBahanHead pembelianBahanHead) {
        this.pembelianBahanHead = pembelianBahanHead;
    }
    
  
    public String getKodeBahan() {
        return kodeBahan.get();
    }

    public void setKodeBahan(String value) {
        kodeBahan.set(value);
    }

    public StringProperty kodeBahanProperty() {
        return kodeBahan;
    }

    public String getNamaBahan() {
        return namaBahan.get();
    }

    public void setNamaBahan(String value) {
        namaBahan.set(value);
    }

    public StringProperty namaBahanProperty() {
        return namaBahan;
    }

    public String getSpesifikasi() {
        return spesifikasi.get();
    }

    public void setSpesifikasi(String value) {
        spesifikasi.set(value);
    }

    public StringProperty spesifikasiProperty() {
        return spesifikasi;
    }


    
    public double getTotal() {
        return total.get();
    }

    public void setTotal(double value) {
        total.set(value);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }
    

    public String getNoPembelian() {
        return noPembelian.get();
    }

    public void setNoPembelian(String value) {
        noPembelian.set(value);
    }

    public StringProperty noPembelianProperty() {
        return noPembelian;
    }
    
}
