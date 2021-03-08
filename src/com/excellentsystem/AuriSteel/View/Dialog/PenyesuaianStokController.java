/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBahanDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBarangDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBahan;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBarang;
import com.excellentsystem.AuriSteel.Model.StokBarang;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PenyesuaianStokController {

    @FXML
    private Label title;
    @FXML
    private Label kodeBarangLabel;
    @FXML
    private Label qtyLabel;
    @FXML
    public TextField kodeBarangField;
    @FXML
    public TextField gudangField;
    @FXML
    public TextField qtyField;
    @FXML
    public TextField nilaiField;
    @FXML
    public ComboBox<String> statusCombo;
    @FXML
    public TextArea catatanField;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    private StokBarang stok;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    private ObservableList<String> allStatus = FXCollections.observableArrayList();

    public void initialize() {
        qtyField.setOnKeyReleased((event) -> {
            try{
                String string = qtyField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        qtyField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                qtyField.end();
            }catch(NumberFormatException e){
                qtyField.undo();
            }
            hitungNilai();
        });
    }

    public void setMainApp(Main main, Stage owner, Stage stage) {
        this.mainApp = main;
        this.owner = owner;
        this.stage = stage;
        Function.setNumberField(nilaiField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        allStatus.clear();
        allStatus.add("Penambahan Stok");
        allStatus.add("Pengurangan Stok");
        statusCombo.setItems(allStatus);
    }

    public void setBarang(StokBarang stokBarang) {
        stok = stokBarang;
        title.setText("Penyesuaian Stok Barang");
        kodeBarangLabel.setText("Barang");
        qtyLabel.setText("Qty");
        kodeBarangField.setText(stokBarang.getKodeBarang());
        gudangField.setText(stokBarang.getKodeGudang());
        qtyField.setText("0");
        nilaiField.setText("0");
    }

    private void hitungNilai() {
        double nilai = 0;
        if (stok.getLogBarang().getStokAkhir() != 0) {
            nilai = stok.getLogBarang().getNilaiAkhir() / stok.getLogBarang().getStokAkhir()
                    * Double.parseDouble(qtyField.getText().replaceAll(",", ""));
        }
        nilaiField.setText(df.format(nilai));
    }

    public void setBahan(String kodeBahan, String kodeGudang) {
        title.setText("Penyesuaian Stok Bahan");
        kodeBarangLabel.setText("Bahan");
        qtyLabel.setText("Berat");
        kodeBarangField.setText(kodeBahan);
        gudangField.setText(kodeGudang);
        qtyField.setText("0");
        nilaiField.setText("0");
    }

    public void setPenyesuaianStokBarang(String noPenyesuaian) {
        try (Connection con = Koneksi.getConnection()) {
            PenyesuaianStokBarang p = PenyesuaianStokBarangDAO.get(con, noPenyesuaian);
            p.setBarang(BarangDAO.get(con, p.getKodeBarang()));

            qtyField.setDisable(true);
            nilaiField.setDisable(true);
            statusCombo.setDisable(true);
            catatanField.setEditable(false);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);

            title.setText("Penyesuaian Stok Barang");
            kodeBarangLabel.setText("Barang");
            qtyLabel.setText("Qty");
            kodeBarangField.setText(p.getKodeBarang());
            gudangField.setText(p.getKodeGudang());
            qtyField.setText("0");
            nilaiField.setText("0");
            if (p.getQty() < 0) {
                statusCombo.getSelectionModel().select("Pengurangan Stok");
                qtyField.setText(df.format(p.getQty() * -1));
                nilaiField.setText(df.format(p.getNilai() * -1));
            } else {
                statusCombo.getSelectionModel().select("Penambahan Stok");
                qtyField.setText(df.format(p.getQty()));
                nilaiField.setText(df.format(p.getNilai()));
            }
            catatanField.setText(p.getCatatan());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void setPenyesuaianStokBahan(String noPenyesuaian) {
        try (Connection con = Koneksi.getConnection()) {
            PenyesuaianStokBahan p = PenyesuaianStokBahanDAO.get(con, noPenyesuaian);
            p.setBahan(BahanDAO.get(con, p.getKodeBahan()));

            qtyField.setDisable(true);
            nilaiField.setDisable(true);
            statusCombo.setDisable(true);
            catatanField.setEditable(false);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);

            setBahan(p.getKodeBahan(), p.getKodeGudang());
            if (p.getQty() < 0) {
                statusCombo.getSelectionModel().select("Pengurangan Stok");
                qtyField.setText(df.format(p.getQty() * -1));
                nilaiField.setText(df.format(p.getNilai() * -1));
            } else {
                statusCombo.getSelectionModel().select("Penambahan Stok");
                qtyField.setText(df.format(p.getQty()));
                nilaiField.setText(df.format(p.getNilai()));
            }
            catatanField.setText(p.getCatatan());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
