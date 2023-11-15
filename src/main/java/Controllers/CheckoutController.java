/*
 */
package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

import DAOs.AccountDAO;
import DAOs.CartDAO;
import DAOs.CartItemDAO;
import DAOs.CustomerDAO;
import DAOs.FoodDAO;
import DAOs.OrderDAO;
import DAOs.VoucherDAO;
import Models.Account;
import Models.Cart;
import Models.CartItem;
import Models.Customer;
import Models.Order;
import Models.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class CheckoutController extends HttpServlet {

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
            out.println("<title>Servlet CheckoutController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        HttpSession session = request.getSession();

        if (session.getAttribute("userID") != null) {
            int userID = (Integer) session.getAttribute("userID");
            // Người dùng có đăng nhập -> lấy thông tin người dùng để autofill
            AccountDAO accountDAO = new AccountDAO();
            Account currentAccount = accountDAO.getAccount(userID);

            request.setAttribute("currentAccount", currentAccount);
            //<editor-fold defaultstate="collapsed" desc="Lấy thông tin khách hàng nếu có">
            // This info will be used to preload the "Thông tin của tôi" form
            // Default int values are assigned 0 instead of null
            if (currentAccount.getCustomerID() != 0) {
                //<editor-fold defaultstate="collapsed" desc="Get customer info">
                int customerID = currentAccount.getCustomerID();
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomer(customerID);
                request.setAttribute("customer", customer);
            }
        }

        // Lưu trữ URL hiện tại vào session attribute
        session.setAttribute("previousUrl", request.getRequestURI());
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            cart = new Cart();
            session.setAttribute("mess", "Giỏ hàng của bạn đang trống, vui lòng thêm món để thanh toán.");
            response.sendRedirect("/");
            return;
        }
        String quantityParam = "";
        for (CartItem cartItem : cart.getItems()) {
            Short foodId = cartItem.getFood().getFoodID();
            if (request.getParameter("quantity-" + foodId) != null) {
                // Thông thường nếu truy cập /checkout thì sẽ có 2 cách truy cập:
                // 1 là từ nút Thanh toán (từ modal Giỏ hàng)
                // 2 là từ nút Đặt món (từ chính trang /checkout)
                // Tất cả đều sử dụng POST và dữ liệu lấy từ form của trang trước đó
                // nên ta sẽ lấy dữ liệu từ parameter
                quantityParam = request.getParameter("quantity-" + foodId);
            } else if (session.getAttribute("quantity-" + foodId) != null) {
                // Tuy nhiên nếu yêu cầu là GET, ví dụ như sau khi đăng nhập
                // hoặc đăng xuất thành công thì trả về trang hiện tại tức /checkout 
                // thì parameter của request sau khi submit form không còn,
                // nên ta phải lưu giá trị của form bằng session attribute,
                // do đó ta phải lấy từ session
                quantityParam = (String) session.getAttribute("quantity-" + foodId);
            }
            int quantity = Integer.parseInt(quantityParam);
            cartItem.setFoodQuantity(quantity); // Cập nhật số lượng cho mục trong giỏ hàng
            // Lưu lại số lượng của các mục trong trường hợp request là GET
            // ví dụ như sau khi đăng nhập/đăng xuất thành công tại /checkout
            session.setAttribute("quantity-" + foodId, quantityParam);
        }
        session.setAttribute("cart", cart);
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    protected void doPostOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int accountID = 0;
        if (request.getParameter("txtAccountID") != null
                && !request.getParameter("txtAccountID").isEmpty()) {
            accountID = Integer.parseInt(request.getParameter("txtAccountID"));
        }

        String lastname = request.getParameter("txtLastName");
        String firstname = request.getParameter("txtFirstName");
        String gender = request.getParameter("txtGender");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        String note = request.getParameter("txtNote");
        //  get payment method
        byte paymentMethod = Byte.parseByte(request.getParameter("paymentMethod"));

        // Trình tự đặt món: thêm Customer -> Cart -> tất cả Cartitem -> Order
        // Thêm Customer
        // Lấy Cart từ session -> thêm vào db
        HttpSession session = request.getSession();
        CustomerDAO customerdao = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        FoodDAO foodDAO = new FoodDAO();
        OrderDAO orderdao = new OrderDAO();
        CartDAO cartdao = new CartDAO();
        VoucherDAO voucherDAO = new VoucherDAO();
        Customer customer = new Customer(firstname, lastname, gender, phone, address);
        int customerID = 0;

        int result = 0;
        if (accountID != 0) {
            // Nếu có accountID -> đã login thành công
            Account account = accountDAO.getAccount(accountID);
            if (account.getCustomerID() != 0) {
                // Tài khoản này đã có thông tin KH
                customerID = account.getCustomerID();
            } else {

                result = customerdao.add(customer);
                if (result == 1) {
                    Customer lastestCustomer = customerdao.getLatestCustomer();
                    account.setCustomerID(lastestCustomer.getCustomerID());
                    accountDAO.updateCustomerID(account);
                    customerID = lastestCustomer.getCustomerID();
                } else {
                    session.setAttribute("toastMessage", "error-order");
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                    return;
                }

                customer = customerdao.getLatestCustomer();  // customerId lay tu DB ra tang dan
                customerID = customer.getCustomerID();
            }
        } else {
            //<editor-fold defaultstate="collapsed" desc="Nếu không có: thêm customer vào db">
            result = customerdao.add(customer);
            if (result != 1) {
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
                return;
            }
            customer = customerdao.getLatestCustomer();  // customerId lay tu DB ra tang dan
            customerID = customer.getCustomerID();
        }
  
        Cart cart = (Cart) session.getAttribute("cart");
        cart.setUserId(customerID);
        result = cartdao.add(cart);
        if (result != 1) {
            session.setAttribute("toastMessage", "error-order");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }

        CartItemDAO cartitemdao = new CartItemDAO();
        List<CartItem> cartItemList = cart.getItems();
        int cartID = cartdao.getLatestCartID();
        cart = cartdao.getCart(cartID);

        double orderTotalDouble = 0;
        for (CartItem item : cartItemList) {
            int itemDiscount = item.getFood().getDiscountPercent();
            double itemPrice = item.getFood().getFoodPrice().doubleValue();
            int itemQuantity = item.getFoodQuantity();
            orderTotalDouble += (itemPrice - (itemPrice * itemDiscount / 100)) * itemQuantity;

            item.setCartID(cart.getId());
            result = cartitemdao.add(item);
            if (result != 1) {
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
                return;
            }
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp orderTime = Timestamp.valueOf(currentTime);
        // Tạo một số ngẫu nhiên từ 5 đến 15
        int randomMinutes = ThreadLocalRandom.current().nextInt(5, 16);
        // Tính toán deliveryTime bằng cách cộng thời gian giao hàng ngẫu nhiên với orderTime
        LocalDateTime deliveryDateTime = currentTime.plusMinutes(randomMinutes);
        Timestamp deliveryTime = Timestamp.valueOf(deliveryDateTime);
        
        Order order = new Order(cartID, customerID, (byte) 1, (byte) 3, phone, address, orderTime, note, deliveryTime);
        order.setPaymentMethodID(paymentMethod);
        // Do khi khởi tạo giá trị mặc định của orderTotal = 0
        // nên ta tự set cho nó
        
        
        if (request.getParameter("txtVoucherCode") != null) {
            String voucherCode = request.getParameter("txtVoucherCode");
            Voucher voucher = voucherDAO.getVoucherByCode(voucherCode);
            if (voucher != null ) {
                double voucherpercent = voucher.getVoucherDiscount();
                orderTotalDouble = orderTotalDouble * (voucherpercent == 1 ? 1 : 1 - voucherpercent);
                voucherDAO.updateQuantity(voucher);
                order.setVoucherID(voucher.getVoucherID());
            } else {
            }
        }
        
        BigDecimal orderTotal = BigDecimal.valueOf(orderTotalDouble);
        order.setOrderTotal(orderTotal);
        result = orderdao.add(order);
        if (result == 1) {
            // Xóa giỏ hàng từ session
            session.removeAttribute("cart");

            if (paymentMethod == 3) {
                session.setAttribute("toastMessage", "success-order");
                response.sendRedirect("/");
            }else if (paymentMethod == 1) {

                // Tạo URL cho việc gọi API
                String apiURL = "http://psql-server:8001/payment_from_cis?cis=" + customerID;

                // Thực hiện HTTP request để lấy vnpay_payment_url
                String vnpayPaymentURL = sendGetRequest(apiURL);

                if (vnpayPaymentURL != null && !vnpayPaymentURL.isEmpty()) {
                    response.sendRedirect(vnpayPaymentURL);
                } else {
                    // Xử lý trường hợp không lấy được vnpay_payment_url
                    request.setAttribute("toastMessage", "error-order-vnpay");
                    request.getRequestDispatcher("/checkout").forward(request, response);
                }  
            }
            
            for (CartItem item : cartItemList) {
                Short foodID = item.getFood().getFoodID();
                int result2 = foodDAO.updateQuantityOfFood(foodID, item.getFoodQuantity());
            }
            
            accountDAO.updateLastTimeOrder(accountID);
        } else {
            // Xử lý trường hợp không thêm đơn hàng thành công
            request.setAttribute("toastMessage", "error-order");
            request.getRequestDispatcher("/checkout").forward(request, response);
        }
    }

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

            // Parse the response and return the vnpay_payment_url
            JSONObject obj = new JSONObject(response.toString());
            String vnpayPaymentURL = obj.getString("vnpay_payment_url");
            return vnpayPaymentURL;
        } else {
            // Xử lý trường hợp không thành công khi gọi API
            return null;
        }
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}


    protected void doPostCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Gets the current time in GMT+7
        Instant instant = Instant.now();
        ZonedDateTime zdt = instant.atZone(ZoneId.of("GMT+7"));
        LocalDateTime currentTime = zdt.toLocalDateTime();
        
        // Gets the current hour in GMT+7
        int hour = currentTime.getHour();
        
        if (hour >= 20 || hour <= 8) {
          session.setAttribute("toastMessage", "error-close-time");
          response.sendRedirect("/");
          return;
        } 
        
        String voucherStatus = "Vui lòng nhập mã giảm giá nếu bạn có";
        request.setAttribute("voucherStatus", voucherStatus);
        request.setAttribute("voucherpercent", 1.0);
        
        if (session.getAttribute("userID") != null) {
            int userID = (Integer) session.getAttribute("userID");
            // Người dùng có đăng nhập -> lấy thông tin người dùng để autofill
            AccountDAO accountDAO = new AccountDAO();
            Account currentAccount = accountDAO.getAccount(userID);

            request.setAttribute("currentAccount", currentAccount);
            //<editor-fold defaultstate="collapsed" desc="Lấy thông tin khách hàng nếu có">
            // This info will be used to preload the "Thông tin của tôi" form
            // Default int values are assigned 0 instead of null
            if (currentAccount.getCustomerID() != 0) {
                //<editor-fold defaultstate="collapsed" desc="Get customer info">
                int customerID = currentAccount.getCustomerID();
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomer(customerID);
                request.setAttribute("customer", customer);
                //</editor-fold>
            }
            //</editor-fold>
        }

        // Lưu trữ URL hiện tại vào session attribute
        session.setAttribute("previousUrl", request.getRequestURI());

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            cart = new Cart();
            session.setAttribute("mess", "Giỏ hàng của bạn đang trống, vui lòng thêm món để thanh toán.");
            response.sendRedirect("/");
            return;
        }
        String quantityParam = "";
        for (CartItem cartItem : cart.getItems()) {
            Short foodId = cartItem.getFood().getFoodID();

            if (request.getParameter("quantity-" + foodId) != null) {
                quantityParam = request.getParameter("quantity-" + foodId);
            } else if (session.getAttribute("quantity-" + foodId) != null) {
                quantityParam = (String) session.getAttribute("quantity-" + foodId);
            }
            int quantity = Integer.parseInt(quantityParam);
            cartItem.setFoodQuantity(quantity); // Cập nhật số lượng cho mục trong giỏ hàng
            session.setAttribute("quantity-" + foodId, quantityParam);
        }
        session.setAttribute("cart", cart);

        // Remove empty cart message if it exists
        if (session.getAttribute("mess") != null) {
            session.removeAttribute("mess");
        }

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }
    
    protected void doPostVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String voucherCode = request.getParameter("voucherCode");
        VoucherDAO voucherDAO = new VoucherDAO();
        Voucher voucher = voucherDAO.getVoucherByCode(voucherCode);
        Double voucherpercent = 1.0;
        String voucherStatus = "Vui lòng nhập mã giảm giá nếu bạn có";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp now = Timestamp.valueOf(currentTime);
        if (voucher != null) {
            if (voucher.getVoucher_status() == 1 && now.compareTo(voucher.getVoucher_date()) < 0){
                request.setAttribute("voucherCode", voucherCode);
                voucherpercent= voucher.getVoucherDiscount();
                System.out.println(voucherpercent);
                
                voucherStatus = voucher.getVoucher_name() + " - Giảm giá " + voucher.getVoucher_discount_percent() + "%";
                request.setAttribute("voucherStatus", voucherStatus);
                request.setAttribute("voucherpercent", voucherpercent);
            } else {              
                request.setAttribute("voucherStatus", voucherStatus);
                request.setAttribute("voucherpercent", voucherpercent);
            }  
        } else {
            request.setAttribute("voucherStatus", voucherStatus);
            request.setAttribute("voucherpercent", voucherpercent);
        }
        
        
        if (session.getAttribute("userID") != null) {
            int userID = (Integer) session.getAttribute("userID");
            // Người dùng có đăng nhập -> lấy thông tin người dùng để autofill
            AccountDAO accountDAO = new AccountDAO();
            Account currentAccount = accountDAO.getAccount(userID);

            request.setAttribute("currentAccount", currentAccount);
            //<editor-fold defaultstate="collapsed" desc="Lấy thông tin khách hàng nếu có">
            // This info will be used to preload the "Thông tin của tôi" form
            // Default int values are assigned 0 instead of null
            if (currentAccount.getCustomerID() != 0) {
                //<editor-fold defaultstate="collapsed" desc="Get customer info">
                int customerID = currentAccount.getCustomerID();
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomer(customerID);

                request.setAttribute("customer", customer);
                //</editor-fold>

            }
            //</editor-fold>
        }

        // Lưu trữ URL hiện tại vào session attribute
        session.setAttribute("previousUrl", request.getRequestURI());

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            cart = new Cart();
            session.setAttribute("mess", "Giỏ hàng của bạn đang trống, vui lòng thêm món để thanh toán.");
            response.sendRedirect("/");
            return;
        }
        String quantityParam = "";
        for (CartItem cartItem : cart.getItems()) {
            Short foodId = cartItem.getFood().getFoodID();

            if (request.getParameter("quantity-" + foodId) != null) {
                quantityParam = request.getParameter("quantity-" + foodId);
            } else if (session.getAttribute("quantity-" + foodId) != null) {
                quantityParam = (String) session.getAttribute("quantity-" + foodId);
            }
            int quantity = Integer.parseInt(quantityParam);
            cartItem.setFoodQuantity(quantity); // Cập nhật số lượng cho mục trong giỏ hàng
            session.setAttribute("quantity-" + foodId, quantityParam);
        }
        session.setAttribute("cart", cart);

        // Remove empty cart message if it exists
        if (session.getAttribute("mess") != null) {
            session.removeAttribute("mess");
        }

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("btnSubmit") != null && request.getParameter("btnSubmit").equals("SubmitOrder")) {
            doPostOrder(request, response);
        } else if (request.getParameter("btnSubmit") != null && request.getParameter("btnSubmit").equals("Checkout")) {
            doPostCheckout(request,response);
        } else if (request.getParameter("btnSubmit") != null && request.getParameter("btnSubmit").equals("SubmitVoucher")) {
            doPostVoucher(request,response);
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
