/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import DAOs.AccountDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.VoucherDAO;
import Models.Account;
import Models.Food;
import Models.Order;
import Models.Voucher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class PromotionManagerController extends HttpServlet {
   
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PromotionManagerController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PromotionManagerController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
    
    private void doGetVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/promotionManager/voucher/delete")) {
            String[] s = path.split("/");
            byte voucherID = Byte.parseByte(s[s.length - 1]);
            VoucherDAO dao = new VoucherDAO();
            dao.delete(voucherID);
            response.sendRedirect("/promotionManager");
        }
        response.sendRedirect("/promotionManager");
    }
    
    private void doPostAddVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String voucherName = (String) request.getParameter("txtvoucher_name");
        byte voucher_discount_percent = Byte.parseByte(request.getParameter("txtAddVoucher_discount_percent"));

        VoucherDAO voucherDAO = new VoucherDAO();
        Voucher voucher = new Voucher(voucherName, voucher_discount_percent);

        int result = voucherDAO.add(voucher);

        if (result == 1) {
            response.sendRedirect("/promotionManager");
            return;
        } else {
            response.sendRedirect("/promotionManager");
            return;
        }
    }
    
     private void doPostUpdateVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        byte voucherID =  Byte.parseByte(request.getParameter("txtvoucher_id"));      
        String voucherName = (String) request.getParameter("txtvoucher_name");
        byte voucher_discount_percent = Byte.parseByte(request.getParameter("txtvoucher_discount_percent"));

        VoucherDAO voucherDAO = new VoucherDAO();
        Voucher voucher = new Voucher(voucherName, voucher_discount_percent);
        voucher.setVoucherID(voucherID);

        int result = voucherDAO.update(voucher);

        if (result == 1) {
            response.sendRedirect("/promotionManager");
            return;
        } else {
            response.sendRedirect("/promotionManager");
            return;
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
        String path = request.getRequestURI();
        if (path.endsWith("/promotionManager")) {
           
            VoucherDAO voucherDAO = new VoucherDAO();
            List<Voucher> voucherList = voucherDAO.getAllList();

            request.setAttribute("voucherList", voucherList);
            request.getRequestDispatcher("/promotionManager.jsp").forward(request, response);
        } else if (path.endsWith("/promotionManager/")) {
            response.sendRedirect("/promotionManager");
        } else if (path.startsWith("/promotionManager/voucher")) {
            doGetVoucher(request, response);
        } else {
            // response.setContentType("text/css");
            request.getRequestDispatcher("/promotionManager.jsp").forward(request, response);
        }
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
        if (request.getParameter("btnSubmit") != null) {
            switch (request.getParameter("btnSubmit")) {               
                case "SubmitAddVoucher":
                    doPostAddVoucher(request, response);
                    break;
                case "SubmitUpdateVoucher":
                    doPostUpdateVoucher(request, response);
                    break;
                default:
                    break;
            }
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
