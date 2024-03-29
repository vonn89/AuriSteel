/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Services;

import com.excellentsystem.AuriSteel.DAO.AsetTetapDAO;
import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPembelianDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPenjualanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPenjualanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.BebanProduksiDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanProduksiHeadDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.GudangDAO;
import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriHutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriKeuanganDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriPiutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriTransaksiDAO;
import com.excellentsystem.AuriSteel.DAO.KeuanganDAO;
import com.excellentsystem.AuriSteel.DAO.LogBahanDAO;
import com.excellentsystem.AuriSteel.DAO.LogBarangDAO;
import com.excellentsystem.AuriSteel.DAO.MesinDAO;
import com.excellentsystem.AuriSteel.DAO.MesinDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.OtoritasDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PembayaranDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananPembelianBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananPembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenerimaanBahanDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBahanDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBarangDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBahanDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiHeadDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiOperatorDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.DAO.StokBarangDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
import com.excellentsystem.AuriSteel.DAO.TerimaPembayaranDAO;
import com.excellentsystem.AuriSteel.DAO.UserAppDAO;
import com.excellentsystem.AuriSteel.DAO.UserDAO;
import com.excellentsystem.AuriSteel.DAO.UserMesinAppDAO;
import com.excellentsystem.AuriSteel.DAO.VerifikasiAppDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.pembulatan;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.AsetTetap;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.BebanPembelian;
import com.excellentsystem.AuriSteel.Model.BebanPenjualanDetail;
import com.excellentsystem.AuriSteel.Model.BebanPenjualanHead;
import com.excellentsystem.AuriSteel.Model.BebanProduksiDetail;
import com.excellentsystem.AuriSteel.Model.BebanProduksiHead;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.KategoriHutang;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import com.excellentsystem.AuriSteel.Model.KategoriPiutang;
import com.excellentsystem.AuriSteel.Model.KategoriTransaksi;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.LogBahan;
import com.excellentsystem.AuriSteel.Model.LogBarang;
import com.excellentsystem.AuriSteel.Model.Mesin;
import com.excellentsystem.AuriSteel.Model.MesinDetailBarang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.Pembayaran;
import com.excellentsystem.AuriSteel.Model.PembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PembelianBarangDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.PemesananBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBahanHead;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PenerimaanBahan;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBahan;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBarang;
import com.excellentsystem.AuriSteel.Model.PindahBahanDetail;
import com.excellentsystem.AuriSteel.Model.PindahBahanHead;
import com.excellentsystem.AuriSteel.Model.PindahBarangDetail;
import com.excellentsystem.AuriSteel.Model.PindahBarangHead;
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.Model.ProduksiOperator;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.Model.StokBarang;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.Model.TerimaPembayaran;
import com.excellentsystem.AuriSteel.Model.User;
import com.excellentsystem.AuriSteel.Model.UserApp;
import com.excellentsystem.AuriSteel.Model.UserMesinApp;
import com.excellentsystem.AuriSteel.Model.VerifikasiApp;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class Service {

    private static void insertKeuangan(Connection con, String noKeuangan, String tanggal, String tglTransaksi, String tipeKeuangan,
            String kategori, String noTransaksi, String deskripsi, double jumlahRp, String kodeUser) throws Exception {
        Keuangan k = new Keuangan();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(tanggal);
        k.setTglTransaksi(tglTransaksi);
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setNoTransaksi(noTransaksi);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        k.setStatus("true");
        k.setTglBatal("2000-01-01 00:00:00");
        k.setUserBatal("");
        KeuanganDAO.insert(con, k);
    }

    public static String setPenyusutanAset(Connection con) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            LocalDate now = LocalDate.parse(tglBarang.format(Function.getServerDate(con)), DateTimeFormatter.ISO_DATE);
            List<Keuangan> listKeuanganAsetTetap = KeuanganDAO.getAllByTipeKeuangan(con, "Aset Tetap");
            for (AsetTetap aset : AsetTetapDAO.getAllByStatus(con, "open")) {
                if (aset.getMasaPakai() != 0) {
                    LocalDate tglBeli = LocalDate.parse(aset.getTglBeli(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    int selisih = ((now.getYear() - tglBeli.getYear()) * 12) + (now.getMonthValue() - tglBeli.getMonthValue());
                    if (selisih <= aset.getMasaPakai()) {
                        double totalPenyusutan = 0;
                        double penyusutanPerbulan = pembulatan(aset.getNilaiAwal() / aset.getMasaPakai());
                        for (int i = 1; i <= selisih; i++) {
                            LocalDate tglSusut = tglBeli.plusMonths(i);
                            if (tglSusut.isBefore(now) || tglSusut.isEqual(now)) {
                                boolean statusInput = true;
                                for (Keuangan k : listKeuanganAsetTetap) {
                                    if (k.getDeskripsi().startsWith("Penyusutan Aset Tetap Ke-" + i )
                                            && k.getNoTransaksi().equals(aset.getNoAset())) {
                                        statusInput = false;
                                    }
                                }
                                if (statusInput) {
                                    Date date = tglBarang.parse(tglSusut.toString());
                                    String noKeuangan = KeuanganDAO.getId(con, date);
                                    insertKeuangan(con, noKeuangan, tglSusut.toString() + " 00:00:00", tglSusut.toString() + " 00:00:00", "Aset Tetap", aset.getKategori(), aset.getNoAset(),
                                            "Penyusutan Aset Tetap Ke-" + i + " - " + aset.getNama(), -penyusutanPerbulan, "System");
                                    insertKeuangan(con, noKeuangan, tglSusut.toString() + " 00:00:00", tglSusut.toString() + " 00:00:00", "Pendapatan/Beban", "Beban Penyusutan Aset Tetap", aset.getNoAset(),
                                            "Penyusutan Aset Tetap Ke-" + i + " - " + aset.getNama(), -penyusutanPerbulan, "System");
                                }
                                totalPenyusutan = totalPenyusutan + penyusutanPerbulan;
                            }
                        }
                        aset.setPenyusutan(totalPenyusutan);
                        aset.setNilaiAkhir(aset.getNilaiAwal() - totalPenyusutan);
                        AsetTetapDAO.update(con, aset);
                    }
                }
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateBahan(Connection con, Bahan b) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            BahanDAO.update(con, b);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newBarang(Connection con, Barang barang) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            if (BarangDAO.get(con, barang.getKodeBarang()) != null) {
                status = "Kode barang sudah pernah terdaftar";
            } else {
                BarangDAO.insert(con, barang);

                Date now = Function.getServerDate(con);
                for (Gudang g : sistem.getListGudang()) {

                    LogBarang log = new LogBarang();
                    log.setTanggal(tglSql.format(now));
                    log.setKodeBarang(barang.getKodeBarang());
                    log.setKodeGudang(g.getKodeGudang());
                    log.setKategori("New Barang");
                    log.setKeterangan("");
                    log.setStokAwal(0);
                    log.setNilaiAwal(0);
                    log.setStokMasuk(0);
                    log.setNilaiMasuk(0);
                    log.setStokKeluar(0);
                    log.setNilaiKeluar(0);
                    log.setStokAkhir(0);
                    log.setNilaiAkhir(0);
                    LogBarangDAO.insert(con, log);

                    StokBarang stok = new StokBarang();
                    stok.setTanggal(tglBarang.format(now));
                    stok.setKodeBarang(barang.getKodeBarang());
                    stok.setKodeGudang(g.getKodeGudang());
                    stok.setStokAwal(0);
                    stok.setStokMasuk(0);
                    stok.setStokKeluar(0);
                    stok.setStokAkhir(0);
                    StokBarangDAO.insert(con, stok);
                }
                for (Mesin m : MesinDAO.getAll(con)) {
                    MesinDetailBarang b = new MesinDetailBarang();
                    b.setKodeMesin(m.getKodeMesin());
                    b.setKodeBarang(barang.getKodeBarang());
                    b.setStatus(false);
                    MesinDetailBarangDAO.insert(con, b);
                }
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateBarang(Connection con, Barang barang) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            BarangDAO.update(con, barang);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteBarang(Connection con, Barang barang) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            double stokAkhir = 0;
            for (Gudang g : sistem.getListGudang()) {
                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, barang.getKodeBarang(), g.getKodeGudang());
                stokAkhir = stokAkhir + log.getStokAkhir();
            }
            if (stokAkhir > 0) {
                status = "Barang tidak dapat dihapus, karena stok barang masih ada";
            } else {
                barang.setStatus("false");
                BarangDAO.update(con, barang);

                MesinDetailBarangDAO.deleteByBarang(con, barang);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newCustomer(Connection con, Customer customer) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            CustomerDAO.insert(con, customer);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateCustomer(Connection con, Customer customer) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            CustomerDAO.update(con, customer);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteCustomer(Connection con, Customer customer) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            if (customer.getDeposit() != 0 || customer.getHutang() != 0) {
                status = "Customer tidak dapat dihapus, karena masih memiliki hutang/deposit";
            } else {
                customer.setStatus("false");
                CustomerDAO.update(con, customer);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPegawai(Connection con, Pegawai pegawai) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PegawaiDAO.insert(con, pegawai);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updatePegawai(Connection con, Pegawai pegawai) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PegawaiDAO.update(con, pegawai);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deletePegawai(Connection con, Pegawai pegawai) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
            for (Customer c : listCustomer) {
                if (c.getKodeSales().equals(pegawai.getKodePegawai())) {
                    status = "tidak dapat dihapus, karena pegawai masih terdaftar sebagai sales pada salah satu customer";
                }
            }
            if (status.equals("true")) {
                pegawai.setStatus("false");
                PegawaiDAO.update(con, pegawai);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newSupplier(Connection con, Supplier supplier) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            SupplierDAO.insert(con, supplier);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateSupplier(Connection con, Supplier supplier) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            SupplierDAO.update(con, supplier);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteSupplier(Connection con, Supplier supplier) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            if (supplier.getDeposit() != 0 || supplier.getHutang() != 0) {
                status = "tidak dapat dihapus, karena masih memiliki hutang/deposit";
            }

            if (status.equals("true")) {
                supplier.setStatus("false");
                SupplierDAO.update(con, supplier);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newUser(Connection con, User user) {
        try {
            String status = "true";
            con.setAutoCommit(false);

            for (User u : UserDAO.getAll(con)) {
                if (user.getKodeUser().equals(u.getKodeUser())) {
                    status = "Username sudah pernah terdaftar";
                }
            }
            UserDAO.insert(con, user);
            for (Otoritas o : user.getOtoritas()) {
                OtoritasDAO.insert(con, o);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateUser(Connection con, User user) {
        try {
            con.setAutoCommit(false);

            UserDAO.update(con, user);
            OtoritasDAO.delete(con, user);
            for (Otoritas o : user.getOtoritas()) {
                OtoritasDAO.insert(con, o);
            }

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteUser(Connection con, User user) {
        try {
            con.setAutoCommit(false);

            user.setStatus("false");
            UserDAO.update(con, user);
            OtoritasDAO.delete(con, user);

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newUserApp(Connection con, UserApp user) {
        try {
            String status = "true";
            con.setAutoCommit(false);

            for (UserApp u : UserAppDAO.getAll(con)) {
                if (user.getKodeUser().equals(u.getKodeUser())) {
                    status = "Username sudah pernah terdaftar";
                }
            }
            UserAppDAO.insert(con, user);
            for (VerifikasiApp v : user.getListVerifikasi()) {
                VerifikasiAppDAO.insert(con, v);
            }
            for (UserMesinApp m : user.getListUserMesinApp()) {
                UserMesinAppDAO.insert(con, m);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateUserApp(Connection con, UserApp user) {
        try {
            con.setAutoCommit(false);

            UserAppDAO.update(con, user);
            VerifikasiAppDAO.delete(con, user);
            for (VerifikasiApp o : user.getListVerifikasi()) {
                VerifikasiAppDAO.insert(con, o);
            }
            UserMesinAppDAO.delete(con, user);
            for (UserMesinApp o : user.getListUserMesinApp()) {
                UserMesinAppDAO.insert(con, o);
            }

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteUserApp(Connection con, UserApp user) {
        try {
            con.setAutoCommit(false);

            UserAppDAO.delete(con, user.getKodeUser());
            VerifikasiAppDAO.delete(con, user);
            UserMesinAppDAO.delete(con, user);

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newMesin(Connection con, Mesin mesin) {
        try {
            String status = "true";
            con.setAutoCommit(false);

            for (Mesin u : MesinDAO.getAll(con)) {
                if (mesin.getKodeMesin().equals(u.getKodeMesin())) {
                    status = "kode mesin sudah pernah terdaftar";
                }
            }
            MesinDAO.insert(con, mesin);
            for (MesinDetailBarang o : mesin.getListDetailBarang()) {
                MesinDetailBarangDAO.insert(con, o);
            }
            for (UserApp u : UserAppDAO.getAll(con)) {
                UserMesinApp m = new UserMesinApp();
                m.setKodeUser(u.getKodeUser());
                m.setKodeMesin(mesin.getKodeMesin());
                m.setStatus(false);
                UserMesinAppDAO.insert(con, m);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateMesin(Connection con, Mesin mesin) {
        try {
            con.setAutoCommit(false);

            MesinDAO.update(con, mesin);
            MesinDetailBarangDAO.delete(con, mesin);
            for (MesinDetailBarang o : mesin.getListDetailBarang()) {
                MesinDetailBarangDAO.insert(con, o);
            }

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteMesin(Connection con, Mesin mesin) {
        try {
            con.setAutoCommit(false);

            MesinDAO.delete(con, mesin);
            MesinDetailBarangDAO.delete(con, mesin);
            UserMesinAppDAO.deleteByMesin(con, mesin);

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newKategoriBahan(Connection con, KategoriBahan k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriBahanDAO.insert(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteKategoriBahan(Connection con, KategoriBahan k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            k.setStatus("false");
            KategoriBahanDAO.update(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newGudang(Connection con, Gudang k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            GudangDAO.insert(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteGudang(Connection con, Gudang k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            GudangDAO.delete(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newKategoriKeuangan(Connection con, KategoriKeuangan t) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriKeuanganDAO.insert(con, t);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteKategoriKeuangan(Connection con, KategoriKeuangan t) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            double saldo = KeuanganDAO.getSaldoAkhir(con, tglBarang.format(Function.getServerDate(con)), t.getKodeKeuangan());
            if (saldo != 0) {
                status = "Tidak dapat dihapus,karena saldo " + t.getKodeKeuangan() + " masih ada";
            } else {
                KategoriKeuanganDAO.delete(con, t);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newKategoriTransaksi(Connection con, KategoriTransaksi k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriTransaksiDAO.insert(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String updateKategoriTransaksi(Connection con, KategoriTransaksi k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriTransaksiDAO.update(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteKategoriTransaksi(Connection con, KategoriTransaksi k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            k.setStatus("false");
            KategoriTransaksiDAO.update(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newKategoriHutang(Connection con, KategoriHutang k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriHutangDAO.insert(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteKategoriHutang(Connection con, KategoriHutang k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriHutangDAO.delete(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newKategoriPiutang(Connection con, KategoriPiutang k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriPiutangDAO.insert(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteKategoriPiutang(Connection con, KategoriPiutang k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            KategoriPiutangDAO.delete(con, k);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPemesanan(Connection con, PemesananBarangHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noPemesanan = PemesananBarangHeadDAO.getId(con, date);
            pemesanan.setNoPemesanan(noPemesanan);
            pemesanan.setTglPemesanan(tglSql.format(date));
            PemesananBarangHeadDAO.insert(con, pemesanan);
            int noUrut = 1;
            for (PemesananBarangDetail detail : pemesanan.getListPemesananBarangDetail()) {
                detail.setNoPemesanan(noPemesanan);
                detail.setNoUrut(noUrut);
                PemesananBarangDetailDAO.insert(con, detail);

                noUrut = noUrut + 1;
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String editPemesanan(Connection con, PemesananBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PemesananBarangHeadDAO.update(con, p);
            PemesananBarangDetailDAO.delete(con, p.getNoPemesanan());
            for (PemesananBarangDetail detail : p.getListPemesananBarangDetail()) {
                PemesananBarangDetailDAO.insert(con, detail);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPemesanan(Connection con, PemesananBarangHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            pemesanan.setTglBatal(tglSql.format(date));
            pemesanan.setUserBatal(sistem.getUser().getKodeUser());
            pemesanan.setStatus("false");
            PemesananBarangHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String selesaiApprovePemesanan(Connection con, PemesananBarangHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PemesananBarangHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPemesananCoil(Connection con, PemesananBahanHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noPemesanan = PemesananBahanHeadDAO.getId(con, date);
            pemesanan.setNoPemesanan(noPemesanan);
            pemesanan.setTglPemesanan(tglSql.format(date));
            PemesananBahanHeadDAO.insert(con, pemesanan);
            for (PemesananBahanDetail detail : pemesanan.getListPemesananBahanDetail()) {
                detail.setNoPemesanan(noPemesanan);
                PemesananBahanDetailDAO.insert(con, detail);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPemesananCoil(Connection con, PemesananBahanHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            List<PenjualanBahanHead> listPenjualan = PenjualanBahanHeadDAO.getAllByNoPemesananAndStatus(
                    con, pemesanan.getNoPemesanan(), "true");
            if (listPenjualan.isEmpty()) {
                pemesanan.setTglBatal(tglSql.format(date));
                pemesanan.setUserBatal(sistem.getUser().getKodeUser());
                pemesanan.setStatus("false");
                PemesananBahanHeadDAO.update(con, pemesanan);
            } else {
                status = "Pemesanan tidak dapat dibatalkan, karena sudah ada penjualan";
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String selesaiPemesananCoil(Connection con, PemesananBahanHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PemesananBahanHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newTerimaDownPayment(Connection con, PemesananBarangHead p, Date tglTransaksi, double jumlahBayar, String tipeKeuangan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            p.setDownPayment(p.getDownPayment() + jumlahBayar);
            p.setSisaDownPayment(p.getSisaDownPayment() + jumlahBayar);
            PemesananBarangHeadDAO.update(con, p);

            Customer customer = p.getCustomer();
            customer.setDeposit(customer.getDeposit() + jumlahBayar);
            CustomerDAO.update(con, customer);

            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con, tglTransaksi));
            h.setTglHutang(tglSql.format(tglTransaksi));
            h.setKategori("Terima Pembayaran Down Payment");
            h.setKeterangan(p.getNoPemesanan());
            h.setTipeKeuangan(tipeKeuangan);
            h.setJumlahHutang(jumlahBayar);
            h.setPembayaran(0);
            h.setSisaHutang(jumlahBayar);
            h.setJatuhTempo("2000-01-01");
            h.setKodeUser(sistem.getUser().getKodeUser());
            h.setStatus("open");
            HutangDAO.insert(con, h);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), tipeKeuangan, "Terima Pembayaran Down Payment", h.getNoHutang(),
                    "Terima Pembayaran Down Payment - " + customer.getNama() + " - " + p.getNoPemesanan(), jumlahBayar, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Hutang", "Terima Pembayaran Down Payment", h.getNoHutang(),
                    "Terima Pembayaran Down Payment - " + customer.getNama() + " - " + p.getNoPemesanan(), jumlahBayar, sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalTerimaDownPayment(Connection con, Hutang h) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            HutangDAO.delete(con, h);

            PemesananBarangHead pemesanan = h.getPemesananHead();
            pemesanan.setDownPayment(pemesanan.getDownPayment() - h.getJumlahHutang());
            pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() - h.getJumlahHutang());
            PemesananBarangHeadDAO.update(con, pemesanan);

            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit() - h.getJumlahHutang());
            CustomerDAO.update(con, customer);

            KeuanganDAO.deleteByNoTransaksi(con, h.getTipeKeuangan(), "Terima Pembayaran Down Payment", h.getNoHutang());
            KeuanganDAO.deleteByNoTransaksi(con, "Hutang", "Terima Pembayaran Down Payment", h.getNoHutang());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newTerimaDownPaymentCoil(Connection con, PemesananBahanHead p, Date tglTransaksi, double jumlahBayar, String tipeKeuangan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            p.setDownPayment(p.getDownPayment() + jumlahBayar);
            p.setSisaDownPayment(p.getSisaDownPayment() + jumlahBayar);
            PemesananBahanHeadDAO.update(con, p);

            Customer customer = p.getCustomer();
            customer.setDeposit(customer.getDeposit() + jumlahBayar);
            CustomerDAO.update(con, customer);

            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con, tglTransaksi));
            h.setTglHutang(tglSql.format(tglTransaksi));
            h.setKategori("Terima Pembayaran Down Payment");
            h.setKeterangan(p.getNoPemesanan());
            h.setTipeKeuangan(tipeKeuangan);
            h.setJumlahHutang(jumlahBayar);
            h.setPembayaran(0);
            h.setSisaHutang(jumlahBayar);
            h.setJatuhTempo("2000-01-01");
            h.setKodeUser(sistem.getUser().getKodeUser());
            h.setStatus("open");
            HutangDAO.insert(con, h);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), tipeKeuangan, "Terima Pembayaran Down Payment", h.getNoHutang(),
                    "Terima Pembayaran Down Payment - " + customer.getNama() + " - " + p.getNoPemesanan(), jumlahBayar, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Hutang", "Terima Pembayaran Down Payment", h.getNoHutang(),
                    "Terima Pembayaran Down Payment - " + customer.getNama() + " - " + p.getNoPemesanan(), jumlahBayar, sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalTerimaDownPaymentCoil(Connection con, Hutang h) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            HutangDAO.delete(con, h);

            PemesananBahanHead pemesanan = h.getPemesananBahanHead();
            pemesanan.setDownPayment(pemesanan.getDownPayment() - h.getJumlahHutang());
            pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() - h.getJumlahHutang());
            PemesananBahanHeadDAO.update(con, pemesanan);

            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit() - h.getJumlahHutang());
            CustomerDAO.update(con, customer);

            KeuanganDAO.deleteByNoTransaksi(con, h.getTipeKeuangan(), "Terima Pembayaran Down Payment", h.getNoHutang());
            KeuanganDAO.deleteByNoTransaksi(con, "Hutang", "Terima Pembayaran Down Payment", h.getNoHutang());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPengiriman(Connection con, PenjualanBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            String noPenjualan = PenjualanBarangHeadDAO.getId(con, date);
            p.setNoPenjualan(noPenjualan);
            p.setTglPengiriman(tglSql.format(date));
            PenjualanBarangHeadDAO.insert(con, p);
            for (PenjualanBarangDetail d : p.getListPenjualanBarangDetail()) {
                d.setNoPenjualan(noPenjualan);
                PenjualanBarangDetailDAO.insert(con, d);

                PemesananBarangDetail detailPemesanan = PemesananBarangDetailDAO.get(con, d.getNoPemesanan(), d.getNoUrut());
                detailPemesanan.setQtyTerkirim(detailPemesanan.getQtyTerkirim() + d.getQty());
                PemesananBarangDetailDAO.update(con, detailPemesanan);
            }

            double qtyBelumDikirim = 0;
            PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, p.getNoPemesanan());
            List<PemesananBarangDetail> listPemesanan = PemesananBarangDetailDAO.getAllByNoPemesanan(con, p.getNoPemesanan());
            for (PemesananBarangDetail d : listPemesanan) {
                qtyBelumDikirim = qtyBelumDikirim + d.getQty() - d.getQtyTerkirim();
            }
            if (qtyBelumDikirim == 0) {
                pemesanan.setStatus("close");
            }
            PemesananBarangHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String verifikasiPengiriman(Connection con, PenjualanBarangHead penjualan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            penjualan.setTglPenjualan(tglSql.format(date));
            penjualan.setKodeUser(sistem.getUser().getKodeUser());
            penjualan.setStatus("true");

            PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, penjualan.getNoPemesanan());
            double dp = pemesanan.getSisaDownPayment();
            if (penjualan.getTotalPenjualan() >= dp) {
                penjualan.setPembayaran(dp);
            } else if (penjualan.getTotalPenjualan() < dp) {
                penjualan.setPembayaran(penjualan.getTotalPenjualan());
            }
            penjualan.setSisaPembayaran(penjualan.getTotalPenjualan() - penjualan.getPembayaran());

            String noKeuangan = KeuanganDAO.getId(con, date);

            PenjualanBarangHeadDAO.update(con, penjualan);

            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), penjualan.getTglPenjualan(), "Penjualan", "Penjualan", penjualan.getNoPenjualan(),
                    "Penjualan - " + penjualan.getNoPenjualan(), penjualan.getTotalPenjualan(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), penjualan.getTglPenjualan(), "Piutang", "Piutang Penjualan", penjualan.getNoPenjualan(),
                    "Penjualan - " + penjualan.getNoPenjualan(), penjualan.getSisaPembayaran(), sistem.getUser().getKodeUser());
//
//            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), "Penjualan", "Penjualan", penjualan.getNoPenjualan(),
//                    "Penjualan - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), penjualan.getTotalPenjualan(), sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), "Piutang", "Piutang Penjualan", penjualan.getNoPenjualan(),
//                    "Penjualan - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), penjualan.getSisaPembayaran(), sistem.getUser().getKodeUser());

            Piutang piutang = new Piutang();
            piutang.setNoPiutang(PiutangDAO.getId(con, date));
            piutang.setTglPiutang(penjualan.getTglPenjualan());
            piutang.setKategori("Piutang Penjualan");
            piutang.setKeterangan(penjualan.getNoPenjualan());
            piutang.setTipeKeuangan("Penjualan");
            piutang.setJumlahPiutang(penjualan.getTotalPenjualan());
            piutang.setPembayaran(penjualan.getPembayaran());
            piutang.setSisaPiutang(penjualan.getSisaPembayaran());
            piutang.setJatuhTempo("2000-01-01");
            piutang.setKodeUser(sistem.getUser().getKodeUser());
            if (piutang.getSisaPiutang() > 0) {
                piutang.setStatus("open");
            } else {
                piutang.setStatus("close");
            }
            PiutangDAO.insert(con, piutang);

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if (penjualan.getSisaPembayaran() > 0) {
                customer.setHutang(customer.getHutang() + penjualan.getSisaPembayaran());
            }

            if (penjualan.getPembayaran() > 0) {
                customer.setDeposit(customer.getDeposit() - penjualan.getPembayaran());

                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() - penjualan.getPembayaran());

                TerimaPembayaran tp = new TerimaPembayaran();
                tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con, date));
                tp.setTglTerima(penjualan.getTglPenjualan());
                tp.setNoPiutang(piutang.getNoPiutang());
                tp.setJumlahPembayaran(penjualan.getPembayaran());
                tp.setTipeKeuangan("Down Payment");
                tp.setCatatan(penjualan.getNoPemesanan());
                tp.setKodeUser(sistem.getUser().getKodeUser());
                tp.setTglBatal("2000-01-01 00:00:00");
                tp.setUserBatal("");
                tp.setStatus("true");
                TerimaPembayaranDAO.insert(con, tp);

                double bayar = penjualan.getPembayaran();
                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                for (Hutang h : listHutang) {
                    if (h.getSisaHutang() > bayar) {
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con, date));
                        pembayaran.setTglPembayaran(penjualan.getTglPenjualan());
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(bayar);
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran() + bayar);
                        h.setSisaHutang(h.getSisaHutang() - bayar);
                        HutangDAO.update(con, h);

                        bayar = 0;
                    } else {
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con, date));
                        pembayaran.setTglPembayaran(penjualan.getTglPenjualan());
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(h.getSisaHutang());
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran() + h.getSisaHutang());
                        h.setSisaHutang(h.getSisaHutang() - h.getSisaHutang());
                        h.setStatus("close");
                        HutangDAO.update(con, h);

                        bayar = bayar - h.getSisaHutang();
                    }
                }
                insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), penjualan.getTglPenjualan(), "Hutang", "Terima Pembayaran Down Payment", penjualan.getNoPenjualan(),
                        "Penjualan - " + penjualan.getNoPenjualan(), -penjualan.getPembayaran(), sistem.getUser().getKodeUser());

//                insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), "Hutang", "Terima Pembayaran Down Payment", penjualan.getNoPenjualan(),
//                        "Penjualan - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), -penjualan.getPembayaran(), sistem.getUser().getKodeUser());
            }
            CustomerDAO.update(con, customer);

//            for (PenjualanBarangDetail detail : penjualan.getListPenjualanBarangDetail()) {
//                PemesananBarangDetail d = PemesananBarangDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
//                d.setQtyTerkirim(d.getQtyTerkirim() + detail.getQty());
//                PemesananBarangDetailDAO.update(con, d);
//            }
//            double qtyBelumDikirim = 0;
//            List<PemesananBarangDetail> listPemesanan = PemesananBarangDetailDAO.getAllByNoPemesanan(con, pemesanan.getNoPemesanan());
//            for (PemesananBarangDetail d : listPemesanan) {
//                qtyBelumDikirim = qtyBelumDikirim + d.getQty() - d.getQtyTerkirim();
//            }
//            if (qtyBelumDikirim == 0) {
//                pemesanan.setStatus("close");
//            }
            PemesananBarangHeadDAO.update(con, pemesanan);

            List<PenjualanBarangDetail> stokBarang = new ArrayList<>();
            double hpp = 0;
            for (PenjualanBarangDetail d : penjualan.getListPenjualanBarangDetail()) {
                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), penjualan.getKodeGudang());
                if (log.getStokAkhir() != 0) {
                    d.setNilai(log.getNilaiAkhir() / log.getStokAkhir());
                }
                PenjualanBarangDetailDAO.update(con, d);

                hpp = hpp + d.getNilai() * d.getQty();

                Boolean inStok = false;
                for (PenjualanBarangDetail detail : stokBarang) {
                    if (d.getKodeBarang().equals(detail.getKodeBarang())) {
                        detail.setNilai((detail.getNilai() * detail.getQty() + d.getNilai() * d.getQty())
                                / (detail.getQty() + d.getQty()));
                        detail.setQty(pembulatan(detail.getQty() + d.getQty()));
                        inStok = true;
                    }
                }
                if (!inStok) {
                    PenjualanBarangDetail temp = new PenjualanBarangDetail();
                    temp.setNoPenjualan(d.getNoPenjualan());
                    temp.setNoPemesanan(d.getNoPemesanan());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    temp.setHargaJual(d.getHargaJual());
                    temp.setTotal(d.getTotal());
                    stokBarang.add(temp);
                }
            }
            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), penjualan.getTglPenjualan(), "HPP", "Penjualan", penjualan.getNoPenjualan(),
                    "Penjualan - " + penjualan.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), penjualan.getTglPenjualan(), "Stok Barang", penjualan.getKodeGudang(), penjualan.getNoPenjualan(),
                    "Penjualan - " + penjualan.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());
//
//            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), "HPP", "Penjualan", penjualan.getNoPenjualan(),
//                    "Penjualan - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, penjualan.getTglPenjualan(), "Stok Barang", penjualan.getKodeGudang(), penjualan.getNoPenjualan(),
//                    "Penjualan - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());

            for (PenjualanBarangDetail d : stokBarang) {
                status = insertStokAndLogBarang(con, date, d.getKodeBarang(), penjualan.getKodeGudang(), "Penjualan", penjualan.getNoPenjualan(),
                        0, 0, d.getQty(), (d.getNilai() * d.getQty()), status);
//                resetStokDanLogBarang(con, d.getKodeBarang(), penjualan.getKodeGudang(), tglSql.format(date), Function.getServerDate(con));
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String editPenjualan(Connection con, PenjualanBarangHead penjualanLama, PenjualanBarangHead penjualanBaru) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date now = Function.getServerDate(con);
            Date tglPenjualan = tglSql.parse(penjualanBaru.getTglPenjualan());
            String noKeuangan = KeuanganDAO.getId(con, tglPenjualan);

            PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, penjualanLama.getNoPemesanan());

            List<PemesananBarangDetail> listPemesanan = PemesananBarangDetailDAO.getAllByNoPemesanan(con, pemesanan.getNoPemesanan());

            //update penjualan lama
            penjualanLama.setTglBatal(tglSql.format(now));
            penjualanLama.setUserBatal(sistem.getUser().getKodeUser());
            penjualanLama.setStatus("false");
            PenjualanBarangHeadDAO.update(con, penjualanLama);

            //insert penjualan baru
            double sisadp = pemesanan.getSisaDownPayment() + penjualanLama.getPembayaran();
            if (penjualanBaru.getTotalPenjualan() >= sisadp) {
                penjualanBaru.setPembayaran(sisadp);
            } else if (penjualanBaru.getTotalPenjualan() < sisadp) {
                penjualanBaru.setPembayaran(penjualanBaru.getTotalPenjualan());
            }
            penjualanBaru.setSisaPembayaran(penjualanBaru.getTotalPenjualan() - penjualanBaru.getPembayaran());
            PenjualanBarangHeadDAO.insert(con, penjualanBaru);

            //delete piutang lama
            Piutang piutangLama = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                    con, "Piutang Penjualan", penjualanLama.getNoPenjualan(), "%");
            PiutangDAO.delete(con, piutangLama);

            //insert piutang baru
            Piutang piutangBaru = new Piutang();
            piutangBaru.setNoPiutang(PiutangDAO.getId(con, tglPenjualan));
            piutangBaru.setTglPiutang(penjualanBaru.getTglPenjualan());
            piutangBaru.setKategori("Piutang Penjualan");
            piutangBaru.setKeterangan(penjualanBaru.getNoPenjualan());
            piutangBaru.setTipeKeuangan("Penjualan");
            piutangBaru.setJumlahPiutang(penjualanBaru.getTotalPenjualan());
            piutangBaru.setPembayaran(penjualanBaru.getPembayaran());
            piutangBaru.setSisaPiutang(penjualanBaru.getSisaPembayaran());
            piutangBaru.setJatuhTempo("2000-01-01");
            piutangBaru.setKodeUser(sistem.getUser().getKodeUser());
            if (piutangBaru.getSisaPiutang() > 0) {
                piutangBaru.setStatus("open");
            } else {
                piutangBaru.setStatus("close");
            }
            PiutangDAO.insert(con, piutangBaru);

            KeuanganDAO.deleteByDeskripsi(con, "Penjualan", "Penjualan", "Penjualan - " + penjualanLama.getNoPenjualan());
            KeuanganDAO.deleteByDeskripsi(con, "Piutang", "Piutang Penjualan", "Penjualan - " + penjualanLama.getNoPenjualan());

//            KeuanganDAO.delete(con, "Penjualan", "Penjualan", penjualanLama.getNoPenjualan());
//            KeuanganDAO.delete(con, "Piutang", "Piutang Penjualan", penjualanLama.getNoPenjualan());
            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), penjualanBaru.getTglPenjualan(), "Penjualan", "Penjualan", penjualanBaru.getNoPenjualan(),
                    "Penjualan - " + penjualanBaru.getNoPenjualan(), penjualanBaru.getTotalPenjualan(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), penjualanBaru.getTglPenjualan(), "Piutang", "Piutang Penjualan", penjualanBaru.getNoPenjualan(),
                    "Penjualan - " + penjualanBaru.getNoPenjualan(), penjualanBaru.getSisaPembayaran(), sistem.getUser().getKodeUser());
//
//            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), "Penjualan", "Penjualan", penjualanBaru.getNoPenjualan(),
//                    "Penjualan - " + penjualanBaru.getCustomer().getNama() + " - " + penjualanBaru.getNoPenjualan(), penjualanBaru.getTotalPenjualan(), sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), "Piutang", "Piutang Penjualan", penjualanBaru.getNoPenjualan(),
//                    "Penjualan - " + penjualanBaru.getCustomer().getNama() + " - " + penjualanBaru.getNoPenjualan(), penjualanBaru.getSisaPembayaran(), sistem.getUser().getKodeUser());

            //batal terima pembayaran dp
            TerimaPembayaran dp = null;
            List<TerimaPembayaran> terimaPembayaran = TerimaPembayaranDAO.getAllByNoPiutangAndStatus(
                    con, piutangLama.getNoPiutang(), "true");
            for (TerimaPembayaran tp : terimaPembayaran) {
                if (tp.getTipeKeuangan().equals("Down Payment")) {
                    dp = tp;
                } else {
                    status = "Tidak dapat dibatal,karena sudah ada pembayaran";
                }
            }
            if (dp != null) {
                TerimaPembayaranDAO.delete(con, dp);

                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                for (Hutang h : listHutang) {
                    List<Pembayaran> pembayaranLama = PembayaranDAO.getAllByNoHutang(con, h.getNoHutang(), "true");
                    for (Pembayaran p : pembayaranLama) {
                        if (p.getTipeKeuangan().equals("Penjualan") && p.getCatatan().equals(penjualanLama.getNoPenjualan())) {
                            p.setTglBatal(tglSql.format(now));
                            p.setUserBatal(sistem.getUser().getKodeUser());
                            p.setStatus("false");
                            PembayaranDAO.update(con, p);

                            h.setPembayaran(h.getPembayaran() - p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang() + p.getJumlahPembayaran());
                        }
                    }
                }
                TerimaPembayaran tp = new TerimaPembayaran();
                tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con, tglPenjualan));
                tp.setTglTerima(penjualanBaru.getTglPenjualan());
                tp.setNoPiutang(piutangBaru.getNoPiutang());
                tp.setJumlahPembayaran(penjualanBaru.getPembayaran());
                tp.setTipeKeuangan("Down Payment");
                tp.setCatatan(penjualanBaru.getNoPemesanan());
                tp.setKodeUser(sistem.getUser().getKodeUser());
                tp.setTglBatal("2000-01-01 00:00:00");
                tp.setUserBatal("");
                tp.setStatus("true");
                TerimaPembayaranDAO.insert(con, tp);

                double bayar = penjualanBaru.getPembayaran();
                for (Hutang h : listHutang) {
                    if (h.getSisaHutang() > bayar) {
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con, tglPenjualan));
                        pembayaran.setTglPembayaran(penjualanBaru.getTglPenjualan());
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(bayar);
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualanBaru.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran() + bayar);
                        h.setSisaHutang(h.getSisaHutang() - bayar);

                        bayar = 0;
                    } else {
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con, tglPenjualan));
                        pembayaran.setTglPembayaran(penjualanBaru.getTglPenjualan());
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(h.getSisaHutang());
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualanBaru.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran() + h.getSisaHutang());
                        h.setSisaHutang(h.getSisaHutang() - h.getSisaHutang());
                        h.setStatus("close");

                        bayar = bayar - h.getSisaHutang();
                    }
                    HutangDAO.update(con, h);
                }
                KeuanganDAO.deleteByDeskripsi(con, "Hutang", "Terima Pembayaran Down Payment", "Penjualan - " + penjualanLama.getNoPenjualan());
//                KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", penjualanLama.getNoPenjualan());

                insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), penjualanBaru.getTglPenjualan(), "Hutang", "Terima Pembayaran Down Payment", penjualanBaru.getNoPenjualan(),
                        "Penjualan - " + penjualanBaru.getNoPenjualan(), -penjualanBaru.getPembayaran(), sistem.getUser().getKodeUser());

//                insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), "Hutang", "Terima Pembayaran Down Payment", penjualanBaru.getNoPenjualan(),
//                        "Penjualan - " + penjualanBaru.getCustomer().getNama() + " - " + penjualanBaru.getNoPenjualan(), -penjualanBaru.getPembayaran(), sistem.getUser().getKodeUser());
            }

            penjualanLama.setListPenjualanBarangDetail(PenjualanBarangDetailDAO.getAllPenjualanDetail(con, penjualanLama.getNoPenjualan()));

            List<StokBarang> allUpdatedStok = new ArrayList<>();
            for (PenjualanBarangDetail d : penjualanLama.getListPenjualanBarangDetail()) {
                Boolean x = true;
                for (StokBarang s : allUpdatedStok) {
                    if (s.getKodeBarang().equals(d.getKodeBarang())) {
                        s.setStokKeluar(s.getStokKeluar() - d.getQty());
                        s.setStokAkhir(s.getStokAkhir() + d.getQty());
                        x = false;
                    }
                }
                if (x) {
                    StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(penjualanLama.getTglPenjualan())), d.getKodeBarang(), penjualanLama.getKodeGudang());
                    stok.setStokKeluar(stok.getStokKeluar() - d.getQty());
                    stok.setStokAkhir(stok.getStokAkhir() + d.getQty());

                    allUpdatedStok.add(stok);
                }
                LogBarangDAO.delete(con, d.getKodeBarang(), penjualanLama.getKodeGudang(), "Penjualan", penjualanLama.getNoPenjualan());
            }

            double hpp = 0;
            for (PenjualanBarangDetail d : penjualanBaru.getListPenjualanBarangDetail()) {
                Boolean x = true;
                for (StokBarang s : allUpdatedStok) {
                    if (s.getKodeBarang().equals(d.getKodeBarang())) {
                        s.setStokKeluar(s.getStokKeluar() + d.getQty());
                        s.setStokAkhir(s.getStokAkhir() - d.getQty());
                        x = false;
                    }
                }
                if (x) {
                    StokBarang stokBarang = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(penjualanBaru.getTglPenjualan())),
                            d.getKodeBarang(), penjualanBaru.getKodeGudang());
                    stokBarang.setStokKeluar(stokBarang.getStokKeluar() + d.getQty());
                    stokBarang.setStokAkhir(stokBarang.getStokAkhir() - d.getQty());
                    allUpdatedStok.add(stokBarang);
                }

                LogBarang logUmum = LogBarangDAO.getLastBeforeDateAndBarangAndGudang(
                        con, tglBarang.format(tglSql.parse(penjualanBaru.getTglPenjualan())), d.getKodeBarang(), penjualanBaru.getKodeGudang());
                if (logUmum.getStokAkhir() != 0) {
                    d.setNilai(logUmum.getNilaiAkhir() / logUmum.getStokAkhir());
                }
                LogBarang logBarang = new LogBarang();
                logBarang.setTanggal(penjualanBaru.getTglPenjualan());
                logBarang.setKodeBarang(logUmum.getKodeBarang());
                logBarang.setKodeGudang(logUmum.getKodeGudang());
                logBarang.setKategori("Penjualan");
                logBarang.setKeterangan(penjualanBaru.getNoPenjualan());
                logBarang.setStokAwal(logUmum.getStokAkhir());
                logBarang.setNilaiAwal(logUmum.getNilaiAkhir());
                logBarang.setStokMasuk(0);
                logBarang.setNilaiMasuk(0);
                logBarang.setStokKeluar(d.getQty());
                logBarang.setNilaiKeluar(pembulatan(d.getQty() * d.getNilai()));
                logBarang.setStokAkhir(logUmum.getStokAkhir() - d.getQty());
                logBarang.setNilaiAkhir(logUmum.getNilaiAkhir() - pembulatan(d.getQty() * d.getNilai()));
                LogBarangDAO.insert(con, logBarang);

                d.setNoPenjualan(penjualanBaru.getNoPenjualan());
                PenjualanBarangDetailDAO.insert(con, d);

                hpp = hpp + pembulatan(d.getQty() * d.getNilai());

            }
            for (StokBarang s : allUpdatedStok) {
                StokBarangDAO.update(con, s);
                resetStokDanLogBarang(con, s.getKodeBarang(), s.getKodeGudang(), penjualanBaru.getTglPenjualan(), now);
            }

            KeuanganDAO.deleteByDeskripsi(con, "HPP", "Penjualan", "Penjualan - " + penjualanLama.getNoPenjualan());
            KeuanganDAO.deleteByDeskripsi(con, "Stok Barang", penjualanLama.getKodeGudang(), "Penjualan - " + penjualanLama.getNoPenjualan());

//            KeuanganDAO.delete(con, "HPP", "Penjualan", penjualanLama.getNoPenjualan());
//            KeuanganDAO.delete(con, "Stok Barang", penjualanLama.getKodeGudang(), penjualanLama.getNoPenjualan());
            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), penjualanBaru.getTglPenjualan(), "HPP", "Penjualan", penjualanBaru.getNoPenjualan(),
                    "Penjualan - " + penjualanBaru.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), penjualanBaru.getTglPenjualan(), "Stok Barang", penjualanBaru.getKodeGudang(), penjualanBaru.getNoPenjualan(),
                    "Penjualan - " + penjualanBaru.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());

//            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), "HPP", "Penjualan", penjualanBaru.getNoPenjualan(),
//                    "Penjualan - " + penjualanBaru.getCustomer().getNama() + " - " + penjualanBaru.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, penjualanBaru.getTglPenjualan(), "Stok Barang", penjualanBaru.getKodeGudang(), penjualanBaru.getNoPenjualan(),
//                    "Penjualan - " + penjualanBaru.getCustomer().getNama() + " - " + penjualanBaru.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());
            //customer
            Customer customer = CustomerDAO.get(con, penjualanLama.getKodeCustomer());
            customer.setHutang(customer.getHutang() - penjualanLama.getSisaPembayaran() + penjualanBaru.getSisaPembayaran());
            customer.setDeposit(customer.getDeposit() + penjualanLama.getPembayaran() - penjualanBaru.getPembayaran());
            CustomerDAO.update(con, customer);

            //pemesanan
            double qtyBelumDikirim = 0;
            for (PemesananBarangDetail d : listPemesanan) {
                for (PenjualanBarangDetail dlama : penjualanLama.getListPenjualanBarangDetail()) {
                    if (d.getNoUrut() == dlama.getNoUrut()) {
                        d.setQtyTerkirim(d.getQtyTerkirim() - dlama.getQty());
                    }
                }
                for (PenjualanBarangDetail dbaru : penjualanBaru.getListPenjualanBarangDetail()) {
                    if (d.getNoUrut() == dbaru.getNoUrut()) {
                        d.setQtyTerkirim(d.getQtyTerkirim() + dbaru.getQty());
                    }
                }
                qtyBelumDikirim = qtyBelumDikirim + d.getQty() - d.getQtyTerkirim();
                PemesananBarangDetailDAO.update(con, d);
            }
            if (qtyBelumDikirim == 0) {
                pemesanan.setStatus("close");
            } else {
                pemesanan.setStatus("open");
            }
            pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() + penjualanLama.getPembayaran() - penjualanBaru.getPembayaran());
            PemesananBarangHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPengiriman(Connection con, PenjualanBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            p.setTglBatal(tglSql.format(date));
            p.setUserBatal(sistem.getUser().getKodeUser());
            p.setStatus("false");
            PenjualanBarangHeadDAO.update(con, p);

            p.setListPenjualanBarangDetail(PenjualanBarangDetailDAO.getAllPenjualanDetail(con, p.getNoPenjualan()));
            PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, p.getNoPemesanan());
            for (PenjualanBarangDetail detail : p.getListPenjualanBarangDetail()) {
                PemesananBarangDetail d = PemesananBarangDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
                d.setQtyTerkirim(d.getQtyTerkirim() - detail.getQty());
                PemesananBarangDetailDAO.update(con, d);
            }
            pemesanan.setStatus("open");
            PemesananBarangHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPenjualan(Connection con, PenjualanBarangHead penjualan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            penjualan.setTglBatal(tglSql.format(date));
            penjualan.setUserBatal(sistem.getUser().getKodeUser());
            penjualan.setStatus("false");
            PenjualanBarangHeadDAO.update(con, penjualan);

            KeuanganDAO.deleteByDeskripsi(con, "Penjualan", "Penjualan", "Penjualan - " + penjualan.getNoPenjualan());

//            KeuanganDAO.delete(con, "Penjualan", "Penjualan", penjualan.getNoPenjualan());
            Piutang piutang = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                    con, "Piutang Penjualan", penjualan.getNoPenjualan(), "%");
            PiutangDAO.delete(con, piutang);

            KeuanganDAO.deleteByDeskripsi(con, "Piutang", "Piutang Penjualan", "Penjualan - " + penjualan.getNoPenjualan());

//            KeuanganDAO.delete(con, "Piutang", "Piutang Penjualan", penjualan.getNoPenjualan());
            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if (penjualan.getSisaPembayaran() > 0) {
                customer.setHutang(customer.getHutang() - penjualan.getSisaPembayaran());
            }

            PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, penjualan.getNoPemesanan());
            TerimaPembayaran dp = null;
            List<TerimaPembayaran> terimaPembayaran = TerimaPembayaranDAO.getAllByNoPiutangAndStatus(
                    con, piutang.getNoPiutang(), "true");
            for (TerimaPembayaran tp : terimaPembayaran) {
                if (tp.getTipeKeuangan().equals("Down Payment")) {
                    dp = tp;
                } else {
                    status = "Tidak dapat dibatal,karena sudah ada pembayaran";
                }
            }
            if (dp != null) {
                customer.setDeposit(customer.getDeposit() + dp.getJumlahPembayaran());

                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() + dp.getJumlahPembayaran());

                dp.setTglBatal(tglSql.format(date));
                dp.setUserBatal(sistem.getUser().getKodeUser());
                dp.setStatus("false");
                TerimaPembayaranDAO.update(con, dp);

                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                for (Hutang h : listHutang) {
                    List<Pembayaran> pembayaran = PembayaranDAO.getAllByNoHutang(con, h.getNoHutang(), "true");
                    for (Pembayaran p : pembayaran) {
                        if (p.getTipeKeuangan().equals("Penjualan") && p.getCatatan().equals(penjualan.getNoPenjualan())) {
                            p.setTglBatal(tglSql.format(date));
                            p.setUserBatal(sistem.getUser().getKodeUser());
                            p.setStatus("false");
                            PembayaranDAO.update(con, p);

                            h.setPembayaran(h.getPembayaran() - p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang() + p.getJumlahPembayaran());
                            h.setStatus("open");
                            HutangDAO.update(con, h);
                        }
                    }
                }
                KeuanganDAO.deleteByDeskripsi(con, "Hutang", "Terima Pembayaran Down Payment", "Penjualan - " + penjualan.getNoPenjualan());
//                KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", penjualan.getNoPenjualan());
            }
            CustomerDAO.update(con, customer);

            penjualan.setListPenjualanBarangDetail(PenjualanBarangDetailDAO.getAllPenjualanDetail(con, penjualan.getNoPenjualan()));
            for (PenjualanBarangDetail detail : penjualan.getListPenjualanBarangDetail()) {
                PemesananBarangDetail d = PemesananBarangDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
                d.setQtyTerkirim(d.getQtyTerkirim() - detail.getQty());
                PemesananBarangDetailDAO.update(con, d);
            }
            pemesanan.setStatus("open");
            PemesananBarangHeadDAO.update(con, pemesanan);

            List<PenjualanBarangDetail> stokBarang = new ArrayList<>();
            double hpp = 0;
            for (PenjualanBarangDetail d : penjualan.getListPenjualanBarangDetail()) {
                hpp = hpp + d.getNilai() * d.getQty();

                Boolean inStok = false;
                for (PenjualanBarangDetail detail : stokBarang) {
                    if (d.getKodeBarang().equals(detail.getKodeBarang())) {
                        detail.setNilai((detail.getNilai() * detail.getQty() + d.getNilai() * d.getQty())
                                / (detail.getQty() + d.getQty()));
                        detail.setQty(detail.getQty() + d.getQty());
                        inStok = true;
                    }
                }
                if (!inStok) {
                    PenjualanBarangDetail temp = new PenjualanBarangDetail();
                    temp.setNoPenjualan(d.getNoPenjualan());
                    temp.setNoPemesanan(d.getNoPemesanan());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    temp.setHargaJual(d.getHargaJual());
                    temp.setTotal(d.getTotal());
                    stokBarang.add(temp);
                }
            }
            KeuanganDAO.deleteByDeskripsi(con, "HPP", "Penjualan", "Penjualan - " + penjualan.getNoPenjualan());

            KeuanganDAO.deleteByDeskripsi(con, "Stok Barang", penjualan.getKodeGudang(), "Penjualan - " + penjualan.getNoPenjualan());
//            
//            KeuanganDAO.delete(con, "HPP", "Penjualan", penjualan.getNoPenjualan());
//
//            KeuanganDAO.delete(con, "Stok Barang", penjualan.getKodeGudang(), penjualan.getNoPenjualan());

            for (PenjualanBarangDetail d : stokBarang) {
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(penjualan.getTglPenjualan())), d.getKodeBarang(), penjualan.getKodeGudang());
                stok.setStokKeluar(stok.getStokKeluar() - d.getQty());
                stok.setStokAkhir(stok.getStokAkhir() + d.getQty());
                StokBarangDAO.update(con, stok);

                LogBarangDAO.delete(con, d.getKodeBarang(), penjualan.getKodeGudang(), "Penjualan", penjualan.getNoPenjualan());

                resetStokDanLogBarang(con, d.getKodeBarang(), penjualan.getKodeGudang(), penjualan.getTglPenjualan(), date);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPenjualanCoil(Connection con, PenjualanBahanHead penjualan, boolean selesai) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            String noPengiriman = PenjualanBahanHeadDAO.getId(con, date);
            penjualan.setNoPenjualan(noPengiriman);
            penjualan.setTglPenjualan(tglSql.format(date));
            PenjualanBahanHeadDAO.insert(con, penjualan);

            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Penjualan", "Penjualan Coil", penjualan.getNoPenjualan(),
                    "Penjualan Coil - " + penjualan.getNoPenjualan(), penjualan.getTotalPenjualan(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Piutang", "Piutang Penjualan", penjualan.getNoPenjualan(),
                    "Penjualan Coil - " + penjualan.getNoPenjualan(), penjualan.getSisaPembayaran(), sistem.getUser().getKodeUser());

//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Penjualan", "Penjualan Coil", penjualan.getNoPenjualan(),
//                    "Penjualan Coil - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), penjualan.getTotalPenjualan(), sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Piutang", "Piutang Penjualan", penjualan.getNoPenjualan(),
//                    "Penjualan Coil - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), penjualan.getSisaPembayaran(), sistem.getUser().getKodeUser());
            Piutang piutang = new Piutang();
            piutang.setNoPiutang(PiutangDAO.getId(con, date));
            piutang.setTglPiutang(tglSql.format(date));
            piutang.setKategori("Piutang Penjualan");
            piutang.setKeterangan(penjualan.getNoPenjualan());
            piutang.setTipeKeuangan("Penjualan Coil");
            piutang.setJumlahPiutang(penjualan.getTotalPenjualan());
            piutang.setPembayaran(penjualan.getPembayaran());
            piutang.setSisaPiutang(penjualan.getSisaPembayaran());
            piutang.setJatuhTempo("2000-01-01");
            piutang.setKodeUser(sistem.getUser().getKodeUser());
            if (piutang.getSisaPiutang() > 0) {
                piutang.setStatus("open");
            } else {
                piutang.setStatus("close");
            }
            PiutangDAO.insert(con, piutang);

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if (penjualan.getSisaPembayaran() > 0) {
                customer.setHutang(customer.getHutang() + penjualan.getSisaPembayaran());
            }

            PemesananBahanHead pemesanan = penjualan.getPemesananBahanHead();
            if (penjualan.getPembayaran() > 0) {
                customer.setDeposit(customer.getDeposit() - penjualan.getPembayaran());

                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() - penjualan.getPembayaran());

                TerimaPembayaran tp = new TerimaPembayaran();
                tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con, date));
                tp.setTglTerima(tglSql.format(date));
                tp.setNoPiutang(piutang.getNoPiutang());
                tp.setJumlahPembayaran(penjualan.getPembayaran());
                tp.setTipeKeuangan("Down Payment");
                tp.setCatatan(penjualan.getNoPemesanan());
                tp.setKodeUser(sistem.getUser().getKodeUser());
                tp.setTglBatal("2000-01-01 00:00:00");
                tp.setUserBatal("");
                tp.setStatus("true");
                TerimaPembayaranDAO.insert(con, tp);

                double bayar = penjualan.getPembayaran();
                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                for (Hutang h : listHutang) {
                    if (h.getSisaHutang() > bayar) {
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con, date));
                        pembayaran.setTglPembayaran(tglSql.format(date));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(bayar);
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran() + bayar);
                        h.setSisaHutang(h.getSisaHutang() - bayar);
                        HutangDAO.update(con, h);

                        bayar = 0;
                    } else {
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con, date));
                        pembayaran.setTglPembayaran(tglSql.format(date));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(h.getSisaHutang());
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran() + h.getSisaHutang());
                        h.setSisaHutang(h.getSisaHutang() - h.getSisaHutang());
                        h.setStatus("close");
                        HutangDAO.update(con, h);

                        bayar = bayar - h.getSisaHutang();
                    }
                }
                insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Hutang", "Terima Pembayaran Down Payment", penjualan.getNoPenjualan(),
                        "Penjualan Coil - " + penjualan.getNoPenjualan(), -penjualan.getPembayaran(), sistem.getUser().getKodeUser());

//                insertKeuangan(con, noKeuangan, tglSql.format(date), "Hutang", "Terima Pembayaran Down Payment", penjualan.getNoPenjualan(),
//                        "Penjualan Coil - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), -penjualan.getPembayaran(), sistem.getUser().getKodeUser());
            }
            CustomerDAO.update(con, customer);

            if (selesai) {
                pemesanan.setStatus("close");
            }
            PemesananBahanHeadDAO.update(con, pemesanan);

            double hpp = 0;
            for (PenjualanBahanDetail d : penjualan.getListPenjualanBahanDetail()) {
                d.setNoPenjualan(noPengiriman);
                LogBahan log = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBahan(), penjualan.getKodeGudang());
                if (log.getStokAkhir() != 0) {
                    d.setNilai(log.getNilaiAkhir() / log.getStokAkhir());
                }
                PenjualanBahanDetailDAO.insert(con, d);

                hpp = hpp + log.getNilaiAkhir();
            }
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "HPP", "Penjualan Coil", penjualan.getNoPenjualan(),
                    "Penjualan Coil - " + penjualan.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", penjualan.getKodeGudang(), penjualan.getNoPenjualan(),
                    "Penjualan Coil - " + penjualan.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());
