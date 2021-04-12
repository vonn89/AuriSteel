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
public class LaporanBarangDipesanController {

    @FXML
    private TreeTableView<PemesananBarangDetail> pemesananDetailTable;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> noPemesananColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> tglPemesananColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> namaCustomerColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> namaSalesColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> kodeBarangColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> catatanInternColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, String> satuanColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, Number> qtyColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, Number> qtySisaColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, Number> hargaJualColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, Number> totalColumn;
    @FXML
    private TreeTableColumn<PemesananBarangDetail, Number> totalSisaColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalQtyField;
    @FXML
    private Label totalQtySisaField;
    @FXML
    private Label totalPemesananField;
    @FXML
    private Label totalSisaPemesananField;
    @FXML
    private DatePicker tglPemesananMulaiPicker;
    @FXML
    private DatePicker tglPemesananAkhirPicker;

    final TreeItem<PemesananBarangDetail> root = new TreeItem<>();
    private ObservableList<PemesananBarangDetail> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPemesananColumn));

        tglPemesananColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPemesananBarangHead().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPemesananBarangHead().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaCustomerColumn));

        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPemesananBarangHead().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSalesColumn));

        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBarangColumn));

        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBarangColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTreeTableCell(keteranganColumn));

        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().catatanInternProperty());
        catatanInternColumn.setCellFactory(col -> Function.getWrapTreeTableCell(catatanInternColumn));

        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().satuanProperty());
        satuanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(satuanColumn));

        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTreeTableCell());

        qtySisaColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getValue().getQty() - cellData.getValue().getValue().getQtyTerkirim());
        });
        qtySisaColumn.setCellFactory(col -> Function.getTreeTableCell());

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> Function.getTreeTableCell());

        totalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalProperty());
        totalColumn.setCellFactory(col -> Function.getTreeTableCell());

        totalSisaColumn.setCellValueFactory(cellData -> {
            double qtySisa = cellData.getValue().getValue().getQty() - cellData.getValue().getValue().getQtyTerkirim();
            return new SimpleDoubleProperty(qtySisa * cellData.getValue().getValue().getHargaJual());
        });
        totalSisaColumn.setCellFactory(col -> Function.getTreeTableCell());

        tglPemesananMulaiPicker.setConverter(Function.getTglConverter());
        tglPemesananMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPemesananMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPemesananAkhirPicker));
        tglPemesananAkhirPicker.setConverter(Function.getTglConverter());
        tglPemesananAkhirPicker.setValue(LocalDate.now());
        tglPemesananAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPemesananMulaiPicker));

        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananBarangDetail> change) -> {
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
        pemesananDetailTable.setContextMenu(rm);
        pemesananDetailTable.setRowFactory((TreeTableView<PemesananBarangDetail> tableView) -> {
            final TreeTableRow<PemesananBarangDetail> row = new TreeTableRow<PemesananBarangDetail>() {
                @Override
                public void updateItem(PemesananBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pemesanan");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPemesanan(item.getPemesananBarangHead());
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
                                    && item.getPemesananBarangHead().getStatus() != null) {
                                rm.getItems().add(detail);
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
        groupBy.add("No Pemesanan");
        groupBy.add("Customer");
        groupBy.add("Sales");
        groupBy.add("Barang");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Pemesanan");
        ObservableList<String> status = FXCollections.observableArrayList();
        status.clear();
        status.add("On Review");
        status.add("Wait");
        status.add("Done");
        status.add("Cancel");
        status.add("Semua");
        statusCombo.setItems(status);
        statusCombo.getSelectionModel().select("Wait");
        getPemesanan();
    }

    @FXML
    private void getPemesanan() {
        Task<List<PemesananBarangDetail>> task = new Task<List<PemesananBarangDetail>>() {
            @Override
            public List<PemesananBarangDetail> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (statusCombo.getSelectionModel().getSelectedItem().equals("Done")) {
                        status = "close";
                    } else if (statusCombo.getSelectionModel().getSelectedItem().equals("Wait")) {
                        status = "open";
                    } else if (statusCombo.getSelectionModel().getSelectedItem().equals("Cancel")) {
                        status = "false";
                    } else if (statusCombo.getSelectionModel().getSelectedItem().equals("On Review")) {
                        status = "wait";
                    }
                    List<PemesananBarangHead> pemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(con,
                            tglPemesananMulaiPicker.getValue().toString(), tglPemesananAkhirPicker.getValue().toString(), status);
                    List<PemesananBarangDetail> temp = PemesananBarangDetailDAO.getAllByDateAndStatus(con,
                            tglPemesananMulaiPicker.getValue().toString(), tglPemesananAkhirPicker.getValue().toString(), status);
                    List<Customer> customer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> sales = PegawaiDAO.getAllByStatus(con, "%");
                    for (PemesananBarangDetail d : temp) {
                        for (PemesananBarangHead h : pemesanan) {
                            if (d.getNoPemesanan().equals(h.getNoPemesanan())) {
                                d.setPemesananBarangHead(h);
                            }
                        }
                        for (Customer c : customer) {
                            if (d.getPemesananBarangHead().getKodeCustomer().equals(c.getKodeCustomer())) {
                                d.getPemesananBarangHead().setCustomer(c);
                            }
                        }
                        for (Pegawai s : sales) {
                            if (d.getPemesananBarangHead().getKodeSales().equals(s.getKodePegawai())) {
                                d.getPemesananBarangHead().setSales(s);
                            }
                        }
                    }
                    return temp;
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
            for (PemesananBarangDetail temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPemesanan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan())))
                            || checkColumn(temp.getPemesananBarangHead().getKodeCustomer())
                            || checkColumn(temp.getPemesananBarangHead().getCustomer().getNama())
                            || checkColumn(temp.getPemesananBarangHead().getPaymentTerm())
                            || checkColumn(temp.getPemesananBarangHead().getCatatan())
                            || checkColumn(temp.getPemesananBarangHead().getKodeSales())
                            || checkColumn(temp.getPemesananBarangHead().getSales().getNama())
                            || checkColumn(temp.getKodeBarang())
                            || checkColumn(df.format(temp.getHargaJual()))
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(df.format(temp.getQty()))
                            || checkColumn(df.format(temp.getQty() - temp.getQtyTerkirim()))
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getCatatanIntern())
                            || checkColumn(temp.getSatuan())
                            || checkColumn(df.format(temp.getTotal()))
                            || checkColumn(df.format(temp.getHargaJual() * (temp.getQty() - temp.getQtyTerkirim())))) {
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

    private void hitungTotal() {
        double totalQty = 0;
        double totalQtySisa = 0;
        double totalPemesanan = 0;
        double totalSisaPemesanan = 0;
        for (PemesananBarangDetail temp : filterData) {
            totalQty = totalQty + temp.getQty();
            totalQtySisa = totalQtySisa + (temp.getQty() - temp.getQtyTerkirim());
            totalPemesanan = totalPemesanan + temp.getTotal();
            totalSisaPemesanan = totalSisaPemesanan + (temp.getQty() - temp.getQtyTerkirim()) * temp.getHargaJual();
        }
        totalQtyField.setText(df.format(totalQty));
        totalQtySisaField.setText(df.format(totalQtySisa));
        totalPemesananField.setText(df.format(totalPemesanan));
        totalSisaPemesananField.setText(df.format(totalSisaPemesanan));
    }

    private void setTable() throws Exception {
        if (pemesananDetailTable.getRoot() != null) {
            pemesananDetailTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PemesananBarangDetail temp : filterData) {
            String group = "";
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pemesanan")) {
                group = temp.getNoPemesanan();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                group = temp.getPemesananBarangHead().getSales().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")) {
                group = temp.getPemesananBarangHead().getCustomer().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")) {
                group = temp.getKodeBarang();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                group = tgl.format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan()));
            }
            if (!groupBy.contains(group)) {
                groupBy.add(group);
            }
        }
        for (String temp : groupBy) {
            PemesananBarangDetail head = new PemesananBarangDetail();
            head.setNoPemesanan(temp);
            head.setPemesananBarangHead(new PemesananBarangHead());
            head.getPemesananBarangHead().setCustomer(new Customer());
            head.getPemesananBarangHead().setSales(new Pegawai());
            TreeItem<PemesananBarangDetail> parent = new TreeItem<>(head);
            double totalQty = 0;
            double totalQtyTerkirim = 0;
            double totalPemesanan = 0;
            for (PemesananBarangDetail detail : filterData) {
                boolean status = false;
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pemesanan") && temp.equals(detail.getNoPemesanan())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                        && temp.equals(tgl.format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                        && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                        && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales") && temp.equals(detail.getPemesananBarangHead().getSales().getNama())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer") && temp.equals(detail.getPemesananBarangHead().getCustomer().getNama())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang") && temp.equals(detail.getKodeBarang())) {
                    status = true;
                }
                if (status) {
                    totalQty = totalQty + detail.getQty();
                    totalQtyTerkirim = totalQtyTerkirim + detail.getQtyTerkirim();
                    totalPemesanan = totalPemesanan + detail.getTotal();
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setQty(totalQty);
            parent.getValue().setQtyTerkirim(totalQtyTerkirim);
            parent.getValue().setTotal(totalPemesanan);
            root.getChildren().add(parent);
        }
        pemesananDetailTable.setRoot(root);
    }

    private void lihatDetailPemesanan(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p.getNoPemesanan());
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

                Sheet sheet = workbook.createSheet("Laporan Barang Dipesan");
                int rc = 0;
                int c = 14;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglPemesananMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglPemesananAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pemesanan");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pemesanan");
                sheet.getRow(rc).getCell(2).setCellValue("Customer");
                sheet.getRow(rc).getCell(3).setCellValue("Sales");
                sheet.getRow(rc).getCell(4).setCellValue("Kode Barang");
                sheet.getRow(rc).getCell(5).setCellValue("Nama Barang");
                sheet.getRow(rc).getCell(6).setCellValue("Keterangan");
                sheet.getRow(rc).getCell(7).setCellValue("Catatan Intern");
                sheet.getRow(rc).getCell(8).setCellValue("Satuan");
                sheet.getRow(rc).getCell(9).setCellValue("Qty");
                sheet.getRow(rc).getCell(10).setCellValue("Qty Sisa");
                sheet.getRow(rc).getCell(11).setCellValue("Harga");
                sheet.getRow(rc).getCell(12).setCellValue("Total Pemesanan");
                sheet.getRow(rc).getCell(13).setCellValue("Sisa Pemesanan");
                rc++;
                List<String> groupBy = new ArrayList<>();
                for (PemesananBarangDetail temp : filterData) {
                    String group = "";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pemesanan")) {
                        group = temp.getNoPemesanan();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                        group = temp.getPemesananBarangHead().getSales().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")) {
                        group = temp.getPemesananBarangHead().getCustomer().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")) {
                        group = temp.getNamaBarang();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                        group = tgl.format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan()));
                    }
                    if (!groupBy.contains(group)) {
                        groupBy.add(group);
                    }
                }
                double grandtotalQty = 0;
                double grandtotalQtyTerkirim = 0;
                double grandtotalPemesanan = 0;
                double grandtotalSisaPemesanan = 0;
                for (String temp : groupBy) {
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalQty = 0;
                    double totalQtyTerkirim = 0;
                    double totalPemesanan = 0;
                    double sisaPemesanan = 0;
                    for (PemesananBarangDetail detail : filterData) {
                        boolean status = false;
                        if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pemesanan")
                                && temp.equals(detail.getNoPemesanan())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                                && temp.equals(tgl.format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                                && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                                && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")
                                && temp.equals(detail.getPemesananBarangHead().getSales().getNama())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")
                                && temp.equals(detail.getPemesananBarangHead().getCustomer().getNama())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")
                                && temp.equals(detail.getNamaBarang())) {
                            status = true;
                        }
                        if (status) {
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getPemesananBarangHead().getNoPemesanan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPemesananBarangHead().getTglPemesanan())));
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPemesananBarangHead().getCustomer().getNama());
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPemesananBarangHead().getSales().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getKodeBarang());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getNamaBarang());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getKeterangan());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getCatatanIntern());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getSatuan());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getQty());
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getQty() - detail.getQtyTerkirim());
                            sheet.getRow(rc).getCell(11).setCellValue(detail.getHargaJual());
                            sheet.getRow(rc).getCell(12).setCellValue(detail.getTotal());
                            sheet.getRow(rc).getCell(13).setCellValue((detail.getQty() - detail.getQtyTerkirim())*detail.getHargaJual());
                            rc++;

                            totalQty = totalQty + detail.getQty();
                            totalQtyTerkirim = totalQtyTerkirim + detail.getQtyTerkirim();
                            totalPemesanan = totalPemesanan + detail.getTotal();
                            sisaPemesanan = sisaPemesanan + (detail.getQty() - detail.getQtyTerkirim())*detail.getHargaJual();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total " + temp);
                    sheet.getRow(rc).getCell(9).setCellValue(totalQty);
                    sheet.getRow(rc).getCell(10).setCellValue(totalQty - totalQtyTerkirim);
                    sheet.getRow(rc).getCell(11).setCellValue(totalPemesanan/ totalQty);
                    sheet.getRow(rc).getCell(12).setCellValue(totalPemesanan);
                    sheet.getRow(rc).getCell(13).setCellValue(sisaPemesanan);
                    rc++;
                    grandtotalQty = grandtotalQty + totalQty;
                    grandtotalQtyTerkirim = grandtotalQtyTerkirim + totalQtyTerkirim;
                    grandtotalPemesanan = grandtotalPemesanan + totalPemesanan;
                    grandtotalSisaPemesanan = grandtotalSisaPemesanan + sisaPemesanan;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(9).setCellValue(grandtotalQty);
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalQty - grandtotalQtyTerkirim);
                sheet.getRow(rc).getCell(11).setCellValue(grandtotalPemesanan/ grandtotalQty);
                sheet.getRow(rc).getCell(12).setCellValue(grandtotalPemesanan);
                sheet.getRow(rc).getCell(13).setCellValue(grandtotalSisaPemesanan);
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
