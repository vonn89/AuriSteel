/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.RencanaProduksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class RencanaProduksiDAO {
    
    public static List<RencanaProduksi> getAll(Connection con)throws Exception{
        String sql = "select * from tt_rencana_produksi";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<RencanaProduksi> allDetail = new ArrayList<>();
        while(rs.next()){
            RencanaProduksi d = new RencanaProduksi();
            d.setNoRencana(rs.getString(1));
            d.setHari(rs.getString(2));
            d.setTujuanKirim(rs.getString(3));
            d.setNoPemesanan(rs.getString(4));
            d.setCustomer(rs.getString(5));
            d.setBarang(rs.getString(6));
            d.setKeterangan(rs.getString(7));
            d.setCatatan(rs.getString(8));
            d.setSales(rs.getString(9));
            d.setQty(rs.getDouble(10));
            d.setTonase(rs.getDouble(11));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<RencanaProduksi> getAllByHari(Connection con, String hari)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rencana_produksi where hari =?");
        ps.setString(1, hari);
        ResultSet rs = ps.executeQuery();
        List<RencanaProduksi> allDetail = new ArrayList<>();
        while(rs.next()){
            RencanaProduksi d = new RencanaProduksi();
            d.setNoRencana(rs.getString(1));
            d.setHari(rs.getString(2));
            d.setTujuanKirim(rs.getString(3));
            d.setNoPemesanan(rs.getString(4));
            d.setCustomer(rs.getString(5));
            d.setBarang(rs.getString(6));
            d.setKeterangan(rs.getString(7));
            d.setCatatan(rs.getString(8));
            d.setSales(rs.getString(9));
            d.setQty(rs.getDouble(10));
            d.setTonase(rs.getDouble(11));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(no_rencana) from tt_rencana_produksi ");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, RencanaProduksi d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rencana_produksi values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoRencana());
        ps.setString(2, d.getHari());
        ps.setString(3, d.getTujuanKirim());
        ps.setString(4, d.getNoPemesanan());
        ps.setString(5, d.getCustomer());
        ps.setString(6, d.getBarang());
        ps.setString(7, d.getKeterangan());
        ps.setString(8, d.getCatatan());
        ps.setString(9, d.getSales());
        ps.setDouble(10, d.getQty());
        ps.setDouble(11, d.getTonase());
        ps.executeUpdate();
    }
    public static void update(Connection con, RencanaProduksi p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_rencana_produksi set "
                + " hari=?, tujuan_kirim=?, no_pemesanan=?, "
                + " customer=?, barang=?, keterangan=?, "
                + " catatan=?, sales=?, qty=?, tonase=? "
                + " where no_rencana=?");
        ps.setString(1, p.getHari());
        ps.setString(2, p.getTujuanKirim());
        ps.setString(3, p.getNoPemesanan());
        ps.setString(4, p.getCustomer());
        ps.setString(5, p.getBarang());
        ps.setString(6, p.getKeterangan());
        ps.setString(7, p.getCatatan());
        ps.setString(8, p.getSales());
        ps.setDouble(9, p.getQty());
        ps.setDouble(10, p.getTonase());
        ps.setString(11, p.getNoRencana());
        ps.executeUpdate();
    }
    public static void delete(Connection con, RencanaProduksi p)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rencana_produksi where no_rencana = ?");
        ps.setString(1, p.getNoRencana());
        ps.executeUpdate();
    }
    public static void deleteGroup(Connection con, RencanaProduksi p)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rencana_produksi where tujuan_kirim = ? and hari = ?");
        ps.setString(1, p.getTujuanKirim());
        ps.setString(2, p.getHari());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rencana_produksi");
        ps.executeUpdate();
    }
}
