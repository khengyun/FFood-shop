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

    // Find the img of the clicked button, and set its border to indicate the chosen category
    let img = $(this).find("img");
    img.addClass("border-4 border-primary shadow");

    // Find the img of the previous button, and remove its border
    if (prevCategoryID !== null) {
      let prevImg = $(".btn-categories[data-food-type-id=" + prevCategoryID + "]").find("img");
      prevImg.removeClass("border-4 border-primary shadow");
    }

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

    // Find the img of the previous button, and remove its border
    if (prevCategoryID !== null) {
      let prevImg = $(".btn-categories[data-food-type-id=" + prevCategoryID + "]").find("img");
      prevImg.removeClass("border-4 border-primary shadow");
    }
    
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
  // Stores the number of items shown before the click
  let itemsShownBeforeClick = itemsShown;
  
  // Show the next 12 items
  for (let i = itemsShown; i < itemsShownBeforeClick + itemsToShow; i++) {
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

// Search bar
function searchFoodByKeyword() {
  const searchInput = document.getElementById("search-bar");
  const searchResultsList = document.getElementById("search-results-list");

  // Clear previous search results
  searchResultsList.innerHTML = "";

  if (searchInput.value.trim() === "") {
    searchResultsList.style.display = "none";
    return;
  }

  // Fetch search results based on the input
  fetch(`http://localhost:8001/search_food_by_name/${searchInput.value}`)
    .then((response) => response.json())
    .then((data) => {
      if (data.length > 0) {
        data.forEach((item) => {
          // Create a card
          const card = document.createElement("div");
          card.classList.add("card", "mb-3");

          // Create a row for card content
          const row = document.createElement("div");
          row.classList.add("row", "g-0");

          // Create a column for the image
          const imageCol = document.createElement("div");
          imageCol.classList.add("col-md-4");

          // Create an image for the card
          const img = document.createElement("img");
          img.src = item.food_url;
          img.alt = item.food_name;
          img.classList.add("img-fluid", "rounded-start", "food-thumbnail");

          // Create a column for the card body
          const bodyCol = document.createElement("div");
          bodyCol.classList.add("col-md-8");

          // Create a card body
          const cardBody = document.createElement("div");
          cardBody.classList.add("card-body");

          // Create card title
          const cardTitle = document.createElement("h5");
          cardTitle.classList.add("card-title");
          cardTitle.textContent = item.food_name;

          // Create card text
          const cardText = document.createElement("p");
          cardText.classList.add("card-text");
          cardText.textContent = item.food_description;

          // Create card text for the price
          const priceText = document.createElement("p");
          priceText.classList.add("card-text");
          priceText.classList.add("text-muted");
          priceText.textContent = `${item.food_price} VNĐ`;

          // Append elements to the card and row
          card.appendChild(row);
          row.appendChild(imageCol);
          row.appendChild(bodyCol);
          imageCol.appendChild(img);
          bodyCol.appendChild(cardBody);
          cardBody.appendChild(cardTitle);
          cardBody.appendChild(cardText);
          cardBody.appendChild(priceText);

          searchResultsList.appendChild(card);
        });
        searchResultsList.style.display = "block";
      } else {
        searchResultsList.style.display = "none";
      }
    })
    .catch((error) => console.error(error));
}
