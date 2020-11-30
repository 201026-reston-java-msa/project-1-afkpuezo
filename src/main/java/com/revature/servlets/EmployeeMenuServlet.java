/**
 * This servlet displays the menu of actions available to an employee
 */
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EmployeeMenuServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public EmployeeMenuServlet() {
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
        
        request.getRequestDispatcher("employee_menu.html").forward(request, response);
    }

}
