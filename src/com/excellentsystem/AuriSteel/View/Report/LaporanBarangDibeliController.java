/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.PembelianBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PembelianBarangDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailHutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianBarangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianController;
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
public class LaporanBarangDibeliController {

    @FXML
    private TreeTableView<PembelianBarangDetail> pembelianDetailTable;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> noPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> tglPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> gudangColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> namaSupplierColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> kodeBarangColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, Number> qtyColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, Number> nilaiPokokColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, Number> hargaBeliColumn;
    @FXML
    private TreeTableColumn<PembelianBarangDetail, Number> totalColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalQtyField;
    @FXML
    private Label totalPembelianField;
    @FXML
    private DatePicker tglPembelianMulaiPicker;
    @FXML
    private DatePicker tglPembelianAkhirPicker;

    private final TreeItem<PembelianBarangDetail> root = new TreeItem<>();
    private ObservableList<PembelianBarangDetail> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPembelianProperty());
        noPembelianColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPembelianColumn));

        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPembelianBarangHead().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(gudangColumn));

        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPembelianBarangHead().getSupplier().namaProperty());
        namaSupplierColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSupplierColumn));

        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBarangColumn));

        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBarangColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTreeTableCell(keteranganColumn));

        tglPembelianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPembelianBarangHead().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembelianColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPembelianColumn));
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));

        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTreeTableCell());

        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> Function.getTreeTableCell());

        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiProperty());
        nilaiPokokColumn.setCellFactory(col -> Function.getTreeTableCell());

        totalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalProperty());
        totalColumn.setCellFactory(col -> Function.getTreeTableCell());

        tglPembelianMulaiPicker.setConverter(Function.getTglConverter());
        tglPembelianMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPembelianMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPembelianAkhirPicker));
        tglPembelianAkhirPicker.setConverter(Function.getTglConverter());
        tglPembelianAkhirPicker.setValue(LocalDate.now());
        tglPembelianAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPembelianMulaiPicker));

        allPembelian.addListener((ListChangeListener.Change<? extends PembelianBarangDetail> change) -> {
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
        pembelianDetailTable.setContextMenu(rm);
        pembelianDetailTable.setRowFactory((TreeTableView<PembelianBarangDetail> tableView) -> {
            final TreeTableRow<PembelianBarangDetail> row = new TreeTableRow<PembelianBarangDetail>() {
                @Override
                public void updateItem(PembelianBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPembelian(item.getPembelianBarangHead());
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran");
                        pembayaran.setOnAction((ActionEvent e) -> {
                            showDetailHutang(item.getPembelianBarangHead());
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
                            if (o.getJenis().equals("Detail Pembelian Barang") && o.isStatus() && item.getPembelianBarangHead().getStatus() != null) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Detail Pembayaran Pembelian Barang") && o.isStatus()
                                    && item.getPembelianBarangHead().getPembayaran() > 0 && item.getPembelianBarangHead().getStatus() != null) {
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
        groupBy.add("No Pembelian");
        groupBy.add("Gudang");
        groupBy.add("Supplier");
        groupBy.add("Barang");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Pembelian");
        getPembelian();
    }

    @FXML
    private void getPembelian() {
        Task<List<PembelianBarangDetail>> task = new Task<List<PembelianBarangDetail>>() {
            @Override
            public List<PembelianBarangDetail> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PembelianBarangHead> head = PembelianBarangHeadDAO.getAllByDateAndStatus(con,
                            tglPembelianMulaiPicker.getValue().toString(), tglPembelianAkhirPicker.getValue().toString(), "true");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianBarangDetail> temp = PembelianBarangDetailDAO.getAllByDateAndStatus(con,
                            tglPembelianMulaiPicker.getValue().toString(), tglPembelianAkhirPicker.getValue().toString(), "true");
                    for (PembelianBarangDetail p : temp) {
                        for (PembelianBarangHead pb : head) {
                            if (pb.getNoPembelian().equals(p.getNoPembelian())) {
                                p.setPembelianBarangHead(pb);
                            }
                        }
                        for (Supplier s : allSupplier) {
                            if (p.getPembelianBarangHead().getKodeSupplier().equals(s.getKodeSupplier())) {
                                p.getPembelianBarangHead().setSupplier(s);
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
            for (PembelianBarangDetail temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPembelian())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian())))
                            || checkColumn(temp.getPembelianBarangHead().getSupplier().getNama())
                            || checkColumn(temp.getPembelianBarangHead().getKodeGudang())
                            || checkColumn(temp.getPembelianBarangHead().getCatatan())
                            || checkColumn(temp.getKodeBarang())
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(df.format(temp.getQty()))
                            || checkColumn(df.format(temp.getHargaBeli()))
                            || checkColumn(df.format(temp.getNilai()))
                            || checkColumn(df.format(temp.getTotal()))) {
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
        if (pembelianDetailTable.getRoot() != null) {
            pembelianDetailTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PembelianBarangDetail temp : filterData) {
            String group = "";
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")) {
                group = temp.getNoPembelian();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                group = temp.getPembelianBarangHead().getKodeGudang();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")) {
                group = temp.getPembelianBarangHead().getSupplier().getNama();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")) {
                group = temp.getKodeBarang();
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                group = tgl.format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian()));
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian()));
            }
            if (!groupBy.contains(group)) {
                groupBy.add(group);
            }
        }
        for (String temp : groupBy) {
            PembelianBarangDetail head = new PembelianBarangDetail();
            head.setNoPembelian(temp);
            head.setPembelianBarangHead(new PembelianBarangHead());
            head.getPembelianBarangHead().setSupplier(new Supplier());
            TreeItem<PembelianBarangDetail> parent = new TreeItem<>(head);
            double totalQty = 0;
            double totalHarga = 0;
            for (PembelianBarangDetail detail : filterData) {
                boolean status = false;
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")
                        && temp.equals(detail.getNoPembelian())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                        && temp.equals(tgl.format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                        && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                        && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())))) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")
                        && temp.equals(detail.getPembelianBarangHead().getSupplier().getNama())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                        && temp.equals(detail.getPembelianBarangHead().getKodeGudang())) {
                    status = true;
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")
                        && temp.equals(detail.getKodeBarang())) {
                    status = true;
                }
                if (status) {
                    totalQty = totalQty + detail.getQty();
                    totalHarga = totalHarga + detail.getTotal();
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setQty(totalQty);
            parent.getValue().setTotal(totalHarga);
            root.getChildren().add(parent);
        }
        pembelianDetailTable.setRoot(root);
    }

    private void hitungTotal() {
        double totalQty = 0;
        double totalBeli = 0;
        for (PembelianBarangDetail temp : filterData) {
            totalQty = totalQty + temp.getQty();
            totalBeli = totalBeli + temp.getTotal();
        }
        totalQtyField.setText(df.format(totalQty));
        totalPembelianField.setText(df.format(totalBeli));
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
                Sheet sheet = workbook.createSheet("Laporan Barang Dibeli");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglPembelianMulaiPicker.getValue().toString())) + " - "
                        + tgl.format(tglBarang.parse(tglPembelianAkhirPicker.getValue().toString())));
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
                sheet.getRow(rc).getCell(4).setCellValue("Kode Barang");
                sheet.getRow(rc).getCell(5).setCellValue("Nama Bahan");
                sheet.getRow(rc).getCell(6).setCellValue("Keterangan");
                sheet.getRow(rc).getCell(7).setCellValue("Qty");
                sheet.getRow(rc).getCell(8).setCellValue("Nilai");
                sheet.getRow(rc).getCell(9).setCellValue("Harga Beli");
                sheet.getRow(rc).getCell(10).setCellValue("Total Harga");
                rc++;

                List<String> groupBy = new ArrayList<>();
                for (PembelianBarangDetail temp : filterData) {
                    String group = "";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")) {
                        group = temp.getNoPembelian();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                        group = temp.getPembelianBarangHead().getKodeGudang();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")) {
                        group = temp.getPembelianBarangHead().getSupplier().getNama();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")) {
                        group = temp.getKodeBarang();
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                        group = tgl.format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")) {
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian()));
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")) {
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPembelianBarangHead().getTglPembelian()));
                    }
                    if (!groupBy.contains(group)) {
                        groupBy.add(group);
                    }
                }
                double grandtotalQty = 0;
                double grandtotalHarga = 0;
                for (String temp : groupBy) {
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalQty = 0;
                    double totalHarga = 0;
                    for (PembelianBarangDetail detail : filterData) {
                        boolean status = false;
                        if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")
                                && temp.equals(detail.getNoPembelian())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")
                                && temp.equals(tgl.format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")
                                && temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")
                                && temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())))) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")
                                && temp.equals(detail.getPembelianBarangHead().getSupplier().getNama())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                                && temp.equals(detail.getPembelianBarangHead().getKodeGudang())) {
                            status = true;
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")
                                && temp.equals(detail.getKodeBarang())) {
                            status = true;
                        }
                        if (status) {
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getNoPembelian());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPembelianBarangHead().getTglPembelian())));
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPembelianBarangHead().getKodeGudang());
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPembelianBarangHead().getSupplier().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getKodeBarang());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getNamaBarang());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getKeterangan());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getQty());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getNilai());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getHargaBeli());
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getTotal());
                            rc++;

                            totalQty = totalQty + detail.getQty();
                            totalHarga = totalHarga + detail.getTotal();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total " + temp);
                    sheet.getRow(rc).getCell(7).setCellValue(totalQty);
                    sheet.getRow(rc).getCell(10).setCellValue(totalHarga);
                    rc++;
                    grandtotalQty = grandtotalQty + totalQty;
                    grandtotalHarga = grandtotalHarga + totalHarga;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalQty);
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalHarga);
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
