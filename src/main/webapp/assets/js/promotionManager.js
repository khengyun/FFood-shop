$(document).on("click", "#btn-update-voucher", function () {
  let voucherID = $(this).data("voucher-id");
  let voucherName = $(this).data("voucher-name");
  let voucherCode = $(this).data("voucher-code");
  let discount_percent = $(this).data("voucher-discount-percent");
  let voucherQuantity = $(this).data("voucher-quantity");
  let voucherStatus = $(this).data("voucher-status");
//  let voucherDate = $(this).data("voucher-date");

  // Set the values of the corresponding form inputs in the modal
  $("#update-voucher-modal").find("input[name='txtvoucher_id']").attr("value", voucherID);
  $("#update-voucher-modal").find("#txtvoucher_name").attr("value", voucherName);
  $("#update-voucher-modal").find("#txtvoucher_code").attr("value", voucherCode);
  $("#update-voucher-modal").find("#txtvoucher_discount_percent").attr("value", discount_percent);
  $("#update-voucher-modal").find("#txtvoucher_quantity").attr("value", voucherQuantity);
  $("#update-voucher-modal").find("#txtvoucher_status").attr("value", voucherStatus);
});

$(document).on("click", "#btn-delete-voucher", function () {
  let voucherName = $(this).data("voucher-name");
  let voucherID = $(this).data("voucher-id");
  let deleteVoucherLink = "";
  if (voucherID === null) {
    deleteVoucherLink = "/promotionManager";
  } else {
    deleteVoucherLink = "/promotionManager/voucher/delete/" + voucherID;
  }
  // Set the values of the corresponding form inputs in the modal
  $("#delete-voucher-modal").find("#voucher_name").html(voucherName + " ");
  $("#delete-voucher-modal").find("#deleteVoucherLink").attr("href", deleteVoucherLink);
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

