/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Models.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CE171454 Hua Tien Thanh
 */
public class LoginController extends HttpServlet {

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
      out.println("<title>Servlet Login</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
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
    request.getRequestDispatcher("/index.jsp").forward(request, response);
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
    String contextPath = request.getContextPath();
    if (request.getParameter("btnSubmit") != null
            && ((String) request.getParameter("btnSubmit")).equals("Submit")) {
      String email = request.getParameter("txtEmail");
      String password = (String) request.getAttribute("txtPassword");

      Account account = new Account(email, password);
      AccountDAO dao = new AccountDAO();
      boolean success;
      try {
        success = dao.login(account);
      } catch (SQLException ex) {
        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        success = false;
      }
      // Truy xuất URL hiện tại từ session attribute
      HttpSession session = request.getSession();
      String previousUrl = (String) session.getAttribute("previousUrl");
      if (success) {
        account = dao.getAccount(email);
        String accountType = account.getAccountType();
        boolean isRemembered = (request.getParameter("chkRememberMe") != null
                && request.getParameter("chkRememberMe").equals("remember"));
        if (isRemembered) {
          if (accountType.equals("user")) {
            int cAge = 24 * 60 * 60 * 7; // 7 days
            account = dao.getAccount(email);
            String username = account.getUsername();
            username = URLEncoder.encode(username, "UTF-8");
            int userID = account.getAccountID();
            Cookie cUser = new Cookie("user", username);
            cUser.setMaxAge(cAge);
            cUser.setPath("/");
            response.addCookie(cUser);
            Cookie cUserID = new Cookie("userID", String.valueOf(userID));
            cUser.setMaxAge(cAge);
            cUser.setPath("/");
            response.addCookie(cUserID);
            if (previousUrl != null) {
              // Chuyển hướng người dùng về trang hiện tại
              response.sendRedirect(previousUrl);
            } else {
              // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
              response.sendRedirect("/");
            }
          } else {
            int cAge = 24 * 60 * 60 * 7; // 7 days
            account = dao.getAccount(email);
            String username = account.getUsername();
            username = URLEncoder.encode(username, "UTF-8");
            Cookie adminCookie = new Cookie("admin", username);
            adminCookie.setMaxAge(cAge);
            adminCookie.setPath("/");
            response.addCookie(adminCookie);
            response.sendRedirect("/admin");
          }
        } else {
          if (accountType.equals("user")) {
            account = dao.getAccount(email);
            String username = account.getUsername();
            int userID = account.getAccountID();
            session = request.getSession();
            session.setAttribute("user", username);
            session.setAttribute("userID", userID);
            if (previousUrl != null) {
              // Chuyển hướng người dùng về trang hiện tại
              response.sendRedirect(previousUrl);
            } else {
              // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
              response.sendRedirect("/");
            }
          } else {
            account = dao.getAccount(email);
            String username = account.getUsername();
            session = request.getSession();
            session.setAttribute("admin", username);
            response.sendRedirect("/admin");
          }
        }
      } else {
        if (previousUrl != null) {
          // Chuyển hướng người dùng về trang hiện tại
          response.sendRedirect(previousUrl);
        } else {
          // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
          response.sendRedirect("/");
        }
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
