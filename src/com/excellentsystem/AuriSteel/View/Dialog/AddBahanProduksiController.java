/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class AddBahanProduksiController {

    @FXML
    public ComboBox<String> kodeKategoriCombo;
    @FXML
    public TextField kodeBahanField;
    @FXML
    public TextField namaBahanField;
    @FXML
    public TextArea spesifikasiField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField beratKotorField;
    @FXML
    public TextField beratBersihField;
    @FXML
    public TextField panjangField;

    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private ObservableList<String> kategori = FXCollections.observableArrayList();

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
        kodeKategoriCombo.setItems(kategori);
        List<KategoriBahan> listKategori = sistem.getListKategoriBahan();
        kategori.clear();
        for (KategoriBahan k : listKategori) {
            kategori.add(k.getKodeKategori());
        }
        kodeKategoriCombo.getSelectionModel().clearSelection();
    }

    public void setBahan(ProduksiDetailBahan detail) {
        kodeKategoriCombo.getSelectionModel().clearSelection();
        kodeBahanField.setText("");
        namaBahanField.setText("");
        spesifikasiField.setText("");
        keteranganField.setText("");
        beratKotorField.setText("0");
        beratBersihField.setText("0");
        panjangField.setText("0");
        if (detail != null) {
            kodeKategoriCombo.getSelectionModel().select(detail.getBahan().getKodeKategori());
            kodeBahanField.setText(detail.getBahan().getKodeBahan());
            namaBahanField.setText(detail.getBahan().getNamaBahan());
            spesifikasiField.setText(detail.getBahan().getSpesifikasi());
            keteranganField.setText(detail.getBahan().getKeterangan());
            beratKotorField.setText(df.format(detail.getBahan().getBeratKotor()));
            beratBersihField.setText(df.format(detail.getBahan().getBeratBersih()));
            panjangField.setText(df.format(detail.getBahan().getPanjang()));
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
