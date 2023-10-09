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

  /*
  Enables/Disables the Update button whenever user selects/deselects row(s)
  Requires Select extension enabled
  */
  foodTable.on('select selectItems deselect', function (e, dt, type, indexes) {
    if (type === 'row' && indexes && Array.isArray(indexes)) {
      let button = $('#btn-update-food');

      // Retrieves selected rows
      let data = foodTable.rows({selected: true}).data();

      // Only allows update for exactly 1 row
      if (data.length === 1) {

        // data's type is a 2D array since the table's data is DOM-sourced
        // https://datatables.net/reference/api/row().data()
        button.attr("data-food-id", data[0][0]);
        button.attr("data-food-type", data[0][1]);
        button.attr("data-food-name", data[0][2]);

        let price = data[0][3]
            .substring(0, data[0][3].length - 1) // Removes currency symbol
            .replace(',', '') // Removes thousand separators
            .concat(".0000"); // Adds optional decimals
        button.attr("data-food-price", price);

        button.attr("data-discount-percent", data[0][4].substring(0, data[0][4].length - 1)); // Removes percent symbol

        let url = data[0][5].match(/src="([^"]*)"/)[1];
        button.attr("data-image-url", url); // Keeps the image URL as the original string is the entire <img> tag

        button.removeClass("disabled");
      } else {
        button.removeAttr("data-food-id");
        button.removeAttr("data-food-type");
        button.removeAttr("data-food-name");
        button.removeAttr("data-food-price");
        button.removeAttr("data-discount-percent");
        button.removeAttr("data-image-url");
        button.addClass("disabled");
      }
    }
  });

  /*
  Disables the Update button whenever user clicks outside of table (blur)
  Requires Select extension enabled
  */
  foodTable.on('select-blur', function (e, dt, target, originalEvent) {
    // Ignores blur event if user clicks on update button or cancel update button
    if (target.id === "btn-update-food" || target.id === "btn-cancel-update") {
      e.preventDefault();
    } else {
      let button = $('#btn-update-food');
      button.removeAttr("data-food-id");
      button.removeAttr("data-food-type");
      button.removeAttr("data-food-name");
      button.removeAttr("data-food-price");
      button.removeAttr("data-discount-percent");
      button.removeAttr("data-image-url");
      button.addClass("disabled");
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