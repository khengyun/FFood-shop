/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CustomerDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.OrderLogDAO;
import Models.Food;
import Models.Order;
import Models.OrderLog;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
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

public class StaffController extends HttpServlet {

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

    protected void doGetList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodDAO foodDAO = new FoodDAO();
        List<Food> foodList = foodDAO.getAllList();

        CustomerDAO customerDAO = new CustomerDAO();
        OrderDAO orderDAO = new OrderDAO();
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
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/staff.jsp").forward(request, response);

    }

    private void doPostAddFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        byte foodTypeID = Byte.parseByte(request.getParameter("txtFoodTypeID"));
        String foodName = request.getParameter("txtFoodName");
        String foodDescription = (String) request.getParameter("txtFoodDescription");
        BigDecimal foodPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("txtFoodPrice")));
        byte discountPercent = Byte.parseByte(request.getParameter("txtDiscountPercent"));
        Short foodQuantity = Short.parseShort(request.getParameter("txtFoodQuantity"));
        byte foodRate = Byte.parseByte(request.getParameter("txtFoodRate"));
        byte foodStatus = Byte.parseByte(request.getParameter("txtFoodStatus"));
        String imageURL = (String) request.getAttribute("txtImageURL");
        FoodDAO foodDAO = new FoodDAO();
        HttpSession session = request.getSession();

        Food food = new Food(foodName, foodDescription, foodPrice, foodStatus, foodRate, discountPercent, imageURL, foodTypeID);
        food.setQuantity(foodQuantity);
        if (foodDAO.getFood(foodName) != null) {
            session.setAttribute("toastMessage", "error-add-food-existing-food");
            response.sendRedirect("/staff");
        }

        int result = foodDAO.add(food);

        if (result >= 1) {
            session.setAttribute("toastMessage", "success-add-food");
            response.sendRedirect("/staff");
            return;
        } else {
            session.setAttribute("toastMessage", "error-add-food");  
            response.sendRedirect("/staff");
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
        if (result >= 1) {
            session.setAttribute("toastMessage", "success-update-food");
            response.sendRedirect("/staff");
            return;
        } else {
            session.setAttribute("toastMessage", "error-update-food");
            response.sendRedirect("/staff");
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

        if (result >= 1) {
            session.setAttribute("toastMessage", "success-delete-food");
            response.sendRedirect("/staff");
        } else {
            session.setAttribute("toastMessage", "error-delete-food");  
            response.sendRedirect("/staff");
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
        HttpSession session = request.getSession();
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
        OrderDAO orderDAO = new OrderDAO();
        Order order = new Order(orderID, orderStatusID, paymentMethodID, phonenumber, address, note, orderTotalPay);

        int result = orderDAO.updateForAdmin(order);

        if (result >= 1) {
            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp logTime = Timestamp.valueOf(currentTime);
            byte staffID = (byte) session.getAttribute("staffID");
            OrderLog log = new OrderLog(orderID, "Cập nhật thông tin đơn hàng", logTime);
            log.setStaff_id(staffID);
            OrderLogDAO logDAO = new OrderLogDAO();
            logDAO.addStaffLog(log);

            session.setAttribute("toastMessage", "success-update-order");
            response.sendRedirect("/staff");
            return;
        } else {
            session.setAttribute("toastMessage", "error-update-order");
            response.sendRedirect("/staff");
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

        if (result >= 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            byte staffID = (byte) session.getAttribute("staffID");
            Timestamp logTime = Timestamp.valueOf(currentTime);
            for (int i = 0; i < orderIDList.size(); i++) {
                OrderLog log = new OrderLog(orderIDList.get(i), "Cập nhật trạng thái đơn hàng", logTime);
                log.setStaff_id(staffID);
                logDAO.addStaffLog(log);
            }
            session.setAttribute("toastMessage", "success-next-order");
            response.sendRedirect("/staff");
            return;
        } else {
            session.setAttribute("toastMessage", "error-next-order");
            response.sendRedirect("/staff");
            return;
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
        String path = request.getRequestURI();
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("tabID") == null) {
          session.setAttribute("tabID", 0);
        }
        if (path.endsWith("/staff")) {
            doGetList(request, response);
        } else if (path.endsWith("/staff/")) {
            response.sendRedirect("/staff");
        } else {
            // response.setContentType("text/css");
            request.getRequestDispatcher("/staff.jsp").forward(request, response);
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
                  session.setAttribute("tabID", 1);
              break;
              case "SubmitUpdateFood":
                  doPostUpdateFood(request, response);
                  session.setAttribute("tabID", 1);
                  break;
              case "SubmitDeleteFood":
                  doPostDeleteFood(request, response);
                  session.setAttribute("tabID", 1);
                  break;
              case "SubmitUpdateOrder":
                  session.setAttribute("tabID", 2);
                  doPostUpdateOrder(request, response);
                  break;
              case "SubmitNextOrder":
                  session.setAttribute("tabID", 2);
                  doPostNextOrder(request, response);
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
