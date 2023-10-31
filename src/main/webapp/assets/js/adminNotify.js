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


//show success for food
$(document).ready(function () {
  if (window.location.hash === '#success_add_food') {
    $('#success_add_food').modal('show');
    setTimeout(function () {
      $('#success_add_food').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_update_food') {
    $('#success_update_food').modal('show');
    setTimeout(function () {
      $('#success_update_food').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_delete_food') {
    $('#success_delete_food').modal('show');
    setTimeout(function () {
      $('#success_delete_food').modal('hide');
    }, 2000);
  };
  
});


//show success for user
$(document).ready(function () {
  if (window.location.hash === '#success_add_user') {
    $('#success_add_user').modal('show');
    setTimeout(function () {
      $('#success_add_user').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_update_user') {
    $('#success_update_user').modal('show');
    setTimeout(function () {
      $('#success_update_user').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_delete_user') {
    $('#success_delete_user').modal('show');
    setTimeout(function () {
      $('#success_delete_user').modal('hide');
    }, 2000);
  }
});

//show success for role
$(document).ready(function () {
  if (window.location.hash === '#success_add_role') {
    $('#success_add_role').modal('show');
    setTimeout(function () {
      $('#success_add_role').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_update_role') {
    $('#success_update_role').modal('show');
    setTimeout(function () {
      $('#success_update_role').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_delete_role') {
    $('#success_delete_role').modal('show');
    setTimeout(function () {
      $('#success_delete_role').modal('hide');
    }, 2000);
  }
});

//show success for order
$(document).ready(function () {
  
  if (window.location.hash === '#success_update_order') {
    $('#success_update_order').modal('show');
    setTimeout(function () {
      $('#success_update_order').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_delete_order') {
    $('#success_delete_order').modal('show');
    setTimeout(function () {
      $('#success_delete_order').modal('hide');
    }, 2000);
  }
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

//show fail for food
$(document).ready(function () {
  if (window.location.hash === '#failure_add_food') {
    $('#failure_add_food').modal('show');
    setTimeout(function () {
      $('#failure_add_food').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_add_food_exist') {
    $('#failure_add_food_exist').modal('show');
    setTimeout(function () {
      $('#failure_add_food_exist').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_update_food') {
    $('#failure_update_food').modal('show');
    setTimeout(function () {
      $('#failure_update_food').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_delete_food') {
    $('#failure_delete_food').modal('show');
    setTimeout(function () {
      $('#failure_delete_food').modal('hide');
    }, 2000);
  };
  
});

//show fail for user
$(document).ready(function () {
  if (window.location.hash === '#failure_add_user') {
    $('#failure_add_user').modal('show');
    setTimeout(function () {
      $('#failure_add_user').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_add_user_exist') {
    $('#failure_add_user_exist').modal('show');
    setTimeout(function () {
      $('#failure_add_user_exist').modal('hide');
    }, 2000);
  };
   if (window.location.hash === '#failure_update_user') {
    $('#failure_update_user').modal('show');
    setTimeout(function () {
      $('#failure_update_user').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_delete_user') {
    $('#failure_delete_user').modal('show');
    setTimeout(function () {
      $('#failure_delete_user').modal('hide');
    }, 2000);
  };
  
});

//show fail for role
$(document).ready(function () {
  if (window.location.hash === '#failure_add_role') {
    $('#failure_add_role').modal('show');
    setTimeout(function () {
      $('#failure_add_role').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_add_role_exist') {
    $('#failure_add_role_exist').modal('show');
    setTimeout(function () {
      $('#failure_add_role_exist').modal('hide');
    }, 2000);
  };
  
   if (window.location.hash === '#failure_update_role') {
    $('#failure_update_role').modal('show');
    setTimeout(function () {
      $('#failure_update_role').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_delete_role') {
    $('#failure_delete_role').modal('show');
    setTimeout(function () {
      $('#failure_delete_role').modal('hide');
    }, 2000);
  };
});
  
  //show fail for order
$(document).ready(function () {
   if (window.location.hash === '#failure_update_order') {
    $('#failure_update_order').modal('show');
    setTimeout(function () {
      $('#failure_update_order').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_delete_order') {
    $('#failure_delete_order').modal('show');
    setTimeout(function () {
      $('#failure_delete_order').modal('hide');
    }, 2000);
  };
});

// =================================== Order ======================================


//$(document).ready(function () {
//   if (window.location.hash === '#history') {
//    $('#OrderLog-modal').modal('show');
//  };
//});
