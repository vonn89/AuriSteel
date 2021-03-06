/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.PemesananBahanHead;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import java.sql.Connection;
import java.text.ParseException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailTerimaPembayaranDownPaymentController {

    @FXML
    public TableView<Hutang> hutangTable;
    @FXML
    private TableColumn<Hutang, String> noHutangColumn;
    @FXML
    private TableColumn<Hutang, String> tglHutangColumn;
    @FXML
    private TableColumn<Hutang, Number> jumlahPembayaranColumn;
    @FXML
    private TableColumn<Hutang, String> tipeKeuanganColumn;

    @FXML
    private TextField noPemesananField;
    @FXML
    private TextField tglPemesananField;
    @FXML
    private TextField totalPemesananField;
    @FXML
    private Label totalDownPaymentLabel;
    @FXML
    private Label sisaPembayaranLabel;

    private ObservableList<Hutang> listHutang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        noHutangColumn.setCellValueFactory(cellData -> cellData.getValue().noHutangProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tglHutangColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHutang())));
            } catch (ParseException ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahHutangProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell());
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        hutangTable.setItems(listHutang);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setDetailPemesanan(PemesananBarangHead p) {
        Task<List<Hutang>> task = new Task<List<Hutang>>() {
            @Override
            public List<Hutang> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                            con, "Terima Pembayaran Down Payment", p.getNoPemesanan(), "%");
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try {
                mainApp.closeLoading();
                p.setListHutang(task.getValue());
                for (Hutang h : p.getListHutang()) {
                    h.setPemesananHead(p);
                }
                noPemesananField.setText(p.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(p.getTglPemesanan())));
                totalPemesananField.setText(df.format(p.getTotalPemesanan()));
                totalDownPaymentLabel.setText(df.format(p.getDownPayment()));
                sisaPembayaranLabel.setText(df.format(p.getTotalPemesanan() - p.getDownPayment()));
                listHutang.clear();
                listHutang.addAll(p.getListHutang());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    public void setDetailPemesananCoil(PemesananBahanHead p) {
        Task<List<Hutang>> task = new Task<List<Hutang>>() {
            @Override
            public List<Hutang> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                            con, "Terima Pembayaran Down Payment", p.getNoPemesanan(), "%");
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try {
                mainApp.closeLoading();
                p.setListHutang(task.getValue());
                for (Hutang h : p.getListHutang()) {
                    h.setPemesananBahanHead(p);
                }
                noPemesananField.setText(p.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(p.getTglPemesanan())));
                totalPemesananField.setText(df.format(p.getTotalPemesanan()));
                totalDownPaymentLabel.setText(df.format(p.getDownPayment()));
                sisaPembayaranLabel.setText(df.format(p.getTotalPemesanan() - p.getDownPayment()));
                listHutang.clear();
                listHutang.addAll(p.getListHutang());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
