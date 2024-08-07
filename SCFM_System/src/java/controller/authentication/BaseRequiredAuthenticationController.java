package controller.authentication;

import dal.UserDBContext;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author sonnt
 */
public abstract class BaseRequiredAuthenticationController extends HttpServlet {
    
    private User getAuthentication(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        if(user==null)
        {
            Cookie[] cookies = request.getCookies();
            if(cookies!=null)
            {
                String username = null;
                String password = null;
                for (Cookie cooky : cookies) {
                    if(cooky.getName().equals("username"))
                        username = cooky.getValue();
                    
                    if(cooky.getName().equals("password"))
                        password = cooky.getValue();
                    
                    if(username != null && password !=null)
                        break;
                }
                if(username == null || password == null)
                    return null;
                else
                {
                    UserDBContext db = new UserDBContext();
                    User test = db.getUserByUsernamePassword(username, password);
                    if(test!=null)
                        session.setAttribute("user", test);
                    return test;
                }
            }
        }
        return user ;
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getAuthentication(request);
        if(user!=null)
        {
            doPost(request, response, user);
        }
        else
        {
            response.getWriter().println("access denied!");
        }
    }
    
    
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response,User user) 
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = getAuthentication(request);
        if(user!=null)
        {
            doGet(request, response, user);
        }
        else
        {
            response.getWriter().println("access denied!");
        }
    
    }
    
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response,User user) 
            throws ServletException, IOException;
    
}
