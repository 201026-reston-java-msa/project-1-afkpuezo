/**
 * This servlet handles the index page - that is, it determines what actions are available
 * to the current user and presents them.
 */
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.service.BackEndUtil;

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

        request.getRequestDispatcher("index.html").forward(request, response);
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
		response.getWriter().write("Hello world!");
	}
}
