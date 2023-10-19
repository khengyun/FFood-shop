"use strict";
$(document).ready(function () {
  // Default configuration for all DataTables
  Object.assign(DataTable.defaults, {
    language: {
      url: '/assets/json/vi.json',
    },
    search: {
      return: true
    },
    pagingType: "full_numbers",
    dom: "<'row'" +
        "<'col-sm-12 col-md-3'P>" + // searchPanes on left col (L)
        "<'d-row col-sm-12 col-md-9 m-0'" +
        "<'row'" +
        "<'col-sm-12 col-lg-6 pt-2'l><'col-sm-12 col-lg-6 pt-1 'f>" + // length and search bar
        ">" +
        "<'col-sm-12'tr>" + // table
        "<'row'" +
        "<'col-sm-12 col-md-5 mt-1'i><'col-sm-12 col-md-7 mt-2'p>" + // info and pagination
        ">" +
        ">" +
        ">",
    fixedHeader: {
      header: true,
      footer: true
    },
    fixedColumns: {
      fixedColumnsLeft: 1
    },
    colReorder: {
      fixedColumnsLeft: 1 // Index column
    },
    select: {
      blurable: false, // Temporary disabled due to conflicts with selectAll button
      info: false
    },
    buttons: [
      "selectAll",
      "selectNone",
      "colvis",
      {
        extend: "collection",
        text: "Xuất file",
        autoClose: true,
        fade: 0,
        buttons: [
          "pdf",
          "excel",
          "csv",
          "print",
          "copy",
        ]
      }
    ],
    scrollX: true
  });

  let foodTable = $('#food-table').DataTable({
    columnDefs: [
      {
        searchable: false,
        orderable: false,
        targets: [-1] // "Image" columns
      }
    ]
  });

  /*
  Highlights current column that the mouse cursor is hovering on
  This should be used in tandem with default hover option for increased cursor visibility
  */
  foodTable.on('mouseenter', 'td', function () {
    let columnIndex = foodTable.cell(this).index().column;

    foodTable.cells()
        .nodes()
        .each((element) => element.classList.remove('highlight'));

    foodTable
        .column(columnIndex)
        .nodes()
        .each((element) => element.classList.add('highlight'));
  });

  function disableUpdateFoodBtn() {
    let btnUpdate = $('#btn-update-food');
    if (btnUpdate) {
      btnUpdate.removeAttr("data-food-id");
      btnUpdate.removeAttr("data-food-type");
      btnUpdate.removeAttr("data-food-name");
      btnUpdate.removeAttr("data-food-price");
      btnUpdate.removeAttr("data-discount-percent");
      btnUpdate.removeAttr("data-image-url");
      btnUpdate.addClass("disabled");
    }
  }

  function disableDeleteFoodBtn() {
    let btnDelete = $('#btn-delete-food');
    if (btnDelete) {
      btnDelete.removeAttr("data-food-id");
      btnDelete.removeAttr("data-food-name");
      btnDelete.addClass("disabled");
    }
  }

  /*
  Enables/Disables the Update button whenever user selects/deselects row(s)
  Requires Select extension enabled
  */
  foodTable.on('select selectItems deselect', function (e, dt, type, indexes) {
    if (type === 'row' && indexes && Array.isArray(indexes)) {
      let btnUpdate = $('#btn-update-food');
      let btnDelete = $('#btn-delete-food');

      // Retrieves selected rows
      let data = foodTable.rows({selected: true}).data();

      // Only allows update for exactly 1 row
      if (data.length === 1) {

        // data's type is a 2D array since the table's data is DOM-sourced
        // https://datatables.net/reference/api/row().data()
        btnUpdate.attr("data-food-id", data[0][0]);
        btnUpdate.attr("data-food-type", data[0][1]);
        btnUpdate.attr("data-food-name", data[0][2]);

        let price = data[0][3]
            .substring(0, data[0][3].length - 1) // Removes currency symbol
            .replace(',', '') // Removes thousand separators
            .concat(".0000"); // Adds optional decimals
        btnUpdate.attr("data-food-price", price);

        btnUpdate.attr("data-discount-percent", data[0][4].substring(0, data[0][4].length - 1)); // Removes percent symbol

        let url = data[0][5].match(/src="([^"]*)"/)[1];
        btnUpdate.attr("data-image-url", url); // Keeps the image URL as the original string is the entire <img> tag

        btnUpdate.removeClass("disabled");

        let foods = {};
        foods[data[0][0]] = data[0][2]; // food[id] = food name
        btnDelete.attr("data-foods", JSON.stringify(foods));
        btnDelete.removeClass("disabled");
      } else if (data.length > 1) {
        let foods = {};
        for (let i = 0; i < data.length; i++) {
          let foodId = data[i][0];
          foods[foodId] = data[i][2]; // Food name
        }
        btnDelete.attr("data-foods", JSON.stringify(foods));
        btnDelete.removeClass("disabled");
        disableUpdateFoodBtn();
      } else {
        disableUpdateFoodBtn();
        disableDeleteFoodBtn();
      }
    }
  });

  /*
  Disables the Update button whenever user clicks outside of table (blur)
  Requires Select extension with blurable option enabled

  The blurable option causes a bug in the selectAll button, causing it unable to select at all,
  but instead deselects all rows. This only happens if I move the table button group outside
  the DataTables container. Initializing the buttons using the dom option is still normal.

  For now I'll stop using blurable for Select, but I'll look into it if I have time.
  */
  foodTable.on('select-blur', function (e, dt, target, originalEvent) {
    // Ignores blur event if user clicks on update/delete/cancel/confirm buttons, or the background of a modal dialog
    if (target.classList.contains("btn-update")
        || target.classList.contains("btn-delete")
        || target.classList.contains("btn-cancel")
        || target.classList.contains("btn-confirm")
        || target.id === "update-food-modal"
        || target.id === "delete-food-modal") {
      e.preventDefault();
    } else {
      disableUpdateFoodBtn();
      disableDeleteFoodBtn();
    }
  })

  /*
  Fixes table header not properly sized on tab load.
  Upon any resize events (such as browser window resize), such elements are displayed correctly.
  This problem is not present if the tabbed content is immediately active on page load (home tab).
  This solution is triggered after the targeted tab has been shown (target is specified in the query selector).

  Refer to the following link for more information:
  https://getbootstrap.com/docs/5.3/components/navs-tabs/#events

  DO NOT USE ON CLICK EVENTS, this will cause a memory issue that freezes the page if the user clicks on the tab repeatedly.
  */
  $("[data-bs-target='#foods']").on('shown.bs.tab', function () {
    // Remove searchPanes' expand and collapse all panes button
    $('.dtsp-showAll').remove();
    $('.dtsp-collapseAll').remove();

    // Additional custom styling for searchPane's title row
    $('.dtsp-titleRow').addClass("d-flex flex-wrap align-items-center gap-2 mt-1");
    $('.dtsp-titleRow > div').addClass("py-0").after("<div class='flex-grow-1'>");
    $('.dtsp-titleRow > button').addClass("d-flex align-items-center btn-sm py-2");

    // Insert the table's button group to existing button container with Add, Update, Delete buttons
    foodTable.buttons().container().prependTo("#foods-button-container");
    // Manually configure classes for the newly inserted button group
    let tableButtons = $("#foods-button-container > div.dt-buttons");
    tableButtons.removeClass("btn-group");
    tableButtons.addClass("col-sm-12 col-lg-7 d-flex gap-2");
    /*$("#foods-button-container > div.dt-buttons > *").addClass("me-1");*/

    // Fix table headers not resized on page load
    $('#food-table').resize();
  });

//  $('#users-table').DataTable();
//  $('#orders-table').DataTable();
})
;