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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.revature.model.ReimbursementRequest;
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

    @Test
    public void testHandleSubmitRequest(){
        
        int authorID = 2;
        UserRole authorRole = UserRole.EMPLOYEE;
        ERSRequest req 
                = new ERSRequest(ERSRequestType.SUBMIT_REQUEST, authorID, authorRole); 
    }
}
