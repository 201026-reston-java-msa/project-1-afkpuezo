/**
 * This class contains tests for the ModifyRequestHandler class.
 */
package com.revature.service.handlers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import com.revature.model.ReimbursementRequest;
import com.revature.model.UserProfile;
import com.revature.model.ReimbursementRequest.ReimbursementStatus;
import com.revature.model.ReimbursementRequest.ReimbursementType;
import com.revature.model.UserProfile.UserRole;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSRequest.ERSRequestType;

public class TestMRH extends TestRequestHandler{
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    // static/class variables
    private static ModifyRequestHandler mrh;
    private static UserProfileDAO updao;
    private static ReimbursementRequestDAO rrdao;

    @Before
    public void setUpVRH(){
        
        updao = mock(UserProfileDAO.class); // more than one way to do this
        rrdao = mock(ReimbursementRequestDAO.class);
        mrh = new ModifyRequestHandler(updao, rrdao);
    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // Tests
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

    // --------------------------------------------------------------------------
    // handleSubmitRequest
    // --------------------------------------------------------------------------

    /**
     * Currently does not check the actual content of the message string, just checks to
     * make sure one exists.
     */
    @Test
    public void testHandleSubmitRequest() throws DAOException {
        
        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        req.putParameter(
                ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "" + 123456L);
        req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(true);
        when(rrdao.saveReimbursementRequest(any())).thenReturn(reimbID);

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureSuccessfulResponse(res);
        ensureResponseListsAreEmpty(res);
        assertNotEquals("", res.getMessage());
    }

    @Test
    public void testHandleSubmitRequestMalformed() throws DAOException {

        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        req.putParameter(
                ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        //req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "" + 123456L);
        req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(true);
        when(rrdao.saveReimbursementRequest(any())).thenReturn(reimbID);

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureMalformedRequestResponse(res);
    }

    @Test
    public void testHandleSubmitRequestMalformed2() throws DAOException {

        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        //req.putParameter(
        //        ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "" + 123456L);
        req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(true);
        when(rrdao.saveReimbursementRequest(any())).thenReturn(reimbID);

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureMalformedRequestResponse(res);
    }

    @Test
    public void testHandleSubmitRequestNoDescription() throws DAOException {

        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        req.putParameter(
                ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "" + 123456L);
        //req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(true);
        when(rrdao.saveReimbursementRequest(any())).thenReturn(reimbID);

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureSuccessfulResponse(res);
        ensureResponseListsAreEmpty(res);
        assertNotEquals("", res.getMessage());
    }

    @Test
    public void testHandleSubmitRequestUserNotFound() throws DAOException {

        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        req.putParameter(
                ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "" + 123456L);
        //req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(false);
        when(rrdao.saveReimbursementRequest(any())).thenReturn(reimbID);

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleSubmitRequestDAOException() throws DAOException {

        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        //int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        req.putParameter(
                ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "" + 123456L);
        req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(true);
        when(rrdao.saveReimbursementRequest(any())).thenThrow(new DAOException(""));

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureDatabaseErrorResponse(res);
    }

    @Test
    public void testHandleSubmitRequestInvalidMoneyAmount() throws DAOException {

        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        int reimbID = 454;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole);
        req.putParameter(
                ERSRequest.REIMBURSEMENT_TYPE_KEY, "" + ReimbursementType.LODGING);
        req.putParameter(ERSRequest.MONEY_AMOUNT_KEY, "not a long");
        req.putParameter(ERSRequest.REIMBURSEMENT_DESCRIPTION_KEY, "stayed in hotel");

        when(updao.checkExists(authorID)).thenReturn(true);
        when(rrdao.saveReimbursementRequest(any())).thenReturn(reimbID);

        ERSResponse res = mrh.handleSubmitRequest(req);
        ensureInvalidParameterResponse(res);
    }

    // --------------------------------------------------------------------------
    // handleSubmitEmployeeUpdateSelf
    // --------------------------------------------------------------------------

    /**
     * Tests if all fields are changed
     */
    @Test
    public void testHandleEmployeeUpdateSelf() throws DAOException {

        int userID = 3;
        UserRole role = UserRole.EMPLOYEE;
        String username = "emp";
        String first = "first";
        String last = "last";
        String email = "email@me.com";
        UserProfile up = new UserProfile(userID, role, username, first, last, email);

        // change everything this time
        String newUsername = "nemp";
        String newFirst = "firstTwo";
        String newLast = "lastTwo";
        String newEmail = "email@two.com";
        ERSRequest req 
                = new ERSRequest(ERSRequestType.EMPLOYEE_UPDATE_SELF, userID, role);
        req.putParameter(ERSRequest.USERNAME_KEY, newUsername);
        req.putParameter(ERSRequest.FIRST_NAME_KEY, newFirst);
        req.putParameter(ERSRequest.LAST_NAME_KEY, newLast);
        req.putParameter(ERSRequest.EMAIL_ADDRESS_KEY, newEmail);

        // make sure everything can proceed
        when(updao.checkExists(userID)).thenReturn(true);
        when(updao.getUserProfile(userID)).thenReturn(up);
        when(updao.checkExists(newUsername)).thenReturn(false);
        when(updao.checkExists(newEmail)).thenReturn(false);
        // tried to use mockito answer here but it was too complicated

        ERSResponse res = mrh.handleEmployeeUpdateSelf(req);
        ensureSuccessfulResponse(res);
        ensureResponseListsAreEmpty(res);

        // the method should have changed the up that we passed in, let's check
        assertEquals(newUsername, up.getUsername());
        assertEquals(newFirst, up.getFirstName());
        assertEquals(newLast, up.getLastName());
        assertEquals(newEmail, up.getEmailAddress());
    }

    /**
     * Does NOT change username or email, so it shouldn't matter that they are already
     * in use
     */
    @Test
    public void testHandleEmployeeUpdateSelfOnlyFirstAndLastNames() throws DAOException {

        int userID = 3;
        UserRole role = UserRole.EMPLOYEE;
        String username = "emp";
        String first = "first";
        String last = "last";
        String email = "email@me.com";
        UserProfile up = new UserProfile(userID, role, username, first, last, email);

        // only change personal names
        String newUsername = "nemp";
        String newFirst = "firstTwo";
        String newLast = "lastTwo";
        String newEmail = "email@two.com";
        ERSRequest req 
                = new ERSRequest(ERSRequestType.EMPLOYEE_UPDATE_SELF, userID, role);
        req.putParameter(ERSRequest.USERNAME_KEY, username); // keep old value
        req.putParameter(ERSRequest.FIRST_NAME_KEY, newFirst);
        req.putParameter(ERSRequest.LAST_NAME_KEY, newLast);
        req.putParameter(ERSRequest.EMAIL_ADDRESS_KEY, email); // keep old value

        // make sure everything can proceed
        when(updao.checkExists(userID)).thenReturn(true);
        when(updao.getUserProfile(userID)).thenReturn(up);
        when(updao.checkExists(newUsername)).thenReturn(true); // shouldn't be checked
        when(updao.checkExists(newEmail)).thenReturn(true); // shouldn't be checked
        // tried to use mockito answer here but it was too complicated

        ERSResponse res = mrh.handleEmployeeUpdateSelf(req);
        ensureSuccessfulResponse(res);
        ensureResponseListsAreEmpty(res);

        // the method should have changed the up that we passed in, let's check
        assertEquals(username, up.getUsername());
        assertEquals(newFirst, up.getFirstName());
        assertEquals(newLast, up.getLastName());
        assertEquals(email, up.getEmailAddress());
    }

    @Test
    public void testHandleEmployeeUpdateSelfUsernameInUse() throws DAOException {

        int userID = 3;
        UserRole role = UserRole.EMPLOYEE;
        String username = "emp";
        String first = "first";
        String last = "last";
        String email = "email@me.com";
        UserProfile up = new UserProfile(userID, role, username, first, last, email);

        // change everything this time
        String newUsername = "nemp";
        String newFirst = "firstTwo";
        String newLast = "lastTwo";
        String newEmail = "email@two.com";
        ERSRequest req 
                = new ERSRequest(ERSRequestType.EMPLOYEE_UPDATE_SELF, userID, role);
        req.putParameter(ERSRequest.USERNAME_KEY, newUsername);
        req.putParameter(ERSRequest.FIRST_NAME_KEY, newFirst);
        req.putParameter(ERSRequest.LAST_NAME_KEY, newLast);
        req.putParameter(ERSRequest.EMAIL_ADDRESS_KEY, newEmail);

        // indicate the username is already taken
        when(updao.checkExists(userID)).thenReturn(true);
        when(updao.getUserProfile(userID)).thenReturn(up);
        when(updao.checkExists(newUsername)).thenReturn(true);
        when(updao.checkExists(newEmail)).thenReturn(false);

        ERSResponse res = mrh.handleEmployeeUpdateSelf(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleEmployeeUpdateSelfEmailInUse() throws DAOException {

        int userID = 3;
        UserRole role = UserRole.EMPLOYEE;
        String username = "emp";
        String first = "first";
        String last = "last";
        String email = "email@me.com";
        UserProfile up = new UserProfile(userID, role, username, first, last, email);

        // change everything this time
        String newUsername = "nemp";
        String newFirst = "firstTwo";
        String newLast = "lastTwo";
        String newEmail = "email@two.com";
        ERSRequest req 
                = new ERSRequest(ERSRequestType.EMPLOYEE_UPDATE_SELF, userID, role);
        req.putParameter(ERSRequest.USERNAME_KEY, newUsername);
        req.putParameter(ERSRequest.FIRST_NAME_KEY, newFirst);
        req.putParameter(ERSRequest.LAST_NAME_KEY, newLast);
        req.putParameter(ERSRequest.EMAIL_ADDRESS_KEY, newEmail);

        // indicate the email is already taken
        when(updao.checkExists(userID)).thenReturn(true);
        when(updao.getUserProfile(userID)).thenReturn(up);
        when(updao.checkExists(newUsername)).thenReturn(false);
        when(updao.checkExists(newEmail)).thenReturn(true);

        ERSResponse res = mrh.handleEmployeeUpdateSelf(req);
        ensureInvalidParameterResponse(res);
    }

    /**
     * Only checks the case where no parameters are included at all
     */
    @Test
    public void testHandleEmployeeUpdateMalformed() throws DAOException {

        int userID = 3;
        UserRole role = UserRole.EMPLOYEE;

        ERSRequest req 
                = new ERSRequest(ERSRequestType.EMPLOYEE_UPDATE_SELF, userID, role);

        when(updao.checkExists(userID)).thenReturn(true);

        ERSResponse res = mrh.handleEmployeeUpdateSelf(req);
        ensureMalformedRequestResponse(res);
    }

    /**
     * Simulates a thrown DAOException when the user's id is checked
     * 
     */
    @Test
    public void testHandleEmployeeUpdateSelfDAOException() throws DAOException {

        int userID = 3;
        UserRole role = UserRole.EMPLOYEE;
        //UserProfile up = new UserProfile(userID, role, username, first, last, email);

        // change everything this time
        String newUsername = "nemp";
        String newFirst = "firstTwo";
        String newLast = "lastTwo";
        String newEmail = "email@two.com";
        ERSRequest req 
                = new ERSRequest(ERSRequestType.EMPLOYEE_UPDATE_SELF, userID, role);
        req.putParameter(ERSRequest.USERNAME_KEY, newUsername);
        req.putParameter(ERSRequest.FIRST_NAME_KEY, newFirst);
        req.putParameter(ERSRequest.LAST_NAME_KEY, newLast);
        req.putParameter(ERSRequest.EMAIL_ADDRESS_KEY, newEmail);

        // make sure everything can proceed
        when(updao.checkExists(userID)).thenThrow(new DAOException(""));

        ERSResponse res = mrh.handleEmployeeUpdateSelf(req);
        ensureDatabaseErrorResponse(res);
    }

    // --------------------------------------------------------------------------
    // handleApproveRequest
    // --------------------------------------------------------------------------

    @Test
    public void testHandleApproveRequest() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        // defaults to PENDING
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);

        ERSRequest req = new ERSRequest(ERSRequestType.APPROVE_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(true);
        when(rrdao.checkExists(reimbID)).thenReturn(true);
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleApproveRequest(req);
        ensureSuccessfulResponse(res);
        ensureResponseListsAreEmpty(res);

        // should edit the reference "returned" by the mock dao
        assertEquals(ReimbursementStatus.APPROVED, reimb.getStatus());
    }

    /**
     * Only tests on a reimb-req that is DENIED
     * 
     * @throws DAOException
     */
    @Test
    public void testHandleApproveRequestNotPending() throws DAOException {

        int userID = 4; // the logged-in manager (but not)
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        ReimbursementStatus oldStatus = ReimbursementStatus.DENIED;
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);
        reimb.setStatus(oldStatus);

        ERSRequest req = new ERSRequest(ERSRequestType.APPROVE_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(true);
        when(rrdao.checkExists(reimbID)).thenReturn(true);
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleApproveRequest(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleApproveRequestUserNotExist() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        // defaults to PENDING
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);

        ERSRequest req = new ERSRequest(ERSRequestType.APPROVE_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(false); // not found
        when(rrdao.checkExists(reimbID)).thenReturn(true);
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleApproveRequest(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleApproveRequestReimbReqNotFound() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45; // invalid
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        // defaults to PENDING
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);

        ERSRequest req = new ERSRequest(ERSRequestType.APPROVE_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(true);
        when(rrdao.checkExists(reimbID)).thenReturn(false); // not found
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleApproveRequest(req);
        ensureInvalidParameterResponse(res);
    }

    /**
     * Only tests if a DAOException is thrown when checking for the userID
     * 
     * @throws DAOException
     */
    @Test
    public void testHandleApproveRequestDAOException() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        ERSRequest req = new ERSRequest(ERSRequestType.APPROVE_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenThrow(new DAOException(""));
        ERSResponse res = mrh.handleApproveRequest(req);
        ensureDatabaseErrorResponse(res);
    }

    @Test
    public void testHandleApproveRequestMalformed() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        //int reimbID = 45;
        ERSRequest req = new ERSRequest(ERSRequestType.APPROVE_REQUEST, userID, role);
        //req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        ERSResponse res = mrh.handleApproveRequest(req);
        ensureMalformedRequestResponse(res);
    }

    // --------------------------------------------------------------------------
    // handleDenyRequest
    // --------------------------------------------------------------------------

    @Test
    public void testHandleDenyRequest() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        // defaults to PENDING
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);

        ERSRequest req = new ERSRequest(ERSRequestType.DENY_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(true);
        when(rrdao.checkExists(reimbID)).thenReturn(true);
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleDenyRequest(req);
        ensureSuccessfulResponse(res);
        ensureResponseListsAreEmpty(res);

        // should edit the reference "returned" by the mock dao
        assertEquals(ReimbursementStatus.DENIED, reimb.getStatus());
    }

    /**
     * Only tests on a reimb-req that is DENIED
     * 
     * @throws DAOException
     */
    @Test
    public void testHandleDenyRequestNotPending() throws DAOException {

        int userID = 4; // the logged-in manager (but not)
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        ReimbursementStatus oldStatus = ReimbursementStatus.DENIED;
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);
        reimb.setStatus(oldStatus);

        ERSRequest req = new ERSRequest(ERSRequestType.DENY_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(true);
        when(rrdao.checkExists(reimbID)).thenReturn(true);
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleDenyRequest(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleDenyRequestUserNotExist() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        // defaults to PENDING
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);

        ERSRequest req = new ERSRequest(ERSRequestType.DENY_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(false); // not found
        when(rrdao.checkExists(reimbID)).thenReturn(true);
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleDenyRequest(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleDenyRequestReimbReqNotFound() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45; // invalid
        int authorID = 1; // employee asking for money
        long moneyAmount = 12345L;
        ReimbursementType type = ReimbursementType.FOOD;
        // defaults to PENDING
        ReimbursementRequest reimb 
                        = new ReimbursementRequest(reimbID, authorID, moneyAmount, type);

        ERSRequest req = new ERSRequest(ERSRequestType.DENY_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenReturn(true);
        when(rrdao.checkExists(reimbID)).thenReturn(false); // not found
        when(rrdao.getReimbursementRequest(reimbID)).thenReturn(reimb);
        ERSResponse res = mrh.handleDenyRequest(req);
        ensureInvalidParameterResponse(res);
    }

    /**
     * Only tests if a DAOException is thrown when checking for the userID
     * 
     * @throws DAOException
     */
    @Test
    public void testHandleDenyRequestDAOException() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        int reimbID = 45;
        ERSRequest req = new ERSRequest(ERSRequestType.DENY_REQUEST, userID, role);
        req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        when(updao.checkExists(userID)).thenThrow(new DAOException(""));
        ERSResponse res = mrh.handleDenyRequest(req);
        ensureDatabaseErrorResponse(res);
    }

    @Test
    public void testHandleDenyRequestMalformed() throws DAOException {

        int userID = 4; // the logged-in manager
        UserRole role = UserRole.MANAGER;

        //int reimbID = 45;
        ERSRequest req = new ERSRequest(ERSRequestType.DENY_REQUEST, userID, role);
        //req.putParameter(ERSRequest.REIMBURSEMENT_ID_KEY, "" + reimbID);

        ERSResponse res = mrh.handleDenyRequest(req);
        ensureMalformedRequestResponse(res);
    }

}
