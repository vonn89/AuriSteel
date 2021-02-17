/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.Mesin;
import com.excellentsystem.AuriSteel.Model.MesinDetailBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class MesinDetailBarangDAO {
    public static List<MesinDetailBarang> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_mesin_detail_barang");
        ResultSet rs = ps.executeQuery();
        List<MesinDetailBarang> allMesinDetailBarang = new ArrayList<>();
        while(rs.next()){
            MesinDetailBarang o = new MesinDetailBarang();
            o.setKodeMesin(rs.getString(1));
            o.setKodeBarang(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allMesinDetailBarang.add(o);
        }
        return allMesinDetailBarang;
    }
    public static List<MesinDetailBarang> getAllByKodeMesin(Connection con, String kodeMesin)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_mesin_detail_barang where kode_mesin=?");
        ps.setString(1, kodeMesin);
        ResultSet rs = ps.executeQuery();
        List<MesinDetailBarang> allMesinDetailBarang = new ArrayList<>();
        while(rs.next()){
            MesinDetailBarang o = new MesinDetailBarang();
            o.setKodeMesin(rs.getString(1));
            o.setKodeBarang(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allMesinDetailBarang.add(o);
        }
        return allMesinDetailBarang;
    }
    public static void insert(Connection con, MesinDetailBarang m)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_mesin_detail_barang values(?,?,?)");
        ps.setString(1, m.getKodeMesin());
        ps.setString(2, m.getKodeBarang());
        ps.setString(3, String.valueOf(m.isStatus()));
        ps.executeUpdate();
    }
    public static void update(Connection con, MesinDetailBarang m)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_mesin_detail_barang set status=? where kode_barang=? and kode_mesin=?");
        ps.setString(1, String.valueOf(m.isStatus()));
        ps.setString(2, m.getKodeBarang());
        ps.setString(3, m.getKodeMesin());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Mesin mesin)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_mesin_detail_barang where kode_mesin=?");
        ps.setString(1, mesin.getKodeMesin());
        ps.executeUpdate();
    }
    public static void deleteByBarang(Connection con, Barang barang)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_mesin_detail_barang where kode_barang=?");
        ps.setString(1, barang.getKodeBarang());
        ps.executeUpdate();
    }
}
