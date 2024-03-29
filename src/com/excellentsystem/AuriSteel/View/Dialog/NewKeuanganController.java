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
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import com.excellentsystem.AuriSteel.Model.KategoriTransaksi;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewKeuanganController {

    @FXML
    public TextArea keteranganField;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public DatePicker tglTransaksiPicker;
    @FXML
    public TextField jumlahRpField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private GridPane gridPane;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private ObservableList<String> allKategori = FXCollections.observableArrayList();

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        Function.setNumberField(jumlahRpField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        kategoriCombo.setItems(allKategori);
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for (KategoriKeuangan kk : sistem.getListKategoriKeuangan()) {
            listKeuangan.add(kk.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
        tglTransaksiPicker.setConverter(Function.getTglConverter());
        tglTransaksiPicker.setValue(LocalDate.now());
        tglTransaksiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCell(
                LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    public void setKategoriTransaksi(List<KategoriTransaksi> listKategori) {
        allKategori.clear();
        for (KategoriTransaksi k : listKategori) {
            allKategori.add(k.getKodeKategori());
        }
    }

    public void setDetailKeuangan(Keuangan k) {
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        tglTransaksiPicker.setDisable(true);
        kategoriCombo.setDisable(true);
        keteranganField.setEditable(false);
        jumlahRpField.setDisable(true);
        tipeKeuanganCombo.setDisable(true);

        tglTransaksiPicker.setValue(LocalDate.parse(k.getTglKeuangan(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        kategoriCombo.getSelectionModel().select(k.getKategori());
        keteranganField.setText(k.getDeskripsi());
        jumlahRpField.setText(df.format(k.getJumlahRp()));
        tipeKeuanganCombo.getSelectionModel().select(k.getTipeKeuangan());
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
