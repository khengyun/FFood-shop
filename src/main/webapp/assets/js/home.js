var sorted = null; // Biến toàn cục lưu trữ danh sách các phần tử đã được sắp xếp
var notSort = null; // Biến toàn cục lưu trữ danh sách các phần tử chưa được sắp xếp
var preButton = null; // Biến toàn cục lưu trữ id của nút trước đó đã được nhấn

$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); // Lưu trữ danh sách ban đầu vào biến notSort
  if (window.location.hash === '#success') {
    $('#success').modal('show');
    setTimeout(function () {
      $('#success').modal('hide');
    }, 3000);
  }
});

$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); // Lưu trữ danh sách ban đầu vào biến notSort
  if (window.location.hash === '#success_register') {
    $('#success_register').modal('show');
    setTimeout(function () {
      $('#success_register').modal('hide');
    }, 3000);
  }
});

$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); // Lưu trữ danh sách ban đầu vào biến notSort
  if (window.location.hash === '#failure') {
    $('#failure').modal('show');
    setTimeout(function () {
      $('#failure').modal('hide');
    }, 3000);
  }
});

$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); // Lưu trữ danh sách ban đầu vào biến notSort
  if (window.location.hash === '#verify-modal') {
    $('#verify-modal').modal('show');
    setTimeout(function () {
      $('#verify-modal').modal('hide');
    }, 30000);
  }
});

$(document).on("click", ".btn-cate", function () {
  let foodTypeID = $(this).data("food-type-id");

  let foodList = document.querySelectorAll("div[id^='food-']");

  if (sorted === null) {
    // Nếu sorted chưa được khởi tạo, đây là lần nhấn đầu tiên
    sorted = Array.from(notSort); // Sao chép danh sách chưa được sắp xếp vào sorted
    sorted.sort(function (a, b) {
      let aId = a.id.substring(5);
      let bId = b.id.substring(5);
      return aId.localeCompare(bId);
    });

    preButton = foodTypeID; // Lưu id của nút hiện tại vào biến preButton
  } else {
    // Nếu sorted đã tồn tại, đây là lần nhấn tiếp theo
    if (foodTypeID !== preButton) {
      // Nếu id của nút hiện tại khác với id của nút trước đó, tiếp tục sắp xếp dữ liệu
      sorted = Array.from(notSort); // Sao chép danh sách chưa được sắp xếp vào sorted
      sorted.sort(function (a, b) {
        let aId = a.id.substring(5);
        let bId = b.id.substring(5);
        return aId.localeCompare(bId);
      });

      preButton = foodTypeID; // Lưu id của nút hiện tại vào biến preButton
    } else {
      // Nếu id của nút hiện tại trùng với id của nút trước đó, trả lại tất cả giá trị như ban đầu
      sorted = null; // Xóa dữ liệu đã sắp xếp
      preButton = null; // Đặt preButton về giá trị null

      // Hiển thị lại tất cả các phần tử
      for (var i = 0; i < foodList.length; i++) {
        foodList[i].style.display = 'block';
      }
      return; // Kết thúc xử lý sự kiện
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

// Loop qua từng nút và thêm sự kiện click
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
            // Handle success response from the server
            console.log("Item added to cart successfully.");
            // You can update the cart UI or perform other actions here
        },
        error: function (error) {
            // Handle error response from the server
            console.error("Error occurred: " + error.responseText);
        }
    });
    window.location.reload();
    // Ví dụ: Hiển thị thông báo
//    console.log("Food ID: " + foodId + ", Quantity: " + quantity);
  });
});


document.addEventListener('DOMContentLoaded', function () {
    // Get the form element
    var form = document.getElementById('signup-form');

    // Add an event listener for form submission
    form.addEventListener('submit', function (event) {
        // Prevent the form from submitting the traditional way
        event.preventDefault();

        // Perform form validation here if needed

        // Open the modal dialog
//        var modal = new bootstrap.Modal(document.getElementById('verify-modal'));
//        modal.show();

        // You can optionally submit the form data using AJAX at this point
        // Example AJAX submission using fetch API:
        // fetch('/signup', {
        //     method: 'POST',
        //     body: new FormData(form)
        // }).then(response => response.json())
        //   .then(data => {
        //     console.log(data);
        //   });
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
      foodContainer.style.display = "block"; // Hiển thị tất cả giá trị food nếu tìm kiếm rỗng
    } else {
      if (foodName.includes(searchTerm)) {
        foodContainer.style.display = "block"; // Hiển thị phần tử nếu tìm thấy kết quả
      } else {
        foodContainer.style.display = "none"; // Ẩn phần tử nếu không tìm thấy kết quả
      }
    }
  }
}


