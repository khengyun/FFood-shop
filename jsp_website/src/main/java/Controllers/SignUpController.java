/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Models.Account;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import Validation.ValidationUtils;

public class SignUpController extends HttpServlet {

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
        String username = request.getParameter("txtAccountUsername");
        String email = request.getParameter("txtAccountEmail");
        String pass = (String) request.getAttribute("txtAccountPassword");
        // Truy xuất URL hiện tại từ session attribute
        HttpSession session = request.getSession();
        String previousUrl = (String) session.getAttribute("previousUrl");
        
        ValidationUtils valid = new ValidationUtils();
        
        if (!valid.signUpValidation(username,email,pass)){
            response.sendRedirect("/");
            return;
        }
        
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, pass, "user");
        if (accountDAO.getAccount(email) != null) {
            session.setAttribute("toastMessage", "error-register-existing-email");
            response.sendRedirect("/");
            return;
        }
        try {
            if (accountDAO.login(account)) {
                session.setAttribute("toastMessage", "success-register");
                if (previousUrl != null) {
                    // Chuyển hướng người dùng về trang hiện tại
                    response.sendRedirect(previousUrl);
                } else {
                    // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
                    response.sendRedirect("/");
                }
            } else {
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
                            message.setSubject("Xác nhận Email của bạn");
                            message.setContent("Mã OTP của bạn là: " + otpvalue + ".<br />Để tránh mất tài khoản, đừng chia sẻ mã này cho bất cứ ai khác.", "text/html; charset=UTF-8");

                            // Send message
                            Transport.send(message);
                            System.out.println("Message sent successfully");
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }

                        request.setAttribute("message", "OTP is sent to your email ID");
                        session.setAttribute("otp", otpvalue);
                        session.setAttribute("type_otp", "sign_up");
                        session.setAttribute("registerUser", account);
                        session.setAttribute("triggerOTP", true);
                        response.sendRedirect("/");
                    }

                } catch (IOException e) {
                  session.setAttribute("toastMessage", "error-register");
                    response.sendRedirect("/");
                }
//              
            }
        } catch (SQLException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
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
