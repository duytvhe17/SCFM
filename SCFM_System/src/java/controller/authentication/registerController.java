package controller.authentication;

import static util.DateHelper.convertStringToDate;
import dal.UserDBContext;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;

public class registerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet registerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registerController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/authentication/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDBContext da = new UserDBContext();

        String username = request.getParameter("userName");
        String password = request.getParameter("passWord");
        String maso = request.getParameter("maso");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String genderParam = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String dobParam = request.getParameter("dateOfBirth");
        String picture = request.getParameter("picture"); // Lấy trường picture từ form
        boolean active = Boolean.parseBoolean(request.getParameter("active")); // Lấy trường active từ form
        String mess = "null";
        String messSuccess = "";
        boolean gender = Boolean.parseBoolean(genderParam);

        if (genderParam.equalsIgnoreCase("Male")) {
            gender = true;
        } else {
            gender = false;
        }

        Date d = convertStringToDate(dobParam);
        User u = new User(maso, username, password, fullName, email, gender, mobile, address, d, null, active);

        if (!da.checkUserNullOrSpace(u)) {
            mess = "Register Fail, Please input full your information";
        } else if (da.checkUsername(username)) {
            mess = "Username Exist";
        } else if (da.checkEmailExist(email)) {
            mess = "Email Exist";
        } else if (!da.checkPassword(username, fullName, password)) {
            mess = "Invalid password, please use another password";
        } else if (!da.isFPTEmail(email)) {
            mess = "Pls use FPT email";
        } else if (!da.isValidUsername(username)) {
            mess = "Username is not valid";
        } else if (da.getValidPhoneNumber(mobile) == null) {
            mess = "The phone number does not meet the requirements, please go to update to fix it";
        } else if (!da.isUserOldEnough(dobParam)) {
            mess = "User need > 16 years old";
        } else {
            da.registerUser(u);
            messSuccess = "Register Successful";
            request.setAttribute("messSuccess", messSuccess);
            request.getRequestDispatcher("/view/authentication/login.jsp").forward(request, response);
            return; // Thêm return để không tiếp tục thực hiện các lệnh dưới đây
        }
        request.setAttribute("userName", username);
        request.setAttribute("passWord", password);
        request.setAttribute("maso", maso);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("gender", genderParam);
        request.setAttribute("mobile", mobile);
        request.setAttribute("address", address);
        request.setAttribute("dateOfBirth", dobParam);
        request.setAttribute("mess", mess);
        request.getRequestDispatcher("/view/authentication/register.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
