package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Models.Voucher;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class VoucherDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public VoucherDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from Voucher";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Voucher> getAllList() {
        ResultSet voucherRS = this.getAll();
        List<Voucher> voucherList = new ArrayList<>();
        try {
            while (voucherRS.next()) {
                Voucher voucher = new Voucher(voucherRS.getString("voucher_id"),
                        voucherRS.getString("voucher_code"),
                        voucherRS.getFloat("voucher_value"),
                        voucherRS.getInt("voucher_quantity"),
                        voucherRS.getInt("voucher_condition"),
                        voucherRS.getDate("voucher_exp_date"),
                        voucherRS.getString("voucher_status"));
                voucherList.add(voucher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voucherList;
    }

    public int add(Voucher voucher) {
        String sql = "insert into Voucher (voucher_ID, voucher_code, voucher_value, voucher_quantity, voucher_condition, voucher_exp_date, voucher_status) values (?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, voucher.getVoucherID());
            ps.setString(2, voucher.getVoucherCode());
            ps.setFloat(3, voucher.getVoucherValue());
            ps.setInt(4, voucher.getVoucherQuantity());
            ps.setInt(5, voucher.getVoucherCondition());
            java.util.Date date = new java.util.Date();
            date = voucher.getVoucherExpDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(6, sqlDate);
            ps.setString(7, voucher.getVoucherStatus());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(int voucherID) {
        int result = 0;
        String sql = "delete from Voucher where voucher_ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, voucherID);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Voucher voucher) {
        String sql = "update Voucher set voucher_ID = ?, voucher_code = ?, voucher_value = ?, voucher_quantity = ?, voucher_condition = ?, voucher_exp_date = ?, voucher_status = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, voucher.getVoucherID());
            ps.setString(2, voucher.getVoucherCode());
            ps.setFloat(3, voucher.getVoucherValue());
            ps.setInt(4, voucher.getVoucherQuantity());
            ps.setInt(5, voucher.getVoucherCondition());
            java.util.Date date = new java.util.Date();
            date = voucher.getVoucherExpDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(6, sqlDate);
            ps.setString(7, voucher.getVoucherStatus());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
