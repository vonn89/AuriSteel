/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.MesinDAO;
import com.excellentsystem.AuriSteel.DAO.MesinDetailBarangDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.Mesin;
import com.excellentsystem.AuriSteel.Model.MesinDetailBarang;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataMesinController {

    @FXML
    private TableView<Mesin> mesinTable;
    @FXML
    private TableColumn<Mesin, String> kodeMesinColumn;
    @FXML
    private TableColumn<Mesin, Number> kapasitasColumn;

    @FXML
    private CheckBox checkBarangJadi;
    @FXML
    private TableView<MesinDetailBarang> barangJadiTable;
    @FXML
    private TableColumn<MesinDetailBarang, String> kodeBarangColumn;
    @FXML
    private TableColumn<MesinDetailBarang, Boolean> statusBarangColumn;

    @FXML
    private TextField kodeMesinField;
    @FXML
    private TextField kapasitasField;

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private List<Barang> listBarang;
    private ObservableList<Mesin> allMesin = FXCollections.observableArrayList();
    private ObservableList<MesinDetailBarang> allMesinProduksi = FXCollections.observableArrayList();
    private Main mainApp;
    private String status;

    public void initialize() {
        kodeMesinColumn.setCellValueFactory(cellData -> cellData.getValue().kodeMesinProperty());
        kodeMesinColumn.setCellFactory(col -> Function.getWrapTableCell(kodeMesinColumn));

        kapasitasColumn.setCellValueFactory(cellData -> cellData.getValue().kapasitasProperty());
        kapasitasColumn.setCellFactory(col -> Function.getTableCell());

        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTableCell(kodeBarangColumn));
        statusBarangColumn.setCellValueFactory((TableColumn.CellDataFeatures<MesinDetailBarang, Boolean> param) -> {
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(param.getValue().isStatus());
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                param.getValue().setStatus(newValue);
                barangJadiTable.refresh();
            });
            return booleanProp;
        });
        statusBarangColumn.setCellFactory((TableColumn<MesinDetailBarang, Boolean> p) -> {
            CheckBoxTableCell<MesinDetailBarang, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        mesinTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectMesin(newValue));

        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Mesin");
        addNew.setOnAction((ActionEvent event) -> {
            newMesin();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getMesin();
        });
        rm.getItems().addAll(addNew, refresh);
        mesinTable.setContextMenu(rm);
        mesinTable.setRowFactory(table -> {
            TableRow<Mesin> row = new TableRow<Mesin>() {
                @Override
                public void updateItem(Mesin item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Mesin");
                        addNew.setOnAction((ActionEvent event) -> {
                            newMesin();
                        });
                        MenuItem hapus = new MenuItem("Hapus Mesin");
                        hapus.setOnAction((ActionEvent event) -> {
                            delete(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getMesin();
                        });
                        rm.getItems().addAll(addNew, hapus, refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        Function.setNumberField(kapasitasField);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        mesinTable.setItems(allMesin);
        barangJadiTable.setItems(allMesinProduksi);
        getListBarang();
        getMesin();
    }

    @FXML
    private void checkBarang() {
        for (MesinDetailBarang d : allMesinProduksi) {
            d.setStatus(checkBarangJadi.isSelected());
        }
        barangJadiTable.refresh();
    }

    private void getListBarang() {
        Task<List<Barang>> task = new Task<List<Barang>>() {
            @Override
            public List<Barang> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return BarangDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            listBarang = task.getValue();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }

    @FXML
    private void getMesin() {
        Task<List<Mesin>> task = new Task<List<Mesin>>() {
            @Override
            public List<Mesin> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Mesin> allMesin = MesinDAO.getAll(con);
                    List<MesinDetailBarang> allProduksi = MesinDetailBarangDAO.getAll(con);
                    for (Mesin m : allMesin) {
                        List<MesinDetailBarang> detail = new ArrayList<>();
                        for (MesinDetailBarang o : allProduksi) {
                            if (m.getKodeMesin().equals(o.getKodeMesin())) {
                                detail.add(o);
                            }
                        }
                        m.setListDetailBarang(detail);

                    }
                    return allMesin;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allMesin.clear();
            allMesin.addAll(task.getValue());
            reset();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }

    @FXML
    private void reset() {
        allMesinProduksi.clear();
        kodeMesinField.setText("");
        kapasitasField.setText("");
        kodeMesinField.setDisable(true);
        kapasitasField.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        status = "";
    }

    public void selectMesin(Mesin u) {
        if (u != null) {
            status = "update";
            allMesinProduksi.clear();
            allMesinProduksi.addAll(u.getListDetailBarang());
            kodeMesinField.setText(u.getKodeMesin());
            kapasitasField.setText(df.format(u.getKapasitas()));
            kodeMesinField.setDisable(true);
            kapasitasField.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);

        }
    }

    public void newMesin() {
        status = "new";
        kodeMesinField.setText("");
        kapasitasField.setText("0");
        kodeMesinField.setDisable(false);
        kapasitasField.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);

        List<MesinDetailBarang> tempMesin = new ArrayList<>();
        for (Barang b : listBarang) {
            MesinDetailBarang temp = new MesinDetailBarang();
            temp.setKodeBarang(b.getKodeBarang());
            temp.setStatus(checkBarangJadi.isSelected());
            tempMesin.add(temp);
        }
        allMesinProduksi.clear();
        allMesinProduksi.addAll(tempMesin);
    }

    public void saveMesin() {
        if (kodeMesinField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode mesin masih kosong");
        } else {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Mesin mesin = new Mesin();
                        mesin.setKodeMesin(kodeMesinField.getText());
                        mesin.setKapasitas(Double.parseDouble(kapasitasField.getText().replaceAll(",", "")));
                        for (MesinDetailBarang v : allMesinProduksi) {
                            v.setKodeMesin(kodeMesinField.getText());
                        }
                        mesin.setListDetailBarang(allMesinProduksi);

                        if (status.equals("update")) {
                            return Service.updateMesin(con, mesin);
                        } else if (status.equals("new")) {
                            return Service.newMesin(con, mesin);
                        } else {
                            return "false";
                        }
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getMesin();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data mesin berhasil disimpan");
                    reset();
                } else {
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }

    public void delete(Mesin mesin) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete mesin " + mesin.getKodeMesin() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deleteMesin(con, mesin);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getMesin();
                String message = task.getValue();
                if (message.equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data mesin berhasil dihapus");
                } else {
                    mainApp.showMessage(Modality.NONE, "Failed", message);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }

}