//
//            insertKeuangan(con, noKeuangan, tglSql.format(date), "HPP", "Penjualan Coil", penjualan.getNoPenjualan(),
//                    "Penjualan Coil - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Bahan", penjualan.getKodeGudang(), penjualan.getNoPenjualan(),
//                    "Penjualan Coil - " + penjualan.getCustomer().getNama() + " - " + penjualan.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());

            for (PenjualanBahanDetail d : penjualan.getListPenjualanBahanDetail()) {
                status = insertStokAndLogBahan(con, date, d.getKodeBahan(), penjualan.getKodeGudang(), "Penjualan", penjualan.getNoPenjualan(),
                        0, 0, d.getBeratBersih(), (d.getNilai() * d.getBeratBersih()), status);

                Bahan bahan = BahanDAO.get(con, d.getKodeBahan());
                bahan.setStatus("false");
                BahanDAO.update(con, bahan);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPenjualanCoil(Connection con, PenjualanBahanHead penjualan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            penjualan.setTglBatal(tglSql.format(date));
            penjualan.setUserBatal(sistem.getUser().getKodeUser());
            penjualan.setStatus("false");
            PenjualanBahanHeadDAO.update(con, penjualan);

            KeuanganDAO.deleteByDeskripsi(con, "Penjualan", "Penjualan Coil", "Penjualan Coil - " + penjualan.getNoPenjualan());
//            KeuanganDAO.delete(con, "Penjualan", "Penjualan Coil", penjualan.getNoPenjualan());

            Piutang piutang = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                    con, "Piutang Penjualan", penjualan.getNoPenjualan(), "%");
            PiutangDAO.delete(con, piutang);

            KeuanganDAO.deleteByDeskripsi(con, "Piutang", "Piutang Penjualan", "Penjualan Coil - " + penjualan.getNoPenjualan());
//            KeuanganDAO.delete(con, "Piutang", "Piutang Penjualan", penjualan.getNoPenjualan());

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if (penjualan.getSisaPembayaran() > 0) {
                customer.setHutang(customer.getHutang() - penjualan.getSisaPembayaran());
            }

            PemesananBahanHead pemesanan = PemesananBahanHeadDAO.get(con, penjualan.getNoPemesanan());
            TerimaPembayaran dp = null;
            List<TerimaPembayaran> terimaPembayaran = TerimaPembayaranDAO.getAllByNoPiutangAndStatus(
                    con, piutang.getNoPiutang(), "true");
            for (TerimaPembayaran tp : terimaPembayaran) {
                if (tp.getTipeKeuangan().equals("Down Payment")) {
                    dp = tp;
                } else {
                    status = "Tidak dapat dibatal,karena sudah ada pembayaran";
                }
            }
            if (dp != null) {
                customer.setDeposit(customer.getDeposit() + dp.getJumlahPembayaran());

                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() + dp.getJumlahPembayaran());

                dp.setTglBatal(tglSql.format(date));
                dp.setUserBatal(sistem.getUser().getKodeUser());
                dp.setStatus("false");
                TerimaPembayaranDAO.update(con, dp);

                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                for (Hutang h : listHutang) {
                    List<Pembayaran> pembayaran = PembayaranDAO.getAllByNoHutang(con, h.getNoHutang(), "true");
                    for (Pembayaran p : pembayaran) {
                        if (p.getTipeKeuangan().equals("Penjualan") && p.getCatatan().equals(penjualan.getNoPenjualan())) {
                            p.setTglBatal(tglSql.format(date));
                            p.setUserBatal(sistem.getUser().getKodeUser());
                            p.setStatus("false");
                            PembayaranDAO.update(con, p);

                            h.setPembayaran(h.getPembayaran() - p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang() + p.getJumlahPembayaran());
                            h.setStatus("close");
                            HutangDAO.update(con, h);
                        }
                    }
                }
                KeuanganDAO.deleteByDeskripsi(con, "Hutang", "Terima Pembayaran Down Payment", "Penjualan Coil - " + penjualan.getNoPenjualan());
//                KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", penjualan.getNoPenjualan());
            }
            CustomerDAO.update(con, customer);

            pemesanan.setStatus("open");
            PemesananBahanHeadDAO.update(con, pemesanan);

            penjualan.setListPenjualanBahanDetail(PenjualanBahanDetailDAO.getAllPenjualanCoilDetail(con, penjualan.getNoPenjualan()));
            List<PenjualanBahanDetail> stokBahan = new ArrayList<>();
            double hpp = 0;
            for (PenjualanBahanDetail d : penjualan.getListPenjualanBahanDetail()) {
                hpp = hpp + d.getNilai() * d.getBeratBersih();

                Boolean inStok = false;
                for (PenjualanBahanDetail detail : stokBahan) {
                    if (d.getKodeBahan().equals(detail.getKodeBahan())) {
                        detail.setNilai((detail.getNilai() * detail.getBeratBersih() + d.getNilai() * d.getBeratBersih())
                                / (detail.getBeratBersih() + d.getBeratBersih()));
                        detail.setBeratBersih(detail.getBeratBersih() + d.getBeratBersih());
                        inStok = true;
                    }
                }
                if (!inStok) {
                    stokBahan.add(d);
                }
            }

            KeuanganDAO.deleteByDeskripsi(con, "HPP", "Penjualan Coil", "Penjualan Coil - " + penjualan.getNoPenjualan());
            KeuanganDAO.deleteByDeskripsi(con, "Stok Bahan", penjualan.getKodeGudang(), "Penjualan Coil - " + penjualan.getNoPenjualan());
