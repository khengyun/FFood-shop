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
import Models.PaymentMethod;

/**
 *
 * @author Hung
 */
public class PaymentMethodDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;

  public PaymentMethodDAO() {
    conn = DBConnection.DBConnection.getConnection();
  }

  public ResultSet getAll() {
    String sql = "select*from PaymentMethod";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      return rs;
    } catch (SQLException ex) {
      Logger.getLogger(PaymentMethodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public PaymentMethod getPaymentMethod(byte paymentMethodID) {
    PaymentMethod paymentMethod = null;
    try {
      ps = conn.prepareStatement("select*from PaymentMethod where payment_method_id=?");
      ps.setByte(1, paymentMethodID);
      rs = ps.executeQuery();
      if (rs.next()) {
        paymentMethod = new PaymentMethod(rs.getByte("payment_method_id"), rs.getString("payment_method"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(PaymentMethodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return paymentMethod;
  }

  public int add(PaymentMethod paymentMethod) {
    String sql = "insert into PaymentMethod values(?, ?)";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, paymentMethod.getPaymentMethodID());
      ps.setString(2, paymentMethod.getPaymentMethod());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(PaymentMethodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int delete(byte paymentMethodID) {
    int result = 0;
    String sql = "delete from PaymentMethod where payment_method_id = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, paymentMethodID);
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(PaymentMethodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int update(PaymentMethod paymentMethod) {
    String sql = "update PaymentMethod set payment_method = ? where payment_method_id = ?";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, paymentMethod.getPaymentMethod());
      ps.setByte(2, paymentMethod.getPaymentMethodID());
    } catch (SQLException ex) {
      Logger.getLogger(PaymentMethodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
}
