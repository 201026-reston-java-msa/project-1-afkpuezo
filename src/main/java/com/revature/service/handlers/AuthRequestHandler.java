/**
 * This handler class handles requests related to user authorization (currently, just
 * logging in and logging out)
 * 
 * @author Andrew Curry
 */
package com.revature.service.handlers;

import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSResponse.ERSResponseType;

public class AuthRequestHandler extends RequestHandler {
    
    // instance variables ------------------
    private UserProfileDAO updao;
    private ReimbursementRequestDAO rrdao;

    // constructor(s) ----------------------

    public AuthRequestHandler(UserProfileDAO updao, ReimbursementRequestDAO rrdao){

        this.updao = updao;
        this.rrdao = rrdao;
    }

    // handler methods

    /**
     * Attempts to log in to the user profile indicated by the parameters in the request.
     * Succeeds only if the username matches an existing account, and the given password
     * matches the password for the given account.
     * 
     * @param req
     * @return
     */
    public ERSResponse handleLogIn(ERSRequest req){

        try{
            if (!req.hasParameter(ERSRequest.USERNAME) 
                    || !req.hasParameter(ERSRequest.PASSWORD))
                return getMalformedRequestResponse();

            String username = req.getParameter(ERSRequest.USERNAME);
            if (!updao.checkExists(username)) 
            return getUserDoesNotExistResponse(username);
            
            String password = req.getParameter(ERSRequest.PASSWORD);
            if (!checkPassword(username, password))
                return getIncorrectPasswordResponse(username);

            return new ERSResponse(ERSResponseType.SUCCESS);
        }
        catch(DAOException e){
            return getGenericDAOExceptionResponse();
        }
    }

    // helpers

    /**
     * Checks to see if the given password is valid for the account indicated by the
     * given username.
     * 
     * NOTE: currently, encryption is not implemented.
     * TODO: encryption
     * 
     * @param username
     * @param password
     * @return
     */
    private boolean checkPassword(String username, String password) throws DAOException{
        
        String truePassword = updao.getPassword(username);
        return password.equals(truePassword);
    }

    /**
     * Returns a standardized response indicating the user was trying to log in with an
     * incorrect password.
     * 
     * This is (or should be) the only handler class that needs to deal with passwords,
     * so this method will not be in the abstract RequestHandler.
     * 
     * @param username
     * @return
     */
    private ERSResponse getIncorrectPasswordResponse(String username) {

        return new ERSResponse(
                ERSResponseType.INVALID_PARAMETER,
                String.format("Invalid password for account '%s'", username));
    }
}
