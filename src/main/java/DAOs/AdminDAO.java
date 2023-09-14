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
import Models.Admin;

/**
 *
 * @author Hung
 */
public class AdminDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;

  public AdminDAO() {
    conn = DBConnection.DBConnection.getConnection();
  }

  public ResultSet getAll() {
    String sql = "select * from [Admin]";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      return rs;
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public Admin getAdmin(byte id) {
    Admin admin = null;
    try {
      ps = conn.prepareStatement("select * from [Admin] where admin_id = ?");
      ps.setByte(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        admin = new Admin(rs.getByte("admin_id"), rs.getString("admin_fullname"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return admin;
  }

  public int add(Admin admin) {
    String sql = "insert into [Admin] values (?)";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, admin.getFullName());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int delete(byte id) {
    int result = 0;
    String sql = "delete from [Admin] where admin_id = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, id);
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int update(Admin admin) {
    String sql = "update [Admin] set admin_fullname = ? where admin_id = ?";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, admin.getFullName());
      ps.setByte(2, admin.getAdminID());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
}
