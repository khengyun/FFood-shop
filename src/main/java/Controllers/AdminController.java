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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
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
      request.setAttribute("tabID", 3);
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

  private void doPostDeleteFood(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Get the JSON string from the request body
    StringBuilder sb = new StringBuilder();
    String line;
    try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
    } catch (Exception e) { e.printStackTrace(); }

    String jsonString = sb.toString();

    // Convert the JSON string to a List of IDs
    Type listType = new TypeToken<ArrayList<Short>>(){}.getType();
    List<Short> foodIDs = new Gson().fromJson(jsonString, listType);

    // Delete each food item, and count deleted items
    FoodDAO dao = new FoodDAO();
    int count = dao.deleteMultiple(foodIDs);

    // Prepare the JSON object to send as a response
    JsonObject jsonResponse = new JsonObject();

    /* Response message: count of successful deletions.
    This number will be handled in client side to generate dynamic status message to users
    based on their configured language. */
    if (count > 0) {
      jsonResponse.addProperty("status", "success");
      jsonResponse.addProperty("message", count);
    } else {
      jsonResponse.addProperty("status", "failure");
      jsonResponse.addProperty("message", count);
    }

    // Set the response content type to JSON
    response.setContentType("application/json");
    // Get the PrintWriter object from response to write the JSON object to the output stream      
    PrintWriter out = response.getWriter();
    // Convert the JSON object to a string and write it to the response stream
    out.print(jsonResponse.toString());
    out.flush();

    // Redirect or forward to another page if necessary
    request.setAttribute("tabID", 3);
    response.sendRedirect("/admin");
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
    if (request.getParameter("btnSubmit") != null) {
      switch(request.getParameter("btnSubmit")) {
        case "SubmitAddFood":
          doPostAddFood(request, response);
          break;
        case "SubmitUpdateFood":
          doPostUpdateFood(request, response);
          break;
        case "SubmitDeleteFood":
          doPostDeleteFood(request, response);
          break;
        case "SubmitAddUser":
          doPostAddUser(request, response);
          break;
        case "SubmitUpdateUser":
          doPostUpdateUser(request, response);
          break;
        default:
          break;
      }
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
