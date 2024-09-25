/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.Staff;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public StaffDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from Staff";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Staff> getAllStaff() {
        ResultSet staffRS = this.getAll();
        try {
            List<Staff> staffList = new ArrayList<>();
            while (staffRS.next()) {
                // Selected account is of User type               
                Staff staff = new Staff(
                        staffRS.getByte("staff_id"),
                        staffRS.getString("staff_fullname")
                );              
                staffList.add(staff);               
            }
            return staffList;
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    public Staff getNewStaff() {
        Staff staff = null;
        String sql = "SELECT TOP 1 * FROM Staff ORDER BY staff_id DESC;";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff(rs.getByte("staff_id"),rs.getString("staff_fullname"));
            }
            return staff;
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   

    public Staff getStaff(byte id) {
        Staff staff = null;
        try {
            ps = conn.prepareStatement("select * from Staff where staff_id = ?");
            ps.setByte(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff(rs.getByte("staff_id"), rs.getString("staff_fullname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }

    public int add(Staff staff) {
        String sql = "insert into Staff values (?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, staff.getFullName());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(byte id) {
        int result = 0;
        String sql = "delete from Staff where staff_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setByte(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int deleteMultiple(List<Byte> staffIDs) {
        int result = 0;
        try {
            conn.setAutoCommit(false); // Start transaction
            for (Byte staffID : staffIDs) {
                if (delete(staffID) == 1) {
                    result++; // Count number of successful deletions
                } else {
                    conn.rollback(); // Rollback transaction if deletion fails
                    return 0;
                }
            }
            conn.commit(); // Commit transaction if all deletions succeed
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback(); // Rollback transaction if any exception occurs
            } catch (SQLException rollbackEx) {
                Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            return 0;
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto commit
            } catch (SQLException finalEx) {
                Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, finalEx);
            }
        }
        return result;
    }

    public int update(Staff staff) {
        String sql = "update Staff set staff_fullname = ? where staff_id = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, staff.getFullName());
            ps.setByte(2, staff.getStaffID());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
