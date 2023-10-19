/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Models.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sun.security.jgss.GSSCaller;
import sun.security.jgss.GSSUtil;

/**
 *
 * @author Hung
 */
public class SignUpController extends HttpServlet {

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
        processRequest(request, response);
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
        String username = request.getParameter("txtAccountUsername");
        String email = request.getParameter("txtAccountEmail");
        String pass = (String) request.getAttribute("txtAccountPassword");
        // Truy xuất URL hiện tại từ session attribute
        HttpSession session = request.getSession();
        String previousUrl = (String) session.getAttribute("previousUrl");
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, pass, "user");
        try {
            if (accountDAO.login(account)) {
                if (previousUrl != null) {
                    // Chuyển hướng người dùng về trang hiện tại
                    response.sendRedirect(previousUrl);
                } else {
                    // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
                    response.sendRedirect("/");
                }
            } else {
                //Account a = dao.login("user");
                int result = accountDAO.add(account);
                if (result == 1) {
                    //Lưu thành công
                    if (previousUrl != null) {
                        // Chuyển hướng người dùng về trang hiện tại
                        response.sendRedirect(previousUrl);
                    } else {
                        // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
                        response.sendRedirect("/");
                    }
                } else {
                    //Lưu không thành công
                    if (previousUrl != null) {
                        // Chuyển hướng người dùng về trang hiện tại
                        response.sendRedirect(previousUrl);
                    } else {
                        // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
                        response.sendRedirect("/");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
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
