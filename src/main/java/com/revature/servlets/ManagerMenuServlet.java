/**
 * This servlet displays the menu of actions available to a manager.
 * 
 * @author Andrew Curry
 */
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManagerMenuServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public ManagerMenuServlet() {
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
        
        request.getRequestDispatcher("manager_menu.html").forward(request, response);
    }
}