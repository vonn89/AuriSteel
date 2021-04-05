/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddBahanController {

    @FXML
    public TreeTableView<StokBahan> bahanTable;
    @FXML
    private TreeTableColumn<StokBahan, String> kodeBahanColumn;
    @FXML
    private TreeTableColumn<StokBahan, String> namaBahanColumn;
    @FXML
    private TreeTableColumn<StokBahan, Number> beratKotorColumn;
    @FXML
    private TreeTableColumn<StokBahan, Number> beratBersihColumn;
    @FXML
    private TreeTableColumn<StokBahan, Number> panjangColumn;
    @FXML
    private TreeTableColumn<StokBahan, Number> stokAkhirColumn;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBahan> allBahan = FXCollections.observableArrayList();
    private final ObservableList<StokBahan> filterData = FXCollections.observableArrayList();
    private final TreeItem<StokBahan> root = new TreeItem<>();

    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBahanColumn));

        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBahanColumn));

        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());

        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());

        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());

        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> Function.getTreeTableCell());

        allBahan.addListener((ListChangeListener.Change<? extends StokBahan> change) -> {
            searchBahan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
    }

    public void getBahan(String kodeGudang) {
        Task<List<StokBahan>> task = new Task<List<StokBahan>>() {
            @Override
            public List<StokBahan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Bahan> listBahan = BahanDAO.getAllByStatus(con, "true");
                    List<StokBahan> listStok = StokBahanDAO.getAllByDateAndGudang(con,
                            tglBarang.format(Function.getServerDate(con)), kodeGudang);
                    List<StokBahan> allStok = new ArrayList<>();
                    for (StokBahan s : listStok) {
                        if (s.getStokAkhir() > 0) {
                            for (Bahan b : listBahan) {
                                if (s.getKodeBahan().equals(b.getKodeBahan())) {
                                    s.setBahan(b);
                                }
                            }
                            allStok.add(s);
                        }
                    }
                    return allStok;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            allBahan.clear();
            allBahan.addAll(task.getValue());
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

    @FXML
    private void searchBahan() {
        filterData.clear();
        for (StokBahan temp : allBahan) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {

                if (checkColumn(temp.getKodeBahan())
                        || checkColumn(temp.getBahan().getNamaBahan())
                        || checkColumn(temp.getBahan().getKodeKategori())
                        || checkColumn(temp.getBahan().getSpesifikasi())
                        || checkColumn(temp.getBahan().getKeterangan())
                        || checkColumn(df.format(temp.getBahan().getBeratKotor()))
                        || checkColumn(df.format(temp.getBahan().getBeratBersih()))
                        || checkColumn(df.format(temp.getBahan().getPanjang()))) {
                    filterData.add(temp);
                }
            }
        }
        setTable();
    }

    private void setTable() {
        if (bahanTable.getRoot() != null) {
            bahanTable.getRoot().getChildren().clear();
        }
        List<String> kategori = new ArrayList<>();
        for (StokBahan temp : filterData) {
            if (!kategori.contains(temp.getBahan().getKodeKategori())) {
                kategori.add(temp.getBahan().getKodeKategori());
            }
        }
        for (String temp : kategori) {
            double totalBeratKotor = 0;
            double totalBeratBersih = 0;
            double totalPanjang = 0;
            double totalStok = 0;
            StokBahan head = new StokBahan();
            Bahan b = new Bahan();
            head.setBahan(b);
            TreeItem<StokBahan> parent = new TreeItem<>(head);
            for (StokBahan detail : filterData) {
                if (temp.equals(detail.getBahan().getKodeKategori())) {
                    TreeItem<StokBahan> child = new TreeItem<>(detail);
                    parent.getChildren().add(child);
                    totalBeratKotor = totalBeratKotor + detail.getBahan().getBeratKotor();
                    totalBeratBersih = totalBeratBersih + detail.getBahan().getBeratBersih();
                    totalPanjang = totalPanjang + detail.getBahan().getPanjang();
                    totalStok = totalStok + detail.getStokAkhir();
                }
            }
            parent.getValue().getBahan().setKodeBahan(temp);
            parent.getValue().getBahan().setBeratKotor(totalBeratKotor);
            parent.getValue().getBahan().setBeratBersih(totalBeratBersih);
            parent.getValue().getBahan().setPanjang(totalPanjang);
            parent.getValue().setStokAkhir(totalStok);
            root.getChildren().add(parent);
        }
        bahanTable.setRoot(root);
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