//            KeuanganDAO.delete(con, "HPP", "Penjualan Coil", penjualan.getNoPenjualan());
//            KeuanganDAO.delete(con, "Stok Bahan", penjualan.getKodeGudang(), penjualan.getNoPenjualan());

            for (PenjualanBahanDetail d : stokBahan) {
                StokBahan stok = StokBahanDAO.get(con,
                        tglBarang.format(tglSql.parse(penjualan.getTglPenjualan())), d.getKodeBahan(), penjualan.getKodeGudang());
                stok.setStokKeluar(stok.getStokKeluar() - d.getBeratBersih());
                stok.setStokAkhir(stok.getStokAkhir() + d.getBeratBersih());
                StokBahanDAO.update(con, stok);

                LogBahanDAO.delete(con, d.getKodeBahan(), penjualan.getKodeGudang(), "Penjualan", penjualan.getNoPenjualan());

                Bahan bahan = BahanDAO.get(con, d.getKodeBahan());
                bahan.setStatus("true");
                BahanDAO.update(con, bahan);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPemesananPembelianBahan(Connection con, PemesananPembelianBahanHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noPemesanan = PemesananPembelianBahanHeadDAO.getId(con, date);
            pemesanan.setNoPemesanan(noPemesanan);
            pemesanan.setTglPemesanan(tglSql.format(date));
            PemesananPembelianBahanHeadDAO.insert(con, pemesanan);
            int noUrut = 1;
            for (PemesananPembelianBahanDetail detail : pemesanan.getListPemesananPembelianBahanDetail()) {
                detail.setNoPemesanan(noPemesanan);
                detail.setNoUrut(noUrut);
                PemesananPembelianBahanDetailDAO.insert(con, detail);

                noUrut = noUrut + 1;
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPemesananPembelianBahan(Connection con, PemesananPembelianBahanHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            pemesanan.setTglBatal(tglSql.format(date));
            pemesanan.setUserBatal(sistem.getUser().getKodeUser());
            pemesanan.setStatus("false");
            PemesananPembelianBahanHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String selesaiPemesananPembelianBahan(Connection con, PemesananPembelianBahanHead pemesanan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PemesananPembelianBahanHeadDAO.update(con, pemesanan);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPembayaranDownPayment(Connection con, PemesananPembelianBahanHead psn, Date tglTransaksi, double jumlahBayar, String tipeKeuangan) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            psn.setDownPayment(psn.getDownPayment() + jumlahBayar);
            psn.setSisaDownPayment(psn.getSisaDownPayment() + jumlahBayar);
            PemesananPembelianBahanHeadDAO.update(con, psn);

            Piutang p = new Piutang();
            p.setNoPiutang(PiutangDAO.getId(con, tglTransaksi));
            p.setTglPiutang(tglSql.format(tglTransaksi));
            p.setKategori("Pembayaran Down Payment");
            p.setKeterangan(psn.getNoPemesanan());
            p.setTipeKeuangan(tipeKeuangan);
            p.setJumlahPiutang(jumlahBayar);
            p.setPembayaran(0);
            p.setSisaPiutang(jumlahBayar);
            p.setJatuhTempo("2000-01-01");
            p.setKodeUser(sistem.getUser().getKodeUser());
            p.setStatus("open");
            PiutangDAO.insert(con, p);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), tipeKeuangan, "Pembayaran Down Payment", p.getNoPiutang(),
                    "Pembayaran Down Payment - " + psn.getSupplier().getNama() + " - " + psn.getNoPemesanan(), -jumlahBayar, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Piutang", "Pembayaran Down Payment", p.getNoPiutang(),
                    "Pembayaran Down Payment - " + psn.getSupplier().getNama() + " - " + psn.getNoPemesanan(), jumlahBayar, sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPembayaranDownPayment(Connection con, Piutang p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            PiutangDAO.delete(con, p);

            PemesananPembelianBahanHead pemesanan = p.getPemesananPembelianBahanHead();
            pemesanan.setDownPayment(pemesanan.getDownPayment() - p.getJumlahPiutang());
            pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() - p.getJumlahPiutang());
            PemesananPembelianBahanHeadDAO.update(con, pemesanan);

            KeuanganDAO.deleteByNoTransaksi(con, p.getTipeKeuangan(), "Pembayaran Down Payment", p.getNoPiutang());
            KeuanganDAO.deleteByNoTransaksi(con, "Hutang", "Pembayaran Down Payment", p.getNoPiutang());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPenerimaanBahan(Connection con, PenerimaanBahan p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            String noPenerimaan = PenerimaanBahanDAO.getId(con, date);
            p.setNoPenerimaan(noPenerimaan);
            p.setTglPenerimaan(tglSql.format(date));
            PenerimaanBahanDAO.insert(con, p);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPenerimaanBahan(Connection con, PenerimaanBahan p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            p.setTglBatal(tglSql.format(date));
            p.setUserBatal(sistem.getUser().getKodeUser());
            p.setStatus("false");
            PenerimaanBahanDAO.update(con, p);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPembelian(Connection con, PembelianBahanHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);
            String noPembelian = PembelianBahanHeadDAO.getId(con, date);

            p.setNoPembelian(noPembelian);
            p.setTglPembelian(tglSql.format(date));

            PembelianBahanHeadDAO.insert(con, p);
            for (BebanPembelian beban : p.getListBebanPembelian()) {
                beban.setNoPembelian(noPembelian);
                BebanPembelianDAO.insert(con, beban);
            }
            Hutang hutang = new Hutang();
            hutang.setNoHutang(HutangDAO.getId(con, date));
            hutang.setTglHutang(tglSql.format(date));
            hutang.setKategori("Hutang Pembelian");
            hutang.setKeterangan(p.getNoPembelian());
            hutang.setTipeKeuangan("Pembelian");
            hutang.setJumlahHutang(p.getGrandtotal());
            hutang.setPembayaran(p.getPembayaran());
            hutang.setSisaHutang(p.getSisaPembayaran());
            hutang.setJatuhTempo("2000-01-01");
            hutang.setKodeUser(sistem.getUser().getKodeUser());
            hutang.setStatus("open");
            HutangDAO.insert(con, hutang);

            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Hutang", "Hutang Pembelian", p.getNoPembelian(),
                    "Pembelian - " + p.getNoPembelian(), p.getSisaPembayaran(), sistem.getUser().getKodeUser()
            );
//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Hutang", "Hutang Pembelian", p.getNoPembelian(),
//                    "Pembelian - " + p.getSupplier().getNama() + " - " + p.getNoPembelian(), p.getSisaPembayaran(), sistem.getUser().getKodeUser()
//            );

            PemesananPembelianBahanHead pemesanan = PemesananPembelianBahanHeadDAO.get(con, p.getNoPemesanan());
            double dp = pemesanan.getSisaDownPayment();
            if (p.getTotalPembelian() >= dp) {
                p.setPembayaran(dp);
            } else if (p.getTotalPembelian() < dp) {
                p.setPembayaran(p.getTotalPembelian());
            }
            p.setSisaPembayaran(p.getTotalPembelian() - p.getPembayaran());

            if (p.getPembayaran() > 0) {
                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() - p.getPembayaran());

                Pembayaran pb = new Pembayaran();
                pb.setNoPembayaran(PembayaranDAO.getId(con, date));
                pb.setTglPembayaran(p.getTglPembelian());
                pb.setNoHutang(hutang.getNoHutang());
                pb.setJumlahPembayaran(p.getPembayaran());
                pb.setTipeKeuangan("Down Payment");
                pb.setCatatan(p.getNoPemesanan());
                pb.setKodeUser(sistem.getUser().getKodeUser());
                pb.setTglBatal("2000-01-01 00:00:00");
                pb.setUserBatal("");
                pb.setStatus("true");
                PembayaranDAO.insert(con, pb);

                double bayar = p.getPembayaran();
                List<Piutang> listPiutang = PiutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                for (Piutang pt : listPiutang) {
                    if (pt.getSisaPiutang() > bayar) {
                        TerimaPembayaran tp = new TerimaPembayaran();
                        tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con, date));
                        tp.setTglTerima(p.getTglPembelian());
                        tp.setNoPiutang(pt.getNoPiutang());
                        tp.setJumlahPembayaran(bayar);
                        tp.setTipeKeuangan("Pembelian");
                        tp.setCatatan(p.getNoPembelian());
                        tp.setKodeUser(sistem.getUser().getKodeUser());
                        tp.setTglBatal("2000-01-01 00:00:00");
                        tp.setUserBatal("");
                        tp.setStatus("true");
                        TerimaPembayaranDAO.insert(con, tp);

                        pt.setPembayaran(pt.getPembayaran() + bayar);
                        pt.setSisaPiutang(pt.getSisaPiutang() - bayar);
                        PiutangDAO.update(con, pt);

                        bayar = 0;
                    } else {
                        TerimaPembayaran tp = new TerimaPembayaran();
                        tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con, date));
                        tp.setTglTerima(p.getTglPembelian());
                        tp.setNoPiutang(pt.getNoPiutang());
                        tp.setJumlahPembayaran(pt.getSisaPiutang());
                        tp.setTipeKeuangan("Pembelian");
                        tp.setCatatan(p.getNoPembelian());
                        tp.setKodeUser(sistem.getUser().getKodeUser());
                        tp.setTglBatal("2000-01-01 00:00:00");
                        tp.setUserBatal("");
                        tp.setStatus("true");
                        TerimaPembayaranDAO.insert(con, tp);

                        pt.setPembayaran(pt.getPembayaran() + pt.getSisaPiutang());
                        pt.setSisaPiutang(pt.getSisaPiutang() - pt.getSisaPiutang());
                        pt.setStatus("close");
                        PiutangDAO.update(con, pt);

                        bayar = bayar - pt.getSisaPiutang();
                    }
                }

                insertKeuangan(con, noKeuangan, p.getTglPembelian(), p.getTglPembelian(), "Piutang", "Pembayaran Down Payment", p.getNoPembelian(),
                        "Pembelian - " + p.getNoPembelian(), -p.getPembayaran(), sistem.getUser().getKodeUser());
//                insertKeuangan(con, noKeuangan, p.getTglPembelian(), "Piutang", "Pembayaran Down Payment", p.getNoPembelian(),
//                        "Pembelian - " + p.getSupplier().getNama() + " - " + p.getNoPembelian(), -p.getPembayaran(), sistem.getUser().getKodeUser());

            }

            for (PembelianBahanDetail detail : p.getListPembelianBahanDetail()) {
                PemesananPembelianBahanDetail d = PemesananPembelianBahanDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
                d.setQtyDiterima(d.getQtyDiterima() + detail.getQty());
                PemesananPembelianBahanDetailDAO.update(con, d);
            }
            double qtyBelumDikirim = 0;
            List<PemesananPembelianBahanDetail> listPemesananPembelianBahanDetail = PemesananPembelianBahanDetailDAO.
                    getAllByNoPemesanan(con, pemesanan.getNoPemesanan());
            for (PemesananPembelianBahanDetail d : listPemesananPembelianBahanDetail) {
                qtyBelumDikirim = qtyBelumDikirim + d.getQty() - d.getQtyDiterima();
            }
            if (qtyBelumDikirim <= 0) {
                pemesanan.setStatus("close");
            }
            PemesananPembelianBahanHeadDAO.update(con, pemesanan);

            double bebanPerItem = pembulatan(p.getTotalBebanPembelian() / p.getListPembelianBahanDetail().size());
            for (PembelianBahanDetail detail : p.getListPembelianBahanDetail()) {
                detail.setNoPembelian(noPembelian);
                PembelianBahanDetailDAO.insert(con, detail);
                Bahan bahan = BahanDAO.get(con, detail.getKodeBahan());
                if (bahan == null) {

                    bahan = new Bahan();
                    bahan.setKodeBahan(detail.getKodeBahan());
                    bahan.setKodeKategori(detail.getKodeKategori());
                    bahan.setNoKontrak(detail.getNoKontrak());
                    bahan.setNamaBahan(detail.getNamaBahan());
                    bahan.setSpesifikasi(detail.getSpesifikasi());
                    bahan.setKeterangan("");
                    bahan.setBeratKotor(detail.getQty());
                    bahan.setBeratBersih(detail.getQty());
                    bahan.setPanjang(0);
                    bahan.setHargaBeli(detail.getTotal() + bebanPerItem);
                    bahan.setStatus("true");
                    BahanDAO.insert(con, bahan);

                    StokBahan stokBahan = new StokBahan();
                    stokBahan.setTanggal(tglSql.format(date));
                    stokBahan.setKodeBahan(detail.getKodeBahan());
                    stokBahan.setKodeGudang(p.getKodeGudang());
                    stokBahan.setStokAwal(0);
                    stokBahan.setStokMasuk(detail.getQty());
                    stokBahan.setStokKeluar(0);
                    stokBahan.setStokAkhir(detail.getQty());
                    StokBahanDAO.insert(con, stokBahan);

                    LogBahan log = new LogBahan();
                    log.setTanggal(tglSql.format(date));
                    log.setKodeBahan(detail.getKodeBahan());
                    log.setKodeGudang(p.getKodeGudang());
                    log.setKategori("Pembelian");
                    log.setKeterangan(p.getNoPembelian());
                    log.setStokAwal(0);
                    log.setNilaiAwal(0);
                    log.setStokMasuk(detail.getQty());
                    log.setNilaiMasuk(detail.getTotal() + bebanPerItem);
                    log.setStokKeluar(0);
                    log.setNilaiKeluar(0);
                    log.setStokAkhir(detail.getQty());
                    log.setNilaiAkhir(detail.getTotal() + bebanPerItem);
                    LogBahanDAO.insert(con, log);
                } else {
                    status = "Kode bahan sudah pernah terdaftar";
                }

                PenerimaanBahan pb = PenerimaanBahanDAO.get(con, detail.getNoPenerimaan());
                pb.setStatus("true");
                PenerimaanBahanDAO.update(con, pb);
            }

            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", p.getKodeGudang(), p.getNoPembelian(),
                    "Pembelian - " + p.getNoPembelian(), p.getGrandtotal(), sistem.getUser().getKodeUser());

