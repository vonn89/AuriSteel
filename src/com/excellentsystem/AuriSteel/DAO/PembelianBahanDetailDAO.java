/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.PembelianBahanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianBahanDetailDAO {
    public static List<PembelianBahanDetail> getAllByDateAndStatus(Connection con, 
            String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembelian_bahan_detail "
            + " where no_pembelian in (select no_pembelian from tt_pembelian_bahan_head "
            + " where left(tgl_pembelian,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PembelianBahanDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianBahanDetail d = new PembelianBahanDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoPemesanan(rs.getString(2));
            d.setNoUrut(rs.getInt(3));
            d.setNoPenerimaan(rs.getString(4));
            d.setKodeBahan(rs.getString(5));
            d.setKodeKategori(rs.getString(6));
            d.setNoKontrak(rs.getString(7));
            d.setNamaBahan(rs.getString(8));
            d.setSpesifikasi(rs.getString(9));
            d.setQty(rs.getDouble(10));
            d.setNilai(rs.getDouble(11));
            d.setHargaBeli(rs.getDouble(12));
            d.setTotal(rs.getDouble(13));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PembelianBahanDetail> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_bahan_detail where no_pembelian=?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        List<PembelianBahanDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianBahanDetail d = new PembelianBahanDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoPemesanan(rs.getString(2));
            d.setNoUrut(rs.getInt(3));
            d.setNoPenerimaan(rs.getString(4));
            d.setKodeBahan(rs.getString(5));
            d.setKodeKategori(rs.getString(6));
            d.setNoKontrak(rs.getString(7));
            d.setNamaBahan(rs.getString(8));
            d.setSpesifikasi(rs.getString(9));
            d.setQty(rs.getDouble(10));
            d.setNilai(rs.getDouble(11));
            d.setHargaBeli(rs.getDouble(12));
            d.setTotal(rs.getDouble(13));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PembelianBahanDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_bahan_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPembelian());
        ps.setString(2, d.getNoPemesanan());
        ps.setInt(3, d.getNoUrut());
        ps.setString(4, d.getNoPenerimaan());
        ps.setString(5, d.getKodeBahan());
        ps.setString(6, d.getKodeKategori());
        ps.setString(7, d.getNoKontrak());
        ps.setString(8, d.getNamaBahan());
        ps.setString(9, d.getSpesifikasi());
        ps.setDouble(10, d.getQty());
        ps.setDouble(11, d.getNilai());
        ps.setDouble(12, d.getHargaBeli());
        ps.setDouble(13, d.getTotal());
        ps.executeUpdate();
    }
}
