$(document).on("click", "#btn-update-voucher", function () {

    let voucherID = $(this).attr("data-voucher-id");
    let voucherName = $(this).attr("data-voucher-name");
    let voucherCode = $(this).attr("data-voucher-code");
    let discount_percent = $(this).attr("data-voucher-discount-percent");
    let voucherQuantity = $(this).attr("data-voucher-quantity");
    let voucherStatus = $(this).attr("data-voucher-status");
    let voucherDate = $(this).attr("data-voucher-date");

    // Set the values of the corresponding form inputs in the modal
    $("#update-voucher-modal").find("input[name='txtvoucher_id']").attr("value", voucherID);
    $("#update-voucher-modal").find("#txtvoucher_name").attr("value", voucherName);
    $("#update-voucher-modal").find("#txtvoucher_code").attr("value", voucherCode);
    $("#update-voucher-modal").find("#txtvoucher_discount_percent").attr("value", discount_percent);
    $("#update-voucher-modal").find("#txtvoucher_quantity").attr("value", voucherQuantity);
    $("#update-voucher-modal").find("#txtvoucher_status").val(voucherStatus);
    $("#update-voucher-modal").find("#txtvoucher_date").val(voucherDate);

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
    modal.find("#txtDiscountPercent").attr("value", discountPercent);
    for (let i = 1; i < 7; i++) {
        let foodTypes = {1: "Mì và Bún", 2: "Bánh và Bánh Mì", 3: "Hải Sản", 4: "Món Ăn Truyền Thống", 5: "Món Ăn Châu Á", 6: "Món Thịt", 7: "Món ăn nhanh", 8: "Món ăn nhẹ", 9:"Món Tráng Miệng", 10: "Đồ uống"};
        if (foodType === foodTypes[i]) {
            modal.find("#txtFoodTypeID option[value = " + i + "]").attr("selected", "selected");
        } else {
            modal.find("#txtFoodTypeID option[value = " + i + "]").removeAttr("selected");
        }
    }
});

$(document).ready(function () {
  // Creates a dictionary of tabIDs and their corresponding tab links
  const tabLinksDict = {
    null: "home",
    "0": "home",
    "1": "foods",
    "2": "vouchers"
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
});
