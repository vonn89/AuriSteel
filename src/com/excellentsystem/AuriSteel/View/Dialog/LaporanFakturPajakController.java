/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
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
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
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
 * @author Xtreme
 */
public class LaporanFakturPajakController {

    @FXML
    public TableView<PenjualanBarangHead> penjualanHeadTable;
    @FXML
    private TableColumn<PenjualanBarangHead, Boolean> checkColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> noPenjualanHeadColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> tglPenjualanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> kodeCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> alamatCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> paymentTermColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, Number> totalPenjualanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, Number> totalBebanPenjualanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, Number> pembayaranColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, Number> sisaPembayaranColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> catatanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> kodeSalesColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> namaSalesColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> kodeUserColumn;

    @FXML
    private TextField nomorFakturField;
    @FXML
    private CheckBox checkAll;
    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    private ObservableList<PenjualanBarangHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().isCheckedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return penjualanHeadTable.getItems().get(v).isCheckedProperty();
        }));

        noPenjualanHeadColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCustomerProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalPenjualanProperty());
        totalBebanPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalBebanPenjualanProperty());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().sisaPembayaranProperty());
        totalPenjualanColumn.setCellFactory(col -> Function.getTableCell());
        totalBebanPenjualanColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusWeeks(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanBarangHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanHeadTable.setItems(filterData);

        penjualanHeadTable.setRowFactory((TableView<PenjualanBarangHead> tableView) -> {
            final TableRow<PenjualanBarangHead> row = new TableRow<PenjualanBarangHead>() {
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        if (row.getItem().isIsChecked()) {
                            row.getItem().setIsChecked(false);
                        } else {
                            row.getItem().setIsChecked(true);
                        }
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
        getPenjualan();
    }

    @FXML
    private void checkAllHandle() {
        for (PenjualanBarangHead d : allPenjualan) {
            d.setIsChecked(checkAll.isSelected());
        }
        penjualanHeadTable.refresh();
    }

    @FXML
    private void getPenjualan() {
        Task<List<PenjualanBarangHead>> task = new Task<List<PenjualanBarangHead>>() {
            @Override
            public List<PenjualanBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanBarangHead> allPenjualan = PenjualanBarangHeadDAO.getAllByDateAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<PenjualanBarangDetail> allDetail = PenjualanBarangDetailDAO.getAllByDateAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    for (PenjualanBarangHead p : allPenjualan) {
                        List<PenjualanBarangDetail> listDetail = new ArrayList<>();
                        for (PenjualanBarangDetail d : allDetail) {
                            if (p.getNoPenjualan().equals(d.getNoPenjualan())) {
                                listDetail.add(d);
                            }
                        }
                        p.setListPenjualanBarangDetail(listDetail);
                        for (Customer c : allCustomer) {
                            if (p.getKodeCustomer().equals(c.getKodeCustomer())) {
                                p.setCustomer(c);
                            }
                        }
                        for (Customer c : allCustomer) {
                            if (p.getKodeCustomerInvoice().equals(c.getKodeCustomer())) {
                                p.setCustomerInvoice(c);
                            }
                        }
                        for (Pegawai s : allSales) {
                            if (p.getKodeSales().equals(s.getKodePegawai())) {
                                p.setSales(s);
                            }
                        }
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
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
                            || checkColumn(temp.getKodeCustomer())
                            || checkColumn(temp.getCustomer().getNama())
                            || checkColumn(temp.getCustomer().getAlamat())
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(df.format(temp.getTotalPenjualan()))
                            || checkColumn(df.format(temp.getTotalBebanPenjualan()))
                            || checkColumn(df.format(temp.getPembayaran()))
                            || checkColumn(df.format(temp.getSisaPembayaran()))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getKodeSales())
                            || checkColumn(temp.getSales().getNama())
                            || checkColumn(temp.getKodeUser())
                            || checkColumn(temp.getTglBatal())
                            || checkColumn(temp.getUserBatal())) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void exportExcelPajak() {
        List<PenjualanBarangHead> listFaktur = new ArrayList<>();
        for (PenjualanBarangHead s : filterData) {
            if (s.isIsChecked()) {
                listFaktur.add(s);
            }
        }
        if (nomorFakturField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Nomor faktur masih kosong");
        } else if (listFaktur.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Data penjualan belum dipilih");
        } else {
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
                    Sheet sheet = workbook.createSheet("Data Penjualan");
                    int rc = 0;
                    int c = 19;
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue("FK");
                    sheet.getRow(rc).getCell(1).setCellValue("KD_JENIS_TRANSAKSI");
                    sheet.getRow(rc).getCell(2).setCellValue("FG_PENGGANTI");
                    sheet.getRow(rc).getCell(3).setCellValue("NOMOR_FAKTUR");
                    sheet.getRow(rc).getCell(4).setCellValue("MASA_PAJAK");
                    sheet.getRow(rc).getCell(5).setCellValue("TAHUN_PAJAK");
                    sheet.getRow(rc).getCell(6).setCellValue("TANGGAL_FAKTUR");
                    sheet.getRow(rc).getCell(7).setCellValue("NPWP");
                    sheet.getRow(rc).getCell(8).setCellValue("NAMA");
                    sheet.getRow(rc).getCell(9).setCellValue("ALAMAT_LENGKAP");
                    sheet.getRow(rc).getCell(10).setCellValue("JUMLAH_DPP");
                    sheet.getRow(rc).getCell(11).setCellValue("JUMLAH_PPN");
                    sheet.getRow(rc).getCell(12).setCellValue("JUMLAH_PPNBM");
                    sheet.getRow(rc).getCell(13).setCellValue("ID_KETERANGAN_TAMBAHAN");
                    sheet.getRow(rc).getCell(14).setCellValue("FG_UANG_MUKA");
                    sheet.getRow(rc).getCell(15).setCellValue("UANG_MUKA_DPP");
                    sheet.getRow(rc).getCell(16).setCellValue("UANG_MUKA_PPN");
                    sheet.getRow(rc).getCell(17).setCellValue("UANG_MUKA_PPNBM");
                    sheet.getRow(rc).getCell(18).setCellValue("REFERENSI");
                    rc++;
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue("LT");
                    sheet.getRow(rc).getCell(1).setCellValue("NPWP");
                    sheet.getRow(rc).getCell(2).setCellValue("NAMA");
                    sheet.getRow(rc).getCell(3).setCellValue("JALAN");
                    sheet.getRow(rc).getCell(4).setCellValue("BLOK");
                    sheet.getRow(rc).getCell(5).setCellValue("NOMOR");
                    sheet.getRow(rc).getCell(6).setCellValue("RT");
                    sheet.getRow(rc).getCell(7).setCellValue("RW");
                    sheet.getRow(rc).getCell(8).setCellValue("KECAMATAN");
                    sheet.getRow(rc).getCell(9).setCellValue("KELURAHAN");
                    sheet.getRow(rc).getCell(10).setCellValue("KABUPATEN");
                    sheet.getRow(rc).getCell(11).setCellValue("PROPINSI");
                    sheet.getRow(rc).getCell(12).setCellValue("KODE_POS");
                    sheet.getRow(rc).getCell(13).setCellValue("NOMOR_TELEPON");
                    rc++;
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue("OF");
                    sheet.getRow(rc).getCell(1).setCellValue("KODE_OBJEK");
                    sheet.getRow(rc).getCell(2).setCellValue("NAMA");
                    sheet.getRow(rc).getCell(3).setCellValue("HARGA_SATUAN");
                    sheet.getRow(rc).getCell(4).setCellValue("JUMLAH_BARANG");
                    sheet.getRow(rc).getCell(5).setCellValue("HARGA_TOTAL");
                    sheet.getRow(rc).getCell(6).setCellValue("DISKON");
                    sheet.getRow(rc).getCell(7).setCellValue("DPP");
                    sheet.getRow(rc).getCell(8).setCellValue("PPN");
                    sheet.getRow(rc).getCell(9).setCellValue("TARIF_PPNBM");
                    sheet.getRow(rc).getCell(10).setCellValue("PPNBM");
                    rc++;
                    long noFaktur = Long.parseLong(nomorFakturField.getText());
                    for (PenjualanBarangHead p : listFaktur) {
                        createRow(workbook, sheet, rc, c, "Detail");
                        sheet.getRow(rc).getCell(0).setCellValue("FK");
                        sheet.getRow(rc).getCell(1).setCellValue("1");
                        sheet.getRow(rc).getCell(2).setCellValue("0");
                        sheet.getRow(rc).getCell(3).setCellValue(noFaktur);
                        sheet.getRow(rc).getCell(4).setCellValue(new SimpleDateFormat("MM").format(tglSql.parse(p.getTglPenjualan())));
                        sheet.getRow(rc).getCell(5).setCellValue(new SimpleDateFormat("yyyy").format(tglSql.parse(p.getTglPenjualan())));
                        sheet.getRow(rc).getCell(6).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(tglSql.parse(p.getTglPenjualan())));
                        sheet.getRow(rc).getCell(7).setCellValue(p.getCustomer().getNoNpwp());
                        sheet.getRow(rc).getCell(8).setCellValue(p.getCustomer().getNamaNpwp());
                        sheet.getRow(rc).getCell(9).setCellValue(p.getCustomer().getAlamatNpwp());
                        sheet.getRow(rc).getCell(10).setCellValue(p.getTotalPenjualan() / 1.1);
                        sheet.getRow(rc).getCell(11).setCellValue(p.getTotalPenjualan() / 1.1 * 0.1);
                        sheet.getRow(rc).getCell(12).setCellValue("");
                        sheet.getRow(rc).getCell(13).setCellValue("");
                        sheet.getRow(rc).getCell(14).setCellValue("");
                        sheet.getRow(rc).getCell(15).setCellValue("");
                        sheet.getRow(rc).getCell(16).setCellValue("");
                        sheet.getRow(rc).getCell(17).setCellValue("");
                        sheet.getRow(rc).getCell(18).setCellValue("");
                        rc++;
                        createRow(workbook, sheet, rc, c, "Detail");
                        sheet.getRow(rc).getCell(0).setCellValue("FAPR");
                        sheet.getRow(rc).getCell(1).setCellValue("PT AURI STEEL METALINDO");
                        sheet.getRow(rc).getCell(2).setCellValue("JL.TERBOYO MEGAH I NO 5, TERBOYO WETAN , KOTA SEMARANG");
                        sheet.getRow(rc).getCell(3).setCellValue("");
                        sheet.getRow(rc).getCell(4).setCellValue("");
                        sheet.getRow(rc).getCell(5).setCellValue("");
                        sheet.getRow(rc).getCell(6).setCellValue("");
                        sheet.getRow(rc).getCell(7).setCellValue("");
                        sheet.getRow(rc).getCell(8).setCellValue("");
                        sheet.getRow(rc).getCell(9).setCellValue("");
                        sheet.getRow(rc).getCell(10).setCellValue("");
                        sheet.getRow(rc).getCell(11).setCellValue("");
                        sheet.getRow(rc).getCell(12).setCellValue("");
                        sheet.getRow(rc).getCell(13).setCellValue("");
                        rc++;
                        for (PenjualanBarangDetail d : p.getListPenjualanBarangDetail()) {
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue("OF");
                            sheet.getRow(rc).getCell(1).setCellValue(d.getKodeBarang());
                            sheet.getRow(rc).getCell(2).setCellValue(d.getNamaBarang());
                            sheet.getRow(rc).getCell(3).setCellValue(d.getHargaJual() / 1.1);
                            sheet.getRow(rc).getCell(4).setCellValue(d.getQty());
                            sheet.getRow(rc).getCell(5).setCellValue(d.getTotal() / 1.1);
                            sheet.getRow(rc).getCell(6).setCellValue("0");
                            sheet.getRow(rc).getCell(7).setCellValue(d.getTotal() / 1.1);
                            sheet.getRow(rc).getCell(8).setCellValue(d.getTotal() / 1.1 * 0.1);
                            sheet.getRow(rc).getCell(9).setCellValue("0");
                            sheet.getRow(rc).getCell(10).setCellValue("0");
                            rc++;
                        }
                        noFaktur++;
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

}
