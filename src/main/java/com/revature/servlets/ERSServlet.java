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
import com.revature.service.BackEndUtil;
import com.revature.service.ServiceFront;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSResponse.ERSResponseType;
import com.revature.service.comms.ERSRequest.ERSRequestType;

  
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
     * Determines what kind of user is logged in to the request's session.
     * If the request's session does not have a user, or there is no session, defaults
     * to LOGGED_OUT.
     * 
     * @param request
     * @return
     */
    protected UserRole getCurrentUserRole(HttpServletRequest request){

        return getCurrentUserRole(request.getSession());
    }

    /**
     * Determines what kind of user is logged in to the current session.
     * If the request's session does not have a user, or there is no session, defaults
     * to LOGGED_OUT.
     * 
     * @param request
     * @return
     */
    protected UserRole getCurrentUserRole(HttpSession session){

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

    /**
     * Returns an ERSRequest with the given type and the info for the user currently
     * logged in to the given session.
     * 
     * @param type
     * @param session
     * @return
     */
    protected ERSRequest makeERSRequest(ERSRequestType type, HttpSession session){

        Integer userIDInteger = (Integer)session.getAttribute("currentUserID");
        int userID;
        if (userIDInteger == null) userID = -1;
        else userID = userIDInteger;

        return new ERSRequest(type, userID, getCurrentUserRole(session));
    }

    /**
     * Hands the given request to the service layer, and returns the response.
     * 
     * @param req
     * @returns
     */
    protected ERSResponse getResponse(ERSRequest req){
        ServiceFront sf = BackEndUtil.getBackEnd();
        return sf.handleERSRequest(req);
    }

    /**
     * Redirects the given response to the problem page, with the given message and
     * destination.
     * 
     * @param response
     * @param session
     * @param message
     * @param destination
     * @returns
     */
    protected void handleProblem (
            HttpServletResponse response,
            HttpSession session, 
            String message, 
            String destination) throws IOException {
        
        session.setAttribute("problemMessage", message);
        session.setAttribute("problemDestination", destination);
        response.sendRedirect("problem");
    }

    protected boolean isFailure(ERSResponse eres){
        return (eres.getType() != ERSResponseType.SUCCESS);
    }
}
