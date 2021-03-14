/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.PenerimaanBahan;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class NewPenerimaanBahanController {

    @FXML
    private GridPane gridPane;
    @FXML
    public ComboBox<String> gudangCombo;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField kodeBahanField;
    @FXML
    public TextField beratKotorField;
    @FXML
    public TextField beratBersihField;
    @FXML
    public TextField slitField;
    @FXML
    public TextField scraftField;
    @FXML
    public TextField beratTimbanganField;
    @FXML
    public TextField keteranganField;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        Function.setNumberField(beratKotorField);
        Function.setNumberField(beratBersihField);
        Function.setNumberField(slitField);
        Function.setNumberField(scraftField);
        Function.setNumberField(beratTimbanganField);

    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });

        ObservableList<String> allGudang = FXCollections.observableArrayList();
        List<Gudang> listGudang = sistem.getListGudang();
        for (Gudang k : listGudang) {
            allGudang.add(k.getKodeGudang());
        }
        gudangCombo.setItems(allGudang);

        ObservableList<String> allKategori = FXCollections.observableArrayList();
        List<KategoriBahan> listKategoriBahan = sistem.getListKategoriBahan();
        for (KategoriBahan k : listKategoriBahan) {
            allKategori.add(k.getKodeKategori());
        }
        kategoriCombo.setItems(allKategori);
    }

    public void setPenerimaan(PenerimaanBahan p) {
        gridPane.getRowConstraints().get(13).setMinHeight(0);
        gridPane.getRowConstraints().get(13).setPrefHeight(0);
        gridPane.getRowConstraints().get(13).setMaxHeight(0);
        
        gudangCombo.setDisable(true);
        kategoriCombo.setDisable(true);
        kodeBahanField.setDisable(true);
        beratKotorField.setDisable(true);
        beratBersihField.setDisable(true);
        slitField.setDisable(true);
        scraftField.setDisable(true);
        beratTimbanganField.setDisable(true);
        keteranganField.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        
        gudangCombo.getSelectionModel().select(p.getKodeGudang());
        kategoriCombo.getSelectionModel().select(p.getKodeKategori());
        kodeBahanField.setText(p.getKodeBahan());
        beratKotorField.setText(df.format(p.getBeratKotor()));
        beratBersihField.setText(df.format(p.getBeratBersih()));
        slitField.setText(df.format(p.getSlit()));
        scraftField.setText(df.format(p.getScraft()));
        beratTimbanganField.setText(df.format(p.getBeratTimbangan()));
        keteranganField.setText(p.getKeterangan());
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