//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Bahan", p.getKodeGudang(), p.getNoPembelian(),
//                    "Pembelian - " + p.getSupplier().getNama() + " - " + p.getNoPembelian(), p.getGrandtotal(), sistem.getUser().getKodeUser());
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                e.printStackTrace();
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPembelian(Connection con, PembelianBahanHead pembelian) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            if (pembelian.getPembayaran() > 0) {
                status = "Tidak dapat dibatalkan, karena sudah ada pembayaran";
            } else {
                Date date = Function.getServerDate(con);

                pembelian.setTglBatal(tglSql.format(date));
                pembelian.setUserBatal(sistem.getUser().getKodeUser());
                pembelian.setStatus("false");
                PembelianBahanHeadDAO.update(con, pembelian);

                Hutang hutang = HutangDAO.getByKategoriAndKeteranganAndStatus(
                        con, "Hutang Pembelian", pembelian.getNoPembelian(), "%");
                HutangDAO.delete(con, hutang);

                KeuanganDAO.deleteByDeskripsi(con, "Hutang", "Hutang Pembelian", "Pembelian - " + pembelian.getNoPembelian());
//                KeuanganDAO.delete(con, "Hutang", "Hutang Pembelian", pembelian.getNoPembelian());

                PemesananPembelianBahanHead pemesanan = PemesananPembelianBahanHeadDAO.get(con, pembelian.getNoPemesanan());
                Pembayaran dp = null;
                List<Pembayaran> listPembayaran = PembayaranDAO.getAllByNoHutang(
                        con, hutang.getNoHutang(), "true");
                for (Pembayaran tp : listPembayaran) {
                    if (tp.getTipeKeuangan().equals("Down Payment")) {
                        dp = tp;
                    } else {
                        status = "Tidak dapat dibatal,karena sudah ada pembayaran";
                    }
                }
                if (dp != null) {
                    pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment() + dp.getJumlahPembayaran());

                    dp.setTglBatal(tglSql.format(date));
                    dp.setUserBatal(sistem.getUser().getKodeUser());
                    dp.setStatus("false");
                    PembayaranDAO.update(con, dp);

                    List<Piutang> listPiutang = PiutangDAO.getAllByKategoriAndKeteranganAndStatus(
                            con, "Pembayaran Down Payment", pemesanan.getNoPemesanan(), "%");
                    for (Piutang pt : listPiutang) {
                        List<TerimaPembayaran> listTerima = TerimaPembayaranDAO.getAllByNoPiutangAndStatus(con, pt.getNoPiutang(), "true");
                        for (TerimaPembayaran p : listTerima) {
                            if (p.getTipeKeuangan().equals("Pembelian") && p.getCatatan().equals(pembelian.getNoPembelian())) {
                                p.setTglBatal(tglSql.format(date));
                                p.setUserBatal(sistem.getUser().getKodeUser());
                                p.setStatus("false");
                                TerimaPembayaranDAO.update(con, p);

                                pt.setPembayaran(pt.getPembayaran() - p.getJumlahPembayaran());
                                pt.setSisaPiutang(pt.getSisaPiutang() + p.getJumlahPembayaran());
                                pt.setStatus("open");
                                PiutangDAO.update(con, pt);
                            }
                        }
                    }
                    KeuanganDAO.deleteByDeskripsi(con, "Piutang", "Pembayaran Down Payment", "Pembelian - " + pembelian.getNoPembelian());
//                    KeuanganDAO.delete(con, "Piutang", "Pembayaran Down Payment", pembelian.getNoPembelian());
                }

                pembelian.setListPembelianBahanDetail(PembelianBahanDetailDAO.getAllByNoPembelian(con, pembelian.getNoPembelian()));

                for (PembelianBahanDetail detail : pembelian.getListPembelianBahanDetail()) {
                    PemesananPembelianBahanDetail d = PemesananPembelianBahanDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
                    d.setQtyDiterima(d.getQtyDiterima() - detail.getQty());
                    PemesananPembelianBahanDetailDAO.update(con, d);

                    PenerimaanBahan pb = PenerimaanBahanDAO.get(con, detail.getNoPenerimaan());
                    pb.setStatus("open");
                    PenerimaanBahanDAO.update(con, pb);
                }
                pemesanan.setStatus("open");
                PemesananPembelianBahanHeadDAO.update(con, pemesanan);

                for (PembelianBahanDetail d : pembelian.getListPembelianBahanDetail()) {
                    Bahan b = BahanDAO.get(con, d.getKodeBahan());
                    double stokBeli = b.getBeratBersih();

                    StokBahan stokAkhir = StokBahanDAO.getStokAkhir(con, d.getKodeBahan(), pembelian.getKodeGudang());
                    if (stokAkhir.getStokAkhir() < stokBeli) {
                        status = "Stok bahan " + stokAkhir.getKodeBahan() + " kurang dari jumlah yang dibeli";
                    } else {
                        StokBahanDAO.delete(con, d.getKodeBahan(), pembelian.getKodeGudang());
                        BahanDAO.delete(con, d.getKodeBahan());

                        LogBahanDAO.delete(con, d.getKodeBahan(), pembelian.getKodeGudang(), "Pembelian", pembelian.getNoPembelian());
                    }
                }
                KeuanganDAO.deleteByDeskripsi(con, "Stok Bahan", pembelian.getKodeGudang(), "Pembelian - " + pembelian.getNoPembelian());
