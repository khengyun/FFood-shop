/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

$(document).on("click", "#btn-update-food", function () {
  let foodID = $(this).data("food-id");
  let foodType = $(this).data("food-type");
  let foodName = $(this).data("food-name");
  let foodPrice = $(this).data("food-price");
  let discountPercent = $(this).data("discount-percent");
  let imageURL = $(this).data("image-url");

  // Set the values of the corresponding form inputs in the modal
  $("#update-food-modal").find("input[name='txtFoodID']").val(foodID);
  $("#update-food-modal").find("#txtFoodName").val(foodName);
  $("#update-food-modal").find("#txtFoodPrice").val(Number(foodPrice).toFixed(2));
  $("#update-food-modal").find("#txtDiscountPercent").val(discountPercent);
  $("#update-food-modal").find("#txtImageURL").val(imageURL);
  switch (foodType) {
    case "1":
      $("#update-food-modal").find("#txtFoodTypeID option[value = 1]").attr("selected", "selected");
      break;
    case "2":
      $("#update-food-modal").find("#txtFoodTypeID option[value = 2]").attr("selected", "selected");
      break;
    case "3":
      $("#update-food-modal").find("#txtFoodTypeID option[value = 3]").attr("selected", "selected");
      break;
    case "4":
      $("#update-food-modal").find("#txtFoodTypeID option[value = 4]").attr("selected", "selected");
      break;
    case "5":
      $("#update-food-modal").find("#txtFoodTypeID option[value = 5]").attr("selected", "selected");
      break;
    case "6":
      $("#update-food-modal").find("#txtFoodTypeID option[value = 6]").attr("selected", "selected");
      break;
  }
});
$(document).on("click", "#btn-delete-food", function () {
  let foodName = $(this).data("food-name");
  let foodID = $(this).data("food-id");
  let deleteFoodLink = "";
  if (foodID === null) {
    deleteFoodLink = "/admin";
  } else {
    deleteFoodLink = "/admin/food/delete/" + foodID;
  }
// Set the values of the corresponding form inputs in the modal
  $("#delete-food-modal").find("#foodName").html(foodName + " ");
  $("#delete-food-modal").find("#deleteFoodLink").attr("href", deleteFoodLink);
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
