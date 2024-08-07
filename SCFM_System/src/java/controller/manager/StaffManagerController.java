package controller.manager;

import controller.authentication.BaseRBACController;
import dal.UserDBContext;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import static util.DateHelper.convertStringToDate;

public class StaffManagerController extends BaseRBACController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet registerStaffController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registerStaffController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        request.getRequestDispatcher("../view/manager/CreateStaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
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
        String mess = null;
        String messSuccess = "";
        boolean gender = Boolean.parseBoolean(genderParam);

        if (genderParam.equalsIgnoreCase("Male")) {
            gender = true;
        } else {
            gender = false;
        }

        Date d = convertStringToDate(dobParam);
        User u = new User(maso, username, password, fullName, email, gender, mobile, address, d, picture, active);

        if (!da.checkUserNullOrSpace2(u)) {
            mess = "Register Fail, Please input full your information";
        } else if (da.checkUsername(username)) {
            mess = "Username Exist";
        } else if (da.checkEmailExist(email)) {
            mess = "Email Exist";
        } else if (!da.checkPassword(username, fullName, password)) {
            mess = "Invalid password, please use another password";
        } else if (!da.isFPTStaffEmail(email)) {
            mess = "Pls use FPT email";
        } else if (!da.isValidUsername(username)) {
            mess = "Username is not valid";
        } else if (da.getValidPhoneNumber(mobile) == null) {
            mess = "The phone number does not meet the requirements, please go to update to fix it";
        } else if (!da.isUserOldEnoughStaff(dobParam)) {
            mess = "User need > 24 years old";
        }else {
            da.createStaff(u);
            messSuccess = "Register Successful";
            request.setAttribute("messSuccess", messSuccess);
            request.getRequestDispatcher("/view/manager/home.jsp").forward(request, response);
            return; 
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
        request.getRequestDispatcher("/view/manager/CreateStaff.jsp").forward(request, response);

        if (mess != null) {
            request.setAttribute("mess", mess);
            request.getRequestDispatcher("../view/manager/CreateStaff.jsp").forward(request, response);
        } else {
            da.createStaff(u);
            messSuccess = "Register Successful";
            request.setAttribute("messSuccess", messSuccess);
            response.sendRedirect("home");
        }
    }
}
