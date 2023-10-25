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

        <title>FFood | Staff Dashboard</title>

        <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
        <%@ include file="WEB-INF/jspf/admin/imports/dataTablesStyle.jspf" %>
    </head>
    <body>
        <div class="container-fluid m-0 p-0">
            <div class="d-flex flex-row m-0">
                <%@ include file="WEB-INF/jspf/staff/components/addFood.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/updateFood.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/deleteFood.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/failure.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/success.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/staffSidebar.jspf" %>

                <!-- Main Content -->
                <main class="w-100 p-4 bg-surface">
                    <div class="tab-content">
                        <!-- Home Tab Content -->
                        <%@ include file="WEB-INF/jspf/staff/home.jspf" %>
                        <!-- Food & Drinks Tab Content -->
                        <%@ include file="WEB-INF/jspf/staff/foods.jspf" %>

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



                    </div>
                </main>
            </div>
        </div>
        <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
        <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
        <%@ include file="WEB-INF/jspf/staff/imports/dataTablesScript.jspf" %>
    </body>
</html>
