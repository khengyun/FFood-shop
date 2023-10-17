<%-- 
    Document   : index
    Created on : Jun 3, 2023, 5:07:28 PM
    Author     : CE171454 Hua Tien Thanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jspf/common/imports/base.jspf" %>
<!DOCTYPE html>
<html lang="en-US" dir="ltr">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Title -->
    <title>FFood | Đặt món nhanh, rẻ, tiện</title>
    <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
    <link rel="stylesheet" href="assets/css/style.css"/>
  </head>
  <body>
    <%@ include file="WEB-INF/jspf/common/components/header.jspf" %>
    <%@ include file="WEB-INF/jspf/guest/components/cart.jspf" %>
    <%@ include file="WEB-INF/jspf/guest/components/login.jspf" %>
    <%@ include file="WEB-INF/jspf/guest/components/signup.jspf" %>
    <%@ include file="WEB-INF/jspf/guest/components/success.jspf" %>
    <!-- Main Content -->
    <main class="main" id="top">

      <!-- Hero section -->
      <%@ include file="WEB-INF/jspf/guest/components/hero.jspf" %>

      <!-- Food menu -->
      <section class="py-4 overflow-hidden">
        <div class="container">
          <div class="row flex-grow-1 mb-6">
            <div class="col-lg-7 mx-auto text-center mt-5 mb-3">
              <h5 class="fw-bold fs-3 fs-lg-5 lh-sm">Danh sách món ăn</h5>
            </div>
          </div>
          <!-- Food Categories -->
          <%@ include file="WEB-INF/jspf/guest/components/foodCategories.jspf" %>         
          <!-- Food list -->
          <%@ include file="WEB-INF/jspf/guest/components/foodList.jspf" %>
        </div>
      </section>

      <!-- Order instructions -->
      <%@ include file="WEB-INF/jspf/guest/components/orderInstructions.jspf" %>

      <!-- CTA order banner -->
      <%@ include file="WEB-INF/jspf/guest/components/ctaOrderBanner.jspf" %>

      <!-- Footer -->
      <%@ include file="WEB-INF/jspf/common/components/footer.jspf" %>
    </main>
    <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %>
    <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
    <script src="assets/js/home.js"></script>
  </body>
</html>