// Requires importing toast.jspf

const successToast = document.getElementById('success');
const errorToast = document.getElementById('error');

// Initialize toasts
const toastElList = [successToast, errorToast];
const toastList = [...toastElList].map(toastEl => new bootstrap.Toast(toastEl, {
  delay: 3000
}))

const toastDetails = {
  "success-add-voucher": {
    title: "Thêm voucher thành công",
    message: "",
  },
  "success-update-voucher": {
    title: "Cập nhật voucher thành công",
    message: "",
  },
  "success-delete-voucher": {
    title: "Xoá voucher thành công",
    message: "",
  },
  "success-add-user": {
    title: "Thêm người dùng thành công",
    message: "",
  },
  "success-update-user": {
    title: "Cập nhật người dùng thành công",
    message: "",
  },
  "success-delete-user": {
    title: "Xoá người dùng thành công",
    message: "",
  },
  "success-add-role": {
    title: "Thêm vai trò thành công",
    message: "",
  },
  "success-update-role": {
    title: "Cập nhật vai trò thành công",
    message: "",
  },
  "success-delete-role": {
    title: "Xoá vai trò thành công",
    message: "",
  },
  "success-add-order": {
    title: "Thêm đơn thành công",
    message: "",
  },
  "success-update-order": {
    title: "Cập nhật đơn thành công",
    message: "",
  },
  "success-delete-order": {
    title: "Xoá đơn thành công",
    message: "",
  },
  "success-next-order": {
    title: "Chuyển trạng thái đơn thành công",
    message: "",
  },
  "success-add-food": {
    title: "Thêm món thành công",
    message: "",
  },
  "success-update-food": {
    title: "Cập nhật món thành công",
    message: "",
  },
  "success-delete-food": {
    title: "Xoá món thành công",
    message: "",
  },
  "error-add-voucher": {
    title: "Không thể thêm voucher",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-voucher-existing-voucher": {
    title: "Không thể thêm voucher",
    message: "Voucher này đã tồn tại.",
  },
  "error-update-voucher": {
    title: "Không thể cập nhật voucher",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-delete-voucher": {
    title: "Không thể xoá voucher",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-user": {
    title: "Không thể thêm người dùng",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-user-existing-account": {
    title: "Không thể thêm người dùng",
    message: "Tài khoản người dùng này đã tồn tại.",
  },
  "error-update-user": {
    title: "Không thể cập nhật người dùng",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-delete-user": {
    title: "Không thể xoá người dùng",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-role": {
    title: "Không thể thêm vai trò",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-role-existing-account": {
    title: "Không thể thêm vai trò",
    message: "Tài khoản nhân viên này đã tồn tại.",
  },
  "error-update-role": {
    title: "Không thể cập nhật vai trò",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-delete-role": {
    title: "Không thể xoá vai trò",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-order": {
    title: "Không thể thêm đơn",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-update-order": {
    title: "Không thể cập nhật đơn",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-delete-order": {
    title: "Không thể xoá đơn",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-next-order": {
    title: "Không thể chuyển trạng thái đơn",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-food": {
    title: "Không thể thêm món",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-add-food-existing-food": {
    title: "Không thể thêm món",
    message: "Món này đã tồn tại.",
  },
  "error-update-food": {
    title: "Không thể cập nhật món",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
  "error-delete-food": {
    title: "Không thể xoá món",
    message: "Đã có lỗi hệ thống xảy ra.",
  },
}

// Get the message from the session scope
let toastContent = successToast.getAttribute('data-message');

function initModal(id) {
  const modal = document.getElementById(`${id}`);
  // Construct the attribute name using the id (e.g. trigger-otp-modal)
  const attributeName = "data-" + id.replace("-modal", "");
  if (modal.getAttribute(attributeName) && modal.getAttribute(attributeName) != "0") {
    const modalInstance = new bootstrap.Modal(`#${id}`, {});
    modal.setAttribute(attributeName, 'true');
    modalInstance.show();
  }
}

function showToast(toast, toastContent) {
  const toastElement = bootstrap.Toast.getOrCreateInstance(toast);
  const toastTitle = toast.getElementsByClassName('toast-title')[0];
  const toastMessage = toast.getElementsByClassName('toast-message')[0];
  
  toastTitle.innerHTML = toastDetails[toastContent].title;
  toastMessage.innerHTML = toastDetails[toastContent].message;
  
  toastElement.show();
}
function hideToast(toast) {
  bootstrap.Toast.getInstance(toast).hide();
}

// Initialize either of these modals
$(document).ready(function () {
  //initModal("trigger-otp-modal");

  toastContent = successToast.getAttribute('data-message');
  // Show success or error toast based on the message
  if (toastContent) {
    if (toastContent.includes('success')) {
      hideToast(errorToast);
      showToast(successToast, toastContent);
    } else if (toastContent.includes('error')) {
      hideToast(successToast);
      showToast(errorToast, toastContent);
    }
  }
});
