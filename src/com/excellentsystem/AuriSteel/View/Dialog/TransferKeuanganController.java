/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class TransferKeuanganController {

    @FXML
    public DatePicker tglTransaksiPicker;
    @FXML
    public ComboBox<String> keCombo;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField jumlahRpField;
    @FXML
    public ComboBox<String> dariCombo;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        Function.setNumberField(jumlahRpField);
        tglTransaksiPicker.setConverter(Function.getTglConverter());
        tglTransaksiPicker.setValue(LocalDate.now());
        tglTransaksiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCell(
                LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for (KategoriKeuangan kk : sistem.getListKategoriKeuangan()) {
            listKeuangan.add(kk.getKodeKeuangan());
        }
        dariCombo.setItems(listKeuangan);
        keCombo.setItems(listKeuangan);
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
