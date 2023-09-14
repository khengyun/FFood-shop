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
import Models.FoodType;

/**
 *
 * @author Hung
 */
public class FoodTypeDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;

  public FoodTypeDAO() {
    conn = DBConnection.DBConnection.getConnection();
  }

  public ResultSet getAllFoodType() {
    String sql = "select * from FoodType";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      return rs;
    } catch (SQLException ex) {
      Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public FoodType getFoodType(byte foodTypeID) {
    FoodType foodType = null;
    try {
      ps = conn.prepareStatement("select * from FoodType where food_type_id = ?");
      ps.setByte(1, foodTypeID);
      rs = ps.executeQuery();
      if (rs.next()) {
        foodType = new FoodType(rs.getByte("food_type_id"), rs.getString("food_type"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return foodType;
  }
  
  public int add(FoodType foodType) {
    String sql = "insert into FoodType values (?, ?)";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, foodType.getFoodTypeID());
      ps.setString(2, foodType.getFoodType());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int delete(byte foodTypeID) {
    int result = 0;
    String sql = "delete from FoodType where food_type_id = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setByte(1, foodTypeID);
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int update(FoodType foodType) {
    String sql = "update FoodType set food_type = ? where food_type_id = ?";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, foodType.getFoodType());
      ps.setByte(2, foodType.getFoodTypeID());
    } catch (SQLException ex) {
      Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
   
}
