/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
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
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.TerimaPembayaran;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailBebanPenjualanController;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.LaporanFakturPajakController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembayaranController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Xtreme
 */
public class PenjualanController {

    @FXML
    private TableView<PenjualanBarangHead> penjualanTable;
    @FXML
    private TableColumn<PenjualanBarangHead, String> noPenjualanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> tglPengirimanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> tglPenjualanColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> gudangColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> alamatCustomerColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> namaInvoiceColumn;
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
    private TableColumn<PenjualanBarangHead, String> namaSalesColumn;
    @FXML
    private TableColumn<PenjualanBarangHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Label totalPenjualanField;
    @FXML
    private Label belumTerbayarField;
    @FXML
    private Label sudahTerbayarField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private ComboBox<String> groupByCombo;
    private ObservableList<PenjualanBarangHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        noPenjualanColumn.setCellFactory(col -> Function.getWrapTableCell(noPenjualanColumn));

        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));

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

        tglPengirimanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPengiriman())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPengirimanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPengirimanColumn));
        tglPengirimanColumn.setComparator(Function.sortDate(tglLengkap));

        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPenjualanColumn));
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));

        totalPenjualanColumn.setCellValueFactory(celldata -> celldata.getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> Function.getTableCell());

        totalBebanPenjualanColumn.setCellValueFactory(celldata -> celldata.getValue().totalBebanPenjualanProperty());
        totalBebanPenjualanColumn.setCellFactory(col -> Function.getTableCell());

        pembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());

        sisaPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));

        final ContextMenu rm = new ContextMenu();
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem faktur = new MenuItem("Export Faktur Pajak");
        faktur.setOnAction((ActionEvent e) -> {
            fakturPajak();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPenjualan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Export Faktur Pajak") && o.isStatus()) {
                rm.getItems().add(faktur);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        penjualanTable.setContextMenu(rm);
        penjualanTable.setRowFactory((TableView<PenjualanBarangHead> tableView) -> {
            final TableRow<PenjualanBarangHead> row = new TableRow<PenjualanBarangHead>() {
                @Override
                public void updateItem(PenjualanBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem pemesanan = new MenuItem("Detail Pemesanan");
                        pemesanan.setOnAction((ActionEvent e) -> {
                            lihatDetailPemesanan(item);
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPenjualan(item);
                        });
                        MenuItem detailBeban = new MenuItem("Detail Beban Penjualan");
                        detailBeban.setOnAction((ActionEvent e) -> {
                            detailBebanPenjualan(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran");
                        pembayaran.setOnAction((ActionEvent e) -> {
                            showDetailPiutang(item);
                        });
                        MenuItem revisi = new MenuItem("Revisi Penjualan");
                        revisi.setOnAction((ActionEvent e) -> {
                            revisiPenjualan(item);
                        });
                        MenuItem invoice = new MenuItem("Print Invoice");
                        invoice.setOnAction((ActionEvent e) -> {
                            printInvoice(item);
                        });
                        MenuItem invoiceSoftcopyKendal = new MenuItem("Print Invoice Softcopy Kendal");
                        invoiceSoftcopyKendal.setOnAction((ActionEvent e) -> {
                            printInvoiceSoftCopyKendal(item);
                        });
                        MenuItem invoiceSoftcopyTerboyo = new MenuItem("Print Invoice Softcopy Terboyo");
                        invoiceSoftcopyTerboyo.setOnAction((ActionEvent e) -> {
                            printInvoiceSoftCopyTerboyo(item);
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran");
                        bayar.setOnAction((ActionEvent e) -> {
                            showPembayaran(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem faktur = new MenuItem("Export Faktur Pajak");
                        faktur.setOnAction((ActionEvent e) -> {
                            fakturPajak();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Revisi Penjualan") && o.isStatus()) {
                                rm.getItems().add(revisi);
                            }
                            if (o.getJenis().equals("Detail Penjualan") && o.isStatus()) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Detail Transaksi") && o.isStatus()) {
                                rm.getItems().add(detailBeban);
                            }
                            if (o.getJenis().equals("Detail Pembayaran Penjualan") && o.isStatus()
                                    && item.getPembayaran() > 0) {
                                rm.getItems().add(pembayaran);
                            }
                            if (o.getJenis().equals("Detail Pemesanan") && o.isStatus()) {
                                rm.getItems().add(pemesanan);
                            }
                            if (o.getJenis().equals("Terima Pembayaran") && o.isStatus() && item.getSisaPembayaran() > 0) {
                                rm.getItems().add(bayar);
                            }
                            if (o.getJenis().equals("Print Invoice") && o.isStatus()) {
                                rm.getItems().addAll(invoice, invoiceSoftcopyKendal, invoiceSoftcopyTerboyo);
                            }
                            if (o.getJenis().equals("Export Faktur Pajak") && o.isStatus()) {
                                rm.getItems().add(faktur);
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
                            if (o.getJenis().equals("Detail Penjualan") && o.isStatus()) {
                                lihatDetailPenjualan(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanBarangHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenjualan();
                });
        filterData.addAll(allPenjualan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Semua");
        groupBy.add("Belum Lunas");
        groupBy.add("Lunas");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().selectFirst();
        getPenjualan();
        penjualanTable.setItems(filterData);
    }

    @FXML
    private void getPenjualan() {
        Task<List<PenjualanBarangHead>> task = new Task<List<PenjualanBarangHead>>() {
            @Override
            public List<PenjualanBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanBarangHead> allPenjualan = PenjualanBarangHeadDAO.getAllByDateAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    List<PenjualanBarangHead> listPenjualan = new ArrayList<>();
                    for (PenjualanBarangHead p : allPenjualan) {
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
                        if (groupByCombo.getSelectionModel().getSelectedItem().equals("Semua")) {
                            listPenjualan.add(p);
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas") && p.getSisaPembayaran() != 0) {
                            listPenjualan.add(p);
                        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Lunas") && p.getSisaPembayaran() == 0) {
                            listPenjualan.add(p);
                        }

                    }
                    return listPenjualan;
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
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getCustomer().getNama())
                            || checkColumn(temp.getCustomerInvoice().getNama())
                            || checkColumn(temp.getCustomer().getAlamat())
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(df.format(temp.getTotalPenjualan()))
                            || checkColumn(df.format(temp.getPembayaran()))
                            || checkColumn(df.format(temp.getSisaPembayaran()))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getSales().getNama())) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        double total = 0;
        double terbayar = 0;
        double belumterbayar = 0;
        for (PenjualanBarangHead temp : filterData) {
            total = total + temp.getTotalPenjualan();
            terbayar = terbayar + temp.getPembayaran();
            belumterbayar = belumterbayar + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(total));
        sudahTerbayarField.setText(df.format(terbayar));
        belumTerbayarField.setText(df.format(belumterbayar));
    }

    private void showPembayaran(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranPenjualan(p.getNoPenjualan());
        controller.saveButton.setOnAction((event) -> {
            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
            if (jumlahBayar > p.getSisaPembayaran()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa pembayaran");
            } else if (controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            Date tglTransaksi = tglSql.parse(controller.tglTransaksiPicker.getValue().toString() + " " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            Piutang pt = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                                    con, "Piutang Penjualan", p.getNoPenjualan(), "%");
                            pt.setPenjualanHead(p);
                            TerimaPembayaran t = new TerimaPembayaran();
                            t.setNoPiutang(pt.getNoPiutang());
                            t.setJumlahPembayaran(Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", "")));
                            t.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            t.setCatatan("");
                            t.setKodeUser(sistem.getUser().getKodeUser());
                            t.setTglBatal("2000-01-01 00:00:00");
                            t.setUserBatal("");
                            t.setStatus("true");
                            t.setPiutang(pt);
                            return Service.newTerimaPembayaranPiutang(con, t, tglTransaksi);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran penjualan berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }

    private void lihatDetailPemesanan(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p.getNoPemesanan());
    }

    private void lihatDetailPenjualan(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }

    private void detailBebanPenjualan(PenjualanBarangHead p) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/DetailBebanPenjualan.fxml");
        DetailBebanPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setDetailBebanPenjualan(p.getNoPenjualan());
    }

    private void fakturPajak() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/LaporanFakturPajak.fxml");
        LaporanFakturPajakController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
    }

    private void revisiPenjualan(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setRevisiPenjualan(p.getNoPenjualan());
        controller.saveButton.setOnAction((event) -> {
            if (controller.allPenjualanDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang yang dijual tidak ada");
            } else if (Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")) < p.getPembayaran()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat disimpan karena jumlah pembayaran lebih besar dari total penjualan");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            String noPenjualan = controller.penjualan.getNoPenjualan();
                            if (noPenjualan.length() > 10) {
                                String no = noPenjualan.substring(0, 10);
                                int noUrut = Integer.parseInt(noPenjualan.substring(11, 12)) + 1;
                                noPenjualan = no + "-" + String.valueOf(noUrut);
                            } else {
                                noPenjualan = noPenjualan + "-1";
                            }
                            controller.penjualan.setNoPenjualan(noPenjualan);

                            p.setListPenjualanBarangDetail(controller.allPenjualanDetail);
                            double totalPenjualan = 0;
                            for (PenjualanBarangDetail d : controller.penjualan.getListPenjualanBarangDetail()) {
                                totalPenjualan = totalPenjualan + d.getTotal();
                            }
                            controller.penjualan.setTotalPenjualan(totalPenjualan);
                            
                            return Service.editPenjualan(con, p, controller.penjualan);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data penjualan berhasil disimpan");
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

    private void showDetailPiutang(PenjualanBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetailPenjualan(p);
        x.pembayaranPiutangTable.setRowFactory((TableView<TerimaPembayaran> tableView) -> {
            final TableRow<TerimaPembayaran> row = new TableRow<TerimaPembayaran>() {
                @Override
                public void updateItem(TerimaPembayaran item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Terima Pembayaran");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembayaran(item, stage);
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Batal Terima Pembayaran") && o.isStatus()) {
                                rm.getItems().add(batal);
                            }
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }

    private void batalPembayaran(TerimaPembayaran pembayaran, Stage stage) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembayaran " + pembayaran.getNoTerimaPembayaran() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalTerimaPembayaranPiutang(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPenjualan();
                if (task.getValue().equals("true")) {
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
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

    private void printInvoice(PenjualanBarangHead p) {
        try (Connection con = Koneksi.getConnection()) {
            List<PenjualanBarangDetail> listPenjualan = PenjualanBarangDetailDAO.getAllPenjualanDetail(con, p.getNoPenjualan());
            for (PenjualanBarangDetail d : listPenjualan) {
                d.setPenjualanBarangHead(p);
            }
            Report report = new Report();
            report.printInvoice(listPenjualan, p.getTotalPenjualan());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void printInvoiceSoftCopyKendal(PenjualanBarangHead p) {
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
    private void printInvoiceSoftCopyTerboyo(PenjualanBarangHead p) {
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
                Sheet sheet = workbook.createSheet("Data Penjualan");
                int rc = 0;
                int c = 10;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Status : " + groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penjualan");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pengiriman");
                sheet.getRow(rc).getCell(2).setCellValue("Tgl Penjualan");
                sheet.getRow(rc).getCell(3).setCellValue("Gudang");
                sheet.getRow(rc).getCell(4).setCellValue("Customer");
                sheet.getRow(rc).getCell(5).setCellValue("Customer Invoice");
                sheet.getRow(rc).getCell(6).setCellValue("Sales");
                sheet.getRow(rc).getCell(7).setCellValue("Total Penjualan");
                sheet.getRow(rc).getCell(8).setCellValue("Pembayaran");
                sheet.getRow(rc).getCell(9).setCellValue("Sisa Pembayaran");
                rc++;
                double penjualan = 0;
                double pembayaran = 0;
                double sisaPembayaran = 0;
                for (PenjualanBarangHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPenjualan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPengiriman())));
                    sheet.getRow(rc).getCell(2).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPenjualan())));
                    sheet.getRow(rc).getCell(3).setCellValue(b.getKodeGudang());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getCustomer().getNama());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getCustomerInvoice().getNama());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getSales().getNama());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getTotalPenjualan());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getPembayaran());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getSisaPembayaran());
                    rc++;
                    penjualan = penjualan + b.getTotalPenjualan();
                    pembayaran = pembayaran + b.getPembayaran();
                    sisaPembayaran = sisaPembayaran + b.getSisaPembayaran();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(7).setCellValue(penjualan);
                sheet.getRow(rc).getCell(8).setCellValue(pembayaran);
                sheet.getRow(rc).getCell(9).setCellValue(sisaPembayaran);
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