//                KeuanganDAO.delete(con, "Stok Bahan", pembelian.getKodeGudang(), pembelian.getNoPembelian());

            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPembelianBarang(Connection con, PembelianBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            String noPembelian = PembelianBarangHeadDAO.getId(con, date);
            p.setNoPembelian(noPembelian);
            p.setTglPembelian(tglSql.format(date));
            PembelianBarangHeadDAO.insert(con, p);

            for (BebanPembelian beban : p.getListBebanPembelian()) {
                beban.setNoPembelian(noPembelian);
                BebanPembelianDAO.insert(con, beban);
            }

            Hutang hutang = new Hutang();
            hutang.setNoHutang(HutangDAO.getId(con, date));
            hutang.setTglHutang(tglSql.format(date));
            hutang.setKategori("Hutang Pembelian");
            hutang.setKeterangan(p.getNoPembelian());
            hutang.setTipeKeuangan("Pembelian");
            hutang.setJumlahHutang(p.getGrandtotal());
            hutang.setPembayaran(p.getPembayaran());
            hutang.setSisaHutang(p.getSisaPembayaran());
            hutang.setJatuhTempo("2000-01-01");
            hutang.setKodeUser(sistem.getUser().getKodeUser());
            hutang.setStatus("open");
            HutangDAO.insert(con, hutang);

            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Hutang", "Hutang Pembelian", p.getNoPembelian(),
                    "Pembelian Barang - " + p.getNoPembelian(), p.getSisaPembayaran(), sistem.getUser().getKodeUser());

//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Hutang", "Hutang Pembelian", p.getNoPembelian(),
//                    "Pembelian Barang - " + p.getSupplier().getNama() + " - " + p.getNoPembelian(), p.getSisaPembayaran(), sistem.getUser().getKodeUser());
            double totalQty = 0;
            for (PembelianBarangDetail detail : p.getListPembelianBarangDetail()) {
                totalQty = totalQty + detail.getQty();
            }
            double bebanPerItem = p.getTotalBebanPembelian() / totalQty;

            double totalNilai = 0;
            List<PembelianBarangDetail> groupByBarang = new ArrayList<>();
            for (PembelianBarangDetail detail : p.getListPembelianBarangDetail()) {
                detail.setNoPembelian(noPembelian);
                detail.setNilai(detail.getHargaBeli() + bebanPerItem);
                PembelianBarangDetailDAO.insert(con, detail);

                boolean x = true;
                for (PembelianBarangDetail d : groupByBarang) {
                    if (detail.getKodeBarang().equals(d.getKodeBarang())) {
                        d.setQty(d.getQty() + detail.getQty());
                        d.setNilai(((d.getNilai() * d.getQty()) + (detail.getNilai() * detail.getQty()))
                                / (d.getQty() + detail.getQty()));
                        x = false;
                    }
                }
                if (x) {
                    groupByBarang.add(detail);
                }

                totalNilai = totalNilai + (detail.getNilai() * detail.getQty());
            }

            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", p.getKodeGudang(), p.getNoPembelian(),
                    "Pembelian Barang - " + p.getNoPembelian(), totalNilai, sistem.getUser().getKodeUser());

//            insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Barang", p.getKodeGudang(), p.getNoPembelian(),
//                    "Pembelian Barang - " + p.getSupplier().getNama() + " - " + p.getNoPembelian(), totalNilai, sistem.getUser().getKodeUser());
            for (PembelianBarangDetail d : groupByBarang) {
                double nilai = d.getNilai() * d.getQty();

                status = insertStokAndLogBarang(con, date, d.getKodeBarang(), p.getKodeGudang(), "Pembelian Barang", p.getNoPembelian(),
                        d.getQty(), nilai, 0, 0, status);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                e.printStackTrace();
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPembelianBarang(Connection con, PembelianBarangHead pembelian) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            if (pembelian.getPembayaran() > 0) {
                status = "Tidak dapat dibatalkan, karena sudah ada pembayaran";
            } else {
                Date date = Function.getServerDate(con);

                pembelian.setTglBatal(tglSql.format(date));
                pembelian.setUserBatal(sistem.getUser().getKodeUser());
                pembelian.setStatus("false");
                PembelianBarangHeadDAO.update(con, pembelian);

                Hutang hutang = HutangDAO.getByKategoriAndKeteranganAndStatus(
                        con, "Hutang Pembelian", pembelian.getNoPembelian(), "%");
                HutangDAO.delete(con, hutang);

                KeuanganDAO.deleteByDeskripsi(con, "Hutang", "Hutang Pembelian", "Pembelian Barang - " + pembelian.getNoPembelian());
//                KeuanganDAO.delete(con, "Hutang", "Hutang Pembelian", pembelian.getNoPembelian());

                pembelian.setListPembelianBarangDetail(PembelianBarangDetailDAO.getAllByNoPembelian(con, pembelian.getNoPembelian()));
                List<PembelianBarangDetail> stokBarang = new ArrayList<>();
                for (PembelianBarangDetail d : pembelian.getListPembelianBarangDetail()) {
                    Boolean inStok = false;
                    for (PembelianBarangDetail detail : stokBarang) {
                        if (d.getKodeBarang().equals(detail.getKodeBarang())) {
                            detail.setQty(detail.getQty() + d.getQty());
                            inStok = true;
                        }
                    }
                    if (!inStok) {
                        stokBarang.add(d);
                    }
                }

                for (PembelianBarangDetail d : stokBarang) {
                    StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(date), d.getKodeBarang(), pembelian.getKodeGudang());
                    if (stokAkhir == null) {
                        status = "Stok barang " + d.getNamaBarang() + " tidak ditemukan";
                    } else {
                        if (stokAkhir.getStokAkhir() < d.getQty()) {
                            status = "Stok barang " + d.getNamaBarang() + " tidak mencukupi";
                        } else {
                            StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(pembelian.getTglPembelian())), d.getKodeBarang(), pembelian.getKodeGudang());
                            stok.setStokMasuk(stok.getStokMasuk() - d.getQty());
                            stok.setStokAkhir(stok.getStokAkhir() - d.getQty());
                            StokBarangDAO.update(con, stok);

                            LogBarangDAO.delete(con, d.getKodeBarang(), pembelian.getKodeGudang(), "Pembelian Barang", pembelian.getNoPembelian());

                            resetStokDanLogBarang(con, d.getKodeBarang(), pembelian.getKodeGudang(), pembelian.getTglPembelian(), date);
                        }
                    }
                }
                KeuanganDAO.deleteByDeskripsi(con, "Stok Barang", pembelian.getKodeGudang(), "Pembelian Barang - " + pembelian.getNoPembelian());
