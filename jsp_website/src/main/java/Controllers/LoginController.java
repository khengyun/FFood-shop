/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Models.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.sql.SQLException;
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
        
        // Check if the form is submitted
        if (request.getParameter("btnSubmit") != null
                && ((String) request.getParameter("btnSubmit")).equals("Submit")) {
            String email = request.getParameter("txtEmail");
            String password = (String) request.getAttribute("txtPassword");
            
             // Validate the login credentials
            if (!valid.loginValidation(email,password)){
                session.setAttribute("toastMessage", "error-login-credentials");
                response.sendRedirect("/"); 
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
                System.err.println("A database issue occurred. Your login was cancelled");
            }
            //found the accoutn in database
            if (success) {
                System.out.println("Successful login");
                session.setAttribute("isSuccessful", success);
                Account account = dao.getAccount(email);
                String accountType = account.getAccountType();
                boolean isRemembered = (request.getParameter("chkRememberMe") != null
                        && request.getParameter("chkRememberMe").equals("remember"));
                
                //handle checked the remember button
                if (isRemembered) {
                    int cAge = 24 * 60 * 60 * 7; // 7 days
                    // Handle different account types
                    if (accountType.equals("user")) {                       
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        username = URLEncoder.encode(username, "UTF-8");
                        //add username cookie
                        Cookie cUser = new Cookie("user", username);
                        cUser.setMaxAge(cAge);
                        cUser.setPath("/");
                        response.addCookie(cUser);
                        //add user id cookie
                        int userID = account.getAccountID();
                        Cookie cUserID = new Cookie("userID", String.valueOf(userID));
                        cUserID.setMaxAge(cAge);
                        cUserID.setPath("/");
                        response.addCookie(cUserID);
                        
                        response.sendRedirect("/");
                    } else if (accountType.equals("admin")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        username = URLEncoder.encode(username, "UTF-8");
                        byte adminID = account.getAdminID();
                        session.setAttribute("adminID", adminID);
                        //add admin cookie
                        Cookie adminCookie = new Cookie("admin", username);
                        adminCookie.setMaxAge(cAge);
                        adminCookie.setPath("/");
                        response.addCookie(adminCookie);
                        //add admin id cookie
                        Cookie adminIDCookie = new Cookie("adminID", Byte.toString(adminID));
                        adminIDCookie.setMaxAge(cAge);
                        adminIDCookie.setPath("/");      
                        response.addCookie(adminIDCookie);                       
                        response.sendRedirect("/admin");
                    } else if (accountType.equals("staff")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        byte staffID = account.getStaffID();                       
                        session.setAttribute("staffID", staffID);
                        username = URLEncoder.encode(username, "UTF-8");
                        
                        //add staff cookie
                        Cookie staffCookie = new Cookie("staff", username);
                        staffCookie.setMaxAge(cAge);
                        staffCookie.setPath("/");
                        response.addCookie(staffCookie);
                        //add staff id cookie
                        Cookie staffIDCookie = new Cookie("staffID", Byte.toString(staffID));                                             
                        staffIDCookie.setMaxAge(cAge);
                        staffIDCookie.setPath("/");                                               
                        response.addCookie(staffIDCookie);
                        
                        response.sendRedirect("/staff");
                        
                    } else if (accountType.equals("promotionManager")) {
                        account = dao.getAccount(email);
                        String username = account.getUsername();
                        username = URLEncoder.encode(username, "UTF-8");
                        //add promotionManager cookie
                        Cookie promotionManagerCookie = new Cookie("promotionManager", username);
                        promotionManagerCookie.setMaxAge(cAge);
                        promotionManagerCookie.setPath("/");
                        response.addCookie(promotionManagerCookie);
                        response.sendRedirect("/promotionManager");
                    } else {
                      session.setAttribute("toastMessage", "error-login");
                        response.sendRedirect("/");
                    }
                //handle not check remember button
                } else {
                    // Handle different account types
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
                      session.setAttribute("toastMessage", "error-login");
                        response.sendRedirect("/");
                    }
                }
            //not found the accoutn in database
            } else {
                System.err.println("Incorrect login credentials");
                session.setAttribute("toastMessage", "error-login-credentials");
                response.sendRedirect("/");
            }                   
        }
    }
}