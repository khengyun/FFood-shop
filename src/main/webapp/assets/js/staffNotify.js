//===============================SUCCESS================================



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

// ===================================FAILURE======================================

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
