/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BebanPenjualanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPenjualanHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.BebanPenjualanDetail;
import com.excellentsystem.AuriSteel.Model.BebanPenjualanHead;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class NewBebanPenjualanController {

    @FXML
    private TextField noBebanPenjualanField;
    @FXML
    public DatePicker tglTransaksiPicker;
    @FXML
    private TextArea penjualanField;
    @FXML
    public TextArea keteranganField;
    @FXML
    public TextField jumlahRpField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;

    @FXML
    private Button addPenjualanButton;
    @FXML
    private Button resetPenjualanButton;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private GridPane gridPane;

    public List<BebanPenjualanDetail> listDetail = new ArrayList<>();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        Function.setNumberField(jumlahRpField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        tglTransaksiPicker.setConverter(Function.getTglConverter());
        tglTransaksiPicker.setValue(LocalDate.now());
        tglTransaksiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCell(
                LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    public void setNewBebanPenjualan() {
        noBebanPenjualanField.setText("");
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for (KategoriKeuangan kk : sistem.getListKategoriKeuangan()) {
            listKeuangan.add(kk.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
    }

    public void setDetailBebanPenjualan(String noBeban) {
        Task<BebanPenjualanHead> task = new Task<BebanPenjualanHead>() {
            @Override
            public BebanPenjualanHead call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    BebanPenjualanHead b = BebanPenjualanHeadDAO.get(con, noBeban);
                    b.setListBebanPenjualanDetail(BebanPenjualanDetailDAO.getAllByNoBeban(con, noBeban));
                    return b;
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try {
                mainApp.closeLoading();
                BebanPenjualanHead b = task.getValue();
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                tglTransaksiPicker.setDisable(true);
                addPenjualanButton.setVisible(false);
                resetPenjualanButton.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                keteranganField.setEditable(false);
                jumlahRpField.setDisable(true);
                tipeKeuanganCombo.setDisable(true);

                noBebanPenjualanField.setText(b.getNoBebanPenjualan());
                tglTransaksiPicker.setValue(LocalDate.parse(b.getTglBebanPenjualan(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                String penjualan = "";
                for (BebanPenjualanDetail d : b.getListBebanPenjualanDetail()) {
                    penjualan = penjualan + d.getNoPenjualan();
                    if (b.getListBebanPenjualanDetail().indexOf(d) < b.getListBebanPenjualanDetail().size() - 1) {
                        penjualan = penjualan + "\n";
                    }
                }
                penjualanField.setText(penjualan);
                keteranganField.setText(b.getKeterangan());
                jumlahRpField.setText(df.format(b.getTotalBebanPenjualan()));
                tipeKeuanganCombo.getSelectionModel().select(b.getTipeKeuangan());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    @FXML
    private void addPenjualan() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPenjualan.fxml");
        AddPenjualanController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.penjualanHeadTable.setRowFactory(table -> {
            final TableRow<PenjualanBarangHead> row = new TableRow<PenjualanBarangHead>() {
                @Override
                public void updateItem(PenjualanBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detailPenjualan = new MenuItem("Detail Penjualan");
                        detailPenjualan.setOnAction((ActionEvent e) -> {
                            detailPenjualan(item, child);
                        });
                        MenuItem detailBebanPenjualan = new MenuItem("Detail Beban Penjualan");
                        detailBebanPenjualan.setOnAction((ActionEvent e) -> {
                            detailBebanPenjualan(item, child);
                        });
                        rm.getItems().addAll(detailPenjualan, detailBebanPenjualan);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        try {
                            mainApp.closeDialog(stage, child);
                            PenjualanBarangHead p = row.getItem();
                            boolean status = true;
                            for (BebanPenjualanDetail d : listDetail) {
                                if (d.getNoPenjualan().equals(p.getNoPenjualan())) {
                                    status = false;
                                }
                            }
                            if (status) {
                                BebanPenjualanDetail d = new BebanPenjualanDetail();
                                d.setNoPenjualan(p.getNoPenjualan());
                                d.setPenjualanHead(p);
                                listDetail.add(d);
                                penjualanField.appendText(p.getNoPenjualan() + " " + p.getCustomer().getNama() + "\n");
                            } else {
                                mainApp.showMessage(Modality.NONE, "Warning", "Penjualan sudah diinput");
                            }
                        } catch (Exception e) {
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void resetPenjualan() {
        penjualanField.setText("");
        listDetail.clear();
    }

    private void detailPenjualan(PenjualanBarangHead p, Stage owner) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(owner, child, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, owner, child);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }

    private void detailBebanPenjualan(PenjualanBarangHead p, Stage owner) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(owner, child, "View/Dialog/DetailBebanPenjualan.fxml");
        DetailBebanPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, owner, child);
        controller.setDetailBebanPenjualan(p.getNoPenjualan());
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
