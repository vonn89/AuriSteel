/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.AsetTetap;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewAsetTetapController {

    @FXML
    private AnchorPane penyusutanPane;
    @FXML
    private Label title;
    @FXML
    private Label harga;
    @FXML
    public TextField namaField;
    @FXML
    public DatePicker tglTransaksiPicker;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextArea keteranganField;
    @FXML
    public TextField bulanField;
    @FXML
    public TextField tahunField;
    @FXML
    public TextField hargaField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        allKategori.clear();
        allKategori.add("Tanah");
        allKategori.add("Bangunan");
        allKategori.add("Mesin");
        allKategori.add("Kendaraan");
        allKategori.add("Peralatan Kantor");
        allKategori.add("Lain-lain");
        kategoriCombo.setItems(allKategori);
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for (KategoriKeuangan kk : sistem.getListKategoriKeuangan()) {
            listKeuangan.add(kk.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
        Function.setNumberField(hargaField);
        Function.setNumberField(bulanField);
        Function.setNumberField(tahunField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        tglTransaksiPicker.setConverter(Function.getTglConverter());
        tglTransaksiPicker.setValue(LocalDate.now());
        tglTransaksiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCell(
                LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    public void setPenjualanAset(AsetTetap aset) {
        if (aset != null) {
            title.setText("Penjualan Aset");
            harga.setText("Harga Jual");
            namaField.setText(aset.getNama());
            kategoriCombo.getSelectionModel().select(aset.getKategori());
            keteranganField.setText(aset.getKeterangan());
            namaField.setDisable(true);
            kategoriCombo.setDisable(true);
            keteranganField.setDisable(true);
            stage.setHeight(370);
            penyusutanPane.setVisible(false);
        } else {
            close();
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
