/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Voucher;
import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public VoucherDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from Voucher";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Voucher getVoucher(byte id) {
        Voucher voucher = null;
        try {
            ps = conn.prepareStatement("select * from Voucher where voucher_id = ?");
            ps.setByte(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                voucher = new Voucher(rs.getByte("voucher_id"), rs.getString("voucher_name"), rs.getString("voucher_code"),rs.getByte("voucher_discount_percent"),rs.getByte("voucher_quantity"), rs.getByte("voucher_status"), rs.getTimestamp("voucher_date"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voucher;
    }
    
    public List<Voucher> getAllList() {
        ResultSet voucherRS = this.getAll();
        List<Voucher> voucherList = new ArrayList<>();
        try {
            while (voucherRS.next()) {
                Voucher voucher = new Voucher(
                        voucherRS.getByte("voucher_id"),
                        voucherRS.getString("voucher_name"),   
                        voucherRS.getString("voucher_code"),    
                        voucherRS.getByte("voucher_discount_percent"),
                        voucherRS.getByte("voucher_quantity"),
                        voucherRS.getByte("voucher_status"),
                        voucherRS.getTimestamp("voucher_date")
                );                
                voucherList.add(voucher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voucherList;
    }

    public int add(Voucher voucher) {
        String sql = "insert into Voucher values (?,?,?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, voucher.getVoucher_name());
            ps.setString(2, voucher.getVoucher_code());
            ps.setByte(3, voucher.getVoucher_discount_percent());
            ps.setByte(4, voucher.getVoucher_quantity());
            ps.setByte(5, voucher.getVoucher_status());
            ps.setTimestamp(6, voucher.getVoucher_date());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(byte id) {
        int result = 0;
        String sql = "delete from Voucher where voucher_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setByte(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Voucher voucher) {
        String sql = "update Voucher set voucher_name = ?, voucher_code = ?,voucher_discount_percent = ?, voucher_quantity = ?, voucher_status = ?, voucher_date = ? where voucher_id = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, voucher.getVoucher_name());
            ps.setString(2, voucher.getVoucher_code());           
            ps.setByte(3, voucher.getVoucher_discount_percent());
            ps.setByte(4, voucher.getVoucher_quantity());
            ps.setByte(5, voucher.getVoucher_status());
            ps.setTimestamp(6, voucher.getVoucher_date());
            ps.setByte(7, voucher.getVoucherID());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
