/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GantiJadwalDanTujuanController {

    @FXML
    public TextField tujuanKirimField;
    @FXML
    public ComboBox<String> hariCombo;
    @FXML
    public Button saveButton;
    private ObservableList<String> allHari = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        allHari.addAll(Function.hari);
        hariCombo.setItems(allHari);
    }

    public void setRencanaProduksi(RencanaProduksi rp) {
        tujuanKirimField.setText("");
        hariCombo.getSelectionModel().clearSelection();
        if (rp != null) {
            tujuanKirimField.setText(rp.getTujuanKirim());
            hariCombo.getSelectionModel().select(rp.getHari());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