//                KeuanganDAO.delete(con, "Stok Barang", pembelian.getKodeGudang(), pembelian.getNoPembelian());
            }
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPindahBahan(Connection con, PindahBahanHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            String noPindah = PindahBahanHeadDAO.getId(con, date);
            p.setNoPindah(noPindah);
            p.setTglPindah(tglSql.format(date));
            PindahBahanHeadDAO.insert(con, p);

            double totalNilai = 0;
            int i = 1;
            for (PindahBahanDetail d : p.getListPindahBahanDetail()) {
                d.setNoPindah(p.getNoPindah());
                d.setNoUrut(i);

                LogBahan log = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBahan(), p.getGudangAsal());
                if (log.getStokAkhir() != 0) {
                    d.setNilai(log.getNilaiAkhir());
                }
                PindahBahanDetailDAO.insert(con, d);

                i++;
                status = insertStokAndLogBahan(con, date, d.getKodeBahan(), p.getGudangAsal(), "Pindah Bahan", p.getNoPindah(),
                        0, 0, d.getBeratBersih(), d.getNilai(), status);
                status = insertStokAndLogBahan(con, date, d.getKodeBahan(), p.getGudangTujuan(), "Pindah Bahan", p.getNoPindah(),
                        d.getBeratBersih(), d.getNilai(), 0, 0, status);

                totalNilai = totalNilai + d.getNilai();
            }
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", p.getGudangAsal(), p.getNoPindah(),
                    "Pindah Bahan - " + p.getNoPindah(), -totalNilai, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", p.getGudangTujuan(), p.getNoPindah(),
                    "Pindah Bahan - " + p.getNoPindah(), totalNilai, sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPindahBahan(Connection con, PindahBahanHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            p.setListPindahBahanDetail(PindahBahanDetailDAO.getAllPindahBahanDetail(con, p.getNoPindah()));
            p.setTglBatal(tglSql.format(date));
            p.setUserBatal(sistem.getUser().getKodeUser());
            p.setStatus("false");
            PindahBahanHeadDAO.update(con, p);

            for (PindahBahanDetail d : p.getListPindahBahanDetail()) {
                StokBahan stokBatalKeluar = StokBahanDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())),
                        d.getKodeBahan(), p.getGudangAsal());
                stokBatalKeluar.setStokKeluar(stokBatalKeluar.getStokKeluar() - d.getBeratBersih());
                stokBatalKeluar.setStokAkhir(stokBatalKeluar.getStokAkhir() + d.getBeratBersih());
                StokBahanDAO.update(con, stokBatalKeluar);

                StokBahan stokBatalMasuk = StokBahanDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())),
                        d.getKodeBahan(), p.getGudangTujuan());
                stokBatalMasuk.setStokMasuk(stokBatalMasuk.getStokMasuk() - d.getBeratBersih());
                stokBatalMasuk.setStokAkhir(stokBatalMasuk.getStokAkhir() - d.getBeratBersih());
                StokBahanDAO.update(con, stokBatalMasuk);

                LogBahanDAO.delete(con, d.getKodeBahan(), p.getGudangAsal(), "Pindah Bahan", p.getNoPindah());

                LogBahanDAO.delete(con, d.getKodeBahan(), p.getGudangTujuan(), "Pindah Bahan", p.getNoPindah());

                resetStokDanLogBahan(con, d.getKodeBahan(), p.getGudangAsal(), p.getTglPindah(), date);
                resetStokDanLogBahan(con, d.getKodeBahan(), p.getGudangTujuan(), p.getTglPindah(), date);
            }
            KeuanganDAO.deleteByNoTransaksi(con, "Stok Bahan", p.getGudangAsal(), p.getNoPindah());
            KeuanganDAO.deleteByNoTransaksi(con, "Stok Bahan", p.getGudangTujuan(), p.getNoPindah());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPindahBarang(Connection con, PindahBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            String noPengiriman = PindahBarangHeadDAO.getId(con, date);
            p.setNoPindah(noPengiriman);
            p.setTglPindah(tglSql.format(date));
            PindahBarangHeadDAO.insert(con, p);

            int i = 1;
            for (PindahBarangDetail d : p.getListPindahBarangDetail()) {
                d.setNoPindah(p.getNoPindah());
                d.setNoUrut(i);
                PindahBarangDetailDAO.insert(con, d);
                i++;
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String verifikasiPindahBarang(Connection con, PindahBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            p.setTglVerifikasi(tglSql.format(date));
            p.setUserVerifikasi(sistem.getUser().getKodeUser());
            p.setStatus("true");
            PindahBarangHeadDAO.update(con, p);

            p.setListPindahBarangDetail(PindahBarangDetailDAO.getAllPindahBarangDetail(con, p.getNoPindah()));
            double totalNilai = 0;
            List<PindahBarangDetail> stokBarang = new ArrayList<>();
            for (PindahBarangDetail d : p.getListPindahBarangDetail()) {
                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), p.getGudangAsal());
                if (log.getStokAkhir() != 0) {
                    d.setNilai(log.getNilaiAkhir() / log.getStokAkhir());
                }
                PindahBarangDetailDAO.update(con, d);

                Boolean inStok = false;
                for (PindahBarangDetail detail : stokBarang) {
                    if (d.getKodeBarang().equals(detail.getKodeBarang())) {
                        detail.setNilai((detail.getNilai() * detail.getQty() + d.getNilai() * d.getQty())
                                / (detail.getQty() + d.getQty()));
                        detail.setQty(detail.getQty() + d.getQty());
                        inStok = true;
                    }
                }
                if (!inStok) {
                    PindahBarangDetail temp = new PindahBarangDetail();
                    temp.setNoPindah(d.getNoPindah());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    stokBarang.add(temp);
                }

                totalNilai = totalNilai + (d.getNilai() * d.getQty());
            }
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", p.getGudangAsal(), p.getNoPindah(),
                    "Pindah Barang - " + p.getNoPindah(), -totalNilai, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", p.getGudangTujuan(), p.getNoPindah(),
                    "Pindah Barang - " + p.getNoPindah(), totalNilai, sistem.getUser().getKodeUser());

            for (PindahBarangDetail d : stokBarang) {
                status = insertStokAndLogBarang(con, date, d.getKodeBarang(), p.getGudangAsal(), "Pindah Barang", p.getNoPindah(),
                        0, 0, d.getQty(), d.getNilai() * d.getQty(), status);
                status = insertStokAndLogBarang(con, date, d.getKodeBarang(), p.getGudangTujuan(), "Pindah Barang", p.getNoPindah(),
                        d.getQty(), d.getNilai() * d.getQty(), 0, 0, status);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPindahBarang(Connection con, PindahBarangHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            p.setListPindahBarangDetail(PindahBarangDetailDAO.getAllPindahBarangDetail(con, p.getNoPindah()));

            if (p.getStatus().equals("true")) {
                List<PindahBarangDetail> stokBarang = new ArrayList<>();
                for (PindahBarangDetail d : p.getListPindahBarangDetail()) {
                    Boolean inStok = false;
                    for (PindahBarangDetail detail : stokBarang) {
                        if (d.getKodeBarang().equals(detail.getKodeBarang())) {
                            detail.setNilai((detail.getNilai() * detail.getQty() + d.getNilai() * d.getQty())
                                    / (detail.getQty() + d.getQty()));
                            detail.setQty(detail.getQty() + d.getQty());
                            inStok = true;
                        }
                    }
                    if (!inStok) {
                        PindahBarangDetail temp = new PindahBarangDetail();
                        temp.setNoPindah(d.getNoPindah());
                        temp.setNoUrut(d.getNoUrut());
                        temp.setKodeBarang(d.getKodeBarang());
                        temp.setNamaBarang(d.getNamaBarang());
                        temp.setKeterangan(d.getKeterangan());
                        temp.setSatuan(d.getSatuan());
                        temp.setQty(d.getQty());
                        temp.setNilai(d.getNilai());
                        stokBarang.add(temp);
                    }
                }
                KeuanganDAO.deleteByNoTransaksi(con, "Stok Barang", p.getGudangAsal(), p.getNoPindah());
                KeuanganDAO.deleteByNoTransaksi(con, "Stok Barang", p.getGudangTujuan(), p.getNoPindah());

                for (PindahBarangDetail d : stokBarang) {
                    StokBarang stokBatalKeluar = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())),
                            d.getKodeBarang(), p.getGudangAsal());
                    stokBatalKeluar.setStokKeluar(stokBatalKeluar.getStokKeluar() - d.getQty());
                    stokBatalKeluar.setStokAkhir(stokBatalKeluar.getStokAkhir() + d.getQty());
                    StokBarangDAO.update(con, stokBatalKeluar);

                    StokBarang stokBatalMasuk = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())),
                            d.getKodeBarang(), p.getGudangTujuan());
                    stokBatalMasuk.setStokMasuk(stokBatalMasuk.getStokMasuk() - d.getQty());
                    stokBatalMasuk.setStokAkhir(stokBatalMasuk.getStokAkhir() - d.getQty());
                    StokBarangDAO.update(con, stokBatalMasuk);

                    LogBarangDAO.delete(con, d.getKodeBarang(), p.getGudangAsal(), "Pindah Barang", p.getNoPindah());

                    LogBarangDAO.delete(con, d.getKodeBarang(), p.getGudangTujuan(), "Pindah Barang", p.getNoPindah());

                    resetStokDanLogBarang(con, d.getKodeBarang(), p.getGudangAsal(), p.getTglPindah(), date);
                    resetStokDanLogBarang(con, d.getKodeBarang(), p.getGudangTujuan(), p.getTglPindah(), date);
                }
            }

            p.setTglBatal(tglSql.format(date));
            p.setUserBatal(sistem.getUser().getKodeUser());
            p.setStatus("false");
            PindahBarangHeadDAO.update(con, p);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newProduksiBarang(Connection con, ProduksiHead produksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            String noProduksi = ProduksiHeadDAO.getId(con, date);
            produksi.setKodeProduksi(noProduksi);
            produksi.setTglProduksi(tglSql.format(date));
            produksi.setTglMulai(tglSql.format(date));
            produksi.setTglSelesai(tglSql.format(date));

            if (produksi.getJenisProduksi().equals("Bahan - Barang") || produksi.getJenisProduksi().equals("Bahan - Bahan")) {
                for (ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()) {
                    Bahan b = BahanDAO.get(con, d.getKodeBarang());
                    d.setBahan(b);
                    d.setNilai(d.getQty() * b.getHargaBeli() / b.getBeratBersih());

                    if (d.isBahanHabis()) {
                        b.setStatus("false");
                        BahanDAO.update(con, b);
                    }
                    status = insertStokAndLogBahan(con, date, d.getKodeBarang(), produksi.getKodeGudang(),
                            "Produksi", produksi.getKodeProduksi(), 0, 0, d.getQty(), d.getNilai(), status);

                    produksi.setMaterialCost(produksi.getMaterialCost() + d.getNilai());
                    d.setKodeProduksi(noProduksi);
                    ProduksiDetailBahanDAO.insert(con, d);
                }
                if (produksi.getJenisProduksi().equals("Bahan - Barang")) {
                    insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", produksi.getKodeGudang(), produksi.getKodeProduksi(),
                            "Produksi Barang - " + produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());
                }
            } else if (produksi.getJenisProduksi().equals("Barang - Barang")) {
                List<ProduksiDetailBahan> groupByBarang = new ArrayList<>();
                for (ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()) {
                    LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                    d.setNilai(log.getNilaiAkhir() / log.getStokAkhir() * d.getQty());
                    d.setKodeProduksi(noProduksi);
                    ProduksiDetailBahanDAO.insert(con, d);
                    produksi.setMaterialCost(produksi.getMaterialCost() + d.getNilai());

                    boolean x = true;
                    for (ProduksiDetailBahan p : groupByBarang) {
                        if (d.getKodeBarang().equals(p.getKodeBarang())) {
                            p.setQty(p.getQty() + d.getQty());
                            p.setNilai(p.getNilai() + d.getNilai());
                            x = false;
                        }
                    }
                    if (x) {
                        groupByBarang.add(d);
                    }
                }
                for (ProduksiDetailBahan d : groupByBarang) {
                    status = insertStokAndLogBarang(con, date, d.getKodeBarang(), produksi.getKodeGudang(), "Produksi", produksi.getKodeProduksi(),
                            0, 0, d.getQty(), d.getNilai(), status);
                }
//                insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Barang", produksi.getKodeGudang(), produksi.getKodeProduksi(),
//                        "Produksi Barang - " + produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            }
            ProduksiHeadDAO.insert(con, produksi);

            if (produksi.getJenisProduksi().equals("Bahan - Barang") || produksi.getJenisProduksi().equals("Barang - Barang")) {

                double totalProduksi = 0;
                for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                    totalProduksi = totalProduksi + (d.getQty() * d.getBarang().getBerat());
                }

                List<ProduksiDetailBarang> groupByBarang = new ArrayList<>();
                for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                    d.setKodeProduksi(noProduksi);
                    d.setNilai(produksi.getMaterialCost() / totalProduksi * (d.getQty() * d.getBarang().getBerat()));
                    ProduksiDetailBarangDAO.insert(con, d);
                    boolean x = true;
                    for (ProduksiDetailBarang p : groupByBarang) {
                        if (d.getKodeBarang().equals(p.getKodeBarang())) {
                            p.setQty(p.getQty() + d.getQty());
                            p.setNilai(p.getNilai() + d.getNilai());
                            x = false;
                        }
                    }
                    if (x) {
                        groupByBarang.add(d);
                    }
                }
                for (ProduksiDetailBarang d : groupByBarang) {
                    status = insertStokAndLogBarang(con, date, d.getKodeBarang(), produksi.getKodeGudang(), "Produksi", produksi.getKodeProduksi(),
                            d.getQty(), d.getNilai(), 0, 0, status);

                }
                if (produksi.getJenisProduksi().equals("Bahan - Barang")) {
                    insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", produksi.getKodeGudang(), produksi.getKodeProduksi(),
                            "Produksi Barang - " + produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());
                }
            } else if (produksi.getJenisProduksi().equals("Bahan - Bahan")) {

                double totalProduksi = 0;
                for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                    totalProduksi = totalProduksi + d.getQty();
                }

                for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                    double nilai = produksi.getMaterialCost() / totalProduksi * d.getQty();
                    d.getBahan().setNoKontrak(noProduksi);
                    d.getBahan().setHargaBeli(nilai);
                    BahanDAO.insert(con, d.getBahan());

                    d.setKodeProduksi(noProduksi);
                    d.setNilai(nilai);
                    ProduksiDetailBarangDAO.insert(con, d);

                    status = insertStokAndLogBahan(con, date, d.getKodeBarang(), produksi.getKodeGudang(),
                            "Produksi", produksi.getKodeProduksi(), d.getQty(), d.getNilai(), 0, 0, status);
                }
