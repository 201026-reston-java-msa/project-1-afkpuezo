/**
 * This class contains utilty methods shared by servlets in this project.
 */
package com.revature.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.UserProfile.UserRole;

public abstract class ERSServlet extends HttpServlet{

    private static final long serialVersionUID = 0L;
    
    // methods

    /**
     * Returns a string containing the html text at the start of every web page (styling, 
     * etc)
     * Currently returns empty string.
     * 
     * @return
     */
    protected String getGenericHead(){
        return "<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\"><title>Insert title here</title></head><body>";
    }

    /**
     * Returns a string containing the html text at the end of every web page
     * Currently returns empty string.
     * 
     * @return
     */
    protected String getGenericFoot(){
        return "</body></html>";
    }

    /**
     * Determines what kind of user is logged in to the current session.
     * If the request's session does not have a user, or there is no session, defaults
     * to LOGGED_OUT.
     * 
     * @param request
     * @return
     */
    protected UserRole getCurrentUserRole(HttpServletRequest request){

        HttpSession session = request.getSession();

        if (session != null && session.getAttribute("role") != null) {
			return (UserRole) session.getAttribute("role");
		} else {
            return UserRole.LOGGED_OUT;
        }
    }

    /**
     * Redirects the given response to the correct landing page when there is no logged-in
     * user.
     * 
     * @param response
     */
    protected void redirectLoggedOut(HttpServletResponse response) throws IOException{
        response.sendRedirect("index");
    }
}
