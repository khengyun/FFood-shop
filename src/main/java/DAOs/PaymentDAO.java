/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Voucher;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public PaymentDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from Payment";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Payment getPayment(byte id) {
        Payment payment = null;
        try {
            ps = conn.prepareStatement("select * from Payment where order_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                payment = new Payment(
                        rs.getInt("order_id"), 
                        rs.getByte("payment_method_id"), 
                        rs.getBigDecimal("payment_total"), 
                        rs.getString("payment_content"), 
                        rs.getString("payment_bank"), 
                        rs.getString("payment_code"), 
                        rs.getByte("payment_status") ,
                        rs.getTimestamp("payment_date")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment;
    }

    public List<Payment> getAllList() {
        ResultSet rs = this.getAll();
        List<Payment> paymentList = new ArrayList<>();
        try {
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("order_id"), 
                        rs.getByte("payment_method_id"), 
                        rs.getBigDecimal("payment_total"), 
                        rs.getString("payment_content"), 
                        rs.getString("payment_bank"), 
                        rs.getString("payment_code"), 
                        rs.getByte("payment_status") ,
                        rs.getTimestamp("payment_date")
                );       
                paymentList.add(payment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paymentList;
    }

    public int addPaymentByBank(Payment payment) {
        String sql = "insert into Payment (order_id, payment_method_id, payment_total, payment_content, payment_bank, payment_code, payment_status, payment_time) values (?,?,?,?,?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, payment.getOrder_id());
            ps.setByte(2, payment.getPayment_method_id());
            ps.setBigDecimal(3, payment.getPayment_total());
            ps.setString(4, payment.getPayment_content());
            ps.setString(5, payment.getPayment_bank());
            ps.setString(6, payment.getPayment_code());
            ps.setByte(7, payment.getPayment_status());
            ps.setTimestamp(8, payment.getPayment_time());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int addPaymentByCOD(Payment payment) {
        String sql = "insert into Payment (order_id, payment_method_id, payment_total, payment_content, payment_status, payment_time) values (?,?,?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, payment.getOrder_id());
            ps.setByte(2, payment.getPayment_method_id());
            ps.setBigDecimal(3, payment.getPayment_total());
            ps.setString(4, payment.getPayment_content());
            ps.setByte(5, payment.getPayment_status());
            ps.setTimestamp(6, payment.getPayment_time());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(byte id) {
        int result = 0;
        String sql = "delete from Payment where order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Payment payment) {
        String sql = "update Payment set payment_method_id = ?, payment_total = ?, payment_content = ?, payment_bank = ?, payment_code = ?, payment_status = ?, payment_time = ? where order_id = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setByte(1, payment.getPayment_method_id());
            ps.setBigDecimal(2, payment.getPayment_total());
            ps.setString(3, payment.getPayment_content());
            ps.setString(4, payment.getPayment_bank());
            ps.setString(5, payment.getPayment_code());
            ps.setByte(6, payment.getPayment_status());
            ps.setTimestamp(7, payment.getPayment_time());
            ps.setInt(8, payment.getOrder_id());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
