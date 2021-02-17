/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.MesinDAO;
import com.excellentsystem.AuriSteel.DAO.UserAppDAO;
import com.excellentsystem.AuriSteel.DAO.UserMesinAppDAO;
import com.excellentsystem.AuriSteel.DAO.VerifikasiAppDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.Mesin;
import com.excellentsystem.AuriSteel.Model.UserApp;
import com.excellentsystem.AuriSteel.Model.UserMesinApp;
import com.excellentsystem.AuriSteel.Model.VerifikasiApp;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataUserAppController {

    @FXML
    private TableView<UserApp> userTable;
    @FXML
    private TableColumn<UserApp, String> kodeUserColumn;

    @FXML
    private CheckBox checkVerifikasi;
    @FXML
    private TableView<VerifikasiApp> verifikasiTable;
    @FXML
    private TableColumn<VerifikasiApp, String> jenisColumn;
    @FXML
    private TableColumn<VerifikasiApp, Boolean> statusVerifikasiColumn;

    @FXML
    private CheckBox checkMesin;
    @FXML
    private TableView<UserMesinApp> mesinTable;
    @FXML
    private TableColumn<UserMesinApp, String> kodeMesinColumn;
    @FXML
    private TableColumn<UserMesinApp, Boolean> statusMesinColumn;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private List<Mesin> allMesin;
    private ObservableList<UserApp> allUser = FXCollections.observableArrayList();
    private ObservableList<VerifikasiApp> verifikasi = FXCollections.observableArrayList();
    private ObservableList<UserMesinApp> mesin = FXCollections.observableArrayList();
    private Main mainApp;
    private String status;

    public void initialize() {
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        jenisColumn.setCellValueFactory(cellData -> cellData.getValue().jenisProperty());
        jenisColumn.setCellFactory(col -> Function.getWrapTableCell(jenisColumn));

        statusVerifikasiColumn.setCellValueFactory((TableColumn.CellDataFeatures<VerifikasiApp, Boolean> param) -> {
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(param.getValue().isStatus());
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                param.getValue().setStatus(newValue);
                verifikasiTable.refresh();
            });
            return booleanProp;
        });
        statusVerifikasiColumn.setCellFactory((TableColumn<VerifikasiApp, Boolean> p) -> {
            CheckBoxTableCell<VerifikasiApp, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        kodeMesinColumn.setCellValueFactory(cellData -> cellData.getValue().kodeMesinProperty());
        kodeMesinColumn.setCellFactory(col -> Function.getWrapTableCell(kodeMesinColumn));

        statusMesinColumn.setCellValueFactory((TableColumn.CellDataFeatures<UserMesinApp, Boolean> param) -> {
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(param.getValue().isStatus());
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                param.getValue().setStatus(newValue);
                mesinTable.refresh();
            });
            return booleanProp;
        });
        statusMesinColumn.setCellFactory((TableColumn<UserMesinApp, Boolean> p) -> {
            CheckBoxTableCell<UserMesinApp, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectUser(newValue));

        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New User");
        addNew.setOnAction((ActionEvent event) -> {
            newUser();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getUser();
        });
        rm.getItems().addAll(addNew, refresh);
        userTable.setContextMenu(rm);
        userTable.setRowFactory(table -> {
            TableRow<UserApp> row = new TableRow<UserApp>() {
                @Override
                public void updateItem(UserApp item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New User");
                        addNew.setOnAction((ActionEvent event) -> {
                            newUser();
                        });
                        MenuItem hapus = new MenuItem("Hapus User");
                        hapus.setOnAction((ActionEvent event) -> {
                            delete(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getUser();
                        });
                        rm.getItems().addAll(addNew, hapus, refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        userTable.setItems(allUser);
        mesinTable.setItems(mesin);
        verifikasiTable.setItems(verifikasi);
        getListMesin();
        getUser();
    }

    @FXML
    private void checkVerifikasi() {
        for (VerifikasiApp d : verifikasi) {
            d.setStatus(checkVerifikasi.isSelected());
        }
        verifikasiTable.refresh();
    }

    @FXML
    private void checkMesin() {
        for (UserMesinApp d : mesin) {
            d.setStatus(checkMesin.isSelected());
        }
        mesinTable.refresh();
    }

    private void getListMesin() {
        Task<List<Mesin>> task = new Task<List<Mesin>>() {
            @Override
            public List<Mesin> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return MesinDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allMesin = task.getValue();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }

    @FXML
    private void getUser() {
        Task<List<UserApp>> task = new Task<List<UserApp>>() {
            @Override
            public List<UserApp> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<UserApp> allUser = UserAppDAO.getAll(con);
                    List<VerifikasiApp> allVerifikasi = VerifikasiAppDAO.getAll(con);
                    List<UserMesinApp> allUserMesin = UserMesinAppDAO.getAll(con);
                    for (UserApp u : allUser) {
                        List<VerifikasiApp> verifikasi = new ArrayList<>();
                        for (VerifikasiApp o : allVerifikasi) {
                            if (u.getKodeUser().equals(o.getKodeUser())) {
                                verifikasi.add(o);
                            }
                        }
                        u.setListVerifikasi(verifikasi);

                        List<UserMesinApp> mesinApp = new ArrayList<>();
                        for (UserMesinApp o : allUserMesin) {
                            if (u.getKodeUser().equals(o.getKodeUser())) {
                                mesinApp.add(o);
                            }
                        }
                        u.setListUserMesinApp(mesinApp);
                    }
                    return allUser;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allUser.clear();
            allUser.addAll(task.getValue());
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
        verifikasi.clear();
        mesin.clear();
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        status = "";
    }

    public void selectUser(UserApp u) {
        if (u != null) {
            status = "update";
            verifikasi.clear();
            verifikasi.addAll(u.getListVerifikasi());
            mesin.clear();
            mesin.addAll(u.getListUserMesinApp());
            usernameField.setText(u.getKodeUser());
            passwordField.setText(u.getPin());
            usernameField.setDisable(true);
            passwordField.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);

        }
    }

    public void newUser() {
        status = "new";
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setDisable(false);
        passwordField.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);

        List<String> allVerifikasi = new ArrayList<>();
        allVerifikasi.add("Mulai Produksi");
        allVerifikasi.add("Penerimaan Bahan");
        allVerifikasi.add("Produksi Barang");
        allVerifikasi.add("Selesai Produksi");
        allVerifikasi.add("Stok Bahan");
        allVerifikasi.add("Stok Barang");
        allVerifikasi.add("Verifikasi Penerimaan Bahan");
        allVerifikasi.add("Verifikasi Produksi");
        List<VerifikasiApp> tempVerifikasi = new ArrayList<>();
        for (String jns : allVerifikasi) {
            VerifikasiApp temp = new VerifikasiApp();
            temp.setJenis(jns);
            temp.setStatus(checkVerifikasi.isSelected());
            tempVerifikasi.add(temp);
        }
        verifikasi.clear();
        verifikasi.addAll(tempVerifikasi);
        
        
        List<UserMesinApp> tempMesin = new ArrayList<>();
        for (Mesin m : allMesin) {
            UserMesinApp temp = new UserMesinApp();
            temp.setKodeMesin(m.getKodeMesin());
            temp.setStatus(checkMesin.isSelected());
            tempMesin.add(temp);
        }
        mesin.clear();
        mesin.addAll(tempMesin);
    }

    public void saveUser() {
        if (usernameField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "User masih kosong");
        } else {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        UserApp user = new UserApp();
                        user.setKodeUser(usernameField.getText());
                        user.setPin(passwordField.getText());
                        for (VerifikasiApp v : verifikasi) {
                            v.setKodeUser(usernameField.getText());
                        }
                        user.setListVerifikasi(verifikasi);

                        for (UserMesinApp m : mesin) {
                            m.setKodeUser(usernameField.getText());
                        }
                        user.setListUserMesinApp(mesin);

                        if (status.equals("update")) {
                            return Service.updateUserApp(con, user);
                        } else if (status.equals("new")) {
                            return Service.newUserApp(con, user);
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
                getUser();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil disimpan");
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

    public void delete(UserApp user) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete user " + user.getKodeUser() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deleteUserApp(con, user);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getUser();
                String message = task.getValue();
                if (message.equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil dihapus");
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
