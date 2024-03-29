/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPengirimanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PengirimanBarangController {

    @FXML
    private TableView<PenjualanBarangHead> pengirimanTable;
    @FXML
    private TableColumn<PenjualanBarangHead, String> noPengirimanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> tglPengirimanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> noPemesananColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> gudangColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> alamatCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> tujuanKirimColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> supirColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> salesColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, Number> tonaseColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private ComboBox<String> groupByCombo;

    private ObservableList<PenjualanBarangHead> allPengiriman = FXCollections.observableArrayList();
    private ObservableList<PenjualanBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPengirimanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        noPengirimanColumn.setCellFactory(col -> Function.getWrapTableCell(noPengirimanColumn));

        tglPengirimanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPengiriman())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPengirimanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPengirimanColumn));
        tglPengirimanColumn.setComparator(Function.sortDate(tglLengkap));

        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));

        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));

        tujuanKirimColumn.setCellValueFactory(cellData -> cellData.getValue().tujuanKirimProperty());
        tujuanKirimColumn.setCellFactory(col -> Function.getWrapTableCell(tujuanKirimColumn));

        supirColumn.setCellValueFactory(cellData -> cellData.getValue().supirProperty());
        supirColumn.setCellFactory(col -> Function.getWrapTableCell(supirColumn));

        salesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        salesColumn.setCellFactory(col -> Function.getWrapTableCell(salesColumn));

        tonaseColumn.setCellValueFactory(cellData -> {
            double tonase = 0;
            for (PenjualanBarangDetail d : cellData.getValue().getListPenjualanBarangDetail()) {
                tonase = tonase + (d.getQty() * d.getBarang().getBerat());
            }
            return new SimpleDoubleProperty(tonase);
        });
        tonaseColumn.setCellFactory(col -> Function.getTableCell());

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pengiriman");
        addNew.setOnAction((ActionEvent e) -> {
            newPengiriman();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPengiriman();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Pengiriman") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        pengirimanTable.setContextMenu(rm);
        pengirimanTable.setRowFactory((TableView<PenjualanBarangHead> tableView) -> {
            final TableRow<PenjualanBarangHead> row = new TableRow<PenjualanBarangHead>() {
                @Override
                public void updateItem(PenjualanBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pengiriman");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPengiriman();
                        });
                        MenuItem detail = new MenuItem("Detail Pengiriman");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPengiriman(item);
                        });
                        MenuItem verifikasi = new MenuItem("Verifikasi Pengiriman");
                        verifikasi.setOnAction((ActionEvent e) -> {
                            verifikasiPengiriman(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pengiriman");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPengiriman(item);
                        });
                        MenuItem suratJalan = new MenuItem("Print Surat Jalan");
                        suratJalan.setOnAction((ActionEvent e) -> {
                            printSuratJalan(item);
                        });
                        MenuItem invoiceKendal = new MenuItem("Print Invoice Kendal");
                        invoiceKendal.setOnAction((ActionEvent e) -> {
                            printInvoiceKendal(item);
                        });
                        MenuItem invoiceTerboyo = new MenuItem("Print Invoice Terboyo");
                        invoiceTerboyo.setOnAction((ActionEvent e) -> {
                            printInvoiceTerboyo(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPengiriman();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Pengiriman") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Pengiriman") && o.isStatus()) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Verifikasi Pengiriman") && o.isStatus() && item.getStatus().equals("open")) {
                                rm.getItems().add(verifikasi);
                            }
                            if (o.getJenis().equals("Batal Pengiriman") && o.isStatus() && !item.getStatus().equals("false")) {
                                rm.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Surat Jalan") && o.isStatus() && !item.getStatus().equals("false")) {
                                rm.getItems().add(suratJalan);
                            }
                            if (o.getJenis().equals("Print Invoice") && o.isStatus() && !item.getStatus().equals("false")) {
                                rm.getItems().addAll(invoiceKendal,invoiceTerboyo);
                            }
                            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                                rm.getItems().add(export);
                            }
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Pengiriman") && o.isStatus()) {
                                lihatDetailPengiriman(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allPengiriman.addListener((ListChangeListener.Change<? extends PenjualanBarangHead> change) -> {
            searchPengiriman();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPengiriman();
                });
        filterData.addAll(allPengiriman);
        pengirimanTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Wait");
        groupBy.add("Done");
        groupBy.add("Cancel");
        groupBy.add("Semua");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Wait");
        getPengiriman();
    }

    @FXML
    private void getPengiriman() {
        Task<List<PenjualanBarangHead>> task = new Task<List<PenjualanBarangHead>>() {
            @Override
            public List<PenjualanBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Done")) {
                        status = "true";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Wait")) {
                        status = "open";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Cancel")) {
                        status = "false";
                    }
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    List<Barang> allBarang = BarangDAO.getAllByStatus(con, "%");
                    List<PenjualanBarangDetail> allPengirimanDetail = PenjualanBarangDetailDAO.getAllByTglKirimAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    List<PenjualanBarangHead> allPengiriman = PenjualanBarangHeadDAO.getAllByTglKirimAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    for (PenjualanBarangHead h : allPengiriman) {
                        for (Customer c : allCustomer) {
                            if (h.getKodeCustomer().equals(c.getKodeCustomer())) {
                                h.setCustomer(c);
                            }
                        }
                        for (Customer c : allCustomer) {
                            if (h.getKodeCustomerInvoice().equals(c.getKodeCustomer())) {
                                h.setCustomerInvoice(c);
                            }
                        }
                        for (Pegawai p : allSales) {
                            if (h.getKodeSales().equals(p.getKodePegawai())) {
                                h.setSales(p);
                            }
                        }
                        List<PenjualanBarangDetail> listDetail = new ArrayList<>();
                        for (PenjualanBarangDetail d : allPengirimanDetail) {
                            if (d.getNoPenjualan().equals(h.getNoPenjualan())) {
                                for (Barang b : allBarang) {
                                    if (b.getKodeBarang().equals(d.getKodeBarang())) {
                                        d.setBarang(b);
                                    }
                                }
                                listDetail.add(d);
                            }
                        }
                        h.setListPenjualanBarangDetail(listDetail);
                    }
                    return allPengiriman;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPengiriman.clear();
            allPengiriman.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            task.getException().printStackTrace();
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchPengiriman() {
        try {
            filterData.clear();
            for (PenjualanBarangHead temp : allPengiriman) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenjualan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPengiriman())))
                            || checkColumn(temp.getNoPemesanan())
                            || checkColumn(temp.getSales().getNama())
                            || checkColumn(temp.getCustomer().getNama())
                            || checkColumn(temp.getCustomer().getAlamat())
                            || checkColumn(temp.getCustomer().getKota())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getTujuanKirim())
                            || checkColumn(temp.getSupir())
                            || checkColumn(temp.getCatatan())) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }

    private void newPengiriman() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengiriman.fxml");
        NewPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewPengiriman();
        controller.saveButton.setOnAction((event) -> {
            if (controller.pemesanan == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Pemesanan belum dipilih");
            } else if (controller.gudangCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
            } else if (controller.allPenjualanDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PenjualanBarangHead pengiriman = new PenjualanBarangHead();
                            pengiriman.setPemesananBarangHead(controller.pemesanan);
                            pengiriman.setTglPenjualan("2000-01-01 00:00:00");
                            pengiriman.setNoPemesanan(controller.noPemesananField.getText());
                            pengiriman.setKodeCustomer(controller.pemesanan.getKodeCustomer());
                            pengiriman.setKodeCustomerInvoice(controller.pemesanan.getKodeCustomerInvoice());
                            pengiriman.setKodeGudang(controller.gudangCombo.getSelectionModel().getSelectedItem());
                            pengiriman.setTujuanKirim(controller.alamatKirimField.getText());
                            pengiriman.setSupir(controller.namaSupirField.getText());
                            pengiriman.setPaymentTerm(controller.pemesanan.getPaymentTerm());
                            pengiriman.setCatatan(controller.pemesanan.getCatatan());
                            pengiriman.setKodeSales(controller.pemesanan.getKodeSales());
                            pengiriman.setKodeUser("");
                            pengiriman.setTglBatal("2000-01-01 00:00:00");
                            pengiriman.setUserBatal("");
                            pengiriman.setTglPengiriman("2000-01-01 00:00:00");
                            pengiriman.setUserPengiriman(sistem.getUser().getKodeUser());
                            pengiriman.setStatus("open");
                            pengiriman.setTotalBebanPenjualan(0);
                            double total = 0;
                            for (PenjualanBarangDetail temp : controller.allPenjualanDetail) {
                                total = total + temp.getTotal();
                            }
                            pengiriman.setTotalPenjualan(total);
                            pengiriman.setPembayaran(0);
                            pengiriman.setSisaPembayaran(total);
                            pengiriman.setListPenjualanBarangDetail(controller.allPenjualanDetail);
                            return Service.newPengiriman(con, pengiriman);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPengiriman();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pengiriman barang berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((ex) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }

    private void verifikasiPengiriman(PenjualanBarangHead pengiriman) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengiriman.fxml");
        NewPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPengiriman(pengiriman.getNoPenjualan(), true);
        controller.saveButton.setOnAction((event) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.verifikasiPengiriman(con, pengiriman);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPengiriman();
                if (task.getValue().equals("true")) {
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pengiriman barang berhasil disetujui");
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }

    private void batalPengiriman(PenjualanBarangHead pengiriman) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pengiriman barang " + pengiriman.getNoPenjualan() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        if (pengiriman.getStatus().equals("open")) {
                            return Service.batalPengiriman(con, pengiriman);
                        } else if (pengiriman.getStatus().equals("true")) {
                            return Service.batalPenjualan(con, pengiriman);
                        } else {
                            return "Status pengiriman salah";
                        }
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPengiriman();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data pengiriman barang berhasil dibatal");
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }

    private void lihatDetailPengiriman(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengiriman.fxml");
        NewPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPengiriman(p.getNoPenjualan(), false);
    }

    private void printInvoiceKendal(PenjualanBarangHead p) {
        try (Connection con = Koneksi.getConnection()) {
            List<PenjualanBarangDetail> listPenjualan = PenjualanBarangDetailDAO.getAllPenjualanDetail(con, p.getNoPenjualan());
            for (PenjualanBarangDetail d : listPenjualan) {
                d.setPenjualanBarangHead(p);
            }
            Report report = new Report();
            report.printInvoiceSoftcopyKendal(listPenjualan, p.getTotalPenjualan());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void printInvoiceTerboyo(PenjualanBarangHead p) {
        try (Connection con = Koneksi.getConnection()) {
            List<PenjualanBarangDetail> listPenjualan = PenjualanBarangDetailDAO.getAllPenjualanDetail(con, p.getNoPenjualan());
            for (PenjualanBarangDetail d : listPenjualan) {
                d.setPenjualanBarangHead(p);
            }
            Report report = new Report();
            report.printInvoiceSoftcopyTerboyo(listPenjualan, p.getTotalPenjualan());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void printSuratJalan(PenjualanBarangHead p) {
        try (Connection con = Koneksi.getConnection()) {
            List<PenjualanBarangDetail> listPenjualan = PenjualanBarangDetailDAO.getAllPenjualanDetail(con, p.getNoPenjualan());
            for (PenjualanBarangDetail d : listPenjualan) {
                d.setPenjualanBarangHead(p);
                d.setBarang(BarangDAO.get(con, d.getKodeBarang()));
            }
            Report report = new Report();
            report.printSuratJalan(listPenjualan);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void exportExcel() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to export");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls")
            );
            File file = fileChooser.showSaveDialog(mainApp.MainStage);
            if (file != null) {
                Workbook workbook;
                if (file.getPath().endsWith("xlsx")) {
                    workbook = new XSSFWorkbook();
                } else if (file.getPath().endsWith("xls")) {
                    workbook = new HSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
                Sheet sheet = workbook.createSheet("Data Pengiriman Barang");
                int rc = 0;
                int c = 9;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pengiriman");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pengiriman");
                sheet.getRow(rc).getCell(2).setCellValue("No Pemesanan");
                sheet.getRow(rc).getCell(3).setCellValue("Gudang");
                sheet.getRow(rc).getCell(4).setCellValue("Nama");
                sheet.getRow(rc).getCell(5).setCellValue("Alamat");
                sheet.getRow(rc).getCell(6).setCellValue("Tujuan Kirim");
                sheet.getRow(rc).getCell(7).setCellValue("Supir");
                sheet.getRow(rc).getCell(8).setCellValue("Tonase");
                rc++;
                for (PenjualanBarangHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPenjualan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPengiriman())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getNoPemesanan());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getKodeGudang());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getCustomer().getNama());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getCustomer().getAlamat());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getTujuanKirim());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getSupir());
                    double tonase = 0;
                    for (PenjualanBarangDetail d : b.getListPenjualanBarangDetail()) {
                        tonase = tonase + (d.getQty() * d.getBarang().getBerat());
                    }
                    sheet.getRow(rc).getCell(8).setCellValue(tonase);
                    rc++;
                }
                for (int i = 0; i < c; i++) {
                    sheet.autoSizeColumn(i);
                }
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
}
