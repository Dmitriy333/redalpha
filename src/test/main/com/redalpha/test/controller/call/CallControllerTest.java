package com.redalpha.test.controller.call;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.service.EntityValidationException;
import com.redalpha.test.service.ServiceException;
import com.redalpha.test.service.call.CallService;

@RunWith(MockitoJUnitRunner.class)
public class CallControllerTest {

    @Mock
    private CallService callService;
    @InjectMocks
    private CallController callController = new CallController();

    private static final Call CREATE_CALL = new Call("123456780", "firstname2", "lastname2", new Date());
    private static final List<Call> CALL_LIST = new ArrayList<Call>() {
        private static final long serialVersionUID = 1L;

        {
            add(new Call("123456789", "firstname", "lastname", new Date()));
            add(new Call("123456780", "firstname2", "lastname2", new Date()));
        }
    };

    @Test
    public void getAllCallsTest() throws ServiceException {
        when(callService.getAllCalls()).thenReturn(CALL_LIST);

        final ResponseEntity<List<Call>> calls = callController.getAllCalls();

        verify(callService, times(1)).getAllCalls();
        assertEquals(OK, calls.getStatusCode());
        assertEquals(calls.getBody().size(), 2);
    }

    @Test
    public void getAllCallsTestThrowsException() throws ServiceException {
        when(callService.getAllCalls()).thenThrow(new ServiceException("Can not get calls."));

        final ResponseEntity<List<Call>> calls = callController.getAllCalls();

        verify(callService, times(1)).getAllCalls();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, calls.getStatusCode());
        assertEquals(calls.getBody(), null);
    }

    @Test
    public void addCallTest() throws ServiceException, EntityValidationException {
        when(callService.addCall(CREATE_CALL)).thenReturn(CREATE_CALL);

        final ResponseEntity<Call> call = callController.addCall(CREATE_CALL);

        verify(callService, times(1)).addCall(CREATE_CALL);
        assertEquals(HttpStatus.CREATED, call.getStatusCode());
        assertEquals(call.getBody(), CREATE_CALL);
    }

    @Test
    public void addNonValidCallTest() throws ServiceException, EntityValidationException {
        when(callService.addCall(CREATE_CALL)).thenThrow(new EntityValidationException("Not valid."));

        final ResponseEntity<Call> call = callController.addCall(CREATE_CALL);

        verify(callService, times(1)).addCall(CREATE_CALL);
        assertEquals(HttpStatus.BAD_REQUEST, call.getStatusCode());
        assertEquals(call.getBody(), null);
    }

    @Test
    public void addCallThrowServiceExceptionTest() throws ServiceException, EntityValidationException {
        when(callService.addCall(CREATE_CALL)).thenThrow(new ServiceException("Not valid."));

        final ResponseEntity<Call> call = callController.addCall(CREATE_CALL);

        verify(callService, times(1)).addCall(CREATE_CALL);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, call.getStatusCode());
        assertEquals(call.getBody(), null);
    }
}
