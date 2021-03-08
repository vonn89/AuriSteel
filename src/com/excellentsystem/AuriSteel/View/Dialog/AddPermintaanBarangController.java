/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import java.sql.Connection;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddPermintaanBarangController {

    @FXML
    private TableView<PemesananBarangDetail> permintaanTable;
    @FXML
    private TableColumn<PemesananBarangDetail, Boolean> checkColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> noPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> tglPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> namaCustomerColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> alamatCustomerColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> kodeBarangColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> catatanInternColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> satuanColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, Number> qtyColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, Number> qtyTerkirimColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, Number> qtySisaColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, Number> tonaseColumn;
    @FXML
    private TableColumn<PemesananBarangDetail, String> salesColumn;

    @FXML
    private CheckBox checkAll;
    @FXML
    private Label totalQtyLabel;
    @FXML
    private Label totalTonaseLabel;
    @FXML
    public ComboBox<String> hariCombo;
    @FXML
    public TextField tujuanField;
    @FXML
    public Button addButton;
    @FXML
    private TextField searchField;
    private ObservableList<String> allHari = FXCollections.observableArrayList();
    private ObservableList<PemesananBarangDetail> allPermintaan = FXCollections.observableArrayList();
    public ObservableList<PemesananBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));

        tglPemesananColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPemesananBarangHead().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananBarangHead().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananBarangHead().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));

        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTableCell(kodeBarangColumn));

        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTableCell(namaBarangColumn));

        salesColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananBarangHead().getSales().namaProperty());
        salesColumn.setCellFactory(col -> Function.getWrapTableCell(salesColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().catatanInternProperty());
        catatanInternColumn.setCellFactory(col -> Function.getWrapTableCell(catatanInternColumn));

        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        satuanColumn.setCellFactory(col -> Function.getWrapTableCell(satuanColumn));

        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());

        qtyTerkirimColumn.setCellValueFactory(cellData -> cellData.getValue().qtyTerkirimProperty());
        qtyTerkirimColumn.setCellFactory(col -> Function.getTableCell());

        qtySisaColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getQty() - cellData.getValue().getQtyTerkirim());
        });
        qtySisaColumn.setCellFactory(col -> Function.getTableCell());

        tonaseColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty((cellData.getValue().getQty() - cellData.getValue().getQtyTerkirim()) * cellData.getValue().getBarang().getBerat());
        });
        tonaseColumn.setCellFactory(col -> Function.getTableCell());

        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return permintaanTable.getItems().get(v).statusProperty();
        }));

        final ContextMenu rm = new ContextMenu();
        MenuItem spk = new MenuItem("Print SPK");
        spk.setOnAction((ActionEvent e) -> {
            printSPK();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPermintaan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Print SPK") && o.isStatus()) {
                rm.getItems().add(spk);
            }
        }
        rm.getItems().addAll(refresh);
        permintaanTable.setContextMenu(rm);
        permintaanTable.setRowFactory((TableView<PemesananBarangDetail> tableView) -> {
            final TableRow<PemesananBarangDetail> row = new TableRow<PemesananBarangDetail>() {
            };
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    double hutang = newValue.getPemesananBarangHead().getCustomer().getHutang();
                    double limitHutang = newValue.getPemesananBarangHead().getCustomer().getLimitHutang();
                    double sisaPemesanan = 0;
                    double downpayment = newValue.getPemesananBarangHead().getSisaDownPayment();
                    for (PemesananBarangDetail d : newValue.getPemesananBarangHead().getListPemesananBarangDetail()) {
                        sisaPemesanan = sisaPemesanan + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
                    }
                    if (limitHutang - hutang - sisaPemesanan + downpayment < 0) {
                        row.setStyle("-fx-background-color: #FFD8D1");//red
                    } else {
                        row.setStyle("");
                    }
                } else {
                    row.setStyle("");
                }
            });
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
        allPermintaan.addListener((ListChangeListener.Change<? extends PemesananBarangDetail> change) -> {
            searchPermintaan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPermintaan();
                });
        filterData.addAll(allPermintaan);
        permintaanTable.setItems(filterData);
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
        getPermintaan();
        allHari.addAll(Function.hari);
        hariCombo.setItems(allHari);
    }

    private void hitungTotal() {
        double qty = 0;
        double berat = 0;
        for (PemesananBarangDetail d : filterData) {
            if (d.isStatus()) {
                qty = qty + d.getQty() - d.getQtyTerkirim();
                berat = berat + (d.getQty() - d.getQtyTerkirim()) * d.getBarang().getBerat();
            }
        }
        totalQtyLabel.setText(df.format(qty));
        totalTonaseLabel.setText(df.format(berat));
    }

    @FXML
    private void getPermintaan() {
        Task<List<PemesananBarangDetail>> task = new Task<List<PemesananBarangDetail>>() {
            @Override
            public List<PemesananBarangDetail> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allPegawai = PegawaiDAO.getAllByStatus(con, "%");
                    List<Barang> allBarang = BarangDAO.getAllByStatus(con, "%");
                    List<PemesananBarangDetail> allPemesananDetail = PemesananBarangDetailDAO.getAllByDateAndStatus(con,
                            "2000-01-01", tglBarang.format(Function.getServerDate(con)), "open");
                    List<PemesananBarangHead> allPemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(con,
                            "2000-01-01", tglBarang.format(Function.getServerDate(con)), "open");
                    for (PemesananBarangHead p : allPemesanan) {
                        List<PemesananBarangDetail> detail = new ArrayList<>();
                        for (PemesananBarangDetail d : allPemesananDetail) {
                            if (d.getNoPemesanan().equals(p.getNoPemesanan())) {
                                detail.add(d);
                            }
                        }
                        p.setListPemesananBarangDetail(detail);
                    }
                    List<PemesananBarangDetail> listPemesananDetail = new ArrayList<>();
                    for (PemesananBarangDetail d : allPemesananDetail) {
                        for (PemesananBarangHead h : allPemesanan) {
                            if (d.getNoPemesanan().equals(h.getNoPemesanan())) {
                                d.setPemesananBarangHead(h);
                            }
                        }
                        for (Customer c : allCustomer) {
                            if (d.getPemesananBarangHead().getKodeCustomer().equals(c.getKodeCustomer())) {
                                d.getPemesananBarangHead().setCustomer(c);
                            }
                        }
                        for (Pegawai p : allPegawai) {
                            if (d.getPemesananBarangHead().getKodeSales().equals(p.getKodePegawai())) {
                                d.getPemesananBarangHead().setSales(p);
                            }
                        }
                        for (Barang b : allBarang) {
                            if (d.getKodeBarang().equals(b.getKodeBarang())) {
                                d.setBarang(b);
                            }
                        }
                        if (d.getQty() - d.getQtyTerkirim() != 0) {
                            listPemesananDetail.add(d);
                        }
                    }
                    return listPemesananDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPermintaan.clear();
            allPermintaan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            task.getException().printStackTrace();
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    @FXML
    private void checkAllHandle() {
        for (PemesananBarangDetail d : allPermintaan) {
            d.setStatus(checkAll.isSelected());
        }
        hitungTotal();
        permintaanTable.refresh();
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchPermintaan() {
        try {
            filterData.clear();
            for (PemesananBarangDetail temp : allPermintaan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPemesanan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan())))
                            || checkColumn(temp.getPemesananBarangHead().getKodeCustomer())
                            || checkColumn(temp.getPemesananBarangHead().getCustomer().getNama())
                            || checkColumn(temp.getPemesananBarangHead().getSales().getNama())
                            || checkColumn(temp.getPemesananBarangHead().getCustomer().getAlamat())
                            || checkColumn(temp.getPemesananBarangHead().getPaymentTerm())
                            || checkColumn(df.format(temp.getPemesananBarangHead().getTotalPemesanan()))
                            || checkColumn(df.format(temp.getPemesananBarangHead().getDownPayment()))
                            || checkColumn(temp.getPemesananBarangHead().getCatatan())
                            || checkColumn(temp.getPemesananBarangHead().getKodeSales())
                            || checkColumn(temp.getPemesananBarangHead().getStatus())
                            || checkColumn(temp.getPemesananBarangHead().getKodeUser())
                            || checkColumn(temp.getKodeBarang())
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getCatatanIntern())
                            || checkColumn(df.format(temp.getQty()))
                            || checkColumn(df.format(temp.getQtyTerkirim()))
                            || checkColumn(temp.getSatuan())) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }

    private void printSPK() {
        try {
            List<PemesananBarangDetail> spk = new ArrayList<>();
            for (PemesananBarangDetail b : filterData) {
                if (b.isStatus()) {
                    spk.add(b);
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
