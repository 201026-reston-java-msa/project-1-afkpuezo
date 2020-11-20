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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.revature.model.ReimbursementRequest;
import com.revature.model.ReimbursementRequest.ReimbursementType;
import com.revature.model.UserProfile.UserRole;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSRequest.ERSRequestType;
import com.revature.service.comms.ERSResponse.ERSResponseType;

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
        int reimbID = 454;
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
}
