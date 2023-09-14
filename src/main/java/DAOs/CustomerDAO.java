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
import Models.Customer;

/**
 *
 * @author Hung
 */
public class CustomerDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public CustomerDAO() {
        conn = DBConnection.DBConnection.getConnection();
    }

    public ResultSet getAll() {
        String sql = "select * from Customer";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Customer getCustomer(int customerID) {
        Customer customer = null;
        try {
            ps = conn.prepareStatement("select * from Customer where customer_id = ?");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_firstname"), rs.getString("customer_lastname"), rs.getString("customer_gender"), rs.getString("customer_phone"), rs.getString("customer_address"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }

    public Customer getCustomer(String lastName, String firstName) {
        Customer customer = null;
        try {
            ps = conn.prepareStatement("select * from Customer where customer_lastname = ? and customer_firstname = ?");
            ps.setString(1, lastName);
            ps.setString(2, firstName);
            rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_firstname"), rs.getString("customer_lastname"), rs.getString("customer_gender"), rs.getString("customer_phone"), rs.getString("customer_address"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }

    public Customer getLatestCustomer() {
        Customer customer = null;
        try {
            ps = conn.prepareStatement("select * from Customer where customer_id = (select max(customer_id) from Customer)");
            rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_firstname"), rs.getString("customer_lastname"), rs.getString("customer_gender"), rs.getString("customer_phone"), rs.getString("customer_address"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }

    public boolean exists(Customer customer) {
        String sql = "select 1 from Customer where "
                + "customer_firstname = ? "
                + "and customer_lastname = ? "
                + "and customer_gender = ?;";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getGender());
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int add(Customer customer) {
        String sql = "insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (?, ?, ?, ?, ?)";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getGender());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());

            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        String sql = "delete from Customer where customer_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Customer customer) {
        String sql = "update Customer set customer_firstname = ?, customer_lastname = ?, customer_gender = ?, customer_phone = ?, customer_address = ? where customer_id = ?";
        int result = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getGender());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getCustomerID());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}