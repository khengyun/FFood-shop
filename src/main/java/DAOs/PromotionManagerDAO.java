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
