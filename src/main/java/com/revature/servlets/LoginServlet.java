/**
 * Handles logging in to a new user account.
 * 
 * @author Andrew Curry
 */
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings(value="all")
public class LoginServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public LoginServlet() {
        super();
    }

    /**
     * Determines what options to show to the current user, based on their  role.
     * 
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("login.html").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!isUsernameValid(username)){
            session.setAttribute("problemMessage", INVALID_USERNAME_MESSAGE);
            session.setAttribute("problemDestination", "login");
            response.sendRedirect("problem");
            return;
        }

        if (!isUsernameValid(password)){
            session.setAttribute("problemMessage", INVALID_PASSWORD_MESSAGE);
            session.setAttribute("problemDestination", "login");
            response.sendRedirect("problem");
            return;
        }

        // now that it's valid we can try to log in
        PrintWriter writer = response.getWriter();
        writer.write("INPUT VALID!\n");
        writer.write(username + " " + password);
	}
}
