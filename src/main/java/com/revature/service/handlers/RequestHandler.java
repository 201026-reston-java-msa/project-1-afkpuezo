/**
 * An abstrast class extended by individual handlers, contains some shared helper/utility
 * methods that can be used by each of the actual request handlers.
 */
package com.revature.service.handlers;

import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSResponse.ERSResponseType;

public abstract class RequestHandler {

    /**
     * Returns a response indicating that a user was not found.
     * 
     * @param userID : included in the message
     * @return
     */
    protected ERSResponse getUserDoesNotExistResponse(int userID) {

        return new ERSResponse(ERSResponseType.INVALID_PARAMETER,
                String.format("No user profile with ID %d was found.", userID));
    }

    protected ERSResponse getGenericDAOExceptionResponse() {

        return new ERSResponse(
                ERSResponseType.DATABASE_ERROR, 
                "There was a problem communicating with the database.");
    }
}
