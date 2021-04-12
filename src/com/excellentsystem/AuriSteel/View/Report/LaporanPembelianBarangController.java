/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.PembelianBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
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
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PembelianBarangDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.View.Dialog.DetailHutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianBarangController;
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
public class LaporanPembelianBarangController {

    @FXML
    private TreeTableView<PembelianBarangHead> pembelianTable;
    @FXML
    private TreeTableColumn<PembelianBarangHead, String> noPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, String> tglPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, String> gudangColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, String> namaSupplierColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, Number> totalPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, Number> totalBebanPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, Number> grandTotalColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, Number> pembayaranColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, Number> sisaPembayaranColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, String> catatanColumn;
    @FXML
    private TreeTableColumn<PembelianBarangHead, String> kodeUserColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPembelianField;
    @FXML
    private Label totalBebanPembelianField;
    @FXML
    private Label totalPembayaranField;
    @FXML
    private Label sisaPembayaranField;
    @FXML
    private DatePicker tglMulaiPembelianPicker;
    @FXML
    private DatePicker tglAkhirPembelianPicker;

    final TreeItem<PembelianBarangHead> root = new TreeItem<>();
    private ObservableList<PembelianBarangHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPembelianProperty());
        noPembelianColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPembelianColumn));

        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(gudangColumn));

        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getSupplier().namaProperty());
        namaSupplierColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSupplierColumn));

        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(catatanColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeUserColumn));

        tglPembelianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembelianColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPembelianColumn));
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));

        totalPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> Function.getTreeTableCell());

        totalBebanPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalBebanPembelianProperty());
        totalBebanPembelianColumn.setCellFactory(col -> Function.getTreeTableCell());

        grandTotalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().grandtotalProperty());
        grandTotalColumn.setCellFactory(col -> Function.getTreeTableCell());

        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());

        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());

        tglMulaiPembelianPicker.setConverter(Function.getTglConverter());
        tglMulaiPembelianPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPembelianPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPembelianPicker));
        tglAkhirPembelianPicker.setConverter(Function.getTglConverter());
        tglAkhirPembelianPicker.setValue(LocalDate.now());
        tglAkhirPembelianPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPembelianPicker));

        allPembelian.addListener((ListChangeListener.Change<? extends PembelianBarangHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembelian();
                });
        filterData.addAll(allPembelian);

        final ContextMenu rm = new ContextMenu();
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().addAll(export);
            }
        }
        rm.getItems().addAll(refresh);
        pembelianTable.setContextMenu(rm);
        pembelianTable.setRowFactory((TreeTableView<PembelianBarangHead> tableView) -> {
            final TreeTableRow<PembelianBarangHead> row = new TreeTableRow<PembelianBarangHead>() {
                @Override
                public void updateItem(PembelianBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPembelian(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran");
                        pembayaran.setOnAction((ActionEvent e) -> {
                            showDetailHutang(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Pembelian Barang") && o.isStatus() && item.getStatus() != null) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Detail Pembayaran Pembelian Barang") && o.isStatus()
                                    && item.getPembayaran() > 0 && item.getStatus() != null) {
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
        groupBy.add("Supplier");
        groupBy.add("Gudang");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Supplier");
        getPembelian();
    }

    @FXML
    private void getPembelian() {
        Task<List<PembelianBarangHead>> task = new Task<List<PembelianBarangHead>>() {
            @Override
            public List<PembelianBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String tglMulai = tglMulaiPembelianPicker.getValue().toString();
                    String tglAkhir = tglAkhirPembelianPicker.getValue().toString();
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianBarangDetail> allDetail = PembelianBarangDetailDAO.getAllByDateAndStatus(
                            con, tglMulai, tglAkhir, "true");
                    List<PembelianBarangHead> temp = PembelianBarangHeadDAO.getAllByDateAndStatus(
                            con, tglMulai, tglAkhir, "true");
                    for (PembelianBarangHead p : temp) {
                        for (Supplier s : allSupplier) {
                            if (p.getKodeSupplier().equals(s.getKodeSupplier())) {
                                p.setSupplier(s);
                            }
                        }
                        List<PembelianBarangDetail> detail = new ArrayList<>();
                        for (PembelianBarangDetail d : allDetail) {
                            if (p.getNoPembelian().equals(d.getNoPembelian())) {
                                detail.add(d);
                            }
                        }
                        p.setListPembelianBarangDetail(detail);
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
            allPembelian.clear();
            allPembelian.addAll(task.getValue());
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

    private void searchPembelian() {
        try {
            filterData.clear();
            for (PembelianBarangHead temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPembelian())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPembelian())))
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeSupplier())
                            || checkColumn(temp.getSupplier().getNama())
                            || checkColumn(df.format(temp.getPembayaran()))
                            || checkColumn(df.format(temp.getTotalPembelian()))
                            || checkColumn(df.format(temp.getSisaPembayaran()))
                            || checkColumn(df.format(temp.getTotalBebanPembelian()))
                            || checkColumn(df.format(temp.getGrandtotal()))
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
        if (pembelianTable.getRoot() != null) {
            pembelianTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PembelianBarangHead temp : filterData) {
            String group = "";
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                group = tgl.format(tglSql.parse(temp.getTglPembelian()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")) {
                group = temp.getSupplier().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                group = temp.getKodeGudang();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getTglPembelian()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getTglPembelian()));
            }
            if (!groupBy.contains(group)) {
                groupBy.add(group);
            }
        }
        for (String temp : groupBy) {
            PembelianBarangHead head = new PembelianBarangHead();
            head.setNoPembelian(temp);
            head.setSupplier(new Supplier());
            TreeItem<PembelianBarangHead> parent = new TreeItem<>(head);
            double totalPembelian = 0;
            double totalBebanPembelian = 0;
            double totalPembayaran = 0;
            double sisaPembayaran = 0;
            for (PembelianBarangHead pj : filterData) {
                boolean status = false;
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                        && temp.equals(tgl.format(tglSql.parse(pj.getTglPembelian())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                        && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(pj.getTglPembelian())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                        && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(pj.getTglPembelian())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")
                        && temp.equals(pj.getSupplier().getNama())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                        && temp.equals(pj.getKodeGudang())) {
                    status = true;
                }
                if (status) {
                    totalPembelian = totalPembelian + pj.getTotalPembelian();
                    totalBebanPembelian = totalBebanPembelian + pj.getTotalBebanPembelian();
                    totalPembayaran = totalPembayaran + pj.getPembayaran();
                    sisaPembayaran = sisaPembayaran + pj.getSisaPembayaran();
                    parent.getChildren().addAll(new TreeItem<>(pj));
                }
            }
            parent.getValue().setTotalPembelian(totalPembelian);
            parent.getValue().setTotalBebanPembelian(totalBebanPembelian);
            parent.getValue().setGrandtotal(totalPembelian + totalBebanPembelian);
            parent.getValue().setPembayaran(totalPembayaran);
            parent.getValue().setSisaPembayaran(sisaPembayaran);
            root.getChildren().add(parent);
        }
        pembelianTable.setRoot(root);
    }

    private void hitungTotal() {
        double totalPembelian = 0;
        double sisaPembayaran = 0;
        double totalBebanPembelian = 0;
        double totalPembayaran = 0;
        for (PembelianBarangHead temp : filterData) {
            totalPembelian = totalPembelian + temp.getTotalPembelian();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
            totalBebanPembelian = totalBebanPembelian + temp.getTotalBebanPembelian();
            totalPembayaran = totalPembayaran + temp.getPembayaran();
        }
        totalPembelianField.setText(df.format(totalPembelian));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
        totalBebanPembelianField.setText(df.format(totalBebanPembelian));
        totalPembayaranField.setText(df.format(totalPembayaran));
    }

    private void lihatDetailPembelian(PembelianBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembelianBarang.fxml");
        NewPembelianBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPembelian(p.getNoPembelian());
    }

    private void showDetailHutang(PembelianBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetailPembelianBarang(p);
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
                Sheet sheet = workbook.createSheet("Laporan Pembelian");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPembelianPicker.getValue().toString())) + " - "
                        + tgl.format(tglBarang.parse(tglAkhirPembelianPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Group By : " + groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pembelian");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pembelian");
                sheet.getRow(rc).getCell(2).setCellValue("Gudang");
                sheet.getRow(rc).getCell(3).setCellValue("Supplier");
                sheet.getRow(rc).getCell(4).setCellValue("Total Pembelian");
                sheet.getRow(rc).getCell(5).setCellValue("Total Beban Pembelian");
                sheet.getRow(rc).getCell(6).setCellValue("Grandtotal");
                sheet.getRow(rc).getCell(7).setCellValue("Pembayaran");
                sheet.getRow(rc).getCell(8).setCellValue("Sisa Pembayaran");
                sheet.getRow(rc).getCell(9).setCellValue("Catatan");
                sheet.getRow(rc).getCell(10).setCellValue("Kode User");
                rc++;

                List<String> groupBy = new ArrayList<>();
                for (PembelianBarangHead temp : filterData) {
                    String group = "";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                        group = tgl.format(tglSql.parse(temp.getTglPembelian()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                        group = temp.getKodeGudang();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")) {
                        group = temp.getSupplier().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getTglPembelian()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getTglPembelian()));
                    }
                    if (!groupBy.contains(group)) {
                        groupBy.add(group);
                    }
                }
                double grandtotalPembelian = 0;
                double grandtotalBebanPembelian = 0;
                double grandtotalPembayaran = 0;
                double grandsisaPembayaran = 0;
                for (String temp : groupBy) {
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalPembelian = 0;
                    double totalBebanPembelian = 0;
                    double totalPembayaran = 0;
                    double sisaPembayaran = 0;
                    for (PembelianBarangHead p : filterData) {
                        boolean status = false;
                        if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                                && temp.equals(tgl.format(tglSql.parse(p.getTglPembelian())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                                && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(p.getTglPembelian())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                                && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(p.getTglPembelian())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")
                                && temp.equals(p.getSupplier().getNama())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                                && temp.equals(p.getKodeGudang())) {
                            status = true;
                        }
                        if (status) {
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(p.getNoPembelian());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(p.getTglPembelian())));
                            sheet.getRow(rc).getCell(2).setCellValue(p.getKodeGudang());
                            sheet.getRow(rc).getCell(3).setCellValue(p.getSupplier().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(p.getTotalPembelian());
                            sheet.getRow(rc).getCell(5).setCellValue(p.getTotalBebanPembelian());
                            sheet.getRow(rc).getCell(6).setCellValue(p.getGrandtotal());
                            sheet.getRow(rc).getCell(7).setCellValue(p.getPembayaran());
                            sheet.getRow(rc).getCell(8).setCellValue(p.getSisaPembayaran());
                            sheet.getRow(rc).getCell(9).setCellValue(p.getCatatan());
                            sheet.getRow(rc).getCell(10).setCellValue(p.getKodeUser());
                            rc++;
                            totalPembelian = totalPembelian + p.getTotalPembelian();
                            totalBebanPembelian = totalBebanPembelian + p.getTotalBebanPembelian();
                            totalPembayaran = totalPembayaran + p.getPembayaran();
                            sisaPembayaran = sisaPembayaran + p.getSisaPembayaran();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total " + temp);
                    sheet.getRow(rc).getCell(4).setCellValue(totalPembelian);
                    sheet.getRow(rc).getCell(5).setCellValue(totalBebanPembelian);
                    sheet.getRow(rc).getCell(6).setCellValue(totalPembelian + totalBebanPembelian);
                    sheet.getRow(rc).getCell(7).setCellValue(totalPembayaran);
                    sheet.getRow(rc).getCell(8).setCellValue(sisaPembayaran);
                    rc++;
                    grandtotalPembelian = grandtotalPembelian + totalPembelian;
                    grandtotalBebanPembelian = grandtotalBebanPembelian + totalBebanPembelian;
                    grandtotalPembayaran = grandtotalPembayaran + totalPembayaran;
                    grandsisaPembayaran = grandsisaPembayaran + sisaPembayaran;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(4).setCellValue(grandtotalPembelian);
                sheet.getRow(rc).getCell(5).setCellValue(grandtotalBebanPembelian);
                sheet.getRow(rc).getCell(6).setCellValue(grandtotalPembelian + grandtotalBebanPembelian);
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalPembayaran);
                sheet.getRow(rc).getCell(8).setCellValue(grandsisaPembayaran);
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
