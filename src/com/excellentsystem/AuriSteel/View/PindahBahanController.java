/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.PindahBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBahanHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PindahBahanDetail;
import com.excellentsystem.AuriSteel.Model.PindahBahanHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPindahBahanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
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
 * @author ASUS
 */
public class PindahBahanController {

    @FXML
    private TableView<PindahBahanHead> pindahBahanTable;
    @FXML
    private TableColumn<PindahBahanHead, String> noPindahColumn;
    @FXML
    private TableColumn<PindahBahanHead, String> tglPindahColumn;
    @FXML
    private TableColumn<PindahBahanHead, String> gudangAsalColumn;
    @FXML
    private TableColumn<PindahBahanHead, String> gudangTujuanColumn;
    @FXML
    private TableColumn<PindahBahanHead, String> supirColumn;
    @FXML
    private TableColumn<PindahBahanHead, String> catatanColumn;
    @FXML
    private TableColumn<PindahBahanHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;

    private ObservableList<PindahBahanHead> allPindah = FXCollections.observableArrayList();
    private ObservableList<PindahBahanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPindahColumn.setCellValueFactory(cellData -> cellData.getValue().noPindahProperty());
        noPindahColumn.setCellFactory(col -> Function.getWrapTableCell(noPindahColumn));

        tglPindahColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPindah())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPindahColumn.setCellFactory(col -> Function.getWrapTableCell(tglPindahColumn));
        tglPindahColumn.setComparator(Function.sortDate(tglLengkap));

        gudangAsalColumn.setCellValueFactory(cellData -> cellData.getValue().gudangAsalProperty());
        gudangAsalColumn.setCellFactory(col -> Function.getWrapTableCell(gudangAsalColumn));

        gudangTujuanColumn.setCellValueFactory(cellData -> cellData.getValue().gudangTujuanProperty());
        gudangTujuanColumn.setCellFactory(col -> Function.getWrapTableCell(gudangTujuanColumn));

        supirColumn.setCellValueFactory(cellData -> cellData.getValue().supirProperty());
        supirColumn.setCellFactory(col -> Function.getWrapTableCell(supirColumn));

        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));

        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pindah Bahan");
        addNew.setOnAction((ActionEvent e) -> {
            newPindah();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPindah();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Pindah Bahan") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        pindahBahanTable.setContextMenu(rm);
        pindahBahanTable.setRowFactory((TableView<PindahBahanHead> tableView) -> {
            final TableRow<PindahBahanHead> row = new TableRow<PindahBahanHead>() {
                @Override
                public void updateItem(PindahBahanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pindah Bahan");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPindah();
                        });
                        MenuItem detail = new MenuItem("Detail Pindah Bahan");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPindah(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pindah Bahan");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPindah(item);
                        });
                        MenuItem suratJalan = new MenuItem("Print Surat Jalan");
                        suratJalan.setOnAction((ActionEvent e) -> {
                            printSuratJalan(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPindah();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Pindah Bahan") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Pindah Bahan") && o.isStatus()) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Batal Pindah Bahan") && o.isStatus()) {
                                rm.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Surat Jalan Pindah Bahan") && o.isStatus()) {
                                rm.getItems().add(suratJalan);
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
                            if (o.getJenis().equals("Detail Pindah Bahan") && o.isStatus()) {
                                lihatDetailPindah(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allPindah.addListener((ListChangeListener.Change<? extends PindahBahanHead> change) -> {
            searchPengiriman();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPengiriman();
                });
        filterData.addAll(allPindah);
        pindahBahanTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPindah();
    }

    @FXML
    private void getPindah() {
        Task<List<PindahBahanHead>> task = new Task<List<PindahBahanHead>>() {
            @Override
            public List<PindahBahanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PindahBahanHead> allPindah = PindahBahanHeadDAO.getAllByDateAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    return allPindah;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPindah.clear();
            allPindah.addAll(task.getValue());
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
            for (PindahBahanHead temp : allPindah) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPindah())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPindah())))
                            || checkColumn(temp.getGudangAsal())
                            || checkColumn(temp.getGudangTujuan())
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

    private void newPindah() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPindahBahan.fxml");
        NewPindahBahanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewPindah();
        controller.saveButton.setOnAction((event) -> {
            if (controller.gudangAsalCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang asal belum dipilih");
            } else if (controller.gudangTujuanCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang tujuan belum dipilih");
            } else if (controller.gudangTujuanCombo.getSelectionModel().getSelectedItem().equals(
                    controller.gudangAsalCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang tujuan belum dipilih");
            } else if (controller.allPindahBahanDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PindahBahanHead p = new PindahBahanHead();
                            p.setGudangAsal(controller.gudangAsalCombo.getSelectionModel().getSelectedItem());
                            p.setGudangTujuan(controller.gudangTujuanCombo.getSelectionModel().getSelectedItem());
                            p.setSupir(controller.namaSupirField.getText());
                            p.setCatatan("");
                            p.setKodeUser(sistem.getUser().getKodeUser());
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            p.setStatus("true");
                            p.setListPindahBahanDetail(controller.allPindahBahanDetail);
                            return Service.newPindahBahan(con, p);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPindah();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pindah bahan berhasil disimpan");
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

    private void batalPindah(PindahBahanHead p) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pindah bahan " + p.getNoPindah() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalPindahBahan(con, p);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPindah();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data pindah bahan berhasil dibatal");
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

    private void lihatDetailPindah(PindahBahanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPindahBahan.fxml");
        NewPindahBahanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPindah(p.getNoPindah());
    }

    private void printSuratJalan(PindahBahanHead p) {
        try (Connection con = Koneksi.getConnection()) {
            List<PindahBahanDetail> listPindah = PindahBahanDetailDAO.getAllPindahBahanDetail(con, p.getNoPindah());
            for (PindahBahanDetail d : listPindah) {
                d.setPindahBahanHead(p);
            }
            Report report = new Report();
            report.printSuratJalanPindahBahan(listPindah);
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
                Sheet sheet = workbook.createSheet("Data Pindah Bahan");
                int rc = 0;
                int c = 7;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pindah");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pindah");
                sheet.getRow(rc).getCell(2).setCellValue("Gudang Asal");
                sheet.getRow(rc).getCell(3).setCellValue("Gudang Tujuan");
                sheet.getRow(rc).getCell(4).setCellValue("Supir");
                sheet.getRow(rc).getCell(5).setCellValue("Catatan");
                sheet.getRow(rc).getCell(6).setCellValue("Kode User");
                rc++;
                for (PindahBahanHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPindah());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPindah())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getGudangAsal());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getGudangTujuan());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getSupir());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getCatatan());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getKodeUser());
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
