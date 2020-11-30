/**
 * This servlet handles the index page - that is, it sends the user to appropriate menu.
 */
package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.UserProfile.UserRole;

public class IndexServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public IndexServlet() {
        super();
    }

    /**
     * DESCRIPTION
     * 
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {       
        
        UserRole role = getCurrentUserRole(request);

        switch(role){
            case EMPLOYEE:
                response.getWriter().write("EMPLOYEE");
                break;
            case MANAGER:
                response.getWriter().write("MANAGER");
                break;
            default:
                response.sendRedirect("nousermenu");
                break;
        }
    }
    
    /**
     * DESCRIPTION
     * 
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        session.setAttribute("role", UserRole.EMPLOYEE);
        response.sendRedirect("index");
	}
}
