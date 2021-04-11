/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.MesinDAO;
import com.excellentsystem.AuriSteel.DAO.MesinDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.RencanaProduksiDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Mesin;
import com.excellentsystem.AuriSteel.Model.MesinDetailBarang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.AddPermintaanBarangController;
import com.excellentsystem.AuriSteel.View.Dialog.DetailRencanaProduksiController;
import com.excellentsystem.AuriSteel.View.Dialog.DetailRencanaProduksiMesinController;
import com.excellentsystem.AuriSteel.View.Dialog.GantiJadwalDanTujuanController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class RencanaProduksiController {

    @FXML
    private TreeTableView<RencanaProduksi> rencanaProduksiTable;
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

    @FXML
    private ToggleButton seninButton;
    @FXML
    private ToggleButton selasaButton;
    @FXML
    private ToggleButton rabuButton;
    @FXML
    private ToggleButton kamisButton;
    @FXML
    private ToggleButton jumatButton;
    @FXML
    private ToggleButton sabtuButton;

    @FXML
    private Label totalKirimLabel;

    @FXML
    private Label totalQtyLabel;

    @FXML
    private Label totalTonaseLabel;

    private List<Mesin> listMesin;
    private ObservableList<RencanaProduksi> allRencanaProduksi = FXCollections.observableArrayList();
    private ObservableList<RencanaProduksi> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private final TreeItem<RencanaProduksi> root = new TreeItem<>();

    public void initialize() {
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

        final ContextMenu rm = new ContextMenu();
        MenuItem add = new MenuItem("Add New Rencana Produksi");
        add.setOnAction((ActionEvent e) -> {
            addRencanaProduksi();
        });
        MenuItem print = new MenuItem("Print Rencana Produksi");
        print.setOnAction((ActionEvent e) -> {
            printRencanaProduksi();
        });
        MenuItem reset = new MenuItem("Reset Rencana Produksi");
        reset.setOnAction((ActionEvent e) -> {
            deleteAllRencanaProduksi();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getRencana();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Rencana Produksi") && o.isStatus()) {
                rm.getItems().add(add);
            }
            if (o.getJenis().equals("Print Rencana Produksi") && o.isStatus()) {
                rm.getItems().add(print);
            }
            if (o.getJenis().equals("Reset Rencana Produksi") && o.isStatus()) {
                rm.getItems().add(reset);
            }
        }
        rm.getItems().addAll(refresh);
        rencanaProduksiTable.setContextMenu(rm);
        rencanaProduksiTable.setRowFactory((TreeTableView<RencanaProduksi> tableView) -> {
            final TreeTableRow<RencanaProduksi> row = new TreeTableRow<RencanaProduksi>() {
                @Override
                public void updateItem(RencanaProduksi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem add = new MenuItem("Add New Rencana Produksi");
                        add.setOnAction((ActionEvent e) -> {
                            addRencanaProduksi();
                        });
                        MenuItem edit = new MenuItem("Edit Rencana Produksi");
                        edit.setOnAction((ActionEvent e) -> {
                            editRencanaProduksi(item);
                        });
                        MenuItem delete = new MenuItem("Delete Rencana Produksi");
                        delete.setOnAction((ActionEvent e) -> {
                            deleteRencanaProduksi(item);
                        });
                        MenuItem editGroup = new MenuItem("Edit Rencana Produksi");
                        editGroup.setOnAction((ActionEvent e) -> {
                            editGroupRencanaProduksi(item);
                        });
                        MenuItem deleteGroup = new MenuItem("Delete Rencana Produksi");
                        deleteGroup.setOnAction((ActionEvent e) -> {
                            deleteGroupRencanaProduksi(item);
                        });
                        MenuItem reset = new MenuItem("Reset Rencana Produksi");
                        reset.setOnAction((ActionEvent e) -> {
                            deleteAllRencanaProduksi();
                        });
                        MenuItem print = new MenuItem("Print Rencana Produksi");
                        print.setOnAction((ActionEvent e) -> {
                            printRencanaProduksi();
                        });
                        MenuItem printDO = new MenuItem("Print Delivery Order");
                        printDO.setOnAction((ActionEvent e) -> {
                            printDeliveryOrder(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getRencana();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Rencana Produksi") && o.isStatus()) {
                                rm.getItems().add(add);
                            }
                            if (o.getJenis().equals("Edit Rencana Produksi") && o.isStatus() && item.getNoPemesanan() != null) {
                                rm.getItems().add(edit);
                            }
                            if (o.getJenis().equals("Delete Rencana Produksi") && o.isStatus() && item.getNoPemesanan() != null) {
                                rm.getItems().add(delete);
                            }
                            if (o.getJenis().equals("Edit Rencana Produksi") && o.isStatus() && item.getNoPemesanan() == null) {
                                rm.getItems().add(editGroup);
                            }
                            if (o.getJenis().equals("Delete Rencana Produksi") && o.isStatus() && item.getNoPemesanan() == null) {
                                rm.getItems().add(deleteGroup);
                            }
                            if (o.getJenis().equals("Print Rencana Produksi") && o.isStatus()) {
                                rm.getItems().add(print);
                            }
                            if (o.getJenis().equals("Print Delivery Order") && o.isStatus() && item.getNoPemesanan() == null) {
                                rm.getItems().add(printDO);
                            }
                            if (o.getJenis().equals("Reset Rencana Produksi") && o.isStatus()) {
                                rm.getItems().addAll(reset);
                            }
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        allRencanaProduksi.addListener((ListChangeListener.Change<? extends RencanaProduksi> change) -> {
            filterData();
        });
        filterData.addAll(allRencanaProduksi);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getRencana();
    }

    private void getRencana() {
        Task<List<RencanaProduksi>> task = new Task<List<RencanaProduksi>>() {
            @Override
            public List<RencanaProduksi> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    listMesin = MesinDAO.getAll(con);
                    List<MesinDetailBarang> listDetailBarang = MesinDetailBarangDAO.getAll(con);
                    for (Mesin m : listMesin) {
                        List<MesinDetailBarang> detail = new ArrayList<>();
                        for (MesinDetailBarang d : listDetailBarang) {
                            if (d.getKodeMesin().equals(m.getKodeMesin())) {
                                detail.add(d);
                            }
                        }
                        m.setListDetailBarang(detail);
                    }
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

    @FXML
    private void filterData() {
        try {
            filterData.clear();
            for (RencanaProduksi rp : allRencanaProduksi) {
                if (seninButton.isSelected() && rp.getHari().equalsIgnoreCase("senin")) {
                    filterData.add(rp);
                } else if (selasaButton.isSelected() && rp.getHari().equalsIgnoreCase("selasa")) {
                    filterData.add(rp);
                } else if (rabuButton.isSelected() && rp.getHari().equalsIgnoreCase("rabu")) {
                    filterData.add(rp);
                } else if (kamisButton.isSelected() && rp.getHari().equalsIgnoreCase("kamis")) {
                    filterData.add(rp);
                } else if (jumatButton.isSelected() && rp.getHari().equalsIgnoreCase("jumat")) {
                    filterData.add(rp);
                } else if (sabtuButton.isSelected() && rp.getHari().equalsIgnoreCase("sabtu")) {
                    filterData.add(rp);
                }
            }
            setTable();
            setGridPane();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void setTable() throws Exception {
        if (rencanaProduksiTable.getRoot() != null) {
            rencanaProduksiTable.getRoot().getChildren().clear();
        }
        List<String> tujuanKirim = new ArrayList<>();
        for (RencanaProduksi temp : filterData) {
            if (!tujuanKirim.contains(temp.getTujuanKirim())) {
                tujuanKirim.add(temp.getTujuanKirim());
            }
        }
        double totalKirim = 0;
        double totalQty = 0;
        double totalTonase = 0;
        for (String temp : tujuanKirim) {
            RencanaProduksi rp = new RencanaProduksi();
            rp.setTujuanKirim(temp);
            TreeItem<RencanaProduksi> parent = new TreeItem<>(rp);
            double qty = 0;
            double tonase = 0;
            String hari = "";
            for (RencanaProduksi temp2 : filterData) {
                if (temp.equals(temp2.getTujuanKirim())) {
                    parent.getChildren().addAll(new TreeItem<>(temp2));
                    tonase = tonase + temp2.getTonase();
                    qty = qty + temp2.getQty();
                    hari = temp2.getHari();

                    totalQty = totalQty + temp2.getQty();
                    totalTonase = totalTonase + temp2.getTonase();
                }
            }
            parent.getValue().setHari(hari);
            parent.getValue().setQty(qty);
            parent.getValue().setTonase(tonase);
            root.getChildren().add(parent);

            totalKirim = totalKirim + 1;
        }
        rencanaProduksiTable.setRoot(root);

        totalKirimLabel.setText(df.format(totalKirim));
        totalQtyLabel.setText(df.format(totalQty));
        totalTonaseLabel.setText(df.format(totalTonase));
    }

    @FXML
    private ScrollPane scrollPane;
    private GridPane gridPane;

    public void setGridPane() {
        try {
            gridPane = new GridPane();
            gridPane.setHgap(1);
            gridPane.setVgap(1);

            for (int i = 1; i <= listMesin.size() + 3; i++) {
                gridPane.getRowConstraints().add(new RowConstraints(30, 30, 30));
            }
            for (int i = 1; i <= 8; i++) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(80, 120, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
            }
            addTopHeaderText(gridPane, "Mesin", 0, 0);
            addTopHeaderText(gridPane, "Kapasitas", 1, 0);
            addTopHeaderText(gridPane, "Senin", 2, 0);
            addTopHeaderText(gridPane, "Selasa", 3, 0);
            addTopHeaderText(gridPane, "Rabu", 4, 0);
            addTopHeaderText(gridPane, "Kamis", 5, 0);
            addTopHeaderText(gridPane, "Jumat", 6, 0);
            addTopHeaderText(gridPane, "Sabtu", 7, 0);

            int i = 1;
            double totalKapasitas = 0;
            for (Mesin m : listMesin) {
                addLeftHeaderText(gridPane, m.getKodeMesin(), 0, i);
                addLeftSubHeaderText(gridPane, df.format(m.getKapasitas()), 1, i);
                i++;
                totalKapasitas = totalKapasitas + m.getKapasitas();
            }
            addLeftHeaderText(gridPane, "Sisa", 0, i);
            addLeftSubHeaderText(gridPane, "", 1, i);
            i++;
            addLeftHeaderText(gridPane, "Total", 0, i);
            addLeftHeaderText(gridPane, df.format(totalKapasitas), 1, i);

            setDetail();

            AnchorPane.setTopAnchor(gridPane, 5.0);
            AnchorPane.setBottomAnchor(gridPane, 5.0);
            AnchorPane.setLeftAnchor(gridPane, 5.0);
            AnchorPane.setRightAnchor(gridPane, 5.0);
            scrollPane.setContent(gridPane);
        } catch (Exception ex) {
            ex.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    private void setDetail() {
        for (RencanaProduksi rp : allRencanaProduksi) {
            rp.setProduksi(0);
        }
        int noMesin = 1;
        for (Mesin m : listMesin) {
            double jumlahProduksiSenin = 0;
            double jumlahProduksiSelasa = 0;
            double jumlahProduksiRabu = 0;
            double jumlahProduksiKamis = 0;
            double jumlahProduksiJumat = 0;
            double jumlahProduksiSabtu = 0;
            List<RencanaProduksi> listRencanaSenin = new ArrayList<>();
            List<RencanaProduksi> listRencanaSelasa = new ArrayList<>();
            List<RencanaProduksi> listRencanaRabu = new ArrayList<>();
            List<RencanaProduksi> listRencanaKamis = new ArrayList<>();
            List<RencanaProduksi> listRencanaJumat = new ArrayList<>();
            List<RencanaProduksi> listRencanaSabtu = new ArrayList<>();
            for (RencanaProduksi rp : allRencanaProduksi) {
                if (rp.getHari().equalsIgnoreCase("senin")) {
                    for (MesinDetailBarang d : m.getListDetailBarang()) {
                        if (d.getKodeBarang().equals(rp.getBarang()) && d.isStatus() && rp.getQty() > rp.getProduksi()) {
                            if (jumlahProduksiSenin + rp.getQty() < m.getKapasitas()) {
                                rp.setProduksi(rp.getProduksi() + rp.getQty());
                                jumlahProduksiSenin = jumlahProduksiSenin + rp.getQty();
                                listRencanaSenin.add(rp);
                            }
                        }
                    }
                }
                if (rp.getHari().equalsIgnoreCase("selasa")) {
                    for (MesinDetailBarang d : m.getListDetailBarang()) {
                        if (d.getKodeBarang().equals(rp.getBarang()) && d.isStatus() && rp.getQty() > rp.getProduksi()) {
                            if (jumlahProduksiSelasa + rp.getQty() < m.getKapasitas()) {
                                rp.setProduksi(rp.getProduksi() + rp.getQty());
                                jumlahProduksiSelasa = jumlahProduksiSelasa + rp.getQty();
                                listRencanaSelasa.add(rp);
                            }
                        }
                    }
                }
                if (rp.getHari().equalsIgnoreCase("rabu")) {
                    for (MesinDetailBarang d : m.getListDetailBarang()) {
                        if (d.getKodeBarang().equals(rp.getBarang()) && d.isStatus() && rp.getQty() > rp.getProduksi()) {
                            if (jumlahProduksiRabu + rp.getQty() < m.getKapasitas()) {
                                rp.setProduksi(rp.getProduksi() + rp.getQty());
                                jumlahProduksiRabu = jumlahProduksiRabu + rp.getQty();
                                listRencanaRabu.add(rp);
                            }
                        }
                    }
                }
                if (rp.getHari().equalsIgnoreCase("kamis")) {
                    for (MesinDetailBarang d : m.getListDetailBarang()) {
                        if (d.getKodeBarang().equals(rp.getBarang()) && d.isStatus() && rp.getQty() > rp.getProduksi()) {
                            if (jumlahProduksiKamis + rp.getQty() < m.getKapasitas()) {
                                rp.setProduksi(rp.getProduksi() + rp.getQty());
                                jumlahProduksiKamis = jumlahProduksiKamis + rp.getQty();
                                listRencanaKamis.add(rp);
                            }
                        }
                    }
                }
                if (rp.getHari().equalsIgnoreCase("jumat")) {
                    for (MesinDetailBarang d : m.getListDetailBarang()) {
                        if (d.getKodeBarang().equals(rp.getBarang()) && d.isStatus() && rp.getQty() > rp.getProduksi()) {
                            if (jumlahProduksiJumat + rp.getQty() < m.getKapasitas()) {
                                rp.setProduksi(rp.getProduksi() + rp.getQty());
                                jumlahProduksiJumat = jumlahProduksiJumat + rp.getQty();
                                listRencanaJumat.add(rp);
                            }
                        }
                    }
                }
                if (rp.getHari().equalsIgnoreCase("sabtu")) {
                    for (MesinDetailBarang d : m.getListDetailBarang()) {
                        if (d.getKodeBarang().equals(rp.getBarang()) && d.isStatus() && rp.getQty() > rp.getProduksi()) {
                            if (jumlahProduksiSabtu + rp.getQty() < m.getKapasitas()) {
                                rp.setProduksi(rp.getProduksi() + rp.getQty());
                                jumlahProduksiSabtu = jumlahProduksiSabtu + rp.getQty();
                                listRencanaSabtu.add(rp);
                            }
                        }
                    }
                }
            }
            addHyperLinkText(gridPane, df.format(jumlahProduksiSenin), 2, noMesin, listRencanaSenin);
            addHyperLinkText(gridPane, df.format(jumlahProduksiSelasa), 3, noMesin, listRencanaSelasa);
            addHyperLinkText(gridPane, df.format(jumlahProduksiRabu), 4, noMesin, listRencanaRabu);
            addHyperLinkText(gridPane, df.format(jumlahProduksiKamis), 5, noMesin, listRencanaKamis);
            addHyperLinkText(gridPane, df.format(jumlahProduksiJumat), 6, noMesin, listRencanaJumat);
            addHyperLinkText(gridPane, df.format(jumlahProduksiSabtu), 7, noMesin, listRencanaSabtu);
            noMesin++;
        }
        double totalSenin = 0;
        double totalSelasa = 0;
        double totalRabu = 0;
        double totalKamis = 0;
        double totalJumat = 0;
        double totalSabtu = 0;
        double sisaSenin = 0;
        double sisaSelasa = 0;
        double sisaRabu = 0;
        double sisaKamis = 0;
        double sisaJumat = 0;
        double sisaSabtu = 0;
        List<RencanaProduksi> listRencanaSisaSenin = new ArrayList<>();
        List<RencanaProduksi> listRencanaSisaSelasa = new ArrayList<>();
        List<RencanaProduksi> listRencanaSisaRabu = new ArrayList<>();
        List<RencanaProduksi> listRencanaSisaKamis = new ArrayList<>();
        List<RencanaProduksi> listRencanaSisaJumat = new ArrayList<>();
        List<RencanaProduksi> listRencanaSisaSabtu = new ArrayList<>();
        for (RencanaProduksi rp : allRencanaProduksi) {
            if (rp.getHari().equalsIgnoreCase("senin")) {
                if (rp.getQty() > rp.getProduksi()) {
                    sisaSenin = sisaSenin + rp.getQty();
                    rp.setProduksi(rp.getProduksi() + rp.getQty());
                    listRencanaSisaSenin.add(rp);
                }
                totalSenin = totalSenin + rp.getQty();
            }
            if (rp.getHari().equalsIgnoreCase("selasa")) {
                if (rp.getQty() > rp.getProduksi()) {
                    sisaSelasa = sisaSelasa + rp.getQty();
                    rp.setProduksi(rp.getProduksi() + rp.getQty());
                    listRencanaSisaSelasa.add(rp);
                }
                totalSelasa = totalSelasa + rp.getQty();
            }
            if (rp.getHari().equalsIgnoreCase("rabu")) {
                if (rp.getQty() > rp.getProduksi()) {
                    sisaRabu = sisaRabu + rp.getQty();
                    rp.setProduksi(rp.getProduksi() + rp.getQty());
                    listRencanaSisaRabu.add(rp);
                }
                totalRabu = totalRabu + rp.getQty();
            }
            if (rp.getHari().equalsIgnoreCase("kamis")) {
                if (rp.getQty() > rp.getProduksi()) {
                    sisaKamis = sisaKamis + rp.getQty();
                    rp.setProduksi(rp.getProduksi() + rp.getQty());
                    listRencanaSisaKamis.add(rp);
                }
                totalKamis = totalKamis + rp.getQty();
            }
            if (rp.getHari().equalsIgnoreCase("jumat")) {
                if (rp.getQty() > rp.getProduksi()) {
                    sisaJumat = sisaJumat + rp.getQty();
                    rp.setProduksi(rp.getProduksi() + rp.getQty());
                    listRencanaSisaJumat.add(rp);
                }
                totalJumat = totalJumat + rp.getQty();
            }
            if (rp.getHari().equalsIgnoreCase("sabtu")) {
                if (rp.getQty() > rp.getProduksi()) {
                    sisaSabtu = sisaSabtu + rp.getQty();
                    rp.setProduksi(rp.getProduksi() + rp.getQty());
                    listRencanaSisaSabtu.add(rp);
                }
                totalSabtu = totalSabtu + rp.getQty();
            }
        }
        addHyperLinkText(gridPane, df.format(sisaSenin), 2, noMesin, listRencanaSisaSenin);
        addHyperLinkText(gridPane, df.format(sisaSelasa), 3, noMesin, listRencanaSisaSelasa);
        addHyperLinkText(gridPane, df.format(sisaRabu), 4, noMesin, listRencanaSisaRabu);
        addHyperLinkText(gridPane, df.format(sisaKamis), 5, noMesin, listRencanaSisaKamis);
        addHyperLinkText(gridPane, df.format(sisaJumat), 6, noMesin, listRencanaSisaJumat);
        addHyperLinkText(gridPane, df.format(sisaSabtu), 7, noMesin, listRencanaSisaSabtu);
        noMesin++;
        addLeftHeaderText(gridPane, df.format(totalSenin), 2, noMesin);
        addLeftHeaderText(gridPane, df.format(totalSelasa), 3, noMesin);
        addLeftHeaderText(gridPane, df.format(totalRabu), 4, noMesin);
        addLeftHeaderText(gridPane, df.format(totalKamis), 5, noMesin);
        addLeftHeaderText(gridPane, df.format(totalJumat), 6, noMesin);
        addLeftHeaderText(gridPane, df.format(totalSabtu), 7, noMesin);
        noMesin++;

    }

    private void addLeftHeaderText(GridPane gridPane, String text, int column, int row) {
        AnchorPane ap = new AnchorPane();
        ap.setStyle("-fx-background-color:seccolor2;");
        gridPane.add(ap, column, row, 1, 1);

        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;" + "-fx-text-fill: seccolor6;");
        label.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(label, column, row);
    }

    private void addLeftSubHeaderText(GridPane gridPane, String text, int column, int row) {
        AnchorPane ap = new AnchorPane();
        ap.setStyle("-fx-background-color:seccolor3;");
        gridPane.add(ap, column, row, 1, 1);

        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;" + "-fx-text-fill: seccolor6;");
        label.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(label, column, row);
    }

    private void addHyperLinkText(GridPane gridPane, String text, int column, int row, List<RencanaProduksi> listRencana) {
        AnchorPane ap = new AnchorPane();
        ap.setStyle("-fx-background-color:seccolor5;");
        gridPane.add(ap, column, row, 1, 1);

        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor2;"
                + "-fx-font-weight:bold;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRencanaProduksiMesin.fxml");
            DetailRencanaProduksiMesinController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setListProduksi(listRencana);
        });
        gridPane.add(hyperlink, column, row);
    }

    private void addNormalText(GridPane gridPane, String text, int column, int row) {
        AnchorPane ap = new AnchorPane();
        ap.setStyle("-fx-background-color:seccolor5;");
        gridPane.add(ap, column, row, 1, 1);

        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;"
                + "-fx-text-fill: seccolor2;");
        label.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(label, column, row);
    }

    private void addTopHeaderText(GridPane gridPane, String text, int column, int row) {
        AnchorPane ap = new AnchorPane();
        ap.setStyle("-fx-background-color:seccolor2;");
        gridPane.add(ap, column, row, 1, 1);

        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;" + "-fx-text-fill: seccolor6;");
        label.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(label, column, row);
    }

    private void addRencanaProduksi() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/AddPermintaanBarang.fxml");
        AddPermintaanBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.addButton.setOnAction((event) -> {
            List<PemesananBarangDetail> spk = new ArrayList<>();
            for (PemesananBarangDetail b : controller.filterData) {
                if (b.isStatus()) {
                    spk.add(b);
                }
            }
            if (spk.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Data permintaan barang belum di pilih");
            } else if (controller.hariCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jadwal kirim belum dipilih");
            } else if (controller.tujuanField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tujuan kirim belum dipilih");
            } else {
                Task<Void> task = new Task<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            for (PemesananBarangDetail d : spk) {
                                RencanaProduksi rp = new RencanaProduksi();
                                rp.setNoRencana(RencanaProduksiDAO.getId(con));
                                rp.setHari(controller.hariCombo.getSelectionModel().getSelectedItem());
                                rp.setTujuanKirim(controller.tujuanField.getText());
                                rp.setNoPemesanan(d.getNoPemesanan());
                                rp.setCustomer(d.getPemesananBarangHead().getCustomer().getNama());
                                rp.setBarang(d.getKodeBarang());
                                rp.setKeterangan(d.getKeterangan());
                                rp.setCatatan(d.getCatatanIntern());
                                rp.setSales(d.getPemesananBarangHead().getSales().getNama());
                                rp.setQty(d.getQty() - d.getQtyTerkirim());
                                rp.setTonase((d.getQty() - d.getQtyTerkirim()) * d.getBarang().getBerat());
                                RencanaProduksiDAO.insert(con, rp);
                            }
                            return null;
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        getRencana();
                        controller.close();
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
        });
    }

    private void editRencanaProduksi(RencanaProduksi rp) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRencanaProduksi.fxml");
        DetailRencanaProduksiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setRencanaProduksi(rp);
        controller.saveButton.setOnAction((event) -> {
            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        double berat = rp.getTonase() / rp.getQty();
                        rp.setTujuanKirim(controller.tujuanKirimField.getText());
                        rp.setKeterangan(controller.keteranganField.getText());
                        rp.setCatatan(controller.catatanField.getText());
                        rp.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                        rp.setTonase(rp.getQty() * berat);
                        RencanaProduksiDAO.update(con, rp);
                        return null;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try {
                    mainApp.closeLoading();
                    getRencana();
                    controller.close();
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
        });
    }

    private void deleteRencanaProduksi(RencanaProduksi rp) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus " + rp.getCustomer() + " - " + rp.getBarang() + " - " + rp.getKeterangan() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        RencanaProduksiDAO.delete(con, rp);
                        return "true";
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                if (task.getValue().equals("true")) {
                    getRencana();
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

    private void editGroupRencanaProduksi(RencanaProduksi rp) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/GantiJadwalDanTujuan.fxml");
        GantiJadwalDanTujuanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setRencanaProduksi(rp);
        controller.saveButton.setOnAction((event) -> {
            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        for (RencanaProduksi r : allRencanaProduksi) {
                            if (rp.getTujuanKirim().equals(r.getTujuanKirim())
                                    && rp.getHari().equals(r.getHari())) {
                                r.setHari(controller.hariCombo.getSelectionModel().getSelectedItem());
                                r.setTujuanKirim(controller.tujuanKirimField.getText());
                                RencanaProduksiDAO.update(con, r);
                            }
                        }
                        return null;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try {
                    mainApp.closeLoading();
                    getRencana();
                    controller.close();
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
        });
    }

    private void deleteGroupRencanaProduksi(RencanaProduksi rp) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus " + rp.getTujuanKirim() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        RencanaProduksiDAO.deleteGroup(con, rp);
                        return "true";
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                if (task.getValue().equals("true")) {
                    getRencana();
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

    private void deleteAllRencanaProduksi() {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus semua rencana produksi mingguan ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        RencanaProduksiDAO.deleteAll(con);
                        return "true";
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                if (task.getValue().equals("true")) {
                    getRencana();
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

    private void printRencanaProduksi() {
        try {
            Report report = new Report();
            report.printRencanaProduksi(filterData);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void printDeliveryOrder(RencanaProduksi rp) {
        try {
            List<RencanaProduksi> listRencana = new ArrayList<>();
            for(RencanaProduksi r : filterData){
                if(r.getTujuanKirim().equals(rp.getTujuanKirim()))
                    listRencana.add(r);
            }
            Report report = new Report();
            report.printDeliveryOrder(listRencana);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
