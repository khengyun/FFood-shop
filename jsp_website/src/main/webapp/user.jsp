
  <%@page contentType="text/html" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">

      <!-- Title -->
      <title>FFood | Tài khoản Người dùng</title>
      <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
    </head>

    <body class="d-flex flex-column">
      <%@ include file="WEB-INF/jspf/common/imports/base.jspf" %>
        <%@ include file="WEB-INF/jspf/user/userHeader.jspf" %>
          <%@ include file="WEB-INF/jspf/common/components/toast.jspf" %>
            <div class="container flex-grow-1 my-5">
              <!-- Nav tabs -->
              <ul id="user-tab" class="nav nav-tabs flex-center" role="tablist">
                <li class="nav-item" role="presentation">
                  <button class="nav-link" id="info-tab"
                    data-bs-toggle="tab" data-bs-target="#info" type="button" role="tab" aria-controls="info"
                    aria-checked="true">Thông tin của tôi</button>
                </li>
                <li class="nav-item" role="presentation">
                  <button class="nav-link" id="account-tab" data-bs-toggle="tab"
                    data-bs-target="#account" type="button" role="tab" aria-controls="account" aria-checked="false">Tài
                    khoản đăng nhập</button>
                </li>
                <li class="nav-item" role="presentation">
                  <button class="nav-link" id="order-tab" data-bs-toggle="tab"
                    data-bs-target="#order" type="button" role="tab" aria-controls="order" aria-checked="false">Đơn
                    món</button>
                </li>
              </ul>

              <!-- Tab panes -->
              <div class="tab-content my-3" data-initial-tab="${tabID}">
              <%
                if (session != null && session.getAttribute("tabID") != null) {
                  session.removeAttribute("tabID");
                }
              %>
                <!-- Edit Information Tab -->
                <div class="tab-pane fade" id="info" role="tabpanel" aria-labelledby="info-tab">
                  <div class="row">
                    <!-- Edit user information form -->
                    <form class="update-info-form mx-auto col-12 col-md-10 col-lg-8" method="post" action="/user/info">
                      <div class="col">
                        <!-- Hidden - User Account ID -->
                        <input type="hidden" id="txtAccountID" name="txtAccountID"
                          value="${currentAccount.accountID}" />
                        <div class="row mb-3">
                          <div class="col-md-6">
                            <label for="txtLastName" class="form-label">Họ</label>
                            <input type="text" class="form-control" id="txtLastName" name="txtLastName"
                              value="${customer.lastName}" required="">
                          </div>
                          <div class="col-md-6">
                            <label for="txtFirstName" class="form-label">Tên</label>
                            <input type="text" class="form-control" id="txtFirstName" name="txtFirstName"
                              value="${customer.firstName}" required="">
                          </div>
                        </div>
                        <div class="mb-3">
                          <label>Giới tính</label><br>
                          <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="txtGender" id="male" value="Nam"
                              ${customer.gender=="Nam" ? "checked" : "" } required="">
                            <label class="form-check-label" for="male">Nam</label>
                          </div>
                          <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="txtGender" id="female" value="Nữ"
                              ${customer.gender=="Nữ" ? "checked" : "" } required="">
                            <label class="form-check-label" for="female">Nữ</label>
                          </div>
                          <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="txtGender" id="other" value="Khác"
                              ${customer.gender=="Khác" ? "checked" : "" } required="">
                            <label class="form-check-label" for="other">Khác</label>
                          </div>
                        </div>
                      </div>
                      <div class="col">
                        <!-- Edit user contact information form -->
                        <div class="mb-3">
                          <label for="txtPhoneNumber" class="form-label">Số điện thoại</label>
                          <input type="tel" class="form-control" id="txtPhoneNumber" name="txtPhoneNumber"
                            value="${customer.phone}" required="">
                        </div>
                        <div class="mb-3">
                          <label for="txtAddress" class="form-label">Địa chỉ</label>
                          <textarea class="form-control" id="txtAddress" name="txtAddress" required=""
                            rows="2">${customer.address}</textarea>
                        </div>
                        <button type="submit" class="btn btn-success">Lưu thông tin</button>
                        <!-- Hidden Submit Value -->
                        <input type="hidden" id="btnSubmit" name="btnSubmit" value="SubmitUpdateInfo">
                      </div>
                    </form>
                  </div>
                </div>

                <!-- Change Login Info Tab -->
                <div class="tab-pane fade" id="account" role="tabpanel" aria-labelledby="account-tab">
                  <div class="row">
                    <form class="update-user-account-form mx-auto col-12 col-md-10 col-lg-8" method="post" action="/user">
                      <!-- Hidden - User Account ID -->
                      <input type="hidden" id="txtAccountID" name="txtAccountID" value="${currentAccount.accountID}" />
                      <div class="col">
                        <!-- Change username  -->
                        <div class="mb-3">
                          <label for="txtAccountUsername" class="form-label">Tên Tài khoản Người dùng</label>
                          <input type="text" class="form-control" id="txtAccountUsername" name="txtAccountUsername"
                            value="${currentAccount.username}" required="">
                        </div>
                      </div>
                      <div class="col">
                        <!-- Change email  -->
                        <div class="mb-3">
                          <label for="txtEmail" class="form-label">Email</label>
                          <input type="email" class="form-control" id="txtEmail" name="txtEmail" value="${currentAccount.email}" readonly>
                        </div>
                      </div>
                      <div class="col">
                        <!-- Change password -->
                        <div class="mb-3">
                          <label for="txtAccountPassword" class="form-label">Mật khẩu mới</label>
                          <input type="password" class="form-control" id="txtAccountPassword" name="txtAccountPassword">
                        </div>
                        <div class="mb-3">
                          <label for="txtAccountRePassword" class="form-label">Nhập lại Mật khẩu mới</label>
                          <input type="password" class="form-control" id="txtUserAccountRePassword" name="txtAccountRePassword">
                        </div>
                        <button type="submit" class="btn btn-success">Cập nhật</button>
                        <!-- Hidden Submit Value -->
                        <input type="hidden" id="btnSubmit" name="btnSubmit" value="SubmitUpdateUser">
                      </div>
                    </form>
                  </div>
                </div>

                <!-- Order History Tab -->
                <div class="tab-pane fade" id="order" role="tabpanel" aria-labelledby="order-tab">
                  <div class="row">
                    <div class="mx-auto col-12 col-md-10 col-lg-8"><!-- Display order history as cards -->
                      <c:if test="${empty orderList}">
                        <p class="my-4 text-center">Không có đơn hàng</p>
                      </c:if>
                      <c:forEach items="${orderList}" var="o">
                        <%@ include file="WEB-INF/jspf/user/cancelOrder.jspf" %>
                          <div class="card mb-3" id="order_card_${o.orderID}">
                            <div class="card-header d-flex align-items-center">
                              <div>Ðơn #${o.hashOrderID} (Trạng thái: ${o.orderStatus})</div>
                              <div class="flex-grow-1"></div>
                              <c:if
                                test="${o.orderStatus eq 'Chờ xác nhận' || o.orderStatus eq 'Đang chuẩn bị món' || o.orderStatus eq 'Đang giao'}">
                                <button type="button" id="btn-cancel-order" class="btn btn-sm btn-danger py-1"
                                  data-bs-toggle="modal" data-bs-target="#cancel-order-modal">
                                  Hủy đơn
                                </button>
                              </c:if>
                            </div>
                            <div class="card-body">
                              <p>Thời gian đặt:
                                <fmt:formatDate value="${o.orderTime}" pattern="HH:mm, dd 'tháng' MM, yyyy" />
                              </p>
                              <p>Các món đặt:</p>
                              <ul>
                                <c:forEach items="${o.orderItems}" var="item">
                                  <li>${item}</li>
                                </c:forEach>
                              </ul>
                              <p>Tổng thanh toán:
                                <fmt:formatNumber type="number" pattern="###,###" value="${o.orderTotal}" />đ
                              <p class="payment_check"></p>
                            </div>
                          </div>
                      </c:forEach>
                    </div>
                  </div>

                </div>
              </div>
              <script>
                document.addEventListener("DOMContentLoaded", function () {
                  const orderCards = document.querySelectorAll("[id^='order_card_']");
                  console.log("Order cards:", orderCards);

                  orderCards.forEach((orderCard) => {

                    let orderID = orderCard.id.split("_")[2]; // Extract orderID from the id attribute
                    // const orderID = orderCard.id
                    // Fetch data from the API using the extracted orderID
                    fetch("http://localhost:8001/check_order_payment/" + orderID)
                      .then((response) => response.json())
                      .then((data) => {
                        payment_infor = data[0];
                        // Log the received data to the console
                        console.log(`Data for order ID ${orderID}:`, data);
                        payment_check = orderCard.querySelector(".payment_check");
                        if (data.length == 0) {
                          payment_check.innerHTML = '<span class="badge bg-danger"> Thanh Toán Khi Nhận Hàng </span>';
                        }
                        else {
                          if (payment_infor.payment_time != null) {
                            payment_check.innerHTML = '<span class="badge bg-success">Đã thanh toán: ' + payment_infor.payment_method + '</span>';
                          } else if (payment_infor.payment_method_id == 3 && payment_infor.payment_time == null) {
                            payment_check.innerHTML = '<span class="badge bg-danger"> Thanh Toán Khi Nhận Hàng </span>';
                          }
                        }

                      })
                      .catch((error) => {
                        console.error(`Error fetching data for order ID ${orderID}:`, error);
                      });
                  });
                });
              </script>


            </div>
            <%@ include file="WEB-INF/jspf/common/components/footer.jspf" %>
              <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
                <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
                  <script src="<%= request.getContextPath() + " assets/js/user.js"%>"></script>
                  <script src="<%= request.getContextPath() + " assets/js/userNotify.js"%>"></script>
    </body>

    </html>