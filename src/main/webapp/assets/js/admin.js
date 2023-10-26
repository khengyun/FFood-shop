$(document).on("click", "#btn-update-food", function () {
    let foodID = $(this).attr("data-food-id");
    let foodType = $(this).attr("data-food-type");
    let foodName = $(this).attr("data-food-name");
    let foodDescription = $(this).attr("data-food-description");
    let foodPrice = $(this).attr("data-food-price");
    let foodStatus = $(this).attr("data-food-status");
    let foodRate = $(this).attr("data-food-rate");
    let discountPercent = $(this).attr("data-discount-percent");
    let imageURL = $(this).attr("data-image-url");

    // Set the values of the corresponding form inputs in the modal
    let modal = $("#update-food-modal");
    modal.find("input[name='txtFoodID']").attr("value", foodID);
    modal.find("#txtFoodName").attr("value", foodName);
    modal.find("#txtFoodDescription").val(foodDescription);
    modal.find("#txtFoodPrice").attr("value", Number(foodPrice).toFixed(2));
    modal.find("#txtFoodStatus").val(foodStatus);
    modal.find("#txtFoodRate").val(foodRate);

    modal.find("#txtDiscountPercent").attr("value", discountPercent);
    modal.find("#txtImageURL").attr("value", imageURL);
    for (let i = 1; i < 7; i++) {
        let foodTypes = {1: "Cơm", 2: "Mì", 3: "Bánh mì", 4: "Đồ ăn vặt", 5: "Tráng miệng", 6: "Đồ uống"};
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
    let accountID = $(this).data("account-id");
    let username = $(this).data("username");
    let email = $(this).data("email");   
    let modal = $("#update-user-modal");
    
    // Set the values of the corresponding form inputs in the modal
    modal.find("input[name='txtAccountID']").attr("value", accountID);
    modal.find("#txtAccountUsername").attr("value", username);
    modal.find("#txtEmail").attr("value", email);
});

$(document).on("click", "#btn-delete-user", function () {
    let modal = $("#delete-user-modal");
    // Clear the list of users in the modal every time the button is clicked
    modal.find(".modal-body ul").empty();

    // Retrieves selected rows' data in JSON format, so that it can be iterated
    let users = JSON.parse($(this).attr("data-users"));

    // Populate the list of users in the modal
    for (let userId in users) {
        let userName = users[userId];
        modal.find("#delete-user-list").append("<li>" + userName + "</li>");
    }
    
    // Keep users IDs as strings
    let userIds = Object.keys(users).toString();

    // Set the values to the hidden input in the modal
    modal.find("input[name='userData']").attr("value", userIds);
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