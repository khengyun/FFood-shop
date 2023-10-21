/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.AdminDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.VoucherDAO;
import Models.Account;
import Models.Admin;
import Models.Food;
import Models.Order;
import Models.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
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
    
    private void doGetVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/admin/voucher/delete")) {
            String[] s = path.split("/");
            byte voucherID = Byte.parseByte(s[s.length - 1]);
            VoucherDAO dao = new VoucherDAO();
            dao.delete(voucherID);
            response.sendRedirect("/admin");
        }
    }

    private void doPostAddFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        byte foodTypeID = Byte.parseByte(request.getParameter("txtFoodTypeID"));
        String foodName = request.getParameter("txtFoodName");
        String foodDescription = (String) request.getParameter("txtFoodDescription");
        BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
        byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
        byte foodRate = Byte.parseByte(request.getParameter("txtFoodRate"));
        byte foodStatus = Byte.parseByte(request.getParameter("txtFoodStatus"));
        String imageURL = (String) request.getAttribute("txtImageURL");

        FoodDAO foodDAO = new FoodDAO();
        Food food = new Food(foodName, foodDescription, foodPrice, foodStatus, foodRate, discountPercent, imageURL, foodTypeID);
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
        String foodDescription = (String) request.getParameter("txtFoodDescription");
        BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
        byte foodRate = Byte.parseByte(request.getParameter("txtFoodRate"));
        byte foodStatus = Byte.parseByte(request.getParameter("txtFoodStatus"));
        byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
        String imageURL = (String) request.getAttribute("txtImageURL");

        FoodDAO foodDAO = new FoodDAO();
        Food food = new Food(foodID, foodName, foodDescription, foodPrice, foodStatus, foodRate, discountPercent, imageURL, foodTypeID);
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

        // Get the string of food IDs from the request
        String[] foodIDs = request.getParameter("foodData").split(",");

        // Convert the strings to numbers
        List<Short> foodIDList = new ArrayList<>();
        for (int i = 0; i < foodIDs.length; i++) {
            foodIDList.add(Short.parseShort(foodIDs[i]));
        }

        // Delete each food item, and count deleted items
        FoodDAO dao = new FoodDAO();
        int count = dao.deleteMultiple(foodIDList);

        // TODO implement a deletion status message after page reload
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
    
    private void doPostAddVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voucherName = (String) request.getParameter("txtvoucher_name");
        byte voucher_discount_percent = Byte.parseByte(request.getParameter("txtAddVoucher_discount_percent"));

        VoucherDAO voucherDAO = new VoucherDAO();
        Voucher voucher = new Voucher(voucherName, voucher_discount_percent);

        int result = voucherDAO.add(voucher);

        if (result == 1) {
            response.sendRedirect("/admin");
            return;
        } else {
            response.sendRedirect("/admin");
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
            response.sendRedirect("/admin");
            return;
        } else {
            response.sendRedirect("/admin");
            return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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

            VoucherDAO voucherDAO = new VoucherDAO();
            List<Voucher> voucherList = voucherDAO.getAllList();

            request.setAttribute("foodList", foodList);
            request.setAttribute("userAccountList", userAccountList);
            request.setAttribute("orderList", orderList);
            request.setAttribute("voucherList", voucherList);
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
        } else if (path.endsWith("/admin/")) {
            response.sendRedirect("/admin");
        } else if (path.startsWith("/admin/food")) {
            doGetFood(request, response);
        } else if (path.startsWith("/admin/user")) {
            doGetUser(request, response);
        } else if (path.startsWith("/admin/voucher")) {
            doGetVoucher(request, response);
        } else {
            // response.setContentType("text/css");
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
            switch (request.getParameter("btnSubmit")) {
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
