/**
 * DOCSTRING
 */
package com.revature.servlets.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.ReimbursementRequest;
import com.revature.model.UserProfile.UserRole;
import com.revature.servlets.ERSServlet;

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

        if (getCurrentUserRole(request) != UserRole.EMPLOYEE) {
            redirectToMenu(response);
            return;
        }

        request.getRequestDispatcher("submit_request.html").forward(request, response);
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

        String moneyString = request.getParameter("moneyAmount");
        String typeString = request.getParameter("type");
        String description = request.getParameter("description");

        if (!isMoneyStringValid(moneyString))
            response.getWriter().write("Invalid money");

        response.getWriter().write(
                moneyStringToLong(moneyString) + " " 
                + ReimbursementRequest.ReimbursementType.fromString(typeString) 
                + " " + description);
    }
}
