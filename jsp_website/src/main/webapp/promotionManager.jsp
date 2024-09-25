<%@ include file="WEB-INF/jspf/common/imports/base.jspf" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>FFood | Promotion Manager Dashboard</title>

        <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
        <%@ include file="WEB-INF/jspf/admin/imports/dataTablesStyle.jspf" %>
        <%@ include file="WEB-INF/jspf/promotionManager/imports/chartjs.jspf" %>
    </head>
    <body>
        <div class="container-fluid m-0 p-0">
            <div class="d-flex flex-row m-0">
                <%@ include file="WEB-INF/jspf/promotionManager/components/addVoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/updatevoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/deleteVoucher.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/updateFood.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/failure.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/success.jspf" %>
                <%@ include file="WEB-INF/jspf/promotionManager/components/promotionManagerSidebar.jspf" %>
                <%@ include file="WEB-INF/jspf/common/components/toast.jspf" %>

                <!-- Main Content -->
                <main class="w-100 p-4 bg-surface">
                    <div class="tab-content" data-initial-tab="${tabID}">
                        <!-- Home Tab Content -->
                        <%@ include file="WEB-INF/jspf/promotionManager/home.jspf" %>
                        <!-- Food & Drinks Tab Content -->
                        <%@ include file="WEB-INF/jspf/promotionManager/foods.jspf" %>
                        <!-- Voucher Tab Content -->
                        <%@ include file="WEB-INF/jspf/promotionManager/voucher.jspf" %>

                    </div>
                </main>
            </div>
        </div>
        <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
        <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
        <%@ include file="WEB-INF/jspf/promotionManager/imports/dataTablesScript.jspf" %>
    </body>
</html>
