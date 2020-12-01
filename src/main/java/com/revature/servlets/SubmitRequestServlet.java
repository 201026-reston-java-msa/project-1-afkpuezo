/**
 * DOCSTRING
 */
package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitRequestServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public SubmitRequestServlet() {
        super();
    }

    /**
     * Serves up the html page with the form.
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
     * Processess the form input, and if valid, adds the new reimb-req.
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
