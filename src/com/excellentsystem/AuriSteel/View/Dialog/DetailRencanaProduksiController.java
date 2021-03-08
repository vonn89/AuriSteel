/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailRencanaProduksiController {

    @FXML
    public TextField tujuanKirimField;
    @FXML
    private TextField noPemesananField;
    @FXML
    private TextField customerField;
    @FXML
    private TextField barangField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField catatanField;
    @FXML
    public TextField qtyField;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        Function.setNumberField(qtyField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setRencanaProduksi(RencanaProduksi rp) {
        tujuanKirimField.setText("");
        noPemesananField.setText("");
        customerField.setText("");
        barangField.setText("");
        keteranganField.setText("");
        catatanField.setText("");
        qtyField.setText("0");
        if (rp != null) {
            tujuanKirimField.setText(rp.getTujuanKirim());
            noPemesananField.setText(rp.getNoPemesanan());
            customerField.setText(rp.getCustomer());
            barangField.setText(rp.getBarang());
            keteranganField.setText(rp.getKeterangan());
            catatanField.setText(rp.getCatatan());
            qtyField.setText(df.format(rp.getQty()));
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
