/*
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.CartDAO;
import DAOs.CartItemDAO;
import DAOs.CustomerDAO;
import DAOs.OrderDAO;
import Models.Account;
import Models.Cart;
import Models.CartItem;
import Models.Customer;
import Models.Order;
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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author LEGION
 */
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
    int userID = (Integer) session.getAttribute("userID");
    AccountDAO accountDAO = new AccountDAO();
    Account currentAccount = accountDAO.getAccount(userID);

    request.setAttribute("currentAccount", currentAccount);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get customer info if it exists">
    // This info will be used to preload the "Thông tin của tôi" form
    // Default int values are assigned 0 instead of null
    if (currentAccount.getCustomerID() != 0) {
      int customerID = currentAccount.getCustomerID();
      CustomerDAO customerDAO = new CustomerDAO();
      Customer customer = customerDAO.getCustomer(customerID);

      request.setAttribute("customer", customer);
    }
    //</editor-fold>

    // Lưu trữ URL hiện tại vào session attribute
    session.setAttribute("previousUrl", request.getRequestURI());
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
      request.setAttribute("mess", "Giỏ hàng trống, vui lòng chọn món để thanh toán");
      request.getRequestDispatcher("home").forward(request, response);
      return;
    }
    if (cart == null) {
      cart = new Cart();
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
    if (request.getParameter("btnSubmit") != null
            && request.getParameter("btnSubmit").equals("SubmitOrder")) {
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

      // Trình tự đặt món: thêm Customer -> Cart -> tất cả Cartitem -> Order
      // Thêm Customer
      Customer customer = new Customer(firstname, lastname, gender, phone, address);
      CustomerDAO customerdao = new CustomerDAO();
      int customerID = 0;

      int result = 0;
      if (accountID != 0) {
        // Nếu có accountID -> đã login thành công
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccount(accountID);
        if (account.getCustomerID() != 0) {
          // Tài khoản này đã có thông tin KH
          customerID = account.getCustomerID();
        } else {
          // Kiểm tra customer người dùng nhập đã tồn tại chưa
          // Trùng họ tên + giới tính
          if (customerdao.exists(customer)) {
            //<editor-fold defaultstate="collapsed" desc="Nếu có: lấy customer đó từ database (vì nó có id đã tự tăng), không add nữa">
            customer = customerdao.getCustomer(lastname, firstname);
            if (customer == null) {
              // không lấy được customer từ database -> đá về
              request.getRequestDispatcher("checkout.jsp").forward(request, response);
              return;
            }
            //</editor-fold>
          } else {
            //<editor-fold defaultstate="collapsed" desc="Nếu không có: thêm customer vào db">
            result = customerdao.add(customer);
            if (result != 1) {
              request.getRequestDispatcher("checkout.jsp").forward(request, response);
              return;
            }

            // Lấy customer mới nhất (tức là customer vừa tạo được
            // Lưu ý không sử dụng cái này nếu không add customer (lấy customer cũ đã có)
            customer = customerdao.getLatestCustomer();  // customerId lay tu DB ra tang dan
            //</editor-fold>
          }
          // Tại bước này, dù đã có hay không tồn tại customer trước đó thì cũng đã
          // cập nhật customer bằng customer mới (có id tự tăng)
          // Đẻ thêm Cart ta cần customerID
          customerID = customer.getCustomerID();
        }
      } else {
        // Kiểm tra customer người dùng nhập đã tồn tại chưa
        // Trùng họ tên + giới tính
        if (customerdao.exists(customer)) {
          //<editor-fold defaultstate="collapsed" desc="Nếu có: lấy customer đó từ database (vì nó có id đã tự tăng), không add nữa">
          customer = customerdao.getCustomer(lastname, firstname);
          if (customer == null) {
            // không lấy được customer từ database -> đá về
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
            return;
          }
          //</editor-fold>
        } else {
          //<editor-fold defaultstate="collapsed" desc="Nếu không có: thêm customer vào db">
          result = customerdao.add(customer);
          if (result != 1) {
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
            return;
          }

          // Lấy customer mới nhất (tức là customer vừa tạo được
          // Lưu ý không sử dụng cái này nếu không add customer (lấy customer cũ đã có)
          customer = customerdao.getLatestCustomer();  // customerId lay tu DB ra tang dan
          //</editor-fold>
        }
        // Tại bước này, dù đã có hay không tồn tại customer trước đó thì cũng đã
        // cập nhật customer bằng customer mới (có id tự tăng)
        // Đẻ thêm Cart ta cần customerID
        customerID = customer.getCustomerID();
      }

      // Lấy Cart từ session -> thêm vào db
      HttpSession session = request.getSession();
      Cart cart = (Cart) session.getAttribute("cart");
      CartDAO cartdao = new CartDAO();

      cart.setUserId(customerID);
      result = cartdao.add(cart);
      if (result != 1) {
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
      }

      CartItemDAO cartitemdao = new CartItemDAO();
      List<CartItem> cartItemList = cart.getItems();
      int cartID = cartdao.getLatestCartID();
      cart = cartdao.getCart(cartID);

      double orderTotalDouble = 0;
      for (CartItem item : cartItemList) {
        double itemPrice = item.getFood().getFoodPrice().doubleValue();
        int itemQuantity = item.getFoodQuantity();
        orderTotalDouble += itemPrice * itemQuantity;

        item.setCartID(cart.getId());
        result = cartitemdao.add(item);
        if (result != 1) {
          request.getRequestDispatcher("checkout.jsp").forward(request, response);
          return;
        }
      }

      // Lấy thời gian hiện tại
      LocalDateTime currentTime = LocalDateTime.now();
      // Chuyển đổi thời gian hiện tại thành Timestamp
      Timestamp orderTime = Timestamp.valueOf(currentTime);
      // Tạo một số ngẫu nhiên từ 5 đến 15
      int randomMinutes = ThreadLocalRandom.current().nextInt(5, 16);
      // Tính toán deliveryTime bằng cách cộng thời gian giao hàng ngẫu nhiên với orderTime
      LocalDateTime deliveryDateTime = currentTime.plusMinutes(randomMinutes);
      Timestamp deliveryTime = Timestamp.valueOf(deliveryDateTime);

      OrderDAO orderdao = new OrderDAO();
      Order order = new Order(cartID, customerID, (byte) 2, (byte) 3, phone, address, orderTime, note, deliveryTime);
      // Do khi khởi tạo giá trị mặc định của orderTotal = 0
      // nên ta tự set cho nó
      BigDecimal orderTotal = BigDecimal.valueOf(orderTotalDouble);
      order.setOrderTotal(orderTotal);
      result = orderdao.add(order);
      if (result != 1) {
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
      }

      session.removeAttribute("cart");
      // Điều hướng về home sau khi add order thành công
      response.sendRedirect("/home#success");

    } else if (request.getParameter("btnSubmit") != null
            && request.getParameter("btnSubmit").equals("Checkout")) {
      System.out.println("tesst222");
      System.out.println("Chạy vào phần else if");

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
          //</editor-fold>

        }
        //</editor-fold>
      }

      // Lưu trữ URL hiện tại vào session attribute
      session.setAttribute("previousUrl", request.getRequestURI());
//         processRequest(request, response);
//        HttpSession session = request.getSession();
//        Cart cart = (Cart) object1;
//        OrderDAO odao = new OrderDAO();
//        odao.insertOrderStatusNotCFYet(cart);
      Cart cart = (Cart) session.getAttribute("cart");
      if (cart == null) {
        request.setAttribute("mess", "Giỏ hàng trống, vui lòng chọn món để thanh toán");
        request.getRequestDispatcher("home").forward(request, response);
        return;
      }
      if (cart == null) {
        cart = new Cart();
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
