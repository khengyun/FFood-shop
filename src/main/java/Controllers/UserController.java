/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.CustomerDAO;
import DAOs.OrderDAO;
import Models.Account;
import Models.Customer;
import Models.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author ibuyc
 */
public class UserController extends HttpServlet {

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
      out.println("<title>Servlet UserController</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  private void doPostUpdateInfo(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
//    int accountID = Integer.parseInt(request.getParameter("txtAccountID"));
    HttpSession session = request.getSession();
    int accountID = (Integer) session.getAttribute("userID");
    String lastName = request.getParameter("txtLastName");
    String firstName = request.getParameter("txtFirstName");
    String phoneNumber = request.getParameter("txtPhoneNumber");
    String gender = request.getParameter("txtGender");
    String address = request.getParameter("txtAddress");
    int result = 0;

    Customer customer = new Customer(firstName, lastName, gender, phoneNumber, address);
    CustomerDAO customerDAO = new CustomerDAO();
    AccountDAO accountDAO = new AccountDAO();
    Account account = accountDAO.getAccount(accountID);

    // If an Account already has an assigned Customer info (customerID)
    // then
    if (account.getCustomerID() != 0) {
      // Account has an associated Customer -> updates existing Customer
      // Retrieve the customerID of matching existing Customer
      int customerID = customerDAO.getCustomer(lastName, firstName).getCustomerID();
      // which is then used to update the current Customer object
      customer.setCustomerID(customerID);
      result = customerDAO.update(customer);
      if (result != 1) {
        // New Customer failed to be updated
        response.sendRedirect("/user#info");
        return;
      }
      // Customer info update is successful
      response.sendRedirect("/user#info");
      return;
    } else {
      // Account has no associated Customer -> create new Customer then assign it to current Account
      // Note: these 2 procedures should be surrounded by a transaction for safety purpose
      if (customerDAO.exists(customer)) {
        // If new Customer already exists, get that Customer instead
        customer = customerDAO.getCustomer(customer.getLastName(), customer.getFirstName());
        if (customer == null) {
          // Existing Customer failed to be obtained from database
          response.sendRedirect("/user#info");
          return;
        }
        // Proceed to assign the customer to User account if the existing Customer is
        // successfully retrieved

        // Update the customer with the newly retrieved Customer entry (with ID)
        account.setCustomerID(customer.getCustomerID());
        result = accountDAO.update(account);
        if (result != 1) {
          // Either or both Customer insertion and Account update procedures are unsuccessful
          response.sendRedirect("/user#info");
          return;
        }
        // Both procedures are successful
        response.sendRedirect("/user#info");
        return;
      } else {
        // Customer does not already exists -> create new Customer
        result = customerDAO.add(customer);
        if (result != 1) {
          // New Customer failed to be added to database
          response.sendRedirect("/user#info");
          return;
        }
        // Proceed to assign the customer to User account if the new Customer is
        // successfully added

        // Update the customer with the newly added Customer entry (with autogenerated ID)
        customer = customerDAO.getLatestCustomer();
        account.setCustomerID(customer.getCustomerID());
        result = accountDAO.update(account);
        if (result != 1) {
          // Either or both Customer insertion and Account update procedures are unsuccessful
          response.sendRedirect("/user#info");
          return;
        }
        // Both procedures are successful
        response.sendRedirect("/user#info");
        return;
      }
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
      request.setAttribute("tabID", 3);
      response.sendRedirect("/user#account");
      return;
    } else {
      response.sendRedirect("/user#account");
      return;
    }
  }

  private void doGetCancelOrder(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String path = request.getRequestURI();
    String[] s = path.split("/");
    int orderID = Integer.parseInt(s[s.length - 1]);
    OrderDAO orderDAO = new OrderDAO();
    orderDAO.cancelOrder(orderID);
    response.sendRedirect("/user#order");
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
    HttpSession session = request.getSession();
    String path = request.getRequestURI();
    if (path.equals("/user")) {
//<editor-fold defaultstate="collapsed" desc="Get user account info">
      int userID = (Integer) session.getAttribute("userID");
      AccountDAO accountDAO = new AccountDAO();
      Account currentAccount = accountDAO.getAccount(userID);

      request.setAttribute("currentAccount", currentAccount);
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="Get customer and order info if it exists">
      // This info will be used to preload the "Thông tin của tôi" form
      // Default int values are assigned 0 instead of null
      if (currentAccount.getCustomerID() != 0) {
        //<editor-fold defaultstate="collapsed" desc="Get customer info">
        int customerID = currentAccount.getCustomerID();
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomer(customerID);

        request.setAttribute("customer", customer);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Get order info">
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getOrdersFromCustomerList(customerID);
        request.setAttribute("orderList", orderList);
        //</editor-fold>
      }
      //</editor-fold>

      request.getRequestDispatcher("/user.jsp").forward(request, response);                
    } else if (path.equals("/user/")) {
      response.sendRedirect("/user");
    } else if (path.startsWith("/user/orders")) {
      request.setAttribute("tabID", 3);
      response.sendRedirect("/user");
    } else if (path.startsWith("/user/cancel")) {
      doGetCancelOrder(request, response);
      request.setAttribute("tabID", 3);
      response.sendRedirect("/user");
    } else {
      response.sendRedirect("/user");
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
            && (request.getParameter("btnSubmit")).equals("SubmitUpdateInfo")) {
      doPostUpdateInfo(request, response);
    } else if (request.getParameter("btnSubmit") != null
            && (request.getParameter("btnSubmit")).equals("SubmitUpdateUser")) {
      doPostUpdateUser(request, response);
    } else if (request.getParameter("btnSubmit") != null
            && (request.getParameter("btnSubmit")).equals("SubmitCancelOrder")) {
      doGetCancelOrder(request, response);
    } else {
      response.sendRedirect("/user");
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
