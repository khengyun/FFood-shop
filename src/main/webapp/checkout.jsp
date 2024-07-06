<%-- 
    Document   : checkout
    Created on : Jul 1, 2023, 8:00:52 PM
    Author     : CE171454 Hua Tien Thanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Title -->
        <title>FFood | Thanh toán đơn món</title>
        <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
        <style>
            .activeError {
                color: red;
            }
            .paying-method {
                margin-right: 12px;
                background-color: #ddd;
            }
        </style>
        <script src="https://accounts.google.com/gsi/client" async></script>
    </head>
    <body>
        <main class="main" id="top">
            <%@ include file="WEB-INF/jspf/common/imports/base.jspf" %>
            <%@ include file="WEB-INF/jspf/common/components/checkoutHeader.jspf" %>
            <%@ include file="WEB-INF/jspf/guest/components/login.jspf" %>
            <%@ include file="WEB-INF/jspf/guest/components/signup.jspf" %>
            <%@ include file="WEB-INF/jspf/guest/components/verify.jspf" %>
            <%@ include file="WEB-INF/jspf/guest/components/forget.jspf" %> 
            <%@ include file="WEB-INF/jspf/guest/components/changePassword.jspf" %> 
            <%@ include file="WEB-INF/jspf/common/components/toast.jspf" %>
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
                                        <th>Số lượng</th>
                                        <th>Số tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="totalPrice" value="0"></c:set>
                                    <c:forEach var="cart" items="${sessionScope['cart'].items}">
                                        <tr>
                                            <td>${cart.food.foodName}</td>
                                            <td>
                                                <fmt:formatNumber type="number" pattern="###,###"
                                                                  value="${cart.food.foodPrice - (cart.food.foodPrice * cart.food.discountPercent / 100)}"/> đ
                                            </td>
                                            <td>${cart.foodQuantity}</td>
                                            <td><c:set var="productPrice" value="${Double.parseDouble(cart.food.foodPrice- (cart.food.foodPrice * cart.food.discountPercent / 100)) * cart.foodQuantity}" />
                                                <c:set var="totalPrice" value="${totalPrice + productPrice}" /> 
                                                <fmt:formatNumber type="number" pattern="###,###"
                                                                  value="${Double.parseDouble(cart.food.foodPrice- (cart.food.foodPrice * cart.food.discountPercent / 100)) * cart.foodQuantity}"/> đ
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <form class="checkout-form" action="checkout" method="post">
                            <div class="row">
                                <!-- Hidden - User Account ID -->
                                <input type="hidden" id="txtAccountID" name="txtAccountID" value="${currentAccount.accountID}"/>
                                <div class="col-md-12">
                                    <h4>Thông tin giao món</h4>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="txtLastName" class="form-label">Họ</label>
                                            <input type="text" class="form-control" id="txtLastName" name="txtLastName" value="${customer.lastName}" required>
                                            <div class="form-error"></div>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="txtFirstName" class="form-label">Tên</label>
                                            <input type="text" class="form-control" id="txtFirstName" name="txtFirstName"value="${customer.firstName}" required>
                                            <div class="form-error"></div>
                                        </div>
                                    </div>


                                    <div class="row">
                                        <label for="txtGender" class="form-label">Giới tính</label>
                                        <div class="col-md-3 mx-3 form-check">
                                            <input class="form-check-input" type="radio" name="txtGender" id="genderMale" value="Nam" ${(customer.gender == "Nam") || (empty customer.gender) ? "checked" : ""}>
                                            <label class="form-check-label" for="genderMale">
                                                Nam
                                            </label>
                                        </div>
                                        <div class="col-md-3 form-check">
                                            <input class="form-check-input" type="radio" name="txtGender" id="genderFemale" value="Nữ" ${customer.gender == "Nữ" ? "checked" : ""}>
                                            <label class="form-check-label" for="genderFemale">
                                                Nữ
                                            </label>
                                        </div>
                                        <div class="col-md-3 form-check">
                                            <input class="form-check-input" type="radio" name="txtGender" id="genderOther" value="Khác" ${customer.gender == "Khác" ? "checked" : ""} >
                                            <label class="form-check-label" for="genderOther">
                                                Khác
                                            </label>
                                        </div>
                                        <div class="form-error"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="txtPhone" class="form-label">Điện thoại liên hệ</label>
                                        <input type="text" class="form-control" id="txtPhone" name="txtPhone" value="${customer.phone}" required>
                                        <div class="form-error"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="txtAddress" class="form-label">Địa chỉ giao hàng</label>
                                        <input type="text" class="form-control" id="txtAddress" name="txtAddress" value="${customer.address}" required>
                                        <div class="form-error"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="txtNote" class="form-label">Ghi chú</label>
                                        <textarea class="form-control" name ="txtNote" id="txtNote" rows="3"></textarea>
                                        <div class="form-error"></div>
                                    </div>

                                </div>
                                <div class="col-md-12">
                                    <h4>Phương thức thanh toán</h4>
                                    <fieldset>
                                        <input class="paying-method" checked type="radio" name="paymentMethod" id="cod-payment" value="3">
                                        <label for="cod-payment">Thanh toán khi nhận món (COD)</label>
                                    </fieldset>
                                    <fieldset>
                                        <input class="paying-method" type="radio" name="paymentMethod" id="vnpay-payment" value="1">
                                        <label for="vnpay-payment">Thanh toán Online (VNPAY)</label>
                                    </fieldset>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 my-3">
                                        <h4>Giảm giá</h4>
                                        <div class="input-group">
                                            <input value="${voucherCode}" type="text" class="form-control" id="txtVoucherCode" name="txtVoucherCode" readonly>
                                            <button class="btn btn-primary" type="button" id="voucherButton" 
                                                    data-bs-toggle="modal" data-bs-target="#voucher-modal" >
                                                Nhập mã giảm giá
                                            </button>
                                        </div>
                                        <div class="form-error"></div>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <span class="">${voucherStatus}</span>
                                </div>

                            </div>
                            <div class="col-md-12 text-md-end mt-3 d-flex justify-content-end align-items-center">
                                <h4 class="d-inline-flex">Tổng thanh toán:
                                  <fmt:formatNumber type="number"
                                                    pattern="###,###"
                                                    value="${totalPrice * ((voucherpercent == 1) ? 1 : (1 - voucherpercent))}" />đ
                                </h4>
                                <button type="submit"  id=”btnSubmit” name="btnSubmit" value="SubmitOrder" class="btn btn-primary ms-3" onclick="return checkAndCompleteOrder();" >Đặt món</button>
                            </div>
                    </div>
                </div>
            </div>
            <%@ include file="WEB-INF/jspf/common/components/footer.jspf" %>
        </main>
        <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
        <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
        <script src="assets/js/userNotify.js"></script>
        <script>
            function checkAndCompleteOrder() {
                if (checkPaying()) {
                    completeOrder();
                    return true; // Allow form submission
                } else {
                    return false; // Prevent form submission
                }
            }
            
            function completeOrder() {
                // Perform actions to process the order...

                // Clear the cart after the order is completed
                sessionStorage.removeItem("cart");

                // Optionally, update the cart display and total price
                updateCartDisplay();
                updateTotalPrice();
            }

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
    </body>
</html>