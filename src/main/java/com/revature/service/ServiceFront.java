/**
 * This class is the interaction point between the front end and the back end.
 * Requests are passed into handleRequest, and the ServiceFront will pass them to the
 * correct RequestHandler field.
 * 
 * The front end will not create instances of this class; they will use the BackEndUtil
 * class which will implement a singleton pattern.
 * 
 * NOTE: I'm very unsure about the quality of this organization style
 * 
 * @author Andrew Curry
 */
package com.revature.service;

import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSRequest.ERSRequestType;
import com.revature.service.comms.ERSResponse.ERSResponseType;
import com.revature.service.handlers.AuthRequestHandler;
import com.revature.service.handlers.ModifyRequestHandler;
import com.revature.service.handlers.ViewRequestHandler;

public class ServiceFront {
    
    // constants

    // class/static variables

    // instance variables
    private AuthRequestHandler arh;
    private ViewRequestHandler vrh;
    private ModifyRequestHandler mrh;

    // constructor(s)

    /**
     * Standard dependency-injection constructor
     */
    public ServiceFront(
            AuthRequestHandler arh,
            ViewRequestHandler vrh, 
            ModifyRequestHandler mrh){

        this.arh = arh;
        this.vrh = vrh;
        this.mrh = mrh;
    }

    // method(s)

    /**
     * Passes the given req to the appropriate RequestHandler and returns the resulting
     * response.
     * Assumes the req is not null.
     * 
     * @param req
     * @return
     */
    public ERSResponse handleERSRequest(ERSRequest req){
        
        ERSRequestType type = req.getType();
        switch(type){
            default:
                return new ERSResponse(
                        ERSResponseType.MALFORMED_REQUEST,
                        String.format("Request Type '%s' not recognized", type.name()));
        }
    }
}
