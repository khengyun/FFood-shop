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
import Models.Cart;

/**
 *
 * @author Hung
 */
public class CartDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;

  public CartDAO() {
    conn = DBConnection.DBConnection.getConnection();
  }

  public ResultSet getAll() {
    String sql = "select * from Cart";
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      return rs;
    } catch (SQLException ex) {
      Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public Cart getCart(int cartID) {
    Cart cart = null;
    try {
      ps = conn.prepareStatement("select * from Cart where cart_id = ?");
      ps.setInt(1, cartID);
      rs = ps.executeQuery();
      if (rs.next()) {
        cart = new Cart(rs.getInt("cart_id"), rs.getInt("customer_id"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return cart;
  }

  public int add(Cart cart) {
    String sql = "insert into Cart (customer_id) values (?)";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, cart.getUserId());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }

  public int delete(int cartID) {
    int ketqua = 0;
    String sql = "delete from Cart where cart_id = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, cartID);
      ketqua = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return ketqua;
  }
  public int getLatestCartID() {
  int cartid=0;
    
    try {
      ps = conn.prepareStatement("select * from Cart where cart_id = (select max(cart_id) from Cart)");
      rs = ps.executeQuery();
      
        System.out.println(rs);
      if (rs.next()) {
         
          cartid = rs.getInt("cart_id");
         
      }
    } catch (SQLException ex) {
      Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return cartid;
  }

  public int update(Cart cart) {
    String sql = "update Cart set customer_id = ? where cart_id = ?";
    int result = 0;
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, cart.getUserId());
      ps.setInt(2, cart.getId());
      result = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
   
           public static void main(String[] args) {
        CartDAO cartDAO = new CartDAO();

        // Tạo một đối tượng Cart mới
        Cart cart = new Cart(1,1); // Ví dụ: customer_id = 1

        // Thử thêm Cart vào cơ sở dữ liệu
        int result = cartDAO.add(cart);

        if (result > 0) {
            System.out.println("Thêm Cart thành công. Số hàng bị ảnh hưởng: " + result);
        } else {
            System.out.println("Thêm Cart thất bại.");
        }
    }      
          
}
    

