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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminController extends HttpServlet {
    
    // send request function
    private String sendGetRequest(String apiURL) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON array response
                JSONArray jsonArray = new JSONArray(response.toString());
                
                // Check if the array is not empty
                if (jsonArray.length() > 0) {
                    // Get the first object from the array
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    // Extract payment_time from the object
                    String paymentTime = jsonObject.getString("payment_time");
                    return paymentTime;
                } else {
                    // Handle empty JSON array (no elements found)
                    return null;
                }
                
                
            } else {
                // Xử lý trường hợp không thành công khi gọi API
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // get list of food, voucher, user, role, order in database
    private void doGetList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CustomerDAO customerDAO = new CustomerDAO();
        FoodDAO foodDAO = new FoodDAO();
        AccountDAO accountDAO = new AccountDAO();
        StaffDAO staffDAO = new StaffDAO();
        PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
        OrderDAO orderDAO = new OrderDAO();
        VoucherDAO voucherDAO = new VoucherDAO();

        List<Food> foodList = foodDAO.getAllList();
        List<Account> userAccountList = accountDAO.getAllUser();
        List<Customer> customerList = customerDAO.getAllCustomer();
        List<Account> accountList = accountDAO.getAllRole();
        List<Staff> StaffList = staffDAO.getAllStaff();
        List<PromotionManager> PromotionManagerList = promotionManagerDAO.getAllPromotionManager();
        List<Voucher> voucherList = voucherDAO.getAllList();

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
                        c.getAddress(),
                        account.getLasttime_order()
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

        List<Order> orderList = orderDAO.getAllList();
        for (int i = 0; i < orderList.size(); i++) {
            String Orderfirstname = customerDAO.getCustomer(orderList.get(i).getCustomerID()).getFirstName();
            String Orderlastname = customerDAO.getCustomer(orderList.get(i).getCustomerID()).getLastName();
            String payment_status = "Chưa thanh toán";
            // Tạo URL cho việc gọi API
            String apiURL = "http://psql-server:8001/check_order_payment/" + orderList.get(i).getOrderID();
            // Thực hiện HTTP request để lấy vnpay_payment_url            
            String payment_time = sendGetRequest(apiURL);
            if (payment_time != null) {
                payment_status = "Đã thanh toán";
            }
            orderList.get(i).setPayment_status(payment_status);
            orderList.get(i).setFirstname(Orderfirstname);
            orderList.get(i).setLastname(Orderlastname);
        }

        request.setAttribute("foodList", foodList);
        request.setAttribute("userList", userList);
        request.setAttribute("roleList", roleList);
        request.setAttribute("orderList", orderList);
        request.setAttribute("voucherList", voucherList);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
    
    //add food
    private void doPostAddFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        byte foodTypeID = Byte.parseByte(request.getParameter("txtFoodTypeID"));
        String foodName = request.getParameter("txtFoodName");
        String foodDescription = (String) request.getParameter("txtFoodDescription");
        BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
        byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
        byte foodRate = Byte.parseByte(request.getParameter("txtFoodRate"));
        Short foodQuantity = Short.parseShort(request.getParameter("txtFoodQuantity"));
        byte foodStatus = Byte.parseByte(request.getParameter("txtFoodStatus"));
        String imageURL = (String) request.getAttribute("txtImageURL");
        FoodDAO foodDAO = new FoodDAO();
        Food food = new Food(foodName, foodDescription, foodPrice, foodStatus, foodRate, discountPercent, imageURL, foodTypeID);
        food.setQuantity(foodQuantity);
        HttpSession session = request.getSession();
        if (foodDAO.getFood(foodName) != null) {
            session.setAttribute("toastMessage", "error-add-food-existing-food");
            response.sendRedirect("/admin");
            return;
        }
        int result = foodDAO.add(food);
        
        if (result >= 1) {
            session.setAttribute("toastMessage", "success-add-food");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-add-food");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // update food
    private void doPostUpdateFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        short foodID = Short.parseShort(request.getParameter("txtFoodID"));
        byte foodTypeID = Byte.parseByte(request.getParameter("txtFoodTypeID"));
        String foodName = request.getParameter("txtFoodName");
        String foodDescription = (String) request.getParameter("txtFoodDescription");
        BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
        Short foodQuantity = Short.parseShort(request.getParameter("txtFoodQuantity"));
        byte foodRate = Byte.parseByte(request.getParameter("txtFoodRate"));
        byte foodStatus = Byte.parseByte(request.getParameter("txtFoodStatus"));
        byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
        String imageURL = (String) request.getAttribute("txtImageURL");

        FoodDAO foodDAO = new FoodDAO();
        Food food = new Food(foodID, foodName, foodDescription, foodPrice, foodStatus, foodRate, discountPercent, imageURL, foodTypeID);
        food.setQuantity(foodQuantity);
        int result = foodDAO.update(food);
        HttpSession session = request.getSession();
        
        if (result >=1) {
          session.setAttribute("toastMessage", "success-update-food");
          response.sendRedirect("/admin");
          return;
        } else {
            session.setAttribute("toastMessage", "error-update-food");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // delete food
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
        

        if (result >= 1) {
            session.setAttribute("toastMessage", "success-delete-food");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-delete-food");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    //add user
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

        HttpSession session = request.getSession();
        if (accountDAO.getAccount(email) != null) {
            session.setAttribute("toastMessage", "error-add-user-existing-account");
            response.sendRedirect("/admin");
            return;
        }

        Customer newCustomer = new Customer(firstname, lastname, gender, phoneNumber, address);
        
        CustomerDAO customerDAO = new CustomerDAO();
        int result = customerDAO.add(newCustomer);

        if (result >= 1) {
            account.setCustomerID(customerDAO.getLatestCustomer().getCustomerID());
            System.out.println(account.getCustomerID());
            int result1 = accountDAO.add(account);
            if (result1 >=1) {
                session.setAttribute("toastMessage", "success-add-user");
                response.sendRedirect("/admin");
                return;
            } else {
                session.setAttribute("toastMessage", "error-add-user");
                response.sendRedirect("/admin");
                return;
            }
        } else {
            session.setAttribute("toastMessage", "error-add-user");
            response.sendRedirect("/admin");
            return;
        }

    }
    
    //update user
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
        
        Account account = new Account(username, email, password, "user");
        account.setAccountID(userID);
        Customer customer = new Customer(firstname, lastname, gender, phoneNumber, address);
        int result1 = 0;
        if (customerID == 0) {
            result1 = customerDAO.add(customer);
            account.setCustomerID(customerDAO.getLatestCustomer().getCustomerID());
        } else {
            customer.setCustomerID(customerID);
            result1 = customerDAO.update(customer);
        }

        if (result1 >= 1) {
            int result = accountDAO.update(account);
            int result2 = accountDAO.updateCustomerID(account);
            session.setAttribute("toastMessage", "success-update-user");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-update-user");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    //delete user
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
        

        // Delete each food item, and count deleted items
        AccountDAO accountDAO = new AccountDAO();
        int result1 = accountDAO.deleteMultiple(userIDList);
        if (result1 >= 1) {
            session.setAttribute("toastMessage", "success-delete-user");
            response.sendRedirect("/admin");            
        } else {
            session.setAttribute("toastMessage", "error-delete-user");
            response.sendRedirect("/admin");
        }
    }   
    
    //add role
    private void doPostAddRole(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("txtAccountUsername");
        String fullname = request.getParameter("txtAccountFullname");
        String email = request.getParameter("txtEmail");
        String role = request.getParameter("txtAccountRole");
        String password = (String) request.getAttribute("txtAccountPassword");
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, password, role);
        HttpSession session = request.getSession();
        if (accountDAO.getAccount(email) != null) {
            session.setAttribute("toastMessage", "error-add-role-existing-account");
            response.sendRedirect("/admin");
            return;
        }
        

        if (role.equals("staff")) {
            Staff newstaff = new Staff(fullname);
            StaffDAO staffDAO = new StaffDAO();
            int result = staffDAO.add(newstaff);

            if (result >= 1) {
                account.setStaffID(staffDAO.getNewStaff().getStaffID());
                int result1 = accountDAO.add(account);
                if (result1 >= 1) {
                    session.setAttribute("toastMessage", "success-add-role");
                    response.sendRedirect("/admin");
                    return;
                } else {
                    session.setAttribute("toastMessage", "error-add-role");
                    response.sendRedirect("/admin");
                    return;
                }
            } else {
                session.setAttribute("toastMessage", "error-add-role");
                response.sendRedirect("/admin");
                return;
            }
        } else if (role.equals("promotionManager")) {
            PromotionManager newPromotionManager = new PromotionManager(fullname);
            PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
            int result = promotionManagerDAO.add(newPromotionManager);

            if (result >= 1) {
                account.setProID(promotionManagerDAO.getNewPromotionManager().getProID());
                int result1 = accountDAO.add(account);
                if (result1 >= 1) {
                    session.setAttribute("toastMessage", "success-add-role");
                    response.sendRedirect("/admin");
                    return;
                } else {
                    session.setAttribute("toastMessage", "error-add-role");
                    response.sendRedirect("/admin");
                    return;
                }
            } else {
                session.setAttribute("toastMessage", "error-add-role");
                response.sendRedirect("/admin");
                return;
            }
        }
    }
    
    // update role
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
        
        if (role.equals("staff")) {
            Staff updatestaff = new Staff(roleID, fullname);
            StaffDAO staffDAO = new StaffDAO();
            int result = staffDAO.update(updatestaff);

            if (result >= 1) {
                account.setStaffID(roleID);
                int result1 = accountDAO.update(account);
                if (result1 >= 1) {
                    session.setAttribute("toastMessage", "success-update-role");
                    response.sendRedirect("/admin");
                    return;
                } else {
                    session.setAttribute("toastMessage", "error-update-role");
                    response.sendRedirect("/admin");
                    return;
                }
            } else {
                session.setAttribute("toastMessage", "error-update-role");
                response.sendRedirect("/admin");
                return;
            }
        } else if (role.equals("promotionManager")) {
            PromotionManager newPromotionManager = new PromotionManager(roleID, fullname);
            PromotionManagerDAO promotionManagerDAO = new PromotionManagerDAO();
            int result = promotionManagerDAO.update(newPromotionManager);

            if (result >= 1) {
                account.setProID(roleID);
                int result1 = accountDAO.update(account);
                if (result1 >= 1) {
                    session.setAttribute("toastMessage", "success-update-role");
                    response.sendRedirect("/admin");
                    return;
                } else {
                    session.setAttribute("toastMessage", "error-update-role");
                    response.sendRedirect("/admin");
                    return;
                }
            } else {
                session.setAttribute("toastMessage", "error-update-role");
                response.sendRedirect("/admin");
                return;
            }
        }
    }
    
    // delete role
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

        // Convert the strings to numbers
        List<Integer> accountIDList = new ArrayList<>();
        for (int i = 0; i < accountIDs.length; i++) {
            accountIDList.add(Integer.parseInt(accountIDs[i]));
        }
        
        List<Byte> StaffIDList = new ArrayList<>();
        List<Byte> ProIDList = new ArrayList<>();
        if (!temp1IDs[0].equals("")) {
            // Convert the strings to numbers            
            for (int i = 0; i < temp1IDs.length; i++) {
                StaffIDList.add(Byte.parseByte(temp1IDs[i]));
            }
        } else {            
            for (int i = 0; i < temp2IDs.length; i++) {
                ProIDList.add(Byte.parseByte(temp2IDs[i]));
            }
        }

        // Delete each food item, and count deleted items
        AccountDAO accountDAO = new AccountDAO();
        int result1 = accountDAO.deleteMultiple(accountIDList);
        int result2 = 0;
        HttpSession session = request.getSession();
        
        if (result1 >= 1) {
            if (StaffIDList.size() != 0) {
                StaffDAO staffDAO = new StaffDAO();
                result2 = staffDAO.deleteMultiple(StaffIDList);

            } else {
                PromotionManagerDAO proDAO = new PromotionManagerDAO();
                result2 = proDAO.deleteMultiple(ProIDList);
            }

            if (result2 >= 1) {
                session.setAttribute("toastMessage", "success-delete-role");
                response.sendRedirect("/admin");
            } else {
                session.setAttribute("toastMessage", "error-delete-role");
                response.sendRedirect("/admin");
            }
        } else {
            session.setAttribute("toastMessage", "error-delete-role");
            response.sendRedirect("/admin");
        }
    }
    
    // add voucher
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

        HttpSession session = request.getSession();
        if (voucherDAO.getVoucher(voucherName) != null || voucherDAO.getVoucherByCode(voucherCode) != null) {
            session.setAttribute("toastMessage", "error-add-voucher-existing-voucher");
            response.sendRedirect("/admin");
            return;
        }

        int result = voucherDAO.add(voucher);
        

        if (result >= 1) {
            session.setAttribute("toastMessage", "success-add-voucher");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-add-voucher");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // update voucher
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
        
        if (result >= 1) {
            session.setAttribute("toastMessage", "success-update-voucher");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-update-voucher");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // delete voucher
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
        
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        if (result >= 1) {
            session.setAttribute("toastMessage", "success-delete-voucher");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-delete-voucher");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // update order
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
        if (status.equals("Chờ xác nhận")) {
            orderStatusID = 1;
        } else if (status.equals("Đang chuẩn bị món")) {
            orderStatusID = 2;
        } else if (status.equals("Đang giao")) {
            orderStatusID = 3;
        } else if (status.equals("Đã giao")) {
            orderStatusID = 4;
        }

        byte paymentMethodID = 3;
        if (paymentmethod.equals("Thẻ tín dụng")) {
            paymentMethodID = 1;
        } else if (paymentmethod.equals("Thẻ ghi nợ")) {
            paymentMethodID = 2;
        }

        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        Order order = new Order(orderID, orderStatusID, paymentMethodID, phonenumber, address, note, orderTotalPay);


        int result = orderDAO.updateForAdmin(order);
        
        if (result >= 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp logTime = Timestamp.valueOf(currentTime);
            byte adminID = (byte) session.getAttribute("adminID");
            OrderLog log = new OrderLog(orderID, "Cập nhật thông tin đơn hàng", logTime);
            log.setAdmin_id(adminID);
            logDAO.addAdminLog(log);
            if (orderStatusID == 5) {
                OrderLog logStatusOrder = new OrderLog(orderID, "Hủy đơn hàng", logTime);
                logStatusOrder.setAdmin_id(adminID);
                logDAO.addAdminLog(logStatusOrder);
            }

            session.setAttribute("toastMessage", "success-update-order");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-update-order");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // delete order 
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

        
        // Redirect or forward to another page if necessary
        if (result >= 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            byte adminID = (byte) session.getAttribute("adminID");
            Timestamp logTime = Timestamp.valueOf(currentTime);
            for (int i = 0; i < orderIDList.size(); i++) {
                OrderLog log = new OrderLog(orderIDList.get(i), "Xóa đơn hàng", logTime);
                log.setAdmin_id(adminID);
                logDAO.addAdminLog(log);
            }
            session.setAttribute("toastMessage", "success-delete-order");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-delete-order");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // update status of list of order to next status
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

        
        // Redirect or forward to another page if necessary
        if (result >= 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            byte adminID = (byte) session.getAttribute("adminID");
            Timestamp logTime = Timestamp.valueOf(currentTime);
            for (int i = 0; i < orderIDList.size(); i++) {
                OrderLog log = new OrderLog(orderIDList.get(i), "Cập nhật trạng thái đơn hàng", logTime);
                log.setAdmin_id(adminID);
                logDAO.addAdminLog(log);
            }
            session.setAttribute("toastMessage", "success-next-order");
            response.sendRedirect("/admin");
            return;
        } else {
            session.setAttribute("toastMessage", "error-next-order");
            response.sendRedirect("/admin");
            return;
        }
    }
    
    // get the history of an order
    private void doGetOrderHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 6);
        String path = request.getRequestURI();
        if (path.startsWith("/admin/history/")) {
            String[] s = path.split("/");
            int orderID = Integer.parseInt(s[s.length - 1]);
            OrderLogDAO dao = new OrderLogDAO();
            StaffDAO staffDAO = new StaffDAO();
            AdminDAO adminDAO = new AdminDAO();
            List<OrderLog> logList = new ArrayList<>();
            logList = dao.getAllListByOrderID(orderID);
            for (int i = 0; i < logList.size(); i++) {
                if (logList.get(i).getAdmin_id() != 0) {
                    logList.get(i).setFullname(adminDAO.getAdmin(logList.get(i).getAdmin_id()).getFullName());
                }
                if (logList.get(i).getStaff_id() != 0) {
                    logList.get(i).setFullname(staffDAO.getStaff(logList.get(i).getStaff_id()).getFullName());
                }
            }
            session.setAttribute("logList", logList);
            session.setAttribute("orderHistory", "orderHistory");
            response.sendRedirect("/admin");
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
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("tabID") == null) {
          session.setAttribute("tabID", 0);
        }
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
        HttpSession session = request.getSession();
        if (request.getParameter("btnSubmit") != null) {
            switch (request.getParameter("btnSubmit")) {
                case "SubmitAddFood":
                    doPostAddFood(request, response);
                    session.setAttribute("tabID", 3);
                    break;
                case "SubmitUpdateFood":
                    doPostUpdateFood(request, response);
                    session.setAttribute("tabID", 3);
                    break;
                case "SubmitDeleteFood":
                    doPostDeleteFood(request, response);
                    session.setAttribute("tabID", 3);
                    break;
                case "SubmitAddUser":
                    doPostAddUser(request, response);
                    session.setAttribute("tabID", 4);
                    break;
                case "SubmitUpdateUser":
                    session.setAttribute("tabID", 4);
                    doPostUpdateUser(request, response);
                    break;
                case "SubmitDeleteUser":
                  session.setAttribute("tabID", 4);
                    doPostDeleteUser(request, response);
                    break;
                case "SubmitAddVoucher":
                    session.setAttribute("tabID", 2);
                    doPostAddVoucher(request, response);
                    break;
                case "SubmitUpdateVoucher":
                    session.setAttribute("tabID", 2);
                    doPostUpdateVoucher(request, response);
                    break;
                case "SubmitDeleteVoucher":
                    session.setAttribute("tabID", 2);
                    doPostDeleteVoucher(request, response);
                    break;
                case "SubmitAddRole":
                    session.setAttribute("tabID", 5);
                    doPostAddRole(request, response);
                    break;
                case "SubmitUpdateRole":
                    session.setAttribute("tabID", 5);
                    doPostUpdateRole(request, response);
                    break;
                case "SubmitDeleteRole":
                    session.setAttribute("tabID", 5);
                    doPostDeleteRole(request, response);
                    break;
                case "SubmitUpdateOrder":
                    session.setAttribute("tabID", 6);
                    doPostUpdateOrder(request, response);
                    break;
                case "SubmitDeleteOrder":
                    session.setAttribute("tabID", 6);
                    doPostDeleteOrder(request, response);
                    break;
                case "SubmitNextOrder":
                    session.setAttribute("tabID", 6);
                    doPostNextOrder(request, response);
                    break;
                default:
                    break;
            }
        }
    }

}
