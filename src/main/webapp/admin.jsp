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

    <title>FFood | Dashboard</title>
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
        <!-- Main Content -->
        <main class="w-100 p-4 bg-surface">
          <div class="tab-content">
            <!-- Home Tab Content -->
            <%@ include file="WEB-INF/jspf/admin/home.jspf" %>
            <!-- Food & Drinks Tab Content -->
            <%@ include file="WEB-INF/jspf/admin/foods.jspf" %>
            <!-- Users Tab Content -->
            <%@ include file="WEB-INF/jspf/admin/user.jspf" %>
            <!-- Orders Tab Content -->
            <%@ include file="WEB-INF/jspf/admin/order.jspf" %>
          </div>
        </main>
      </div>
    </div>
    <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
    <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
    <%@ include file="WEB-INF/jspf/admin/imports/dataTablesScript.jspf" %>
  </body>
</html>
