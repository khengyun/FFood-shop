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
import Validation.ValidationUtils;

public class LoginController extends HttpServlet {

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
        ValidationUtils valid = new ValidationUtils();
        HttpSession session = request.getSession();
        if (request.getParameter("btnSubmit") != null
                && ((String) request.getParameter("btnSubmit")).equals("Submit")) {
            String email = request.getParameter("txtEmail");
            String password = (String) request.getAttribute("txtPassword");
            
            if (!valid.loginValidation(email,password)){
                session.setAttribute("isSuccessful", false);
                response.sendRedirect("/home#failure_login_info"); 
                return;
            }
            
            Account loginAccount = new Account(email, password);
            AccountDAO dao = new AccountDAO();
            boolean success;
            try {
                success = dao.login(loginAccount);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
            }
            
            if (success) {
                session.setAttribute("isSuccessful", success);
                Account account = dao.getAccount(email);
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
                        cUserID.setMaxAge(cAge);
                        cUser.setPath("/");
                        cUserID.setPath("/");
                        response.addCookie(cUserID);
                        response.sendRedirect("/");
                    } else if (accountType.equals("admin")) {
                        int cAge = 24 * 60 * 60 * 7; // 7 days
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        username = URLEncoder.encode(username, "UTF-8");
                        byte adminID = account.getAdminID();
                        session.setAttribute("adminID", adminID);
                        Cookie adminCookie = new Cookie("admin", username);
                        Cookie adminIDCookie = new Cookie("adminID", Byte.toString(adminID));
                        adminCookie.setMaxAge(cAge);
                        adminCookie.setPath("/");
                        adminIDCookie.setMaxAge(cAge);
                        adminIDCookie.setPath("/");
                        response.addCookie(adminCookie);
                        response.addCookie(adminIDCookie);
                        response.sendRedirect("/admin");
                    } else if (accountType.equals("staff")) {
                        int cAge = 24 * 60 * 60 * 7; // 7 days
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        byte staffID = account.getStaffID();
                        System.out.println("staffID " + staffID);
                        session.setAttribute("staffID", staffID);
                        username = URLEncoder.encode(username, "UTF-8");
                        Cookie staffCookie = new Cookie("staff", username);
                        Cookie staffIDCookie = new Cookie("staffID", Byte.toString(staffID));                       
                        staffCookie.setMaxAge(cAge);
                        staffCookie.setPath("/");
                        staffIDCookie.setMaxAge(cAge);
                        staffIDCookie.setPath("/");                        
                        response.addCookie(staffCookie);
                        response.addCookie(staffIDCookie);
                        response.sendRedirect("/staff");
                    } else if (accountType.equals("promotionManager")) {
                        int cAge = 24 * 60 * 60 * 7; // 7 days
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        username = URLEncoder.encode(username, "UTF-8");
                        Cookie promotionManagerCookie = new Cookie("promotionManager", username);
                        promotionManagerCookie.setMaxAge(cAge);
                        promotionManagerCookie.setPath("/");
                        response.addCookie(promotionManagerCookie);
                        response.sendRedirect("/promotionManager");
                    }
                } else {
                    if (accountType.equals("user")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        int userID = account.getAccountID();
                        session = request.getSession();
                        session.setAttribute("user", username);
                        session.setAttribute("userID", userID);
                        response.sendRedirect("/");
                    } else if (accountType.equals("admin")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        session = request.getSession();
                        byte adminID = account.getAdminID();
                        session.setAttribute("adminID", adminID);
                        session.setAttribute("admin", username);
                        response.sendRedirect("/admin");
                    } else if (accountType.equals("staff")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        session = request.getSession();
                        byte staffID = account.getStaffID();
                        session.setAttribute("staffID", staffID);
                        session.setAttribute("staff", username);
                        response.sendRedirect("/staff");
                    } else if (accountType.equals("promotionManager")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        session = request.getSession();
                        session.setAttribute("promotionManager", username);
                        response.sendRedirect("/promotionManager");
                    } else {
                        response.sendRedirect("/home#failure_login");
                    }
                }
            } else {
                session.setAttribute("isSuccessful", success);
                response.sendRedirect("/home#failure_login_info");
            }                   
        }
    }
}
