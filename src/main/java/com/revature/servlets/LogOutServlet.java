/**
 * Handles logging out.
 * 
 * @author Andrew Curry
 */
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSRequest.ERSRequestType;
import com.revature.model.UserProfile;


import com.revature.service.comms.ERSResponse;

public class LogOutServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public LogOutServlet() {
        super();
    }

    /**
     * Carries out the actual logging out.
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
}
