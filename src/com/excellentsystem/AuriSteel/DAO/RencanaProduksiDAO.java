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
            d.setNoKirim(rs.getInt(3));
            d.setTujuanKirim(rs.getString(4));
            d.setNoPemesanan(rs.getString(5));
            d.setCustomer(rs.getString(6));
            d.setBarang(rs.getString(7));
            d.setKeterangan(rs.getString(8));
            d.setCatatan(rs.getString(9));
            d.setSales(rs.getString(10));
            d.setQty(rs.getDouble(11));
            d.setTonase(rs.getDouble(12));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<RencanaProduksi> getAllByHari(Connection con, String hari)throws Exception{
        String sql = "select * from tt_rencana_produksi where hari =?";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<RencanaProduksi> allDetail = new ArrayList<>();
        while(rs.next()){
            RencanaProduksi d = new RencanaProduksi();
            d.setNoRencana(rs.getString(1));
            d.setHari(rs.getString(2));
            d.setNoKirim(rs.getInt(3));
            d.setTujuanKirim(rs.getString(4));
            d.setNoPemesanan(rs.getString(5));
            d.setCustomer(rs.getString(6));
            d.setBarang(rs.getString(7));
            d.setKeterangan(rs.getString(8));
            d.setCatatan(rs.getString(9));
            d.setSales(rs.getString(10));
            d.setQty(rs.getDouble(11));
            d.setTonase(rs.getDouble(12));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, RencanaProduksi d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rencana_produksi values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoRencana());
        ps.setString(2, d.getHari());
        ps.setInt(3, d.getNoKirim());
        ps.setString(4, d.getTujuanKirim());
        ps.setString(5, d.getNoPemesanan());
        ps.setString(6, d.getCustomer());
        ps.setString(7, d.getBarang());
        ps.setString(8, d.getKeterangan());
        ps.setString(9, d.getCatatan());
        ps.setString(10, d.getSales());
        ps.setDouble(11, d.getQty());
        ps.setDouble(12, d.getTonase());
        ps.executeUpdate();
    }
    public static void update(Connection con, RencanaProduksi p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_rencana_produksi set "
                + " hari=?, no_kirim=?, tujuan_kirim=?, "
                + " no_pemesanan=?, customer=?, barang=?, keterangan=?, "
                + " catatan=?, sales=?, qty=?, tonase=? "
                + " where no_rencana=?");
        ps.setString(1, p.getHari());
        ps.setInt(2, p.getNoKirim());
        ps.setString(3, p.getTujuanKirim());
        ps.setString(4, p.getNoPemesanan());
        ps.setString(5, p.getCustomer());
        ps.setString(6, p.getBarang());
        ps.setString(7, p.getKeterangan());
        ps.setString(8, p.getCatatan());
        ps.setString(9, p.getSales());
        ps.setDouble(10, p.getQty());
        ps.setDouble(11, p.getTonase());
        ps.setString(12, p.getNoRencana());
        ps.executeUpdate();
    }
    public static void delete(Connection con, RencanaProduksi p)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rencana_produksi where no_rencana = ?");
        ps.setString(1, p.getNoRencana());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rencana_produksi");
        ps.executeUpdate();
    }
}
