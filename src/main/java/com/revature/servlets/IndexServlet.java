/**
 * This servlet handles the index page - that is, it determines what actions are available
 * to the current user and presents them.
 */
package com.revature.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.UserProfile.UserRole;
import com.revature.service.BackEndUtil;

import org.terracotta.agent.repkg.de.schlichtherle.io.FileWriter;


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
        
        String message;
        UserRole role = getCurrentUserRole(request);
        if (role == UserRole.LOGGED_OUT) message = "Not logged in";
        else message = "Logged in as a(n) " + role;
        
        String html = "<h1>Welcome to Servlets...</h1><form action=\"index\" method=\"post\"/>%MESSAGE%<input type=\"submit\" value=\"This should be changed!\"/></form>";
        html = html.replaceAll("%MESSAGE%", message);

        PrintWriter writer = response.getWriter();
        writer.write(getGenericHead());
        writer.write(html);
        writer.write(getGenericFoot());
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
        session.setAttribute("role", UserRole.EMPLOYEE);
        response.sendRedirect("index");
	}
}
