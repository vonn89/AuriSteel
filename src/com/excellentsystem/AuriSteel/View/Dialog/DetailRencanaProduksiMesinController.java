/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailRencanaProduksiMesinController {
    
    @FXML
    private TableView<RencanaProduksi> rencanaProduksiTable;
    @FXML
    private TableColumn<RencanaProduksi, String> tujuanKirimColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> noPemesananColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> customerColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> barangColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> keteranganColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> catatanInternColumn;
    @FXML
    private TableColumn<RencanaProduksi, String> salesColumn;
    @FXML
    private TableColumn<RencanaProduksi, Number> qtyColumn;
    @FXML
    private TableColumn<RencanaProduksi, Number> tonaseColumn;
    
    private ObservableList<RencanaProduksi> allRencanaProduksi = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    
    public void initialize() {
        tujuanKirimColumn.setCellValueFactory(cellData -> cellData.getValue().tujuanKirimProperty());
        tujuanKirimColumn.setCellFactory(col -> Function.getWrapTableCell(tujuanKirimColumn));
        
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));
        
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        customerColumn.setCellFactory(col -> Function.getWrapTableCell(customerColumn));
        
        barangColumn.setCellValueFactory(cellData -> cellData.getValue().barangProperty());
        barangColumn.setCellFactory(col -> Function.getWrapTableCell(barangColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanInternColumn.setCellFactory(col -> Function.getWrapTableCell(catatanInternColumn));
        
        salesColumn.setCellValueFactory(cellData -> cellData.getValue().salesProperty());
        salesColumn.setCellFactory(col -> Function.getWrapTableCell(salesColumn));
        
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        
        tonaseColumn.setCellValueFactory(cellData -> cellData.getValue().tonaseProperty());
        tonaseColumn.setCellFactory(col -> Function.getTableCell());
        
        rencanaProduksiTable.setItems(allRencanaProduksi);
    }
    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void setListProduksi(List<RencanaProduksi> x) {
        allRencanaProduksi.clear();
        allRencanaProduksi.addAll(x);
    }
    
    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
