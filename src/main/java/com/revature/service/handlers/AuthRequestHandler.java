/**
 * This handler class handles requests related to user authorization (currently, just
 * logging in and logging out)
 * 
 * @author Andrew Curry
 */
package com.revature.service.handlers;

import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;

public class AuthRequestHandler extends RequestHandler {
    
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
        return null;
    }
}
