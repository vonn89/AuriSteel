/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Helper.Notification;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Piutang;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 *
 * @author Xtreme
 */
public class MainAppController {

    @FXML
    public VBox vbox;
    @FXML
    private Accordion accordion;
    @FXML
    private Label title;

    @FXML
    private TitledPane loginButton;
    @FXML
    private MenuButton logoutButton;
    @FXML
    private MenuButton ubahPasswordButton;

    @FXML
    private TitledPane dashboardPane;

    @FXML
    private TitledPane masterPane;
    @FXML
    private VBox masterVbox;
    @FXML
    private MenuButton menuDataCustomer;
    @FXML
    private MenuButton menuDataSupplier;
    @FXML
    private MenuButton menuDataPegawai;
    @FXML
    private MenuButton menuDataBahan;
    @FXML
    private MenuButton menuDataBarang;

    @FXML
    private TitledPane penjualanPane;
    @FXML
    private VBox penjualanVbox;
    @FXML
    private MenuButton menuPemesanan;
    @FXML
    private MenuButton menuPenjualan;
    @FXML
    private MenuButton menuPemesananCoil;
    @FXML
    private MenuButton menuPenjualanCoil;

    @FXML
    private TitledPane pembelianPane;
    @FXML
    private VBox pembelianVbox;
    @FXML
    private MenuButton menuPemesananPembelianBahan;
    @FXML
    private MenuButton menuPembelianBahan;
    @FXML
    private MenuButton menuPembelianBarang;

    @FXML
    private TitledPane produksiPane;
    @FXML
    private VBox produksiVbox;
    @FXML
    private MenuButton menuPermintaanBarang;
    @FXML
    private MenuButton menuRencanaProduksiDanPengirimanBarang;
    @FXML
    private MenuButton menuProduksiBarang;

    @FXML
    private TitledPane barangPane;
    @FXML
    private VBox barangVbox;
    @FXML
    private MenuButton menuPengirimanBarang;
    @FXML
    private MenuButton menuPengirimanCoil;
    @FXML
    private MenuButton menuPenerimaanBahan;
    @FXML
    private MenuButton menuPindahBarang;
    @FXML
    private MenuButton menuPindahBahan;

    @FXML
    private TitledPane keuanganPane;
    @FXML
    private VBox keuanganVbox;
    @FXML
    private MenuButton menuKeuangan;
    @FXML
    private MenuButton menuHutang;
    @FXML
    private MenuButton menuPiutang;
    @FXML
    private MenuButton menuModal;
    @FXML
    private MenuButton menuAsetTetap;

    @FXML
    private TitledPane laporanPane;
    @FXML
    private VBox laporanVbox;
    @FXML
    private MenuButton menuLaporanBarang;
    @FXML
    private MenuButton menuLaporanPenjualan;
    @FXML
    private MenuButton menuLaporanPembelian;
    @FXML
    private MenuButton menuLaporanKeuangan;
    @FXML
    private MenuButton menuLaporanManagerial;

    @FXML
    private TitledPane pengaturanPane;
    @FXML
    private VBox pengaturanVbox;
    @FXML
    private MenuButton menuDataUser;
    @FXML
    private MenuButton menuDataUserApp;
    @FXML
    private MenuButton menuDataGudang;
    @FXML
    private MenuButton menuDataMesin;
    @FXML
    private MenuButton menuKategoriBahan;
    @FXML
    private MenuButton menuKategoriHutang;
    @FXML
    private MenuButton menuKategoriPiutang;
    @FXML
    private MenuButton menuKategoriKeuangan;
    @FXML
    private MenuButton menuKategoriTransaksi;

    private Main mainApp;
    private ObservableList<Notification> allNotif = FXCollections.observableArrayList();

