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
import Models.PromotionManager;
import Models.Staff;
import java.util.ArrayList;
import java.util.List;

public class PromotionManagerDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public PromotionManagerDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from PromotionManager";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public List<PromotionManager> getAllPromotionManager() {
        ResultSet promotionManagerRS = this.getAll();
        try {
            List<PromotionManager> promotionManagerList = new ArrayList<>();
            while (promotionManagerRS.next()) {
                // Selected account is of User type               
                PromotionManager promotionManager = new PromotionManager(
                        promotionManagerRS.getByte("pro_id"),
                        promotionManagerRS.getString("pro_fullname")
                );              
                promotionManagerList.add(promotionManager);               
            }
            return promotionManagerList;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public PromotionManager getPromotionManager(byte id) {
        PromotionManager pro = null;
        try {
            ps = conn.prepareStatement("select * from PromotionManager where pro_id = ?");
            ps.setByte(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro = new PromotionManager(rs.getByte("pro_id"), rs.getString("pro_fullname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pro;
    }
    
    public PromotionManager getNewPromotionManager() {
        PromotionManager promotionManager = null;
        String sql = "SELECT TOP 1 * FROM PromotionManager ORDER BY pro_id DESC;";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                promotionManager = new PromotionManager(rs.getByte("pro_id"),rs.getString("pro_fullname"));
            }
            return promotionManager;
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int add(PromotionManager pro) {
        String sql = "insert into PromotionManager values (?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getFullName());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(byte id) {
        int result = 0;
        String sql = "delete from PromotionManager where pro_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setByte(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int deleteMultiple(List<Byte> proIDs) {
        int result = 0;
        try {
            conn.setAutoCommit(false); // Start transaction
            for (Byte proID : proIDs) {
                if (delete(proID) == 1) {
                    result++; // Count number of successful deletions
                } else {
                    conn.rollback(); // Rollback transaction if deletion fails
                    return 0;
                }
            }
            conn.commit(); // Commit transaction if all deletions succeed
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback(); // Rollback transaction if any exception occurs
            } catch (SQLException rollbackEx) {
                Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            return 0;
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto commit
            } catch (SQLException finalEx) {
                Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, finalEx);
            }
        }
        return result;
    }

    public int update(PromotionManager pro) {
        String sql = "update PromotionManager set pro_fullname = ? where pro_id = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getFullName());
            ps.setByte(2, pro.getProID());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
