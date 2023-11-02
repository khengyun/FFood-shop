/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.OrderLogDAO;
import DAOs.VoucherDAO;
import Models.Account;
import Models.Food;
import Models.Order;
import Models.OrderLog;
import Models.Voucher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StaffController extends HttpServlet {

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
            out.println("<title>Servlet StaffController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void doGetFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/staff/food/delete")) {
            String[] s = path.split("/");
            short foodID = Short.parseShort(s[s.length - 1]);
            FoodDAO dao = new FoodDAO();
            int result = dao.delete(foodID);
            request.setAttribute("tabID", 3);
            if (result == 1) {
                response.sendRedirect("/staff#success_delete_food");
            } else {
                response.sendRedirect("/staff#failure_delete_food");
            }

            response.sendRedirect("/staff#failure_delete_food");
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
        HttpSession session = request.getSession();
        session.setAttribute("tabID", 1);

        Food food = new Food(foodName, foodDescription, foodPrice, foodStatus, foodRate, discountPercent, imageURL, foodTypeID);
        if (foodDAO.getFood(foodName) != null) {
            response.sendRedirect("/staff#failure_add_food_exist");
        }

        int result = foodDAO.add(food);

        if (result == 1) {
            response.sendRedirect("/staff#success_add_food");
            return;
        } else {
            response.sendRedirect("/staff#failure_add_food");
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
        session.setAttribute("tabID", 1);
        if (result == 1) {
            response.sendRedirect("/staff#success_update_food");
            return;
        } else {
            response.sendRedirect("/staff#failure_update_food");
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
        session.setAttribute("tabID", 1);
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        request.setAttribute("tabID", 3);

        if (result > 1) {
            response.sendRedirect("/staff#success_delete_food");
        } else {
            response.sendRedirect("/staff#failure_delete_food");
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
        session.setAttribute("tabID", 2);
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
        Order oldOrder = orderDAO.getOrder(orderID);
        
        int result = orderDAO.updateForAdmin(order);
        
        if (result == 1) {
            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp logTime = Timestamp.valueOf(currentTime);
            byte staffID = (byte) session.getAttribute("staffID");
            OrderLog log = new OrderLog(orderID, "Cập nhật thông tin đơn hàng", logTime);
            log.setStaff_id(staffID);
            OrderLogDAO logDAO = new OrderLogDAO();
            logDAO.addStaffLog(log);
            
            if (oldOrder.getOrderStatusID() != orderStatusID){
                OrderLog logStatusOrder = new OrderLog(orderID, "Cập nhật trạng thái đơn hàng: " + status, logTime);
                logStatusOrder.setStaff_id(staffID);
                logDAO.addStaffLog(logStatusOrder);
            }
            
            if (oldOrder.getOrderTotal() != orderTotalPay){
                OrderLog logTotalOrder = new OrderLog(orderID, "Cập nhật thanh toán đơn hàng: " + orderTotalPay, logTime);
                logTotalOrder.setStaff_id(staffID);
                logDAO.addStaffLog(logTotalOrder);
            }
            
            response.sendRedirect("/staff#success_update_order");
            return;
        } else {
            response.sendRedirect("/staff#failure_update_order");
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

        session.setAttribute("tabID", 2);
        // TODO implement a deletion status message after page reload
        // Redirect or forward to another page if necessary
        if (result > 1) {
            OrderLogDAO logDAO = new OrderLogDAO();
            LocalDateTime currentTime = LocalDateTime.now();
            byte staffID = (byte) session.getAttribute("staffID");
            Timestamp logTime = Timestamp.valueOf(currentTime);
            for (int i = 0; i < orderIDList.size(); i++) {
                OrderLog log = new OrderLog(orderIDList.get(i), "Cập nhật trạng thái đơn hàng", logTime);
                log.setStaff_id(staffID);
                logDAO.addStaffLog(log);
            }
            response.sendRedirect("/staff#success_next_order");
            return;
        } else {
            response.sendRedirect("/staff#failure_next_order");
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
        if (path.endsWith("/staff")) {
            FoodDAO foodDAO = new FoodDAO();
            List<Food> foodList = foodDAO.getAllList();

            OrderDAO orderDAO = new OrderDAO();
            List<Order> orderList = orderDAO.getAllList();
            request.setAttribute("foodList", foodList);
            request.setAttribute("orderList", orderList);
            request.getRequestDispatcher("/staff.jsp").forward(request, response);
        } else if (path.endsWith("/staff/")) {
            response.sendRedirect("/staff");
        } else if (path.startsWith("/staff/food")) {
            doGetFood(request, response);
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
                case "SubmitUpdateOrder":
                    doPostUpdateOrder(request, response);
                    break;
                case "SubmitNextOrder":
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
