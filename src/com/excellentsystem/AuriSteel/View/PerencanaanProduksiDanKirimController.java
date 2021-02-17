/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.RencanaProduksiDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PerencanaanProduksiDanKirimController {
    
    @FXML
    private TableView hariTable;
    @FXML
    private TableColumn<Map, String> hariColumn = new TableColumn<>("hari");
    private ObservableList<Map<String, Object>> hari = FXCollections.<Map<String, Object>>observableArrayList();
    
    @FXML
    private TreeTableView<RencanaProduksi> rencanaProduksiDanPengirimanTable;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> tujuanKirimColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> noPemesananColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> customerColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> barangColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> keteranganColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> catatanInternColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, String> salesColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, Number> qtyColumn;
    @FXML
    private TreeTableColumn<RencanaProduksi, Number> tonaseColumn;
    
    private ObservableList<RencanaProduksi> allRencanaProduksi = FXCollections.observableArrayList();
    private Main mainApp;
    private final TreeItem<RencanaProduksi> root = new TreeItem<>();
    
    public void initialize() {
//        hariColumn.setCellValueFactory(cellData -> cellData.getValue().hariProperty());
//        hariColumn.setCellFactory(col -> Function.getWrapTableCell(hariColumn));

        tujuanKirimColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().tujuanKirimProperty());
        tujuanKirimColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tujuanKirimColumn));
        
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPemesananColumn));
        
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().customerProperty());
        customerColumn.setCellFactory(col -> Function.getWrapTreeTableCell(customerColumn));
        
        barangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().barangProperty());
        barangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(barangColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTreeTableCell(keteranganColumn));
        
        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().catatanProperty());
        catatanInternColumn.setCellFactory(col -> Function.getWrapTreeTableCell(catatanInternColumn));
        
        salesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().salesProperty());
        salesColumn.setCellFactory(col -> Function.getWrapTreeTableCell(salesColumn));
        
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        tonaseColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().tonaseProperty());
        tonaseColumn.setCellFactory(col -> Function.getTreeTableCell());

//        final ContextMenu rm = new ContextMenu();
//        MenuItem spk = new MenuItem("Print SPK");
//        spk.setOnAction((ActionEvent e) -> {
//            printSPK();
//        });
//        MenuItem export = new MenuItem("Export Excel");
//        export.setOnAction((ActionEvent e) -> {
////            exportExcel();
//        });
//        MenuItem refresh = new MenuItem("Refresh");
//        refresh.setOnAction((ActionEvent e) -> {
//            getPermintaan();
//        });
//        for (Otoritas o : sistem.getUser().getOtoritas()) {
//            if (o.getJenis().equals("Print SPK") && o.isStatus()) {
//                rm.getItems().add(spk);
//            }
//            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
//                rm.getItems().add(export);
//            }
//        }
//        rm.getItems().addAll(refresh);
//        rencanaProduksiDanPengirimanTable.setContextMenu(rm);
        hariTable.setItems(hari);
        
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        Map<String, Object> item1 = new HashMap<>();
        item1.put("hari", "Senin");
        hari.add(item1);
        Map<String, Object> item2 = new HashMap<>();
        item1.put("hari", "Selasa");
        hari.add(item2);
        Map<String, Object> item3 = new HashMap<>();
        item1.put("hari", "Rabu");
        hari.add(item3);
        getPermintaan();
    }
    
    @FXML
    private void getPermintaan() {
        Task<List<RencanaProduksi>> task = new Task<List<RencanaProduksi>>() {
            @Override
            public List<RencanaProduksi> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return RencanaProduksiDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allRencanaProduksi.clear();
                allRencanaProduksi.addAll(task.getValue());
                setTable();
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            task.getException().printStackTrace();
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    
    private void setTable() throws Exception {
        if (rencanaProduksiDanPengirimanTable.getRoot() != null) {
            rencanaProduksiDanPengirimanTable.getRoot().getChildren().clear();
        }
        List<Integer> noUrut = new ArrayList<>();
        for (RencanaProduksi temp : allRencanaProduksi) {
            if (!noUrut.contains(temp.getNoKirim())) {
                noUrut.add(temp.getNoKirim());
            }
        }
        for (int temp : noUrut) {
            RencanaProduksi rp = new RencanaProduksi();
            rp.setNoKirim(temp);
            TreeItem<RencanaProduksi> parent = new TreeItem<>(rp);
            double tonase = 0;
            for (RencanaProduksi temp2 : allRencanaProduksi) {
                if (temp == temp2.getNoKirim()) {
                    TreeItem<RencanaProduksi> child = new TreeItem<>(temp2);
                    parent.getChildren().addAll(child);
                    tonase = tonase + temp2.getTonase();
                }
            }
            parent.getValue().setTonase(tonase);
            root.getChildren().add(parent);
        }
        rencanaProduksiDanPengirimanTable.setRoot(root);
    }
}
