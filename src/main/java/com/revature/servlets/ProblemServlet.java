/**
 * DOCSTRING
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
public class ProblemServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public ProblemServlet() {
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
		
	}
}
