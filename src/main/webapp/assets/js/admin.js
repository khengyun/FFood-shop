// Function to parse a float number from a string, using the current locale of the page
  // https://stackoverflow.com/questions/59678901/using-parsefloat-in-different-locales
  function localeParseFloat(s) {
    // Get the thousands and decimal separator characters used in the locale.
    let [,thousandsSeparator,,,,decimalSeparator] = 1111.1.toLocaleString(pageLocale);
    // Remove thousand separators, and put a point where the decimal separator occurs
    s = Array.from(s, c => c === thousandsSeparator ? "" 
                        : c === decimalSeparator   ? "." : c).join("");
    // Now it can be parsed
    return parseFloat(s);
}

$(document).on("click", "#btn-update-food", function () {
    let foodID = $(this).attr("data-food-id");
    let foodType = $(this).attr("data-food-type");
    let foodName = $(this).attr("data-food-name");
    let foodDescription = $(this).attr("data-food-description");
    let foodPrice = $(this).attr("data-food-price");
    let foodQuantity = $(this).attr("data-food-quantity");
    let foodStatus = $(this).attr("data-food-status");
    let foodRate = $(this).attr("data-food-rate");
    let discountPercent = $(this).attr("data-discount-percent");
    let imageURL = $(this).attr("data-image-url");

    // Set the values of the corresponding form inputs in the modal
    let modal = $("#update-food-modal");
    modal.find("input[name='txtFoodID']").attr("value", foodID);
    modal.find("#txtFoodName").attr("value", foodName);
    modal.find("#txtFoodDescription").val(foodDescription);
    foodPrice = localeParseFloat(foodPrice);
    modal.find("#txtFoodPrice").attr("value", foodPrice);
    modal.find("#txtFoodQuantity").attr("value", foodQuantity);
    modal.find("#txtFoodStatus").val(foodStatus);
    modal.find("#txtFoodRate").val(foodRate);

    modal.find("#txtDiscountPercent").attr("value", discountPercent);
    modal.find("#txtImageURL").attr("value", imageURL);
    for (let i = 1; i < 10; i++) {
        let foodTypes = {1: "Mì và Bún", 2: "Bánh và Bánh Mì", 3: "Hải Sản", 4: "Món Ăn Truyền Thống", 5: "Món Ăn Châu Á", 6: "Món Thịt", 7: "Món ăn nhanh", 8: "Món ăn nhẹ", 9:"Món Tráng Miệng", 10: "Đồ uống"};
        if (foodType === foodTypes[i]) {
            modal.find("#txtFoodTypeID option[value = " + i + "]").attr("selected", "selected");
        } else {
            modal.find("#txtFoodTypeID option[value = " + i + "]").removeAttr("selected");
        }
    }
});

$(document).on("click", "#btn-delete-food", function () {
    let modal = $("#delete-food-modal");
    // Clear the list of foods in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let foods = JSON.parse($(this).attr("data-foods"));

    // Populate the list of foods in the modal
    for (let foodId in foods) {
        let foodName = foods[foodId];
        modal.find("#delete-food-list").append("<li>" + foodName + "</li>");
    }

    // Keep food IDs as strings
    let foodIds = Object.keys(foods).toString();

    // Set the values to the hidden input in the modal
    modal.find("input[name='foodData']").attr("value", foodIds);
});

$(document).on("click", "#btn-update-user", function () {
    let userID = $(this).attr("data-user-id");
    let customerID = $(this).attr("data-user-customerid");
    let username = $(this).attr("data-user-username");
    let lastname = $(this).attr("data-user-lastname");
    let firstname = $(this).attr("data-user-firstname");
    let gender = $(this).attr("data-user-gender");
    let phonenumber = $(this).attr("data-user-phone");
    let email = $(this).attr("data-user-email");
    let address = $(this).attr("data-user-address");

    let modal = $("#update-user-modal");
    // Set the values of the corresponding form inputs in the modal
    modal.find("input[name='txtUserID']").attr("value", userID);
    modal.find("input[name='txtCustomerID']").attr("value", customerID);

    modal.find("#txtAccountUsername").attr("value", username);
    modal.find("#txtLastName").attr("value", lastname);
    modal.find("#txtFirstName").attr("value", firstname);
    modal.find("#txtGender").val(gender);
    modal.find("#txtPhoneNumber").val(phonenumber);
    modal.find("#txtEmail").val(email);
    modal.find("#txtAddress").val(address);
});

