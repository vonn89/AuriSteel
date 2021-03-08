/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.View.Dialog.DetailTerimaPembayaranDownPaymentController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
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
 * @author Xtreme
 */
public class LaporanPemesananController {

    @FXML
    private TreeTableView<PemesananBarangHead> pemesananTable;
    @FXML
    private TableColumn<PemesananBarangHead, String> noPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> tglPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> alamatCustomerColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> namaInvoiceColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> paymentTermColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> totalPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> sisaPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> downPaymentColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> sisaDownPaymentColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> statusColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> catatanColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> namaSalesColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> kodeUserColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPemesananField;
    @FXML
    private Label sisaPemesananField;
    @FXML
    private Label totalDownPaymentField;
    @FXML
    private Label totalSisaDownPaymentField;
    @FXML
    private DatePicker tglMulaiPemesananPicker;
    @FXML
    private DatePicker tglAkhirPemesananPicker;

    private final TreeItem<PemesananBarangHead> root = new TreeItem<>();
    private ObservableList<PemesananBarangHead> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));

        namaInvoiceColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerInvoice().namaProperty());
        namaInvoiceColumn.setCellFactory(col -> Function.getWrapTableCell(namaInvoiceColumn));

        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));

        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));

        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        statusColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStatus().equals("close")) {
                return new SimpleStringProperty("Done");
            } else if (cellData.getValue().getStatus().equals("open")) {
                return new SimpleStringProperty("Wait");
            } else if (cellData.getValue().getStatus().equals("false")) {
                return new SimpleStringProperty("Cancel");
            } else if (cellData.getValue().getStatus().equals("wait")) {
                return new SimpleStringProperty("On Review");
            } else {
                return null;
            }
        });
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));

        tglPemesananColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));

        totalPemesananColumn.setCellValueFactory(celldata -> celldata.getValue().totalPemesananProperty());
        totalPemesananColumn.setCellFactory(col -> Function.getTableCell());

        sisaPemesananColumn.setCellValueFactory(celldata -> {
            double sisaPemesanan = 0;
            for (PemesananBarangDetail d : celldata.getValue().getListPemesananBarangDetail()) {
                sisaPemesanan = sisaPemesanan + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
            }
            return new SimpleDoubleProperty(sisaPemesanan);
        });
        sisaPemesananColumn.setCellFactory(col -> Function.getTableCell());

        downPaymentColumn.setCellValueFactory(celldata -> celldata.getValue().downPaymentProperty());
        downPaymentColumn.setCellFactory(col -> Function.getTableCell());

        sisaDownPaymentColumn.setCellValueFactory(celldata -> celldata.getValue().sisaDownPaymentProperty());
        sisaDownPaymentColumn.setCellFactory(col -> Function.getTableCell());

        tglMulaiPemesananPicker.setConverter(Function.getTglConverter());
        tglMulaiPemesananPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPemesananPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPemesananPicker));
        tglAkhirPemesananPicker.setConverter(Function.getTglConverter());
        tglAkhirPemesananPicker.setValue(LocalDate.now());
        tglAkhirPemesananPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPemesananPicker));

        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananBarangHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPemesanan();
                });
        filterData.addAll(allPemesanan);

        final ContextMenu rm = new ContextMenu();
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPemesanan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().addAll(export);
            }
        }
        rm.getItems().addAll(refresh);
        pemesananTable.setContextMenu(rm);
        pemesananTable.setRowFactory((TreeTableView<PemesananBarangHead> tableView) -> {
            final TreeTableRow<PemesananBarangHead> row = new TreeTableRow<PemesananBarangHead>() {
                @Override
                public void updateItem(PemesananBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pemesanan");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPemesanan(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Terima Pembayaran DP");
                        pembayaran.setOnAction((ActionEvent e) -> {
                            lihatTerimaPembayaranDownPayment(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPemesanan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Pemesanan") && o.isStatus()
                                    && item.getStatus() != null) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Detail Terima Pembayaran DP") && o.isStatus()
                                    && item.getDownPayment() > 0 && item.getStatus() != null) {
                                rm.getItems().add(pembayaran);
                            }
                            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                                rm.getItems().addAll(export);
                            }
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("Customer");
        groupBy.add("Sales");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Customer");
        getPemesanan();
    }

    @FXML
    private void getPemesanan() {
        Task<List<PemesananBarangHead>> task = new Task<List<PemesananBarangHead>>() {
            @Override
            public List<PemesananBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PemesananBarangHead> allPemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(con,
                            tglMulaiPemesananPicker.getValue().toString(), tglAkhirPemesananPicker.getValue().toString(), "true");
                    List<PemesananBarangDetail> allDetail = PemesananBarangDetailDAO.getAllByDateAndStatus(con,
                            tglMulaiPemesananPicker.getValue().toString(), tglAkhirPemesananPicker.getValue().toString(), "true");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    for (PemesananBarangHead p : allPemesanan) {
                        for (Customer c : allCustomer) {
                            if (p.getKodeCustomer().equals(c.getKodeCustomer())) {
                                p.setCustomer(c);
                            }
                        }
                        for (Pegawai s : allSales) {
                            if (p.getKodeSales().equals(s.getKodePegawai())) {
                                p.setSales(s);
                            }
                        }
                        List<PemesananBarangDetail> detail = new ArrayList<>();
                        for (PemesananBarangDetail d : allDetail) {
                            if (p.getNoPemesanan().equals(d.getNoPemesanan())) {
                                detail.add(d);
                            }
                        }
                        p.setListPemesananBarangDetail(detail);
                    }
                    return allPemesanan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
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

    private void searchPemesanan() {
        try {
            filterData.clear();
            for (PemesananBarangHead temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPemesanan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPemesanan())))
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(temp.getKodeCustomer())
                            || checkColumn(temp.getCustomer().getNama())
                            || checkColumn(temp.getKodeSales())
                            || checkColumn(temp.getSales().getNama())
                            || checkColumn(df.format(temp.getDownPayment()))
                            || checkColumn(df.format(temp.getTotalPemesanan()))
                            || checkColumn(df.format(temp.getSisaDownPayment()))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getKodeUser())) {
                        filterData.add(temp);
                    }
                }
            }
            setTable();
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void setTable() throws Exception {
        if (pemesananTable.getRoot() != null) {
            pemesananTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PemesananBarangHead temp : filterData) {
            String group = "";
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                group = tgl.format(tglSql.parse(temp.getTglPemesanan()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                group = temp.getSales().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")) {
                group = temp.getCustomer().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getTglPemesanan()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getTglPemesanan()));
            }
            if (!groupBy.contains(group)) {
                groupBy.add(group);
            }
        }
        for (String temp : groupBy) {
            PemesananBarangHead head = new PemesananBarangHead();
            head.setNoPemesanan(temp);
            head.setCustomer(new Customer());
            head.setSales(new Pegawai());
            TreeItem<PemesananBarangHead> parent = new TreeItem<>(head);
            double totalPemesanan = 0;
            double totalPembayaran = 0;
            double sisaPembayaran = 0;
            for (PemesananBarangHead pj : filterData) {
                boolean status = false;
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                        && temp.equals(tgl.format(tglSql.parse(pj.getTglPemesanan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                        && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(pj.getTglPemesanan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                        && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(pj.getTglPemesanan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")
                        && temp.equals(pj.getSales().getNama())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")
                        && temp.equals(pj.getCustomer().getNama())) {
                    status = true;
                }
                if (status) {
                    totalPemesanan = totalPemesanan + pj.getTotalPemesanan();
                    totalPembayaran = totalPembayaran + pj.getDownPayment();
                    sisaPembayaran = sisaPembayaran + pj.getSisaDownPayment();
                    parent.getChildren().addAll(new TreeItem<>(pj));
                }
            }
            parent.getValue().setTotalPemesanan(totalPemesanan);
            parent.getValue().setDownPayment(totalPembayaran);
            parent.getValue().setSisaDownPayment(sisaPembayaran);
            root.getChildren().add(parent);
        }
        pemesananTable.setRoot(root);
    }

    private void hitungTotal() {
        double totalPemesanan = 0;
        double sisaPemesanan = 0;
        double totalPembayaran = 0;
        double sisaPembayaran = 0;
        for (PemesananBarangHead temp : filterData) {
            totalPemesanan = totalPemesanan + temp.getTotalPemesanan();
            for (PemesananBarangDetail d : temp.getListPemesananBarangDetail()) {
                sisaPemesanan = sisaPemesanan + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
            }
            totalPembayaran = totalPembayaran + temp.getDownPayment();
            sisaPembayaran = sisaPembayaran + temp.getSisaDownPayment();
        }
        totalPemesananField.setText(df.format(totalPemesanan));
        sisaPemesananField.setText(df.format(sisaPemesanan));
        totalDownPaymentField.setText(df.format(totalPembayaran));
        totalSisaDownPaymentField.setText(df.format(sisaPembayaran));
    }

    private void lihatDetailPemesanan(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p.getNoPemesanan());
    }

    private void lihatTerimaPembayaranDownPayment(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaPembayaranDownPayment.fxml");
        DetailTerimaPembayaranDownPaymentController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p);
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
                Sheet sheet = workbook.createSheet("Laporan Pemesanan");
                int rc = 0;
                int c = 10;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPemesananPicker.getValue().toString())) + " - "
                        + tgl.format(tglBarang.parse(tglAkhirPemesananPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Group By : " + groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pemesanan");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pemesanan");
                sheet.getRow(rc).getCell(2).setCellValue("Customer");
                sheet.getRow(rc).getCell(3).setCellValue("Sales");
                sheet.getRow(rc).getCell(4).setCellValue("Total Pemesanan");
                sheet.getRow(rc).getCell(5).setCellValue("Sisa Pemesanan");
                sheet.getRow(rc).getCell(6).setCellValue("Pembayaran Down Payment");
                sheet.getRow(rc).getCell(7).setCellValue("Sisa Pembayaran Down Payment");
                rc++;

                List<String> groupBy = new ArrayList<>();
                for (PemesananBarangHead temp : filterData) {
                    String group = "";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                        group = tgl.format(tglSql.parse(temp.getTglPemesanan()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                        group = temp.getSales().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")) {
                        group = temp.getCustomer().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getTglPemesanan()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getTglPemesanan()));
                    }
                    if (!groupBy.contains(group)) {
                        groupBy.add(group);
                    }
                }
                double grandtotalPemesanan = 0;
                double grandtotalSisaPemesanan = 0;
                double grandtotalPembayaran = 0;
                double grandsisaPembayaran = 0;
                for (String temp : groupBy) {
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalPemesanan = 0;
                    double sisaPemesanan = 0;
                    double totalPembayaran = 0;
                    double sisaPembayaran = 0;
                    for (PemesananBarangHead p : filterData) {
                        boolean status = false;
                        if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                                && temp.equals(tgl.format(tglSql.parse(p.getTglPemesanan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                                && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(p.getTglPemesanan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                                && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(p.getTglPemesanan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")
                                && temp.equals(p.getSales().getNama())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")
                                && temp.equals(p.getCustomer().getNama())) {
                            status = true;
                        }
                        if (status) {
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(p.getNoPemesanan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(p.getTglPemesanan())));
                            sheet.getRow(rc).getCell(2).setCellValue(p.getCustomer().getNama());
                            sheet.getRow(rc).getCell(3).setCellValue(p.getSales().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(p.getTotalPemesanan());
                            double x = 0;
                            for (PemesananBarangDetail d : p.getListPemesananBarangDetail()) {
                                x = x + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
                            }
                            sheet.getRow(rc).getCell(5).setCellValue(x);
                            sheet.getRow(rc).getCell(6).setCellValue(p.getDownPayment());
                            sheet.getRow(rc).getCell(7).setCellValue(p.getSisaDownPayment());
                            rc++;
                            totalPemesanan = totalPemesanan + p.getTotalPemesanan();
                            sisaPemesanan = sisaPemesanan + x;
                            totalPembayaran = totalPembayaran + p.getDownPayment();
                            sisaPembayaran = sisaPembayaran + p.getSisaDownPayment();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total " + temp);
                    sheet.getRow(rc).getCell(4).setCellValue(totalPemesanan);
                    sheet.getRow(rc).getCell(5).setCellValue(sisaPemesanan);
                    sheet.getRow(rc).getCell(6).setCellValue(totalPembayaran);
                    sheet.getRow(rc).getCell(7).setCellValue(sisaPembayaran);
                    rc++;
                    grandtotalPemesanan = grandtotalPemesanan + totalPemesanan;
                    grandtotalSisaPemesanan = grandtotalSisaPemesanan + sisaPemesanan;
                    grandtotalPembayaran = grandtotalPembayaran + totalPembayaran;
                    grandsisaPembayaran = grandsisaPembayaran + sisaPembayaran;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(4).setCellValue(grandtotalPemesanan);
                sheet.getRow(rc).getCell(5).setCellValue(grandtotalSisaPemesanan);
                sheet.getRow(rc).getCell(6).setCellValue(grandtotalPembayaran);
                sheet.getRow(rc).getCell(7).setCellValue(grandsisaPembayaran);
                rc++;
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
