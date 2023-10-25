//===============================SUCCESS================================
//show success register account
$(document).ready(function () {
  if (window.location.hash === '#success_register') {
    $('#success_register').modal('show');
    setTimeout(function () {
      $('#success_register').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#success_changePassword') {
    $('#success_changePassword').modal('show');
    setTimeout(function () {
      $('#success_changePassword').modal('hide');
    }, 2000);
  };
});


// =================================== failure ======================================

$(document).ready(function () {
  if (window.location.hash === '#failure') {
    $('#failure').modal('show');
    setTimeout(function () {
      $('#failure').modal('hide');
    }, 2000);
  };
//Login
  if (window.location.hash === '#failure_login') {
    $('#failure_login').modal('show');
    setTimeout(function () {
      $('#failure_login').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_login_info') {
    $('#failure_login_info').modal('show');
    setTimeout(function () {
      $('#failure_login_info').modal('hide');
    }, 2000);
  };
//  Register
  if (window.location.hash === '#failure_register') {
    $('#failure_register').modal('show');
    setTimeout(function () {
      $('#failure_register').modal('hide');
    }, 2000);
  };
  if (window.location.hash === '#failure_register_email') {
    $('#failure_register_email').modal('show');
    setTimeout(function () {
      $('#failure_register_email').modal('hide');
    }, 2000);
  };
  
  if (window.location.hash === '#failure_changePassword') {
    $('#failure_changePassword').modal('show');
    setTimeout(function () {
      $('#failure_changePassword').modal('hide');
    }, 2000);
  };
 
//  Verify
   if (window.location.hash === '#verify_OTP') {
    $('#verify_OTP').modal('show');
    setTimeout(function () {
      $('#verify_OTP').modal('hide');
    }, 120000);
  };
  
  if (window.location.hash === '#changePass_modal') {
    $('#changePass_modal').modal('show');
  };
  
});
