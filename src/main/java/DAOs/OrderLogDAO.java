/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.OrderLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Voucher;
import java.util.ArrayList;
import java.util.List;

public class OrderLogDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public OrderLogDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from OrderLog";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet getAllByID(int id) {
        String sql = "select * from OrderLog where order_id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OrderLog getOrderLog(int id) {
        OrderLog log = null;
        try {
            ps = conn.prepareStatement("select * from OrderLog where log_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                log = new OrderLog(rs.getInt("log_id"), rs.getInt("order_id"), rs.getByte("staff_id"), rs.getByte("admin_id"), rs.getString("log_activity"), rs.getTimestamp("log_time"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }
    
    public OrderLog getOrderLogByOrderID(int id) {
        OrderLog log = null;
        try {
            ps = conn.prepareStatement("select * from OrderLog where order_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                log = new OrderLog(rs.getInt("log_id"), rs.getInt("order_id"), rs.getByte("staff_id"), rs.getByte("admin_id"), rs.getString("log_activity"), rs.getTimestamp("log_time"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }
    
    public List<OrderLog> getAllList() {
        ResultSet rs = this.getAll();
        List<OrderLog> logList = new ArrayList<>();
        try {
            while (rs.next()) {
                OrderLog log = new OrderLog(
                        rs.getInt("log_id"), 
                        rs.getInt("order_id"), 
                        rs.getByte("staff_id"), 
                        rs.getByte("admin_id"), 
                        rs.getString("log_activity"), 
                        rs.getTimestamp("log_time")
                );
                logList.add(log);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logList;
    }
    
    public List<OrderLog> getAllListByOrderID(int id) {
        ResultSet rs = this.getAllByID(id);
        List<OrderLog> logList = new ArrayList<>();
        try {
            while (rs.next()) {
                OrderLog log = new OrderLog(
                        rs.getInt("log_id"), 
                        rs.getInt("order_id"), 
                        rs.getByte("staff_id"), 
                        rs.getByte("admin_id"), 
                        rs.getString("log_activity"), 
                        rs.getTimestamp("log_time")
                );
                logList.add(log);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logList;
    }

    public int addAdminLog(OrderLog log) {
        String sql = "insert into OrderLog (order_id, admin_id, log_activity, log_time) values (?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, log.getOrder_id());
            ps.setByte(2, log.getAdmin_id());
            ps.setString(3, log.getLog_activity());
            ps.setTimestamp(4, log.getLog_time());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int addStaffLog(OrderLog log) {
        String sql = "insert into OrderLog (order_id, staff_id, log_activity, log_time) values (?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, log.getOrder_id());
            ps.setByte(2, log.getStaff_id());
            ps.setString(3, log.getLog_activity());
            ps.setTimestamp(4, log.getLog_time());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(byte id) {
        int result = 0;
        String sql = "delete from OrderLog where log_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setByte(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(OrderLog log) {
        String sql = "update OrderLog set order_id = ?, staff_id = ?,admin_id = ?, log_activity = ?, log_time = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);         
            ps.setInt(1, log.getOrder_id());
            ps.setByte(2, log.getStaff_id());
            ps.setByte(3, log.getAdmin_id());
            ps.setString(4, log.getLog_activity());
            ps.setTimestamp(5, log.getLog_time());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
