/* eslint-disable no-undef */
"use strict";
$(document).ready(function () {
    // Default configuration for all DataTables
    Object.assign(DataTable.defaults, {
        language: {
            url: "/assets/json/vi.json",
        },
        search: {
            return: true,
        },
        pagingType: "full_numbers",
        dom:
                "<'row'" +
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
            footer: true,
        },
        fixedColumns: {
            fixedColumnsLeft: 1,
        },
        colReorder: {
            fixedColumnsLeft: 1, // Index column
        },
        select: {
            blurable: false, // Temporary disabled due to conflicts with selectAll button
            info: false,
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
                buttons: ["pdf", "excel", "csv", "print", "copy"],
            },
        ],
        scrollX: true,
    });

    // Food table config
    if (document.querySelector("#food-table")) {
        let foodTable = $("#food-table").DataTable({
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: [-1], // "Image" columns
                },
            ],
            searchPanes: {
                columns: [1, 4, 5, 6, 7],
                orderable: false,
                collapse: false
            },
        });

        function disableUpdateFoodBtn() {
            let btnUpdate = $("#btn-update-food");
            if (btnUpdate) {
                btnUpdate.removeAttr("data-food-id");
                btnUpdate.removeAttr("data-food-type");
                btnUpdate.removeAttr("data-food-name");
                btnUpdate.removeAttr("data-food-description");
                btnUpdate.removeAttr("data-food-price");
                btnUpdate.removeAttr("data-food-quantity");
                btnUpdate.removeAttr("data-food-status");
                btnUpdate.removeAttr("data-food-rate");
                btnUpdate.removeAttr("data-discount-percent");
                btnUpdate.removeAttr("data-image-url");
                btnUpdate.addClass("disabled");
            }
        }

        function disableDeleteFoodBtn() {
            let btnDelete = $("#btn-delete-food");
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
        foodTable.on(
                "select selectItems deselect",
                function (e, dt, type, indexes) {
                    if (type === "row" && indexes && Array.isArray(indexes)) {
                        let btnUpdate = $("#btn-update-food");
                        let btnDelete = $("#btn-delete-food");
                        // Retrieves selected rows
                        let data = foodTable.rows({selected: true}).data();
                        // Only allows update for exactly 1 row
                        if (data.length === 1) {
                            // data's type is a 2D array since the table's data is DOM-sourced
                            // https://datatables.net/reference/api/row().data()
                            btnUpdate.attr("data-food-id", data[0][0]);
                            btnUpdate.attr("data-food-type", data[0][1]);
                            console.log(data[0][1]);
                            btnUpdate.attr("data-food-name", data[0][2]);
                            btnUpdate.attr("data-food-description", data[0][3]);
                            let price = data[0][4]
                                    .substring(0, data[0][4].length - 1) // Removes currency symbol
                                    .replace(",", "")
                                    .replace(".", ""); // Removes thousand separators
                            btnUpdate.attr("data-food-price", price);
                            btnUpdate.attr("data-food-quantity", data[0][6]);
                            let status = 0;
                            if (data[0][7] == "Hết") {
                                status = 0;
                            } else {
                                status = 1;
                            }
                            btnUpdate.attr("data-food-status", status);
                            let rate = parseInt(data[0][5].split(" ")[0]);
                            btnUpdate.attr("data-food-rate", rate);
                            btnUpdate.attr(
                                    "data-discount-percent",
                                    data[0][8].substring(0, data[0][8].length - 1)
                                    ); // Removes percent symbol
                            let url = data[0][9].match(/src="([^"]*)"/)[1];
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
                }
        );

        /*
         Disables the Update button whenever user clicks outside of table (blur)
         Requires Select extension with blurable option enabled
         
         The blurable option causes a bug in the selectAll button, causing it unable to select at all,
         but instead deselects all rows. This only happens if I move the table button group outside
         the DataTables container. Initializing the buttons using the dom option is still normal.
         
         For now I'll stop using blurable for Select, but I'll look into it if I have time.
         */
        foodTable.on("select-blur", function (e, dt, target) {
            // Ignores blur event if user clicks on update/delete/cancel/confirm buttons, or the background of a modal dialog
            if (
                    target.classList.contains("btn-update") ||
                    target.classList.contains("btn-delete") ||
                    target.classList.contains("btn-cancel") ||
                    target.classList.contains("btn-confirm") ||
                    target.id === "update-food-modal" ||
                    target.id === "delete-food-modal"
                    ) {
                e.preventDefault();
            } else {
                disableUpdateFoodBtn();
                disableDeleteFoodBtn();
            }
        });

        dataTables.push(foodTable);
    }

    // Voucher table config
    if (document.querySelector("#vouchers-table")) {
        let voucherTable = $("#vouchers-table").DataTable({
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: [-1], // "Image" columns
                },
            ],
            searchPanes: {
                columns: [3, 4, 5],
                orderable: false,
                collapse: false
            },
        });

        function disableUpdateVoucherBtn() {
            let btnUpdate = $("#btn-update-voucher");
            if (btnUpdate) {
                btnUpdate.removeAttr("data-voucher-id");
                btnUpdate.removeAttr("data-voucher-name");
                btnUpdate.removeAttr("data-voucher-code");
                btnUpdate.removeAttr("data-voucher-discount-percent");
                btnUpdate.removeAttr("data-voucher-quantity");
                btnUpdate.removeAttr("data-voucher-status");
                btnUpdate.removeAttr("data-voucher-date");
                btnUpdate.addClass("disabled");
            }
        }

        function disableDeleteVoucherBtn() {
            let btnDelete = $("#btn-delete-voucher");
            if (btnDelete) {
                btnDelete.removeAttr("data-voucher-id");
                btnDelete.removeAttr("data-voucher-name");
                btnDelete.addClass("disabled");
            }
        }

        /*
         Enables/Disables the Update button whenever user selects/deselects row(s)
         Requires Select extension enabled
         */
        voucherTable.on(
                "select selectItems deselect",
                function (e, dt, type, indexes) {
                    if (type === "row" && indexes && Array.isArray(indexes)) {
                        let btnUpdate = $("#btn-update-voucher");
                        let btnDelete = $("#btn-delete-voucher");

                        // Retrieves selected rows
                        let data1 = voucherTable.rows({selected: true}).data();
                        // Only allows update for exactly 1 row
                        if (data1.length === 1) {
                            disableUpdateVoucherBtn();
                            // data's type is a 2D array since the table's data is DOM-sourced
                            // https://datatables.net/reference/api/row().data()
                            btnUpdate.attr("data-voucher-id", data1[0][0]);
                            btnUpdate.attr("data-voucher-name", data1[0][1]);
                            btnUpdate.attr("data-voucher-code", data1[0][2]);
                            let percentageValue = parseFloat(data1[0][3].replace("%", ""));
                            btnUpdate.attr("data-voucher-discount-percent", percentageValue);
                            btnUpdate.attr("data-voucher-quantity", data1[0][4]);
                            let status = 0;
                            if (data1[0][5] === "Hết") {
                                status = 0;
                            } else {
                                status = 1;
                            }
                            btnUpdate.attr("data-voucher-status", status);
                            btnUpdate.attr("data-voucher-date", data1[0][6]);
                            btnUpdate.removeClass("disabled");

                            let vouchers = {};
                            vouchers[data1[0][0]] = data1[0][1]; // vouchers[id] = voucher name
                            btnDelete.attr("data-vouchers", JSON.stringify(vouchers));
                            btnDelete.removeClass("disabled");
                        } else if (data1.length > 1) {
                            let vouchers = {};
                            for (let i = 0; i < data1.length; i++) {
                                let voucherId = data1[i][0];
                                vouchers[voucherId] = data1[i][1]; // Voucher name
                            }
                            btnDelete.attr("data-vouchers", JSON.stringify(vouchers));
                            btnDelete.removeClass("disabled");

                            disableUpdateVoucherBtn();
                        } else {
                            disableUpdateVoucherBtn();
                            disableDeleteVoucherBtn();
                        }
                    }
                }
        );

        voucherTable.on("select-blur", function (e, dt, target) {
            // Ignores blur event if user clicks on update/delete/cancel/confirm buttons, or the background of a modal dialog
            if (
                    target.classList.contains("btn-update") ||
                    target.classList.contains("btn-delete") ||
                    target.classList.contains("btn-cancel") ||
                    target.classList.contains("btn-confirm") ||
                    target.id === "update-voucher-modal" ||
                    target.id === "delete-voucher-modal"
                    ) {
                e.preventDefault();
            } else {
                disableUpdateVoucherBtn();
                disableDeleteVoucherBtn();
            }
        });

        dataTables.push(voucherTable);
    }

    // Role table config
    if (document.querySelector("#roles-table")) {
        let roleTable = $("#roles-table").DataTable({
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: [-1], // "Image" columns
                },
            ],
            searchPanes: {
                columns: [1, 5],
                orderable: false,
                collapse: false
            },
        });

        function disableUpdateRoleBtn() {
            let btnUpdate = $("#btn-update-role");
            if (btnUpdate) {
                btnUpdate.removeAttr("data-role-accountid");
                btnUpdate.removeAttr("data-role-roleid");
                btnUpdate.removeAttr("data-role-username");
                btnUpdate.removeAttr("data-role-fullname");
                btnUpdate.removeAttr("data-role-email");
                btnUpdate.removeAttr("data-role-type");
                btnUpdate.addClass("disabled");
            }
        }

        function disableDeleteRoleBtn() {
            let btnDelete = $("#btn-delete-role");
            if (btnDelete) {
                btnDelete.removeAttr("data-role-accountid");
                btnDelete.removeAttr("data-role-roleid");
                btnDelete.removeAttr("data-role-fullname");
                btnDelete.removeAttr("data-role-username");
                btnDelete.addClass("disabled");
            }
        }

        roleTable.on(
                "select selectItems deselect",
                function (e, dt, type, indexes) {
                    if (type === "row" && indexes && Array.isArray(indexes)) {
                        let btnUpdate = $("#btn-update-role");
                        let btnDelete = $("#btn-delete-role");

                        // Retrieves selected rows
                        let data2 = roleTable.rows({selected: true}).data();

                        // Only allows update for exactly 1 row
                        if (data2.length === 1) {
                            // data's type is a 2D array since the table's data is DOM-sourced
                            // https://datatables.net/reference/api/row().data()
                            btnUpdate.attr("data-role-accountid", data2[0][0]);
                            btnUpdate.attr("data-role-roleid", data2[0][1]);
                            btnUpdate.attr("data-role-username", data2[0][2]);
                            btnUpdate.attr("data-role-fullname", data2[0][3]);
                            btnUpdate.attr("data-role-email", data2[0][4]);
                            btnUpdate.attr("data-role-type", data2[0][5]);
                            btnUpdate.removeClass("disabled");

                            let roles = {};
                            let accounts = {};
                            let temp1s = {};
                            let temp2s = {};

                            roles[data2[0][1]] = data2[0][3];
                            accounts[data2[0][0]] = data2[0][2];
                            let account_type = "";
                            if (data2[0][5] === "staff") {
                                account_type = "staff";
                                temp1s[data2[0][1]] = account_type;
                                btnDelete.attr("data-temp1s", JSON.stringify(temp1s));
                            } else {
                                account_type = "promotionManager";
                                temp2s[data2[0][1]] = account_type;
                                btnDelete.attr("data-temp2s", JSON.stringify(temp2s));
                            }
                            btnDelete.attr("data-roles", JSON.stringify(roles));
                            btnDelete.attr("data-accounts", JSON.stringify(accounts));
                            btnDelete.attr("data-temp1s", JSON.stringify(temp1s));
                            btnDelete.attr("data-temp2s", JSON.stringify(temp2s));

                            btnDelete.removeClass("disabled");
                        } else if (data2.length > 1) {
                            let roles = {};
                            let temp1s = {};
                            let temp2s = {};
                            let accounts = {};
                            for (let i = 0; i < data2.length; i++) {
                                let roleId = data2[i][1];
                                let accountId = data2[i][0];
                                roles[i] = data2[i][3];
                                accounts[accountId] = data2[i][2];

                                let account_type = "";
                                if (data2[i][5] === "staff") {
                                    account_type = "staff";
                                    temp1s[roleId] = account_type;
                                } else {
                                    account_type = "promotionManager";
                                    temp2s[roleId] = account_type;
                                }
                            }
                            btnDelete.attr("data-roles", JSON.stringify(roles));
                            btnDelete.attr("data-temp1s", JSON.stringify(temp1s));
                            btnDelete.attr("data-temp2s", JSON.stringify(temp2s));
                            btnDelete.attr("data-accounts", JSON.stringify(accounts));
                            btnDelete.removeClass("disabled");
                            disableUpdateRoleBtn();
                        } else {
                            disableUpdateRoleBtn();
                            disableDeleteRoleBtn();
                        }
                    }
                }
        );

        roleTable.on("select-blur", function (e, dt, target) {
            // Ignores blur event if user clicks on update/delete/cancel/confirm buttons, or the background of a modal dialog
            if (
                    target.classList.contains("btn-update") ||
                    target.classList.contains("btn-delete") ||
                    target.classList.contains("btn-cancel") ||
                    target.classList.contains("btn-confirm") ||
                    target.id === "update-role-modal" ||
                    target.id === "delete-role-modal"
                    ) {
                e.preventDefault();
            } else {
                disableUpdateRoleBtn();
                disableDeleteRoleBtn();
            }
        });

        dataTables.push(roleTable);
    }

    // User table config
    if (document.querySelector("#user-table")) {
//        let userTable = $("#user-table").DataTable({
//            columnDefs: [
//                {
//                    searchable: false,
//                    userable: false,
//                    targets: [-1], // "Image" columns
//                },
//            ],
//            dom:
//                    "<'row'" +
//                    "<'d-row col-sm-12 m-0'" +
//                    "<'row'" +
//                    "<'col-sm-12 col-lg-6 pt-2'l><'col-sm-12 col-lg-6 pt-1 'f>" + // length and search bar
//                    ">" +
//                    "<'col-sm-12'tr>" + // table
//                    "<'row'" +
//                    "<'col-sm-12 col-md-5 mt-1'i><'col-sm-12 col-md-7 mt-2'p>" + // info and pagination
//                    ">" +
//                    ">" +
//                    ">",
//        });

        let userTable = $("#user-table").DataTable({
                    columnDefs: [
                        {
                            searchable: false,
                            orderable: false,
                            targets: [-1], // "Image" columns
                        },
                    ],
                    searchPanes: {
                        columns: [1, 5],
                        orderable: false,
                        collapse: false
                    },
                });

        function disableUpdateUserBtn() {
            let btnUpdate = $("#btn-update-user");
            if (btnUpdate) {
                btnUpdate.removeAttr("data-user-id");
                btnUpdate.removeAttr("data-user-customerid");
                btnUpdate.removeAttr("data-user-username");
                btnUpdate.removeAttr("data-user-lastname");
                btnUpdate.removeAttr("data-user-firstname");
                btnUpdate.removeAttr("data-user-gender");
                btnUpdate.removeAttr("data-user-phone");
                btnUpdate.removeAttr("data-user-email");
                btnUpdate.removeAttr("data-user-address");
                btnUpdate.addClass("disabled");
            }
        }

        function disableDeleteUserBtn() {
            let btnDelete = $("#btn-delete-user");
            if (btnDelete) {
                btnDelete.removeAttr("data-user-id");
                btnDelete.removeAttr("data-user-customerid");
                btnDelete.removeAttr("data-user-username");
                btnDelete.removeAttr("data-user-firstname");
                btnDelete.addClass("disabled");
            }
        }

        userTable.on(
                "select selectItems deselect",
                function (e, dt, type, indexes) {
                    if (type === "row" && indexes && Array.isArray(indexes)) {
                        let btnUpdate = $("#btn-update-user");
                        let btnDelete = $("#btn-delete-user");

                        // Retrieves selected rows
                        let data3 = userTable.rows({selected: true}).data();

                        // Only allows update for exactly 1 row
                        if (data3.length === 1) {
                            // data's type is a 2D array since the table's data is DOM-sourced
                            // https://datatables.net/reference/api/row().data()
                            btnUpdate.attr("data-user-id", data3[0][0]);
                            btnUpdate.attr("data-user-customerid", data3[0][1]);
                            btnUpdate.attr("data-user-username", data3[0][2]);
                            btnUpdate.attr("data-user-lastname", data3[0][3]);
                            btnUpdate.attr("data-user-firstname", data3[0][4]);
                            btnUpdate.attr("data-user-gender", data3[0][5]);
                            btnUpdate.attr("data-user-phone", data3[0][6]);
                            btnUpdate.attr("data-user-email", data3[0][7]);
                            btnUpdate.attr("data-user-address", data3[0][8]);
                            btnUpdate.removeClass("disabled");

                            let users = {};
                            let customers = {};

                            users[data3[0][0]] = data3[0][2];
                            customers[data3[0][1]] = data3[0][4];
                            btnDelete.attr("data-users", JSON.stringify(users));
                            btnDelete.attr("data-customers", JSON.stringify(customers));
                            btnDelete.removeClass("disabled");
                        } else if (data3.length > 1) {
                            let users = {};
                            let customers = {};
                            for (let i = 0; i < data3.length; i++) {
                                let userId = data3[i][0];
                                let customerId = data3[i][1];
                                users[userId] = data3[i][1];
                                customers[customerId] = data3[i][4];
                            }
                            btnDelete.attr("data-users", JSON.stringify(users));
                            btnDelete.attr("data-customers", JSON.stringify(customers));
                            btnDelete.removeClass("disabled");
                            disableUpdateUserBtn();
                        } else {
                            disableUpdateUserBtn();
                            disableDeleteUserBtn();
                        }
                    }
                }
        );

        userTable.on("select-blur", function (e, dt, target) {
            // Ignores blur event if user clicks on update/delete/cancel/confirm buttons, or the background of a modal dialog
            if (
                    target.classList.contains("btn-update") ||
                    target.classList.contains("btn-delete") ||
                    target.classList.contains("btn-cancel") ||
                    target.classList.contains("btn-confirm") ||
                    target.id === "update-user-modal" ||
                    target.id === "delete-user-modal"
                    ) {
                e.preventDefault();
            } else {
                disableUpdateUserBtn();
                disableDeleteUserBtn();
            }
        });
        dataTables.push(userTable);
    }

    // Order table config
    if (document.querySelector("#order-table")) {
        let orderTable = $("#order-table").DataTable({
            columnDefs: [
                {
                    searchable: false,
                    userable: false,
                    targets: [-1], // "Image" columns
                },
            ],
            dom:
                    "<'row'" +
                    "<'d-row col-sm-12 m-0'" +
                    "<'row'" +
                    "<'col-sm-12 col-lg-6 pt-2'l><'col-sm-12 col-lg-6 pt-1 'f>" + // length and search bar
                    ">" +
                    "<'col-sm-12'tr>" + // table
                    "<'row'" +
                    "<'col-sm-12 col-md-5 mt-1'i><'col-sm-12 col-md-7 mt-2'p>" + // info and pagination
                    ">" +
                    ">" +
                    ">",
        });

        function disableUpdateOrderBtn() {
            let btnUpdate = $("#btn-update-order");
            if (btnUpdate) {
                btnUpdate.removeAttr("data-order-id");
                btnUpdate.removeAttr("data-order-phonenumber");
                btnUpdate.removeAttr("data-order-lastname");
                btnUpdate.removeAttr("data-order-firstname");
                btnUpdate.removeAttr("data-order-address");
                btnUpdate.removeAttr("data-order-paymentmethod");
                btnUpdate.removeAttr("data-order-note");
                btnUpdate.removeAttr("data-order-status");
                btnUpdate.removeAttr("data-order-total");
                btnUpdate.addClass("disabled");
            }
        }
        
        function disableHistoryOrderBtn() {
            let btnHistory = $("#btn-history-order");
            if (btnHistory) {
                btnHistory.removeAttr("data-order-id");
                btnHistory.addClass("disabled");
            }
        }

        function disableDeleteOrderBtn() {
            let btnDelete = $("#btn-delete-order");
            if (btnDelete) {
                btnDelete.removeAttr("data-orders");
                btnDelete.addClass("disabled");
            }
        }

        function disableNextOrderBtn() {
            let btnNext = $("#btn-next-order");
            if (btnNext) {
                btnNext.removeAttr("data-orders");
                btnNext.addClass("disabled");
            }
        }
        
        orderTable.on(
                "select selectItems deselect",
                function (e, dt, type, indexes) {
                    if (type === "row" && indexes && Array.isArray(indexes)) {
                        let btnUpdate = $("#btn-update-order");
                        let btnDelete = $("#btn-delete-order");
                        let btnNext = $("#btn-next-order");
                        let btnHistory = $("#btn-history-order");

                        // Retrieves selected rows
                        let data4 = orderTable.rows({selected: true}).data();

                        // Only allows update for exactly 1 row
                        if (data4.length === 1) {
                            // data's type is a 2D array since the table's data is DOM-sourced
                            // https://datatables.net/reference/api/row().data()
                            btnUpdate.attr("data-order-id", data4[0][0]);
                            btnHistory.attr("data-order-id", data4[0][0]);
                            btnUpdate.attr("data-order-phonenumber", data4[0][3]);
                            btnUpdate.attr("data-order-address", data4[0][6]);
                            btnUpdate.attr("data-order-paymentmethod", data4[0][7]);
                            btnUpdate.attr("data-order-note", data4[0][9]);
                            let total = data4[0][10]
                                    .substring(0, data4[0][10].length - 1) // Removes currency symbol
                                    .replace(",", "")
                                    .replace(".", ""); // Removes thousand separators
                            btnUpdate.attr("data-order-total", total);
                            btnUpdate.attr("data-order-status", data4[0][12]);
                            btnUpdate.removeClass("disabled");
                            btnHistory.removeClass("disabled");

                            let orders = {};
                            
                            // Initially enable the next button
                            btnNext.removeClass("disabled");
                            // And then disabled it if the order status is "Đã giao" or "Đã hủy"
                            if (data4[0][12] === "Đã giao" || data4[0][12] === "Đã hủy") {
                              disableNextOrderBtn();
                            } else {
                              orders[data4[0][0]] = data4[0][0];
                            }

                            btnNext.attr("data-orders", JSON.stringify(orders));
                            orders[data4[0][0]] = data4[0][0];
                            btnDelete.attr("data-orders", JSON.stringify(orders));
                            btnDelete.removeClass("disabled");
                            
                        } else if (data4.length > 1) {
                            let orders = {};
                            
                            // Initially enable the next button
                            btnNext.removeClass("disabled");
                            // And then disabled it if any order status is "Đã giao" or "Đã hủy"
                            for (let i = 0; i < data4.length; i++) {
                              if (data4[i][12] === "Đã giao" || data4[i][12] === "Đã hủy") {
                                disableNextOrderBtn();
                              } else {
                                let orderId = data4[i][0];
                                orders[orderId] = data4[i][0];
                              }
                            }
                            btnNext.attr("data-orders", JSON.stringify(orders));
                            btnDelete.attr("data-orders", JSON.stringify(orders));
                            btnDelete.removeClass("disabled");
                            disableUpdateOrderBtn();
                            disableHistoryOrderBtn();
                        } else {
                            disableUpdateOrderBtn();
                            disableHistoryOrderBtn();
                            disableDeleteOrderBtn();
                            disableNextOrderBtn();
                        }
                    }
                }
        );
        // Add event listener for opening and closing details
        orderTable.on("click", "td.dt-control", function (e) {
            let tr = e.target.closest("tr");
            let row = orderTable.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
            } else {
                // Open this row
                row.child(format(row.data())).show();
            }
        });

        orderTable.on('select-blur', function (e, dt, target) {
            // Ignores blur event if user clicks on update/delete/cancel/confirm buttons, or the background of a modal dialog
            if (target.classList.contains("btn-update")
                    || target.classList.contains("btn-delete")
                    || target.classList.contains("btn-cancel")
                    || target.classList.contains("btn-confirm")
                    || target.id === "update-order-modal"
                    || target.id === "delete-order-modal" 
                    || target.id === "next-order-modal"
                    || target.id === "history-order-modal")
            {
                e.preventDefault();
            } else {
                disableUpdateOrderBtn();
                disableHistoryOrderBtn();
                disableDeleteOrderBtn();
                disableNextOrderBtn();
            }
        });

        dataTables.push(orderTable);
    }

});

