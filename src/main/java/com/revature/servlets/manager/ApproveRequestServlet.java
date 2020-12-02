/**
 * Handles a manager approving a request.
 */
package com.revature.servlets.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.servlets.ERSServlet;

public class ApproveRequestServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;
    
    public ApproveRequestServlet() {
        super();
    }

    /**
     * Serves the basic html page.
     * 
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("approve_request.html").forward(request, response); 
    }
    
    /**
     * Validate the ID number and carry out the actual action.
     * 
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idString = request.getParameter("reimbID");

        response.getWriter().write(idString);
	}
}
