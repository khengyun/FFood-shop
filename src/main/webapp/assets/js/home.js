// Original (unfiltered) list
let foodList = $("#food-list > div");
// List of food items to show or hide, basically a subset of the original list
let foodItems;
let sorted = null;
// Store the category id of the previous button
let prevCategoryID = null;
let showMoreButton = document.getElementById("btn-show-more");
const itemsToShow = 12;
let itemsShown = 0;

//show success order food
$(document).ready(function () {
  notSort = document.querySelectorAll("div[id^='food-']"); 
  if (window.location.hash === "#success") {
    $("#success").modal("show");
    setTimeout(function () {
      $("#success").modal("hide");
    }, 3000);
  }
  showInitialFoodItems();
});

$(document).on("click", ".btn-categories", function () {
  let categoryID = $(this).data("food-type-id");
  foodList = $("#food-list > div");

  // Sort the food items if they are not sorted or the clicked button is different from the previous
  // Often this happens when the user clicks on a category for the first time, or when the user switches to another
  if (sorted === null || categoryID !== prevCategoryID) {
    // Makes a copy of the original (unsorted) list, then sorts it
    sorted = Array.from(foodList);
    sorted.sort(function (a, b) {
      let aId = a.id.substring(5);
      let bId = b.id.substring(5);
      return aId.localeCompare(bId);
    });

    // Store the id of the clicked button
    prevCategoryID = categoryID;

    // Hide all the food items not matching the chosen category, and show the matching ones
    for (let i = 0; i < foodList.length; i++) {
      let foodTypeID = Number.parseInt(foodList[i].id.substring(5));
      if (foodTypeID !== categoryID) {
        foodList[i].classList.add("d-none");
      } else {
        foodList[i].classList.remove("d-none");
      }
    }
    showInitialFoodItems();
  } else {
    // In the case of the same button, the list restored to its original state (not sorted)
    sorted = null;
    prevCategoryID = null; // Remove the id of the previous button

    // Restore the original list
    for (let i = 0; i < foodList.length; i++) {
      foodList[i].classList.remove("d-none");
    }

    // Show the first 12 items
    showInitialFoodItems();
  }
});

// Get all button addToCartBtn
var addToCartButtons = document.querySelectorAll(".addToCartBtn");

// Loop each button and add click event
addToCartButtons.forEach(function (button) {
  button.addEventListener("click", function () {
    let foodId = this.getAttribute("data-foodid");
    let quantity = this.getAttribute("data-quantity");
    // Send AJAX request to addToCart servlet endpoint
    $.ajax({
      type: "GET",
      url: "addToCart",
      data: {
        fid: foodId,
        quantity: quantity,
      },
      success: function (response) {
        console.log("Item added to cart successfully.");
      },
      error: function (error) {
        console.error("Error occurred: " + error.responseText);
      },
    });
    window.location.reload();
  });
});

/**
 * Show default food items
 *
 * This function selects the default food items from the food-list element
 * and hides the rest of the items.
 */
function showInitialFoodItems() {
  // Get all the food items from the food-list element
  foodItems = Array.from($("#food-list > div:not(.d-none)"));
  // Define the number of items to show
  const itemsToShow = 12;

  // Define the number of items shown
  itemsShown = foodItems.length;

  // Hide the items that exceed the number of items to show
  for (let i = itemsToShow; i < foodItems.length; i++) {
    foodItems[i].classList.add("d-none");
    itemsShown--;
  }

  // Show the "show more" button if there are more items to show
  autoHideButton();
}

// Add event listener to the "Xem thêm" button
showMoreButton.addEventListener("click", function () {
  // Show the next 12 items
  for (let i = itemsShown; i < itemsShown + itemsToShow; i++) {
    if (i < foodItems.length) {
      foodItems[i].classList.remove("d-none");
      itemsShown++;
    }
  }
  autoHideButton();
});

function autoHideButton() {
  // Hide the "show more" button if there are no more items to show
  if (itemsShown >= foodItems.length) {
    showMoreButton.classList.add("d-none");
  } else {
    showMoreButton.classList.remove("d-none");
  }
}

/**
 * Searches for food items based on a search term and updates the visibility of the food items accordingly.
 */
function searchFoodByKeyword() {
  // Get the search term input value and convert it to lowercase
  let searchTerm = $("#search-bar").val().toLowerCase();

  // Iterate through each food item
  for (let i = 0; i < foodList.length; i++) {
    let foodName = foodList[i]
      .querySelector(".card-title")
      .textContent.toLowerCase();
    let foodContainer = foodList[i];

    // Check if the search term is empty
    if (searchTerm === "") {
      foodContainer.classList.remove("d-none"); // Show all food items if no search term is entered
    } else {
      // Check if the food name includes the search term
      if (foodName.includes(searchTerm)) {
        foodContainer.classList.remove("d-none"); // Show food items that contain the search term
      } else {
        foodContainer.classList.add("d-none"); // Hide food items that do not contain the search term
      }
    }
  }
  showInitialFoodItems();
}