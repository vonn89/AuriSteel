/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Barang;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddBarangPembelianController {

    @FXML
    private TableView<Barang> barangTable;
    @FXML
    private TableColumn<Barang, String> kodeBarangColumn;
    @FXML
    private TableColumn<Barang, String> namaBarangColumn;
    @FXML
    private TableColumn<Barang, String> satuanColumn;

    @FXML
    private TextField namaBarangField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField qtyField;
    @FXML
    private TextField satuanField;
    @FXML
    public TextField hargaBeliField;
    @FXML
    public TextField totalField;
    @FXML
    private TextField searchField;
    @FXML
    public Button addButton;
    public Barang barang;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<Barang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        hargaBeliField.setOnKeyReleased((event) -> {
            try {
                String string = hargaBeliField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        hargaBeliField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        hargaBeliField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    hargaBeliField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                hargaBeliField.end();
            } catch (NumberFormatException e) {
                hargaBeliField.undo();
            }
            hitungTotal();
        });
        qtyField.setOnKeyReleased((event) -> {
            try {
                String string = qtyField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        qtyField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                qtyField.end();
            } catch (NumberFormatException e) {
                qtyField.undo();
            }
            hitungTotal();
        });
        barangTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectBarang(newValue);
        });
        allBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        barangTable.setItems(filterData);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        getBarang();
    }

    public void getBarang() {
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
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            selectBarang(null);
            allBarang.clear();
            allBarang.addAll(task.getValue());
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

    private void searchBarang() {
        filterData.clear();
        for (Barang temp : allBarang) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getKodeBarang())
                        || checkColumn(temp.getNamaBarang())
                        || checkColumn(temp.getSpesifikasi())
                        || checkColumn(temp.getSatuan())
                        || checkColumn(df.format(temp.getBerat()))
                        || checkColumn(df.format(temp.getHargaJual()))) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void hitungTotal() {
        if (qtyField.getText().equals("")) {
            qtyField.setText("0");
        }
        if (hargaBeliField.getText().equals("")) {
            hargaBeliField.setText("0");
        }
        totalField.setText(df.format(
                Double.parseDouble(qtyField.getText().replaceAll(",", ""))
                * Double.parseDouble(hargaBeliField.getText().replaceAll(",", ""))
        ));
    }

    private void selectBarang(Barang value) {
        barang = null;
        namaBarangField.setText("");
        keteranganField.setText("");
        satuanField.setText("");
        hargaBeliField.setText("0");
        qtyField.setText("0");
        totalField.setText("0");
        if (value != null) {
            barang = value;
            namaBarangField.setText(value.getNamaBarang());
            satuanField.setText(value.getSatuan());
            hitungTotal();
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
