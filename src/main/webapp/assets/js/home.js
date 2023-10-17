var sorted = null; 
var notSort = null; 
var preButton = null; 

//show success order food
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#success') {
    $('#success').modal('show');
    setTimeout(function () {
      $('#success').modal('hide');
    }, 3000);
  }
});

//show success register account
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#success_register') {
    $('#success_register').modal('show');
    setTimeout(function () {
      $('#success_register').modal('hide');
    }, 3000);
  }
});

//show success change password
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#success_changePassword') {
    $('#success_changePassword').modal('show');
    setTimeout(function () {
      $('#success_changePassword').modal('hide');
    }, 3000);
  }
});

//show fail order food
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#failure') {
    $('#failure').modal('show');
    setTimeout(function () {
      $('#failure').modal('hide');
    }, 3000);
  }
});

//show fail login
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']");
  if (window.location.hash === '#failure_login') {
    $('#failure_login').modal('show');
    setTimeout(function () {
      $('#failure_login').modal('hide');
    }, 3000);
  }
});

//show fail register
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#failure_register') {
    $('#failure_register').modal('show');
    setTimeout(function () {
      $('#failure_register').modal('hide');
    }, 3000);
  }
});

//show fail change password
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#failure_changePassword') {
    $('#failure_changePassword').modal('show');
    setTimeout(function () {
      $('#failure_changePassword').modal('hide');
    }, 3000);
  }
});

//show verify OTP modal
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#verify_OTP') {
    $('#verify_OTP').modal('show');
    setTimeout(function () {
      $('#verify_OTP').modal('hide');
    }, 120000);
  }
});

//show change password modal
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === '#changePass_modal') {
    $('#changePass_modal').modal('show');
  }
});

$(document).on("click", ".btn-cate", function () {
  let foodTypeID = $(this).data("food-type-id");
  let foodList = document.querySelectorAll("div[id^='food-']");
  if (sorted === null) {
    sorted = Array.from(notSort); 
    sorted.sort(function (a, b) {
      let aId = a.id.substring(5);
      let bId = b.id.substring(5);
      return aId.localeCompare(bId);
    });

    preButton = foodTypeID; 
  } else {

    if (foodTypeID !== preButton) {

      sorted = Array.from(notSort); 
      sorted.sort(function (a, b) {
        let aId = a.id.substring(5);
        let bId = b.id.substring(5);
        return aId.localeCompare(bId);
      });

      preButton = foodTypeID; 
    } else {
      sorted = null; 
      preButton = null; 

      for (var i = 0; i < foodList.length; i++) {
        foodList[i].style.display = 'block';
      }
      return; 
    }
  }

  for (var i = 0; i < foodList.length; i++) {
    let idString = foodList[i].id;
    let idFood = idString.substring(5);
    if (idFood != foodTypeID) {
      foodList[i].style.display = 'none';
    } else {
      foodList[i].style.display = 'block';
    }
  }
});


// Get all button addToCartBtn
var addToCartButtons = document.querySelectorAll('.addToCartBtn');

// Loop each button and add click event
addToCartButtons.forEach(function(button) {
  button.addEventListener('click', function() {
    var foodId = this.getAttribute('data-foodid');
    var quantity = this.getAttribute('data-quantity');

    // Send AJAX request to addToCart servlet endpoint
    $.ajax({
        type: "GET",
        url: "addToCart",
        data: {
            fid: foodId,
            quantity: quantity
        },
        success: function (response) {
            console.log("Item added to cart successfully.");
        },
        error: function (error) {

            console.error("Error occurred: " + error.responseText);
        }
    });
    window.location.reload();
  });
});


//search 
function searchFood() {
  var searchTerm = document.getElementById("btn-search").value.toLowerCase();
  var foodList = document.getElementById("foodList").querySelectorAll(".col-sm-6");

  for (var i = 0; i < foodList.length; i++) {
    var foodName = foodList[i].querySelector(".card-title").textContent.toLowerCase();
    var foodContainer = foodList[i];

    if (searchTerm === "") {
      foodContainer.style.display = "block"; 
    } else {
      if (foodName.includes(searchTerm)) {
        foodContainer.style.display = "block"; 
      } else {
        foodContainer.style.display = "none"; 
      }
    }
  }
}


