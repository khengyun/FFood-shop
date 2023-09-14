<%-- 
    Document   : checkout
    Created on : Jul 1, 2023, 8:00:52 PM
    Author     : CE171454 Hua Tien Thanh
--%>

<%-- 
    Document   : checkout
    Created on : Jul 1, 2023, 8:00:52 PM
    Author     : CE171454 Hua Tien Thanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Title -->
    <title>FFood | Thanh toán đơn món</title>
    <%@ include file="WEB-INF/jspf/resources.jspf" %>
    <style>
      .activeError {
        color: red;
      }
      .paying-method {
        margin-right: 12px;
        background-color: #ddd;
      }
    </style>
  </head>
  <body>
    <main class="main" id="top">
      <%@ include file="WEB-INF/jspf/base.jspf" %>
      <%@ include file="WEB-INF/jspf/header.jspf" %>
      <%@ include file="WEB-INF/jspf/login.jspf" %>
      <%@ include file="WEB-INF/jspf/signup.jspf" %>

      <div class="container my-5">
        <div class="row">
          <div class="col-md-6">
            <h4>Giỏ hàng của bạn</h4>
            <div class="table-responsive">
              <table class="table">
                <thead>
                  <tr>
                    <th>Món ăn/Đồ uống</th>
                    <th>Đơn giá</th>
                    <th>Só lượng</th>
                    <th>Số tiền</th>
                  </tr>
                </thead>
                <tbody>
                  <c:set var="totalPrice" value="0"></c:set>
                  <c:forEach var="cart" items="${sessionScope['cart'].items}">
                    <tr>
                      <td>${cart.food.foodName}</td>
                      <td>${cart.food.getFoodPriceFormat()}</td>
                      <td>${cart.foodQuantity}</td>
                      <td><c:set var="productPrice" value="${Double.parseDouble(cart.food.foodPrice) * cart.foodQuantity}" />
                        <c:set var="totalPrice" value="${totalPrice + productPrice}" /> 
                        ${Double.parseDouble(cart.food.foodPrice) * cart.foodQuantity} đ</td>
                    </tr>
                  </c:forEach>


                </tbody>
              </table>
            </div>
          </div>
          <div class="col-md-6">
            <form action="checkout" method="post">
              <div class="row">
                <!-- Hidden - User Account ID -->
                <input type="hidden" id="txtAccountID" name="txtAccountID" value="${currentAccount.accountID}"/>
                <div class="col-md-12">
                  <h4>Thông tin giao món</h4>
                  <div class="mb-3">
                    <label for="txtLastName" class="form-label">Họ</label>
                    <input type="text" class="form-control" id="txtLastName" name="txtLastName" value="${customer.lastName}" placeholder="Enter your last name">
                    <div class="form-error"></div>
                  </div>
                  <div class="mb-3">
                    <label for="txtFirstName" class="form-label">Tên</label>
                    <input type="text" class="form-control" id="txtFirstName" name="txtFirstName"value="${customer.firstName}" placeholder="Enter your first name">
                    <div class="form-error"></div>
                  </div>

                  <div class="mb-3">
                    <label for="txtGender" class="form-label">Giới tính</label>
                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="txtGender" id="genderMale" value="Nam" ${(customer.gender == "Nam") || (empty customer.gender) ? "checked" : ""}>
                      <label class="form-check-label" for="genderMale">
                        Nam
                      </label>
                    </div>
                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="txtGender" id="genderFemale" value="Nữ" ${customer.gender == "Nữ" ? "checked" : ""}>
                      <label class="form-check-label" for="genderFemale">
                        Nữ
                      </label>
                    </div>
                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="txtGender" id="genderOther" value="Khác" ${customer.gender == "Khác" ? "checked" : ""} >
                      <label class="form-check-label" for="genderOther">
                        Khác
                      </label>
                    </div>
                    <div class="form-error"></div>
                  </div>
                  <div class="mb-3">
                    <label for="txtPhone" class="form-label">Điện thoại liên hệ</label>
                    <input type="text" class="form-control" id="txtPhone" name="txtPhone" placeholder="Enter your phone number" value="${customer.phone}">
                    <div class="form-error"></div>
                  </div>
                  <div class="mb-3">
                    <label for="txtAddress" class="form-label">Địa chỉ giao hàng</label>
                    <input type="text" class="form-control" id="txtAddress" name="txtAddress" placeholder="Enter your address" value="${customer.address}">
                    <div class="form-error"></div>
                  </div>
                  <div class="mb-3">
                    <label for="txtNote" class="form-label">Ghi chú</label>
                    <textarea class="form-control" name ="txtNote" id="txtNote" rows="3" placeholder="Enter any additional notes"></textarea>
                    <div class="form-error"></div>
                  </div>

                </div>
                <div class="col-md-12">
                  <h4>Phương thức thanh toán</h4>
                  <input class="paying-method" checked type="radio" disabled>
                  <span class="">Thanh toán khi nhận món (COD)</button>
                    <!--<input type="radio" class="btn-check" name="paymentMethod" id="paymentMethod3" >-->
                    <!--<label class="btn btn-outline-dark p-3" for="paymentMethod3">Thanh toán khi nhận món (COD)</label>-->
                </div>
              </div>
              <div class="col-md-12 text-md-end mt-3 d-flex justify-content-end align-items-center">
                <h4 class="d-inline-flex">Tổng thanh toán: ${totalPrice}đ</h4>
                <button type="submit"  id=”btnSubmit” name="btnSubmit" value="SubmitOrder" class="btn btn-primary ms-3" onclick="return checkPaying();" >Đặt món</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>


    <%@ include file="WEB-INF/jspf/footer.jspf" %>
  </main>
  <%@ include file="WEB-INF/jspf/javascript.jspf" %>

  <script>
    // Primary variables
    var firstName = document.querySelector("#txtFirstName");
    var lastName = document.querySelector("#txtLastName");
    var phone = document.querySelector("#txtPhone");
    var gender = document.querySelector("#txtGender");
    var address = document.querySelector("#txtAddress");
    var note = document.querySelector("#txtNote");
    var form = document.querySelector("form");

    function showError(inputID, errorMessage) {
      let parent = inputID.parentElement;
      let formError = parent.querySelector(".form-error");
      let formInput = parent.querySelector("input");
      formError.classList.add("activeError"); // add class "activeError" into formError.
      formInput.classList.add("activeErrorOnInput"); // add class "activeError" into formError.
      formError.innerText = errorMessage; // set message into formError.
    }

    function showSuccess(inputID) {
      let parent = inputID.parentElement;
      let formError = parent.querySelector(".form-error");
      let formInput = parent.querySelector("input");
      formError.classList.remove("activeError"); // add class "activeError" into formError.
      formInput.classList.remove("activeErrorOnInput"); // add class "activeError" into formError.
      formError.innerText = ""; // set message into formError.
    }

    // 2. Check Email;
    function checkName(input, option) {
      var isNameValid = true;
      if (input.value.trim() === "") {
        // Kiểm tra trường rỗng
        if (option === "firstName") {
          showError(input, "Tên không được để trống");
        } else {
          showError(input, "Họ không được để trống");
        }
        isNameValid = false;
      } else if (input.value.trim().length > 40 && option === "firstName")
      {
        showError(input, "Tên không được vượt quá 40 ký tự");
        isNameValid = false;
      } else if (input.value.trim().length > 10 && option === "lastName")
      {
        showError(input, "Họ không được vượt quá 10 ký tự");
        isNameValid = false;
      } else {
        showSuccess(input);
      }
      return isNameValid;
    }
    //            // 2. Check phone;
    function checkPhoneValid(input) {
      const regexPhone = /^0[1-9]\d{8,9}$/;
      input.value = input.value.trim();
      var isPhoneValid = regexPhone.test(input.value);
      if (input.value === "") {
        showError(input, "Số điện thoại không được để trống");
      } else if (!isPhoneValid) {
        showError(input, "Số điện thoại không hợp lệ");
      } else {
        showSuccess(input);
      }
      return isPhoneValid;
    }


    // 2. Check address;
    function checkAddress(input) {
      var isCheckAddressValid = true;
      if (input.value.trim() === "") {
        showError(input, "Địa chỉ không được để trống");
        isCheckAddressValid = false;
      } else if (input.value.trim().length > 255 && option === "firstName")
      {
        showError(input, "Họ không được vượt quá 10 ký tự");
        isCheckAddressValid = false;
      } else {
        showSuccess(input);
      }
      return isCheckAddressValid;
    }

    // 3. Check all in form
    function checkPaying() {
      let isFirstNameValid = checkName(firstName, "firstName");
      let isLastNameValid = checkName(lastName, "lastName");
      let isPhoneValid = checkPhoneValid(phone);
      let isAddressValid = checkAddress(address);

      if (isFirstNameValid && isLastNameValid && isPhoneValid && isAddressValid) {

        return true;

      }
      return false;
    }

  </script>


  <%@ include file="WEB-INF/jspf/validation.jspf" %>

</body>
</html>