    public void setMainApp(Main mainApp) {
        try {
            this.mainApp = mainApp;
            vbox.setPrefWidth(0);
            vbox.setVisible(false);
            for (Node n : vbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : masterVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : penjualanVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : pembelianVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : produksiVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : barangVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : keuanganVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : laporanVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : pengaturanVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            title.setText("AURI STEEL METALINDO");
            setUser();
            getAllNotif();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }

    public void setUser() {
        dashboardPane.setVisible(false);

        menuDataCustomer.setVisible(false);
        menuDataSupplier.setVisible(false);
        menuDataPegawai.setVisible(false);
        menuDataBahan.setVisible(false);
        menuDataBarang.setVisible(false);

        menuPemesanan.setVisible(false);
        menuPenjualan.setVisible(false);
        menuPemesananCoil.setVisible(false);
        menuPenjualanCoil.setVisible(false);

        menuPemesananPembelianBahan.setVisible(false);
        menuPembelianBahan.setVisible(false);
        menuPembelianBarang.setVisible(false);

        menuPermintaanBarang.setVisible(false);
        menuRencanaProduksiDanPengirimanBarang.setVisible(false);
        menuProduksiBarang.setVisible(false);

        menuPengirimanBarang.setVisible(false);
        menuPengirimanCoil.setVisible(false);
        menuPenerimaanBahan.setVisible(false);
        menuPindahBahan.setVisible(false);
        menuPindahBarang.setVisible(false);

        menuKeuangan.setVisible(false);
        menuHutang.setVisible(false);
        menuPiutang.setVisible(false);
        menuModal.setVisible(false);
        menuAsetTetap.setVisible(false);

        menuLaporanBarang.setVisible(false);
        menuLaporanPenjualan.setVisible(false);
        menuLaporanPembelian.setVisible(false);
        menuLaporanKeuangan.setVisible(false);
        menuLaporanManagerial.setVisible(false);

        menuDataUser.setVisible(false);
        menuDataUserApp.setVisible(false);
        menuDataMesin.setVisible(false);
        menuDataGudang.setVisible(false);
        menuKategoriBahan.setVisible(false);
        menuKategoriHutang.setVisible(false);
        menuKategoriPiutang.setVisible(false);
        menuKategoriKeuangan.setVisible(false);
        menuKategoriTransaksi.setVisible(false);
        if (sistem.getUser() == null) {
            mainApp.showLoginScene();
        } else {
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText("User : " + sistem.getUser().getKodeUser());
            for (Otoritas o : sistem.getUser().getOtoritas()) {
                if (o.getJenis().equals("Dashboard")) {
                    if (o.isStatus()) {
                        dashboardPane.setVisible(true);
                    } else {
                        accordion.getPanes().remove(dashboardPane);
                    }
                } else if (o.getJenis().equals("Data Customer")) {
                    menuDataCustomer.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Supplier")) {
                    menuDataSupplier.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Pegawai")) {
                    menuDataPegawai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Bahan")) {
                    menuDataBahan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Barang")) {
                    menuDataBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pemesanan")) {
                    menuPemesanan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Penjualan")) {
                    menuPenjualan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pemesanan Coil")) {
                    menuPemesananCoil.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Penjualan Coil")) {
                    menuPenjualanCoil.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pemesanan Pembelian Bahan")) {
                    menuPemesananPembelianBahan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pembelian Bahan")) {
                    menuPembelianBahan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pembelian Barang")) {
                    menuPembelianBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Permintaan Barang")) {
                    menuPermintaanBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Rencana Produksi")) {
                    menuRencanaProduksiDanPengirimanBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Produksi Barang")) {
                    menuProduksiBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pengiriman Barang")) {
                    menuPengirimanBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pengiriman Coil")) {
                    menuPengirimanCoil.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Penerimaan Bahan")) {
                    menuPenerimaanBahan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pindah Bahan")) {
                    menuPindahBahan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pindah Barang")) {
                    menuPindahBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Keuangan")) {
                    menuKeuangan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Hutang")) {
                    menuHutang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Piutang")) {
                    menuPiutang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Modal")) {
                    menuModal.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Aset Tetap")) {
                    menuAsetTetap.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Barang")) {
                    menuLaporanBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Penjualan")) {
                    menuLaporanPenjualan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Pembelian")) {
                    menuLaporanPembelian.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Keuangan")) {
                    menuLaporanKeuangan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Managerial")) {
                    menuLaporanManagerial.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data User")) {
                    menuDataUser.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Gudang")) {
                    menuDataGudang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data User App")) {
                    menuDataUserApp.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Mesin")) {
                    menuDataMesin.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Bahan")) {
                    menuKategoriBahan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Hutang")) {
                    menuKategoriHutang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Piutang")) {
                    menuKategoriPiutang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Keuangan")) {
                    menuKategoriKeuangan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Transaksi")) {
                    menuKategoriTransaksi.setVisible(o.isStatus());
                }
            }
            if (menuDataCustomer.isVisible() == false
                    && menuDataSupplier.isVisible() == false
                    && menuDataPegawai.isVisible() == false
                    && menuDataBahan.isVisible() == false
                    && menuDataBarang.isVisible() == false) {
                accordion.getPanes().remove(masterPane);
            }
            if (menuPemesanan.isVisible() == false
                    && menuPenjualan.isVisible() == false
                    && menuPemesananCoil.isVisible() == false
                    && menuPenjualanCoil.isVisible() == false) {
                accordion.getPanes().remove(penjualanPane);
            }
            if (menuPembelianBahan.isVisible() == false
                    && menuPembelianBarang.isVisible() == false
                    && menuPemesananPembelianBahan.isVisible() == false) {
                accordion.getPanes().remove(pembelianPane);
            }
            if (menuPermintaanBarang.isVisible() == false
                    && menuRencanaProduksiDanPengirimanBarang.isVisible() == false
                    && menuProduksiBarang.isVisible() == false) {
                accordion.getPanes().remove(produksiPane);
            }
            if (menuPengirimanBarang.isVisible() == false
                    && menuPengirimanCoil.isVisible() == false
                    && menuPenerimaanBahan.isVisible() == false
                    && menuPindahBahan.isVisible() == false
                    && menuPindahBarang.isVisible() == false) {
                accordion.getPanes().remove(barangPane);
            }
            if (menuKeuangan.isVisible() == false
                    && menuHutang.isVisible() == false
                    && menuPiutang.isVisible() == false
                    && menuModal.isVisible() == false
                    && menuAsetTetap.isVisible() == false) {
                accordion.getPanes().remove(keuanganPane);
            }
            if (menuLaporanBarang.isVisible() == false
                    && menuLaporanPenjualan.isVisible() == false
                    && menuLaporanPembelian.isVisible() == false
                    && menuLaporanKeuangan.isVisible() == false
                    && menuLaporanManagerial.isVisible() == false) {
                accordion.getPanes().remove(laporanPane);
            }
            if (menuDataUser.isVisible() == false
                    && menuDataUserApp.isVisible() == false
                    && menuDataMesin.isVisible() == false
                    && menuDataGudang.isVisible() == false
                    && menuKategoriBahan.isVisible() == false
                    && menuKategoriHutang.isVisible() == false
                    && menuKategoriPiutang.isVisible() == false
                    && menuKategoriKeuangan.isVisible() == false
                    && menuKategoriTransaksi.isVisible() == false) {
                accordion.getPanes().remove(pengaturanPane);
            }
        }

    }

    public void getAllNotif() throws Exception {
        allNotif.clear();
        try (Connection con = Koneksi.getConnection()) {
            Date date = Function.getServerDate(con);
            List<Piutang> allPiutang = PiutangDAO.getAllByStatus(con, "open");
            for (Piutang p : allPiutang) {
                if (!p.getJatuhTempo().equals("2000-01-01")) {
                    if (tglBarang.parse(p.getJatuhTempo()).before(date) || tglBarang.parse(p.getJatuhTempo()).equals(date)) {
                        Notification notif = new Notification();
                        notif.setNama(p.getKeterangan());
                        notif.setJenisTransaksi(p.getKategori());
                        notif.setNoTransaksi(p.getNoPiutang());
                        notif.setJumlahRp(p.getSisaPiutang());
                        allNotif.add(notif);
                    }
                }
            }
            List<Hutang> allHutang = HutangDAO.getAllByStatus(con, "open");
            for (Hutang h : allHutang) {
                if (!h.getJatuhTempo().equals("2000-01-01")) {
                    if (tglBarang.parse(h.getJatuhTempo()).before(date) || tglBarang.parse(h.getJatuhTempo()).equals(date)) {
                        Notification notif = new Notification();
                        notif.setNama(h.getKeterangan());
                        notif.setJenisTransaksi(h.getKategori());
                        notif.setNoTransaksi(h.getKategori());
                        notif.setJumlahRp(h.getSisaHutang());
                        allNotif.add(notif);
                    }
                }
            }
        }
    }

    @FXML
    public void showHideMenu() {
        final Animation hideSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            @Override
            protected void interpolate(double frac) {
                final double curWidth = 240 * (1.0 - frac);
                vbox.setPrefWidth(curWidth);
                masterPane.setExpanded(false);
                penjualanPane.setExpanded(false);
                pembelianPane.setExpanded(false);
                barangPane.setExpanded(false);
                keuanganPane.setExpanded(false);
                laporanPane.setExpanded(false);
                pengaturanPane.setExpanded(false);
                loginButton.setExpanded(false);
            }
        };
        hideSidebar.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            vbox.setVisible(false);
        });
        final Animation showSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            @Override
            protected void interpolate(double frac) {
                final double curWidth = 240 * frac;
                vbox.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (vbox.isVisible()) {
                hideSidebar.play();
            } else {
                vbox.setVisible(true);
                showSidebar.play();
            }
        }
    }

    public void setTitle(String x) {
        title.setText(x);
    }

    @FXML
    private void menuLogout() {
        mainApp.showLoginScene();
    }

    @FXML
    private void menuExit() {
        System.exit(0);
    }

    @FXML
    private void menushowUbahPassword() {
        mainApp.showUbahPassword();
    }

    @FXML
    private void menuDashboard() {
        mainApp.showDashboard();
    }

    @FXML
    private void menuDataCustomer() {
        mainApp.showDataCustomer();
    }

    @FXML
    private void menuDataSupplier() {
        mainApp.showDataSupplier();
    }

    @FXML
    private void menuDataPegawai() {
        mainApp.showDataPegawai();
    }

    @FXML
    private void menuDataBahan() {
        mainApp.showDataBahan();
    }

    @FXML
    private void menuDataBarang() {
        mainApp.showDataBarang();
    }

    @FXML
    private void menuDataUser() {
        mainApp.showDataUser();
    }

    @FXML
    private void menuDataUserApp() {
        mainApp.showDataUserApp();
    }

    @FXML
    private void menuDataMesin() {
        mainApp.showDataMesin();
    }

    @FXML
    private void menuDataGudang() {
        mainApp.showGudang();
    }

    @FXML
    private void showKategoriBahan() {
        mainApp.showKategoriBahan();
    }

    @FXML
    private void showKategoriHutang() {
        mainApp.showKategoriHutang();
    }

    @FXML
    private void showKategoriPiutang() {
        mainApp.showKategoriPiutang();
    }

    @FXML
    private void showKategoriKeuangan() {
        mainApp.showKategoriKeuangan();
    }

    @FXML
    private void showKategoriTransaksi() {
        mainApp.showKategoriTransaksi();
    }

    @FXML
    private void menuPenjualan() {
        mainApp.showPenjualan();
    }

    @FXML
    private void menuPenjualanCoil() {
        mainApp.showPenjualanCoil();
    }

    @FXML
    private void menuPemesanan() {
        mainApp.showPemesanan();
    }

    @FXML
    private void menuPemesananCoil() {
        mainApp.showPemesananCoil();
    }

    @FXML
    private void menuPemesananPembelian() {
        mainApp.showPemesananPembelian();
    }

    @FXML
    private void menuPembelian() {
        mainApp.showPembelianBahan();
    }

    @FXML
    private void menuPembelianBarang() {
        mainApp.showPembelianBarang();
    }

    @FXML
    private void menuPermintaanBarang() {
        mainApp.showPermintaanBarang();
    }

    @FXML
    private void menuRencanaProduksiBarang() {
        mainApp.showRencanaProduksiBarang();
    }

    @FXML
    private void menuProduksiBarang() {
        mainApp.showProduksiBarang();
    }

    @FXML
    private void menuPengirimanBarang() {
        mainApp.showPengirimanBarang();
    }

    @FXML
    private void menuPengirimanCoil() {
        mainApp.showPengirimanCoil();
    }

    @FXML
    private void menuPenerimaanBahan() {
        mainApp.showPenerimaanBahan();
    }

    @FXML
    private void menuPindahBarang() {
        mainApp.showPindahBarang();
    }

    @FXML
    private void menuPindahBahan() {
        mainApp.showPindahBahan();
    }

    @FXML
    private void menuKeuangan() {
        mainApp.showKeuangan();
    }

    @FXML
    private void menuHutang() {
        mainApp.showHutang();
    }

    @FXML
    private void menuPiutang() {
        mainApp.showPiutang();
    }

    @FXML
    private void menuModal() {
        mainApp.showModal();
    }

    @FXML
    private void menuAsetTetap() {
        mainApp.showAsetTetap();
    }

    @FXML
    private void menuLaporanBarang() {
        mainApp.showLaporanBarang();
    }

    @FXML
    private void menuLaporanBahan() {
        mainApp.showLaporanBahan();
    }

    @FXML
    private void menuLaporanProduksiBarang() {
        mainApp.showLaporanProduksiBarang();
    }

    @FXML
    private void menuLaporanBarangDiproduksi() {
        mainApp.showLaporanBarangDiproduksi();
    }

    @FXML
    private void menuLaporanPengirimanBarang() {
        mainApp.showLaporanPengirimanBarang();
    }

    @FXML
    private void menuLaporanBarangDikirim() {
        mainApp.showLaporanBarangDikirim();
    }
    
    @FXML
    private void menuLaporanPenyesuaianStokBahan() {
        mainApp.showLaporanPenyesuaianStokBahan();
    }

    @FXML
    private void menuLaporanPenyesuaianStokBarang() {
        mainApp.showLaporanPenyesuaianStokBarang();
    }

    @FXML
    private void menuLaporanPemesanan() {
        mainApp.showLaporanPemesanan();
    }

    @FXML
    private void menuLaporanBarangTerpesan() {
        mainApp.showLaporanBarangTerpesan();
    }

    @FXML
    private void menuLaporanPenjualan() {
        mainApp.showLaporanPenjualan();
    }

    @FXML
    private void menuLaporanBarangTerjual() {
        mainApp.showLaporanBarangTerjual();
    }

    @FXML
    private void menuLaporanPenjualanCoil() {
        mainApp.showLaporanPenjualanCoil();
    }

    @FXML
    private void menuLaporanCoilTerjual() {
        mainApp.showLaporanCoilTerjual();
    }

    @FXML
    private void menuLaporanPembelian() {
        mainApp.showLaporanPembelian();
    }

    @FXML
    private void menuLaporanBahanDibeli() {
        mainApp.showLaporanBahanDibeli();
    }
    
    @FXML
    private void menuLaporanPembelianBarang() {
        mainApp.showLaporanPembelianBarang();
    }

    @FXML
    private void menuLaporanBarangDibeli() {
        mainApp.showLaporanBarangDibeli();
    }

    @FXML
    private void menuLaporanKeuangan() {
        mainApp.showLaporanKeuangan();
    }

    @FXML
    private void menuLaporanHutang() {
        mainApp.showLaporanHutang();
    }

    @FXML
    private void menuLaporanPiutang() {
        mainApp.showLaporanPiutang();
    }

    @FXML
    private void menuLaporanUntungRugiPerpetual() {
        mainApp.showLaporanUntungRugi();
    }

    @FXML
    private void menuLaporanUntungRugiPeriode() {
        mainApp.showLaporanUntungRugiPeriode();
    }

    @FXML
    private void menuLaporanNeracaPerpetual() {
        mainApp.showLaporanNeraca();
    }

    @FXML
    private void menuLaporanUntungRugiSummary() {
        mainApp.showLaporanUntungRugiSummary();
    }
}