// Define a list of DataTables instances to be used
let dataTables = [];

// Ensures that the function is called only once to prevent memory issues
let areTablesInitialized = false;
// This triggers once any tab links are clicked, regardless of the destination tab
$("ul [data-bs-target]").on("shown.bs.tab", function (event) {
    // Initialize all tables exactly once
    // And only triggers when targeted tab is not the home tab
    const targetTab = event.target.getAttribute("data-bs-target");
    if (!areTablesInitialized && targetTab != "#home") {
        // Remove searchPanes' expand and collapse all panes button
        $(".dtsp-showAll").remove();
        $(".dtsp-collapseAll").remove();

        // Additional custom styling for searchPane's title row
        $(".dtsp-titleRow").addClass(
                "d-flex flex-wrap align-items-center gap-2 mt-1"
                );
        $(".dtsp-titleRow > div")
                .addClass("py-0")
                .after("<div class='flex-grow-1'>");
        $(".dtsp-titleRow > button").addClass(
                "d-flex align-items-center btn-sm py-2"
                );

        dataTables.forEach((dataTable) => {
            /*
              Highlights current column that the mouse cursor is hovering on
              This should be used in tandem with default hover option for increased cursor visibility
              */
            dataTable.on("mouseenter", "td", function () {
                let columnIndex = dataTable.cell(this).index().column;

                dataTable
                        .cells()
                        .nodes()
                        .each((element) => element.classList.remove("highlight"));

                dataTable
                        .column(columnIndex)
                        .nodes()
                        .each((element) => element.classList.add("highlight"));
            });

            // Get the HTMLElement table from the DataTableAPI object
            let table = dataTable.tables().nodes()[0];
            let tabID = table.closest(".tab-pane").id;
            let buttonContainer = document.querySelector(
                    `#${tabID} .button-container`
                    );
            
            // Fix dumbass button group not showing up after a page load with a non-default tab selected
            // This is a workaround for the fact that DataTables doesn't initialize the buttons group for some goddamn reason
            // Took me 8 hours for fuck's sake so uh...
            // Fuck you DataTables, why do I have to initialize the buttons again?
            new $.fn.dataTable.Buttons( dataTable, {
                buttons: [
                    {
                        extend: "selectAll",
                        text: "Chọn tất cả",
                    },
                    {
                        extend: "selectNone",
                        text: "Bỏ chọn tất cả",
                    },
                    {
                        extend: "colvis",
                        text: "Hiển thị theo cột",
                    },
                    {
                        extend: "collection",
                        text: "Xuất file",
                        autoClose: true,
                        fade: 0,
                        buttons: ["pdf", "excel", "csv", "print", "copy"],
                    },
                ]
            } );  
            
            // Insert the table's button group to existing button container that already has Add, Update, Delete buttons
            dataTable.buttons().container().prependTo(buttonContainer);

            // Manually configure classes for the newly inserted button group
            let tableButtons = $(buttonContainer).find("div.dt-buttons");
            console.log(tableButtons)
            tableButtons.removeClass("btn-group");
            tableButtons.addClass("col-sm-12 col-lg-6 d-flex gap-2");

        });
        // Mark state of all tables as initialized
        areTablesInitialized = true;
    }

    // Get target tab's ID, which can be used to find its table
    let tabID = $(this).attr("data-bs-target");
    // Resize the table	
    $(tabID).find("table").resize();
});
