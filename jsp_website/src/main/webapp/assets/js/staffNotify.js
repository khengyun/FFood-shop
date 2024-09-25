// Requires importing toast.jspf

const successToast = document.getElementById('success');
const errorToast = document.getElementById('error');

// Initialize toasts
const toastElList = [successToast, errorToast];
const toastList = [...toastElList].map(toastEl => new bootstrap.Toast(toastEl, {
  delay: 3000
}))

const toastDetails = {
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
