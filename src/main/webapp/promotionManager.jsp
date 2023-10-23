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

                        <!-- Voucher Tab Content -->
                        <div class="tab-pane fade" id="vouchers">
                            <div class="container-fluid p-4 shadow rounded-4 bg-white">
                                <table id="vouchers-table" class="table table-hover nowrap align-middle" style="width: 100%;">
                                    <h1 class="text-start fw-bold fs-3 mb-3">Quản lý Khuyến mãi</h1>
                                    <div id="voucher-button-container" class="row mb-2">
                                        <div class="col-sm-12 col-lg-5 mt-sm-2 mt-lg-0 d-flex flex-wrap gap-2 justify-content-lg-end">
                                            <button type="button" class="btn btn-success d-flex  align-items-center flex-shrink-0" 
                                                    id="btn-add-voucher"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#add-voucher-modal">
                                                <i class="ph-bold ph-plus fs-1 me-2"></i>
                                                Thêm Voucher
                                            </button>
                                            <button type="button" id="btn-update-voucher"
                                                    class="btn btn-primary btn-update d-flex  align-items-center flex-shrink-0" data-bs-toggle="modal"
                                                    data-bs-target="#update-voucher-modal">
                                                <i class="ph-bold ph-pencil fs-1 me-2"></i>
                                                Cập nhật
                                            </button>
                                            <button type="button" id="btn-delete-voucher"
                                                    class="btn btn-danger btn-delete d-flex align-items-center flex-shrink-0" data-bs-toggle="modal"
                                                    data-bs-target="#delete-voucher-modal">
                                                <i class="ph-bold ph-trash fs-1 me-2"></i>
                                                Xoá Voucher
                                            </button>
                                        </div>
                                    </div>
                                    <thead>
                                        <tr>
                                            <th class="col-sm-3 col-gap-5">Mã Khuyến mãi</th>  
                                            <th>Tên Khuyến mãi</th>
                                            <th>Mã Code</th>
                                            <th>Phần trăm KM</th>
                                            <th>Số lượng</th>
                                            <th>Trạng thái</th>
                                            <th>Thời gian</th>
                                            <!--<th >Thao tác</th>-->

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
