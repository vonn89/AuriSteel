/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddPemesananController {

    @FXML public TableView<PemesananBarangHead> pemesananHeadTable;
    @FXML private TableColumn<PemesananBarangHead, String> noPemesananHeadColumn;
    @FXML private TableColumn<PemesananBarangHead, String> tglPemesananColumn;
    @FXML private TableColumn<PemesananBarangHead, String> namaCustomerColumn;
    @FXML private TableColumn<PemesananBarangHead, String> alamatCustomerColumn;
    @FXML private TableColumn<PemesananBarangHead, String> namaInvoiceColumn;
    @FXML private TableColumn<PemesananBarangHead, String> catatanColumn;
    @FXML private TableColumn<PemesananBarangHead, String> namaSalesColumn;
    @FXML private TableColumn<PemesananBarangHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    private ObservableList<PemesananBarangHead> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPemesananHeadColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));
        namaInvoiceColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerInvoice().namaProperty());
        namaInvoiceColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        catatanColumn.setCellValueFactory(cellData ->cellData.getValue().catatanProperty());
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        tglPemesananColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananBarangHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPemesanan();
        });
        filterData.addAll(allPemesanan);
        pemesananHeadTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
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
        getPemesanan();
    }
    private void getPemesanan(){
        Task<List<PemesananBarangHead>> task = new Task<List<PemesananBarangHead>>() {
            @Override 
            public List<PemesananBarangHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PemesananBarangHead> allPemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(con, 
                            "2000-01-01", "2050-01-01", "open");
                    List<PemesananBarangDetail> allDetail = PemesananBarangDetailDAO.getAllByDateAndStatus(con, 
                            "2000-01-01", "2050-01-01", "open");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    for(PemesananBarangHead p : allPemesanan){
                        List<PemesananBarangDetail> detail = new ArrayList<>();
                        for(PemesananBarangDetail d : allDetail){
                            if(d.getNoPemesanan().equals(p.getNoPemesanan()))
                                detail.add(d);
                        }
                        p.setListPemesananBarangDetail(detail);
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomerInvoice().equals(c.getKodeCustomer()))
                                p.setCustomerInvoice(c);
                        }
                        for(Pegawai s : allSales){
                            if(p.getKodeSales().equals(s.getKodePegawai()))
                                p.setSales(s);
                        }
                    }
                    return allPemesanan;
                }
            }
            
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPemesanan() {
        try{
            filterData.clear();
            for (PemesananBarangHead temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPemesanan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPemesanan())))||
                        checkColumn(temp.getKodeCustomer())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getCustomerInvoice().getNama())||
                        checkColumn(temp.getCustomer().getAlamat())||
                        checkColumn(temp.getPaymentTerm())||
                        checkColumn(df.format(temp.getTotalPemesanan()))||
                        checkColumn(df.format(temp.getDownPayment()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeSales())||
                        checkColumn(temp.getSales().getNama())||
                        checkColumn(temp.getKodeUser())||
                        checkColumn(temp.getTglBatal())||
                        checkColumn(temp.getUserBatal()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
}