//                insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Bahan", produksi.getKodeGudang(), produksi.getKodeProduksi(),
//                        "Produksi Barang - " + produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            }

            for (ProduksiOperator op : produksi.getListProduksiOperator()) {
                op.setKodeProduksi(noProduksi);
                ProduksiOperatorDAO.insert(con, op);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String verifikasiProduksiBarang(Connection con, ProduksiHead produksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            produksi.setTglProduksi(tglSql.format(date));
            produksi.setKodeUser(sistem.getUser().getKodeUser());
            produksi.setStatus("true");

            for (ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()) {
                Bahan b = BahanDAO.get(con, d.getKodeBarang());
                d.setBahan(b);
                d.setNilai(d.getQty() * b.getHargaBeli() / b.getBeratBersih());

                StokBahan stokBahan = StokBahanDAO.get(con, tglBarang.format(date), b.getKodeBahan(), produksi.getKodeGudang());
                if (stokBahan.getStokAkhir() - d.getQty() < 1) {
                    b.setStatus("false");
                    BahanDAO.update(con, b);
                }
                status = insertStokAndLogBahan(con, date, d.getKodeBarang(), produksi.getKodeGudang(),
                        "Produksi", produksi.getKodeProduksi(), 0, 0, d.getQty(), d.getNilai(), status);

                produksi.setMaterialCost(produksi.getMaterialCost() + d.getNilai());
                ProduksiDetailBahanDAO.update(con, d);
            }
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", produksi.getKodeGudang(), produksi.getKodeProduksi(),
                    "Produksi Barang - " + produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());

            ProduksiHeadDAO.update(con, produksi);

            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", produksi.getKodeGudang(), produksi.getKodeProduksi(),
                    "Produksi Barang - " + produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());

            double totalProduksi = 0;
            for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                totalProduksi = totalProduksi + (d.getQty() * d.getBarang().getBerat());
            }

            List<ProduksiDetailBarang> groupByBarang = new ArrayList<>();
            for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                d.setNilai(produksi.getMaterialCost() / totalProduksi * (d.getQty() * d.getBarang().getBerat()));
                ProduksiDetailBarangDAO.update(con, d);
                boolean x = true;
                for (ProduksiDetailBarang p : groupByBarang) {
                    if (d.getKodeBarang().equals(p.getKodeBarang())) {
                        p.setQty(p.getQty() + d.getQty());
                        p.setNilai(p.getNilai() + d.getNilai());
                        x = false;
                    }
                }
                if (x) {
                    groupByBarang.add(d);
                }
            }
            for (ProduksiDetailBarang d : groupByBarang) {
                status = insertStokAndLogBarang(con, date, d.getKodeBarang(), produksi.getKodeGudang(), "Produksi", produksi.getKodeProduksi(),
                        d.getQty(), d.getNilai(), 0, 0, status);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalProduksiBarangApp(Connection con, ProduksiHead produksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            produksi.setTglBatal(tglSql.format(date));
            produksi.setUserBatal(sistem.getUser().getKodeUser());
            produksi.setStatus("false");

            for (ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()) {
                Bahan b = BahanDAO.get(con, d.getKodeBarang());
                b.setStatus("true");
                BahanDAO.update(con, b);
            }
            ProduksiHeadDAO.update(con, produksi);

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalProduksiBarang(Connection con, ProduksiHead produksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);

            produksi.setTglBatal(tglSql.format(date));
            produksi.setUserBatal(sistem.getUser().getKodeUser());
            produksi.setStatus("false");

            if (produksi.getJenisProduksi().equals("Bahan - Barang") || produksi.getJenisProduksi().equals("Bahan - Bahan")) {
                for (ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()) {
                    Bahan b = BahanDAO.get(con, d.getKodeBarang());
                    b.setStatus("true");
                    BahanDAO.update(con, b);

                    d.setNilai(d.getQty() * b.getHargaBeli() / b.getBeratBersih());

                    status = insertStokAndLogBahan(con, date, d.getKodeBarang(), produksi.getKodeGudang(),
                            "Batal Produksi", produksi.getKodeProduksi(), d.getQty(), d.getNilai(), 0, 0, status);
                }
                if (produksi.getJenisProduksi().equals("Bahan - Barang")) {
                    insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", produksi.getKodeGudang(), produksi.getKodeProduksi(),
                            "Batal Produksi Barang - " + produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());
                }
            } else if (produksi.getJenisProduksi().equals("Barang - Barang")) {

                produksi.setListProduksiDetailBahan(ProduksiDetailBahanDAO.get(con, produksi.getKodeProduksi()));
                List<ProduksiDetailBahan> groupByBarang = new ArrayList<>();
                for (ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()) {
                    boolean x = true;
                    for (ProduksiDetailBahan p : groupByBarang) {
                        if (d.getKodeBarang().equals(p.getKodeBarang())) {
                            p.setQty(p.getQty() + d.getQty());
                            p.setNilai(p.getNilai() + d.getNilai());
                            x = false;
                        }
                    }
                    if (x) {
                        groupByBarang.add(d);
                    }
                }
                for (ProduksiDetailBahan d : groupByBarang) {
                    status = insertStokAndLogBarang(con, date, d.getKodeBarang(), produksi.getKodeGudang(), "Batal Produksi", produksi.getKodeProduksi(),
                            d.getQty(), d.getNilai(), 0, 0, status);
                }
//                insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Barang", produksi.getKodeGudang(),
//                        "Batal Produksi Barang - " + produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());

            }
            ProduksiHeadDAO.update(con, produksi);

            if (produksi.getJenisProduksi().equals("Bahan - Barang") || produksi.getJenisProduksi().equals("Barang - Barang")) {
                produksi.setListProduksiDetailBarang(ProduksiDetailBarangDAO.get(con, produksi.getKodeProduksi()));
                List<ProduksiDetailBarang> groupByBarang = new ArrayList<>();
                for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                    boolean x = true;
                    for (ProduksiDetailBarang p : groupByBarang) {
                        if (d.getKodeBarang().equals(p.getKodeBarang())) {
                            p.setQty(p.getQty() + d.getQty());
                            p.setNilai(p.getNilai() + d.getNilai());
                            x = false;
                        }
                    }
                    if (x) {
                        groupByBarang.add(d);
                    }
                }
                for (ProduksiDetailBarang d : groupByBarang) {
                    status = insertStokAndLogBarang(con, date, d.getKodeBarang(), produksi.getKodeGudang(),
                            "Batal Produksi", produksi.getKodeProduksi(), 0, 0, d.getQty(), d.getNilai(), status);
                }
                if (produksi.getJenisProduksi().equals("Bahan - Barang")) {
                    insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", produksi.getKodeGudang(), produksi.getKodeProduksi(),
                            "Batal Produksi Barang - " + produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());
                }
            } else if (produksi.getJenisProduksi().equals("Bahan - Bahan")) {
                for (ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()) {
                    Bahan b = BahanDAO.get(con, d.getKodeBarang());
                    b.setStatus("false");
                    BahanDAO.update(con, b);

                    status = insertStokAndLogBahan(con, date, d.getKodeBarang(), produksi.getKodeGudang(),
                            "Batal Produksi", produksi.getKodeProduksi(), 0, 0, d.getQty(), d.getNilai(), status);
                }
//                insertKeuangan(con, noKeuangan, tglSql.format(date), "Stok Bahan", produksi.getKodeGudang(),
//                        "Batal Produksi Barang - " + produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());

            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newBebanPenjualan(Connection con, BebanPenjualanHead b, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            b.setNoBebanPenjualan(BebanPenjualanHeadDAO.getId(con, tglTransaksi));
            b.setTglBebanPenjualan(tglSql.format(tglTransaksi));
            BebanPenjualanHeadDAO.insert(con, b);

            double totalPenjualan = 0;
            for (BebanPenjualanDetail d : b.getListBebanPenjualanDetail()) {
                totalPenjualan = totalPenjualan + d.getPenjualanHead().getTotalPenjualan();
            }
            String keterangan = "";
            for (BebanPenjualanDetail d : b.getListBebanPenjualanDetail()) {
                double beban = d.getPenjualanHead().getTotalPenjualan() / totalPenjualan * b.getTotalBebanPenjualan();
                d.getPenjualanHead().setTotalBebanPenjualan(d.getPenjualanHead().getTotalBebanPenjualan() + beban);
                PenjualanBarangHeadDAO.update(con, d.getPenjualanHead());

                Customer customer = CustomerDAO.get(con, d.getPenjualanHead().getKodeCustomer());
                keterangan = keterangan + customer.getNama() + " - " + d.getPenjualanHead().getNoPenjualan() + "\n";

                d.setNoBebanPenjualan(b.getNoBebanPenjualan());
                d.setJumlahRp(beban);
                d.setStatus("true");
                BebanPenjualanDetailDAO.insert(con, d);
            }
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Pendapatan/Beban", "Beban Penjualan Langsung",
                    b.getNoBebanPenjualan(), "Beban Penjualan - " + b.getKeterangan() + "\n" + keterangan, -b.getTotalBebanPenjualan(), sistem.getUser().getKodeUser());

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), b.getTipeKeuangan(), "Beban Penjualan Langsung",
                    b.getNoBebanPenjualan(), "Beban Penjualan - " + b.getKeterangan() + "\n" + keterangan, -b.getTotalBebanPenjualan(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalBebanPenjualan(Connection con, BebanPenjualanHead b) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            BebanPenjualanHeadDAO.update(con, b);
            for (BebanPenjualanDetail d : b.getListBebanPenjualanDetail()) {
                PenjualanBarangHead p = PenjualanBarangHeadDAO.get(con, d.getNoPenjualan());
                p.setTotalBebanPenjualan(p.getTotalBebanPenjualan() - d.getJumlahRp());
                PenjualanBarangHeadDAO.update(con, p);

                d.setStatus("false");
                BebanPenjualanDetailDAO.update(con, d);
            }
            KeuanganDAO.deleteByNoTransaksi(con, "Pendapatan/Beban", "Beban Penjualan Langsung", b.getNoBebanPenjualan());
            KeuanganDAO.deleteByNoTransaksi(con, b.getTipeKeuangan(), "Beban Penjualan Langsung", b.getNoBebanPenjualan());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newBebanProduksi(Connection con, BebanProduksiHead b, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            b.setNoBebanProduksi(BebanProduksiHeadDAO.getId(con, tglTransaksi));
            b.setTglBebanProduksi(tglSql.format(tglTransaksi));
            BebanProduksiHeadDAO.insert(con, b);

            double totalProduksi = 0;
            for (BebanProduksiDetail d : b.getListBebanProduksiDetail()) {
                totalProduksi = totalProduksi + d.getProduksiHead().getMaterialCost();
            }
            for (BebanProduksiDetail d : b.getListBebanProduksiDetail()) {
                double beban = d.getProduksiHead().getMaterialCost() / totalProduksi * b.getTotalBebanProduksi();
                d.getProduksiHead().setBiayaProduksi(d.getProduksiHead().getBiayaProduksi() + beban);
                ProduksiHeadDAO.update(con, d.getProduksiHead());

                d.setNoBebanProduksi(b.getNoBebanProduksi());
                d.setJumlahRp(beban);
                d.setStatus("true");
                BebanProduksiDetailDAO.insert(con, d);
            }

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Pendapatan/Beban", "Beban Produksi Langsung",
                    b.getNoBebanProduksi(), "Beban Produksi Langsung - " + b.getKeterangan(), -b.getTotalBebanProduksi(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), b.getTipeKeuangan(), "Beban Produksi Langsung",
                    b.getNoBebanProduksi(), "Beban Produksi Langsung - " + b.getKeterangan(), -b.getTotalBebanProduksi(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalBebanProduksi(Connection con, BebanProduksiHead b) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            BebanProduksiHeadDAO.update(con, b);
            for (BebanProduksiDetail d : b.getListBebanProduksiDetail()) {
                ProduksiHead p = ProduksiHeadDAO.get(con, d.getNoProduksi());
                p.setBiayaProduksi(p.getBiayaProduksi() - d.getJumlahRp());
                ProduksiHeadDAO.update(con, p);

                d.setStatus("false");
                BebanProduksiDetailDAO.update(con, d);
            }
            KeuanganDAO.deleteByNoTransaksi(con, "Pendapatan/Beban", "Beban Produksi Langsung", b.getNoBebanProduksi());
            KeuanganDAO.deleteByNoTransaksi(con, b.getTipeKeuangan(), "Beban Produksi Langsung", b.getNoBebanProduksi());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newKeuangan(Connection con, Keuangan keu, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), keu.getTipeKeuangan(), keu.getKategori(), noKeuangan,
                    keu.getDeskripsi(), keu.getJumlahRp(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Pendapatan/Beban", keu.getKategori(), noKeuangan,
                    keu.getDeskripsi(), keu.getJumlahRp(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalTransaksi(Connection con, Keuangan k) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, date);
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), k.getTipeKeuangan(), k.getKategori(), noKeuangan,
                    "Batal Transaksi - " + k.getDeskripsi(), k.getJumlahRp() * -1, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Pendapatan/Beban", k.getKategori(), noKeuangan,
                    "Batal Transaksi - " + k.getDeskripsi(), k.getJumlahRp() * -1, sistem.getUser().getKodeUser());
//            KeuanganDAO.delete(con, "Pendapatan/Beban", k.getKategori(), k.getNoKeuangan());
//            KeuanganDAO.delete(con, k.getTipeKeuangan(), k.getKategori(), k.getNoKeuangan());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String transferKeuangan(Connection con, Date tglTransaksi, String dari, String ke, String keterangan, double jumlahRp) throws Exception {
        try {
            String status = "true";
            con.setAutoCommit(false);

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), dari, "Transfer Keuangan", noKeuangan,
                    keterangan, -jumlahRp, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), ke, "Transfer Keuangan", noKeuangan,
                    keterangan, jumlahRp, sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newHutang(Connection con, Hutang hutang, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            hutang.setNoHutang(HutangDAO.getId(con, tglTransaksi));
            hutang.setTglHutang(tglSql.format(tglTransaksi));
            HutangDAO.insert(con, hutang);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), hutang.getTipeKeuangan(), hutang.getKategori(), hutang.getNoHutang(),
                    hutang.getNoHutang() + " - " + hutang.getKeterangan(), hutang.getJumlahHutang(), sistem.getUser().getKodeUser());

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Hutang", hutang.getKategori(), hutang.getNoHutang(),
                    hutang.getNoHutang() + " - " + hutang.getKeterangan(), hutang.getJumlahHutang(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPembayaranHutang(Connection con, Pembayaran pembayaran, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            pembayaran.setNoPembayaran(PembayaranDAO.getId(con, tglTransaksi));
            pembayaran.setTglPembayaran(tglSql.format(tglTransaksi));
            PembayaranDAO.insert(con, pembayaran);

            Hutang hutang = pembayaran.getHutang();
            hutang.setPembayaran(hutang.getPembayaran() + pembayaran.getJumlahPembayaran());
            hutang.setSisaHutang(hutang.getSisaHutang() - pembayaran.getJumlahPembayaran());
            if (hutang.getSisaHutang() == 0) {
                hutang.setStatus("close");
            }
            HutangDAO.update(con, hutang);

            if (pembayaran.getHutang().getKategori().equals("Hutang Pembelian")) {
                String supplier = "";
                if (hutang.getKeterangan().startsWith("PO")) {
                    PembelianBahanHead pembelian = PembelianBahanHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran() + pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran() - pembayaran.getJumlahPembayaran());
                    PembelianBahanHeadDAO.update(con, pembelian);

                    supplier = SupplierDAO.get(con, pembelian.getKodeSupplier()).getNama();
                } else if (hutang.getKeterangan().startsWith("PB")) {
                    PembelianBarangHead pembelian = PembelianBarangHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran() + pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran() - pembayaran.getJumlahPembayaran());
                    PembelianBarangHeadDAO.update(con, pembelian);

                    supplier = SupplierDAO.get(con, pembelian.getKodeSupplier()).getNama();
                }
                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), pembayaran.getTipeKeuangan(), hutang.getKategori(), pembayaran.getNoPembayaran(),
                        "Pembayaran - " + supplier + " - " + hutang.getKeterangan(),
                        -pembayaran.getJumlahPembayaran(), sistem.getUser().getKodeUser());

                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Hutang", hutang.getKategori(), pembayaran.getNoPembayaran(),
                        "Pembayaran - " + supplier + " - " + hutang.getKeterangan(),
                        -pembayaran.getJumlahPembayaran(), sistem.getUser().getKodeUser());
            } else {
                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), pembayaran.getTipeKeuangan(), hutang.getKategori(), pembayaran.getNoPembayaran(),
                        "Pembayaran - " + hutang.getKeterangan(),
                        -pembayaran.getJumlahPembayaran(), sistem.getUser().getKodeUser());

                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Hutang", hutang.getKategori(), pembayaran.getNoPembayaran(),
                        "Pembayaran - " + hutang.getKeterangan(),
                        -pembayaran.getJumlahPembayaran(), sistem.getUser().getKodeUser());

            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPembayaranHutang(Connection con, Pembayaran pembayaran) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            pembayaran.setTglBatal(tglSql.format(date));
            pembayaran.setUserBatal(sistem.getUser().getKodeUser());
            pembayaran.setStatus("false");
            PembayaranDAO.update(con, pembayaran);

            Hutang hutang = HutangDAO.get(con, pembayaran.getNoHutang());
            hutang.setPembayaran(hutang.getPembayaran() - pembayaran.getJumlahPembayaran());
            hutang.setSisaHutang(hutang.getSisaHutang() + pembayaran.getJumlahPembayaran());
            if (hutang.getSisaHutang() != 0) {
                hutang.setStatus("open");
            }
            HutangDAO.update(con, hutang);

            if (hutang.getKategori().equals("Hutang Pembelian")) {
                if (hutang.getKeterangan().startsWith("PO")) {
                    PembelianBahanHead pembelian = PembelianBahanHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran() - pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran() + pembayaran.getJumlahPembayaran());
                    PembelianBahanHeadDAO.update(con, pembelian);
                } else if (hutang.getKeterangan().startsWith("PB")) {
                    PembelianBarangHead pembelian = PembelianBarangHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran() - pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran() + pembayaran.getJumlahPembayaran());
                    PembelianBarangHeadDAO.update(con, pembelian);
                }
            }

            KeuanganDAO.deleteByNoTransaksi(con, pembayaran.getTipeKeuangan(), hutang.getKategori(), pembayaran.getNoPembayaran());

            KeuanganDAO.deleteByNoTransaksi(con, "Hutang", hutang.getKategori(), pembayaran.getNoPembayaran());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newPiutang(Connection con, Piutang piutang, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            piutang.setNoPiutang(PiutangDAO.getId(con, tglTransaksi));
            piutang.setTglPiutang(tglSql.format(tglTransaksi));
            PiutangDAO.insert(con, piutang);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), piutang.getTipeKeuangan(), piutang.getKategori(),
                    piutang.getNoPiutang(), piutang.getNoPiutang() + " - " + piutang.getKeterangan(), -piutang.getJumlahPiutang(), sistem.getUser().getKodeUser());

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Piutang", piutang.getKategori(),
                    piutang.getNoPiutang(), piutang.getNoPiutang() + " - " + piutang.getKeterangan(), piutang.getJumlahPiutang(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newTerimaPembayaranPiutang(Connection con, TerimaPembayaran t, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            t.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con, tglTransaksi));
            t.setTglTerima(tglSql.format(tglTransaksi));
            TerimaPembayaranDAO.insert(con, t);

            Piutang piutang = t.getPiutang();
            piutang.setPembayaran(piutang.getPembayaran() + t.getJumlahPembayaran());
            piutang.setSisaPiutang(piutang.getSisaPiutang() - t.getJumlahPembayaran());
            if (piutang.getSisaPiutang() == 0) {
                piutang.setStatus("close");
            }
            PiutangDAO.update(con, piutang);

            if (piutang.getKategori().equals("Piutang Penjualan")) {
                String kodeCustomer = "";
                if (piutang.getKeterangan().startsWith("PJ")) {
                    PenjualanBarangHead penjualan = PenjualanBarangHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran() + t.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran() - t.getJumlahPembayaran());
                    PenjualanBarangHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                } else if (piutang.getKeterangan().startsWith("PE")) {
                    PenjualanBahanHead penjualan = PenjualanBahanHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran() + t.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran() - t.getJumlahPembayaran());
                    PenjualanBahanHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                }
                Customer customer = CustomerDAO.get(con, kodeCustomer);
                customer.setHutang(customer.getHutang() - t.getJumlahPembayaran());
                CustomerDAO.update(con, customer);

                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), t.getTipeKeuangan(), piutang.getKategori(),
                        t.getNoTerimaPembayaran(), "Terima Pembayaran - " + customer.getNama() + " - " + piutang.getKeterangan(),
                        t.getJumlahPembayaran(), sistem.getUser().getKodeUser());

                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Piutang", piutang.getKategori(),
                        t.getNoTerimaPembayaran(), "Terima Pembayaran - " + customer.getNama() + " - " + piutang.getKeterangan(),
                        -t.getJumlahPembayaran(), sistem.getUser().getKodeUser());
            } else {
                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), t.getTipeKeuangan(), piutang.getKategori(),
                        t.getNoTerimaPembayaran(), "Terima Pembayaran - " + piutang.getKeterangan(), t.getJumlahPembayaran(), sistem.getUser().getKodeUser());

                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Piutang", piutang.getKategori(),
                        t.getNoTerimaPembayaran(), "Terima Pembayaran - " + piutang.getKeterangan(), -t.getJumlahPembayaran(), sistem.getUser().getKodeUser());
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalTerimaPembayaranPiutang(Connection con, TerimaPembayaran t) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);

            t.setTglBatal(tglSql.format(date));
            t.setUserBatal(sistem.getUser().getKodeUser());
            t.setStatus("false");
            TerimaPembayaranDAO.update(con, t);

            Piutang piutang = PiutangDAO.get(con, t.getNoPiutang());
            piutang.setPembayaran(piutang.getPembayaran() - t.getJumlahPembayaran());
            piutang.setSisaPiutang(piutang.getSisaPiutang() + t.getJumlahPembayaran());
            if (piutang.getSisaPiutang() != 0) {
                piutang.setStatus("open");
            }
            PiutangDAO.update(con, piutang);

            if (piutang.getKategori().equals("Piutang Penjualan")) {
                String kodeCustomer = "";
                if (piutang.getKeterangan().startsWith("PJ")) {
                    PenjualanBarangHead penjualan = PenjualanBarangHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran() - t.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran() + t.getJumlahPembayaran());
                    PenjualanBarangHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                } else if (piutang.getKeterangan().startsWith("PE")) {
                    PenjualanBahanHead penjualan = PenjualanBahanHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran() - t.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran() + t.getJumlahPembayaran());
                    PenjualanBahanHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                }

                Customer customer = CustomerDAO.get(con, kodeCustomer);
                customer.setHutang(customer.getHutang() + t.getJumlahPembayaran());
                CustomerDAO.update(con, customer);

            }
            KeuanganDAO.deleteByNoTransaksi(con, t.getTipeKeuangan(), piutang.getKategori(),
                    t.getNoTerimaPembayaran());

            KeuanganDAO.deleteByNoTransaksi(con, "Piutang", piutang.getKategori(),
                    t.getNoTerimaPembayaran());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String newModal(Connection con, Keuangan k, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), k.getTipeKeuangan(), k.getKategori(), k.getNoKeuangan(),
                    k.getDeskripsi(), k.getJumlahRp(), k.getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Modal", k.getKategori(), k.getNoKeuangan(),
                    k.getDeskripsi(), k.getJumlahRp(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String pembelianAsetTetap(Connection con, AsetTetap aset, String tipeKeuangan, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);
            aset.setNoAset(AsetTetapDAO.getId(con, tglTransaksi));
            aset.setTglBeli(tglSql.format(tglTransaksi));
            AsetTetapDAO.insert(con, aset);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), tipeKeuangan, "Pembelian Aset Tetap", aset.getNoAset(),
                    "Pembelian Aset Tetap - " + aset.getNama(), -aset.getNilaiAwal(), sistem.getUser().getKodeUser());

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Aset Tetap", aset.getKategori(), aset.getNoAset(),
                    "Pembelian Aset Tetap - " + aset.getNama(), aset.getNilaiAwal(), sistem.getUser().getKodeUser());

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String penjualanAsetTetap(Connection con, AsetTetap aset, String tipeKeuangan, Date tglTransaksi) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Date date = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con, tglTransaksi);
            aset.setTglJual(tglSql.format(tglTransaksi));
            AsetTetapDAO.update(con, aset);

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), tipeKeuangan, "Penjualan Aset Tetap", aset.getNoAset(),
                    "Penjualan Aset Tetap - " + aset.getNama(), aset.getHargaJual(), sistem.getUser().getKodeUser());

            insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Aset Tetap", aset.getKategori(), aset.getNoAset(),
                    "Penjualan Aset Tetap - " + aset.getNama(), -aset.getNilaiAkhir(), sistem.getUser().getKodeUser());

            if (aset.getHargaJual() > aset.getNilaiAkhir()) {
                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Pendapatan/Beban", "Pendapatan Penjualan Aset Tetap", aset.getNoAset(),
                        "Penjualan Aset Tetap - " + aset.getNama(), aset.getHargaJual() - aset.getNilaiAkhir(), sistem.getUser().getKodeUser());

            } else if (aset.getHargaJual() < aset.getNilaiAkhir()) {
                insertKeuangan(con, noKeuangan, tglSql.format(tglTransaksi), tglSql.format(date), "Pendapatan/Beban", "Beban Penjualan Aset Tetap", aset.getNoAset(),
                        "Penjualan Aset Tetap - " + aset.getNama(), aset.getNilaiAkhir() - aset.getHargaJual(), sistem.getUser().getKodeUser());

            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);

            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String savePenyesuaianStokBahan(Connection con, PenyesuaianStokBahan p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            LogBahan logBahan = LogBahanDAO.getLastByBahanAndGudang(con, p.getKodeBahan(), p.getKodeGudang());
            logBahan.setStokAkhir(pembulatan(logBahan.getStokAkhir()));
            if (logBahan.getStokAkhir() + p.getQty() < 0) {
                status = "Stok bahan " + p.getKodeBahan() + " tidak mencukupi";
            } else {
                Date date = Function.getServerDate(con);
                String noKeuangan = KeuanganDAO.getId(con, date);

                p.setNoPenyesuaian(PenyesuaianStokBahanDAO.getId(con, date));
                p.setTglPenyesuaian(tglSql.format(date));

                double qty = p.getQty();
                double nilai = 0;
                if (logBahan.getStokAkhir() != 0) {
                    nilai = logBahan.getNilaiAkhir() / logBahan.getStokAkhir() * p.getQty();
                }

                if (logBahan.getStokAkhir() + p.getQty() <= 0) {
                    Bahan bahan = BahanDAO.get(con, logBahan.getKodeBahan());
                    bahan.setStatus("false");
                    BahanDAO.update(con, bahan);
                }

                if (qty < 0) {
                    status = insertStokAndLogBahan(con, date, p.getKodeBahan(), p.getKodeGudang(),
                            "Penyesuaian Stok Bahan", p.getNoPenyesuaian(), 0, 0, qty * -1, nilai * -1, status);
                } else {
                    status = insertStokAndLogBahan(con, date, p.getKodeBahan(), p.getKodeGudang(),
                            "Penyesuaian Stok Bahan", p.getNoPenyesuaian(), qty, nilai, 0, 0, status);
                }

                insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Bahan", p.getKodeGudang(), p.getNoPenyesuaian(),
                        "Penyesuaian Stok Bahan - " + p.getKodeGudang() + " - " + p.getKodeBahan(), nilai, sistem.getUser().getKodeUser());

                insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Pendapatan/Beban", "Penyesuaian Stok Bahan", p.getNoPenyesuaian(),
                        "Penyesuaian Stok Bahan - " + p.getKodeGudang() + " - " + p.getKodeBahan(), nilai, sistem.getUser().getKodeUser());

                p.setNilai(nilai);
                PenyesuaianStokBahanDAO.insert(con, p);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String savePenyesuaianStokBarang(Connection con, PenyesuaianStokBarang p) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            LogBarang logBarang = LogBarangDAO.getLastByBarangAndGudang(con, p.getKodeBarang(), p.getKodeGudang());
            logBarang.setStokAkhir(pembulatan(logBarang.getStokAkhir()));
            if (logBarang.getStokAkhir() + p.getQty() < 0) {
                status = "Stok barang " + p.getKodeBarang() + " tidak mencukupi";
            } else {
                Date date = Function.getServerDate(con);
                String noKeuangan = KeuanganDAO.getId(con, date);

                p.setNoPenyesuaian(PenyesuaianStokBarangDAO.getId(con, date));
                p.setTglPenyesuaian(tglSql.format(date));

                double qty = p.getQty();
                double nilai = p.getNilai();
//                if (logBarang.getStokAkhir() != 0) {
//                    nilai = logBarang.getNilaiAkhir() / logBarang.getStokAkhir() * p.getQty();
//                }

                if (qty < 0) {
                    status = insertStokAndLogBarang(con, date, p.getKodeBarang(), p.getKodeGudang(),
                            "Penyesuaian Stok Barang", p.getNoPenyesuaian(), 0, 0, qty * -1, nilai * -1, status);
                } else {
                    status = insertStokAndLogBarang(con, date, p.getKodeBarang(), p.getKodeGudang(),
                            "Penyesuaian Stok Barang", p.getNoPenyesuaian(), qty, nilai, 0, 0, status);
                }

                insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Stok Barang", p.getKodeGudang(), p.getNoPenyesuaian(),
                        "Penyesuaian Stok Barang - " + p.getKodeGudang() + " - " + p.getKodeBarang(), nilai, sistem.getUser().getKodeUser());

                insertKeuangan(con, noKeuangan, tglSql.format(date), tglSql.format(date), "Pendapatan/Beban", "Penyesuaian Stok Barang", p.getNoPenyesuaian(),
                        "Penyesuaian Stok Barang - " + p.getKodeGudang() + " - " + p.getKodeBarang(), nilai, sistem.getUser().getKodeUser());

                p.setNilai(nilai);
                PenyesuaianStokBarangDAO.insert(con, p);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static void resetStokDanLogBarang(Connection con, String kodeBarang, String kodeGudang, String tglTransaksi, Date now) throws Exception {
        System.out.println(kodeBarang);
        System.out.println(kodeGudang);
        List<StokBarang> listStokBarang = StokBarangDAO.getAllByTanggalAndBarangAndGudang(
                con, tglBarang.format(tglSql.parse(tglTransaksi)), tglBarang.format(now),
                kodeBarang, kodeGudang);
        listStokBarang.sort(Comparator.comparing(StokBarang::getTanggal));
        double stokBarang = listStokBarang.get(0).getStokAwal();
        for (StokBarang s : listStokBarang) {
            s.setStokAwal(stokBarang);
            stokBarang = stokBarang + s.getStokMasuk() - s.getStokKeluar();
            s.setStokAkhir(stokBarang);
            StokBarangDAO.update(con, s);
        }
        List<LogBarang> listBarang = LogBarangDAO.getAllByTanggalAndBarangAndGudang(
                con, tglBarang.format(tglSql.parse(tglTransaksi)), tglBarang.format(now), kodeBarang, kodeGudang);
        LogBarang logBarang = LogBarangDAO.getLastBeforeDateAndBarangAndGudang(
                con, tglBarang.format(tglSql.parse(tglTransaksi)), kodeBarang, kodeGudang);
        listBarang.sort(Comparator.comparing(LogBarang::getTanggal));
        double stok = logBarang.getStokAkhir();
        double nilai = logBarang.getNilaiAkhir();
        for (LogBarang log : listBarang) {
            log.setStokAwal(stok);
            log.setNilaiAwal(nilai);

            stok = stok + log.getStokMasuk() - log.getStokKeluar();
            nilai = nilai + log.getNilaiMasuk() - log.getNilaiKeluar();

            log.setStokAkhir(stok);
            log.setNilaiAkhir(nilai);

            LogBarangDAO.update(con, log);
        }
    }

    public static void resetStokDanLogBahan(Connection con, String kodeBahan, String kodeGudang, String tglTransaksi, Date now) throws Exception {
        List<StokBahan> listStokBahan = StokBahanDAO.getAllByTanggalAndBahanAndGudang(con,
                tglBarang.format(tglSql.parse(tglTransaksi)), tglBarang.format(now), kodeBahan, kodeGudang);
        listStokBahan.sort(Comparator.comparing(StokBahan::getTanggal));
        double stokBahan = listStokBahan.get(0).getStokAwal();
        for (StokBahan s : listStokBahan) {
            s.setStokAwal(stokBahan);
            stokBahan = stokBahan + s.getStokMasuk() - s.getStokKeluar();
            s.setStokAkhir(stokBahan);
            StokBahanDAO.update(con, s);
        }
        List<LogBahan> listBarang = LogBahanDAO.getAllByTanggalAndBahanAndGudang(
                con, tglBarang.format(tglSql.parse(tglTransaksi)), tglBarang.format(now), kodeBahan, kodeGudang);
        LogBahan logBahan = LogBahanDAO.getLastBeforeDateAndBahanAndGudang(
                con, tglBarang.format(tglSql.parse(tglTransaksi)), kodeBahan, kodeGudang);
        listBarang.sort(Comparator.comparing(LogBahan::getTanggal));
        double stok = 0;
        double nilai = 0;
        if (logBahan != null) {
            stok = logBahan.getStokAkhir();
            nilai = logBahan.getNilaiAkhir();
        }
        for (LogBahan log : listBarang) {
            log.setStokAwal(stok);
            log.setNilaiAwal(nilai);

            stok = stok + log.getStokMasuk() - log.getStokKeluar();
            nilai = nilai + log.getNilaiMasuk() - log.getNilaiKeluar();

            log.setStokAkhir(stok);
            log.setNilaiAkhir(nilai);

            LogBahanDAO.update(con, log);
        }
    }

    public static String insertStokAndLogBahan(Connection con, Date date, String kodeBahan, String kodeGudang, String kategori, String keterangan,
            double qtyIn, double nilaiIn, double qtyOut, double nilaiOut, String status) throws Exception {
        if (qtyIn != 0 || qtyOut != 0) {
            StokBahan stokBahan = StokBahanDAO.get(con, tglBarang.format(date), kodeBahan, kodeGudang);
            if (stokBahan == null) {
                StokBahan stok = new StokBahan();
                stok.setTanggal(tglBarang.format(date));
                stok.setKodeBahan(kodeBahan);
                stok.setKodeGudang(kodeGudang);
                stok.setStokAwal(0);
                stok.setStokMasuk(qtyIn);
                stok.setStokKeluar(qtyOut);
                stok.setStokAkhir(qtyIn - qtyOut);
                StokBahanDAO.insert(con, stok);

                LogBahan logBahan = new LogBahan();
                logBahan.setTanggal(tglSql.format(date));
                logBahan.setKodeBahan(kodeBahan);
                logBahan.setKodeGudang(kodeGudang);
                logBahan.setKategori(kategori);
                logBahan.setKeterangan(keterangan);
                logBahan.setStokAwal(0);
                logBahan.setNilaiAwal(0);
                logBahan.setStokMasuk(qtyIn);
                logBahan.setNilaiMasuk(nilaiIn);
                logBahan.setStokKeluar(qtyOut);
                logBahan.setNilaiKeluar(nilaiOut);
                logBahan.setStokAkhir(qtyIn - qtyOut);
                logBahan.setNilaiAkhir(nilaiIn - nilaiOut);
                LogBahanDAO.insert(con, logBahan);
            } else if (qtyOut > 0 && pembulatan(stokBahan.getStokAkhir()) < qtyOut) {
                status = "Stok bahan " + kodeBahan + " tidak mencukupi";
            } else {
                if (stokBahan.getTanggal().equals(tglBarang.format(date))) {
                    stokBahan.setStokMasuk(stokBahan.getStokMasuk() + qtyIn);
                    stokBahan.setStokKeluar(stokBahan.getStokKeluar() + qtyOut);
                    stokBahan.setStokAkhir(stokBahan.getStokAkhir() + qtyIn - qtyOut);
                    StokBahanDAO.update(con, stokBahan);
                } else {
                    StokBahan stok = new StokBahan();
                    stok.setTanggal(tglBarang.format(date));
                    stok.setKodeBahan(stokBahan.getKodeBahan());
                    stok.setKodeGudang(stokBahan.getKodeGudang());
                    stok.setStokAwal(stokBahan.getStokAkhir());
                    stok.setStokMasuk(qtyIn);
                    stok.setStokKeluar(qtyOut);
                    stok.setStokAkhir(stokBahan.getStokAkhir() + qtyIn - qtyOut);
                    StokBahanDAO.insert(con, stok);
                }
                LogBahan logUmum = LogBahanDAO.getLastByBahanAndGudang(con, kodeBahan, kodeGudang);
                LogBahan logBahan = new LogBahan();
                logBahan.setTanggal(tglSql.format(date));
                logBahan.setKodeBahan(logUmum.getKodeBahan());
                logBahan.setKodeGudang(logUmum.getKodeGudang());
                logBahan.setKategori(kategori);
                logBahan.setKeterangan(keterangan);
                logBahan.setStokAwal(logUmum.getStokAkhir());
                logBahan.setNilaiAwal(logUmum.getNilaiAkhir());
                logBahan.setStokMasuk(qtyIn);
                logBahan.setNilaiMasuk(nilaiIn);
                logBahan.setStokKeluar(qtyOut);
                logBahan.setNilaiKeluar(nilaiOut);
                logBahan.setStokAkhir(logUmum.getStokAkhir() + qtyIn - qtyOut);
                logBahan.setNilaiAkhir(logUmum.getNilaiAkhir() + nilaiIn - nilaiOut);
                LogBahanDAO.insert(con, logBahan);
            }
        }

        return status;
    }

    public static String insertStokAndLogBarang(Connection con, Date date, String kodeBarang, String kodeGudang, String kategori, String keterangan,
            double qtyIn, double nilaiIn, double qtyOut, double nilaiOut, String status) throws Exception {
        if (qtyIn != 0 || qtyOut != 0) {
            StokBarang stokBarang = StokBarangDAO.get(con, tglBarang.format(date), kodeBarang, kodeGudang);
            if (stokBarang == null) {
                status = "Stok barang " + kodeBarang + " tidak ditemukan";
            } else if (qtyOut > 0 && stokBarang.getStokAkhir() < qtyOut) {
                status = "Stok barang " + kodeBarang + " tidak mencukupi";
            } else {
                if (stokBarang.getTanggal().equals(tglBarang.format(date))) {
                    stokBarang.setStokMasuk(stokBarang.getStokMasuk() + qtyIn);
                    stokBarang.setStokKeluar(stokBarang.getStokKeluar() + qtyOut);
                    stokBarang.setStokAkhir(stokBarang.getStokAkhir() + qtyIn - qtyOut);
                    StokBarangDAO.update(con, stokBarang);
                } else {
                    StokBarang stok = new StokBarang();
                    stok.setTanggal(tglBarang.format(date));
                    stok.setKodeBarang(stokBarang.getKodeBarang());
                    stok.setKodeGudang(stokBarang.getKodeGudang());
                    stok.setStokAwal(stokBarang.getStokAkhir());
                    stok.setStokMasuk(qtyIn);
                    stok.setStokKeluar(qtyOut);
                    stok.setStokAkhir(stokBarang.getStokAkhir() + qtyIn - qtyOut);
                    StokBarangDAO.insert(con, stok);
                }
                LogBarang logUmum = LogBarangDAO.getLastByBarangAndGudang(con, kodeBarang, kodeGudang);
                LogBarang logBarang = new LogBarang();
                logBarang.setTanggal(tglSql.format(date));
                logBarang.setKodeBarang(logUmum.getKodeBarang());
                logBarang.setKodeGudang(logUmum.getKodeGudang());
                logBarang.setKategori(kategori);
                logBarang.setKeterangan(keterangan);
                logBarang.setStokAwal(logUmum.getStokAkhir());
                logBarang.setNilaiAwal(logUmum.getNilaiAkhir());
                logBarang.setStokMasuk(qtyIn);
                logBarang.setNilaiMasuk(nilaiIn);
                logBarang.setStokKeluar(qtyOut);
                logBarang.setNilaiKeluar(nilaiOut);
                logBarang.setStokAkhir(logUmum.getStokAkhir() + qtyIn - qtyOut);
                logBarang.setNilaiAkhir(logUmum.getNilaiAkhir() + nilaiIn - nilaiOut);
                LogBarangDAO.insert(con, logBarang);
            }
        }
        return status;
    }

