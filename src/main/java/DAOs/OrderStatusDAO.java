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
import Models.OrderStatus;

/**
 *
 * @author Hung
 */
public class OrderStatusDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;

  public OrderStatusDAO() {
    conn = DBConnection.DBConnection.getConnection();
  }

  public ResultSet getAll() {
    String sql = "select*from OrderStatus";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      return rs;
    } catch (SQLException ex) {
      Logger.getLogger(OrderStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public OrderStatus getOrderStatus(String gods) {
    OrderStatus ods = null;
    try {
      ps = conn.prepareStatement("select*from OrderStatus where food_id=?");
      ps.setString(1, gods);
      rs = ps.executeQuery();
      if (rs.next()) {
        ods = new OrderStatus(rs.getByte("order_status_id"), rs.getString("order_status"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(OrderStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return ods;
  }

  public int add(OrderStatus orderStatus) {
    String sql = "insert into OrderStatus values(?, ?)";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, orderStatus.getOrderStatusID());
      ps.setString(2, orderStatus.getOrderStatus());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(OrderStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int delete(int orderStatusID) {
    int result = 0;
    String sql = "delete from OrderStatus where order_status_id = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, orderStatusID);
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(OrderStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int update(OrderStatus orderStatus) {
    String sql = "update OrderStatus set order_status = ? where order_status_id = ?";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, orderStatus.getOrderStatus());
      ps.setByte(2, orderStatus.getOrderStatusID());
    } catch (SQLException ex) {
      Logger.getLogger(OrderStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
}
