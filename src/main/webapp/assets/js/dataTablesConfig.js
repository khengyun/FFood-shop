"use strict";
$(document).ready(function () {
  // Default configuration for all DataTables
  Object.assign(DataTable.defaults, {
    language: {
      url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/vi.json',
      buttons: {
        selectAll: "Chọn tất cả",
        selectNone: "Bỏ chọn tất cả"
      },
      searchPanes: {
        clearMessage: "Xoá bộ lọc"
      },
      info: "Hiển thị _START_ tới _END_ trên _TOTAL_ dòng",
      infoEmpty: "Hiển thị 0 tới 0 trên 0 dòng",
      lengthMenu: "Hiển thị _MENU_ dòng",
      infoFiltered: "(được lọc từ _MAX_ dòng)"
    },
    search: {
      return: true
    },
    pagingType: "full_numbers",
    dom: "<'d-flex flex-row my-3'B>" + // Buttons
        "<'flex-row'P>" + // Search pane
        "<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" + // length on left col, search bar on right col
        "<'row'<'col-sm-12'tr>>" + // table and processing display element
        "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>", // info on left col, pagination on right column
    fixedHeader: {
      header: true,
      footer: true
    },
    fixedColumns: {
      fixedColumnsLeft: 1
    },
    colReorder: {
      fixedColumnsLeft: 1, // Index column
      fixedColumnsRight: 1 // Actions column
    },
    select: {
      blurable: true,
      info: false
    },
    buttons: [
      "selectAll",
      "selectNone",
      "colvis",
      "excel",
      "pdf",
      "csv",
      "copy",
      "print",
    ],
    scrollX: true,
    scrollY: "450px",
    searchPanes: {
      initCollapsed: true
    }
  });

  let foodTable = $('#food-table').DataTable({
    columnDefs: [
      {
        searchable: false,
        orderable: false,
        targets: [-2, -1] // "Image" and "Actions" columns
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

  function disableUpdateFoodBtn () {
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

  function disableDeleteFoodBtn () {
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
        console.log(data);
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
  Requires Select extension enabled
  */
  foodTable.on('select-blur', function (e, dt, target, originalEvent) {
    // Ignores blur event if user clicks on update/delete/cancel/confirm buttons
    if (target.classList.contains("btn-update")
        || target.classList.contains("btn-delete")
        || target.classList.contains("btn-cancel")
        || target.classList.contains("btn-confirm")) {
      e.preventDefault();
    } else {
      let btnUpdate = $('#btn-update-food');
      let btnDelete = $('#btn-delete-food');

      disableUpdateFoodBtn();
      disableDeleteFoodBtn();
    }
  })

  /*
  Fixes searchPanes and table header not properly sized on page load.
  Upon any resize events (such as browser window resize), such elements are displayed correctly.
  This problem is not present if the tabbed content is immediately active on page load (home tab).
  This solution is triggered on click of tab button on the admin sidebar (whose target is specified in the query selector).
  */
  $("[data-bs-target='#foods']").click(function () {
    $('#food-table').resize();
    $('.dtsp-searchPanes').resize();
  });

  $('#users-table').DataTable();
  $('#orders-table').DataTable();
});