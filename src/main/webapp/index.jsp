<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/common/imports/base.jspf" %>
<!DOCTYPE html>
<html lang="vi" dir="ltr">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- Title -->
    <title>FFood | Đặt món nhanh, rẻ, tiện</title>

    <!-- Locale for JSTL fmt tags -->
    <fmt:setLocale value="vi" scope="session"/>

    <%@ include file="WEB-INF/jspf/common/imports/resources.jspf" %>
    <link rel="stylesheet" href="assets/css/style.css" />
    
    <script src="https://accounts.google.com/gsi/client" async></script>
  </head>
  <body>
    <%@ include file="WEB-INF/jspf/common/components/mainHeader.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/cart.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/login.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/signup.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/forget.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/changePassword.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/verify.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/failure.jspf" %> 
    <%@ include file="WEB-INF/jspf/guest/components/camera.jspf" %>
    <%@ include file="WEB-INF/jspf/common/components/toast.jspf" %>
    
    <!-- Main Content -->
    <main class="main" id="top">
      <!-- Hero section -->
      <%@ include file="WEB-INF/jspf/guest/components/hero.jspf" %>

      <!-- Food menu -->
      <section class="py-4 overflow-hidden">
        <div class="container">
          <div class="row flex-grow-1 mb-2">
            <div class="col-lg-7 mx-auto text-center mt-5 mb-3">
              <h5 class="fw-bold fs-3 fs-lg-5 lh-sm">Danh sách món ăn</h5>
            </div>
          </div>
          <!-- Food Categories -->
          <%@ include file="WEB-INF/jspf/guest/components/foodCategories.jspf"
          %>
          <!-- Food list -->
          <%@ include file="WEB-INF/jspf/guest/components/foodList.jspf" %>

          <!-- Food details -->
          <%@ include file="WEB-INF/jspf/guest/components/foodDetails.jspf" %>
        </div>
      </section>

      <!-- Order instructions -->
      <%@ include file="WEB-INF/jspf/guest/components/orderInstructions.jspf" %>

      <!--GOOGLE MAP-->
      <section class="py-0">
        <div class="container-fluid px-0 py-0">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3817.1930812065903!2d105.73423656511284!3d10.013112967960716!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31a0882139720a77%3A0x3916a227d0b95a64!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgQ-G6p24gVGjGoQ!5e0!3m2!1sen!2s!4v1697447209454!5m2!1sen!2s"
            width="100%"
            height="600"
            style="border: 0"
            allowfullscreen=""
            loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"
          ></iframe>
        </div>
      </section>

      <!-- CTA order banner -->
      <%@ include file="WEB-INF/jspf/guest/components/ctaOrderBanner.jspf" %>

      <!-- Footer -->
      <%@ include file="WEB-INF/jspf/common/components/footer.jspf" %>
    </main>
    <%@ include file="WEB-INF/jspf/common/imports/javascript.jspf" %> 
    <%@ include file="WEB-INF/jspf/common/imports/validation.jspf" %>
    <script src="assets/js/home.js"></script>
    <script src="assets/js/userNotify.js"></script>
  </body>
</html>
