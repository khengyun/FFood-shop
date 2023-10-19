$(document).on("click", "#btn-update-food", function () {
  let foodID = $(this).attr("data-food-id");
  let foodType = $(this).attr("data-food-type");
  let foodName = $(this).attr("data-food-name");
  let foodPrice = $(this).attr("data-food-price");
  let discountPercent = $(this).attr("data-discount-percent");
  let imageURL = $(this).attr("data-image-url");

  // Set the values of the corresponding form inputs in the modal
  let modal = $("#update-food-modal");
  modal.find("input[name='txtFoodID']").attr("value", foodID);
  modal.find("#txtFoodName").attr("value", foodName);
  modal.find("#txtFoodPrice").attr("value", Number(foodPrice).toFixed(2));
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
  // Set the values of the corresponding form inputs in the modal
  $("#update-user-modal").find("input[name='txtAccountID']").attr("value", accountID);
  $("#update-user-modal").find("#txtAccountUsername").attr("value", username);
  $("#update-user-modal").find("#txtEmail").attr("value", email);
});

$(document).on("click", "#btn-delete-user", function () {
  let username = $(this).data("username");
  let accountID = $(this).data("account-id");
  let deleteAccountLink = "";
  if (accountID === null) {
    deleteAccountLink = "/admin";
  } else {
    deleteAccountLink = "/admin/user/delete/" + accountID;
  }
  // Set the values of the corresponding form inputs in the modal
  $("#delete-user-modal").find("#username").html(username + " ");
  $("#delete-user-modal").find("#deleteAccountLink").attr("href", deleteAccountLink);
});

$(document).on("click", "#btn-update-voucher", function () {
  let voucherID = $(this).data("voucher-id");
  let voucherName = $(this).data("voucher-name");
  let discount_percent = $(this).data("voucher-discount-percent");
  // Set the values of the corresponding form inputs in the modal
  $("#update-voucher-modal").find("input[name='txtvoucher_id']").attr("value", voucherID);
  $("#update-voucher-modal").find("#txtvoucher_name").attr("value", voucherName);
  $("#update-voucher-modal").find("#txtvoucher_discount_percent").attr("value", discount_percent);
});

$(document).on("click", "#btn-delete-voucher", function () {
  let voucherName = $(this).data("voucher-name");
  let voucherID = $(this).data("voucher-id");
  let deleteVoucherLink = "";
  if (voucherID === null) {
    deleteVoucherLink = "/admin";
  } else {
    deleteVoucherLink = "/admin/voucher/delete/" + voucherID;
  }
  // Set the values of the corresponding form inputs in the modal
  $("#delete-voucher-modal").find("#voucher_name").html(voucherName + " ");
  $("#delete-voucher-modal").find("#deleteVoucherLink").attr("href", deleteVoucherLink);
});

