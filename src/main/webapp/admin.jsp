<%-- 
    Document   : admin
    Created on : Jul 2, 2023, 10:36:53 PM
    Author     : CE171454 Hua Tien Thanh
--%>
<%@ include file="WEB-INF/jspf/common/imports/base.jspf" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>FFood | Admin Dashboard</title>
        <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
        <%@ include file="WEB-INF/jspf/admin/imports/dataTablesStyle.jspf" %>

    </head>
    <body>
        <div class="container-fluid m-0 p-0">
            <div class="d-flex flex-row m-0">
                <%@ include file="WEB-INF/jspf/admin/components/addFood.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/updateFood.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/deleteFood.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/addUser.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/updateUser.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/deleteUser.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/adminSidebar.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/addadmin.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/updateAdmin.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/addVoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/deleteVoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/admin/components/updatevoucher.jspf" %>
                <!-- Main Content -->
                <main class="w-100 p-4 bg-surface">
                    <div class="tab-content">
                        <!-- Home Tab Content -->
                        <%@ include file="WEB-INF/jspf/admin/home.jspf" %>
                        <!-- Food & Drinks Tab Content -->
                        <%@ include file="WEB-INF/jspf/admin/foods.jspf" %>

                        <!-- Users Tab Content -->
                        <div class="tab-pane fade" id="users">
                            <div class="container-fluid p-2">
                                <table id="users-table" class="table table-bordered table-striped">
                                    <h1 class="text-center fw-bold fs-3">Quản lý Người dùng</h1>
                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                            type="button" class="btn btn-sm btn-success py-1 my-2 me-2" data-bs-toggle="modal" data-bs-target="#add-user-modal">
                                        Tạo Tài khoản Người dùng
                                    </button>
                                    <thead>
                                        <tr>
                                            <th>Mã TK</th>
                                            <th>Tên Người dùng</th>
                                            <th>Email</th>
                                            <th>Loại TK</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${userAccountList}" var="u">
                                            <tr>
                                                <td class="col-sm-3 col-gap-5">${u.accountID}</td>
                                                <td>${u.username}</td>
                                                <td>${u.email}</td>
                                                <td>${u.accountType}</td>
                                                <td>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                                            type="button" id="btn-update-user"
                                                            class="btn btn-sm btn-success py-1 m-1"
                                                            data-account-id="${u.accountID}"
                                                            data-username="${u.username}"
                                                            data-email="${u.email}"
                                                            data-bs-toggle="modal" data-bs-target="#update-user-modal">
                                                        Cập nhật
                                                    </button>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                                            type="button" id="btn-delete-user"
                                                            class="btn btn-sm btn-danger py-1 m-1"
                                                            data-account-id="${u.accountID}"
                                                            data-username="${u.username}"
                                                            data-bs-toggle="modal" data-bs-target="#delete-user-modal">
                                                        Xóa
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>



                        <!-- Roles Tab Content -->
                        <div class="tab-pane fade" id="roles">
                            <div class="container-fluid p-2">
                                <table id="roles-table" class="table table-bordered table-striped" >
                                    <h1 class="text-center fw-bold fs-3">Quản lý Admin</h1>
                                    <button 
                                        style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);" 
                                        "height: 40px;"
                                        type="button" class="btn btn-sm btn-success py-1 my-2 me-2" data-bs-toggle="modal" data-bs-target="#add-admin-modal">
                                        Tạo Tài khoản Admin
                                    </button>

                                    <thead>
                                        <tr>
                                            <th class="col-sm-3 col-gap-5" >Mã TK</th>                                   
                                            <th >Tên</th>
                                            <th >Email</th>
                                            <th >Loại TK</th>
                                            <th >Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${StaffPromotionList}" var="a">
                                            <tr>
                                                <td>${a.accountID}</td>
                                                <td >${a.username}</td>
                                                <td >${a.email}</td>
                                                <td>${a.accountType}</td>
                                                <td>
                                                    <button 
                                                        style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);" 
                                                        type="button" id="btn-update-admin"
                                                        "height: 26px;"
                                                        class="btn btn-sm btn-success py-1 m-1"
                                                        data-account-id="${a.accountID}"
                                                        data-username="${a.username}"
                                                        data-email="${a.email}"
                                                        data-bs-toggle="modal" data-bs-target="#update-admin-modal">
                                                        Cập nhật
                                                    </button>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);" 
                                                            type="button" id="btn-delete-admin"
                                                            "height: 26px;"
                                                            class="btn btn-sm btn-danger py-1 m-1"
                                                            data-account-id="${a.accountID}"
                                                            data-username="${a.username}"
                                                            data-bs-toggle="modal" data-bs-target="#delete-admin-modal">
                                                        Đổi Password
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- Orders Tab Content -->
                        <div class="tab-pane fade" id="orders">
                            <div class="container-fluid p-2">
                                <table id="orders-table" class="table table-bordered table-striped">
                                    <h1 class="text-center fw-bold fs-3">Quản lý Đơn món</h1>
                                    <thead>
                                        <tr>
                                            <th>Mã Đơn</th>
                                            <th>Mã KH</th>
                                            <th>SĐT liên lạc</th>
                                            <th>Địa chỉ nhận</th>
                                            <th>Phương thức thanh toán</th>
                                            <th>Các món đặt</th>
                                            <th>Ghi chú</th> <!-- contains both customer note and cart details -->
                                            <th>Thanh toán</th>
                                            <th>Trạng thái</th>
                                            <th>Thời gian đặt</th>
                                            <th>Thời gian nhận</th>
                                            <th>Thời gian hủy</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${orderList}" var="o">
                                            <tr>
                                                <td>${o.orderID}</td>
                                                <td>${o.customerID}</td>
                                                <td>${o.contactPhone}</td>
                                                <td>${o.deliveryAddress}</td>
                                                <td>${o.paymentMethod}</td>
                                                <td>
                                                    <c:forEach items="${o.orderItems}" var="orderItem">
                                                        <p>${orderItem}</p>
                                                    </c:forEach>
                                                </td>
                                                <td>${(o.orderNote == null) ? "" : o.orderNote}</td>
                                                <td><fmt:formatNumber type="number" pattern="###,###,###.##" value="${o.orderTotal}"/>đ</td>
                                                <td>${o.orderStatus}</td>
                                                <td>${o.orderTime}</td>
                                                <td>${(o.deliveryTime == null) ? "" : o.deliveryTime}</td>
                                                <td>${(o.orderCancelTime == null) ? "" : o.orderCancelTime}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- Voucher Tab Content -->
                        <div class="tab-pane fade" id="promotions">
                            <div class="container-fluid p-2">
                                <table id="users-table" class="table table-bordered table-striped">
                                    <h1 class="text-center fw-bold fs-3" align="left">Khuyến mãi</h1>
                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                            type="button" id="btn-add-voucher" class="btn btn-sm btn-success py-1 my-2 me-2" data-bs-toggle="modal" data-bs-target="#add-voucher-modal">
                                        Tạo Khuyến mãi
                                    </button>
                                    <thead>
                                        <tr>
                                            <th class="col-sm-3 col-gap-5">Mã Khuyến mãi</th>  
                                            <th>Tên Khuyến mãi</th>
                                            <th>Mã Code</th>
                                            <th>Phần trăm KM</th>
                                            <th>Số lượng</th>
                                            <th>Trạng thái</th>
                                            <th>Thời gian</th>
                                            <th >Thao tác</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${voucherList}" var="v">
                                            <tr>
                                                <td>${v.voucherID}</td>
                                                <td>${v.voucher_name}</td>
                                                <td>${v.voucher_code}</td>
                                                <td>${(v.voucher_discount_percent == null) ? 0 : v.voucher_discount_percent}%</td>                                 
                                                <td>${v.voucher_quantity}</td>
                                                <td>${v.voucher_status eq 0 ? 'Hết' : 'Còn'}</td>
                                                <td>${v.voucher_date}</td>
                                                <td>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                                            type="button" id="btn-update-voucher"
                                                            class="btn btn-sm btn-success py-1 m-1"
                                                            data-voucher-id="${v.voucherID}"
                                                            data-voucher-name="${v.voucher_name}"
                                                            data-voucher-code ="${v.voucher_code}"
                                                            data-voucher-discount-percent="${v.voucher_discount_percent}"
                                                            data-voucher-quantity ="${v.voucher_quantity}"
                                                            data-voucher-status ="${v.voucher_status}"
                                                            data-voucher-date ="${v.voucher_date}"
                                                            data-bs-toggle="modal" data-bs-target="#update-voucher-modal">
                                                        Cập nhật
                                                    </button>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                                            type="button" id="btn-delete-voucher"
                                                            class="btn btn-sm btn-danger py-1 m-1"
                                                            data-voucher-id="${v.voucherID}"
                                                            data-voucher-name="${v.voucher_name}"
                                                            data-bs-toggle="modal" data-bs-target="#delete-voucher-modal">
                                                        Xóa
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>
                </main>
            </div>
        </div>
        <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
        <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
        <%@ include file="WEB-INF/jspf/admin/imports/dataTablesScript.jspf" %>
    </body>
</html>
