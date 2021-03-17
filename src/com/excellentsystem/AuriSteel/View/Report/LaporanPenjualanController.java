/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
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
public class LaporanPenjualanController {

    @FXML
    private TreeTableView<PenjualanBarangHead> penjualanTable;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> noPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> tglPengirimanColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> tglPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> gudangColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> namaCustomerColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> alamatCustomerColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> namaInvoiceColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> supirColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> paymentTermColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, Number> totalPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, Number> totalBebanPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, Number> pembayaranColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, Number> sisaPembayaranColumn;
    @FXML
    private TreeTableColumn<PenjualanBarangHead, String> namaSalesColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPenjualanField;
    @FXML
    private Label totalPembayaranField;
    @FXML
    private Label sisaPembayaranField;
    @FXML
    private DatePicker tglMulaiPenjualanPicker;
    @FXML
    private DatePicker tglAkhirPenjualanPicker;

    private final TreeItem<PenjualanBarangHead> root = new TreeItem<>();
    private ObservableList<PenjualanBarangHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        noPenjualanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPenjualanColumn));

        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(gudangColumn));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaCustomerColumn));

        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTreeTableCell(alamatCustomerColumn));

        namaInvoiceColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getCustomerInvoice().namaProperty());
        namaInvoiceColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaInvoiceColumn));

        supirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().supirProperty());
        supirColumn.setCellFactory(col -> Function.getWrapTreeTableCell(supirColumn));
        
        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTreeTableCell(paymentTermColumn));
        
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSalesColumn));

        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPenjualanColumn));
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));

        tglPengirimanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglPengiriman())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPengirimanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPengirimanColumn));
        tglPengirimanColumn.setComparator(Function.sortDate(tglLengkap));
        
        totalBebanPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalBebanPenjualanProperty());
        totalBebanPenjualanColumn.setCellFactory(col -> Function.getTreeTableCell());

        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> Function.getTreeTableCell());

        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());

        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());

        tglMulaiPenjualanPicker.setConverter(Function.getTglConverter());
        tglMulaiPenjualanPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPenjualanPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPenjualanPicker));
        tglAkhirPenjualanPicker.setConverter(Function.getTglConverter());
        tglAkhirPenjualanPicker.setValue(LocalDate.now());
        tglAkhirPenjualanPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPenjualanPicker));

        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanBarangHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenjualan();
                });
        filterData.addAll(allPenjualan);

        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Print Laporan") && o.isStatus()) {
                rm.getItems().addAll(print);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().addAll(export);
            }
        }
        rm.getItems().addAll(refresh);
        penjualanTable.setContextMenu(rm);
        penjualanTable.setRowFactory((TreeTableView<PenjualanBarangHead> tableView) -> {
            final TreeTableRow<PenjualanBarangHead> row = new TreeTableRow<PenjualanBarangHead>() {
                @Override
                public void updateItem(PenjualanBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPenjualan(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan");
                        pembayaran.setOnAction((ActionEvent e) -> {
                            showDetailPiutang(item);
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent event) -> {
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Penjualan") && o.isStatus()
                                    && item.getStatus() != null) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Detail Pembayaran Penjualan") && o.isStatus()
                                    && item.getPembayaran() > 0 && item.getStatus() != null) {
                                rm.getItems().add(pembayaran);
                            }
                            if (o.getJenis().equals("Print Laporan") && o.isStatus()) {
                                rm.getItems().addAll(print);
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
        groupBy.add("Gudang");
        groupBy.add("Sales");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Customer");
        getPenjualan();
    }

    @FXML
    private void getPenjualan() {
        Task<List<PenjualanBarangHead>> task = new Task<List<PenjualanBarangHead>>() {
            @Override
            public List<PenjualanBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanBarangHead> allPenjualan = PenjualanBarangHeadDAO.getAllByDateAndStatus(con,
                            tglMulaiPenjualanPicker.getValue().toString(), tglAkhirPenjualanPicker.getValue().toString(), "true");
                    List<PenjualanBarangDetail> allDetail = PenjualanBarangDetailDAO.getAllByDateAndStatus(con,
                            tglMulaiPenjualanPicker.getValue().toString(), tglAkhirPenjualanPicker.getValue().toString(), "true");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    for (PenjualanBarangHead p : allPenjualan) {
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
                        List<PenjualanBarangDetail> detail = new ArrayList<>();
                        for (PenjualanBarangDetail d : allDetail) {
                            if (p.getNoPenjualan().equals(d.getNoPenjualan())) {
                                detail.add(d);
                            }
                        }
                        p.setListPenjualanBarangDetail(detail);
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
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

    private void searchPenjualan() {
        try {
            filterData.clear();
            for (PenjualanBarangHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenjualan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPengiriman())))
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeCustomer())
                            || checkColumn(temp.getCustomer().getNama())
                            || checkColumn(temp.getCustomer().getAlamat())
                            || checkColumn(temp.getCustomerInvoice().getNama())
                            || checkColumn(temp.getSupir())
                            || checkColumn(temp.getKodeSales())
                            || checkColumn(temp.getSales().getNama())
                            || checkColumn(df.format(temp.getPembayaran()))
                            || checkColumn(df.format(temp.getTotalPenjualan()))
                            || checkColumn(df.format(temp.getTotalBebanPenjualan()))
                            || checkColumn(df.format(temp.getSisaPembayaran()))
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
        if (penjualanTable.getRoot() != null) {
            penjualanTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PenjualanBarangHead temp : filterData) {
            String group = "";
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                group = tgl.format(tglSql.parse(temp.getTglPenjualan()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                group = temp.getKodeGudang();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                group = temp.getSales().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")) {
                group = temp.getCustomer().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getTglPenjualan()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getTglPenjualan()));
            }
            if (!groupBy.contains(group)) {
                groupBy.add(group);
            }
        }
        for (String temp : groupBy) {
            PenjualanBarangHead head = new PenjualanBarangHead();
            head.setNoPenjualan(temp);
            head.setCustomer(new Customer());
            head.setSales(new Pegawai());
            TreeItem<PenjualanBarangHead> parent = new TreeItem<>(head);
            double totalPenjualan = 0;
            double totalBebanPenjualan = 0;
            double totalPembayaran = 0;
            double sisaPembayaran = 0;
            for (PenjualanBarangHead pj : filterData) {
                boolean status = false;
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                        && temp.equals(tgl.format(tglSql.parse(pj.getTglPenjualan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                        && temp.equals(pj.getKodeGudang())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                        && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(pj.getTglPenjualan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                        && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(pj.getTglPenjualan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")
                        && temp.equals(pj.getSales().getNama())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")
                        && temp.equals(pj.getCustomer().getNama())) {
                    status = true;
                }
                if (status) {
                    totalPenjualan = totalPenjualan + pj.getTotalPenjualan();
                    totalBebanPenjualan = totalBebanPenjualan + pj.getTotalBebanPenjualan();
                    totalPembayaran = totalPembayaran + pj.getPembayaran();
                    sisaPembayaran = sisaPembayaran + pj.getSisaPembayaran();
                    parent.getChildren().addAll(new TreeItem<>(pj));
                }
            }
            parent.getValue().setTotalPenjualan(totalPenjualan);
            parent.getValue().setTotalBebanPenjualan(totalBebanPenjualan);
            parent.getValue().setPembayaran(totalPembayaran);
            parent.getValue().setSisaPembayaran(sisaPembayaran);
            root.getChildren().add(parent);
        }
        penjualanTable.setRoot(root);
    }

    private void hitungTotal() {
        double totalPenjualan = 0;
        double totalPembayaran = 0;
        double sisaPembayaran = 0;
        for (PenjualanBarangHead temp : filterData) {
            totalPenjualan = totalPenjualan + temp.getTotalPenjualan();
            totalPembayaran = totalPembayaran + temp.getPembayaran();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(totalPenjualan));
        totalPembayaranField.setText(df.format(totalPembayaran));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
    }

    private void lihatDetailPenjualan(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }

    private void showDetailPiutang(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetailPenjualan(p);
    }

    private void print() {
        try {
            Report report = new Report();
            report.printLaporanPenjualan(allPenjualan, tglMulaiPenjualanPicker.getValue().toString(),
                    tglAkhirPenjualanPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
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
                Sheet sheet = workbook.createSheet("Laporan Penjualan");
                int rc = 0;
                int c = 15;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPenjualanPicker.getValue().toString())) + " - "
                        + tgl.format(tglBarang.parse(tglAkhirPenjualanPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Group By : " + groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penjualan");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pengiriman");
                sheet.getRow(rc).getCell(2).setCellValue("Tgl Penjualan");
                sheet.getRow(rc).getCell(3).setCellValue("Gudang");
                sheet.getRow(rc).getCell(4).setCellValue("Nama Customer");
                sheet.getRow(rc).getCell(5).setCellValue("Alamat Customer");
                sheet.getRow(rc).getCell(6).setCellValue("Nama Invoice");
                sheet.getRow(rc).getCell(7).setCellValue("Payment Term");
                sheet.getRow(rc).getCell(8).setCellValue("Sales");
                sheet.getRow(rc).getCell(9).setCellValue("Total Beban Penjualan");
                sheet.getRow(rc).getCell(10).setCellValue("Total Penjualan");
                sheet.getRow(rc).getCell(11).setCellValue("Pembayaran");
                sheet.getRow(rc).getCell(12).setCellValue("Sisa Pembayaran");
                sheet.getRow(rc).getCell(13).setCellValue("Catatan");
                sheet.getRow(rc).getCell(14).setCellValue("Kode User");
                rc++;

                List<String> groupBy = new ArrayList<>();
                for (PenjualanBarangHead temp : filterData) {
                    String group = "";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                        group = tgl.format(tglSql.parse(temp.getTglPenjualan()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                        group = temp.getKodeGudang();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                        group = temp.getSales().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")) {
                        group = temp.getCustomer().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getTglPenjualan()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getTglPenjualan()));
                    }
                    if (!groupBy.contains(group)) {
                        groupBy.add(group);
                    }
                }
                double grandtotalPenjualan = 0;
                double grandtotalBebanPenjualan = 0;
                double grandtotalPembayaran = 0;
                double grandsisaPembayaran = 0;
                for (String temp : groupBy) {
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalPenjualan = 0;
                    double totalBebanPenjualan = 0;
                    double totalPembayaran = 0;
                    double sisaPembayaran = 0;
                    for (PenjualanBarangHead p : filterData) {
                        boolean status = false;
                        if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                                && temp.equals(tgl.format(tglSql.parse(p.getTglPenjualan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                                && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(p.getTglPenjualan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                                && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(p.getTglPenjualan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                                && temp.equals(p.getKodeGudang())) {
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
                            sheet.getRow(rc).getCell(0).setCellValue(p.getNoPenjualan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(p.getTglPengiriman())));
                            sheet.getRow(rc).getCell(2).setCellValue(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
                            sheet.getRow(rc).getCell(3).setCellValue(p.getKodeGudang());
                            sheet.getRow(rc).getCell(4).setCellValue(p.getCustomer().getNama());
                            sheet.getRow(rc).getCell(5).setCellValue(p.getCustomer().getAlamat());
                            sheet.getRow(rc).getCell(6).setCellValue(p.getCustomerInvoice().getNama());
                            sheet.getRow(rc).getCell(7).setCellValue(p.getPaymentTerm());
                            sheet.getRow(rc).getCell(8).setCellValue(p.getSales().getNama());
                            sheet.getRow(rc).getCell(9).setCellValue(p.getTotalBebanPenjualan());
                            sheet.getRow(rc).getCell(10).setCellValue(p.getTotalPenjualan());
                            sheet.getRow(rc).getCell(11).setCellValue(p.getPembayaran());
                            sheet.getRow(rc).getCell(12).setCellValue(p.getSisaPembayaran());
                            sheet.getRow(rc).getCell(13).setCellValue(p.getCatatan());
                            sheet.getRow(rc).getCell(14).setCellValue(p.getKodeUser());
                            rc++;
                            totalBebanPenjualan = totalBebanPenjualan + p.getTotalBebanPenjualan();
                            totalPenjualan = totalPenjualan + p.getTotalPenjualan();
                            totalPembayaran = totalPembayaran + p.getPembayaran();
                            sisaPembayaran = sisaPembayaran + p.getSisaPembayaran();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total " + temp);
                    sheet.getRow(rc).getCell(9).setCellValue(totalBebanPenjualan);
                    sheet.getRow(rc).getCell(10).setCellValue(totalPenjualan);
                    sheet.getRow(rc).getCell(11).setCellValue(totalPembayaran);
                    sheet.getRow(rc).getCell(12).setCellValue(sisaPembayaran);
                    rc++;
                    grandtotalBebanPenjualan = grandtotalBebanPenjualan + totalBebanPenjualan;
                    grandtotalPenjualan = grandtotalPenjualan + totalPenjualan;
                    grandtotalPembayaran = grandtotalPembayaran + totalPembayaran;
                    grandsisaPembayaran = grandsisaPembayaran + sisaPembayaran;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(9).setCellValue(grandtotalBebanPenjualan);
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalPenjualan);
                sheet.getRow(rc).getCell(11).setCellValue(grandtotalPembayaran);
                sheet.getRow(rc).getCell(12).setCellValue(grandsisaPembayaran);
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
