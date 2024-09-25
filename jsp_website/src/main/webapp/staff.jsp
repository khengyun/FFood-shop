<%-- 
    Document   : admin
    Created on : Jul 2, 2023, 10:36:53 PM
    Author     : CE171454 Hua Tien Thanh
--%>
<%@ include file="WEB-INF/jspf/common/imports/base.jspf" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>FFood | Staff Dashboard</title>

        <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
        <%@ include file="WEB-INF/jspf/admin/imports/dataTablesStyle.jspf" %>
        <%@ include file="WEB-INF/jspf/staff/imports/chartjs.jspf" %>
    </head>
    <body>
        <div class="container-fluid m-0 p-0">
            <div class="d-flex flex-row m-0">
                <%@ include file="WEB-INF/jspf/staff/components/addFood.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/updateFood.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/deleteFood.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/updateOrder.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/nextOrder.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/failure.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/success.jspf" %>
                <%@ include file="WEB-INF/jspf/staff/components/staffSidebar.jspf" %>
                <%@ include file="WEB-INF/jspf/common/components/toast.jspf" %>

                <!-- Main Content -->
                <main class="w-100 p-4 bg-surface">
                    <div class="tab-content" data-initial-tab="${tabID}">
                        <!-- Home Tab Content -->
                        <%@ include file="WEB-INF/jspf/staff/home.jspf" %>
                        <!-- Food & Drinks Tab Content -->
                        <%@ include file="WEB-INF/jspf/staff/foods.jspf" %>
                        <!-- Order Tab Content -->
                        <%@ include file="WEB-INF/jspf/staff/orders.jspf" %>
                    </div>
                </main>
            </div>
        </div>
        <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
        <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
        <%@ include file="WEB-INF/jspf/staff/imports/dataTablesScript.jspf" %>
    </body>
</html>
