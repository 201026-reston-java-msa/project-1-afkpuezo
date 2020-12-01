/**
 * This servlet displays result messages (problem or success) to the user and then directs
 * them to the appropriate page.
 * 
 * I didn't call it an error page because it seems distinct from HTTP errors.
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
public class ResultServlet extends ERSServlet {

    private static final long serialVersionUID = 0L;

    public ResultServlet() {
        super();
    }

    /**
     * Display the problem message and the okay button.
     * 
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // get the parameters/attributes from the page that sent the user here
        HttpSession session = request.getSession();
        String message = (String)session.getAttribute("resultMessage");
        if (message == null) message = "";
        String destination = (String)session.getAttribute("resultDestination");
        if (destination == null) destination = "menu";

        // very clumsy
        String html = readTextFile("result.html");

        html = html.replaceFirst("%MESSAGE%", message);
        html = html.replaceFirst("%DEST%", destination);

        response.getWriter().write(html);
    }
}