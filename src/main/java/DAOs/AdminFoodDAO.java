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
import Models.AdminFood;

/**
 *
 * @author Hung
 */
public class AdminFoodDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;

  public AdminFoodDAO() {
    conn = DBConnection.DBConnection.getConnection();
  }

  public ResultSet getAll() {
    String sql = "select * from AdminFood";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      return rs;
    } catch (SQLException ex) {
      Logger.getLogger(AdminFoodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public AdminFood getAdminFood(byte adminID, short foodID) {
    AdminFood adminFood = null;
    try {
      ps = conn.prepareStatement("select * from AdminFood where admin_id = ? and food_id = ?");
      ps.setByte(1, adminID);
      ps.setShort(2, foodID);
      rs = ps.executeQuery();
      if (rs.next()) {
        adminFood = new AdminFood(rs.getByte("admin_id"), rs.getShort("food_id"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(AdminFoodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return adminFood;
  }

  public int add(AdminFood adminFood) {
    String sql = "insert into AdminFood values (?, ?)";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, adminFood.getAdminID());
      ps.setShort(2, adminFood.getFoodID());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(AdminFoodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int delete(byte adminID, short foodID) {
    int result = 0;
    String sql = "delete from AdminFood where admin_id = ? and food_id = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, adminID);
      ps.setShort(2, foodID);
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(AdminFoodDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
}