$(document).on("click", "#btn-delete-user", function () {
    let modal = $("#delete-user-modal");
    // Clear the list of users in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let users = JSON.parse($(this).attr("data-users"));
    let customers = JSON.parse($(this).attr("data-customers"));

    // Populate the list of users in the modal
    for (let userId in users) {
        let userName = users[userId];
        modal.find("#delete-user-list").append("<li>" + userName + "</li>");
    }

    // Keep users IDs as strings
    let userIds = Object.keys(users).toString();
    let customerIds = Object.keys(customers).toString();


    // Set the values to the hidden input in the modal
    modal.find("input[name='userData']").attr("value", userIds);
    modal.find("input[name='customerData']").attr("value", customerIds);
});

$(document).on("click", "#btn-update-voucher", function () {
    let voucherID = $(this).attr("data-voucher-id");
    let voucherName = $(this).attr("data-voucher-name");
    let voucherCode = $(this).attr("data-voucher-code");
    let discount_percent = $(this).attr("data-voucher-discount-percent");
    let voucherQuantity = $(this).attr("data-voucher-quantity");
    let voucherStatus = $(this).attr("data-voucher-status");
    let voucherDate = $(this).attr("data-voucher-date");

    let modal = $("#update-voucher-modal");
    // Set the values of the corresponding form inputs in the modal
    modal.find("input[name='txtvoucher_id']").attr("value", voucherID);
    modal.find("#txtvoucher_name").attr("value", voucherName);
    modal.find("#txtvoucher_code").attr("value", voucherCode);
    modal.find("#txtvoucher_discount_percent").attr("value", discount_percent);
    modal.find("#txtvoucher_quantity").attr("value", voucherQuantity);
    modal.find("#txtvoucher_status").val(voucherStatus);
    modal.find("#txtvoucher_date").val(voucherDate);
});


$(document).on("click", "#btn-add-voucher", function () {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let randomCode = '';
    for (let i = 0; i < 16; i++) {
        const randomIndex = Math.floor(Math.random() * characters.length);
        randomCode += characters[randomIndex];
    }
    // Set the values of the corresponding form inputs in the modal
    $("#add-voucher-modal").find("#txtvoucher_code").attr("value", randomCode);
});

$(document).on("click", "#btn-delete-voucher", function () {
    let modal = $("#delete-voucher-modal");
    // Clear the list of foods in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let vouchers = JSON.parse($(this).attr("data-vouchers"));

    // Populate the list of foods in the modal
    for (let voucherId in vouchers) {
        let voucheName = vouchers[voucherId];
        modal.find("#delete-voucher-list").append("<li>" + voucheName + "</li>");
    }

    // Keep food IDs as strings
    let voucherIds = Object.keys(vouchers).toString();

    // Set the values to the hidden input in the modal
    modal.find("input[name='voucherData']").attr("value", voucherIds);
});

$(document).on("click", "#btn-update-role", function () {
    let accountID = $(this).attr("data-role-accountid");
    let roleID = $(this).attr("data-role-roleid");
    let roleUsername = $(this).attr("data-role-username");
    let roleFullname = $(this).attr("data-role-fullname");
    let roleEmail = $(this).attr("data-role-email");
    let roleType = $(this).attr("data-role-type");
    
    let modal = $("#update-role-modal");
    
    // Set the values of the corresponding form inputs in the modal
    modal.find("input[name='txtAccountID']").attr("value", accountID);
    modal.find("input[name='txtRoleID']").attr("value", roleID);

    modal.find("#txtAccountUsername").attr("value", roleUsername);
    modal.find("#txtAccountFullname").attr("value", roleFullname);
    modal.find("#txtEmail").attr("value", roleEmail);
    modal.find("#txtAccountRole").val(roleType);
});

$(document).on("click", "#btn-delete-role", function () {
    let modal = $("#delete-role-modal");
    // Clear the list of foods in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let accounts = JSON.parse($(this).attr("data-accounts")); //accounts contain accountID of username
    let roles = JSON.parse($(this).attr("data-roles")); // roles contain ID and fullname of this role
    let temp1s = JSON.parse($(this).attr("data-temp1s")); // temp1s contain the account_type "staff" of roleID
    let temp2s = JSON.parse($(this).attr("data-temp2s")); // temp2s contain the account_type "promotionManager" of roleID
    
    //     Populate the list of accounts in the modal
    for (let accountId in accounts) {
        let accountName = accounts[accountId];
        modal.find("#delete-account-list").append("<li>" + accountName + "</li>");
    }
    
    // Populate the list of roles in the modal
    for (let roleId in roles) {
        let roleName = roles[roleId];
        modal.find("#delete-role-list").append("<li>" + roleName + "</li>");
    }

    // Keep food IDs as strings
    let accountIds = Object.keys(accounts).toString();
    let roleIds = Object.keys(roles).toString();
    let temp1Ids = Object.keys(temp1s).toString();
    let temp2Ids = Object.keys(temp2s).toString();

    // Set the values to the hidden input in the modal
    modal.find("input[name='accountData']").attr("value", accountIds);
    modal.find("input[name='roleData']").attr("value", roleIds);
    modal.find("input[name='temp1Data']").attr("value", temp1Ids);
    modal.find("input[name='temp2Data']").attr("value", temp2Ids);
});

