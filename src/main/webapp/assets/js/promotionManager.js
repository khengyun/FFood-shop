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
    deleteVoucherLink = "/promotionManager";
  } else {
    deleteVoucherLink = "/promotionManager/voucher/delete/" + voucherID;
  }
  // Set the values of the corresponding form inputs in the modal
  $("#delete-voucher-modal").find("#voucher_name").html(voucherName + " ");
  $("#delete-voucher-modal").find("#deleteVoucherLink").attr("href", deleteVoucherLink);
});
