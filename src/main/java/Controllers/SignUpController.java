/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Models.Account;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
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
import sun.security.jgss.GSSCaller;
import sun.security.jgss.GSSUtil;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
        String contextPath = request.getContextPath();
        String username = request.getParameter("txtAccountUsername");
        String email = request.getParameter("txtAccountEmail");
        String pass = (String) request.getAttribute("txtAccountPassword");
        // Truy xuất URL hiện tại từ session attribute
        HttpSession session = request.getSession();
        String previousUrl = (String) session.getAttribute("previousUrl");
        
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account(username, email, pass, "user");
        try {
            if (accountDAO.login(account)) {
                if (previousUrl != null) {
                    // Chuyển hướng người dùng về trang hiện tại
                    response.sendRedirect(previousUrl);
                } else {
                    // Nếu không có URL trước đó, chuyển hướng người dùng về trang mặc định
                    response.sendRedirect("/");
                }
            } else {
                try {

                    RequestDispatcher dispatcher = null;
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
                            message.setSubject("Hello");
                            message.setText("Your OTP is: " + otpvalue);

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
                        response.sendRedirect("/home#verify_OTP");
                    }

                } catch (IOException e) {
                    System.out.println("Could not send user register");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
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
