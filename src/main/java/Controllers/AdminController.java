/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import Models.Account;
import Models.Food;
import Models.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author CE171454 Hua Tien Thanh
 */
public class AdminController extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try ( PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet AdminController</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  private void doGetUser(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String path = request.getRequestURI();
    if (path.startsWith("/admin/user/delete")) {
      String[] s = path.split("/");
      int accountID = Integer.parseInt(s[s.length - 1]);
      AccountDAO dao = new AccountDAO();
      dao.delete(accountID);
      response.sendRedirect("/admin");
    }
  }

  private void doGetFood(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String path = request.getRequestURI();
    if (path.startsWith("/admin/food/delete")) {
      String[] s = path.split("/");
      short foodID = Short.parseShort(s[s.length - 1]);
      FoodDAO dao = new FoodDAO();
      dao.delete(foodID);
      response.sendRedirect("/admin");
    }
  }

  private void doPostAddFood(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    byte foodTypeID = Byte.parseByte(request.getParameter("txtFoodTypeID"));
    String foodName = request.getParameter("txtFoodName");
    BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
    byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
    String imageURL = (String) request.getAttribute("txtImageURL");

    FoodDAO foodDAO = new FoodDAO();
    Food food = new Food(foodName, foodPrice, discountPercent, imageURL, foodTypeID);
    int result = foodDAO.add(food);

    if (result == 1) {
      response.sendRedirect("/admin");
      return;
    } else {
      response.sendRedirect("/admin");
      return;
    }
  }

  private void doPostUpdateFood(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    short foodID = Short.parseShort(request.getParameter("txtFoodID"));
    byte foodTypeID = Byte.parseByte(request.getParameter("txtFoodTypeID"));
    String foodName = request.getParameter("txtFoodName");
    BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
    byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
    String imageURL = (String) request.getAttribute("txtImageURL");

    FoodDAO foodDAO = new FoodDAO();
    Food food = new Food(foodID, foodName, foodPrice, discountPercent, imageURL, foodTypeID);
    int result = foodDAO.update(food);

    if (result == 1) {
      response.sendRedirect("/admin");
      return;
    } else {
      response.sendRedirect("/admin");
      return;
    }
  }

  private void doPostAddUser(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String username = request.getParameter("txtAccountUsername");
    String email = request.getParameter("txtEmail");
    String password = (String) request.getAttribute("txtAccountPassword");

    AccountDAO accountDAO = new AccountDAO();
    Account account = new Account(username, email, password, "user");
    
    int result = accountDAO.add(account);

    if (result == 1) {
      response.sendRedirect("/admin");
      return;
    } else {
      response.sendRedirect("/admin");
      return;
    }
  }

  private void doPostUpdateUser(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    int accountID = Integer.parseInt(request.getParameter("txtAccountID"));
    String username = request.getParameter("txtAccountUsername");
    String email = request.getParameter("txtEmail");
    String password = (String) request.getAttribute("txtAccountPassword");

    AccountDAO accountDAO = new AccountDAO();
    Account account = new Account(username, email, password, "user");
    account.setAccountID(accountID);
    
    int result = accountDAO.update(account);

    if (result == 1) {
      response.sendRedirect("/admin");
      return;
    } else {
      response.sendRedirect("/admin");
      return;
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String path = request.getRequestURI();
    if (path.endsWith("/admin")) {
      FoodDAO foodDAO = new FoodDAO();
      List<Food> foodList = foodDAO.getAllList();
      AccountDAO accountDAO = new AccountDAO();
      List<Account> userAccountList = accountDAO.getAllUser();
      OrderDAO orderDAO = new OrderDAO();
      List<Order> orderList = orderDAO.getAllList();

      request.setAttribute("foodList", foodList);
      request.setAttribute("userAccountList", userAccountList);
      request.setAttribute("orderList", orderList);
      request.getRequestDispatcher("/admin.jsp").forward(request, response);
    } else if (path.endsWith("/admin/")) {
      response.sendRedirect("/admin");
    } else if (path.startsWith("/admin/food")) {
      doGetFood(request, response);
    } else if (path.startsWith("/admin/user")) {
      doGetUser(request, response);
    } else {
      //response.setContentType("text/css");
      request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    if (request.getParameter("btnSubmit") != null
            && (request.getParameter("btnSubmit")).equals("SubmitAddFood")) {
      doPostAddFood(request, response);
    }

    if (request.getParameter("btnSubmit") != null
            && (request.getParameter("btnSubmit")).equals("SubmitUpdateFood")) {
      doPostUpdateFood(request, response);
    }

    if (request.getParameter("btnSubmit") != null
            && (request.getParameter("btnSubmit")).equals("SubmitAddUser")) {
      doPostAddUser(request, response);
    }

    if (request.getParameter("btnSubmit") != null
            && (request.getParameter("btnSubmit")).equals("SubmitUpdateUser")) {
      doPostUpdateUser(request, response);
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