//    public static String newReturPenjualan(Connection con, ReturPenjualanHead retur){
//        try{
//            con.setAutoCommit(false);
//            String status = "true";
//            
//            ReturPenjualanHeadDAO.insert(con, retur);
//            
//            String noKeuangan = KeuanganDAO.getId(con);
//            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), retur.getTipeKeuangan(), "Retur Penjualan", 
//                    "Retur Penjualan - "+retur.getNoReturPenjualan(), -retur.getTotalRetur(), sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Retur Penjualan", "Retur Penjualan", 
//                    "Retur Penjualan - "+retur.getNoReturPenjualan(), retur.getTotalRetur(), sistem.getUser().getKodeUser());
//            
//            double hpp = 0;
//            List<ReturPenjualanDetail> stokBarang = new ArrayList<>();
//            for(ReturPenjualanDetail d : retur.getListReturPenjualanDetail()){
//                ReturPenjualanDetailDAO.insert(con, d);
//                
//                hpp = hpp + d.getNilai()*d.getQty();
//                
//                Boolean inStok = false;
//                for(ReturPenjualanDetail detail : stokBarang){
//                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
//                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
//                                (detail.getQty()+d.getQty()));
//                        detail.setQty(detail.getQty()+d.getQty());
//                        inStok = true;
//                    }
//                }
//                if(!inStok)
//                    stokBarang.add(d);
//            }
//            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "HPP", "Retur Penjualan", 
//                    "Retur Penjualan - "+retur.getNoReturPenjualan(), -hpp, sistem.getUser().getKodeUser());
//            
//            for(ReturPenjualanDetail d : stokBarang){
//                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), d.getKodeBarang());
//                if(stok==null){
//                    StokBarang newStok = new StokBarang();
//                    newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
//                    newStok.setKodeBarang(d.getKodeBarang());
//                    newStok.setStokAwal(0);
//                    newStok.setStokMasuk(d.getQty());
//                    newStok.setStokKeluar(0);
//                    newStok.setStokAkhir(d.getQty());
//                    StokBarangDAO.insert(con, newStok);
//                }else{
//                    if(stok.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
//                        stok.setStokMasuk(stok.getStokMasuk()+d.getQty());
//                        stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
//                        StokBarangDAO.update(con, stok);
//                    }else{
//                        StokBarang newStok = new StokBarang();
//                        newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
//                        newStok.setKodeBarang(d.getKodeBarang());
//                        newStok.setStokAwal(stok.getStokAkhir());
//                        newStok.setStokMasuk(d.getQty());
//                        newStok.setStokKeluar(0);
//                        newStok.setStokAkhir(stok.getStokAkhir()+d.getQty());
//                        StokBarangDAO.insert(con, newStok);
//                    }
//                }
//                LogBarang lb = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());
//                double nilai = d.getNilai()* d.getQty();
//
//                LogBarang log = new LogBarang();
//                log.setTanggal(tglSql.format(Function.getServerDate(con)));
//                log.setKodeBarang(d.getKodeBarang());
//                log.setKategori("Retur Penjualan");
//                log.setKeterangan(retur.getNoReturPenjualan());
//                log.setStokAwal(lb.getStokAkhir());
//                log.setNilaiAwal(lb.getNilaiAkhir());
//                log.setStokMasuk(d.getQty());
//                log.setNilaiMasuk(nilai);
//                log.setStokKeluar(0);
//                log.setNilaiKeluar(0);
//                log.setStokAkhir(lb.getStokAkhir()+d.getQty());
//                log.setNilaiAkhir(lb.getNilaiAkhir()+nilai);
//                LogBarangDAO.insert(con, log);
//
//                insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Barang", d.getKodeBarang(), 
//                        "Retur Penjualan - "+retur.getNoReturPenjualan(), nilai, sistem.getUser().getKodeUser());
//            }
//            if(status.equals("true"))
//                con.commit();
//            else 
//                con.rollback();
//            con.setAutoCommit(true);
//            
//            return status;
//        }catch(Exception e){
//            try{
//                con.rollback();
//            con.setAutoCommit(true);
//                return e.toString();
//            }catch(SQLException ex){
//                return ex.toString();
//            }
//        }
//    }
//    public static String batalReturPenjualan(Connection con, ReturPenjualanHead retur){
//        try{
//            con.setAutoCommit(false);
//            String status = "true";
//            
//            ReturPenjualanHeadDAO.update(con, retur);
//            
//            KeuanganDAO.delete(con, retur.getTipeKeuangan(), "Retur Penjualan", "Retur Penjualan - "+retur.getNoReturPenjualan());
//            KeuanganDAO.delete(con, "Retur Penjualan", "Retur Penjualan", "Retur Penjualan - "+retur.getNoReturPenjualan());
//            
//            retur.setListReturPenjualanDetail(ReturPenjualanDetailDAO.getAllByNoRetur(con, retur.getNoReturPenjualan()));
//            double hpp = 0;
//            List<ReturPenjualanDetail> stokBarang = new ArrayList<>();
//            for(ReturPenjualanDetail d : retur.getListReturPenjualanDetail()){
//                hpp = hpp + d.getNilai()*d.getQty();
//                
//                Boolean inStok = false;
//                for(ReturPenjualanDetail detail : stokBarang){
//                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
//                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
//                                (detail.getQty()+d.getQty()));
//                        detail.setQty(detail.getQty()+d.getQty());
//                        inStok = true;
//                    }
//                }
//                if(!inStok)
//                    stokBarang.add(d);
//            }
//            KeuanganDAO.delete(con, "HPP", "HPP", "Retur Penjualan - "+retur.getNoReturPenjualan());
//            
//            for(ReturPenjualanDetail d : stokBarang){
//                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(retur.getTglReturPenjualan())), d.getKodeBarang());
//                if(stok==null)
//                    status = "Stok barang "+d.getKodeBarang()+" tidak ditemukan";
//                else if(stok.getStokAkhir()<d.getQty())
//                    status = "Stok barang "+d.getKodeBarang()+" tidak mencukupi";
//                else{    
//                    stok.setStokMasuk(stok.getStokMasuk()-d.getQty());
//                    stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
//                    StokBarangDAO.update(con, stok);
//                    resetStokBarang(con, d.getKodeBarang(), tglBarang.format(tglSql.parse(retur.getTglReturPenjualan())));
//
//                    LogBarangDAO.delete(con, d.getKodeBarang(), "Retur Penjualan", retur.getNoReturPenjualan());
//                    resetLogBarang(con, retur.getTglReturPenjualan(), d.getKodeBarang());
//
//                    KeuanganDAO.delete(con, "Stok Barang", d.getKodeBarang(), "Retur Penjualan - "+retur.getNoReturPenjualan());
//                }
//            }
//
//            if(status.equals("true"))
//                con.commit();
//            else 
//                con.rollback();
//            con.setAutoCommit(true);
//            
//            return status;
//        }catch(Exception e){
//            try{
//                con.rollback();
//                con.setAutoCommit(true);
//                return e.toString();
//            }catch(SQLException ex){
//                return ex.toString();
//            }
//        }
//    }
//    public static String saveAbsensiManual(Connection con ,Absensi a){
//        try{
//            con.setAutoCommit(false);
//            String status = "true";
//            
//            Absensi alama = AbsensiDAO.get(con, a.getTanggal(), a.getKodePegawai());
//            if(alama==null){
//                AbsensiDAO.insert(con, a);
//            }else{
//                AbsensiDAO.update(con, a);
//            }
//            
//            if(status.equals("true"))
//                con.commit();
//            else
//                con.rollback();
//            con.setAutoCommit(true);
//            return status;
//        }catch(Exception e){
//            try{
//                con.rollback();
//                con.setAutoCommit(true);
//                return e.toString();
//            }catch(SQLException ex){
//                return ex.toString();
//            }
//        }
//    }
}