$(document).on("click", "#btn-update-order", function () {
    let orderID = $(this).attr("data-order-id");
    let phoneNumber = $(this).attr("data-order-phonenumber");
    let address = $(this).attr("data-order-address");
    let Ordernote = $(this).attr("data-order-note");
    let paymentMethod = $(this).attr("data-order-paymentmethod");
    let orderStatus = $(this).attr("data-order-status");
    let orderTotal = $(this).attr("data-order-total");

    let modal = $("#update-order-modal");
    // Set the values of the corresponding form inputs in the modal
    modal.find("input[name='txtOrderID']").attr("value", orderID);
    modal.find("#txtPhoneNumber").attr("value", phoneNumber);
    modal.find("#txtOrderAddress").val(address);
    modal.find("#txtPaymentMethod").val(paymentMethod);
    modal.find("#txtOrderNote").val(Ordernote);
    modal.find("#txtOrderStatus").val(orderStatus);
    modal.find("#txtOrderTotal").val(orderTotal);
});

$(document).on("click", "#btn-delete-order", function () {
    let modal = $("#delete-order-modal");
    // Clear the list of foods in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let orders = JSON.parse($(this).attr("data-orders")); //accounts contain accountID of username
    
    // Populate the list of roles in the modal
    for (let orderId in orders) {
        let orderID = orders[orderId];
        modal.find("#delete-order-list").append("<li>" + orderID + "</li>");
    }

    // Keep food IDs as strings
    let orderIds = Object.keys(orders).toString();

    // Set the values to the hidden input in the modal
    modal.find("input[name='orderData']").attr("value", orderIds);
});

$(document).on("click", "#btn-next-order", function () {
    let modal = $("#next-order-modal");
    // Clear the list of foods in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let orders = JSON.parse($(this).attr("data-orders")); //accounts contain accountID of username
    
    // Populate the list of roles in the modal
    for (let orderId in orders) {
        let orderID = orders[orderId];
        modal.find("#next-order-list").append("<li>" + orderID + "</li>");
    }

    // Keep food IDs as strings
    let orderIds = Object.keys(orders).toString();

    // Set the values to the hidden input in the modal
    modal.find("input[name='orderData']").attr("value", orderIds);
});


$(document).on("click", "#btn-history-order", function () {
        let orderID = $(this).attr("data-order-id");
        window.location.href = '/admin/history/' + orderID;
//        window.location.reload();
});

$(document).ready(function () {
  // Creates a dictionary of tabIDs and their corresponding tab links
  const tabLinksDict = {
    null: "home",
    "0": "home",
    "1": "insights",
    "2": "vouchers",
    "3": "foods",
    "4": "users",
    "5": "roles",
    "6": "orders",
  };

  let tabID = document.getElementsByClassName("tab-content").item(0).getAttribute("data-initial-tab");
  
  if (tabID === null || tabID === undefined || tabID === "") {
    tabID = "0";
  }
  
  // Get the initial tabID ("home", "foods", etc. from the tabID index)
  const initialTabID = tabLinksDict[tabID];

  // Get a list of all tab links that correspond to the initial tab
  // Because the page has a minimized and maximized version,
  // each tab has 2 triggering links
  const tabLinks = document.querySelectorAll("ul [data-bs-target='#" + initialTabID + "']");
  tabLinks[0].click();

  // Check for the data-order-history attribute
  const needsOrderHistory =
    document
      .getElementsByClassName("tab-content")
      .item(0)
      .getAttribute("data-order-history") !== "0" &&
    document
      .getElementsByClassName("tab-content")
      .item(0)
      .getAttribute("data-order-history") != null &&
    document
      .getElementsByClassName("tab-content")
      .item(0)
      .getAttribute("data-order-history") != "";
  if (needsOrderHistory) {
    // Show the order history modal
    const modal = new bootstrap.Modal("#history-order-modal");
    modal.show();
  }
})