/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
 * @author Xtreme
 */
public class CetakBarcodeController {

    @FXML
    private TableView<StokBahan> bahanTable;
    @FXML
    private TableColumn<StokBahan, Boolean> checkColumn;
    @FXML
    private TableColumn<StokBahan, String> kodeBahanColumn;
    @FXML
    private TableColumn<StokBahan, String> kategoriBahanColumn;
    @FXML
    private TableColumn<StokBahan, String> namaBahanColumn;
    @FXML
    private TableColumn<StokBahan, Number> beratKotorColumn;
    @FXML
    private TableColumn<StokBahan, Number> beratBersihColumn;
    @FXML
    private TableColumn<StokBahan, Number> panjangColumn;
    @FXML
    private TableColumn<StokBahan, Number> stokAkhirColumn;

    @FXML
    private CheckBox checkAll;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalLabel;

    private ObservableList<StokBahan> allBahan = FXCollections.observableArrayList();
    private ObservableList<StokBahan> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().isCheckedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return bahanTable.getItems().get(v).isCheckedProperty();
        }));

        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTableCell(kodeBahanColumn));

        kategoriBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().kodeKategoriProperty());
        kategoriBahanColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriBahanColumn));

        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTableCell(namaBahanColumn));

        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());

        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());

        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTableCell());

        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> Function.getTableCell());

        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            bahanTable.refresh();
        });
        rm.getItems().addAll(refresh);
        bahanTable.setContextMenu(rm);
        bahanTable.setRowFactory((TableView<StokBahan> tableView) -> {
            final TableRow<StokBahan> row = new TableRow<StokBahan>() {
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
                        hitungTotal();
                    }
                }
            });
            return row;
        });

        allBahan.addListener((ListChangeListener.Change<? extends StokBahan> change) -> {
            searchBahan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchBahan();
                });
        filterData.addAll(allBahan);
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
        bahanTable.setItems(filterData);
    }

    @FXML
    private void checkAllHandle() {
        for (StokBahan d : allBahan) {
            d.setIsChecked(checkAll.isSelected());
        }
        bahanTable.refresh();
        hitungTotal();
    }

    public void setBahan(List<StokBahan> listStok) {
        allBahan.clear();
        allBahan.addAll(listStok);
    }

    @FXML
    private void hitungTotal() {
        int qty = 0;
        for (StokBahan d : allBahan) {
            if (d.isIsChecked()) {
                qty = qty + 1;
            }
        }
        totalLabel.setText(df.format(qty));
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchBahan() {
        filterData.clear();
        for (StokBahan temp : allBahan) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getBahan().getKodeBahan())
                        || checkColumn(temp.getKodeGudang())
                        || checkColumn(temp.getBahan().getNamaBahan())
                        || checkColumn(temp.getBahan().getNoKontrak())
                        || checkColumn(temp.getBahan().getKodeKategori())
                        || checkColumn(df.format(temp.getBahan().getHargaBeli()))
                        || checkColumn(df.format(temp.getBahan().getBeratKotor()))
                        || checkColumn(df.format(temp.getBahan().getBeratBersih()))
                        || checkColumn(df.format(temp.getBahan().getPanjang()))
                        || checkColumn(df.format(temp.getStokAkhir()))) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void cetakBarcode() {
        try {
            List<Bahan> listBarcode = new ArrayList<>();
            for (StokBahan s : filterData) {
                if (s.isIsChecked()) {
                    listBarcode.add(s.getBahan());
                }
            }
            Report report = new Report();
            report.printBarcode(listBarcode);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
