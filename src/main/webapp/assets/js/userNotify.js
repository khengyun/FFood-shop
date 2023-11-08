// Requires importing toast.jspf

const successToast = document.getElementById('success');
const errorToast = document.getElementById('error');

// Initialize toasts
const toastElList = [successToast, errorToast];
const toastList = [...toastElList].map(toastEl => new bootstrap.Toast(toastEl, {
  delay: 3000
}))

const toastDetails = {
  "success-cart": {
    title: "Thêm món thành công",
    message: "Đã thêm món vào giỏ hàng.",
  },
  "success-order": {
    title: "Đặt món thành công",
    message: "",
  },
  "success-cancel-order": {
    title: "Huỷ đơn thành công",
    message: "",
  },
  "success-register": {
    title: "Đăng ký tài khoản thành công",
    message: "",
  },
  "success-change-password": {
    title: "Đổi mật khẩu thành công",
    message: "",
  },
  "success-update-info": {
    title: "Cập nhật thông tin thành công",
    message: "",
  },
  "error-cart": {
    title: "Không thể thêm món",
    message: "Hãy thử lại sau ít phút, hoặc liên hệ chúng tôi để đặt món.",
  },
  "error-order": {
    title: "Không thể đặt đơn",
    message: "Hãy thử lại sau ít phút, hoặc liên hệ chúng tôi đặt đơn này.",
  },
  "error-cancel-order": {
    title: "Không thể huỷ đơn",
    message: "Đơn đặt đã quá thời gian cho phép huỷ.",
  },
  "error-register": {
    title: "Không thể đăng ký tài khoản",
    message: "Hãy thử lại sau ít phút, hoặc liên hệ chúng tôi để tạo tài khoản.",
  },
  "error-register-existing-email": {
    title: "Không thể đăng ký tài khoản",
    message: "Đã có tài khoản đăng ký bằng email này.",
  },
  "error-verify-email": {
    title: "Không thể xác thực email",
    message: "Liên hệ chúng tôi để xác thực tài khoản.",
  },
  "error-send-otp": {
    title: "Không thể gửi mã OTP",
    message: "Hãy thử lại sau ít phút, hoặc liên hệ chúng tôi để xác thực tài khoản.",
  },
  "error-wrong-otp": {
    title: "Mã xác thực không đúng",
    message: "Hãy nhập lại mã xác thực, hoặc liên hệ chúng tôi để xác thực tài khoản.",
  },
  "error-change-password": {
    title: "Không thể đổi mật khẩu",
    message: "Hãy thử lại sau ít phút, hoặc liên hệ chúng tôi để đổi mật khẩu.",
  },
  "error-update-info": {
    title: "Không thể cập nhật thông tin",
    message: "Hãy thử lại sau ít phút, hoặc liên hệ chúng tôi để cập nhật thông tin.",
  },
  "error-login": {
    title: "Không thể đăng nhập",
    message: "Hãy thử lại sau ít phút.",
  },
  "error-login-credentials": {
    title: "Không thể đăng nhập",
    message: "Hãy kiểm tra lại email và mật khẩu của bạn.",
  },
  "error-no-email-found": {
    title: "Không tìm thấy email",
    message: "Hãy kiểm tra lại email của bạn.",
  },
  "error-close-time": {
    title: "Quán hiện đang đóng cửa",
    message: "Vui lòng đặt món từ 8:00 - 20:00.",
  },
  "error-404": {
    title: "Không tìm thấy trang",
    message: "Đường dẫn hiện không tồn tại.",
  }
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


