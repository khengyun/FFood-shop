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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EmailVerifyController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerifyOTPController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyOTPController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      if (request.getParameter("cancel") != null && request.getParameter("cancel").equals("true")) {
        HttpSession session = request.getSession();    
        session.removeAttribute("otp");
        session.removeAttribute("email");
        session.removeAttribute("type_otp");
        session.removeAttribute("triggerOTP");
      }
      response.sendRedirect("/");
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int value = Integer.parseInt(request.getParameter("otp"));
        HttpSession session = request.getSession();
        int otp = 0;
        if (session.getAttribute("otp") != null){
            otp = (int) session.getAttribute("otp");
        }
        
        if (otp == 0) {
            session.setAttribute("toastMessage", "error-verify-email");
            response.sendRedirect("/");
            return;
        }
        AccountDAO accountDAO = new AccountDAO();
        
        if (value == otp) {
            session.removeAttribute("otp");
            String type_otp = (String) session.getAttribute("type_otp");
            if (type_otp.equals("sign_up")){
                Account account = (Account) session.getAttribute("registerUser");               
                int result = accountDAO.add(account);
                session.removeAttribute("registerUser");
                if (result == 1) {
                    session.setAttribute("toastMessage", "success-register");
                    response.sendRedirect("/");
                } else {
                    session.setAttribute("toastMessage", "error-register");
                    response.sendRedirect("/");
                }
            } else if (type_otp.equals("forget")){
                String email = (String) session.getAttribute("email");
                Account account = new Account(email,"user");
                session.setAttribute("account", account);
                session.setAttribute("triggerChangePassword", "true");
                response.sendRedirect("/");
            }
        } else {
            session.setAttribute("toastMessage", "error-wrong-otp");
            session.setAttribute("triggerOTP", true);
            response.sendRedirect("/");  
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
