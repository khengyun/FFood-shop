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
import Models.Food;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public FoodDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from Food";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Food> getAllList() {
        ResultSet foodRS = this.getAll();
        List<Food> foodList = new ArrayList<>();
        try {
            while (foodRS.next()) {
                Food food = new Food(
                        foodRS.getShort("food_id"),
                        foodRS.getString("food_name"),
                        foodRS.getString("food_description"),
                        foodRS.getBigDecimal("food_price"),
                        foodRS.getByte("food_status"),
                        foodRS.getByte("food_rate"),
                        foodRS.getByte("discount_percent"),
                        foodRS.getString("food_img_url"),
                        foodRS.getByte("food_type_id"),
                        this.getFoodType(foodRS.getByte("food_type_id")));
                foodList.add(food);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foodList;
    }

    public Food getFood(short foodID) {
        Food food = null;
        try {
            ps = conn.prepareStatement("select * from Food where food_id = ?");
            ps.setShort(1, foodID);
            rs = ps.executeQuery();
            if (rs.next()) {
                food = new Food(
                        rs.getShort("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBigDecimal("food_price"),
                        rs.getByte("food_status"),
                        rs.getByte("food_rate"),
                        rs.getByte("discount_percent"),
                        rs.getString("food_img_url"),
                        rs.getByte("food_type_id"),
                        this.getFoodType(rs.getByte("food_type_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return food;
    }
    
    public Food getFood(String food_name) {
        Food food = null;
        try {
            ps = conn.prepareStatement("select * from Food where food_name = ?");
            ps.setString(1, food_name);
            rs = ps.executeQuery();
            if (rs.next()) {
                food = new Food(
                        rs.getShort("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBigDecimal("food_price"),
                        rs.getByte("food_status"),
                        rs.getByte("food_rate"),
                        rs.getByte("discount_percent"),
                        rs.getString("food_img_url"),
                        rs.getByte("food_type_id"),
                        this.getFoodType(rs.getByte("food_type_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return food;
    }

    public int add(Food food) {
        String sql = "insert into Food (food_name, food_description, food_price, food_status, food_rate, discount_percent, food_img_url, food_type_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        System.out.println("url: " + food.getImageURL());
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, food.getFoodName());
            ps.setString(2, food.getDescription());
            ps.setBigDecimal(3, food.getFoodPrice());
            ps.setByte(4, food.getStatus());
            ps.setByte(5, food.getRate());
            ps.setByte(6, food.getDiscountPercent());
            ps.setString(7, food.getImageURL());
            ps.setByte(8, food.getFoodTypeID());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(short foodID) {
        int result = 0;
        String sql = "delete from Food where food_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setShort(1, foodID);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int deleteMultiple(List<Short> foodIDs) {

        int result = 0;
        try {
            conn.setAutoCommit(false); // Start transaction
            for (Short foodID : foodIDs) {
                if (delete(foodID) == 1) {
                    result++; // Count number of successful deletions
                } else {
                    conn.rollback(); // Rollback transaction if deletion fails
                    return 0;
                }
            }
            conn.commit(); // Commit transaction if all deletions succeed
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback(); // Rollback transaction if any exception occurs
            } catch (SQLException rollbackEx) {
                Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            return 0;
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto commit
            } catch (SQLException finalEx) {
                Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, finalEx);
            }
        }
        return result;
    }

    public int update(Food food) {
        String sql = "update Food set food_name = ?, food_description = ?, food_price = ?, food_status = ?, food_rate = ?, discount_percent = ?, food_img_url = ?, food_type_id = ? where food_id = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, food.getFoodName());
             ps.setString(2, food.getDescription());
            ps.setBigDecimal(3, food.getFoodPrice());
            ps.setByte(4, food.getStatus());
            ps.setByte(5, food.getRate());
            ps.setByte(6, food.getDiscountPercent());
            ps.setString(7, food.getImageURL());
            ps.setByte(8, food.getFoodTypeID());
            ps.setShort(9, food.getFoodID());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String getFoodType(byte foodTypeID) {
        String foodType = null;
        try {
            ps = conn.prepareStatement("select * from FoodType where food_type_id = ?");
            ps.setByte(1, foodTypeID);
            rs = ps.executeQuery();
            if (rs.next()) {
                foodType = rs.getString("food_type");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foodType;
    }

    public ResultSet searchByName(String txtSearch) {
        String query = "select * from Food\n"
                + "where [food_name] like ?";
        try {
            conn = DBConnection.DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSearch + "%");
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Food> searchByNameList(String txtSearch) {
        ResultSet searchRS = searchByName(txtSearch);
        List<Food> list = new ArrayList<>();
        String query = "select * from Food\n"
                + "where [food_name] like ?";
        try {
            while (searchRS.next()) {
                Food food = new Food(
                        searchRS.getShort("food_id"),
                        searchRS.getString("food_name"),
                        searchRS.getString("food_description"),
                        searchRS.getBigDecimal("food_price"),
                        searchRS.getByte("food_status"),
                        searchRS.getByte("food_rate"),
                        searchRS.getByte("discount_percent"),
                        searchRS.getString("food_img_url"),
                        searchRS.getByte("food_type_id"),
                        this.getFoodType(searchRS.getByte("food_type_id")));
                list.add(food);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Food> getCateByCid(String cid) {
        List<Food> list = new ArrayList<>();
        String query = "select * from Food\n"
                + "where cateID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, cid);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Food(
                        rs.getShort("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBigDecimal("food_price"),
                        rs.getByte("food_status"),
                        rs.getByte("food_rate"),
                        rs.getByte("discount_percent"),
                        rs.getString("food_img_url"),
                        rs.getByte("food_type_id")));
            }
        } catch (Exception e) {
        }
        return list;
    }
}
