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
import com.revature.model.UserProfile;
import com.revature.model.ReimbursementRequest.ReimbursementType;

public class ModifyRequestHandler extends RequestHandler {

    // instance variables ------------------
    private UserProfileDAO updao;
    private ReimbursementRequestDAO rrdao;

    // constructor(s) ----------------------

    public ModifyRequestHandler(UserProfileDAO updao, ReimbursementRequestDAO rrdao) {

        this.updao = updao;
        this.rrdao = rrdao;
    }

    // handler methods ---------------------------

    /**
     * Employee submits a new reimbursement request. If successful, the message of
     * the returned response will indicate the ID of the new reimb-req. Fails if the
     * req is missing parameter(s) : (type, moneyAmount) Can optionally have
     * parameter(s) : (description) Fails if the logged-in user cannot be found.
     * Fails if there is a DAO exception. Fails if the moneyAmount parameter cannot
     * be covnerted to a long.
     * 
     * @param req
     * @return
     */
    public ERSResponse handleSubmitRequest(ERSRequest req) {

        if (!(req.hasParameter(ERSRequest.REIMBURSEMENT_TYPE_KEY) && (req.hasParameter(ERSRequest.MONEY_AMOUNT_KEY))))
            return getMalformedRequestResponse();

        try {
            int authorID = req.getUserID();

            if (!updao.checkExists(authorID))
                return getUserDoesNotExistResponse(authorID);

            // good to go ahead and build the reimb-req
            long moneyAmount = Long.parseLong(req.getParameter(ERSRequest.MONEY_AMOUNT_KEY));
            ReimbursementType type = ReimbursementType.fromString(req.getParameter(ERSRequest.REIMBURSEMENT_TYPE_KEY));
            if (type == ReimbursementType.NONE)
                return new ERSResponse(ERSResponseType.INVALID_PARAMETER, "Invalid input for expense type.");

            ReimbursementRequest reimb = new ReimbursementRequest(authorID, moneyAmount, type);
            if (req.hasParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY))
                reimb.setDescription(req.getParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY));

            int reimbID = rrdao.saveReimbursementRequest(reimb);
            ERSResponse res = new ERSResponse(ERSResponseType.SUCCESS,
                    String.format("Successfully submitted new reimbursement request with ID %n", reimbID));
            return res;
        } catch (DAOException e) {
            return getGenericDAOExceptionResponse();
        } catch (NumberFormatException e) {
            return new ERSResponse(ERSResponseType.INVALID_PARAMETER, "Invalid input format for money amount field.");
        }
    }

    /**
     * Employee updates their personal information. Can't change ID; but can change
     * username, firstName, lastName, emailAddress Assumes parameters are formatted
     * properly. Fails if any of the parameters are missing (if you don't want to
     * change a given field, just pass the old value in the parameter) Fails if the
     * new username or emailaddress are already in use. Fails if the logged-in user
     * cannot be found. Fails if there is a DAO exception.
     * 
     * @param req
     * @return
     */
    public ERSResponse handleEmployeeUpdateSelf(ERSRequest req) {

        if (!(req.hasParameter(ERSRequest.USERNAME_KEY) 
                && (req.hasParameter(ERSRequest.FIRST_NAME_KEY)))
                && (req.hasParameter(ERSRequest.LAST_NAME_KEY))
                && (req.hasParameter(ERSRequest.EMAIL_ADDRESS_KEY)))
            return getMalformedRequestResponse();

        try{

            int userID = req.getUserID();

            if (!updao.checkExists(userID))
                return getUserDoesNotExistResponse(userID);
            
            // this is a clumsy way of checking to see if there are duplicate values
            UserProfile up = updao.getUserProfile(userID);

            String newUsername = req.getParameter(ERSRequest.USERNAME_KEY);
            String newFirst = req.getParameter(ERSRequest.FIRST_NAME_KEY);
            String newLast = req.getParameter(ERSRequest.LAST_NAME_KEY);
            String newEmail = req.getParameter(ERSRequest.EMAIL_ADDRESS_KEY);

            // if new username, make sure it's unique
            if (!up.getUsername().equals(newUsername)){
                
                if (updao.checkExists(newUsername))
                    return new ERSResponse(
                            ERSResponseType.INVALID_PARAMETER,
                            String.format(
                                    "Username '%s' is already in use.", newUsername));
                else up.setUsername(newUsername);
            }

            // same with email
            if (!up.getEmailAddress().equals(newEmail)){
                
                if (updao.checkExists(newEmail))
                    return new ERSResponse(
                            ERSResponseType.INVALID_PARAMETER,
                            String.format(
                                    "Email address '%s' is already in use.", newEmail));
                else up.setEmailAddress(newEmail);
            }

            // okay to double up on real names
            up.setFirstName(newFirst);
            up.setLastName(newLast);

            updao.saveUserProfile(up);
            return new ERSResponse(ERSResponseType.SUCCESS);
        }
        catch (DAOException e) {
            return getGenericDAOExceptionResponse();
        }

    }
}
