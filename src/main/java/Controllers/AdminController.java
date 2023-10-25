/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.AdminDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.PromotionManagerDAO;
import DAOs.StaffDAO;
import DAOs.VoucherDAO;
import Models.Account;
import Models.Admin;
import Models.Food;
import Models.Order;
import Models.PromotionManager;
import Models.Role;
import Models.Staff;
import Models.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
            int result = dao.delete(accountID);
            
            if (result == 1) {
                response.sendRedirect("/admin#success_delete_user");
            } else {
                response.sendRedirect("/admin#failure_delete_user");
            }
            
        }
    }

    private void doGetFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/admin/food/delete")) {
            String[] s = path.split("/");
            short foodID = Short.parseShort(s[s.length - 1]);
            FoodDAO dao = new FoodDAO();
            int result = dao.delete(foodID);
            request.setAttribute("tabID", 3);
            if (result == 1) {
                response.sendRedirect("/admin#success_delete_food");
            } else {
                response.sendRedirect("/admin#failure_delete_food");
            }
        }
    }

    private void doGetVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/admin/voucher/delete")) {
            String[] s = path.split("/");
            byte voucherID = Byte.parseByte(s[s.length - 1]);
            VoucherDAO dao = new VoucherDAO();
            int result = dao.delete(voucherID);
            if (result == 1) {
                response.sendRedirect("/admin#success_delete_voucher");
            } else {
                response.sendRedirect("/admin#failure_delete_voucher");
            }
        }
    }

    private void doGetList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodDAO foodDAO = new FoodDAO();
        List<Food> foodList = foodDAO.getAllList();

        AccountDAO accountDAO = new AccountDAO();
        List<Account> userAccountList = accountDAO.getAllUser();

        StaffDAO staffDAO = new StaffDAO();
        PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
        
        List<Account> accountList = accountDAO.getAllRole();
        List<Staff> StaffList = staffDAO.getAllStaff();
        List<PromotionManager> PromotionManagerList = promotionManagerDAO.getAllPromotionManager();

        List<Role> roleList = new ArrayList<>();
        for (Account a : accountList) {
            if (a.getAccountType().equals("staff")) {
                String fullname = "";
                for (Staff s : StaffList) {
                    if (s.getStaffID() == a.getStaffID()) {
                        fullname = s.getFullName();
                        Role newRole = new Role(a.getAccountID(), a.getStaffID(), a.getUsername(), fullname, a.getEmail(), a.getAccountType());
                        roleList.add(newRole);
                        break;
                    }
                }
            } else if (a.getAccountType().equals("promotionManager")) {
                String fullname = "";
                for (PromotionManager p : PromotionManagerList) {
                    if (p.getProID() == a.getProID()) {
                        fullname = p.getFullName();
                        Role newRole = new Role(a.getAccountID(), a.getProID(), a.getUsername(), fullname, a.getEmail(), a.getAccountType());
                        roleList.add(newRole);
                        break;

                    }
                }
            }
        }
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllList();

        VoucherDAO voucherDAO = new VoucherDAO();
        List<Voucher> voucherList = voucherDAO.getAllList();
        request.setAttribute("foodList", foodList);
        request.setAttribute("userAccountList", userAccountList);
        request.setAttribute("roleList", roleList);
        request.setAttribute("orderList", orderList);
        request.setAttribute("voucherList", voucherList);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
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
        if (foodDAO.getFood(foodName) != null){
            response.sendRedirect("/admin#failure_add_food_exist");
            return;
        }
        int result = foodDAO.add(food);

        if (result == 1) {
            response.sendRedirect("/admin#success_add_food");
            return;
        } else {
            response.sendRedirect("/admin#failure_add_food");
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
            response.sendRedirect("/admin#success_update_food");
            return;
        } else {
            response.sendRedirect("/admin#failure_update_food");
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
        int result = dao.deleteMultiple(foodIDList);
        
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        request.setAttribute("tabID", 3);
        
        if (result == 1) {
            response.sendRedirect("/admin#success_delete_food");
            return;
        } else {
            response.sendRedirect("/admin#failure_delete_food");
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
        
        if (accountDAO.getAccount(email) != null){
            response.sendRedirect("/admin#failure_add_user_exist");
            return;
        }
        
        int result = accountDAO.add(account);

        if (result == 1) {
            response.sendRedirect("/admin#success_add_user");
            return;
        } else {
            response.sendRedirect("/admin#failure_add_user");
            return;
        }
    }

    private void doPostAddRole(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("txtAccountUsername");
        String fullname = request.getParameter("txtAccountFullname");
        String email = request.getParameter("txtEmail");
        String role = request.getParameter("txtAccountRole");
        String password = (String) request.getAttribute("txtAccountPassword");
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, password, role);
        if (accountDAO.getAccount(email) != null){
            response.sendRedirect("/admin#failure_add_role_exist");
            return;
        }
        
        if (role.equals("staff")) {
            Staff newstaff = new Staff(fullname);
            StaffDAO staffDAO = new StaffDAO();
            int result = staffDAO.add(newstaff);

            if (result == 1) {
                account.setStaffID(staffDAO.getNewStaff().getStaffID());
                int result1 = accountDAO.add(account);
                if (result1 == 1) {
                    response.sendRedirect("/admin#success_add_role");
                    return;
                } else {
                    response.sendRedirect("/admin#failure_add_role");
                    return;
                }
            } else {
                response.sendRedirect("/admin#failure_add_role");
                return;
            }
        } else if (role.equals("promotionManager")) {
            PromotionManager newPromotionManager = new PromotionManager(fullname);
            PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
            int result = promotionManagerDAO.add(newPromotionManager);

            if (result == 1) {
                account.setProID(promotionManagerDAO.getNewPromotionManager().getProID());
                int result1 = accountDAO.add(account);
                if (result1 == 1) {
                    response.sendRedirect("/admin#success_add_role");
                    return;
                } else {
                    response.sendRedirect("/admin#failure_add_role");
                    return;
                }
            } else {
                response.sendRedirect("/admin#failure_add_role");
                return;
            }
        }
    }
    
    private void doPostUpdateRole(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer accountID = Integer.parseInt(request.getParameter("txtAccountID"));
        Byte roleID = Byte.parseByte(request.getParameter("txtRoleID"));
        String username = request.getParameter("txtAccountUsername");
        String fullname = request.getParameter("txtAccountFullname");
        String email = request.getParameter("txtEmail");
        String role = request.getParameter("txtAccountRole");
        String password = (String) request.getAttribute("txtAccountPassword");
        
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, password, role);
        account.setAccountID(accountID);
        if (role.equals("staff")) {
            Staff updatestaff = new Staff(roleID,fullname);
            StaffDAO staffDAO = new StaffDAO();
            int result = staffDAO.update(updatestaff);

            if (result == 1) {
                account.setStaffID(roleID);
                int result1 = accountDAO.update(account);
                System.out.println(result1);
                if (result1 == 1) {
                    response.sendRedirect("/admin#success_update_role");
                    return;
                } else {
                    response.sendRedirect("/admin#success_update_role");
                    return;
                }
            } else {
                response.sendRedirect("/admin#failure_update_role");
                return;
            }
        } else if (role.equals("promotionManager")) {
            PromotionManager newPromotionManager = new PromotionManager(roleID,fullname);
            PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
            int result = promotionManagerDAO.update(newPromotionManager);

            if (result == 1) {
                account.setProID(roleID);
                int result1 = accountDAO.add(account);
                System.out.println(result1);

                if (result1 == 1) {
                    response.sendRedirect("/admin#success_update_role");
                    return;
                } else {
                    response.sendRedirect("/admin#success_update_role");
                    return;
                }
            } else {
                response.sendRedirect("/admin#failure_update_role");
                return;
            }
        }
    }
    
    private void doPostDeleteRole(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the string of food IDs from the request
        String[] accountIDs = request.getParameter("accountData").split(",");
        String[] roleIDs = request.getParameter("roleData").split(",");
        String[] temp1IDs = request.getParameter("temp1Data").split(",");
        String[] temp2IDs = request.getParameter("temp2Data").split(",");
        
        
        
        // Convert the strings to numbers
        List<Byte> roleIDList = new ArrayList<>();
        for (int i = 0; i < roleIDs.length; i++) {
            roleIDList.add(Byte.parseByte(roleIDs[i]));
        }
        
//        // Convert the strings to numbers
        List<Byte> accountIDList = new ArrayList<>();
        for (int i = 0; i < accountIDs.length; i++) {
            accountIDList.add(Byte.parseByte(accountIDs[i]));
        }
//        
//        // Convert the strings to numbers
        List<Byte> StaffIDList = new ArrayList<>();
        for (int i = 0; i < temp1IDs.length; i++) {
            StaffIDList.add(Byte.parseByte(temp1IDs[i]));
        }
        List<Byte> ProIDList = new ArrayList<>();
        for (int i = 0; i < temp2IDs.length; i++) {
            ProIDList.add(Byte.parseByte(temp2IDs[i]));
        }
        
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        request.setAttribute("tabID", 3);
        // Delete each food item, and count deleted items
        AccountDAO accountDAO = new AccountDAO();
        int result1 = accountDAO.deleteMultiple(accountIDList);
        int result2 = 0;
        if (result1 == 1) {
            if (StaffIDList.size() != 0) {
                StaffDAO staffDAO = new StaffDAO();
                result2 = staffDAO.deleteMultiple(StaffIDList);
                
            } else {
                PromotionManagerDAO proDAO = new PromotionManagerDAO();
                result2 = proDAO.deleteMultiple(ProIDList);
            }
            
            if (result2 == 1){
                response.sendRedirect("/admin#success_delete_role");
            } else {
                response.sendRedirect("/admin#failure_delete_role");
            }
            
        } else {
            response.sendRedirect("/admin#failure_delete_role");
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
            response.sendRedirect("/admin#success_update_user");
            return;
        } else {
            response.sendRedirect("/admin#failure_update_user");
            return;
        }
    }

    private void doPostAddVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voucherName = (String) request.getParameter("txtvoucher_name");
        String voucherCode = (String) request.getParameter("txtvoucher_code");
        Byte voucher_discount_percent = Byte.parseByte(request.getParameter("txtvoucher_discount_percent"));
        Byte voucher_quantity = Byte.parseByte(request.getParameter("txtvoucher_quantity"));
        Byte voucher_status = Byte.parseByte(request.getParameter("txtvoucher_status"));
        String datetimelocal = request.getParameter("txtvoucher_date");
        Timestamp datetime = Timestamp.valueOf(datetimelocal.replace("T", " ") + ":00");

        VoucherDAO voucherDAO = new VoucherDAO();
        Voucher voucher = new Voucher(voucherName, voucherCode, voucher_discount_percent, voucher_quantity, voucher_status, datetime);
        
        if (voucherDAO.getVoucher(voucherName) != null) {
            response.sendRedirect("/admin#failure_add_voucher_exist");
            return;
        }
        
        int result = voucherDAO.add(voucher);

        if (result == 1) {
            response.sendRedirect("/admin#success_add_voucher");
            return;
        } else {
            response.sendRedirect("/admin#failure_add_voucher");
            return;
        }
    }

    private void doPostUpdateVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Byte voucherID = Byte.parseByte(request.getParameter("txtvoucher_id"));
        String voucherName = (String) request.getParameter("txtvoucher_name");
        String voucherCode = (String) request.getParameter("txtvoucher_code");
        Byte voucher_discount_percent = Byte.parseByte(request.getParameter("txtvoucher_discount_percent"));
        Byte voucher_quantity = Byte.parseByte(request.getParameter("txtvoucher_quantity"));
        Byte voucher_status = Byte.parseByte(request.getParameter("txtvoucher_status"));
        String datetimelocal = request.getParameter("txtvoucher_date");
        Timestamp datetime = Timestamp.valueOf(datetimelocal.replace("T", " ") + ":00");

        VoucherDAO voucherDAO = new VoucherDAO();
        Voucher voucher = new Voucher(voucherName, voucherCode, voucher_discount_percent, voucher_quantity, voucher_status, datetime);
        voucher.setVoucherID(voucherID);

        int result = voucherDAO.update(voucher);

        if (result == 1) {
            response.sendRedirect("/admin#success_update_voucher");
            return;
        } else {
            response.sendRedirect("/admin#failure_update_voucher");
            return;
        }
    }

    private void doPostDeleteVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the string of food IDs from the request
        String[] voucherIDs = request.getParameter("voucherData").split(",");

        // Convert the strings to numbers
        List<Byte> voucherIDList = new ArrayList<>();
        for (int i = 0; i < voucherIDs.length; i++) {
            voucherIDList.add(Byte.parseByte(voucherIDs[i]));
        }

        // Delete each food item, and count deleted items
        VoucherDAO dao = new VoucherDAO();
        int result = dao.deleteMultiple(voucherIDList);

        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        request.setAttribute("tabID", 3);
        
        if (result == 1) {
            response.sendRedirect("/admin#success_delete_voucher");
            return;
        } else {
            response.sendRedirect("/admin#failure_delete_voucher");
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
            doGetList(request, response);
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
                case "SubmitDeleteVoucher":
                    doPostDeleteVoucher(request, response);
                    break;
                case "SubmitAddRole":
                    doPostAddRole(request, response);
                    break;
                case "SubmitUpdateRole":
                    doPostUpdateRole(request, response);
                    break;
                case "SubmitDeleteRole":
                    doPostDeleteRole(request, response);
                    break;
                default:
                    break;
            }
        }
    }

}
