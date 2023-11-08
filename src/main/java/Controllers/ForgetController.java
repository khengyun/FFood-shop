/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgetController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("btnSubmit") != null
                && ((String) request.getParameter("btnSubmit")).equals("Submit")) {
            String email = request.getParameter("txtEmail");
            AccountDAO accountDAO = new AccountDAO();
            if (accountDAO.getAccount(email) != null) {
                try {
                    int otpvalue = 0;

                    if (email != null && !email.equals("")) {
                        // Sending OTP
                        Random rand = new Random();
                        otpvalue = rand.nextInt(1255650);

                        String to = email; // Change accordingly

                        // Get the session object
                        Properties props = new Properties();
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.socketFactory.port", "465");
                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.port", "465");
                        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("ffood.shop.cantho@gmail.com", "xhwjyflpdsgeizzr"); // Put your email ID and password here
                            }
                        });

                        // Compose message
                        try {
                            MimeMessage message = new MimeMessage(mailSession);
                            message.setFrom(new InternetAddress("your-email@example.com")); // Change accordingly
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                            message.setSubject("Xác nhận email của bạn", "utf-8");
                            message.setContent("Mã xác nhận của bạn là: " + otpvalue + "<br />Không chia sẻ mã này cho người khác.", "text/html; charset=utf-8");
                            
                            // Send message
                            Transport.send(message);
                            System.out.println("Message sent successfully");
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                        request.setAttribute("message", "OTP is sent to your email ID");
                        session.setAttribute("otp", otpvalue);
                        session.setAttribute("email", email);
                        session.setAttribute("type_otp", "forget");
                        session.setAttribute("triggerOTP", true);
                        response.sendRedirect("/");
                    }

                } catch (IOException e) {
                    System.out.println("Could not send user register");
                    session.setAttribute("toastMessage", "error-send-otp");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            } else {
              session.setAttribute("toastMessage", "error-no-email-found");
              response.sendRedirect("/");
            }
        } else {
          session.setAttribute("toastMessage", "error-no-email-found");
          response.sendRedirect("/");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
