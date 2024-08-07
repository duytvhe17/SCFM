/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;



import dal.RoleDBContext;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public abstract class BaseRBACController extends BaseRequiredAuthenticationController {

    private ArrayList<Role> getRoles(User user,HttpServletRequest response)
    {
        RoleDBContext db = new RoleDBContext();
        String url = response.getServletPath();
        return db.getByUsernameAndUrl(user.getUserName(), url);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException
    {
        ArrayList<Role> roles = getRoles(user,request);
        if(roles.size()<1)
        {
            response.getWriter().println("access denied!");
        }
        else
        {
            doGet(request, response, user, roles);
        }
    }
     
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles)
            throws ServletException, IOException;

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException{
        ArrayList<Role> roles = getRoles(user,request);
        if(roles.size()<1)
        {
            response.getWriter().println("access denied!");
        }
        else
        {
            doPost(request, response, user, roles);
        }
    }
    
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles)
            throws ServletException, IOException;
    
}
 