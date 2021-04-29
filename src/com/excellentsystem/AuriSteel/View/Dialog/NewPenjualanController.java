/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPenjualanController {

    @FXML
    private TableView<PenjualanBarangDetail> penjualanDetailTable;
    @FXML
    private TableColumn<PenjualanBarangDetail, String> kodeBarangColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, String> satuanColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, Number> qtyColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, Number> hargaJualColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, Number> hargaJualPPNColumn;
    @FXML
    private TableColumn<PenjualanBarangDetail, Number> subTotalColumn;

    @FXML
    private GridPane gridPane;
    @FXML
    private Label noPenjualanField;
    @FXML
    private Label tglPenjualanField;

    @FXML
    private TextField namaField;
    @FXML
    private TextArea alamatField;
    @FXML
    private TextField namaSalesField;
    @FXML
    private TextField gudangField;

    @FXML
    private TextField paymentTermField;

    @FXML
    private TextArea catatanField;

    @FXML
    private Label totalQtyField;
    @FXML
    private TextField totalPenjualanField;
    @FXML
    private TextField ppnField;
    @FXML
    public TextField grandtotalField;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    private PenjualanBarangHead penjualan;
    public ObservableList<PenjualanBarangDetail> allPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        hargaJualColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHargaJual() / 1.1));
        hargaJualPPNColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHargaJual()));
        subTotalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal() / 1.1));
        qtyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getQty()));
        hargaJualColumn.setCellFactory(col -> Function.getTableCell());
        hargaJualPPNColumn.setCellFactory(col -> Function.getTableCell());
        subTotalColumn.setCellFactory(col -> Function.getTableCell());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        penjualanDetailTable.setItems(allPenjualanDetail);

        ContextMenu cm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            penjualanDetailTable.refresh();
        });
        cm.getItems().addAll(refresh);
        penjualanDetailTable.setContextMenu(cm);
        penjualanDetailTable.setRowFactory((TableView<PenjualanBarangDetail> tv) -> {
            final TableRow<PenjualanBarangDetail> row = new TableRow<PenjualanBarangDetail>() {
                @Override
                public void updateItem(PenjualanBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem edit = new MenuItem("Edit Barang");
                        edit.setOnAction((ActionEvent e) -> {
                            editBarang(item);
                        });
                        MenuItem delete = new MenuItem("Delete Barang");
                        delete.setOnAction((ActionEvent e) -> {
                            deleteBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            penjualanDetailTable.refresh();
                        });
                        if (saveButton.isVisible()) {
                            rm.getItems().addAll(edit, delete);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
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

    public void setDetailPenjualan(String noPenjualan) {
        Task<String> task = new Task<String>() {
            @Override
            public String call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    penjualan = PenjualanBarangHeadDAO.get(con, noPenjualan);
                    penjualan.setPemesananBarangHead(PemesananBarangHeadDAO.get(con, penjualan.getNoPemesanan()));
                    penjualan.setCustomer(CustomerDAO.get(con, penjualan.getKodeCustomer()));
                    penjualan.setCustomerInvoice(CustomerDAO.get(con, penjualan.getKodeCustomerInvoice()));
                    penjualan.setSales(PegawaiDAO.get(con, penjualan.getKodeSales()));
                    penjualan.setListPenjualanBarangDetail(PenjualanBarangDetailDAO.getAllPenjualanDetail(con, noPenjualan));
                    return "true";
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try {
                mainApp.closeLoading();
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                List<MenuItem> removeMenu = new ArrayList<>();
                for (MenuItem m : penjualanDetailTable.getContextMenu().getItems()) {
                    if (m.getText().equals("Add Barang")) {
                        removeMenu.add(m);
                    }
                }
                penjualanDetailTable.getContextMenu().getItems().removeAll(removeMenu);

                noPenjualanField.setText(penjualan.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualan.getTglPenjualan())));
                namaField.setText(penjualan.getCustomer().getNama());
                alamatField.setText(penjualan.getCustomer().getAlamat());
                gudangField.setText(penjualan.getKodeGudang());
                paymentTermField.setText(penjualan.getPaymentTerm());
                namaSalesField.setText(penjualan.getSales().getNama());
                catatanField.setText(penjualan.getCatatan());
                allPenjualanDetail.addAll(penjualan.getListPenjualanBarangDetail());
                totalPenjualanField.setText(df.format(penjualan.getTotalPenjualan() / 1.1));
                ppnField.setText(df.format(penjualan.getTotalPenjualan() / 1.1 * 0.1));
                grandtotalField.setText(df.format(penjualan.getTotalPenjualan()));
                double qty = 0;
                for (PenjualanBarangDetail d : allPenjualanDetail) {
                    qty = qty + d.getQty();
                }
                totalQtyField.setText(df.format(qty));
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    } 
    public void setRevisiPenjualan(String noPenjualan) {
        Task<String> task = new Task<String>() {
            @Override
            public String call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    penjualan = PenjualanBarangHeadDAO.get(con, noPenjualan);
                    penjualan.setPemesananBarangHead(PemesananBarangHeadDAO.get(con, penjualan.getNoPemesanan()));
                    penjualan.setCustomer(CustomerDAO.get(con, penjualan.getKodeCustomer()));
                    penjualan.setCustomerInvoice(CustomerDAO.get(con, penjualan.getKodeCustomerInvoice()));
                    penjualan.setSales(PegawaiDAO.get(con, penjualan.getKodeSales()));
                    penjualan.setListPenjualanBarangDetail(PenjualanBarangDetailDAO.getAllPenjualanDetail(con, noPenjualan));
                    return "true";
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try {
                mainApp.closeLoading();
                noPenjualanField.setText(penjualan.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualan.getTglPenjualan())));
                namaField.setText(penjualan.getCustomer().getNama());
                alamatField.setText(penjualan.getCustomer().getAlamat());
                gudangField.setText(penjualan.getKodeGudang());
                paymentTermField.setText(penjualan.getPaymentTerm());
                namaSalesField.setText(penjualan.getSales().getNama());
                catatanField.setText(penjualan.getCatatan());
                allPenjualanDetail.addAll(penjualan.getListPenjualanBarangDetail());
                totalPenjualanField.setText(df.format(penjualan.getTotalPenjualan() / 1.1));
                ppnField.setText(df.format(penjualan.getTotalPenjualan() / 1.1 * 0.1));
                grandtotalField.setText(df.format(penjualan.getTotalPenjualan()));
                double qty = 0;
                for (PenjualanBarangDetail d : allPenjualanDetail) {
                    qty = qty + d.getQty();
                }
                totalQtyField.setText(df.format(qty));
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    @FXML
    private void namaInvoice() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NamaInvoice.fxml");
        NamaInvoiceController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setCustomer(penjualan.getCustomerInvoice());
        controller.setViewOnly();
    }

    @FXML
    private void deleteBarang(PenjualanBarangDetail d) {
        allPenjualanDetail.remove(d);
        hitungTotal();
        penjualanDetailTable.refresh();
    }

    @FXML
    private void editBarang(PenjualanBarangDetail detail) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/EditBarang.fxml");
        EditBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.editBarang(detail);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if (controller.barang == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
            } else if (controller.qtyField.getText().equals("0") || controller.qtyField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            } else if (controller.totalField.getText().equals("0") || controller.totalField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            } else if (!sistem.getUser().getLevel().equals("Manager")
                    && controller.barang.getHargaJual() > Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual tidak boleh di bawah batas harga");
            } else {
                mainApp.closeDialog(stage, child);
                detail.setKodeBarang(controller.barang.getKodeBarang());
                detail.setNamaBarang(controller.barang.getNamaBarang());
                detail.setKeterangan(controller.keteranganField.getText());
                detail.setCatatanIntern(controller.catatanInternField.getText());
                detail.setSatuan(controller.barang.getSatuan());
                detail.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                detail.setHargaJual(Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")));
                detail.setTotal(Double.parseDouble(controller.totalField.getText().replaceAll(",", "")));
                hitungTotal();
                penjualanDetailTable.refresh();
            }
        });
    }

    @FXML
    private void hitungTotal() {
        double totalQty = 0;
        double totalPenjualan = 0;
        for (PenjualanBarangDetail temp : allPenjualanDetail) {
            totalPenjualan = totalPenjualan + temp.getTotal();
            totalQty = totalQty + temp.getQty();
        }
        totalPenjualanField.setText(df.format(totalPenjualan / 1.1));
        ppnField.setText(df.format(totalPenjualan / 1.1 * 0.1));
        grandtotalField.setText(df.format(totalPenjualan));
        totalQtyField.setText(df.format(totalQty));
    }

    @FXML
    private void backToDataPenjualan() {
        mainApp.closeDialog(owner, stage);
    }
}
