
package controller.user;

import dal.UserDBContext;
import entity.User;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;
import java.util.Random;

public class forgotPasswordServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet forgotPasswordServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet forgotPasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("view/profile/forgot_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDBContext f = new UserDBContext();
        
        if (!f.checkEmailExist(email)) {
            request.setAttribute("error", "Email không tồn tại");
            request.getRequestDispatcher("view/profile/forgot_password.jsp?error=invalidIdentifier").forward(request, response);
            return;
        }
        User user = f.getUserByEmail(email);

        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String to = email;
        String from = "duytvhe172200@fpt.edu.vn";
        String host = "smtp.gmail.com";
        String username = "duytvhe172200@fpt.edu.vn";
        String password = "piduieyazupmetbw";
        
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); 
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Use TLS

        Session mailSession = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Password Reset Verification Code");
            message.setText("Your verification code is: " + code);
            
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        HttpSession session = request.getSession();
        String strCode = String.valueOf(code);
        session.setAttribute("verificationCode", strCode);
        session.setAttribute("email", email);
        session.setAttribute("user", user);
        response.sendRedirect("verifycode");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
