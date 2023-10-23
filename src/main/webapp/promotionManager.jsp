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

        <title>FFood | Promotion Manager Dashboard</title>

    <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
    <%@ include file="WEB-INF/jspf/admin/imports/dataTablesStyle.jspf" %>
    </head>
    <body>
        <div class="container-fluid m-0 p-0">
            <div class="d-flex flex-row m-0">
                <%@ include file="WEB-INF/jspf/promotionManager/components/addVoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/updatevoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/deleteVoucher.jspf" %>
                
                <%@ include file="WEB-INF/jspf/promotionManager/components/promotionManagerSidebar.jspf" %>

                <!-- Main Content -->
                <main class="w-100 p-4 bg-surface">
                    <div class="tab-content">
                        <!-- Home Tab Content -->
                        <%@ include file="WEB-INF/jspf/promotionManager/home.jspf" %>
                     
                        
                          <!-- Promotion Tab Content -->
                        <div class="tab-pane fade" id="promotions">
                            <div class="container-fluid p-2">
                                <table id="users-table" class="table table-bordered table-striped">
                                    <h1 class="text-center fw-bold fs-3" align="left">Khuyến mãi</h1>
                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                            type="button" class="btn btn-sm btn-success py-1 my-2 me-2" data-bs-toggle="modal" data-bs-target="#add-voucher-modal">
                                        Tạo Khuyến mãi
                                    </button>
                                    <thead>
                                        <tr>
                                            <th class="col-sm-3 col-gap-5">Mã Khuyến mãi</th>  
                                            <th>Tên Khuyến mãi</th>
                                            <th>Voucher</th>
                                            <th >Thao tác</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${voucherList}" var="v">
                                            <tr>
                                                <td>${v.voucherID}</td>
                                                <td>${v.name}</td>

                                                <td>${(v.voucher_discount_percent == null) ? 0 : v.voucher_discount_percent}%</td>                                 
                                                <td>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                                            type="button" id="btn-update-voucher"
                                                            class="btn btn-sm btn-success py-1 m-1"
                                                            data-voucher-id="${v.voucherID}"
                                                            data-voucher-name="${v.name}"
                                                            data-voucher-discount-percent="${v.voucher_discount_percent}"
                                                            data-bs-toggle="modal" data-bs-target="#update-voucher-modal">
                                                        Cập nhật
                                                    </button>
                                                    <button style="color: white;border-color: rgba(207, 126, 0, 1); background-color: rgba(207, 126, 0, 1);"
                                                            type="button" id="btn-delete-voucher"
                                                            class="btn btn-sm btn-danger py-1 m-1"
                                                            data-voucher-id="${v.voucherID}"
                                                            data-voucher-name="${v.name}"
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
        <%@ include file="WEB-INF/jspf/promotionManager/imports/dataTablesScript.jspf" %>
    </body>
</html>
