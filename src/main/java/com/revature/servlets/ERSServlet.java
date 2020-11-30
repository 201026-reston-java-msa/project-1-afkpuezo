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

    // constants
    protected static final String INVALID_USERNAME_MESSAGE
            = "Invalid Username: Must have at least 1 character and no spaces.";
    protected static final String INVALID_PASSWORD_MESSAGE
            = "Invalid Password: Must have at least 1 character and no spaces.";
    
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
    protected void redirectToMenu(HttpServletResponse response) throws IOException{
        response.sendRedirect("menu");
    }

    /** 
     * Determines if the given username is in the valid format.
     * Currently, that just means no spaces and at least 1 letter.
     * 
     * @param username
     * @return
    */
    protected boolean isUsernameValid(String username){
        
        if (username.length() < 1) return false;

        for (char c : username.toCharArray()){
            if (c == ' ') return false;
        }
        return true;
    }

    /** 
     * Determines if the given password is in the valid format.
     * Currently, that just means no spaces and at least 1 letter.
     * 
     * @param username
     * @return
    */
    protected boolean isPasswordValid(String password){
        
        return isUsernameValid(password); // same requirements
    }
}
