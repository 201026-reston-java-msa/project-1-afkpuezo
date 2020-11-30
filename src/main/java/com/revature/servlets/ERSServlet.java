/**
 * This class contains utilty methods shared by servlets in this project.
 */
package com.revature.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.model.UserProfile.UserRole;

public abstract class ERSServlet extends HttpServlet{

    private static final long serialVersionUID = 0L;
    
    // methods

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
}
