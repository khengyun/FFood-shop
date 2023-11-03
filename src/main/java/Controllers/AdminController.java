/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.AdminDAO;
import DAOs.CustomerDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.OrderLogDAO;
import DAOs.PromotionManagerDAO;
import DAOs.StaffDAO;
import DAOs.VoucherDAO;
import Models.Account;
import Models.Admin;
import Models.Customer;
import Models.Food;
import Models.Order;
import Models.OrderLog;
import Models.PromotionManager;
import Models.Role;
import Models.Staff;
import Models.User;
import Models.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdminController extends HttpServlet {
 
    private void doGetList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodDAO foodDAO = new FoodDAO();
        List<Food> foodList = foodDAO.getAllList();

        AccountDAO accountDAO = new AccountDAO();
        List<Account> userAccountList = accountDAO.getAllUser();
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.getAllCustomer();

        List<User> userList = new ArrayList<>();
        for (Account account : userAccountList) {
            if (account.getCustomerID() != 0) {
                Customer c = customerDAO.getCustomer(account.getCustomerID());
                User user = new User(
                        account.getAccountID(),
                        account.getCustomerID(),
                        account.getUsername(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getFullName(),
                        c.getGender(),
                        c.getPhone(),
                        account.getEmail(),
                        c.getAddress()
                );
                userList.add(user);
            } else {
                User user = new User(
                        account.getAccountID(),
                        0,
                        account.getUsername(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        account.getEmail(),
                        ""
                );
                userList.add(user);
            }
        }

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
        // Sorting orderList based on status in ascending order and ID in descending order
//        Collections.sort(orderList, new Comparator<Order>() {
//            @Override
//            public int compare(Order o1, Order o2) {
//                // Compare status in ascending order
//                int statusComparison = Integer.compare(o1.getOrderStatusID(), o2.getOrderStatusID());
//
//                // If status is equal, sort by ID in descending order
//                if (statusComparison == 0) {
//                    return Integer.compare(o2.getOrderID(), o1.getOrderID());
//                }
//
//                // Else sort by status in ascending order
//                return statusComparison;
//            }
//        });
        for (int i = 0; i < orderList.size(); i++){
            String Orderfirstname = customerDAO.getCustomer(orderList.get(i).getCustomerID()).getFirstName();
            String Orderlastname = customerDAO.getCustomer(orderList.get(i).getCustomerID()).getLastName();
            orderList.get(i).setFirstname(Orderfirstname);
            orderList.get(i).setLastname(Orderlastname);
        }
        
        VoucherDAO voucherDAO = new VoucherDAO();
        List<Voucher> voucherList = voucherDAO.getAllList();
        request.setAttribute("foodList", foodList);
        request.setAttribute("userList", userList);
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
        if (foodDAO.getFood(foodName) != null) {
            response.sendRedirect("/admin#failure_add_food_exist");
            return;
        }
        int result = foodDAO.add(food);
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 3);
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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 3);
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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 3);
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        if (result > 1) {
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
        String lastname = request.getParameter("txtLastName");
        String firstname = request.getParameter("txtFirstName");
        String gender = request.getParameter("txtGender");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String email = request.getParameter("txtEmail");
        String address = request.getParameter("txtAddress");
        String password = (String) request.getAttribute("txtAccountPassword");

        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, password, "user");

        if (accountDAO.getAccount(email) != null) {
            response.sendRedirect("/admin#failure_add_user_exist");
            return;
        }

        Customer newCustomer = new Customer(firstname, lastname, gender, phoneNumber, address);
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 4);
        CustomerDAO customerDAO = new CustomerDAO();
        int result = customerDAO.add(newCustomer);

        if (result == 1) {
            account.setCustomerID(customerDAO.getLatestCustomer().getCustomerID());
            System.out.println(account.getCustomerID());
            int result1 = accountDAO.add(account);
            if (result == 1) {
                response.sendRedirect("/admin#success_add_user");
                return;
            } else {
                response.sendRedirect("/admin#failure_add_user");
                return;
            }
        } else {
            response.sendRedirect("/admin#failure_add_user");
            return;
        }

    }
    
    private void doPostUpdateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("txtUserID"));
        int customerID = Integer.parseInt(request.getParameter("txtCustomerID"));
        String username = request.getParameter("txtAccountUsername");
        String lastname = request.getParameter("txtLastName");
        String firstname = request.getParameter("txtFirstName");
        String gender = request.getParameter("txtGender");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String email = request.getParameter("txtEmail");
        String address = request.getParameter("txtAddress");
        String password = (String) request.getAttribute("txtAccountPassword");

        AccountDAO accountDAO = new AccountDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 4);
        Account account = new Account(username, email, password, "user");
        account.setAccountID(userID);
        Customer customer = new Customer(firstname, lastname, gender, phoneNumber, address);
        int result1 = 0;
        if (customerID == 0){
            result1 = customerDAO.add(customer);
            account.setCustomerID(customerDAO.getLatestCustomer().getCustomerID());
        } else {
            customer.setCustomerID(customerID);
            result1 = customerDAO.update(customer); 
        }
     
        if (result1 == 1) {
            int result = accountDAO.update(account);
            int result2 = accountDAO.updateCustomerID(account);
            response.sendRedirect("/admin#success_update_user");
            return;
        } else {
            response.sendRedirect("/admin#failure_update_user");
            return;
        }
    }
    
    private void doPostDeleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the string of food IDs from the request
        String[] userIDs = request.getParameter("userData").split(",");
        String[] customerIDs = request.getParameter("customerData").split(",");

        // Convert the strings to numbers
        List<Integer> userIDList = new ArrayList<>();
        for (int i = 0; i < userIDs.length; i++) {
            userIDList.add(Integer.parseInt(userIDs[i]));
        }

        // Convert the strings to numbers
        List<Integer> customerIDList = new ArrayList<>();
        for (int i = 0; i < customerIDs.length; i++) {
            customerIDList.add(Integer.parseInt(customerIDs[i]));
        }
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 4);

        // Delete each food item, and count deleted items
        AccountDAO accountDAO = new AccountDAO();
        int result1 = accountDAO.deleteMultiple(userIDList);
        int result2 = 0;
        if (result1 == 1) {
            CustomerDAO customerDAO = new CustomerDAO();
            result2 = customerDAO.deleteMultiple(customerIDList);
            if (result2 > 1) {
                response.sendRedirect("/admin#success_delete_user");
            } else {
                response.sendRedirect("/admin#failure_delete_user");
            }

        } else {
            response.sendRedirect("/admin#failure_delete_user");
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
        if (accountDAO.getAccount(email) != null) {
            response.sendRedirect("/admin#failure_add_role_exist");
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 5);

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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 5);
        if (role.equals("staff")) {
            Staff updatestaff = new Staff(roleID, fullname);
            StaffDAO staffDAO = new StaffDAO();
            int result = staffDAO.update(updatestaff);

            if (result == 1) {
                account.setStaffID(roleID);
                int result1 = accountDAO.update(account);
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
            PromotionManager newPromotionManager = new PromotionManager(roleID, fullname);
            PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
            int result = promotionManagerDAO.update(newPromotionManager);

            if (result == 1) {
                account.setProID(roleID);
                int result1 = accountDAO.add(account);
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
        List<Integer> accountIDList = new ArrayList<>();
        for (int i = 0; i < accountIDs.length; i++) {
            accountIDList.add(Integer.parseInt(accountIDs[i]));
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

        // Delete each food item, and count deleted items
        AccountDAO accountDAO = new AccountDAO();
        int result1 = accountDAO.deleteMultiple(accountIDList);
        int result2 = 0;
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 5);
        if (result1 == 1) {
            if (StaffIDList.size() != 0) {
                StaffDAO staffDAO = new StaffDAO();
                result2 = staffDAO.deleteMultiple(StaffIDList);

            } else {
                PromotionManagerDAO proDAO = new PromotionManagerDAO();
                result2 = proDAO.deleteMultiple(ProIDList);
            }

            if (result2 > 1) {
                response.sendRedirect("/admin#success_delete_role");
            } else {
                response.sendRedirect("/admin#failure_delete_role");
            }
        } else {
            response.sendRedirect("/admin#failure_delete_role");
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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 2);

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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 2);
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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 2);
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        if (result > 1) {
            response.sendRedirect("/admin#success_delete_voucher");
            return;
        } else {
            response.sendRedirect("/admin#failure_delete_voucher");
            return;
        }
    }
    
    private void doPostUpdateOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("txtOrderID"));
        String phonenumber = request.getParameter("txtPhoneNumber");
        String address = request.getParameter("txtOrderAddress");
        String paymentmethod = request.getParameter("txtPaymentMethod");
        String note = request.getParameter("txtOrderNote");
        String status = request.getParameter("txtOrderStatus");
        Double orderTotal = Double.parseDouble(request.getParameter("txtOrderTotal"));

        BigDecimal orderTotalPay = BigDecimal.valueOf(orderTotal);
        
        byte orderStatusID = 5;
        if (status.equals("Chờ xác nhận")){
            orderStatusID = 1;
        } else if (status.equals("Đang chuẩn bị món")){
            orderStatusID = 2;
        } else if (status.equals("Đang giao")){
            orderStatusID = 3;
        } else if (status.equals("Đã giao")){
            orderStatusID = 4;
        } 
        
        byte paymentMethodID = 3;
        if (paymentmethod.equals("Thẻ tín dụng")){
            paymentMethodID = 1;
        } else if (paymentmethod.equals("Thẻ ghi nợ")){
            paymentMethodID = 2;
        }
        
        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        Order order = new Order(orderID, orderStatusID, paymentMethodID, phonenumber, address, note, orderTotalPay);
        
        Order oldOrder = orderDAO.getOrder(orderID);
        
        int result = orderDAO.updateForAdmin(order);
        session.setAttribute("tabID", 6);
        if (result == 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp logTime = Timestamp.valueOf(currentTime);
            byte adminID = (byte) session.getAttribute("adminID");
            OrderLog log = new OrderLog(orderID, "Cập nhật thông tin đơn hàng", logTime);
            log.setAdmin_id(adminID);
            logDAO.addAdminLog(log);
            if (oldOrder.getOrderStatusID() != orderStatusID){
                OrderLog logStatusOrder = new OrderLog(orderID, "Cập nhật trạng thái đơn hàng: " + status, logTime);
                logStatusOrder.setAdmin_id(adminID);
                logDAO.addAdminLog(logStatusOrder);
            }
            
            if (oldOrder.getOrderTotal() != orderTotalPay){
                OrderLog logTotalOrder = new OrderLog(orderID, "Cập nhật thanh toán đơn hàng: " + orderTotalPay, logTime);
                logTotalOrder.setAdmin_id(adminID);
                logDAO.addAdminLog(logTotalOrder);
            }

            response.sendRedirect("/admin#success_update_order");
            return;
        } else {
            response.sendRedirect("/admin#failure_update_order");
            return;
        }
    }
    
    private void doPostDeleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the string of food IDs from the request
        String[] orderIDs = request.getParameter("orderData").split(",");

        // Convert the strings to numbers
        List<Integer> orderIDList = new ArrayList<>();
        for (int i = 0; i < orderIDs.length; i++) {
            orderIDList.add(Integer.parseInt(orderIDs[i]));
        }

        // Delete each food item, and count deleted items
        HttpSession session = request.getSession();
        OrderDAO dao = new OrderDAO();
        int result = dao.deleteMultiple(orderIDList);
        
        session.setAttribute("tabID", 6);
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        if (result > 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            byte adminID = (byte) session.getAttribute("adminID");
            Timestamp logTime = Timestamp.valueOf(currentTime);
            for (int i = 0; i < orderIDList.size(); i++) {  
                OrderLog log = new OrderLog(orderIDList.get(i), "Xóa đơn hàng", logTime);
                log.setAdmin_id(adminID);               
                logDAO.addAdminLog(log);
            }
            response.sendRedirect("/admin#success_delete_order");
            return;
        } else {
            response.sendRedirect("/admin#failure_delete_order");
            return;
        }
    }
    
    private void doPostNextOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the string of food IDs from the request
        String[] orderIDs = request.getParameter("orderData").split(",");

        // Convert the strings to numbers
        List<Integer> orderIDList = new ArrayList<>();
        for (int i = 0; i < orderIDs.length; i++) {
            orderIDList.add(Integer.parseInt(orderIDs[i]));
        }

        // Delete each food item, and count deleted items
        HttpSession session = request.getSession();
        OrderDAO dao = new OrderDAO();
        int result = dao.changeStatusMultiple(orderIDList);
        
        session.setAttribute("tabID", 6);
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        if (result > 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            byte adminID = (byte) session.getAttribute("adminID");
            Timestamp logTime = Timestamp.valueOf(currentTime);
            for (int i = 0; i < orderIDList.size(); i++) {  
                OrderLog log = new OrderLog(orderIDList.get(i), "Cập nhật trạng thái đơn hàng", logTime);
                log.setAdmin_id(adminID);               
                logDAO.addAdminLog(log);
            }
            response.sendRedirect("/admin#success_next_order");
            return;
        } else {
            response.sendRedirect("/admin#failure_next_order");
            return;
        }
    }
    
    private void doGetOrderHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 7);
        int orderID = Integer.parseInt(request.getParameter("txtOrderID"));
        OrderLogDAO dao = new OrderLogDAO();
        StaffDAO staffDAO = new StaffDAO();
        AdminDAO adminDAO = new AdminDAO();
        List<OrderLog> logList = new ArrayList<>();
        logList = dao.getAllListByOrderID(orderID);
        for (int i = 0; i < logList.size(); i++){
            if (logList.get(i).getAdmin_id() != 0) {
                logList.get(i).setFullname(adminDAO.getAdmin(logList.get(i).getAdmin_id()).getFullName());
            } if (logList.get(i).getStaff_id()!= 0) {
                logList.get(i).setFullname(staffDAO.getStaff(logList.get(i).getStaff_id()).getFullName());
            }
        }

        session.setAttribute("logList", logList);
        response.sendRedirect("/admin");
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
        } else if (path.startsWith("/admin/history")) {
            doGetOrderHistory(request, response);
        } else {
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
                case "SubmitDeleteUser":
                    doPostDeleteUser(request, response);
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
                case "SubmitUpdateOrder":
                    doPostUpdateOrder(request, response);
                    break;
                case "SubmitDeleteOrder":
                    doPostDeleteOrder(request, response);
                    break;
                case "SubmitNextOrder":
                    doPostNextOrder(request, response);
                    break;
                default:
                    break;
            }
        }
    }

}
