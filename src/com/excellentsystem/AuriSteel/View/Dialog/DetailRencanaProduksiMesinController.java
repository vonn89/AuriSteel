/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailRencanaProduksiMesinController {

    @FXML
    private TableView<RencanaProduksi> rencanaProduksiTable;
    @FXML
    private TableColumn<RencanaProduksi, Boolean> checkColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> tujuanKirimColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> noPemesananColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> customerColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> barangColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> keteranganColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> catatanInternColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> salesColumn;
    @FXML
    private TableColumn<RencanaProduksi, Number> qtyColumn;
    @FXML
    private TableColumn<RencanaProduksi, Number> tonaseColumn;

    @FXML
    private CheckBox checkAll;
    @FXML
    private Label totalQtyLabel;
    @FXML
    private Label totalTonaseLabel;

    private ObservableList<RencanaProduksi> allRencanaProduksi = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return rencanaProduksiTable.getItems().get(v).statusProperty();
        }));
        tujuanKirimColumn.setCellValueFactory(cellData -> cellData.getValue().tujuanKirimProperty());
        tujuanKirimColumn.setCellFactory(col -> Function.getWrapTableCell(tujuanKirimColumn));

        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));

        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        customerColumn.setCellFactory(col -> Function.getWrapTableCell(customerColumn));

        barangColumn.setCellValueFactory(cellData -> cellData.getValue().barangProperty());
        barangColumn.setCellFactory(col -> Function.getWrapTableCell(barangColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanInternColumn.setCellFactory(col -> Function.getWrapTableCell(catatanInternColumn));

        salesColumn.setCellValueFactory(cellData -> cellData.getValue().salesProperty());
        salesColumn.setCellFactory(col -> Function.getWrapTableCell(salesColumn));

        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());

        tonaseColumn.setCellValueFactory(cellData -> cellData.getValue().tonaseProperty());
        tonaseColumn.setCellFactory(col -> Function.getTableCell());

        rencanaProduksiTable.setItems(allRencanaProduksi);

        final ContextMenu rm = new ContextMenu();
        MenuItem spk = new MenuItem("Print SPK");
        spk.setOnAction((ActionEvent e) -> {
            printSPK();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Print SPK") && o.isStatus()) {
                rm.getItems().add(spk);
            }
        }
        rencanaProduksiTable.setContextMenu(rm);
        rencanaProduksiTable.setRowFactory((TableView<RencanaProduksi> tableView) -> {
            final TableRow<RencanaProduksi> row = new TableRow<RencanaProduksi>() {
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        if (row.getItem().isStatus()) {
                            row.getItem().setStatus(false);
                        } else {
                            row.getItem().setStatus(true);
                        }
                        hitungTotal();
                    }
                }
            });
            return row;
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    private void checkAllHandle() {
        for (RencanaProduksi d : allRencanaProduksi) {
            d.setStatus(checkAll.isSelected());
        }
        rencanaProduksiTable.refresh();
        hitungTotal();
    }

    private void hitungTotal() {
        double totalQty = 0;
        double totalTonase = 0;
        for (RencanaProduksi d : allRencanaProduksi) {
            if (d.isStatus()) {
                totalQty = totalQty + d.getQty();
                totalTonase = totalTonase + d.getTonase();
            }
        }
        totalQtyLabel.setText(df.format(totalQty));
        totalTonaseLabel.setText(df.format(totalTonase));
    }

    public void setListProduksi(List<RencanaProduksi> x) {
        allRencanaProduksi.clear();
        allRencanaProduksi.addAll(x);
    }

    private void printSPK() {
        try (Connection con = Koneksi.getConnection()) {
            List<PemesananBarangDetail> spk = new ArrayList<>();
            for (RencanaProduksi b : allRencanaProduksi) {
                if (b.isStatus()) {
                    PemesananBarangDetail d = new PemesananBarangDetail();
                    d.setNoPemesanan(b.getNoPemesanan());
                    d.setKodeBarang(b.getBarang());
                    d.setNamaBarang(b.getBarang());
                    d.setKeterangan(b.getKeterangan());
                    d.setCatatanIntern(b.getCatatan());
                    d.setQty(b.getQty());
                    d.setQtyTerkirim(0);

                    PemesananBarangHead head = new PemesananBarangHead();
                    head.setNoPemesanan(b.getNoPemesanan());
                    head.setTglPemesanan(tglSql.format(Function.getServerDate(con)));

                    Customer c = new Customer();
                    c.setNama(b.getCustomer());
                    head.setCustomer(c);

                    d.setPemesananBarangHead(head);
                    spk.add(d);
                }
            }
            if (spk.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Data permintaan barang belum di pilih");
            } else {
                Report report = new Report();
                report.printSPK(spk);
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
