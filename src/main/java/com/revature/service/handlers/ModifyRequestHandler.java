/**
 * This handler class handles requests that modify the system state / change data in the
 * database.
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

import javax.management.monitor.Monitor;

import com.revature.model.ReimbursementRequest;
import com.revature.model.ReimbursementRequest.ReimbursementType;

public class ModifyRequestHandler extends RequestHandler {
    
    // instance variables ------------------
    private UserProfileDAO updao;
    private ReimbursementRequestDAO rrdao;

    // constructor(s) ----------------------

    public ModifyRequestHandler(UserProfileDAO updao, ReimbursementRequestDAO rrdao){

        this.updao = updao;
        this.rrdao = rrdao;
    }

    // handler methods ---------------------------

    /**
     * Employee submits a new reimbursement request.
     * If successful, the message of the returned response will indicate the ID of the new
     * reimb-req.
     * Fails if the req is missing parameter(s) : (type, moneyAmount)
     * Can optionally have parameter(s) : (description)
     * Fails if the logged-in user cannot be found.
     * Fails if there is a DAO exception.
     * Fails if the moneyAmount parameter cannot be covnerted to a long.
     * 
     * @param req
     * @return
     */
    public ERSResponse handleSubmitRequest(ERSRequest req){

        if (!(req.hasParameter(ERSRequest.REIMBURSEMENT_TYPE) 
                && (req.hasParameter(ERSRequest.MONEY_AMOUNT))))
            return getMalformedRequestResponse();
        
        try{
            int authorID = req.getUserID();

            if (!updao.checkExists(authorID)) 
                return getUserDoesNotExistResponse(authorID);
            
            // good to go ahead and build the reimb-req
            long moneyAmount = Long.parseLong(req.getParameter(ERSRequest.MONEY_AMOUNT));
            ReimbursementType type = ReimbursementType.fromString(
                    req.getParameter(ERSRequest.REIMBURSEMENT_TYPE));

            ReimbursementRequest reimb 
                    = new ReimbursementRequest(authorID, moneyAmount, type);
            if (req.hasParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION))
                reimb.setDescription(
                        req.getParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION));
            
            int reimbID = rrdao.saveReimbursementRequest(reimb);
            ERSResponse res = new ERSResponse(
                    ERSResponseType.SUCCESS,
                    String.format(
                            "Successfully submitted new reimbursement request with ID %n",
                             reimbID));
            return res;
        }
        catch(DAOException e){
            return getGenericDAOExceptionResponse();
        }
        catch(NumberFormatException e){
            return new ERSResponse(
                    ERSResponseType.INVALID_PARAMETER, 
                    "Invalid input format for money amount field.");
        }
    }
}
