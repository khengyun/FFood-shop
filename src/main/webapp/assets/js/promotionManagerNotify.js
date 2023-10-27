//===============================SUCCESS================================
//show success for voucher
$(document).ready(function () {
  if (window.location.hash === '#success_add_voucher') {
    $('#success_add_voucher').modal('show');
    setTimeout(function () {
      $('#success_add_voucher').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_update_voucher') {
    $('#success_update_voucher').modal('show');
    setTimeout(function () {
      $('#success_update_voucher').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_delete_voucher') {
    $('#success_delete_voucher').modal('show');
    setTimeout(function () {
      $('#success_delete_voucher').modal('hide');
    }, 2000);
  };
});

// ===================================FAILURE======================================

//show fail for voucher
$(document).ready(function () {
  if (window.location.hash === '#failure_add_voucher') {
    $('#failure_add_voucher').modal('show');
    setTimeout(function () {
      $('#failure_add_voucher').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_add_voucher_exist') {
    $('#failure_add_voucher_exist').modal('show');
    setTimeout(function () {
      $('#failure_add_voucher_exist').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_update_voucher') {
    $('#failure_update_voucher').modal('show');
    setTimeout(function () {
      $('#failure_update_voucher').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_delete_voucher') {
    $('#failure_delete_voucher').modal('show');
    setTimeout(function () {
      $('#failure_delete_voucher').modal('hide');
    }, 2000);
  };
});