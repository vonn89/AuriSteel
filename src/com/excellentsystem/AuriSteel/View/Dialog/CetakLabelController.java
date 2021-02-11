/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class CetakLabelController {

    @FXML
    private TextField namaBarangField;
    @FXML
    private TextField keteranganField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField jumlahField;

    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        jumlahField.setOnKeyReleased((event) -> {
            try {
                jumlahField.setText(df.format(Double.parseDouble(jumlahField.getText().replaceAll(",", ""))));
                jumlahField.end();
            } catch (Exception e) {
                jumlahField.undo();
            }
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setBarang(String kodeBarang) {
        namaBarangField.setText(kodeBarang);
    }

    @FXML
    private void cetakLabel() {
        try {
            Report report = new Report();
            report.printLabel(namaBarangField.getText().toUpperCase(), 
                    keteranganField.getText().toUpperCase(), qtyField.getText(), Integer.parseInt(jumlahField.getText()));
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
