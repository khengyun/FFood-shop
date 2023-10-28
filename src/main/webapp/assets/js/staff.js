$(document).on("click", "#btn-update-food", function () {
  let foodID = $(this).attr("data-food-id");
  let foodType = $(this).attr("data-food-type");
  let foodName = $(this).attr("data-food-name");
  let foodDescription = $(this).attr("data-food-description");
  let foodPrice = $(this).attr("data-food-price");
  let foodStatus = $(this).attr("data-food-status");
  let foodRate = $(this).attr("data-food-rate");
  let discountPercent = $(this).attr("data-discount-percent");
  let imageURL = $(this).attr("data-image-url");

  // Set the values of the corresponding form inputs in the modal
  let modal = $("#update-food-modal");
  modal.find("input[name='txtFoodID']").attr("value", foodID);
  modal.find("#txtFoodName").attr("value", foodName);
  modal.find("#txtFoodDescription").val(foodDescription);
  modal.find("#txtFoodPrice").attr("value", Number(foodPrice).toFixed(2));
  modal.find("#txtFoodStatus").attr("value", foodStatus);
  modal.find("#txtFoodRate").attr("value", foodRate);
  modal.find("#txtDiscountPercent").attr("value", discountPercent);
  modal.find("#txtImageURL").attr("value", imageURL);
  for (let i = 1; i < 7; i++) {
    let foodTypes = {1: "Cơm", 2: "Mì", 3: "Bánh mì", 4: "Đồ ăn vặt", 5: "Tráng miệng", 6: "Đồ uống"};
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

  // Retrieves selected rows' data in JSON format, so that it can be iterated
  let foods = JSON.parse($(this).attr("data-foods"));

  // Populate the list of foods in the modal
  for (let foodId in foods) {
    let foodName = foods[foodId];
    modal.find("#delete-food-list").append("<li>" + foodName + "</li>");
  }

  // Keep food IDs as strings
  let foodIds = Object.keys(foods).toString();

  // Set the values to the hidden input in the modal
  modal.find("input[name='foodData']").attr("value", foodIds);
});

function handleSelectChange(selectElement) {
    // Get the selected value
    const selectedStatus = selectElement.value;

    // Get the data-orderid attribute value
    const orderid = selectElement.getAttribute("data-orderid");

    // Send AJAX request to addToCart servlet endpoint
    $.ajax({
      type: "GET",
      url: "staff/order",
      data: {
        orderID: orderid,
        Changestatus: selectedStatus
      },
      success: function (response) {
        console.log("Change order status successfully.");
      },
      error: function (error) {
        console.error("Error occurred: " + error.responseText);
      }
    });
}

$(document).on("click", "#btn-update-order", function () {
    let orderID = $(this).attr("data-order-id");
    let phoneNumber = $(this).attr("data-order-phonenumber");
    let address = $(this).attr("data-order-address");
    let Ordernote = $(this).attr("data-order-note");
    let paymentMethod = $(this).attr("data-order-paymentmethod");
    let orderStatus = $(this).attr("data-order-status");
    let orderTotal = $(this).attr("data-order-total");

    let modal = $("#update-order-modal");
    // Set the values of the corresponding form inputs in the modal
    modal.find("input[name='txtOrderID']").attr("value", orderID);
    modal.find("#txtPhoneNumber").attr("value", phoneNumber);
    modal.find("#txtOrderAddress").val(address);
    modal.find("#txtPaymentMethod").val(paymentMethod);
    modal.find("#txtOrderNote").val(Ordernote);
    modal.find("#txtOrderStatus").val(orderStatus);
    modal.find("#txtOrderTotal").val(orderTotal);
});