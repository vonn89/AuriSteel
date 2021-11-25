/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymmdd;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KeuanganDAO {
    public static List<Keuangan> getAllByTanggal(Connection con, String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_keuangan "
                + " where left(tgl_keuangan,10) between ? and ? and status = 'true'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            k.setTglTransaksi(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
            k.setTipeKeuangan(rs.getString(4));
            k.setKategori(rs.getString(5));
            k.setNoTransaksi(rs.getString(6));
            k.setDeskripsi(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeUser(rs.getString(9));
            k.setStatus(rs.getString(10));
            k.setTglBatal(rs.getString(11));
            k.setUserBatal(rs.getString(12));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static List<Keuangan> getAllByTipeKeuanganAndTanggal(
            Connection con, String tipeKeuangan,String tglMulai,String tglAkhir)throws Exception{
        String sql = "select * from tt_keuangan where left(tgl_keuangan,10) between ? and ? and status = 'true' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        sql = sql + " order by tgl_keuangan ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            k.setTglTransaksi(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
            k.setTipeKeuangan(rs.getString(4));
            k.setKategori(rs.getString(5));
            k.setNoTransaksi(rs.getString(6));
            k.setDeskripsi(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeUser(rs.getString(9));
            k.setStatus(rs.getString(10));
            k.setTglBatal(rs.getString(11));
            k.setUserBatal(rs.getString(12));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static List<Keuangan> getAllByTipeKeuangan(Connection con, String tipeKeuangan)throws Exception{
        String sql = "select * from tt_keuangan where status = 'true' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        sql = sql + " order by tgl_keuangan ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            k.setTglTransaksi(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
            k.setTipeKeuangan(rs.getString(4));
            k.setKategori(rs.getString(5));
            k.setNoTransaksi(rs.getString(6));
            k.setDeskripsi(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeUser(rs.getString(9));
            k.setStatus(rs.getString(10));
            k.setTglBatal(rs.getString(11));
            k.setUserBatal(rs.getString(12));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static Double getSaldoAkhir(Connection con, String tanggal,String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan "
                + " where left(tgl_keuangan,10) <= ? and status = 'true' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        double saldoAwal = 0;
        if(rs.next())
            saldoAwal = rs.getDouble(1);
        return saldoAwal;
    }
    public static Double getSaldoAwal(Connection con, String tanggal,String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan "
                + " where left(tgl_keuangan,10) < ? and status = 'true' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        double saldoAwal = 0;
        if(rs.next())
            saldoAwal = rs.getDouble(1);
        return saldoAwal;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) "
                + " from tt_keuangan where mid(no_keuangan,4,6)=? ");
        ps.setString(1, yymmdd.format(date));
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return "KK-"+yymmdd.format(date)+"-" +new DecimalFormat("0000").format(rs.getInt(1)+1);
        else 
            return "KK-"+yymmdd.format(date)+"-"+new DecimalFormat("0000").format(1);
    }
    public static void insert(Connection con, Keuangan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan values (?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, k.getNoKeuangan());
        ps.setString(2, k.getTglKeuangan());
        ps.setString(3, k.getTglTransaksi());
        ps.setString(4, k.getTipeKeuangan());
        ps.setString(5, k.getKategori());
        ps.setString(6, k.getNoTransaksi());
        ps.setString(7, k.getDeskripsi());
        ps.setDouble(8, k.getJumlahRp());
        ps.setString(9, k.getKodeUser());
        ps.setString(10, k.getStatus());
        ps.setString(11, k.getTglBatal());
        ps.setString(12, k.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, Keuangan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_keuangan set "
                + " tgl_keuangan = ?, tgl_transaksi = ?, tipe_keuangan = ?, kategori = ?, "
                + " no_transaksi = ?, deskripsi = ?, jumlah_rp = ?, kode_user = ?, "
                + " status = ?, tgl_batal = ?, user_batal = ? "
                + " where no_keuangan = ?");
        ps.setString(1, k.getTglKeuangan());
        ps.setString(2, k.getTglTransaksi());
        ps.setString(3, k.getTipeKeuangan());
        ps.setString(4, k.getKategori());
        ps.setString(5, k.getNoTransaksi());
        ps.setString(6, k.getDeskripsi());
        ps.setDouble(7, k.getJumlahRp());
        ps.setString(8, k.getKodeUser());
        ps.setString(9, k.getStatus());
        ps.setString(10, k.getTglBatal());
        ps.setString(11, k.getUserBatal());
        ps.setString(12, k.getNoKeuangan());
        ps.executeUpdate();
    }
    public static void deleteByDeskripsi(Connection con, String tipeKeuangan, String kategori, String deskripsi)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan "
                + " where tipe_keuangan = ? and kategori = ? and deskripsi = ? ");
        ps.setString(1, tipeKeuangan);
        ps.setString(2, kategori);
        ps.setString(3, deskripsi);
        ps.executeUpdate();
    }
    public static void deleteByNoTransaksi(Connection con, String tipeKeuangan, String kategori, String noTransaksi)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan "
                + " where tipe_keuangan = ? and kategori = ? and no_transaksi = ? ");
        ps.setString(1, tipeKeuangan);
        ps.setString(2, kategori);
        ps.setString(3, noTransaksi);
        ps.executeUpdate();
    }
    public static void deleteByNoKeuangan1(Connection con, String noKeuangan)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan "
                + " where no_keuangan=?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
