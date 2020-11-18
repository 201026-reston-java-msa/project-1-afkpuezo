/**
 * Tests the ViewRequestHandler class
 */
package com.revature.service.handlers;

import static org.junit.Assert.*;

import com.revature.model.ReimbursementRequest;
import com.revature.model.UserProfile;
import com.revature.model.ReimbursementRequest.ReimbursementStatus;
import com.revature.model.ReimbursementRequest.ReimbursementType;
import com.revature.model.UserProfile.UserRole;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO.SearchType;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSRequest.ERSRequestType;
import com.revature.service.comms.ERSResponse.ERSResponseType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class TestVRH extends TestRequestHandler{

    // rules
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    // static/class variables
    private static ViewRequestHandler vrh;
    private static UserProfileDAO updao;
    private static ReimbursementRequestDAO rrdao;

    @Before
    public void setUpVRH(){
        
        updao = mock(UserProfileDAO.class); // more than one way to do this
        rrdao = mock(ReimbursementRequestDAO.class);
        vrh = new ViewRequestHandler(updao, rrdao);
    }
    
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // Tests
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

    // --------------------------------------------------------------------------
    // handleEmployeeViewPending
    // --------------------------------------------------------------------------

    @Test
    public void testHandleEmployeeViewPending() throws DAOException {

        int userID = 1;
        int rrID = 2;
        long moneyAmount = 12345;
        ReimbursementType type = ReimbursementType.LODGING;

        List<ReimbursementRequest> rrlist = new ArrayList<>();
        rrlist.add(new ReimbursementRequest(rrID, userID, moneyAmount, type));

        ERSRequest req = new ERSRequest(
                ERSRequestType.EMPLOYEE_VIEW_PENDING, userID, UserRole.EMPLOYEE);
        when (rrdao.getReimbursementRequests(userID, SearchType.PENDING))
                .thenReturn(rrlist);
        when (updao.checkExists(userID)).thenReturn(true); // forgot to do this!

        ERSResponse res = vrh.handleEmployeeViewPending(req);
        assertNotNull(res);

        List<UserProfile> returnedUserList = res.getReturnedUserProfiles();
        assertTrue(returnedUserList.isEmpty());

        List<ReimbursementRequest> returnedReimbList 
                = res.getReturnedReimbursementRequests();
        assertEquals(rrlist.size(), returnedReimbList.size());
    }

    /**
     * I don't know if I want to create a test like this for every single handler
     */
    @Test
    public void testHandleEmployeeViewPendingUserNotFound() throws DAOException {

        int userID = 1;

        ERSRequest req = new ERSRequest(
                ERSRequestType.EMPLOYEE_VIEW_PENDING, userID, UserRole.EMPLOYEE);
        when(updao.checkExists(userID)).thenReturn(false);

        ERSResponse res = vrh.handleEmployeeViewPending(req);
        ensureInvalidParameterResponse(res);
    }

    /**
     * I don't know if I want to create a test like this for every single handler
     */
    @Test
    public void testHandleEmployeeViewPendingDAOException() throws DAOException {

        int userID = 1;
        int rrID = 2;
        long moneyAmount = 12345;
        ReimbursementType type = ReimbursementType.LODGING;

        List<ReimbursementRequest> rrlist = new ArrayList<>();
        rrlist.add(new ReimbursementRequest(rrID, userID, moneyAmount, type));

        ERSRequest req = new ERSRequest(
                ERSRequestType.EMPLOYEE_VIEW_PENDING, userID, UserRole.EMPLOYEE);
        when (rrdao.getReimbursementRequests(userID, SearchType.PENDING))
                .thenThrow(new DAOException(""));
        when (updao.checkExists(userID)).thenReturn(true);

        ERSResponse res = vrh.handleEmployeeViewPending(req);
        ensureDatabaseErrorResponse(res);
    }

    // --------------------------------------------------------------------------
    // handleEmployeeViewResolved
    // --------------------------------------------------------------------------

    @Test
    public void testHandleEmployeeViewResolved() throws DAOException {

        int userID = 1;
        int rrID = 2;
        long moneyAmount = 12345;
        ReimbursementType type = ReimbursementType.LODGING;

        List<ReimbursementRequest> rrlist = new ArrayList<>();
        ReimbursementRequest reimb = 
                new ReimbursementRequest(rrID, userID, moneyAmount, type);
        reimb.setStatus(ReimbursementStatus.APPROVED);
        rrlist.add(reimb);

        ERSRequest req = new ERSRequest(
                ERSRequestType.EMPLOYEE_VIEW_RESOLVED, userID, UserRole.EMPLOYEE);
        when (rrdao.getReimbursementRequests(userID, SearchType.RESOLVED))
                .thenReturn(rrlist);
        when (updao.checkExists(userID)).thenReturn(true); // forgot to do this!

        ERSResponse res = vrh.handleEmployeeViewResolved(req);
        assertNotNull(res);

        List<UserProfile> returnedUserList = res.getReturnedUserProfiles();
        assertTrue(returnedUserList.isEmpty());

        List<ReimbursementRequest> returnedReimbList 
                = res.getReturnedReimbursementRequests();
        assertEquals(rrlist.size(), returnedReimbList.size());
    }

    @Test
    public void testHandleEmployeeViewResolvedUserNotFound() throws DAOException {

        int userID = 1;

        ERSRequest req = new ERSRequest(
                ERSRequestType.EMPLOYEE_VIEW_RESOLVED, userID, UserRole.EMPLOYEE);
        when(updao.checkExists(userID)).thenReturn(false);

        ERSResponse res = vrh.handleEmployeeViewResolved(req);
        ensureInvalidParameterResponse(res);
    }

    @Test
    public void testHandleEmployeeViewResolvedDAOException() throws DAOException {

        int userID = 1;
        int rrID = 2;
        long moneyAmount = 12345;
        ReimbursementType type = ReimbursementType.LODGING;

        List<ReimbursementRequest> rrlist = new ArrayList<>();
        rrlist.add(new ReimbursementRequest(rrID, userID, moneyAmount, type));

        ERSRequest req = new ERSRequest(
                ERSRequestType.EMPLOYEE_VIEW_RESOLVED, userID, UserRole.EMPLOYEE);
        when (rrdao.getReimbursementRequests(userID, SearchType.RESOLVED))
                .thenThrow(new DAOException(""));
        when (updao.checkExists(userID)).thenReturn(true);

        ERSResponse res = vrh.handleEmployeeViewResolved(req);
        ensureDatabaseErrorResponse(res);
    }

    // helper methods --------------------

}
