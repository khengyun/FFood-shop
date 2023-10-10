$(document).on("click", "#btn-update-food", function () {
  let foodID = $(this).attr("data-food-id");
  let foodType = $(this).attr("data-food-type");
  let foodName = $(this).attr("data-food-name");
  let foodPrice = $(this).attr("data-food-price");
  let discountPercent = $(this).attr("data-discount-percent");
  let imageURL = $(this).attr("data-image-url");

  // Set the values of the corresponding form inputs in the modal
  let modal = $("#update-food-modal");
  modal.find("input[name='txtFoodID']").val(foodID);
  modal.find("#txtFoodName").val(foodName);
  modal.find("#txtFoodPrice").val(Number(foodPrice).toFixed(2));
  modal.find("#txtDiscountPercent").val(discountPercent);
  modal.find("#txtImageURL").val(imageURL);
  for (let i = 1; i < 7; i++) {
    let foodTypes = {1: "Cơm", 2: "Mì", 3: "Bánh mì", 4: "Đồ ăn vặt", 5: "Tráng miệng", 6: "Đồ uống"}
    if (foodType === foodTypes[i]) {
      modal.find("#txtFoodTypeID option[value = " + i + "]").attr("selected", "selected");
    } else {
      modal.find("#txtFoodTypeID option[value = " + i + "]").removeAttr("selected");
    }
  }
});

$(document).on("click", "#btn-delete-food", function () {
  let modal = $("#delete-food-modal");
  // Clear the list of foods in the modal every time the button is clicked
  modal.find(".modal-body ul").empty();
  let foods = JSON.parse($(this).attr("data-foods"));

  // Populate the list of foods in the modal
  for (let foodId in foods) {
    let foodName = foods[foodId];
    modal.find("#delete-food-list").append("<li>" + foodName + "</li>");
  }

  let result;
  // Send a POST request with multiple IDs with fetch API or jQuery's $.ajax method
  // https://stackoverflow.com/questions/24468459/sending-multiple-ids-to-a-servlet-using-ajax
  fetch('/admin/user/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(foods),
  })
      .then(response => response.json())
      .then(data => {
        result = data;
        console.log(data);
        // TODO add deletion status popup
      })
      .catch((error) => console.error('Error:', error));
});

$(document).on("click", "#btn-update-user", function () {
  let accountID = $(this).data("account-id");
  let username = $(this).data("username");
  let email = $(this).data("email");
  // Set the values of the corresponding form inputs in the modal
  $("#update-user-modal").find("input[name='txtAccountID']").val(accountID);
  $("#update-user-modal").find("#txtAccountUsername").val(username);
  $("#update-user-modal").find("#txtEmail").val(email);
});
$(document).on("click", "#btn-delete-user", function () {
  let username = $(this).data("username");
  let accountID = $(this).data("account-id");
  let deleteAccountLink = "";
  if (accountID === null) {
    deleteAccountLink = "/admin";
  } else {
    deleteAccountLink = "/admin/user/delete/" + accountID;
  }
// Set the values of the corresponding form inputs in the modal
  $("#delete-user-modal").find("#username").html(username + " ");
  $("#delete-user-modal").find("#deleteAccountLink").attr("href", deleteAccountLink);
});
